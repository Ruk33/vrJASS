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

	private int line = -1;
	private int col = -1;
	private ScopeSymbol catchedScope;
	private Symbol catchedSymbol;
	private Class catchSymbolType;
	private Type catchTypeCompatible;

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

	public void catchSymbolIn(int line, int col) {
		this.line = line;
		this.col = col-1;
	}

	public Symbol getCatchedSymbol() {
		return this.catchedSymbol;
	}

	public ScopeSymbol getCatchedScope() {
		return this.catchedScope;
	}

	public Class getCatchSymbolType() {
		return this.catchSymbolType;
	}

	public Type getCatchTypeCompatible() {
		return this.catchTypeCompatible;
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
	public Symbol visitGenericExpression(GenericExpressionContext ctx) {
		return this.visit(ctx.validType());
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
	public Symbol visitImplementableExtendable(ImplementableExtendableContext ctx) {
		if (ctx.chainExpression() != null) {
			return this.visit(ctx.chainExpression());
		}

		if (!this.validator.mustBeDefined(this.scopes.peek(), ctx.validName().getText(), ctx.getStart())) {
			throw this.validator.getException();
		}

		return this.validator.getValidatedSymbol();
	}

	@Override
	public Symbol visitStructDefinition(StructDefinitionContext ctx) {		
		ClassSymbol _class = (ClassSymbol) this.symbols.get(ctx);
		Token token = ctx.name.getStart();

		this.scopes.push(_class);

		ClassSymbol prevClass = this._class;
		this._class = _class;
		
		if (ctx.extendsFrom != null /*&& !this.definingSymbolTypes*/) {
			Symbol _extends = this.visit(ctx.extendsFrom);
			
			if (!this.validator.mustBeExtendableValid(_extends, ctx.extendsFrom.getStart())) {
				throw this.validator.getException();
			}

			ClassSymbol parent = (ClassSymbol) _extends;

			if (!this.validator.mustImplementAllMethods(parent, _class, token)) {
				throw this.validator.getException();
			}
			
			_class.extendsFrom(parent);
		}
		
		if (ctx.implementsList() != null && !this.definingSymbolTypes) {
			String interfaceName;
			Token interfaceToken;
			
			for (ImplementableExtendableContext implement : ctx.implementsList().implementableExtendable()) {
				Symbol _interface = this.visit(implement);

				if (!this.validator.mustBeImplementableTypeValid(this.validator.getValidatedSymbol(), implement.getStart())) {
					throw this.validator.getException();
				}
				
				if (!this.validator.mustImplementAllMethods((InterfaceSymbol) _interface, _class, token)) {
					throw this.validator.getException();
				}
				
				_class.define(_interface);
			}
		}

		if (this.definingSymbolTypes) {
			this.functionDefinitions.push(ctx);
		}

		super.visitStructDefinition(ctx);

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
		
		if (ctx.validType() != null) {
			type = this.visit(ctx.validType());
			Token token = ctx.validType().getStart();
			
			if (!this.validator.mustBeValidType(this.scopes.peek(), type, token)) {
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
			Symbol type = this.visit(ctx.returnType());
			function.setType((Type) type);
		}
		
		if (ctx.parameters() != null) {
			this.visit(ctx.parameters());
		}

		if (function.hasModifier(Modifier.OPERATOR)) {
			if (!this.validator.mustBeValidMethodOperator(function, function.getToken())) {
				throw this.validator.getException();
			}
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
		Symbol type = null;

		if (ctx.chainExpression() != null) {
			type = this.visit(ctx.chainExpression());
		} else {
			type = this.scopes.peek().resolve(ctx.validName().getText());
		}

		if (this.line == ctx.getStart().getLine() && ctx.getStop().getCharPositionInLine() == this.col) {
			this.catchedScope = this.scopes.peek();
			this.catchedSymbol = type;
			this.catchSymbolType = BuiltInTypeSymbol.class;
		}

		if (this.line > 0) {
			return type;
		}

		if (ctx.genericExpression() != null && type != null) {
			Symbol generic = new GenericType(type.getName(), this.scopes.peek(), ctx.getStart());

			generic.setType((Type) type);
			generic.setGeneric(this.visit(ctx.genericExpression()));

			type = generic;
		}

		if (!this.validator.mustBeValidType(this.scopes.peek(), type, ctx.getStart())) {
			throw this.validator.getException();
		}
		
		return type;
	}
	
	@Override
	public Symbol visitVariableDeclaration(VariableDeclarationContext ctx) {
		ScopeSymbol scope = this.scopes.peek();
		
		Symbol variable = this.symbols.get(ctx);
		Symbol type = this.visit(ctx.validType());
		Token typeToken = ctx.validType().getStart();
		boolean searchingSymbol = this.line > 0;

		if (!searchingSymbol && !this.validator.mustHaveAccess(scope, type, typeToken)) {
			throw this.validator.getException();
		}

		if (variable != null) {
			variable.setType((Type) type);
		}
		
		if (variable != null && ctx.value != null) {
			Symbol value = this.visit(ctx.value);
			
			if (!this.validator.mustBeTypeCompatible(scope, variable, value, typeToken)) {
				throw this.validator.getException();
			}
		}

		if (ctx.validName() != null && this.line == ctx.getStart().getLine() && ctx.validName().getStop().getCharPositionInLine() == this.col) {
			this.catchedScope = scope;
			this.catchedSymbol = variable;
		}
		
		return variable;
	}
	
	@Override
	public Symbol visitDiv(DivContext ctx) {
		Symbol left = this.visit(ctx.left);
		Symbol right = this.visit(ctx.right);
		
		if (!this.validator.mustBeValidMathOperation(this.scopes.peek(), left, right, ctx.getStart())) {
			throw this.validator.getException();
		}
		
		return this.scope.resolve("real");
	}
	
	@Override
	public Symbol visitMult(MultContext ctx) {
		Symbol left = this.visit(ctx.left);
		Symbol right = this.visit(ctx.right);
		
		if (!this.validator.mustBeValidMathOperation(this.scopes.peek(), left, right, ctx.getStart())) {
			throw this.validator.getException();
		}
		
		return this.scope.resolve("real");
	}
	
	@Override
	public Symbol visitPlus(PlusContext ctx) {
		Symbol left = this.visit(ctx.left);
		Symbol right = this.visit(ctx.right);
		
		if (left.getType().getName().equals("string")) {
			if (!this.validator.mustBeValidStringConcatenation(this.scopes.peek(), left, right, ctx.getStart())) {
				throw this.validator.getException();
			}
		} else {
			if (!this.validator.mustBeValidMathOperation(this.scopes.peek(), left, right, ctx.getStart())) {
				throw this.validator.getException();
			}
		}
		
		return left;
	}
	
	@Override
	public Symbol visitMinus(MinusContext ctx) {
		Symbol left = this.visit(ctx.left);
		Symbol right = this.visit(ctx.right);
		
		if (!this.validator.mustBeValidMathOperation(this.scopes.peek(), left, right, ctx.getStart())) {
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
		Symbol _super = new Symbol("super", this.scopes.peek(), null);
		_super.setType(this._class.getSuper());
		return _super;
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
			
			for (AllExpressionContext expr : ctx.arguments().allExpression()) {
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

		boolean searchingSymbol = this.line > 0;
		
		if (!this.validator.mustBeDefined(scope, name, token) && !searchingSymbol) {
			throw this.validator.getException();
		}
		
		Symbol variable = this.validator.getValidatedSymbol();

		if (variable != null) {
			if (!this.validator.mustBeDeclaredBeforeUsed(variable, ctx.getStart())) {
				throw this.validator.getException();
			}

			this.symbols.put(ctx, variable);

			if (ctx.index != null) {
				// chain expressions can modify the current scope so restore it
				// to avoid failing
				// example: this.foo[this.bar]
				// 'this' will update the current scope to whatever class it points to
				// then it will look for foo (which it will find it), but then it will
				// try to find 'this' and it will fail (because the current scope is
				// the class
				this.scopes.push(this.enclosingScopes.peek());
				this.visit(ctx.index);
				this.scopes.pop();

				if (variable.getType().isUserType() && !variable.hasModifier(Modifier.ARRAY)) {
					Symbol set = ((ScopeSymbol) variable.getType()).resolve("[]=");
					Symbol get = ((ScopeSymbol) variable.getType()).resolve("[]");

					if (set != null && variable.getType() instanceof GenericType) {
						((FunctionSymbol) set).registerGeneric((Symbol) variable.getType());
					}

					if (get != null && variable.getType() instanceof GenericType) {
						((FunctionSymbol) get).registerGeneric((Symbol) variable.getType());
					}

					Symbol overloaded;

					if (ctx.getParent() instanceof AssignmentStatementContext) {
						overloaded = set;
					} else {
						overloaded = get;
					}

					Symbol result = overloaded;

					if (overloaded.getGeneric() != null) {
						result = new GenericType("", this.scopes.peek(), null);
						result.setType(variable.getType());
					}

					return result;
				}
			}
		}
		
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

		if (!this.validator.mustBeCastable(cast, ctx.getStart())) {
			throw this.validator.getException();
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

		Symbol generic = null;
		Symbol parentType = null;

		for (MemberExpressionContext expr : ctx.memberExpression()) {
			generic = null;
			parentType = (Symbol) parent.getType();

			if (parentType instanceof GenericType) {
				generic = parentType;
				parentType = (Symbol) parentType.getType();

				parentType.getGeneric().setType(generic.getType());
			}

			if (parentType instanceof ScopeSymbol) {
				parentScope = (ScopeSymbol) parentType;
			} else if (parent instanceof ScopeSymbol) {
				parentScope = (ScopeSymbol) parent;
			}

			if (parentType instanceof UserTypeSymbol && parent.getType() instanceof GenericType) {
				parentType.getGeneric().setType((Type)((Symbol) parent.getType()).getGeneric());
			}

			this.scopes.push(parentScope);
			member = this.visit(expr);
			this.scopes.pop();

			if (member == null) {
				break;
			}

			if (!this.validator.mustHaveAccess(scope, member, expr.getStart())) {
				throw this.validator.getException();
			}

			if (!this.validator.mustBeValidMember(parent, member, expr.getStart())) {
				throw this.validator.getException();
			}

			if (generic != null && member.getGeneric() != null && member instanceof FunctionSymbol) {
				((FunctionSymbol) member).registerGeneric(generic);
			}

			parent = member;
		}

		if (this.line == ctx.getStart().getLine() && ctx.getStop().getCharPositionInLine() == this.col + 1) {
			this.catchedScope = scope;
			this.catchedSymbol = parent;
		}

		return member;
	}
	
	@Override
	public Symbol visitAssignmentStatement(AssignmentStatementContext ctx) {
		Symbol variable = this.visit(ctx.getChild(1));
		Symbol value = this.visit(ctx.value);
		
		if (!this.validator.mustBeTypeCompatible(this.scopes.peek(), variable, value, ctx.getStart())) {
			throw this.validator.getException();
		}
		
		return variable;
	}
	
	@Override
	public Symbol visitFunctionStatement(FunctionStatementContext ctx) {
		Symbol function = this.visit(ctx.getChild(1));

		if (this.line == ctx.getStart().getLine()) {
			this.catchSymbolType = FunctionSymbol.class;

			if (this.catchedScope == null) {
				this.catchedScope = this.scopes.peek();
			}
		}

		return function;
	}

	@Override
	public Symbol visitExitWhenStatement(ExitWhenStatementContext ctx) {
		if (!this.validator.mustBeInsideOfLoop(ctx, ctx.getStart())) {
			throw this.validator.getException();
		}
		
		return this.visit(ctx.expression());
	}

	@Override
	public Symbol visitContinueStatement(ContinueStatementContext ctx) {
		if (!this.validator.mustBeInsideOfLoop(ctx, ctx.getStart())) {
			throw this.validator.getException();
		}

		return null;
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
		
		ScopeSymbol scope = this.scopes.peek();
		
		Symbol expr = this.visit(ctx.expression());
		Token token = ctx.expression().getStart();

		if (expr == null && this.line == ctx.getStart().getLine()) {
			this.catchedScope = scope;
			this.catchTypeCompatible = scope.getType();
		} else if (!this.validator.mustBeTypeCompatible(this.scopes.peek(), scope, expr, token)) {
			throw this.validator.getException();
		}
		
		return expr;
	}
}
