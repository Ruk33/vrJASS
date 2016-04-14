// Generated from C:/Users/Ruke/IntelliJ/vrJASS\vrjass.g4 by ANTLR 4.5.1
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link vrjassParser}.
 */
public interface vrjassListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link vrjassParser#init}.
	 * @param ctx the parse tree
	 */
	void enterInit(vrjassParser.InitContext ctx);
	/**
	 * Exit a parse tree produced by {@link vrjassParser#init}.
	 * @param ctx the parse tree
	 */
	void exitInit(vrjassParser.InitContext ctx);
	/**
	 * Enter a parse tree produced by {@link vrjassParser#topDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterTopDeclaration(vrjassParser.TopDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link vrjassParser#topDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitTopDeclaration(vrjassParser.TopDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link vrjassParser#validType}.
	 * @param ctx the parse tree
	 */
	void enterValidType(vrjassParser.ValidTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link vrjassParser#validType}.
	 * @param ctx the parse tree
	 */
	void exitValidType(vrjassParser.ValidTypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link vrjassParser#validName}.
	 * @param ctx the parse tree
	 */
	void enterValidName(vrjassParser.ValidNameContext ctx);
	/**
	 * Exit a parse tree produced by {@link vrjassParser#validName}.
	 * @param ctx the parse tree
	 */
	void exitValidName(vrjassParser.ValidNameContext ctx);
	/**
	 * Enter a parse tree produced by {@link vrjassParser#variableDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterVariableDeclaration(vrjassParser.VariableDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link vrjassParser#variableDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitVariableDeclaration(vrjassParser.VariableDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by the {@code Cast}
	 * labeled alternative in {@link vrjassParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterCast(vrjassParser.CastContext ctx);
	/**
	 * Exit a parse tree produced by the {@code Cast}
	 * labeled alternative in {@link vrjassParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitCast(vrjassParser.CastContext ctx);
	/**
	 * Enter a parse tree produced by the {@code Null}
	 * labeled alternative in {@link vrjassParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterNull(vrjassParser.NullContext ctx);
	/**
	 * Exit a parse tree produced by the {@code Null}
	 * labeled alternative in {@link vrjassParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitNull(vrjassParser.NullContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ChainExpression}
	 * labeled alternative in {@link vrjassParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterChainExpression(vrjassParser.ChainExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ChainExpression}
	 * labeled alternative in {@link vrjassParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitChainExpression(vrjassParser.ChainExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code Logical}
	 * labeled alternative in {@link vrjassParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterLogical(vrjassParser.LogicalContext ctx);
	/**
	 * Exit a parse tree produced by the {@code Logical}
	 * labeled alternative in {@link vrjassParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitLogical(vrjassParser.LogicalContext ctx);
	/**
	 * Enter a parse tree produced by the {@code VariableExpression}
	 * labeled alternative in {@link vrjassParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterVariableExpression(vrjassParser.VariableExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code VariableExpression}
	 * labeled alternative in {@link vrjassParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitVariableExpression(vrjassParser.VariableExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code String}
	 * labeled alternative in {@link vrjassParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterString(vrjassParser.StringContext ctx);
	/**
	 * Exit a parse tree produced by the {@code String}
	 * labeled alternative in {@link vrjassParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitString(vrjassParser.StringContext ctx);
	/**
	 * Enter a parse tree produced by the {@code Code}
	 * labeled alternative in {@link vrjassParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterCode(vrjassParser.CodeContext ctx);
	/**
	 * Exit a parse tree produced by the {@code Code}
	 * labeled alternative in {@link vrjassParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitCode(vrjassParser.CodeContext ctx);
	/**
	 * Enter a parse tree produced by the {@code Integer}
	 * labeled alternative in {@link vrjassParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterInteger(vrjassParser.IntegerContext ctx);
	/**
	 * Exit a parse tree produced by the {@code Integer}
	 * labeled alternative in {@link vrjassParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitInteger(vrjassParser.IntegerContext ctx);
	/**
	 * Enter a parse tree produced by the {@code Div}
	 * labeled alternative in {@link vrjassParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterDiv(vrjassParser.DivContext ctx);
	/**
	 * Exit a parse tree produced by the {@code Div}
	 * labeled alternative in {@link vrjassParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitDiv(vrjassParser.DivContext ctx);
	/**
	 * Enter a parse tree produced by the {@code AnonymousExpression}
	 * labeled alternative in {@link vrjassParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterAnonymousExpression(vrjassParser.AnonymousExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code AnonymousExpression}
	 * labeled alternative in {@link vrjassParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitAnonymousExpression(vrjassParser.AnonymousExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code Not}
	 * labeled alternative in {@link vrjassParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterNot(vrjassParser.NotContext ctx);
	/**
	 * Exit a parse tree produced by the {@code Not}
	 * labeled alternative in {@link vrjassParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitNot(vrjassParser.NotContext ctx);
	/**
	 * Enter a parse tree produced by the {@code Parenthesis}
	 * labeled alternative in {@link vrjassParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterParenthesis(vrjassParser.ParenthesisContext ctx);
	/**
	 * Exit a parse tree produced by the {@code Parenthesis}
	 * labeled alternative in {@link vrjassParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitParenthesis(vrjassParser.ParenthesisContext ctx);
	/**
	 * Enter a parse tree produced by the {@code FunctionExpression}
	 * labeled alternative in {@link vrjassParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterFunctionExpression(vrjassParser.FunctionExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code FunctionExpression}
	 * labeled alternative in {@link vrjassParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitFunctionExpression(vrjassParser.FunctionExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code Negative}
	 * labeled alternative in {@link vrjassParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterNegative(vrjassParser.NegativeContext ctx);
	/**
	 * Exit a parse tree produced by the {@code Negative}
	 * labeled alternative in {@link vrjassParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitNegative(vrjassParser.NegativeContext ctx);
	/**
	 * Enter a parse tree produced by the {@code Mult}
	 * labeled alternative in {@link vrjassParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterMult(vrjassParser.MultContext ctx);
	/**
	 * Exit a parse tree produced by the {@code Mult}
	 * labeled alternative in {@link vrjassParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitMult(vrjassParser.MultContext ctx);
	/**
	 * Enter a parse tree produced by the {@code Comparison}
	 * labeled alternative in {@link vrjassParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterComparison(vrjassParser.ComparisonContext ctx);
	/**
	 * Exit a parse tree produced by the {@code Comparison}
	 * labeled alternative in {@link vrjassParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitComparison(vrjassParser.ComparisonContext ctx);
	/**
	 * Enter a parse tree produced by the {@code Real}
	 * labeled alternative in {@link vrjassParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterReal(vrjassParser.RealContext ctx);
	/**
	 * Exit a parse tree produced by the {@code Real}
	 * labeled alternative in {@link vrjassParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitReal(vrjassParser.RealContext ctx);
	/**
	 * Enter a parse tree produced by the {@code This}
	 * labeled alternative in {@link vrjassParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterThis(vrjassParser.ThisContext ctx);
	/**
	 * Exit a parse tree produced by the {@code This}
	 * labeled alternative in {@link vrjassParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitThis(vrjassParser.ThisContext ctx);
	/**
	 * Enter a parse tree produced by the {@code Boolean}
	 * labeled alternative in {@link vrjassParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterBoolean(vrjassParser.BooleanContext ctx);
	/**
	 * Exit a parse tree produced by the {@code Boolean}
	 * labeled alternative in {@link vrjassParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitBoolean(vrjassParser.BooleanContext ctx);
	/**
	 * Enter a parse tree produced by the {@code Plus}
	 * labeled alternative in {@link vrjassParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterPlus(vrjassParser.PlusContext ctx);
	/**
	 * Exit a parse tree produced by the {@code Plus}
	 * labeled alternative in {@link vrjassParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitPlus(vrjassParser.PlusContext ctx);
	/**
	 * Enter a parse tree produced by the {@code Minus}
	 * labeled alternative in {@link vrjassParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterMinus(vrjassParser.MinusContext ctx);
	/**
	 * Exit a parse tree produced by the {@code Minus}
	 * labeled alternative in {@link vrjassParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitMinus(vrjassParser.MinusContext ctx);
	/**
	 * Enter a parse tree produced by {@link vrjassParser#typeDefinition}.
	 * @param ctx the parse tree
	 */
	void enterTypeDefinition(vrjassParser.TypeDefinitionContext ctx);
	/**
	 * Exit a parse tree produced by {@link vrjassParser#typeDefinition}.
	 * @param ctx the parse tree
	 */
	void exitTypeDefinition(vrjassParser.TypeDefinitionContext ctx);
	/**
	 * Enter a parse tree produced by {@link vrjassParser#nativeDefinition}.
	 * @param ctx the parse tree
	 */
	void enterNativeDefinition(vrjassParser.NativeDefinitionContext ctx);
	/**
	 * Exit a parse tree produced by {@link vrjassParser#nativeDefinition}.
	 * @param ctx the parse tree
	 */
	void exitNativeDefinition(vrjassParser.NativeDefinitionContext ctx);
	/**
	 * Enter a parse tree produced by {@link vrjassParser#globalVariableStatement}.
	 * @param ctx the parse tree
	 */
	void enterGlobalVariableStatement(vrjassParser.GlobalVariableStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link vrjassParser#globalVariableStatement}.
	 * @param ctx the parse tree
	 */
	void exitGlobalVariableStatement(vrjassParser.GlobalVariableStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link vrjassParser#globalStatements}.
	 * @param ctx the parse tree
	 */
	void enterGlobalStatements(vrjassParser.GlobalStatementsContext ctx);
	/**
	 * Exit a parse tree produced by {@link vrjassParser#globalStatements}.
	 * @param ctx the parse tree
	 */
	void exitGlobalStatements(vrjassParser.GlobalStatementsContext ctx);
	/**
	 * Enter a parse tree produced by {@link vrjassParser#globalDefinition}.
	 * @param ctx the parse tree
	 */
	void enterGlobalDefinition(vrjassParser.GlobalDefinitionContext ctx);
	/**
	 * Exit a parse tree produced by {@link vrjassParser#globalDefinition}.
	 * @param ctx the parse tree
	 */
	void exitGlobalDefinition(vrjassParser.GlobalDefinitionContext ctx);
	/**
	 * Enter a parse tree produced by {@link vrjassParser#libraryRequirements}.
	 * @param ctx the parse tree
	 */
	void enterLibraryRequirements(vrjassParser.LibraryRequirementsContext ctx);
	/**
	 * Exit a parse tree produced by {@link vrjassParser#libraryRequirements}.
	 * @param ctx the parse tree
	 */
	void exitLibraryRequirements(vrjassParser.LibraryRequirementsContext ctx);
	/**
	 * Enter a parse tree produced by {@link vrjassParser#libraryStatement}.
	 * @param ctx the parse tree
	 */
	void enterLibraryStatement(vrjassParser.LibraryStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link vrjassParser#libraryStatement}.
	 * @param ctx the parse tree
	 */
	void exitLibraryStatement(vrjassParser.LibraryStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link vrjassParser#libraryDefinition}.
	 * @param ctx the parse tree
	 */
	void enterLibraryDefinition(vrjassParser.LibraryDefinitionContext ctx);
	/**
	 * Exit a parse tree produced by {@link vrjassParser#libraryDefinition}.
	 * @param ctx the parse tree
	 */
	void exitLibraryDefinition(vrjassParser.LibraryDefinitionContext ctx);
	/**
	 * Enter a parse tree produced by {@link vrjassParser#interfaceDefinition}.
	 * @param ctx the parse tree
	 */
	void enterInterfaceDefinition(vrjassParser.InterfaceDefinitionContext ctx);
	/**
	 * Exit a parse tree produced by {@link vrjassParser#interfaceDefinition}.
	 * @param ctx the parse tree
	 */
	void exitInterfaceDefinition(vrjassParser.InterfaceDefinitionContext ctx);
	/**
	 * Enter a parse tree produced by {@link vrjassParser#propertyStatement}.
	 * @param ctx the parse tree
	 */
	void enterPropertyStatement(vrjassParser.PropertyStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link vrjassParser#propertyStatement}.
	 * @param ctx the parse tree
	 */
	void exitPropertyStatement(vrjassParser.PropertyStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link vrjassParser#structStatement}.
	 * @param ctx the parse tree
	 */
	void enterStructStatement(vrjassParser.StructStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link vrjassParser#structStatement}.
	 * @param ctx the parse tree
	 */
	void exitStructStatement(vrjassParser.StructStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link vrjassParser#implementsList}.
	 * @param ctx the parse tree
	 */
	void enterImplementsList(vrjassParser.ImplementsListContext ctx);
	/**
	 * Exit a parse tree produced by {@link vrjassParser#implementsList}.
	 * @param ctx the parse tree
	 */
	void exitImplementsList(vrjassParser.ImplementsListContext ctx);
	/**
	 * Enter a parse tree produced by {@link vrjassParser#structDefinition}.
	 * @param ctx the parse tree
	 */
	void enterStructDefinition(vrjassParser.StructDefinitionContext ctx);
	/**
	 * Exit a parse tree produced by {@link vrjassParser#structDefinition}.
	 * @param ctx the parse tree
	 */
	void exitStructDefinition(vrjassParser.StructDefinitionContext ctx);
	/**
	 * Enter a parse tree produced by {@link vrjassParser#returnType}.
	 * @param ctx the parse tree
	 */
	void enterReturnType(vrjassParser.ReturnTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link vrjassParser#returnType}.
	 * @param ctx the parse tree
	 */
	void exitReturnType(vrjassParser.ReturnTypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link vrjassParser#parameter}.
	 * @param ctx the parse tree
	 */
	void enterParameter(vrjassParser.ParameterContext ctx);
	/**
	 * Exit a parse tree produced by {@link vrjassParser#parameter}.
	 * @param ctx the parse tree
	 */
	void exitParameter(vrjassParser.ParameterContext ctx);
	/**
	 * Enter a parse tree produced by {@link vrjassParser#parameters}.
	 * @param ctx the parse tree
	 */
	void enterParameters(vrjassParser.ParametersContext ctx);
	/**
	 * Exit a parse tree produced by {@link vrjassParser#parameters}.
	 * @param ctx the parse tree
	 */
	void exitParameters(vrjassParser.ParametersContext ctx);
	/**
	 * Enter a parse tree produced by {@link vrjassParser#arguments}.
	 * @param ctx the parse tree
	 */
	void enterArguments(vrjassParser.ArgumentsContext ctx);
	/**
	 * Exit a parse tree produced by {@link vrjassParser#arguments}.
	 * @param ctx the parse tree
	 */
	void exitArguments(vrjassParser.ArgumentsContext ctx);
	/**
	 * Enter a parse tree produced by {@link vrjassParser#functionSignature}.
	 * @param ctx the parse tree
	 */
	void enterFunctionSignature(vrjassParser.FunctionSignatureContext ctx);
	/**
	 * Exit a parse tree produced by {@link vrjassParser#functionSignature}.
	 * @param ctx the parse tree
	 */
	void exitFunctionSignature(vrjassParser.FunctionSignatureContext ctx);
	/**
	 * Enter a parse tree produced by {@link vrjassParser#functionDefinitionExpression}.
	 * @param ctx the parse tree
	 */
	void enterFunctionDefinitionExpression(vrjassParser.FunctionDefinitionExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link vrjassParser#functionDefinitionExpression}.
	 * @param ctx the parse tree
	 */
	void exitFunctionDefinitionExpression(vrjassParser.FunctionDefinitionExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link vrjassParser#functionDefinition}.
	 * @param ctx the parse tree
	 */
	void enterFunctionDefinition(vrjassParser.FunctionDefinitionContext ctx);
	/**
	 * Exit a parse tree produced by {@link vrjassParser#functionDefinition}.
	 * @param ctx the parse tree
	 */
	void exitFunctionDefinition(vrjassParser.FunctionDefinitionContext ctx);
	/**
	 * Enter a parse tree produced by {@link vrjassParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterStatement(vrjassParser.StatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link vrjassParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitStatement(vrjassParser.StatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link vrjassParser#localVariableStatement}.
	 * @param ctx the parse tree
	 */
	void enterLocalVariableStatement(vrjassParser.LocalVariableStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link vrjassParser#localVariableStatement}.
	 * @param ctx the parse tree
	 */
	void exitLocalVariableStatement(vrjassParser.LocalVariableStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link vrjassParser#assignmentStatement}.
	 * @param ctx the parse tree
	 */
	void enterAssignmentStatement(vrjassParser.AssignmentStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link vrjassParser#assignmentStatement}.
	 * @param ctx the parse tree
	 */
	void exitAssignmentStatement(vrjassParser.AssignmentStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link vrjassParser#functionStatement}.
	 * @param ctx the parse tree
	 */
	void enterFunctionStatement(vrjassParser.FunctionStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link vrjassParser#functionStatement}.
	 * @param ctx the parse tree
	 */
	void exitFunctionStatement(vrjassParser.FunctionStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link vrjassParser#breakStatement}.
	 * @param ctx the parse tree
	 */
	void enterBreakStatement(vrjassParser.BreakStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link vrjassParser#breakStatement}.
	 * @param ctx the parse tree
	 */
	void exitBreakStatement(vrjassParser.BreakStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link vrjassParser#exitWhenStatement}.
	 * @param ctx the parse tree
	 */
	void enterExitWhenStatement(vrjassParser.ExitWhenStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link vrjassParser#exitWhenStatement}.
	 * @param ctx the parse tree
	 */
	void exitExitWhenStatement(vrjassParser.ExitWhenStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link vrjassParser#loopStatement}.
	 * @param ctx the parse tree
	 */
	void enterLoopStatement(vrjassParser.LoopStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link vrjassParser#loopStatement}.
	 * @param ctx the parse tree
	 */
	void exitLoopStatement(vrjassParser.LoopStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link vrjassParser#whileLoopStatement}.
	 * @param ctx the parse tree
	 */
	void enterWhileLoopStatement(vrjassParser.WhileLoopStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link vrjassParser#whileLoopStatement}.
	 * @param ctx the parse tree
	 */
	void exitWhileLoopStatement(vrjassParser.WhileLoopStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link vrjassParser#ifStatement}.
	 * @param ctx the parse tree
	 */
	void enterIfStatement(vrjassParser.IfStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link vrjassParser#ifStatement}.
	 * @param ctx the parse tree
	 */
	void exitIfStatement(vrjassParser.IfStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link vrjassParser#elseIfStatement}.
	 * @param ctx the parse tree
	 */
	void enterElseIfStatement(vrjassParser.ElseIfStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link vrjassParser#elseIfStatement}.
	 * @param ctx the parse tree
	 */
	void exitElseIfStatement(vrjassParser.ElseIfStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link vrjassParser#elseStatement}.
	 * @param ctx the parse tree
	 */
	void enterElseStatement(vrjassParser.ElseStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link vrjassParser#elseStatement}.
	 * @param ctx the parse tree
	 */
	void exitElseStatement(vrjassParser.ElseStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link vrjassParser#returnStatement}.
	 * @param ctx the parse tree
	 */
	void enterReturnStatement(vrjassParser.ReturnStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link vrjassParser#returnStatement}.
	 * @param ctx the parse tree
	 */
	void exitReturnStatement(vrjassParser.ReturnStatementContext ctx);
}