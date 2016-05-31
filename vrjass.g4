grammar vrjass;

init: topDeclaration* EOF;

topDeclaration
    : NL
    | importStatement
	| typeDefinition
	| nativeDefinition
	| globalDefinition
	| libraryDefinition
	| interfaceDefinition
	| structDefinition
	| functionDefinition
	;

importStatement: IMPORT path=STRING NL;

genericExpression: LESS validType GREATER;

// Refers to a valid variable type, like integer, reals, etc.
validType: (validName | chainExpression) genericExpression?;
validName: ID | END;

variableDeclaration: 
	type=validType (ARRAY? name=validName (EQ value=allExpression)?)?;

variableExpression: validName (BRACKET_LEFT index=expression BRACKET_RIGHT)?;

functionExpression: validName PAREN_LEFT arguments? PAREN_RIGHT;

parenthesis: PAREN_LEFT expression PAREN_RIGHT;

thisExpression: THIS;

superExpression: SUPER;

memberExpression: variableExpression | functionExpression;

chainExpression:
    (parenthesis | superExpression | thisExpression | variableExpression | functionExpression | cast) (DOT memberExpression?)+;

cast: CAST original=expression TO (chainExpression | validName);

allExpression
    : expression                                #ignoreExpression
    | functionDefinitionExpression              #AnonymousExpression
    | FUNCTION (validName | chainExpression)    #Code
    ;

expression
    : parenthesis                               #ignoreParenthesisExpression
    | cast                                      #ignoreCastExpression
    | MINUS expression                          #Negative
    | NOT expression                            #Not
    | left=expression MOD right=expression      #Modulo
    | left=expression DIV right=expression      #Div
    | left=expression TIMES right=expression    #Mult
    | left=expression MINUS right=expression    #Minus
    | left=expression PLUS right=expression     #Plus

	| variableExpression                        #ignoreVariableExpression
	| functionExpression                        #ignoreFunctionExpression
	| chainExpression                           #ignoreChainExpression

	| left=expression operator=(EQEQ | NOT_EQ | GREATER | GREATER_EQ | LESS | LESS_EQ) right=expression #Comparison

	| left=expression operator=(OR | AND) right=expression #Logical

	| INT               #Integer
    | REAL              #Real
    | STRING            #String
    | (TRUE | FALSE)    #Boolean
    | NULL              #Null
    | thisExpression    #ignoreThis
	;

typeDefinition: 
	TYPE validName (EXTENDS validType)? NL;

nativeDefinition: 
	functionSignature NL;

/* 
 * ---------------------------------------------------------------------------
 * Globals
 * ---------------------------------------------------------------------------
 */	
globalVariableStatement: 
	(PRIVATE | PUBLIC)? CONSTANT? variableDeclaration NL;

globalStatements: 
	(NL | globalVariableStatement)*;

globalDefinition:
	GLOBALS NL
		globalStatements
	(ENDGLOBALS | END) NL
	;


/* 
 * ---------------------------------------------------------------------------
 * Libraries
 * ---------------------------------------------------------------------------
 */

libraryRequirements: 
	validName (COMMA validName)*;

libraryStatement
    : NL
	| globalDefinition
	| interfaceDefinition
	| structDefinition
	| functionDefinition
	;

libraryDefinition:
	LIBRARY name=validName (INITIALIZER libInit=validName)? (REQUIRES libraryRequirements)? NL
		libraryStatement*
	(ENDLIBRARY | END) NL
	;

 
 /* 
 * ---------------------------------------------------------------------------
 * Interfaces
 * ---------------------------------------------------------------------------
 */
interfaceDefinition:
	(PRIVATE | PUBLIC)? INTERFACE validName NL
		(NL | functionSignature)*
	(ENDINTERFACE | END) NL
	;


/* 
 * ---------------------------------------------------------------------------
 * Structs
 * ---------------------------------------------------------------------------
 */
propertyStatement:
	(PRIVATE | PUBLIC | PROTECTED)? STATIC? variableDeclaration NL;

structStatement
    : propertyStatement
	| functionDefinition
	| functionSignature
	;

implementableExtendable: validName | chainExpression;
implementsList: implementableExtendable (COMMA implementableExtendable)*;

structDefinition:
	(PRIVATE | PUBLIC)? ABSTRACT? STRUCT name=validName genericExpression? (EXTENDS extendsFrom=implementableExtendable)? (IMPLEMENTS implementsList)? NL
		(NL | structStatement)*
	(ENDSTRUCT | END) NL
	;


/* 
 * ---------------------------------------------------------------------------
 * Functions
 * ---------------------------------------------------------------------------
 */
returnType: validType | NOTHING;

parameter: variableDeclaration;

parameters
    : (parameter (COMMA parameter)*)
	| NOTHING
	;

arguments: allExpression (COMMA allExpression)*;

functionSignature: 
	(PRIVATE | PUBLIC | PROTECTED)? ABSTRACT? CONSTANT? STATIC? (NATIVE | FUNCTION | METHOD) (name=validName)? (TAKES parameters)? (RETURNS returnType)?;

functionDefinitionExpression:
	functionSignature NL
		statement*
	(ENDFUNCTION | ENDMETHOD | END);

functionDefinition: functionDefinitionExpression NL;


statement
    : localVariableStatement
	| assignmentStatement
	| functionStatement
	| loopStatement
	| whileLoopStatement
	| breakStatement
	| exitWhenStatement
	| continueStatement
	| ifStatement
	| returnStatement
	| NL
	;

localVariableStatement: LOCAL variableDeclaration NL;

assignmentStatement: 
	SET (variableExpression | chainExpression) operator=(PLUS | MINUS | TIMES | DIV)? EQ value=allExpression NL;

functionStatement: CALL (functionExpression | chainExpression) NL;

breakStatement: BREAK NL;

exitWhenStatement: EXITWHEN expression NL;

continueStatement: CONTINUE NL;

loopStatement:
	LOOP NL
		statement*
	(ENDLOOP | END) NL
	;

whileLoopStatement:
	WHILE expression NL
		statement*
	(ENDWHILE | END) NL
	;

ifStatement: 
	IF expression THEN NL 
		statement*
		elseIfStatement*
		elseStatement?
	(ENDIF | END) NL
	;

elseIfStatement: 
	ELSEIF expression THEN NL statement*;

elseStatement: 
	ELSE NL statement*;

returnStatement:
	RETURN (expression)? NL;


IMPORT: 'import';
ABSTRACT: 'abstract';
BREAK: 'break';
WHILE: 'while';
ENDWHILE: 'endwhile';
END: 'end';
CAST: 'cast';
TO: 'to';
LIBRARY: 'library';
ENDLIBRARY: 'endlibrary';
INITIALIZER: 'initializer';
REQUIRES: 'requires';
THIS: 'this';
THISTYPE: 'thistype';
SUPER: 'super';
DOT: '.';
PROTECTED: 'protected';
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
CONTINUE: 'continue';
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
MOD: '%';
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