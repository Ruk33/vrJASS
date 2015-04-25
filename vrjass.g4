grammar vrjass;

// Rule where begin
// Final EOF prevent a rare but known bug of Antlr4, dont remove it
init: altInit+ EOF;

altInit
	:functionDefinition
	|globalDefinition
	|libraryDefinition
	|classDefinition
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
	 
propertyStatement: (visibility=('private'|'public'))? variableType propertyName=ID ('=' value=expression)?;

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

expression
	:left=expression '/' right=expression #Div
	|left=expression '*' right=expression #Mult
	|left=expression '-' right=expression #Minus
	|left=expression '+' right=expression #Plus	
	|left=expression operator=('==' | '!=' | '>' | '>=' | '<' | '<=') right=expression #Comparison
	|left=expression operator=('or'|'and') right=expression #Logical
	|INT #Integer
	|STR #String
	|('true' || 'false') #Boolean
	|'this' #This
	|left=expression '.' right=expression #Member
	|functionExpression #Function
	|varName=ID ('[' index=expression ']')? #Variable
	|'(' expression ')' #Parenthesis
	;
	
argument: expression;

arguments
	:argument (',' argument)*
	|
	; 

elseIfStatement: 'elseif' '(' expression ')' 'then' EOL statements;

elseStatement: 'else' EOL statements;

ifStatement: 'if' '(' expression ')' 'then' EOL statements (elseIfStatement)* (elseStatement)? 'endif';

functionStatement: 'call' func=expression;

functionExpression: functionName=ID '(' arguments ')';

returnStatement: 'return' expression;

setVariableStatement: 'set' varName=ID ('[' index=expression ']')? operator=('=' | '/=' | '*=' | '-=' | '+=') value=expression;

localVariableStatement: 'local' variableType (array='array')? varName=ID ('=' value=expression)?;

globalVariableStatement: (visibility=('private'|'public'))? variableType (array='array')? varName=ID ('=' value=expression)?;

STATIC: 'static';

ID: [a-zA-Z][a-zA-Z0-9_]*;
INT: [0-9]+;
STR: '"' .*? '"';

EOL : [\r\n]+;
WS: [\t ]+ -> skip;