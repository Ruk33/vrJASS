grammar vrjass;

init: topDeclaration* EOF;

topDeclaration:
	NL
	|typeDefinition
	|nativeDefinition
	|globalDefinition
	|libraryDefinition
	|interfaceDefinition
	|structDefinition
	|functionDefinition
	;

// Refers to a valid variable type, like integer, reals, etc.
validType: ID;
validName: ID;

variableDeclaration: 
	type=validType ARRAY? name=validName (EQ value=expression)?;

expression:
	INT #Integer
	|REAL #Real
	|STRING #String
	|MINUS expression #Negative
	|NOT expression #Not
	|(TRUE | FALSE) #Boolean	
	|NULL #Null
	|FUNCTION expression #Code
	|THIS #This
	|validName PAREN_LEFT arguments? PAREN_RIGHT #FunctionExpression
	|validName (BRACKET_LEFT index=expression BRACKET_RIGHT)? #VariableExpression
	|expression DOT expression #ChainExpression
	|expression CAST validName #Cast
	|PAREN_LEFT expression PAREN_RIGHT #Parenthesis
	|left=expression DIV right=expression #Div
	|left=expression TIMES right=expression #Mult
	|left=expression MINUS right=expression #Minus
	|left=expression PLUS right=expression #Plus
	|left=expression operator=(EQEQ | NOT_EQ | GREATER | GREATER_EQ | LESS | LESS_EQ) right=expression #Comparison
	|left=expression operator=(OR | AND) right=expression #Logical
	;

typeDefinition: 
	TYPE validName (EXTENDS validType)? NL;

nativeDefinition: 
	CONSTANT? NATIVE validName TAKES parameters RETURNS returnType NL;

/* 
 * ---------------------------------------------------------------------------
 * Globals
 * ---------------------------------------------------------------------------
 */	
globalVariableStatement: 
	(PRIVATE|PUBLIC)? CONSTANT? variableDeclaration NL;

globalStatements: 
	(NL | globalVariableStatement)*;

globalDefinition:
	GLOBALS NL
		globalStatements
	ENDGLOBALS NL
	;


/* 
 * ---------------------------------------------------------------------------
 * Libraries
 * ---------------------------------------------------------------------------
 */

libraryRequirements: 
	validName (COMMA validName)*;

libraryStatement:
	NL
	|globalDefinition
	|interfaceDefinition
	|structDefinition
	|functionDefinition
	;

libraryDefinition:
	LIBRARY name=validName (INITIALIZER libInit=validName)? (REQUIRES libraryRequirements)? NL
		libraryStatement*
	ENDLIBRARY NL
	;

 
 /* 
 * ---------------------------------------------------------------------------
 * Interfaces
 * ---------------------------------------------------------------------------
 */
 interfaceStatement:
	(PRIVATE|PUBLIC)? STATIC? METHOD validName TAKES parameters RETURNS returnType NL;

interfaceDefinition:
	(PRIVATE|PUBLIC)? INTERFACE validName NL
		interfaceStatement*
	ENDINTERFACE NL
	;


/* 
 * ---------------------------------------------------------------------------
 * Structs
 * ---------------------------------------------------------------------------
 */
propertyStatement:
	(PRIVATE|PUBLIC)? STATIC? variableDeclaration NL;

structStatement:
	NL
	|propertyStatement
	|functionDefinition
	;

implementsList: validName (COMMA validName)*;

structDefinition:
	(PRIVATE|PUBLIC)? STRUCT name=validName (EXTENDS extendsFrom=validName)? (IMPLEMENTS implementsList)? NL
		structStatement*
	ENDSTRUCT NL
	;


/* 
 * ---------------------------------------------------------------------------
 * Functions
 * ---------------------------------------------------------------------------
 */
returnType: 
	validType | NOTHING;

parameter: 
	variableDeclaration;

parameters: 
	(parameter (COMMA parameter)*)
	|NOTHING
	;

arguments: 
	expression (COMMA expression)*;

functionDefinition: 
	(PRIVATE|PUBLIC)? CONSTANT? STATIC? (FUNCTION | METHOD) validName (TAKES parameters)? (RETURNS returnType)? NL
		statement*
	(ENDFUNCTION | ENDMETHOD) NL
	;


statement:
	NL
	|localVariableStatement
	|assignmentStatement
	|functionStatement
	|loopStatement
	|exitWhenStatement
	|ifStatement
	|returnStatement
	;

localVariableStatement: 
	LOCAL variableDeclaration NL;

assignmentStatement: 
	SET name=expression operator=(PLUS | MINUS | TIMES | DIV)? EQ value=expression NL;

functionStatement:
	CALL expression NL;

exitWhenStatement:
	EXITWHEN expression NL;

loopStatement:
	LOOP NL
		statement*
	ENDLOOP NL
	;

ifStatement: 
	IF expression THEN NL 
		statement*
		elseIfStatement*
		elseStatement
	ENDIF NL
	;

elseIfStatement: 
	ELSEIF expression THEN NL statement*;

elseStatement: 
	(ELSE NL statement*)?;

returnStatement:
	RETURN (expression)? NL;


CAST: 'cast';
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