package visitor;

import symbol.MethodSymbol;
import symbol.ParameterSymbol;
import symbol.PrimitiveType;
import symbol.PropertySymbol;
import symbol.Symbol;
import symbol.VariableSymbol;
import symbol.Visibility;
import antlr4.vrjassBaseVisitor;
import antlr4.vrjassParser.ClassDefinitionContext;
import antlr4.vrjassParser.ClassStatementsContext;
import antlr4.vrjassParser.FunctionDefinitionContext;
import antlr4.vrjassParser.GlobalVariableStatementContext;
import antlr4.vrjassParser.LibraryDefinitionContext;
import antlr4.vrjassParser.LibraryStatementsContext;
import antlr4.vrjassParser.LocalVariableStatementContext;
import antlr4.vrjassParser.MethodDefinitionContext;
import antlr4.vrjassParser.ParameterContext;
import antlr4.vrjassParser.PropertyStatementContext;

public class SymbolVisitor extends vrjassBaseVisitor<Void> {

	protected MainVisitor main;
	
	protected Symbol globalScope;
	protected Symbol scope;
	
	public SymbolVisitor(MainVisitor main) {
		this.main = main;
		
		this.globalScope = new Symbol(null, null, null, null, null);
		this.scope = this.globalScope;
	}
	
	@Override
	public Void visitGlobalVariableStatement(GlobalVariableStatementContext ctx) {
		String name = ctx.varName.getText();
		String type = ctx.variableType().getText();
		boolean isArray = ctx.array != null;
		Visibility visibility = this.main.getVisibility(ctx.visibility);
		
		new VariableSymbol(
			name, type, isArray, true, visibility, this.scope
		);
		
		return null;
	}
	
	@Override
	public Void visitLocalVariableStatement(LocalVariableStatementContext ctx) {
		String name = ctx.varName.getText();
		String type = ctx.variableType().getText();
		boolean isArray = ctx.array != null;
		
		new VariableSymbol(
			name, type, isArray, false, Visibility.PRIVATE, this.scope
		);
		
		return null;
	}
	
	@Override
	public Void visitParameter(ParameterContext ctx) {
		String name = ctx.ID().getText();
		String type = ctx.variableType().getText();
		
		new ParameterSymbol(name, type, this.scope);
		
		return null;
	}
	
	@Override
	public Void visitPropertyStatement(PropertyStatementContext ctx) {
		String name = ctx.propertyName.getText();
		String type = ctx.variableType().getText();
		Visibility visibility = this.main.getVisibility(ctx.visibility);
		
		new PropertySymbol(
			name, type, false, false, visibility, this.scope
		);
		
		return null;
	}
	
	@Override
	public Void visitMethodDefinition(MethodDefinitionContext ctx) {
		String name = ctx.methodName.getText();
		String type = ctx.returnType().getText();
		Visibility visibility = this.main.getVisibility(ctx.visibility);
		
		this.scope = new MethodSymbol(
			name, type, false, visibility, this.scope
		);
		
		this.visit(ctx.parameters());
		this.visit(ctx.statements());
		
		this.scope = this.scope.getParent();
		
		return null;
	}
	
	@Override
	public Void visitFunctionDefinition(FunctionDefinitionContext ctx) {
		String name = ctx.functionName.getText();
		String type = ctx.returnType().getText();
		Visibility visibility = this.main.getVisibility(ctx.visibility);
		
		this.scope = new Symbol(
			name, type, PrimitiveType.FUNCTION, visibility, this.scope
		);
		
		this.visit(ctx.parameters());
		this.visit(ctx.statements());
		
		this.scope = this.scope.getParent();
		
		return null;
	}
	
	@Override
	public Void visitClassDefinition(ClassDefinitionContext ctx) {
		String name = ctx.className.getText();
		
		this.scope = new Symbol(
			name, null, PrimitiveType.CLASS, Visibility.PUBLIC, this.scope
		);
		
		for (ClassStatementsContext statement : ctx.classStatements()) {
			this.visit(statement);
		}
		
		this.scope = this.scope.getParent();
		
		return null;
	}
	
	@Override
	public Void visitLibraryDefinition(LibraryDefinitionContext ctx) {
		String name = ctx.libraryName.getText();
		
		this.scope = new Symbol(
			name, null, PrimitiveType.LIBRARY, Visibility.PUBLIC, this.scope
		);
		
		for (LibraryStatementsContext library : ctx.libraryStatements()) {
			this.visit(library);
		}
		
		this.scope = this.scope.getParent();
		
		return null;
	}
	
}
