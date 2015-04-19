grammar vrjass;

// Rule where begin
// Final EOF prevent a rare but known bug of Antlr4, dont remove it
init: altInit+ EOF;

altInit
	:functionDefinition (EOL functionDefinition)*
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

functionDefinition:
	 'function' functionName=ID 'takes' parameters 'returns' returnType EOL
	 	statements
	 'endfunction'
	 ;

expression
	:'(' expression ')' #Parenthesis
	|left=expression '/' right=expression #Div
	|left=expression '*' right=expression #Mult
	|left=expression '-' right=expression #Minus
	|left=expression '+' right=expression #Plus
	|functionExpression #Function
	|INT #Integer
	|STR #String
	|ID #Variable
	;
	
argument: expression;

arguments
	:argument (',' argument)*
	|
	; 

functionStatement: 'call' functionExpression;

functionExpression: functionName=ID '(' arguments ')';

returnStatement: 'return' expression;

setVariableStatement: 'set' varName=expression ('[' index=expression ']')? '=' value=expression;

localVariableStatement: 'local' variableType (array='array')? varName=ID ('=' value=expression)?;

ID: [a-zA-Z][a-zA-Z0-9_]*;
INT: [0-9]+;
STR: '"' .*? '"';

EOL : [\r\n]+;
WS: [\t ]+ -> skip;