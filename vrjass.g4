grammar vrjass;

// Rule where begin
// Final EOF prevent a rare but known bug of Antlr4, dont remove it
init: altInit+ EOF;

altInit
	:functionDefinition
	|interfaceDefinition
	|globalDefinition
	|libraryDefinition
	|classDefinition
	|nativeDefinition
	|typeDefinition
	|EOL
	;

variableType: ID;

parameter: variableType ID; 

parameters
	:(parameter (',' parameter)*)
	|'nothing'
	;

statement
	:functionStatement
	|returnStatement
	|setVariableStatement
	|localVariableStatement
	|ifStatement
	|loopStatement
	|exitwhenStatement
	|EOL
	;
	
statements: statement*;

returnType: variableType|'nothing';

globals
	:globalVariableStatement
	|EOL;

globalDefinition:
	'globals' EOL
		globals*
	'endglobals'
	;

libraryStatements
	:globalDefinition
	|interfaceDefinition
	|functionDefinition
	|classDefinition
	|nativeDefinition
	|typeDefinition
	|EOL
	;
	
requirements: 'requires' ID (',' ID)*;

libraryDefinition:
	'library' libraryName=ID requirements? EOL
		libraryStatements*
	'endlibrary'
	;

methodDefinition:
	(visibility=('private'|'public'))? ABSTRACT? STATIC? 'method' methodName=ID 'takes' parameters 'returns' returnType EOL
	 	statements
	 'endmethod'
	 ;
	 
propertyStatement: (visibility=('private'|'public'))? STATIC? variableType propertyName=ID ('=' value=expression)?;

classStatements
	:methodDefinition
	|propertyStatement
	|interfaceMethodDefinition
	|EOL
	;

extendList
	:(ID (',' ID)*)
	|'array'
	;

classDefinition:
	ABSTRACT? 'struct' className=ID ('extends' extendList)? EOL
		classStatements*
	'endstruct'
	;

interfaceMethodDefinition
	: (visibility='public')? STATIC? 'method' methodName=ID 'takes' parameters 'returns' returnType;

interfaceStatements
	:interfaceMethodDefinition
	|EOL
	;
	
interfaceDefinition:
	(visibility='public')? 'interface' interfaceName=ID EOL
		interfaceStatements*
	'endinterface'
	;

functionDefinition:
	 (visibility=('private'|'public'))? 'function' functionName=ID 'takes' parameters 'returns' returnType EOL
	 	statements
	 'endfunction'
	 ;

typeDefinition:
	'type' typeName=ID 'extends' extendName=ID
	;

nativeDefinition:
	CONSTANT? 'native' functionName=ID 'takes' parameters 'returns' returnType
	;

booleanExpression: expression;

expression
	:left=expression '/' right=expression #Div
	|left=expression '*' right=expression #Mult
	|left=expression '-' right=expression #Minus	
	|left=expression operator=('==' | '!=' | '>' | '>=' | '<' | '<=') right=expression #Comparison
	|left=expression operator=('or'|'and') right=expression #Logical
	|INT #Integer
	|REAL #Real
	|STR #String
	|'-' expression #Negative
	|'not' expression #Not
	|('true'|'false') #Boolean
	|'null' #Null
	|'function' expression #Code
	|left=expression '.' right=expression #Member
	|'this' #This
	|'super' #Super
	|functionExpression #Function
	|varName=ID ('[' index=expression ']')? #Variable	
	|'(' expression ')' #Parenthesis
	|left=expression '+' right=expression #Plus
	;
	
argument: expression;

arguments: argument (',' argument)*; 

exitwhenStatement: 'exitwhen' booleanExpression;

loopStatement: 'loop' EOL statements 'endloop';

elseIfStatement: 'elseif' booleanExpression 'then' EOL statements;

elseStatement: 'else' EOL statements;

ifStatement: 'if' booleanExpression 'then' EOL statements (elseIfStatement)* (elseStatement)? 'endif';

functionStatement: 'call' func=expression;

functionExpression: functionName=ID '(' arguments? ')';

returnStatement: 'return' expression?;

setVariableStatement: 'set' varName=expression operator=('=' | '/=' | '*=' | '-=' | '+=') value=expression;

localVariableStatement: 'local' variableType (array='array')? varName=ID ('=' value=expression)?;

globalVariableStatement
	:CONSTANT? (visibility=('private'|'public'))? variableType (array='array')? varName=ID ('=' value=expression)?
	;

CONSTANT: 'constant';
STATIC: 'static';
ABSTRACT: 'stub';

ID: [a-zA-Z][a-zA-Z0-9_]*;
REAL: [0-9]+ '.' [0-9]* | '.'[0-9]+;
INT: [0-9]+ | '0x' [0-9a-fA-F]+ | '\'' . . . . '\'' | '\'' . '\'';
STR: '"' .*? '"';

EOL: [\r\n]+;

//COMMENT : '/*' .*? '*/' -> skip;
WS: [\t ]+ -> skip;
LINE_COMMENT: '//' .*? [\r\n] -> skip;