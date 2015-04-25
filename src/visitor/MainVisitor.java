package visitor;

import java.util.Stack;

import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;

import exception.ElementNoAccessException;
import exception.EqualNotEqualComparisonException;
import exception.IncorrectReturnTypeFunctionException;
import exception.InitializeArrayVariableException;
import exception.InvalidArrayVariableIndexException;
import exception.LessGreaterComparisonException;
import exception.LogicalException;
import exception.MathematicalExpressionException;
import exception.NoFunctionException;
import exception.NoReturnFunctionException;
import exception.IncorrectArgumentTypeFunctionCallException;
import exception.IncorrectVariableTypeException;
import exception.NoScopeVisibilityException;
import exception.TooFewArgumentsFunctionCallException;
import exception.TooManyArgumentsFunctionCallException;
import exception.UndefinedFunctionException;
import exception.UndefinedVariableException;
import exception.VariableIsNotArrayException;
import symbol.FunctionSymbol;
import symbol.MethodSymbol;
import symbol.PrimitiveType;
import symbol.Symbol;
import symbol.VariableSymbol;
import symbol.Visibility;
import util.ClassDefaultAllocator;
import util.FunctionSorter;
import util.Prefix;
import util.VariableTypeDetector;
import antlr4.vrjassBaseVisitor;
import antlr4.vrjassParser;
import antlr4.vrjassParser.AltInitContext;
import antlr4.vrjassParser.ArgumentContext;
import antlr4.vrjassParser.ArgumentsContext;
import antlr4.vrjassParser.ClassDefinitionContext;
import antlr4.vrjassParser.ClassStatementsContext;
import antlr4.vrjassParser.ComparisonContext;
import antlr4.vrjassParser.DivContext;
import antlr4.vrjassParser.FunctionDefinitionContext;
import antlr4.vrjassParser.FunctionExpressionContext;
import antlr4.vrjassParser.FunctionStatementContext;
import antlr4.vrjassParser.GlobalVariableStatementContext;
import antlr4.vrjassParser.GlobalsContext;
import antlr4.vrjassParser.InitContext;
import antlr4.vrjassParser.IntegerContext;
import antlr4.vrjassParser.LibraryDefinitionContext;
import antlr4.vrjassParser.LibraryStatementsContext;
import antlr4.vrjassParser.LocalVariableStatementContext;
import antlr4.vrjassParser.LogicalContext;
import antlr4.vrjassParser.MethodDefinitionContext;
import antlr4.vrjassParser.MinusContext;
import antlr4.vrjassParser.MultContext;
import antlr4.vrjassParser.ParameterContext;
import antlr4.vrjassParser.ParametersContext;
import antlr4.vrjassParser.ParenthesisContext;
import antlr4.vrjassParser.PlusContext;
import antlr4.vrjassParser.ReturnStatementContext;
import antlr4.vrjassParser.ReturnTypeContext;
import antlr4.vrjassParser.SetVariableStatementContext;
import antlr4.vrjassParser.StatementContext;
import antlr4.vrjassParser.StatementsContext;
import antlr4.vrjassParser.StringContext;
import antlr4.vrjassParser.VariableContext;
import antlr4.vrjassParser.VariableTypeContext;

public class MainVisitor extends vrjassBaseVisitor<String> {
	
	protected Stack<String> globalsBlock;
	
	protected Stack<String> classGlobals;
	
	protected Stack<String> requiredLibraries;
	
	protected FunctionSorter functionSorter;
	
	protected Prefix prefixer;
	
	protected SymbolVisitor symbolVisitor;
	
	protected Symbol scope;
	
	protected Symbol symbol;
	
	protected boolean hasReturn;
	
	protected String expressionType;
	
	protected String output;
	
	public MainVisitor(vrjassParser parser) {
		this.globalsBlock = new Stack<String>();
		this.classGlobals = new Stack<String>();
		this.functionSorter = new FunctionSorter();
		this.requiredLibraries = new Stack<String>();
		
		this.prefixer = new Prefix();
		
		this.symbolVisitor = new SymbolVisitor(this);
		
		this.symbolVisitor.visit(parser.init());
		parser.reset();
		
		this.output = this.visit(parser.init());
	}
	
	public Prefix getPrefixer() {
		return this.prefixer;
	}
	
	public Visibility getVisibility(Token visibility) {
		if (visibility == null) {
			return Visibility.PUBLIC;
		}
		
		if (visibility.getText().equals("private")) {
			return Visibility.PRIVATE;
		}
		
		return Visibility.PUBLIC;
	}
			
	@Override
	public String visitVariable(VariableContext ctx) {
		String name = ctx.varName.getText();
		String indexType = (ctx.index != null) ? this.visit(ctx.index) : null;
		
		VariableSymbol variable = (VariableSymbol) this.scope.resolve(
			name, PrimitiveType.VARIABLE, true
		);
		
		if (variable == null) {
			throw new UndefinedVariableException(ctx.varName);
		}
		
		if (!this.scope.hasAccess(variable)) {
			throw new ElementNoAccessException(ctx.varName);
		}
		
		if (ctx.index != null && !"integer".equals(indexType)) {
			throw new InvalidArrayVariableIndexException(ctx.index.getStart());
		}
		
		this.expressionType = variable.getType();
		return variable.getFullName();
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
		boolean leftIsNumeric = "real".equals(leftType) || "integer".equals(leftType);
		
		String right = this.visit(ctx.right);
		String rightType = this.expressionType;
		boolean rightIsNumeric = "real".equals(rightType) || "integer".equals(rightType);
		
		if (!leftIsNumeric) {
			throw new MathematicalExpressionException(ctx.left.getStart());
		}
		
		if (!rightIsNumeric) {
			throw new MathematicalExpressionException(ctx.right.getStart());
		}
		
		this.expressionType = "integer";
		return left + '/' + right;
	}
	
	@Override
	public String visitMult(MultContext ctx) {
		String left = this.visit(ctx.left);
		String leftType = this.expressionType;
		boolean leftIsNumeric = "real".equals(leftType) || "integer".equals(leftType);
		
		String right = this.visit(ctx.right);
		String rightType = this.expressionType;
		boolean rightIsNumeric = "real".equals(rightType) || "integer".equals(rightType);
		
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
		boolean leftIsNumeric = "real".equals(leftType) || "integer".equals(leftType);
		
		String right = this.visit(ctx.right);
		String rightType = this.expressionType;
		boolean rightIsNumeric = "real".equals(rightType) || "integer".equals(rightType);
		
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
		boolean leftIsNumeric = "real".equals(leftType) || "integer".equals(leftType);
		
		String right = this.visit(ctx.right);
		String rightType = this.expressionType;
		boolean rightIsNumeric = "real".equals(rightType) || "integer".equals(rightType);
		
		if (!leftIsNumeric) {
			throw new MathematicalExpressionException(ctx.left.getStart());
		}
		
		if (!rightIsNumeric) {
			throw new MathematicalExpressionException(ctx.right.getStart());
		}
		
		this.expressionType = "integer";
		return left + '+' + right;
	}
	
	@Override
	public String visitComparison(ComparisonContext ctx) {
		String left = this.visit(ctx.left);
		String leftType = this.expressionType;
		boolean leftIsNumeric = "real".equals(leftType) || "integer".equals(leftType);
		
		String right = this.visit(ctx.right);
		String rightType = this.expressionType;
		boolean rightIsNumeric = "real".equals(rightType) || "integer".equals(rightType);
		
		String operator = ctx.operator.getText();
		
		switch (operator) {
		case "==":
		case "!=":
			if (!leftType.equals(rightType)) {
				if (!leftIsNumeric || !rightIsNumeric) {
					throw new EqualNotEqualComparisonException(ctx.getStart());
				}
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
		boolean leftIsBoolean = "boolean".equals(leftType);
		
		String right = this.visit(ctx.right);
		String rightType = this.expressionType;
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
		Stack<VariableSymbol> params = this.function.getParams();
		Stack<String> args = new Stack<String>();
		String prevExprType = this.expressionType;
		int i = 0;
		
		for (ArgumentContext arg : ctx.argument()) {
			args.push(this.visit(arg));
			
			if (!params.get(i).getType().equals(this.expressionType)) {
				throw new IncorrectArgumentTypeFunctionCallException(
					arg.getStart(),
					params.get(i).getType(),
					this.expressionType
				);
			}
			
			this.expressionType = null;
		}
		
		this.expressionType = prevExprType;
		
		return String.join(",", args);
	}
	
	@Override
	public String visitReturnType(ReturnTypeContext ctx) {
		return ctx.getText();
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
		FunctionSymbol func = null;
		
		if (this.className != null) {
			func = this.getMethod(this.scopeName, this.className, name);
		}
		
		if (func == null) {
			func = this.getFunction(this.scopeName, name);
		}
		
		if (func == null) {
			throw new UndefinedFunctionException(ctx.functionName);
		}
		
		int argumentsCount = ctx.arguments().argument().size();
		
		FunctionSymbol prevFunction = this.function;
		
		if (!this.hasAccess(func, this.scopeName, this.requiredLibraries)) {
			throw new ElementNoAccessException(ctx.functionName);
		}
		
		if (argumentsCount > func.getParams().size()) {
			throw new TooManyArgumentsFunctionCallException(ctx.functionName);
		}
		
		if (argumentsCount < func.getParams().size()) {
			throw new TooFewArgumentsFunctionCallException(ctx.functionName);
		}
		
		this.function = func;
		
		String finalName = func.getName();
		String prevFuncName = prevFunction.getName();
		
		if (!this.functionSorter.functionBeingCalled(func, prevFuncName)) {
			finalName = this.functionSorter.getDummyPrefix() + finalName;
		}
		
		String args = this.visit(ctx.arguments());
		
		if (this.function instanceof MethodSymbol) {
			if (args.equals("")) {
				args = "this";
			} else {
				args = "this," + args;
			}
		}
		
		String result = finalName + "(" + args + ")";
		this.expressionType = this.function.getReturnType();
				
		this.function = prevFunction;
		
		return result;
	}
	
	@Override
	public String visitFunctionStatement(FunctionStatementContext ctx) {
		String exprType = ctx.func.getClass().getSimpleName();
		
		if (exprType.equals("MemberContext")) {
			int exprCount = ctx.func.getChildCount();
			ParseTree lastExpr = ctx.func.getChild(exprCount-1);
			exprType = lastExpr.getClass().getSimpleName();
		}

		if (!exprType.equals("FunctionContext")) {
			throw new NoFunctionException(ctx.func.getStart());
		}
		
		return "call " + this.visit(ctx.func);
	}
	
	@Override
	public String visitReturnStatement(ReturnStatementContext ctx) {
		String result = "return " + this.visit(ctx.expression());
		
		if (!this.function.getReturnType().equals(this.expressionType)) {
			throw new IncorrectReturnTypeFunctionException(
				ctx.getStart(),
				this.function,
				this.expressionType
			);
		}
		
		this.hasReturn = true;
		
		return result;
	}
	
	@Override
	public String visitSetVariableStatement(SetVariableStatementContext ctx) {
		String variableName = ctx.varName.getText();
		String index = (ctx.index == null) ? "" : "[" + this.visit(ctx.index) + "]";
		String indexType = this.expressionType;
		String operator = ctx.operator.getText();
		VariableSymbol prevVar = this.variable;
		String result;
		String scope;
		
		if (this.className != null) {
			scope = this.className;
		} else if (this.function != null) {
			scope = this.function.getName();
		} else {
			scope = this.scopeName;
		}
		
		this.variable = this.getLocalOrGlobalVariable(scope, variableName);
		
		if (this.variable == null) {
			throw new UndefinedVariableException(ctx.varName);
		}
		
		result = "set " + this.variable.getName() + index + "=";
		
		switch (operator) {
		case "/=":
		case "*=":
		case "-=":
		case "+=":
			result += this.variable.getName() + index + operator.replace("=", "");
		}
		
		result += this.visit(ctx.value);
		
		if (ctx.index != null && !this.variable.isArray()) {
			throw new VariableIsNotArrayException(ctx.varName);
		}
		
		if (ctx.index != null && !"integer".equals(indexType)) {
			throw new InvalidArrayVariableIndexException(ctx.index.getStart());
		}
		
		if (!this.variable.getType().equals(this.expressionType)) {
			throw new IncorrectVariableTypeException(
				ctx.varName,
				this.variable.getType(),
				this.expressionType
			);
		}
		
		this.variable = prevVar;
		
		return result;
	}
	
	@Override
	public String visitLocalVariableStatement(LocalVariableStatementContext ctx) {
		String variableName = ctx.varName.getText();
		String variableType = this.visit(ctx.variableType());
		String array = (ctx.array != null) ? " array" : "";
		String result = "local " + variableType + array + " " + variableName;
		
		if (ctx.value != null) {
			if (ctx.array != null) {
				throw new InitializeArrayVariableException(ctx.varName);
			}
			
			result += "=" + this.visit(ctx.value);
			
			if (!ctx.variableType().getText().equals(this.expressionType)) {
				throw new IncorrectVariableTypeException(
					ctx.varName,
					ctx.variableType().getText(),
					this.expressionType
				);
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
		FunctionSymbol prevFunc = this.function;
		boolean prevHasReturn = this.hasReturn;
		
		if (ctx.visibility != null && this.scopeName == null) {
			throw new NoScopeVisibilityException(ctx.functionName);
		}
		
		this.function = this.getFunction(this.scopeName, name);
		this.hasReturn = false;
		
		this.functionSorter.functionBeingDefined(this.function.getName());
		
		String result =
				"function " + this.function.getName() +
				" takes " + params +
				" returns " + type + System.lineSeparator() +
				this.visit(ctx.statements()) + System.lineSeparator() +
				"endfunction";
		
		if (!type.equals("nothing")) {
			if (!this.hasReturn) {
				throw new NoReturnFunctionException(ctx.getStart(), this.function);
			}
		}
		
		this.functionSorter.setFunctionBody(this.function.getName(), result);
		
		this.hasReturn = prevHasReturn;
		this.function = prevFunc;
		
		return result;
	}
	
	@Override
	public String visitMethodDefinition(MethodDefinitionContext ctx) {
		String name = ctx.methodName.getText();
		FunctionSymbol prevFunc = this.function;
		String params = this.visit(ctx.parameters());
		
		this.function = this.getMethod(this.scopeName, this.className, name);
		
		if (params.equals("nothing")) {
			params = "integer this";
		} else {
			params = "integer this," + params;
		}
		
		this.functionSorter.functionBeingDefined(this.function.getName());
		
		String result =
			"function " + this.function.getName() +
			" takes " + params +
			" returns " + this.visit(ctx.returnType()) + System.lineSeparator() +
			this.visit(ctx.statements()) + System.lineSeparator() +
			"endfunction";
		
		this.functionSorter.setFunctionBody(this.function.getName(), result);
		
		this.function = prevFunc;
		
		return result;
	}
	
	@Override
	public String visitClassDefinition(ClassDefinitionContext ctx) {
		String prevClassName = this.className;
		Stack<String> result = new Stack<String>();
		boolean extendsArray = false;
		String visited;
		
		String name = ctx.className.getText();
		ClassDefaultAllocator cda = new ClassDefaultAllocator(name);
		
		if (ctx.extendName != null) {
			extendsArray = ctx.extendName.getText().equals("array");
		}

		if (!extendsArray) {
			result.push(cda.getAllocator());
			result.push(cda.getDeallocator());
			
			this.functionSorter.functionBeingDefined(cda.getAllocatorName());
			this.functionSorter.setFunctionBody(
				cda.getAllocatorName(), cda.getAllocator()
			);
			
			this.functionSorter.functionBeingDefined(cda.getDeallocatorName());
			this.functionSorter.setFunctionBody(
				cda.getDeallocatorName(), cda.getDeallocator()
			);
			
			this.classGlobals.addAll(cda.getGlobals());
		}
		
		this.className = name;
		
		for (ClassStatementsContext classStat : ctx.classStatements()) {
			visited = this.visit(classStat);
			
			if (visited != null) {
				result.push(visited);
			}
		}
		
		this.className = prevClassName;
		
		return String.join(System.lineSeparator(), result);
	}
	
	@Override
	public String visitGlobalVariableStatement(
			GlobalVariableStatementContext ctx) {
		String prefix = this.prefixer.getForScope(ctx.visibility, this.scopeName);
		String variableType = this.visit(ctx.variableType());
		String array = (ctx.array != null) ? " array" : "";
		String variableName = prefix + ctx.varName.getText();
		
		if (ctx.visibility != null && this.scopeName == null) {
			throw new NoScopeVisibilityException(ctx.varName);
		}
		
		String result = variableType + array + " " + variableName;
		
		if (ctx.value != null) {
			if (ctx.array != null) {
				throw new InitializeArrayVariableException(ctx.varName);
			}
			
			result += "=" + this.visit(ctx.value);
			
			if (!ctx.variableType().getText().equals(this.expressionType)) {
				throw new IncorrectVariableTypeException(
					ctx.varName,
					ctx.variableType().getText(),
					this.expressionType
				);
			}
		}
		
		return result;
	}
	
	@Override
	public String visitGlobals(GlobalsContext ctx) {
		String visited;
		
		for (GlobalVariableStatementContext global : ctx.globalVariableStatement()) {
			visited = this.visit(global);
			
			if (visited != null) {
				this.globalsBlock.push(visited);
			}
		}
		
		return null;
	}
	
	@Override
	public String visitLibraryDefinition(LibraryDefinitionContext ctx) {
		String prevScopeName = this.scopeName;
		Stack<String> prevRequiredLibraries = this.requiredLibraries;
		Stack<String> result = new Stack<String>();
		String visited;
		
		this.scopeName = ctx.libraryName.getText();
		this.requiredLibraries = new Stack<String>();
		
		if (ctx.requirements() != null) {
			for (TerminalNode req : ctx.requirements().ID()) {
				this.requiredLibraries.push(req.getText());
			}
		}
		
		for (LibraryStatementsContext library : ctx.libraryStatements()) {
			visited = this.visit(library);
			
			if (visited != null) {
				result.push(visited);
			}
		}
		
		this.scopeName = prevScopeName;
		this.requiredLibraries = prevRequiredLibraries;
		
		return String.join(System.lineSeparator(), result);
	}
	
	@Override
	public String visitInit(InitContext ctx) {
		Stack<String> result = new Stack<String>();
		Stack<String> dummyGlobals = this.functionSorter.getDummyGlobals();
		
		for (AltInitContext alt : ctx.altInit()) {
			this.visit(alt);
		}
		
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
	
}
