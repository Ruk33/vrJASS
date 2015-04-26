grammar vrjass;

// Rule where begin
// Final EOF prevent a rare but known bug of Antlr4, dont remove it
init: altInit+ EOF;

altInit
	:functionDefinition
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
	;
	
statements: (statement EOL)*;

returnType: variableType|'nothing';

globals: (globalVariableStatement EOL)*;

globalDefinition:
	'globals' EOL
		globals
	'endglobals'
	;

libraryStatements
	:globalDefinition
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
	(visibility=('private'|'public'))? STATIC? 'method' methodName=ID 'takes' parameters 'returns' returnType EOL
	 	statements
	 'endmethod'
	 ;
	 
propertyStatement: (visibility=('private'|'public'))? STATIC? variableType propertyName=ID ('=' value=expression)?;

classStatements
	:methodDefinition
	|propertyStatement
	|EOL
	;

classDefinition:
	'struct' className=ID ('extends' extendName=(ID|'array'))? EOL
		classStatements*
	'endstruct'
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

expression
	:left=expression '/' right=expression #Div
	|left=expression '*' right=expression #Mult
	|left=expression '-' right=expression #Minus	
	|left=expression operator=('==' | '!=' | '>' | '>=' | '<' | '<=') right=expression #Comparison
	|left=expression operator=('or'|'and') right=expression #Logical
	|INT #Integer
	|STR #String
	|('true' || 'false') #Boolean
	|'function' expression #Code
	|left=expression '.' right=expression #Member
	|'this' #This
	|functionExpression #Function
	|varName=ID ('[' index=expression ']')? #Variable	
	|'(' expression ')' #Parenthesis
	|left=expression '+' right=expression #Plus
	;
	
argument: expression;

arguments
	:argument (',' argument)*
	|
	; 

exitwhenStatement: 'exitwhen' '(' expression ')';

loopStatements
	:statement
	|exitwhenStatement
	|EOL
	;

loopStatement: 'loop' EOL loopStatements* 'endloop';

elseIfStatement: 'elseif' '(' expression ')' 'then' EOL statements;

elseStatement: 'else' EOL statements;

ifStatement: 'if' '(' expression ')' 'then' EOL statements (elseIfStatement)* (elseStatement)? 'endif';

functionStatement: 'call' func=expression;

functionExpression: functionName=ID '(' arguments ')';

returnStatement: 'return' expression;

setVariableStatement: 'set' varName=expression operator=('=' | '/=' | '*=' | '-=' | '+=') value=expression;

localVariableStatement: 'local' variableType (array='array')? varName=ID ('=' value=expression)?;

globalVariableStatement: CONSTANT? (visibility=('private'|'public'))? variableType (array='array')? varName=ID ('=' value=expression)?;

CONSTANT: 'constant';
STATIC: 'static';

ID: [a-zA-Z][a-zA-Z0-9_]*;
INT: [0-9]+;
STR: '"' .*? '"';

EOL : [\r\n]+;

LINECOMMENT : '//' .*? '\r'? '\n' -> skip;
COMMENT : '/*' .*? '*/' -> skip;
WS: [\t ]+ -> skip;