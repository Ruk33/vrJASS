package com.ruke.vrjassc.vrjassc.phase;

import com.ruke.vrjassc.Config;
import com.ruke.vrjassc.translator.SymbolOverrideTranslator;
import com.ruke.vrjassc.translator.expression.*;
import com.ruke.vrjassc.translator.expression.MathExpression.Operator;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassBaseVisitor;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.*;
import com.ruke.vrjassc.vrjassc.symbol.*;
import com.ruke.vrjassc.vrjassc.util.InitializerHandler;
import com.ruke.vrjassc.vrjassc.util.TokenSymbolBag;

public class TranslationPhase extends vrjassBaseVisitor<Expression> {
	
	protected TokenSymbolBag symbols;

	protected ClassSymbol _class;

	protected ScopeSymbol scope;
	
	protected InitializerHandler initializerHandler;
	
	protected JassContainer container;
	
	private int classEnum;
	
	private int propertyEnum;
	
	public TranslationPhase(TokenSymbolBag symbols, ScopeSymbol scope) {
		this.symbols = symbols;
		this.scope = scope;
	}

	@Override
	public Expression visitInit(InitContext ctx) {
		this.container = new JassContainer();
		this.initializerHandler = new InitializerHandler();
		
		InitializerList initList = new InitializerList(this.initializerHandler);
		Expression e = null;
		
		for (TopDeclarationContext stat : ctx.topDeclaration()) {
			e = this.visit(stat);
			
			if (e != null) {
				this.container.add((Statement) e);
				
				if (e.getSymbol().getName().equals("main")) {
					((StatementBody) e).add(initList);
				}
			}
		}
		
		if (this.classEnum > 0) {
			Symbol symbol = new Symbol("vrjass_structs", null, null);
			symbol.setType(new BuiltInTypeSymbol("hashtable", null, null));
			
			Statement structHashtable = new VariableStatement(symbol, new RawExpression("InitHashtable()", null));
			this.container.addGlobal(structHashtable);
			
			Symbol vtype = new VariableSymbol("vtype", null, null);
			vtype.setType(new BuiltInTypeSymbol("integer", null, null));
			this.container.addGlobal(new VariableStatement(vtype, new RawExpression("-1")));
		}

		return this.container;
	}
	
	@Override
	public Expression visitCast(CastContext ctx) {		
		Expression expression = this.visit(ctx.original);
		Expression cast = null;

		if (ctx.chainExpression() != null) {
			cast = this.visit(ctx.chainExpression());
		} else {
			cast = new VariableExpression(this.symbols.get(ctx.validName()), null);
		}
		
		return new CastExpression(expression, cast);
	}

	@Override
	public Expression visitNull(NullContext ctx) {
		return new RawExpression("null", this.scope.resolve("null"));
	}

	@Override
	public Expression visitLogical(LogicalContext ctx) {
		Expression left = new BooleanExpression(this.visit(ctx.left));
		Expression right = new BooleanExpression(this.visit(ctx.right));
		
		LogicalExpression.Operator operator;
		
		if (ctx.OR() == null) {
			operator = LogicalExpression.Operator.AND;
		} else {
			operator = LogicalExpression.Operator.OR;
		}

		return new LogicalExpression(left, operator, right);
	}

	@Override
	public Expression visitString(StringContext ctx) {
		return new RawExpression(ctx.getText(), this.scope.resolve("string"));
	}

	@Override
	public Expression visitCode(CodeContext ctx) {
		Symbol function = null;

		if (ctx.chainExpression() != null) {
			function = this.visit(ctx.chainExpression()).getSymbol();
		} else {
			function = this.symbols.get(ctx.validName());
		}

		return new FunctionExpression(function, true, new ExpressionList());
	}

	@Override
	public Expression visitInteger(IntegerContext ctx) {
		return new RawExpression(ctx.getText(), this.scope.resolve("integer"));
	}

	@Override
	public Expression visitModulo(ModuloContext ctx) {
		Expression a = this.visit(ctx.left);
		Expression b = this.visit(ctx.right);

		return new ModuloExpression(a, b);
	}

	@Override
	public Expression visitDiv(DivContext ctx) {
		Expression a = this.visit(ctx.left);
		Expression b = this.visit(ctx.right);
		
		return new MathExpression(a, MathExpression.Operator.DIV, b);
	}

	@Override
	public Expression visitNot(NotContext ctx) {
		return new NotExpression(this.visit(ctx.expression()));
	}

	@Override
	public Expression visitParenthesis(ParenthesisContext ctx) {
		return new ParenthesisExpression(this.visit(ctx.expression()));
	}

	@Override
	public Expression visitNegative(NegativeContext ctx) {
		return new NegativeExpression(this.visit(ctx.expression()));
	}

	@Override
	public Expression visitMult(MultContext ctx) {
		Expression a = this.visit(ctx.left);
		Expression b = this.visit(ctx.right);
		
		return new MathExpression(a, MathExpression.Operator.MULT, b);
	}

	@Override
	public Expression visitComparison(ComparisonContext ctx) {
		Expression a = this.visit(ctx.left);
		Expression b = null;
		BooleanExpression.Operator operator = null;
		
		if (ctx.right != null) {
			b = this.visit(ctx.right);
		}
		
		if (ctx.EQEQ() != null) {
			operator = BooleanExpression.Operator.EQUAL_EQUAL;
		} else if (ctx.NOT_EQ() != null) {
			operator = BooleanExpression.Operator.NOT_EQUAL;
		} else if (ctx.GREATER() != null) {
			operator = BooleanExpression.Operator.GREATER;
		} else if (ctx.GREATER_EQ() != null) {
			operator = BooleanExpression.Operator.GREATER_EQUAL;
		} else if (ctx.LESS() != null) {
			operator = BooleanExpression.Operator.LESS;
		} else if (ctx.LESS_EQ() != null) {
			operator = BooleanExpression.Operator.LESS_EQUAL;
		}
		
		return new BooleanExpression(a, operator, b);
	}

	@Override
	public Expression visitReal(RealContext ctx) {
		return new RawExpression(ctx.getText(), this.scope.resolve("real"));
	}

	@Override
	public Expression visitBoolean(BooleanContext ctx) {
		return new RawExpression(ctx.getText(), this.scope.resolve("boolean"));
	}

	@Override
	public Expression visitPlus(PlusContext ctx) {
		Expression a = this.visit(ctx.left);
		Expression b = this.visit(ctx.right);

		return new MathExpression(a, MathExpression.Operator.PLUS, b);
	}

	@Override
	public Expression visitMinus(MinusContext ctx) {
		Expression a = this.visit(ctx.left);
		Expression b = this.visit(ctx.right);
		
		return new MathExpression(a, MathExpression.Operator.MINUS, b);
	}

	@Override
	public Expression visitFunctionExpression(FunctionExpressionContext ctx) {
		Symbol symbol = this.symbols.get(ctx);
		ExpressionList args = null;
		
		if (ctx.arguments() == null) {
			args = new ExpressionList();
		} else {
			args = (ExpressionList) this.visit(ctx.arguments());
		}

		if (symbol == null) {
			String name = ctx.validName().getText();
			symbol = this._class.resolve(name);
		}

		Expression result = new FunctionExpression(symbol, false, args);

		return result;
	}

	@Override
	public Expression visitVariableExpression(VariableExpressionContext ctx) {
		Symbol symbol = this.symbols.get(ctx);
		Expression index = null;
		
		if (ctx.index != null) {
			index = this.visit(ctx.index);

			if (symbol.getType().isUserType()) {
				Symbol operator = null;
				
				if (ctx.getParent() instanceof AssignmentStatementContext) {
					operator = ((ScopeSymbol) symbol.getType()).resolve("[]=");
				} else {
					operator = ((ScopeSymbol) symbol.getType()).resolve("[]");
				}
				
				if (operator != null) {
					ExpressionList el = new ExpressionList();

					el.add(new VariableExpression(symbol, null));
					el.add(index);

					return new FunctionExpression(operator, false, el);
				}
			}
		}
		
		return new VariableExpression(symbol, index);
	}

	@Override
	public Expression visitSuperExpression(SuperExpressionContext ctx) {
		return new SuperExpression(this._class.getSuper());
	}

	@Override
	public Expression visitThisExpression(ThisExpressionContext ctx) {
		Symbol _this = this.symbols.get(ctx);
		
		if (_this == null) {
			// methods create a 'this' parameter, that is why
			// the symbol bag returns null
			_this = new Symbol("this", null, null);
			_this.setType(this._class);
		}
		
		return new VariableExpression(_this, null);
	}

	@Override
	public Expression visitMemberExpression(MemberExpressionContext ctx) {
		if (ctx.variableExpression() != null) {
			return this.visit(ctx.variableExpression());
		}

		return this.visit(ctx.functionExpression());
	}

	@Override
	public Expression visitChainExpression(ChainExpressionContext ctx) {
		ChainExpression chain = new ChainExpression();
		
		chain.setHashtableName(Config.STRUCT_HASHTABLE_NAME);

		chain.append(this.visit(ctx.getChild(0)), null);

		for (MemberExpressionContext expr : ctx.memberExpression()) {
			chain.append(this.visit(expr), null);
		}
		
		return chain;
	}

	@Override
	public Expression visitElseIfStatement(ElseIfStatementContext ctx) {
		BooleanExpression condition = new BooleanExpression(this.visit(ctx.expression()));
		ElseIfStatement elif = new ElseIfStatement(condition);
		
		for (StatementContext statement : ctx.statement()) {
			elif.add((Statement) this.visit(statement));
		}
		
		return elif;
	}

	@Override
	public Expression visitElseStatement(ElseStatementContext ctx) {
		ElseStatement _else = new ElseStatement();
		
		for (StatementContext statement : ctx.statement()) {
			_else.add((Statement) this.visit(statement));
		}
		
		return _else;
	}

	@Override
	public Expression visitIfStatement(IfStatementContext ctx) {
		BooleanExpression condition = new BooleanExpression(this.visit(ctx.expression()));
		IfStatement _if = new IfStatement(condition);

		for (StatementContext stat : ctx.statement()) {
			_if.add((Statement) this.visit(stat));
		}
		
		if (ctx.elseIfStatement() != null) {
			for (ElseIfStatementContext elif : ctx.elseIfStatement()) {
				_if.add((Statement) this.visit(elif));
			}
		}
		
		if (ctx.elseStatement() != null) {
			_if.add((Statement) this.visit(ctx.elseStatement()));
		}
		
		return _if;
	}

	@Override
	public Expression visitLoopStatement(LoopStatementContext ctx) {
		LoopStatement loop = new LoopStatement();
		Statement statement;
		
		for (StatementContext stat : ctx.statement()) {
			statement = (Statement) this.visit(stat);
			
			if (statement != null) { 
				loop.add(statement);
			}
		}
		
		return loop;
	}
	
	@Override
	public Expression visitWhileLoopStatement(WhileLoopStatementContext ctx) {
		LoopStatement loop = new WhileLoopStatement(new BooleanExpression(this.visit(ctx.expression())));
		Statement statement;
		
		for (StatementContext stat : ctx.statement()) {
			statement = (Statement) this.visit(stat);
			
			if (statement != null) {
				loop.add(statement);
			}
		}
		
		return loop;
	}
	
	@Override
	public Expression visitGlobalVariableStatement(GlobalVariableStatementContext ctx) {
		Symbol variable = this.symbols.get(ctx);
		Expression value = null;
		
		if (ctx.variableDeclaration().value != null) {
			value = this.visit(ctx.variableDeclaration().value);
		}
		
		Statement global = new VariableStatement(variable, value);
		this.container.addGlobal(global);
		
		return global;
	}

	@Override
	public Expression visitLocalVariableStatement(LocalVariableStatementContext ctx) {
		Symbol symbol = this.symbols.get(ctx);
		Expression value = null;
		
		if (ctx.variableDeclaration().value != null) {
			value = this.visit(ctx.variableDeclaration().value);
		}
		
		return new VariableStatement(symbol, value);
	}

	@Override
	public Expression visitAssignmentStatement(AssignmentStatementContext ctx) {
		Expression name = this.visit(ctx.getChild(1));
		Operator operator = null;
		Expression value;
		
		if (ctx.PLUS() != null) {
			operator = Operator.PLUS;
		} else if (ctx.MINUS() != null) {
			operator = Operator.MINUS;
		} else if (ctx.TIMES() != null) {
			operator = Operator.MULT;
		} else if (ctx.DIV() != null) {
			operator = Operator.DIV;
		}
		
		value = this.visit(ctx.value);
		
		if (operator != null) {
			if (name.getSymbol() instanceof FunctionSymbol) {
				Symbol s = ((FunctionSymbol) name.getSymbol()).resolve("[]");
				ExpressionList nameArgs = ((FunctionExpression) name).getArguments();
				ExpressionList args = new ExpressionList();
				
				args.add(new RawExpression(nameArgs.getList().get(0)));
				args.add(new RawExpression(nameArgs.getList().get(1)));
				
				value = new MathExpression(
					new FunctionExpression(s, false, args),
					operator,
					value
				);
			} else {
				// Avoid mutual recursion
				value = new MathExpression(
					new RawExpression(name.translate(), name.getSymbol()),
					operator,
					value
				);
			}
		}
		
		return new AssignmentStatement(name, value);
	}

	@Override
	public Expression visitFunctionStatement(FunctionStatementContext ctx) {
		return new FunctionStatement(this.visit(ctx.getChild(1)));
	}

	@Override
	public Expression visitExitWhenStatement(ExitWhenStatementContext ctx) {
		BooleanExpression condition = new BooleanExpression(this.visit(ctx.expression()));
		return new ExitWhenStatement(condition);
	}

	@Override
	public Expression visitContinueStatement(ContinueStatementContext ctx) {
		return new ContinueStatement();
	}

	@Override
	public Expression visitBreakStatement(BreakStatementContext ctx) {
		return new ExitWhenStatement(new RawExpression("true"));
	}

	@Override
	public Expression visitReturnStatement(ReturnStatementContext ctx) {
		Expression value = null;
		
		if (ctx.expression() != null) {
			value = this.visit(ctx.expression());
		}
		
		return new ReturnStatement(value);
	}
	
	@Override
	public Expression visitInterfaceDefinition(InterfaceDefinitionContext ctx) {
		InterfaceSymbol _interface = (InterfaceSymbol) this.symbols.get(ctx);
		
		for (Symbol method : _interface.getChilds().values()) {
			if (method instanceof FunctionSymbol == false) continue;
			
			this.container.add(
				SymbolOverrideTranslator.build(
					_interface, 
					(FunctionSymbol) method
				)
			);
		}
		
		return null;
	}

	@Override
	public Expression visitStructDefinition(StructDefinitionContext ctx) {
		Statement statement;
		
		this.classEnum++;
		
		ClassSymbol _class = (ClassSymbol) this.symbols.get(ctx);
		this.initializerHandler.add(_class);

		ClassSymbol prevClass = this._class;
		this._class = _class;

		for (Symbol _abstract : _class.getAbstractMethods()) {
			this.container.add(
				SymbolOverrideTranslator.build(
				(FunctionSymbol) _abstract,
				(FunctionSymbol) _abstract
				)
			);
		}

		for (StructStatementContext ssc : ctx.structStatement()) {
			statement = (Statement) this.visit(ssc);

			if (statement != null) {
				this.container.add(statement);
			}
		}

		this._class = prevClass;

		return null;
	}
	
	@Override
	public Expression visitAnonymousExpression(AnonymousExpressionContext ctx) {
		this.container.add((Statement) this.visit(ctx.functionDefinitionExpression()));
		return new FunctionExpression(this.symbols.get(ctx), true, new ExpressionList());
	}
	
	@Override
	public Expression visitFunctionDefinitionExpression(FunctionDefinitionExpressionContext ctx) {
		FunctionSymbol symbol = (FunctionSymbol) this.symbols.get(ctx);
		FunctionDefinition function = new FunctionDefinition(symbol);
		Expression e;
		
		if (!symbol.getImplementations().isEmpty() || symbol.hasModifier(Modifier.ABSTRACT)) {
			this.container.add(SymbolOverrideTranslator.build(symbol, symbol));
		}
		
		for (StatementContext statement : ctx.statement()) {
			e = this.visit(statement);
			
			if (e != null) {
				function.add((Statement) e);
			}
		}

		if (!symbol.getGenerics().isEmpty()) {
			function = new GenericFunctionDefinition(function);
		}
		
		return function;
	}
	
	@Override
	public Expression visitFunctionDefinition(FunctionDefinitionContext ctx) {
		return this.visit(ctx.functionDefinitionExpression());
	}

	@Override
	public Expression visitArguments(ArgumentsContext ctx) {
		ExpressionList args = new ExpressionList();
		
		for (AllExpressionContext expr : ctx.allExpression()) {
			args.add(this.visit(expr));
		}
		
		return args;
	}
	
	@Override
	public Expression visitPropertyStatement(PropertyStatementContext ctx) {
		Symbol symbol = this.symbols.get(ctx);
		Expression value = null;
		
		if (!symbol.hasModifier(Modifier.STATIC)) {
			this.propertyEnum++;
			value = new RawExpression(String.valueOf(this.propertyEnum), this.scope.resolve("integer"));
		}
		
		Symbol global = new VariableSymbol(symbol.getName(), symbol.getParentScope(), null);
		
		if (symbol.hasModifier(Modifier.STATIC)) {
			global = symbol;
		} else {
			global.setType((Type) this.scope.resolve("integer"));
		}
		
		this.container.addGlobal(new VariableStatement(global, value));
		
		return null;
	}
		
	@Override
	public Expression visitLibraryDefinition(LibraryDefinitionContext ctx) {
		LibrarySymbol lib = (LibrarySymbol) this.symbols.get(ctx);
		Expression e;
		
		this.initializerHandler.add(lib);
		
		for (LibraryStatementContext statement : ctx.libraryStatement()) {
			e = this.visit(statement);
			
			if (e != null) {
				this.container.add((Statement) e);
			}
		}
		
		return null;
	}
	
}
