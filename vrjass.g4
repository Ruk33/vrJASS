grammar vrjass;

init: topDeclaration* EOF;

topDeclaration:
	NL
	|globalDefinition
	|functionDefinition
	|nativeDefinition
	|typeDefinition
	|structDefinition
	|interfaceDefinition
	|libraryDefinition
	;

expression:
	left=expression DIV right=expression #Div
	|left=expression TIMES right=expression #Mult
	|left=expression MINUS right=expression #Minus
	|left=expression PLUS right=expression #Plus
	|left=expression operator=(EQEQ | NOT_EQ | GREATER | GREATER_EQ | LESS | LESS_EQ) right=expression #Comparison
	|left=expression operator=(OR | AND) right=expression #Logical
	|INT #Integer
	|REAL #Real
	|STRING #String
	|MINUS expression #Negative
	|NOT expression #Not
	|(TRUE | FALSE) #Boolean	
	|NULL #Null
	|FUNCTION expression #Code
	|THIS #This
	//|THISTYPE #Thistype
	//|SUPER #Super
	|functionExpression #Function
	|memberExpression #Member
	|variableExpression #Variable
	|PAREN_LEFT expression PAREN_RIGHT #Parenthesis
	;

superThistypeThis: SUPER | THISTYPE | THIS; 

functionExpression: validName PAREN_LEFT arguments? PAREN_RIGHT;

variableExpression: validName (BRACKET_LEFT index=integerExpression BRACKET_RIGHT)?;

thisExpression: THIS;

functionOrVariable: functionExpression | variableExpression | thisExpression;

//primaryChainExpression: left=superThistypeThis (DOT functionOrVariable)+;

chainExpression: functionOrVariable (DOT functionOrVariable)+;

memberExpression: chainExpression;

integerExpression: expression;

booleanExpression: expression;

statements: statement*;

elseIfStatement: 
	ELSEIF booleanExpression THEN NL statements;
	
elseIfStatements: elseIfStatement*;

elseStatement: 
	(ELSE NL statements)?;

ifStatement: 
	IF booleanExpression THEN NL 
		statements
		elseIfStatements
		elseStatement
	ENDIF NL
	;

loopStatement:
	LOOP NL
		statements
	ENDLOOP NL
	;

publicPrivate: PUBLIC | PRIVATE;

globalVariableStatement: 
	publicPrivate? CONSTANT? validType ARRAY? validName (EQ value=expression)? NL;

localVariableStatement: 
	LOCAL validType ARRAY? validName (EQ value=expression)? NL;

setVariableStatement: 
	SET name=expression operator=(PLUS | MINUS | TIMES | DIV) EQ value=expression NL;

callMethodStatement: CALL memberExpression NL;

callFunctionStatement: CALL functionExpression NL;

exitwhenStatement: 
	EXITWHEN booleanExpression NL;

returnStatement: 
	RETURN (expression)? NL;

statement:
	NL
	|localVariableStatement
	|setVariableStatement
	|callFunctionStatement
	|callMethodStatement
	|exitwhenStatement
	|loopStatement
	|ifStatement
	|returnStatement
	;

globalStatements: 
	(NL | globalVariableStatement)*;

globalDefinition:
	GLOBALS NL
		globalStatements
	ENDGLOBALS NL
	;

typeDefinition: 
	TYPE validName (EXTENDS validType)? NL;

nativeDefinition: 
	CONSTANT? NATIVE validName TAKES parameters RETURNS returnType NL;

propertyStatement:
	visibility STATIC? validType ARRAY? validName (EQ value=expression)? NL;

structStatement:
	NL
	|propertyStatement
	|methodDefinition
	;
	
structStatements: structStatement*;

validImplementName: validName;

implementsList: validImplementName (COMMA validImplementName)*;

extendValidName: validName;

structDefinition:
	visibility STRUCT name=validName (EXTENDS extendValidName)? (IMPLEMENTS implementsList)? NL
		structStatements
	ENDSTRUCT NL
	;

methodDefinition:
	visibility STATIC? METHOD validName TAKES parameters RETURNS returnType NL
		statements
	ENDMETHOD NL
	;

interfaceStatement:
	visibility STATIC? METHOD validName TAKES parameters RETURNS returnType NL;

interfaceStatements: interfaceStatement*;

interfaceDefinition:
	visibility INTERFACE validName NL
		interfaceStatements
	ENDINTERFACE NL
	;

functionDefinition: 
	visibility CONSTANT? FUNCTION validName TAKES parameters RETURNS returnType NL
		statements
	ENDFUNCTION NL
	;

libraryInitializer: 
	(INITIALIZER validName)?;

libraryRequirementsList: 
	validName (COMMA validName)*;

libraryRequirements: 
	(REQUIRES libraryRequirementsList)?;

libraryStatement:
	NL
	|functionDefinition
	|structDefinition
	|interfaceDefinition
	|globalDefinition
	;
	
libraryStatements: libraryStatement*;

libraryDefinition:
	LIBRARY validName libraryInitializer libraryRequirements NL
		libraryStatements
	ENDLIBRARY NL
	;

returnType: 
	validType | NOTHING;

parameter: 
	validType validName;

parameters: 
	(
		parameter (COMMA parameter)*
	)
	|NOTHING
	;
	
arguments: 
	expression (COMMA expression)*;

validType: ID;

validName: ID;

visibility: (PRIVATE | PUBLIC)?;

LIBRARY: 'library';
ENDLIBRARY: 'endlibrary';
INITIALIZER: 'initializer';
REQUIRES: 'requires';
THIS: 'this';
THISTYPE: 'thistype';
SUPER: 'super';
DOT: '.';
PRIVATE: 'private';
PUBLIC: 'public';
AND: 'and';
OR: 'or';
NULL: 'null';
NOT: 'not';
ARRAY: 'array';
LOCAL: 'local';
SET: 'set';
EXITWHEN: 'exitwhen';
CALL: 'call';
GLOBALS: 'globals';
ENDGLOBALS: 'endglobals';
EXTENDS: 'extends';
IMPLEMENTS: 'implements';
TYPE: 'type';
NATIVE: 'native';
CONSTANT: 'constant';
STATIC: 'static';
STRUCT: 'struct';
ENDSTRUCT: 'endstruct';
METHOD: 'method';
ENDMETHOD: 'endmethod';
INTERFACE: 'interface';
ENDINTERFACE: 'endinterface';
FUNCTION: 'function';
ENDFUNCTION: 'endfunction';
TAKES: 'takes';
RETURNS: 'returns';
RETURN: 'return';
IF: 'if';
THEN: 'then';
ELSEIF: 'elseif';
ELSE: 'else';
ENDIF: 'endif';
LOOP: 'loop';
ENDLOOP: 'endloop';
TRUE: 'true';
FALSE: 'false';
NOTHING: 'nothing';
PAREN_LEFT: '(';
PAREN_RIGHT: ')';
BRACKET_LEFT: '[';
BRACKET_RIGHT: ']';
COMMA: ',';
DIV: '/';
TIMES: '*';
MINUS: '-';
PLUS: '+';
EQ: '=';
EQEQ: '==';
NOT_EQ: '!=';
LESS: '<';
LESS_EQ: '<=';
GREATER: '>';
GREATER_EQ: '>=';

NL: NEWLINE+;
fragment NEWLINE: [\r\n];

ID: [a-zA-Z_][a-zA-Z0-9_]*;

STRING: '"' .*? '"';
REAL: [0-9]+ '.' [0-9]* | '.'[0-9]+;
INT: [0-9]+ | '0x' [0-9a-fA-F]+ | '\'' . . . . '\'' | '\'' . '\'';

WS : [ \t]+ -> skip;
COMMENT : '/*' .*? '*/' -> skip;
LINE_COMMENT: '//' ~[\r\n]* -> skip;