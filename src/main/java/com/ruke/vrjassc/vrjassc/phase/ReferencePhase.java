package com.ruke.vrjassc.vrjassc.phase;

import com.ruke.vrjassc.vrjassc.antlr4.vrjassBaseVisitor;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.*;
import com.ruke.vrjassc.vrjassc.exception.NoFunctionException;
import com.ruke.vrjassc.vrjassc.symbol.*;
import com.ruke.vrjassc.vrjassc.util.TokenSymbolBag;
import com.ruke.vrjassc.vrjassc.util.Validator;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;

import java.util.Stack;

public class ReferencePhase extends vrjassBaseVisitor<Symbol> {

	private TokenSymbolBag symbols;
	
	private Validator validator;
	
	private ScopeSymbol scope;

	private ClassSymbol _class;
	
	private Stack<ScopeSymbol> scopes;
	
	private Stack<ScopeSymbol> enclosingScopes;
	
	/**
	 * We cant visit function statements before defining all the symbol
	 * types (for example, if a function takes a parameter of type Foo
	 * but that function is used before its definition the parameter type
	 * is going to be null)
	 */
	private Stack<ParserRuleContext> functionDefinitions;
	private boolean definingSymbolTypes = true;

	public void setValidator(Validator validator) {
		this.validator = validator;
	}
	
	public ReferencePhase(TokenSymbolBag symbols, ScopeSymbol scope) {
		this.symbols = symbols;
		this.scope = scope;
		this.scopes = new Stack<ScopeSymbol>();
		this.enclosingScopes = new Stack<ScopeSymbol>();
		this.functionDefinitions = new Stack<ParserRuleContext>();
		
		this.scopes.push(this.scope);
		
		this.setValidator(new Validator());
		this.validator.scope = scope;
	}
	
	@Override
	public Symbol visitInit(InitContext ctx) {
		super.visitInit(ctx);
		this.definingSymbolTypes = false;
		
		for (ParserRuleContext definition : this.functionDefinitions) {
			this.visit(definition);
		}
		
		return null;
	}
	
	@Override
	public Symbol visitTypeDefinition(TypeDefinitionContext ctx) {
		return this.symbols.get(ctx);
	}
	
	@Override
	public Symbol visitLibraryDefinition(LibraryDefinitionContext ctx) {
		LibrarySymbol library = (LibrarySymbol) this.symbols.get(ctx);
		
		this.scopes.push(library);
		
		if (ctx.libInit != null) {
			String initName = ctx.libInit.getText();
			Token initToken = ctx.libInit.getStart();
			
			if (!this.validator.mustBeDefined(library, initName, initToken)) {
				throw this.validator.getException();
			}
			
			library.setInitializer(this.validator.getValidatedSymbol());
		}
		
		if (ctx.libraryRequirements() != null) {
			String reqName;
			Token reqToken;
			
			for (ValidNameContext requirement : ctx.libraryRequirements().validName()) {
				reqName = requirement.getText();
				reqToken = requirement.getStart();
				
				if (!this.validator.mustBeDefined(library, reqName, reqToken)) {
					throw this.validator.getException();
				}
				
				library.defineRequirement((LibrarySymbol) this.validator.getValidatedSymbol());
			}
		}
		
		super.visitLibraryDefinition(ctx);
		
		this.scopes.pop();
		
		return library;
	}
	
	@Override
	public Symbol visitInterfaceDefinition(InterfaceDefinitionContext ctx) {
		InterfaceSymbol _interface = (InterfaceSymbol) this.symbols.get(ctx);
		
		this.scopes.push(_interface);
		super.visitInterfaceDefinition(ctx);
		this.scopes.pop();
		
		return _interface;
	}
	
	@Override
	public Symbol visitStructDefinition(StructDefinitionContext ctx) {		
		ClassSymbol _class = (ClassSymbol) this.symbols.get(ctx);
		Token token = ctx.name.getStart();

		this.scopes.push(_class);

		ClassSymbol prevClass = this._class;
		this._class = _class;
		
		if (ctx.extendsFrom != null) {
			String extendsName = ctx.extendsFrom.getText();
			Token extendsToken = ctx.extendsFrom.getStart();
			
			if (!this.validator.mustBeDefined(_class, extendsName, extendsToken)) {
				throw this.validator.getException();
			}
			
			if (!this.validator.mustBeExtendableValid(this.validator.getValidatedSymbol(), extendsToken)) {
				throw this.validator.getException();
			}

			ClassSymbol parent = (ClassSymbol) this.validator.getValidatedSymbol();

			if (!this.validator.mustImplementAllMethods(parent, _class, token)) {
				throw this.validator.getException();
			}
			
			_class.extendsFrom(parent);
		}

		super.visitStructDefinition(ctx);
		
		if (ctx.implementsList() != null) {
			String interfaceName;
			Token interfaceToken;
			
			for (ValidNameContext implement : ctx.implementsList().validName()) {
				interfaceName = implement.getText();
				interfaceToken = implement.getStart();
				
				if (!this.validator.mustBeDefined(_class, interfaceName, interfaceToken)) {
					throw this.validator.getException();
				}

				if (!this.validator.mustBeImplementableTypeValid(this.validator.getValidatedSymbol(), interfaceToken)) {
					throw this.validator.getException();
				}

				InterfaceSymbol _interface = (InterfaceSymbol) this.validator.getValidatedSymbol();
				
				if (!this.validator.mustImplementAllMethods(_interface, _class, token)) {
					throw this.validator.getException();
				}
				
				_class.define(_interface);
			}
		}

		Symbol initializer = _class.getInitializer();
		if (initializer != null && !this.validator.mustBeValidInitializer(initializer, initializer.getToken())) {
			throw this.validator.getException();
		}
		
		this.scopes.pop();
		this._class = prevClass;
		
		return _class;
	}
	
	@Override
	public Symbol visitReturnType(ReturnTypeContext ctx) {
		Symbol type = null;
		
		if (ctx.expression() != null) {
			type = this.visit(ctx.expression());
			Token token = ctx.expression().getStart();
			
			if (!this.validator.mustBeValidType(type, token)) {
				throw this.validator.getException();
			}
		}
		
		return type;
	}
	
	@Override
	public Symbol visitFunctionSignature(FunctionSignatureContext ctx) {
		FunctionSymbol function = (FunctionSymbol) this.symbols.get(ctx);

		this.scopes.push(function);
		this.enclosingScopes.push(function);
		
		if (ctx.returnType() != null) {
			function.setType((Type) this.visit(ctx.returnType()));
		}
		
		if (ctx.parameters() != null) {
			this.visit(ctx.parameters());
		}
		
		this.scopes.pop();
		this.enclosingScopes.pop();
		
		return function;
	}
	
	@Override
	public Symbol visitAnonymousExpression(AnonymousExpressionContext ctx) {
		this.visit(ctx.functionDefinitionExpression());
		return this.scopes.get(0).resolve("code");
	}
	
	@Override
	public Symbol visitFunctionDefinitionExpression(FunctionDefinitionExpressionContext ctx) {		
		FunctionSymbol function = (FunctionSymbol) this.visit(ctx.functionSignature());

		if (!this.validator.mustReturn(function, ctx.statement())) {
			throw this.validator.getException();
		}
		
		this.scopes.push(function);
		this.enclosingScopes.push(function);

		ClassSymbol prevClass = this._class;

		if (function.getParentScope() instanceof ClassSymbol) {
			this._class = (ClassSymbol) function.getParentScope();
		}
		
		if (this.definingSymbolTypes) {
			this.functionDefinitions.push(ctx);
		} else {
			super.visitFunctionDefinitionExpression(ctx);
		}
		
		this.scopes.pop();
		this.enclosingScopes.pop();

		this._class = prevClass;
		
		return function;
	}
	
	@Override
	public Symbol visitFunctionDefinition(FunctionDefinitionContext ctx) {
		return this.visit(ctx.functionDefinitionExpression());
	}
	
	@Override
	public Symbol visitValidType(ValidTypeContext ctx) {
		Symbol type = this.visit(ctx.expression());
		
		if (!this.validator.mustBeValidType(type, ctx.getStart())) {
			throw this.validator.getException();
		}
		
		return type;
	}
	
	@Override
	public Symbol visitVariableDeclaration(VariableDeclarationContext ctx) {
		Scope scope = this.scopes.peek();
		
		Symbol variable = this.symbols.get(ctx);
		Symbol type = this.visit(ctx.validType());
		Token typeToken = ctx.validType().getStart();
		
		if (!this.validator.mustHaveAccess(scope, type, typeToken)) {
			throw this.validator.getException();
		}
		
		variable.setType((Type) type);
		
		if (ctx.value != null) {
			Symbol value = this.visit(ctx.value);
			
			if (!this.validator.mustBeTypeCompatible(variable, value, typeToken)) {
				throw this.validator.getException();
			}
		}
		
		return variable;
	}
	
	@Override
	public Symbol visitDiv(DivContext ctx) {
		Symbol left = this.visit(ctx.left);
		Symbol right = this.visit(ctx.right);
		
		if (!this.validator.mustBeValidMathOperation(left, right, ctx.getStart())) {
			throw this.validator.getException();
		}
		
		return this.scope.resolve("real");
	}
	
	@Override
	public Symbol visitMult(MultContext ctx) {
		Symbol left = this.visit(ctx.left);
		Symbol right = this.visit(ctx.right);
		
		if (!this.validator.mustBeValidMathOperation(left, right, ctx.getStart())) {
			throw this.validator.getException();
		}
		
		return this.scope.resolve("real");
	}
	
	@Override
	public Symbol visitPlus(PlusContext ctx) {
		Symbol left = this.visit(ctx.left);
		Symbol right = this.visit(ctx.right);
		
		if (left.getType().getName().equals("string")) {
			if (!this.validator.mustBeValidStringConcatenation(left, right, ctx.getStart())) {
				throw this.validator.getException();
			}
		} else {
			if (!this.validator.mustBeValidMathOperation(left, right, ctx.getStart())) {
				throw this.validator.getException();
			}
		}
		
		return left;
	}
	
	@Override
	public Symbol visitMinus(MinusContext ctx) {
		Symbol left = this.visit(ctx.left);
		Symbol right = this.visit(ctx.right);
		
		if (!this.validator.mustBeValidMathOperation(left, right, ctx.getStart())) {
			throw this.validator.getException();
		}
		
		return this.scope.resolve("real");
	}
	
	@Override
	public Symbol visitComparison(ComparisonContext ctx) {
		super.visitComparison(ctx);
		return this.scopes.get(0).resolve("boolean");
	}
	
	@Override
	public Symbol visitLogical(LogicalContext ctx) {
		super.visitLogical(ctx);
		return this.scopes.get(0).resolve("boolean");
	}
	
	@Override
	public Symbol visitInteger(IntegerContext ctx) {
		return this.scopes.get(0).resolve("integer");
	}
	
	@Override
	public Symbol visitReal(RealContext ctx) {
		return this.scopes.get(0).resolve("real");
	}
	
	@Override
	public Symbol visitString(StringContext ctx) {
		return this.scopes.get(0).resolve("string");
	}
	
	@Override
	public Symbol visitNegative(NegativeContext ctx) {
		return this.visit(ctx.expression());
	}
	
	@Override
	public Symbol visitNot(NotContext ctx) {
		this.visit(ctx.expression());
		return this.scopes.get(0).resolve("boolean");
	}
	
	@Override
	public Symbol visitBoolean(BooleanContext ctx) {
		return this.scopes.get(0).resolve("boolean");
	}
	
	@Override
	public Symbol visitNull(NullContext ctx) {
		return this.scopes.get(0).resolve("null");
	}
	
	@Override
	public Symbol visitCode(CodeContext ctx) {
		Symbol code = null;

		if (ctx.chainExpression() != null) {
			code = this.visit(ctx.chainExpression());
		} else {
			code = this.scopes.peek().resolve(ctx.validName().getText());
			this.symbols.put(ctx.validName(), code);
		}

		if (!this.validator.mustBeValidCode(code, ctx.getStart())) {
			throw this.validator.getException();
		}

		return this.scopes.get(0).resolve("code");
	}

	@Override
	public Symbol visitSuperExpression(SuperExpressionContext ctx) {
		if (!this.validator.mustBeAbleToUseSuper(this._class, ctx.getStart())) {
			throw this.validator.getException();
		}

		return this._class.getSuper();
	}

	@Override
	public Symbol visitThisExpression(ThisExpressionContext ctx) {
		return this.scopes.peek().resolve("this");
	}
	
	@Override
	public Symbol visitFunctionExpression(FunctionExpressionContext ctx) {
		Scope scope = this.scopes.peek();
		
		String name = ctx.validName().getText();
		Token token = ctx.validName().getStart();
		
		if (!this.validator.mustBeDefined(scope, name, token)) {
			if (name.startsWith("InitTrig_")) {
				scope.getEnclosingScope().define(new InitTrigSymbol(name, null));
				this.validator.mustBeDefined(scope, name, token);
			} else {
				throw this.validator.getException();
			}
		}
		
		if (this.validator.getValidatedSymbol() instanceof FunctionSymbol == false) {
			throw new NoFunctionException(token, this.validator.getValidatedSymbol());
		}
		
		FunctionSymbol function = (FunctionSymbol) this.validator.getValidatedSymbol();
		Stack<Symbol> arguments = new Stack<Symbol>();
		
		if (ctx.arguments() != null) {
			this.scopes.push(this.enclosingScopes.peek());
			
			for (ExpressionContext expr : ctx.arguments().expression()) {
				arguments.push(this.visit(expr));
			}
			
			this.scopes.pop();
		}
		
		if (!this.validator.mustMatchArguments(function, arguments, token)) {
			throw this.validator.getException();
		}
		
		this.symbols.put(ctx, function);
		
		return function;
	}
	
	@Override
	public Symbol visitVariableExpression(VariableExpressionContext ctx) {
		Scope scope = this.scopes.peek();
		
		String name = ctx.validName().getText();
		Token token = ctx.validName().getStart();
		
		if (!this.validator.mustBeDefined(scope, name, token)) {
			throw this.validator.getException();
		}
		
		Symbol variable = this.validator.getValidatedSymbol();
		
		if (!this.validator.mustBeDeclaredBeforeUsed(variable, ctx.getStart())) {
			throw this.validator.getException();
		}
		
		if (ctx.index != null) {
			// chain expressions can modifiy the current scope so restore it
			// to avoid failing
			// example: this.foo[this.bar]
			// 'this' will update the current scope to whatever class it points to
			// then it will look for foo (which it will find it), but then it will
			// try to find 'this' and it will fail (because the current scope is
			// the class
			this.scopes.push(this.enclosingScopes.peek());
			this.visit(ctx.index);
			this.scopes.pop();
		}
		
		this.symbols.put(ctx, variable);
		
		return variable;
	}
	
	@Override
	public Symbol visitCast(CastContext ctx) {		
		Symbol original = this.visit(ctx.original);
		Symbol cast = null;

		if (ctx.chainExpression() != null) {
			cast = this.visit(ctx.chainExpression());
		} else {
			cast = this.scopes.peek().resolve(ctx.validName().getText());
			this.symbols.put(ctx.validName(), cast);
		}

		Symbol result = new CastSymbol(original, cast, ctx.getStart());
		
		this.symbols.put(ctx, result);
		
		return result;
	}
	
	@Override
	public Symbol visitParenthesis(ParenthesisContext ctx) {
		Symbol symbol = this.visit(ctx.expression());
		this.symbols.put(ctx, symbol);
		return symbol;
	}

	@Override
	public Symbol visitMemberExpression(MemberExpressionContext ctx) {
		if (ctx.variableExpression() != null) {
			return this.visit(ctx.variableExpression());
		}

		return this.visit(ctx.functionExpression());
	}

	@Override
	public Symbol visitChainExpression(ChainExpressionContext ctx) {
		ScopeSymbol scope = this.scopes.peek();
		ScopeSymbol parentScope = null;

		Symbol parent = this.visit(ctx.getChild(0));
		Symbol member = null;

		for (MemberExpressionContext expr : ctx.memberExpression()) {
			if (parent.getType() instanceof ScopeSymbol) {
				parentScope = (ScopeSymbol) parent.getType();
			} else if (parent instanceof ScopeSymbol) {
				parentScope = (ScopeSymbol) parent;
			}

			this.scopes.push(parentScope);
			member = this.visit(expr);
			this.scopes.pop();

			if (!this.validator.mustHaveAccess(scope, member, expr.getStart())) {
				throw this.validator.getException();
			}

			if (!this.validator.mustBeValidMember(parent, member, expr.getStart())) {
				throw this.validator.getException();
			}

			parent = member;
		}

		return member;
	}
	
	@Override
	public Symbol visitAssignmentStatement(AssignmentStatementContext ctx) {
		Symbol variable = this.visit(ctx.name);
		Symbol value = this.visit(ctx.value);
		
		if (!this.validator.mustBeTypeCompatible(variable, value, ctx.getStart())) {
			throw this.validator.getException();
		}
		
		return variable;
	}
	
	@Override
	public Symbol visitFunctionStatement(FunctionStatementContext ctx) {
		return this.visit(ctx.expression());
	}

	@Override
	public Symbol visitExitWhenStatement(ExitWhenStatementContext ctx) {
		if (!this.validator.mustBeInsideOfLoop(ctx, ctx.getStart())) {
			throw this.validator.getException();
		}
		
		return this.visit(ctx.expression());
	}
	
	@Override
	public Symbol visitBreakStatement(BreakStatementContext ctx) {
		if (!this.validator.mustBeInsideOfLoop(ctx, ctx.getStart())) {
			throw this.validator.getException();
		}
		
		return null;
	}
	
	@Override
	public Symbol visitReturnStatement(ReturnStatementContext ctx) {
		if (ctx.expression() == null) {
			return null;
		}
		
		Scope scope = this.scopes.peek();
		
		Symbol expr = this.visit(ctx.expression());
		Token token = ctx.expression().getStart();
		
		if (!this.validator.mustBeTypeCompatible((Symbol) scope, expr, token)) {
			throw this.validator.getException();
		}
		
		return expr;
	}
}
