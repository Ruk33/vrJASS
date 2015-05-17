package com.ruke.vrjassc.vrjassc.visitor;

import java.util.LinkedList;
import java.util.Stack;

import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;

import com.ruke.vrjassc.vrjassc.antlr4.vrjassBaseVisitor;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.AltInitContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.ArgumentContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.ArgumentsContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.BooleanContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.BooleanExpressionContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.ClassDefinitionContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.ClassStatementsContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.CodeContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.ComparisonContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.DivContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.ElseIfStatementContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.ElseStatementContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.ExitwhenStatementContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.ExtendListContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.FunctionDefinitionContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.FunctionExpressionContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.FunctionStatementContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.GlobalVariableStatementContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.IfStatementContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.ImplementModuleContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.InitContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.InitializerContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.IntegerContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.LibraryDefinitionContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.LibraryStatementsContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.LocalVariableStatementContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.LogicalContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.LoopStatementContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.MemberContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.MethodDefinitionContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.MinusContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.ModuleDefinitionContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.MultContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.NativeDefinitionContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.NullContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.ParameterContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.ParametersContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.ParenthesisContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.PlusContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.PropertyStatementContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.RealContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.ReturnStatementContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.ReturnTypeContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.SetVariableStatementContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.StatementContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.StatementsContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.StringContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.SuperContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.ThisContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.ThistypeContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.TypeDefinitionContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.VariableContext;
import com.ruke.vrjassc.vrjassc.antlr4.vrjassParser.VariableTypeContext;
import com.ruke.vrjassc.vrjassc.exception.ElementNoAccessException;
import com.ruke.vrjassc.vrjassc.exception.EqualNotEqualComparisonException;
import com.ruke.vrjassc.vrjassc.exception.ImplementAbstractMethodClassException;
import com.ruke.vrjassc.vrjassc.exception.IncorrectArgumentTypeFunctionCallException;
import com.ruke.vrjassc.vrjassc.exception.IncorrectReturnTypeFunctionException;
import com.ruke.vrjassc.vrjassc.exception.IncorrectVariableTypeException;
import com.ruke.vrjassc.vrjassc.exception.InitializeArrayVariableException;
import com.ruke.vrjassc.vrjassc.exception.InvalidArrayVariableIndexException;
import com.ruke.vrjassc.vrjassc.exception.InvalidBooleanException;
import com.ruke.vrjassc.vrjassc.exception.LessGreaterComparisonException;
import com.ruke.vrjassc.vrjassc.exception.LogicalException;
import com.ruke.vrjassc.vrjassc.exception.MathematicalExpressionException;
import com.ruke.vrjassc.vrjassc.exception.NoFunctionException;
import com.ruke.vrjassc.vrjassc.exception.NoReturnFunctionException;
import com.ruke.vrjassc.vrjassc.exception.NoScopeVisibilityException;
import com.ruke.vrjassc.vrjassc.exception.SetConstantVariableException;
import com.ruke.vrjassc.vrjassc.exception.TooFewArgumentsFunctionCallException;
import com.ruke.vrjassc.vrjassc.exception.TooManyArgumentsFunctionCallException;
import com.ruke.vrjassc.vrjassc.exception.TooManyExtendClassException;
import com.ruke.vrjassc.vrjassc.exception.UndefinedFunctionException;
import com.ruke.vrjassc.vrjassc.exception.UndefinedMethodException;
import com.ruke.vrjassc.vrjassc.exception.UndefinedModuleException;
import com.ruke.vrjassc.vrjassc.exception.UndefinedPropertyException;
import com.ruke.vrjassc.vrjassc.exception.UndefinedVariableException;
import com.ruke.vrjassc.vrjassc.exception.VariableIsNotArrayException;
import com.ruke.vrjassc.vrjassc.symbol.ClassMemberSymbol;
import com.ruke.vrjassc.vrjassc.symbol.ClassSymbol;
import com.ruke.vrjassc.vrjassc.symbol.FunctionSymbol;
import com.ruke.vrjassc.vrjassc.symbol.InitializerContainerSymbol;
import com.ruke.vrjassc.vrjassc.symbol.InterfaceSymbol;
import com.ruke.vrjassc.vrjassc.symbol.MethodSymbol;
import com.ruke.vrjassc.vrjassc.symbol.PrimitiveType;
import com.ruke.vrjassc.vrjassc.symbol.PropertySymbol;
import com.ruke.vrjassc.vrjassc.symbol.Symbol;
import com.ruke.vrjassc.vrjassc.symbol.VariableSymbol;
import com.ruke.vrjassc.vrjassc.util.ClassDefaultAllocator;
import com.ruke.vrjassc.vrjassc.util.FunctionSorter;
import com.ruke.vrjassc.vrjassc.util.InitializerHandler;
import com.ruke.vrjassc.vrjassc.util.Prefix;
import com.ruke.vrjassc.vrjassc.util.TypeCompatibleChecker;
import com.ruke.vrjassc.vrjassc.util.VariableTypeDetector;

public class MainVisitor extends vrjassBaseVisitor<String> {

	protected Stack<String> types;

	protected Stack<String> natives;

	protected Stack<String> globalsBlock;

	protected Stack<String> classGlobals;

	protected FunctionSorter functionSorter;
	
	protected InitializerHandler initializerHandler;

	protected Prefix prefixer;

	protected SymbolVisitor symbolVisitor;

	protected Symbol scope;

	protected Symbol _class;

	protected Symbol symbol;

	protected boolean hasReturn;

	protected String expressionType;

	protected String output;

	public MainVisitor(vrjassParser parser, SymbolVisitor symbolVisitor) {
		this.types = new Stack<String>();
		this.globalsBlock = new Stack<String>();
		this.classGlobals = new Stack<String>();
		this.natives = new Stack<String>();
		this.functionSorter = new FunctionSorter();
		this.initializerHandler = new InitializerHandler();

		this.prefixer = new Prefix();
		this.symbolVisitor = symbolVisitor;
		this.scope = this.symbolVisitor.getGlobalScope();

		this.symbolVisitor.visit(parser.init());
		parser.reset();

		this.output = this.visit(parser.init());
	}

	public MainVisitor(vrjassParser parser) {
		this(parser, new SymbolVisitor());
	}

	public Prefix getPrefixer() {
		return this.prefixer;
	}
	
	@Override
	public String visitModuleDefinition(ModuleDefinitionContext ctx) {
		return "";
	}
	
	@Override
	public String visitImplementModule(ImplementModuleContext ctx) {
		String name = ctx.moduleName.getText();
		Symbol module = this.scope.resolve(name, PrimitiveType.MODULE, true);
		
		if (module == null) {
			throw new UndefinedModuleException(ctx.moduleName, name);
		}
		
		this.scope.addChild(module);
		
		return "";
	}
	
	@Override
	public String visitNull(NullContext ctx) {
		this.expressionType = "null";
		return "null";
	}

	@Override
	public String visitReal(RealContext ctx) {
		this.expressionType = "real";
		return ctx.REAL().getText();
	}

	@Override
	public String visitCode(CodeContext ctx) {
		String result = "function " + this.visit(ctx.expression());

		this.expressionType = "code";
		return result;
	}

	@Override
	public String visitTypeDefinition(TypeDefinitionContext ctx) {
		String name = ctx.typeName.getText();
		String extendName = ctx.extendName.getText();
		String result = "type " + name + " extends " + extendName;

		this.types.push(result);

		return result;
	}

	@Override
	public String visitNativeDefinition(NativeDefinitionContext ctx) {
		String name = ctx.functionName.getText();
		String params = this.visit(ctx.parameters());
		String type = "nothing";

		if (ctx.returnType() != null && ctx.returnType().variableType() != null) {
			type = ctx.returnType().variableType().getText();
		}

		String result = "native " + name + " takes " + params + " returns "
				+ type;

		if (ctx.CONSTANT() != null) {
			result = "constant " + result;
		}

		this.natives.push(result);
		return result;
	}

	@Override
	public String visitExitwhenStatement(ExitwhenStatementContext ctx) {
		return "exitwhen " + this.visit(ctx.booleanExpression());
	}

	@Override
	public String visitLoopStatement(LoopStatementContext ctx) {
		Stack<String> result = new Stack<String>();

		result.push("loop");
		result.push(this.visit(ctx.statements()));
		result.push("endloop");

		return String.join(System.lineSeparator(), result);
	}

	@Override
	public String visitElseStatement(ElseStatementContext ctx) {
		Stack<String> result = new Stack<String>();
		String visited = this.visit(ctx.statements());

		result.push("else");

		if (visited != null && !visited.isEmpty()) {
			result.push(visited);
		}

		return String.join(System.lineSeparator(), result);
	}

	@Override
	public String visitBooleanExpression(BooleanExpressionContext ctx) {
		String result = this.visit(ctx.expression());

		if (this.expressionType.equals("integer")) {
			result += "!=0";
			this.expressionType = "boolean";
		}

		if (this.expressionType.equals("null")
				|| VariableTypeDetector.isHandle(this.expressionType)) {
			result += "!=null";
			this.expressionType = "boolean";
		}

		if (!this.expressionType.equals("boolean")) {
			throw new InvalidBooleanException(ctx.expression().getStart(),
					this.expressionType);
		}

		return result;
	}

	@Override
	public String visitElseIfStatement(ElseIfStatementContext ctx) {
		Stack<String> result = new Stack<String>();
		String visited = this.visit(ctx.booleanExpression());

		result.push("elseif " + visited + " then");

		visited = this.visit(ctx.statements());

		if (visited != null && !visited.isEmpty()) {
			result.push(visited);
		}

		return String.join(System.lineSeparator(), result);
	}

	@Override
	public String visitIfStatement(IfStatementContext ctx) {
		Stack<String> result = new Stack<String>();
		String visited = this.visit(ctx.booleanExpression());

		result.push("if " + visited + " then");

		visited = this.visit(ctx.statements());

		if (!visited.isEmpty()) {
			result.push(visited);
		}

		for (ElseIfStatementContext elseIfStatement : ctx.elseIfStatement()) {
			visited = this.visit(elseIfStatement);

			if (visited != null) {
				result.push(visited);
			}
		}

		if (ctx.elseStatement() != null) {
			result.push(this.visit(ctx.elseStatement()));
		}

		result.push("endif");

		return String.join(System.lineSeparator(), result);
	}

	@Override
	public String visitBoolean(BooleanContext ctx) {
		this.expressionType = "boolean";
		return ctx.getText();
	}

	@Override
	public String visitThis(ThisContext ctx) {
		this.expressionType = this.scope.resolve("this",
				PrimitiveType.VARIABLE, false).getType();
		return "this";
	}

	@Override
	public String visitThistype(ThistypeContext ctx) {
		Symbol _class = this.scope.getParent();
		this.expressionType = _class.getType();

		return _class.getFullName();
	}
	
	@Override
	public String visitSuper(SuperContext ctx) {
		ClassSymbol _super = ((ClassSymbol) this.scope.getParent()).getSuper();
		this.expressionType = _super.getType();
		
		return "this";
	}
	
	@Override
	public String visitMember(MemberContext ctx) {
		String left = this.visit(ctx.left);
		String leftType = this.expressionType;
		String right = null;
		Symbol member = null;
		String result;
		
		Symbol prevClass = this._class;
		this._class = this.scope.resolve(leftType, PrimitiveType.CLASS, true);

		right = this.visit(ctx.right);
		member = this.symbol;
		result = member.getFullName();

		if (member instanceof ClassMemberSymbol) {
			if (!((ClassMemberSymbol) member).isStatic()) {
				if (member instanceof MethodSymbol) {
					result += "(" + left + ")";
				} else {
					result += "[" + left + "]";
				}
			} else {
				result = right;
			}
		}

		this._class = prevClass;
		this.expressionType = member.getType();
		return result;
	}

	@Override
	public String visitVariable(VariableContext ctx) {
		String name = ctx.varName.getText();
		String index = "";
		Symbol variable = null;

		if (this._class != null) {
			variable = this._class.resolve(name, PrimitiveType.VARIABLE, false);

			if (variable == null || variable.getParent() != this._class) {
				throw new UndefinedPropertyException(ctx.varName, this._class);
			}
		} else {
			variable = this.scope.resolve(name, PrimitiveType.VARIABLE, true);

			if (variable == null) {
				throw new UndefinedVariableException(ctx.varName);
			}
		}

		if (!this.scope.hasAccess(variable)) {
			throw new ElementNoAccessException(ctx.varName);
		}

		if (ctx.index != null) {
			index = "[" + this.visit(ctx.index) + "]";

			if (((VariableSymbol) variable).isArray()) {
				if (!"integer".equals(this.expressionType)) {
					throw new InvalidArrayVariableIndexException(
							ctx.index.getStart());
				}
			} else {
				throw new VariableIsNotArrayException(ctx.varName);
			}
		}

		this.symbol = variable;
		this.expressionType = variable.getType();

		return variable.getFullName() + index;
	}

	@Override
	public String visitVariableType(VariableTypeContext ctx) {
		String type = ctx.getText();

		if (VariableTypeDetector.isUserType(type)) {
			type = "integer";
		}

		return type;
	}

	@Override
	public String visitString(StringContext ctx) {
		this.expressionType = "string";
		return ctx.getText();
	}

	@Override
	public String visitInteger(IntegerContext ctx) {
		this.expressionType = "integer";
		return ctx.INT().getText();
	}

	@Override
	public String visitParenthesis(ParenthesisContext ctx) {
		return "(" + this.visit(ctx.expression()) + ")";
	}

	@Override
	public String visitDiv(DivContext ctx) {
		String left = this.visit(ctx.left);
		String leftType = this.expressionType;
		boolean leftIsNumeric = "real".equals(leftType)
				|| "integer".equals(leftType);

		String right = this.visit(ctx.right);
		String rightType = this.expressionType;
		boolean rightIsNumeric = "real".equals(rightType)
				|| "integer".equals(rightType);

		if (!leftIsNumeric) {
			throw new MathematicalExpressionException(ctx.left.getStart());
		}

		if (!rightIsNumeric) {
			throw new MathematicalExpressionException(ctx.right.getStart());
		}

		this.expressionType = "real";
		return left + '/' + right;
	}

	@Override
	public String visitMult(MultContext ctx) {
		String left = this.visit(ctx.left);
		String leftType = this.expressionType;
		boolean leftIsNumeric = "real".equals(leftType)
				|| "integer".equals(leftType);

		String right = this.visit(ctx.right);
		String rightType = this.expressionType;
		boolean rightIsNumeric = "real".equals(rightType)
				|| "integer".equals(rightType);

		if (!leftIsNumeric) {
			throw new MathematicalExpressionException(ctx.left.getStart());
		}

		if (!rightIsNumeric) {
			throw new MathematicalExpressionException(ctx.right.getStart());
		}

		this.expressionType = "integer";
		return left + '*' + right;
	}

	@Override
	public String visitMinus(MinusContext ctx) {
		String left = this.visit(ctx.left);
		String leftType = this.expressionType;
		boolean leftIsNumeric = "real".equals(leftType)
				|| "integer".equals(leftType);

		String right = this.visit(ctx.right);
		String rightType = this.expressionType;
		boolean rightIsNumeric = "real".equals(rightType)
				|| "integer".equals(rightType);

		if (!leftIsNumeric) {
			throw new MathematicalExpressionException(ctx.left.getStart());
		}

		if (!rightIsNumeric) {
			throw new MathematicalExpressionException(ctx.right.getStart());
		}

		this.expressionType = "integer";
		return left + '-' + right;
	}

	@Override
	public String visitPlus(PlusContext ctx) {
		String left = this.visit(ctx.left);
		String leftType = this.expressionType;
		boolean leftIsNumeric = "real".equals(leftType)
				|| "integer".equals(leftType);

		String right = this.visit(ctx.right);
		String rightType = this.expressionType;
		boolean rightIsNumeric = "real".equals(rightType)
				|| "integer".equals(rightType);

		if (leftType.equals("string") && rightType.equals("string")) {
			this.expressionType = "string";
		} else {
			if (!leftIsNumeric) {
				throw new MathematicalExpressionException(ctx.left.getStart());
			}

			if (!rightIsNumeric) {
				throw new MathematicalExpressionException(ctx.right.getStart());
			}

			this.expressionType = "integer";
		}

		return left + '+' + right;
	}

	@Override
	public String visitComparison(ComparisonContext ctx) {
		String left = this.visit(ctx.left);
		String leftType = this.expressionType;
		boolean leftIsNumeric = "real".equals(leftType)
				|| "integer".equals(leftType);

		String right = this.visit(ctx.right);
		String rightType = this.expressionType;
		boolean rightIsNumeric = "real".equals(rightType)
				|| "integer".equals(rightType);

		String operator = ctx.operator.getText();

		switch (operator) {
		case "==":
		case "!=":
			if (!TypeCompatibleChecker.isCompatible(leftType, rightType)) {
				throw new EqualNotEqualComparisonException(ctx.getStart());
			}

			break;

		case "<":
		case ">":
		case "<=":
		case ">=":
			if (!leftIsNumeric) {
				throw new LessGreaterComparisonException(ctx.left.getStart());
			}

			if (!rightIsNumeric) {
				throw new LessGreaterComparisonException(ctx.right.getStart());
			}

			break;
		}

		this.expressionType = "boolean";
		return left + operator + right;
	}

	@Override
	public String visitLogical(LogicalContext ctx) {
		String left = this.visit(ctx.left);
		String leftType = this.expressionType;

		if (leftType.equals("integer")) {
			left += "!=0";
			leftType = "boolean";
		}

		boolean leftIsBoolean = "boolean".equals(leftType);

		String right = this.visit(ctx.right);
		String rightType = this.expressionType;

		if (rightType.equals("integer")) {
			right += "!=0";
			rightType = "boolean";
		}

		boolean rightIsBoolean = "boolean".equals(rightType);

		if (!leftIsBoolean) {
			throw new LogicalException(ctx.left.getStart());
		}

		if (!rightIsBoolean) {
			throw new LogicalException(ctx.right.getStart());
		}

		this.expressionType = "boolean";
		return left + " " + ctx.operator.getText() + " " + right;
	}

	@Override
	public String visitArgument(ArgumentContext ctx) {
		return this.visit(ctx.expression());
	}

	@Override
	public String visitArguments(ArgumentsContext ctx) {
		FunctionSymbol function = (FunctionSymbol) this.symbol;

		Stack<Symbol> params = function.getParams();
		Stack<String> args = new Stack<String>();

		String prevExprType = this.expressionType;

		int i = 0;

		if (params.size() == 0) {
			return "";
		}

		for (ArgumentContext arg : ctx.argument()) {
			args.push(this.visit(arg));

			if (!params.get(i).isTypeCompatible(this.expressionType)) {
				throw new IncorrectArgumentTypeFunctionCallException(
						arg.getStart(), params.get(i).getType(),
						this.expressionType);
			}

			i++;

			this.expressionType = null;
		}

		this.expressionType = prevExprType;
		return String.join(",", args);
	}

	@Override
	public String visitReturnType(ReturnTypeContext ctx) {
		if (ctx.variableType() == null) {
			return "nothing";
		}

		return this.visit(ctx.variableType());
	}

	@Override
	public String visitParameter(ParameterContext ctx) {
		return this.visit(ctx.variableType()) + ' ' + ctx.ID().getText();
	}

	@Override
	public String visitParameters(ParametersContext ctx) {
		Stack<String> params = new Stack<String>();

		if (ctx.parameter().size() == 0) {
			params.push("nothing");
		} else {
			for (ParameterContext param : ctx.parameter()) {
				params.push(this.visit(param));
			}
		}

		return String.join(",", params);
	}

	@Override
	public String visitFunctionExpression(FunctionExpressionContext ctx) {
		String name = ctx.functionName.getText();
		FunctionSymbol function = null;
		int argumentsCount = 0;
		
		if (this._class != null) {
			function = (FunctionSymbol) this._class.resolve(name, PrimitiveType.FUNCTION, true);
		} else {
			function = (FunctionSymbol) this.scope.resolve(name, PrimitiveType.FUNCTION, true);
		}

		if (function == null) {
			if (this._class != null) {
				throw new UndefinedMethodException(ctx.functionName, this._class);
			} else {
				if (name.startsWith("InitTrig_")) {
					return "";
				}

				throw new UndefinedFunctionException(ctx.functionName);
			}
		}

		if (!this.scope.hasAccess(function.getParent())) {
			throw new ElementNoAccessException(ctx.functionName);
		}

		int funcParamCount = function.getParams().size();

		if (function instanceof MethodSymbol) {
			if (!((MethodSymbol) function).isStatic()) {
				funcParamCount--;
			}
		}

		if (ctx.arguments() != null) {
			argumentsCount = ctx.arguments().argument().size();
		}

		if (argumentsCount > funcParamCount) {
			throw new TooManyArgumentsFunctionCallException(ctx.functionName);
		}

		if (argumentsCount < funcParamCount) {
			throw new TooFewArgumentsFunctionCallException(ctx.functionName);
		}

		this.symbol = function;

		String finalName = function.getFullName();
		String functionCalling = this.scope.getFullName();

		if (!this.functionSorter.functionBeingCalled(function, functionCalling)) {
			finalName = this.functionSorter.getDummyPrefix() + finalName;
		}

		String args = "";

		if (argumentsCount > 0) {
			args = this.visit(ctx.arguments());
		}

		if (function instanceof MethodSymbol) {
			if (!((MethodSymbol) function).isStatic()) {
				if (args.equals("")) {
					args = "this";
				} else {
					args = "this," + args;
				}
			}
		}

		this.expressionType = function.getType();

		return finalName + "(" + args + ")";
	}

	@Override
	public String visitFunctionStatement(FunctionStatementContext ctx) {
		String exprType = ctx.func.getClass().getSimpleName();

		if (exprType.equals("MemberContext")) {
			int exprCount = ctx.func.getChildCount();
			ParseTree lastExpr = ctx.func.getChild(exprCount - 1);
			exprType = lastExpr.getClass().getSimpleName();
		}

		if (!exprType.equals("FunctionContext")) {
			throw new NoFunctionException(ctx.func.getStart());
		}

		String visited = this.visit(ctx.func);

		if (visited.isEmpty()) {
			return "";
		}

		return "call " + visited;
	}

	@Override
	public String visitReturnStatement(ReturnStatementContext ctx) {
		String result = "return";

		if (ctx.expression() != null) {
			result += " " + this.visit(ctx.expression());
		} else {
			this.expressionType = "nothing";
		}

		if (!this.scope.isTypeCompatible(this.expressionType)) {
			throw new IncorrectReturnTypeFunctionException(ctx.getStart(),
					(FunctionSymbol) this.scope, this.expressionType);
		}

		this.hasReturn = true;

		return result;
	}

	@Override
	public String visitSetVariableStatement(SetVariableStatementContext ctx) {
		String nameExpression = this.visit(ctx.varName);
		Symbol variable = this.symbol;

		if (((VariableSymbol) variable).isConstant()) {
			throw new SetConstantVariableException(ctx.varName.getStart());
		}

		String result = "set " + nameExpression + "=";
		String operator = ctx.operator.getText();

		switch (operator) {
		case "/=":
		case "*=":
		case "-=":
		case "+=":
			result += nameExpression + operator.replace("=", "");
		}

		result += this.visit(ctx.value);

		if (!variable.isTypeCompatible(this.expressionType)) {
			throw new IncorrectVariableTypeException(ctx.varName.getStart(),
					variable.getType(), this.expressionType);
		}

		return result;
	}

	@Override
	public String visitLocalVariableStatement(LocalVariableStatementContext ctx) {
		String variableName = ctx.varName.getText();
		String variableType = this.visit(ctx.variableType());
		String array = (ctx.array != null) ? " array" : "";
		String result = "local " + variableType + array + " " + variableName;
		Symbol variable = this.scope.resolve(variableName,
				PrimitiveType.VARIABLE, false);

		this.symbol = variable;

		if (ctx.value != null) {
			if (ctx.array != null) {
				throw new InitializeArrayVariableException(ctx.varName);
			}

			result += "=" + this.visit(ctx.value);

			if (!variable.isTypeCompatible(this.expressionType)) {
				throw new IncorrectVariableTypeException(ctx.varName,
						variable.getType(), this.expressionType);
			}
		}

		return result;
	}

	@Override
	public String visitStatements(StatementsContext ctx) {
		Stack<String> variables = new Stack<String>();
		Stack<String> statements = new Stack<String>();
		Stack<String> result = new Stack<String>();
		String visited;

		for (StatementContext stat : ctx.statement()) {
			visited = this.visit(stat);

			if (visited == null) {
				continue;
			}

			if (stat.localVariableStatement() != null) {
				variables.push(visited);
			} else {
				statements.push(visited);
			}
		}

		result.addAll(variables);
		result.addAll(statements);

		return String.join(System.lineSeparator(), result);
	}

	@Override
	public String visitFunctionDefinition(FunctionDefinitionContext ctx) {
		String name = ctx.functionName.getText();
		String params = this.visit(ctx.parameters());
		String type = this.visit(ctx.returnType());

		boolean prevHasReturn = this.hasReturn;
		Symbol prevScope = this.scope;

		this.hasReturn = false;
		this.scope = this.scope.resolve(name, PrimitiveType.FUNCTION, true);

		if (ctx.visibility != null
				&& this.scope.getParent() == this.symbolVisitor
						.getGlobalScope()) {
			throw new NoScopeVisibilityException(ctx.functionName);
		}

		this.functionSorter.functionBeingDefined(this.scope.getFullName());

		String result = "function " + this.scope.getFullName() + " takes "
				+ params + " returns " + type + System.lineSeparator()
				+ this.visit(ctx.statements()) + System.lineSeparator();
		
		if (this.scope.getName().equals("main")) {
			for (Symbol init : this.initializerHandler.getInitializers()) {
				result += "call "
						+ ((InitializerContainerSymbol) init).getInitializer().getFullName()
						+ "()"
						+ System.lineSeparator();
			}
		}
		
		result += "endfunction";

		if (!type.equals("nothing")) {
			if (!this.hasReturn) {
				throw new NoReturnFunctionException(ctx.getStart(),
						(FunctionSymbol) this.scope);
			}
		}

		this.functionSorter.setFunctionBody(this.scope.getFullName(), result);

		this.hasReturn = prevHasReturn;
		this.scope = prevScope;

		return result;
	}

	@Override
	public String visitPropertyStatement(PropertyStatementContext ctx) {
		String variableName = ctx.propertyName.getText();
		String variableType = this.visit(ctx.variableType());
		Symbol variable = this.scope.resolve(variableName, PrimitiveType.VARIABLE, false);
		String result = variableType;

		if (((VariableSymbol) variable).isArray()) {
			result += " array ";
		} else {
			result += " ";
		}

		result += variable.getFullName();

		this.symbol = variable;

		if (ctx.value != null) {
			((VariableSymbol) variable).setValue(this.visit(ctx.value));
			
			if (((VariableSymbol) variable).isArray()) {
				if (variable instanceof PropertySymbol == false) {
					throw new InitializeArrayVariableException(ctx.propertyName);
				}
			} else {
				result += "=" + ((VariableSymbol) variable).getValue();
			}

			if (!variable.getType().equals(this.expressionType)) {
				throw new IncorrectVariableTypeException(ctx.propertyName,
						variable.getType(), this.expressionType);
			}
		}

		this.classGlobals.push(result);

		return result;
	}

	@Override
	public String visitMethodDefinition(MethodDefinitionContext ctx) {
		String name = ctx.methodName.getText();
		String params = this.visit(ctx.parameters());

		Symbol prevScope = this.scope;

		this.scope = this.scope.resolve(name, PrimitiveType.FUNCTION, true);

		if (!((MethodSymbol) this.scope).isStatic()) {
			if (params.equals("nothing")) {
				params = "integer this";
			} else {
				params = "integer this," + params;
			}
		}

		this.functionSorter.functionBeingDefined(this.scope.getFullName());

		String result = "function " + this.scope.getFullName() + " takes "
				+ params + " returns " + this.visit(ctx.returnType())
				+ System.lineSeparator() + this.visit(ctx.statements())
				+ System.lineSeparator() + "endfunction";

		this.functionSorter.setFunctionBody(this.scope.getFullName(), result);

		this.scope = prevScope;

		return result;
	}
	
	@Override
	public String visitExtendList(ExtendListContext ctx) {
		String className = this.scope.getName();
		
		if (!ctx.getText().equals("array")) {
			Symbol extendSymbol;
			
			for (TerminalNode _extend : ctx.ID()) {
				extendSymbol = this.scope.resolve(
					_extend.getText(),
					PrimitiveType.CLASS,
					true
				);
				
				if (extendSymbol instanceof InterfaceSymbol) {
					if (((ClassSymbol) this.scope).getSuper() != null) {
						throw new TooManyExtendClassException(
							ctx.getStart(),
							className,
							ctx.ID().size()
						);
					}
					
					this.scope.addChild(extendSymbol);
				}
			}
		}
		
		return "";
	}

	@Override
	public String visitClassDefinition(ClassDefinitionContext ctx) {
		String name = ctx.className.getText();
		LinkedList<String> result = new LinkedList<String>();
		ClassDefaultAllocator cda = null;
		String visited;

		Symbol prevScope = this.scope;
		this.scope = this.scope.resolve(name, PrimitiveType.CLASS, true);
		
		Symbol initializer = this.scope.resolve("onInit", null, false);
		((InitializerContainerSymbol) this.scope).setInitializer(initializer);
		this.initializerHandler.add(this.scope);
		
		if (ctx.extendList() != null) {
			this.visit(ctx.extendList());
			
			if (!((ClassSymbol) this.scope).hasDefinedAllAbstractMethods()) {
				throw new ImplementAbstractMethodClassException(
					ctx.className,
					name
				);
			}
		} else {
			cda = new ClassDefaultAllocator(this.scope);
		}
		
		for (ClassStatementsContext classStat : ctx.classStatements()) {
			visited = this.visit(classStat);

			if (visited != null) {
				result.add(visited);
			}
		}
		
		if (cda != null) {
			this.functionSorter.functionBeingDefined(cda.getSetPropertiesName());
			this.functionSorter.setFunctionBody(cda.getSetPropertiesName(), cda.getSetProperties());
			
			this.functionSorter.functionBeingDefined(cda.getAllocatorName());
			this.functionSorter.setFunctionBody(cda.getAllocatorName(), cda.getAllocator());

			this.functionSorter.functionBeingDefined(cda.getDeallocatorName());
			this.functionSorter.setFunctionBody(cda.getDeallocatorName(), cda.getDeallocator());

			this.classGlobals.addAll(cda.getGlobals());
			
			result.addFirst(cda.getSetProperties());
			result.addFirst(cda.getAllocator());
			result.addFirst(cda.getDeallocator());
		}

		this.scope = prevScope;

		return String.join(System.lineSeparator(), result);
	}

	@Override
	public String visitGlobalVariableStatement(
			GlobalVariableStatementContext ctx) {
		String name = ctx.varName.getText();
		String variableType = this.visit(ctx.variableType());
		String array = (ctx.array != null) ? " array" : "";
		Symbol variable = this.scope.resolve(name, PrimitiveType.VARIABLE,
				false);

		if (ctx.visibility != null && this.scope.getName() == null) {
			throw new NoScopeVisibilityException(ctx.varName);
		}

		this.symbol = variable;

		String result = variableType + array + " " + variable.getFullName();

		if (ctx.value != null) {
			if (ctx.array != null) {
				throw new InitializeArrayVariableException(ctx.varName);
			}

			result += "=" + this.visit(ctx.value);

			if (!variable.getType().equals(this.expressionType)) {
				if (!variable.isTypeCompatible(this.expressionType)) {
					throw new IncorrectVariableTypeException(ctx.varName,
							variable.getType(), this.expressionType);
				}
			}
		}

		if (ctx.CONSTANT() != null) {
			result = "constant " + result;
		}

		this.globalsBlock.push(result);

		return result;
	}

	@Override
	public String visitInitializer(InitializerContext ctx) {
		Symbol initializer = this.scope.resolve(
			ctx.funcName.getText(),
			PrimitiveType.FUNCTION,
			false
		);
		
		((InitializerContainerSymbol) this.scope).setInitializer(initializer);
		
		return "";
	}
	
	@Override
	public String visitLibraryDefinition(LibraryDefinitionContext ctx) {
		String name = ctx.libraryName.getText();
		Stack<String> result = new Stack<String>();
		String visited;

		Symbol prevScope = this.scope;

		this.scope = this.scope.resolve(name, PrimitiveType.LIBRARY, false);
				
		if (ctx.requirements() != null) {
			for (TerminalNode req : ctx.requirements().ID()) {
				this.scope.addChild(
					this.symbolVisitor.getGlobalScope().resolve(
						req.getText(), PrimitiveType.LIBRARY, false
					)
				);
			}
		}
		
		if (ctx.initializer() != null) {
			this.visit(ctx.initializer());
		}
		
		this.initializerHandler.add(this.scope);

		for (LibraryStatementsContext library : ctx.libraryStatements()) {
			visited = this.visit(library);

			if (visited != null) {
				result.push(visited);
			}
		}

		this.scope = prevScope;

		return String.join(System.lineSeparator(), result);
	}

	@Override
	public String visitInit(InitContext ctx) {
		Stack<String> result = new Stack<String>();
		Stack<String> dummyGlobals = this.functionSorter.getDummyGlobals();

		for (AltInitContext alt : ctx.altInit()) {
			this.visit(alt);
		}

		result.addAll(this.types);
		result.addAll(this.natives);

		this.globalsBlock.addAll(dummyGlobals);
		this.globalsBlock.addAll(this.classGlobals);

		if (this.globalsBlock.size() != 0) {
			result.push("globals");
			result.addAll(this.globalsBlock);
			result.push("endglobals");
		}

		result.addAll(this.functionSorter.getFunctions());

		return String.join(System.lineSeparator(), result);
	}

	public String getOutput() {
		return this.output;
	}

	public SymbolVisitor getSymbolVisitor() {
		return this.symbolVisitor;
	}

}
