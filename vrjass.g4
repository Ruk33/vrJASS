grammar vrjass;

// Rule where begin
// Final EOF prevent a rare but known bug of Antlr4, dont remove it
init: altInit+ EOF;

altInit
	:functionDefinition
	|globalDefinition
	|libraryDefinition
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
	|EOL
	;

libraryDefinition:
	'library' libraryName=ID EOL
		libraryStatements*
	'endlibrary'
	;

functionDefinition:
	 (visibility=('private'|'public'))? 'function' functionName=ID 'takes' parameters 'returns' returnType EOL
	 	statements
	 'endfunction'
	 ;

expression
	:'(' expression ')' #Parenthesis
	|left=expression '/' right=expression #Div
	|left=expression '*' right=expression #Mult
	|left=expression '-' right=expression #Minus
	|left=expression '+' right=expression #Plus	
	|left=expression operator=('==' | '!=' | '>' | '>=' | '<' | '<=') right=expression #Comparison
	|left=expression operator=('or'|'and') right=expression #Logical
	|functionExpression #Function
	|INT #Integer
	|STR #String
	|varName=ID ('[' index=expression ']')? #Variable
	;
	
argument: expression;

arguments
	:argument (',' argument)*
	|
	; 

functionStatement: 'call' functionExpression;

functionExpression: functionName=ID '(' arguments ')';

returnStatement: 'return' expression;

setVariableStatement: 'set' varName=ID ('[' index=expression ']')? operator=('=' | '/=' | '*=' | '-=' | '+=') value=expression;

localVariableStatement: 'local' variableType (array='array')? varName=ID ('=' value=expression)?;

globalVariableStatement: variableType (array='array')? varName=ID ('=' value=expression)?;

ID: [a-zA-Z][a-zA-Z0-9_]*;
INT: [0-9]+;
STR: '"' .*? '"';

EOL : [\r\n]+;
WS: [\t ]+ -> skip;