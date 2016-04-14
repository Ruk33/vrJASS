// Generated from C:/Users/Ruke/IntelliJ/vrJASS\vrjass.g4 by ANTLR 4.5.1
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link vrjassParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface vrjassVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link vrjassParser#init}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInit(vrjassParser.InitContext ctx);
	/**
	 * Visit a parse tree produced by {@link vrjassParser#topDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTopDeclaration(vrjassParser.TopDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link vrjassParser#validType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitValidType(vrjassParser.ValidTypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link vrjassParser#validName}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitValidName(vrjassParser.ValidNameContext ctx);
	/**
	 * Visit a parse tree produced by {@link vrjassParser#variableDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVariableDeclaration(vrjassParser.VariableDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Cast}
	 * labeled alternative in {@link vrjassParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCast(vrjassParser.CastContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Null}
	 * labeled alternative in {@link vrjassParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNull(vrjassParser.NullContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ChainExpression}
	 * labeled alternative in {@link vrjassParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitChainExpression(vrjassParser.ChainExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Logical}
	 * labeled alternative in {@link vrjassParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLogical(vrjassParser.LogicalContext ctx);
	/**
	 * Visit a parse tree produced by the {@code VariableExpression}
	 * labeled alternative in {@link vrjassParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVariableExpression(vrjassParser.VariableExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code String}
	 * labeled alternative in {@link vrjassParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitString(vrjassParser.StringContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Code}
	 * labeled alternative in {@link vrjassParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCode(vrjassParser.CodeContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Integer}
	 * labeled alternative in {@link vrjassParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInteger(vrjassParser.IntegerContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Div}
	 * labeled alternative in {@link vrjassParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDiv(vrjassParser.DivContext ctx);
	/**
	 * Visit a parse tree produced by the {@code AnonymousExpression}
	 * labeled alternative in {@link vrjassParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAnonymousExpression(vrjassParser.AnonymousExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Not}
	 * labeled alternative in {@link vrjassParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNot(vrjassParser.NotContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Parenthesis}
	 * labeled alternative in {@link vrjassParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParenthesis(vrjassParser.ParenthesisContext ctx);
	/**
	 * Visit a parse tree produced by the {@code FunctionExpression}
	 * labeled alternative in {@link vrjassParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunctionExpression(vrjassParser.FunctionExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Negative}
	 * labeled alternative in {@link vrjassParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNegative(vrjassParser.NegativeContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Mult}
	 * labeled alternative in {@link vrjassParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMult(vrjassParser.MultContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Comparison}
	 * labeled alternative in {@link vrjassParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitComparison(vrjassParser.ComparisonContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Real}
	 * labeled alternative in {@link vrjassParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitReal(vrjassParser.RealContext ctx);
	/**
	 * Visit a parse tree produced by the {@code This}
	 * labeled alternative in {@link vrjassParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitThis(vrjassParser.ThisContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Boolean}
	 * labeled alternative in {@link vrjassParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBoolean(vrjassParser.BooleanContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Plus}
	 * labeled alternative in {@link vrjassParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPlus(vrjassParser.PlusContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Minus}
	 * labeled alternative in {@link vrjassParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMinus(vrjassParser.MinusContext ctx);
	/**
	 * Visit a parse tree produced by {@link vrjassParser#typeDefinition}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTypeDefinition(vrjassParser.TypeDefinitionContext ctx);
	/**
	 * Visit a parse tree produced by {@link vrjassParser#nativeDefinition}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNativeDefinition(vrjassParser.NativeDefinitionContext ctx);
	/**
	 * Visit a parse tree produced by {@link vrjassParser#globalVariableStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGlobalVariableStatement(vrjassParser.GlobalVariableStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link vrjassParser#globalStatements}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGlobalStatements(vrjassParser.GlobalStatementsContext ctx);
	/**
	 * Visit a parse tree produced by {@link vrjassParser#globalDefinition}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGlobalDefinition(vrjassParser.GlobalDefinitionContext ctx);
	/**
	 * Visit a parse tree produced by {@link vrjassParser#libraryRequirements}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLibraryRequirements(vrjassParser.LibraryRequirementsContext ctx);
	/**
	 * Visit a parse tree produced by {@link vrjassParser#libraryStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLibraryStatement(vrjassParser.LibraryStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link vrjassParser#libraryDefinition}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLibraryDefinition(vrjassParser.LibraryDefinitionContext ctx);
	/**
	 * Visit a parse tree produced by {@link vrjassParser#interfaceDefinition}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInterfaceDefinition(vrjassParser.InterfaceDefinitionContext ctx);
	/**
	 * Visit a parse tree produced by {@link vrjassParser#propertyStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPropertyStatement(vrjassParser.PropertyStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link vrjassParser#structStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStructStatement(vrjassParser.StructStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link vrjassParser#implementsList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitImplementsList(vrjassParser.ImplementsListContext ctx);
	/**
	 * Visit a parse tree produced by {@link vrjassParser#structDefinition}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStructDefinition(vrjassParser.StructDefinitionContext ctx);
	/**
	 * Visit a parse tree produced by {@link vrjassParser#returnType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitReturnType(vrjassParser.ReturnTypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link vrjassParser#parameter}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParameter(vrjassParser.ParameterContext ctx);
	/**
	 * Visit a parse tree produced by {@link vrjassParser#parameters}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParameters(vrjassParser.ParametersContext ctx);
	/**
	 * Visit a parse tree produced by {@link vrjassParser#arguments}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArguments(vrjassParser.ArgumentsContext ctx);
	/**
	 * Visit a parse tree produced by {@link vrjassParser#functionSignature}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunctionSignature(vrjassParser.FunctionSignatureContext ctx);
	/**
	 * Visit a parse tree produced by {@link vrjassParser#functionDefinitionExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunctionDefinitionExpression(vrjassParser.FunctionDefinitionExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link vrjassParser#functionDefinition}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunctionDefinition(vrjassParser.FunctionDefinitionContext ctx);
	/**
	 * Visit a parse tree produced by {@link vrjassParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStatement(vrjassParser.StatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link vrjassParser#localVariableStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLocalVariableStatement(vrjassParser.LocalVariableStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link vrjassParser#assignmentStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssignmentStatement(vrjassParser.AssignmentStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link vrjassParser#functionStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunctionStatement(vrjassParser.FunctionStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link vrjassParser#breakStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBreakStatement(vrjassParser.BreakStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link vrjassParser#exitWhenStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExitWhenStatement(vrjassParser.ExitWhenStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link vrjassParser#loopStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLoopStatement(vrjassParser.LoopStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link vrjassParser#whileLoopStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWhileLoopStatement(vrjassParser.WhileLoopStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link vrjassParser#ifStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIfStatement(vrjassParser.IfStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link vrjassParser#elseIfStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitElseIfStatement(vrjassParser.ElseIfStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link vrjassParser#elseStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitElseStatement(vrjassParser.ElseStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link vrjassParser#returnStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitReturnStatement(vrjassParser.ReturnStatementContext ctx);
}