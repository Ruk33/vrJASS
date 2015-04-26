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
import exception.InvalidBooleanException;
import exception.LessGreaterComparisonException;
import exception.LogicalException;
import exception.MathematicalExpressionException;
import exception.NoFunctionException;
import exception.NoReturnFunctionException;
import exception.IncorrectArgumentTypeFunctionCallException;
import exception.IncorrectVariableTypeException;
import exception.NoScopeVisibilityException;
import exception.SetConstantVariableException;
import exception.TooFewArgumentsFunctionCallException;
import exception.TooManyArgumentsFunctionCallException;
import exception.UndefinedFunctionException;
import exception.UndefinedMethodException;
import exception.UndefinedPropertyException;
import exception.UndefinedVariableException;
import exception.VariableIsNotArrayException;
import symbol.ClassMemberSymbol;
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
import antlr4.vrjassParser.BooleanContext;
import antlr4.vrjassParser.ClassDefinitionContext;
import antlr4.vrjassParser.ClassStatementsContext;
import antlr4.vrjassParser.ComparisonContext;
import antlr4.vrjassParser.DivContext;
import antlr4.vrjassParser.ElseIfStatementContext;
import antlr4.vrjassParser.ElseStatementContext;
import antlr4.vrjassParser.ExitwhenStatementContext;
import antlr4.vrjassParser.FunctionDefinitionContext;
import antlr4.vrjassParser.FunctionExpressionContext;
import antlr4.vrjassParser.FunctionStatementContext;
import antlr4.vrjassParser.GlobalVariableStatementContext;
import antlr4.vrjassParser.GlobalsContext;
import antlr4.vrjassParser.IfStatementContext;
import antlr4.vrjassParser.InitContext;
import antlr4.vrjassParser.IntegerContext;
import antlr4.vrjassParser.LibraryDefinitionContext;
import antlr4.vrjassParser.LibraryStatementsContext;
import antlr4.vrjassParser.LocalVariableStatementContext;
import antlr4.vrjassParser.LogicalContext;
import antlr4.vrjassParser.LoopStatementContext;
import antlr4.vrjassParser.LoopStatementsContext;
import antlr4.vrjassParser.MemberContext;
import antlr4.vrjassParser.MethodDefinitionContext;
import antlr4.vrjassParser.MinusContext;
import antlr4.vrjassParser.MultContext;
import antlr4.vrjassParser.NativeDefinitionContext;
import antlr4.vrjassParser.ParameterContext;
import antlr4.vrjassParser.ParametersContext;
import antlr4.vrjassParser.ParenthesisContext;
import antlr4.vrjassParser.PlusContext;
import antlr4.vrjassParser.PropertyStatementContext;
import antlr4.vrjassParser.ReturnStatementContext;
import antlr4.vrjassParser.ReturnTypeContext;
import antlr4.vrjassParser.SetVariableStatementContext;
import antlr4.vrjassParser.StatementContext;
import antlr4.vrjassParser.StatementsContext;
import antlr4.vrjassParser.StringContext;
import antlr4.vrjassParser.ThisContext;
import antlr4.vrjassParser.TypeDefinitionContext;
import antlr4.vrjassParser.VariableContext;
import antlr4.vrjassParser.VariableTypeContext;

public class MainVisitor extends vrjassBaseVisitor<String> {
	
	protected Stack<String> types;
	
	protected Stack<String> globalsBlock;
	
	protected Stack<String> classGlobals;
	
	protected Stack<String> natives;
	
	protected Stack<String> requiredLibraries;
	
	protected FunctionSorter functionSorter;
	
	protected Prefix prefixer;
	
	protected SymbolVisitor symbolVisitor;
	
	protected Symbol scope;
	
	protected Symbol _class;
	
	protected Symbol symbol;
	
	protected boolean hasReturn;
	
	protected String expressionType;
	
	protected String output;
	
	public MainVisitor(vrjassParser parser) {
		this.types = new Stack<String>();
		this.globalsBlock = new Stack<String>();
		this.classGlobals = new Stack<String>();
		this.natives = new Stack<String>();
		this.functionSorter = new FunctionSorter();
		this.requiredLibraries = new Stack<String>();
		
		this.prefixer = new Prefix();
		this.symbolVisitor = new SymbolVisitor(this);
		this.scope = this.symbolVisitor.getGlobalScope();
		
		this.symbolVisitor.visit(parser.init());
		parser.reset();
		
		this.output = this.visit(parser.init());
	}
	
	public Prefix getPrefixer() {
		return this.prefixer;
	}
	
	public Visibility getVisibility(Token visibility) {
		if (visibility == null) {
			return null;
		}
		
		if (visibility.getText().equals("private")) {
			return Visibility.PRIVATE;
		}
		
		return Visibility.PUBLIC;
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
		String type = ctx.returnType().getText();
		
		String result = "native " + name + " takes " + params + " returns " + type;
		
		if (ctx.CONSTANT() != null) {
			result = "constant " + result;
		}
		
		this.natives.push(result);
		return result;
	}
	
	@Override
	public String visitExitwhenStatement(ExitwhenStatementContext ctx) {
		String expression = this.visit(ctx.expression());
		
		if (!this.expressionType.equals("boolean")) {
			throw new InvalidBooleanException(
				ctx.expression().getStart(), this.expressionType
			);
		}
		
		return "exitwhen (" + expression + ")";
	}
	
	@Override
	public String visitLoopStatement(LoopStatementContext ctx) {
		Stack<String> result = new Stack<String>();
		String visited;
		
		result.push("loop");
		
		for (LoopStatementsContext statement : ctx.loopStatements()) {
			visited = this.visit(statement);
			
			if (visited != null) {
				result.push(visited);
			}
		}
		
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
	public String visitElseIfStatement(ElseIfStatementContext ctx) {
		Stack<String> result = new Stack<String>();
		String visited = this.visit(ctx.expression());
		
		if (this.expressionType.equals("integer")) {
			visited += "!=0";
			this.expressionType = "boolean";
		}
		
		if (!this.expressionType.equals("boolean")) {
			throw new InvalidBooleanException(
				ctx.expression().getStart(),
				this.expressionType
			);
		}
		
		result.push("elseif (" + visited + ") then");
		
		visited = this.visit(ctx.statements());
		
		if (visited != null && !visited.isEmpty()) {
			result.push(visited);
		}
		
		return String.join(System.lineSeparator(), result);
	}
	
	@Override
	public String visitIfStatement(IfStatementContext ctx) {
		Stack<String> result = new Stack<String>();
		String visited = this.visit(ctx.expression());
		
		if (this.expressionType.equals("integer")) {
			visited += "!=0";
			this.expressionType = "boolean";
		}
		
		if (!this.expressionType.equals("boolean")) {
			throw new InvalidBooleanException(
				ctx.expression().getStart(),
				this.expressionType
			);
		}
		
		result.push("if (" + visited + ") then");
		
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
		this.expressionType = this.scope.resolve("this", PrimitiveType.VARIABLE, false).getType();
		return "this";
	}
	
	@Override
	public String visitMember(MemberContext ctx) {
		String left = this.visit(ctx.left);
		String leftName = ctx.left.getText();
		String leftType = this.expressionType;
		Symbol member = null;
		String result;
		
		if (leftName.equals("this")) {
			this._class = this.scope.resolve("this", PrimitiveType.VARIABLE, false).getParent().getParent();
		} else {
			this._class = this.scope.resolve(leftType, PrimitiveType.CLASS, true);
		}
		
		String right = this.visit(ctx.right);
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
		
		this._class = null;
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
					throw new InvalidArrayVariableIndexException(ctx.index.getStart());
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
		boolean leftIsNumeric =
				"real".equals(leftType) ||
				"integer".equals(leftType);
		
		String right = this.visit(ctx.right);
		String rightType = this.expressionType;
		boolean rightIsNumeric =
				"real".equals(rightType) ||
				"integer".equals(rightType);
		
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
		FunctionSymbol function = (FunctionSymbol) this.scope;
		
		Stack<Symbol> params = function.getParams();
		Stack<String> args = new Stack<String>();
		
		String prevExprType = this.expressionType;
		
		int i = 0;
		
		if (params.size() == 0) {
			return "";
		}
		
		for (ArgumentContext arg : ctx.argument()) {
			args.push(this.visit(arg));
			
			if (!params.get(i).getType().equals(this.expressionType)) {
				throw new IncorrectArgumentTypeFunctionCallException(
					arg.getStart(),
					params.get(i).getType(),
					this.expressionType
				);
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
		int argumentsCount = ctx.arguments().argument().size();
		Symbol prevScope = this.scope;
		
		FunctionSymbol function = (FunctionSymbol) this.scope.resolve(
			name, PrimitiveType.FUNCTION, true
		);
		
		if (function == null) {
			if (this._class != null) {
				throw new UndefinedMethodException(ctx.functionName, this._class);
			} else {
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
		
		// if argument is nothing, remove it
		if (ctx.arguments().argument(0).getText().isEmpty()) {
			argumentsCount--;
		}
		
		if (argumentsCount > funcParamCount) {
			throw new TooManyArgumentsFunctionCallException(ctx.functionName);
		}
		
		if (argumentsCount < funcParamCount) {
			throw new TooFewArgumentsFunctionCallException(ctx.functionName);
		}
		
		this.symbol = function;
		this.scope = function;
		
		String finalName = function.getFullName();
		String prevFuncName = prevScope.getFullName();
		
		if (!this.functionSorter.functionBeingCalled(function, prevFuncName)) {
			finalName = this.functionSorter.getDummyPrefix() + finalName;
		}
		
		String args = "";
		
		if (argumentsCount > 0) {
			args = this.visit(ctx.arguments());
		}
		
		if (this.scope instanceof MethodSymbol) {
			if (!((MethodSymbol) this.scope).isStatic()) {
				if (args.equals("")) {
					args = "this";
				} else {
					args = "this," + args;
				}
			}
		}
		
		this.expressionType = this.scope.getType();
		this.scope = prevScope;

		return finalName + "(" + args + ")";
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
		
		if (!this.scope.getType().equals(this.expressionType)) {
			throw new IncorrectReturnTypeFunctionException(
				ctx.getStart(),
				(FunctionSymbol) this.scope,
				this.expressionType
			);
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
		
		if (!variable.getType().equals(this.expressionType)) {
			throw new IncorrectVariableTypeException(
				ctx.varName.getStart(),
				variable.getType(),
				this.expressionType
			);
		}
		
		return result;
	}
	
	@Override
	public String visitLocalVariableStatement(LocalVariableStatementContext ctx) {
		String variableName = ctx.varName.getText();
		String variableType = this.visit(ctx.variableType());
		String array = (ctx.array != null) ? " array" : "";
		String result = "local " + variableType + array + " " + variableName;
		Symbol variable = this.scope.resolve(variableName, PrimitiveType.VARIABLE, false);
		
		this.symbol = variable;
		
		if (ctx.value != null) {
			if (ctx.array != null) {
				throw new InitializeArrayVariableException(ctx.varName);
			}
			
			result += "=" + this.visit(ctx.value);
			
			if (!variable.getType().equals(this.expressionType)) {
				throw new IncorrectVariableTypeException(
					ctx.varName,
					variable.getType(),
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
		
		boolean prevHasReturn = this.hasReturn;
		Symbol prevScope = this.scope;
		
		this.hasReturn = false;
		this.scope = this.scope.resolve(name, PrimitiveType.FUNCTION, true);
		
		if (ctx.visibility != null && this.scope.getParent() == this.symbolVisitor.getGlobalScope()) {
			throw new NoScopeVisibilityException(ctx.functionName);
		}
				
		this.functionSorter.functionBeingDefined(this.scope.getFullName());
		
		String result =
				"function " + this.scope.getFullName() +
				" takes " + params +
				" returns " + type + System.lineSeparator() +
				this.visit(ctx.statements()) + System.lineSeparator() +
				"endfunction";
		
		if (!type.equals("nothing")) {
			if (!this.hasReturn) {
				throw new NoReturnFunctionException(
					ctx.getStart(), (FunctionSymbol) this.scope
				);
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
			if (((VariableSymbol) variable).isArray()) {
				throw new InitializeArrayVariableException(ctx.propertyName);
			}
			
			result += "=" + this.visit(ctx.value);
			
			if (!variable.getType().equals(this.expressionType)) {
				throw new IncorrectVariableTypeException(
					ctx.propertyName,
					variable.getType(),
					this.expressionType
				);
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
		
		String result =
			"function " + this.scope.getFullName() +
			" takes " + params +
			" returns " + this.visit(ctx.returnType()) + System.lineSeparator() +
			this.visit(ctx.statements()) + System.lineSeparator() +
			"endfunction";
		
		this.functionSorter.setFunctionBody(this.scope.getFullName(), result);
		
		this.scope = prevScope;
		
		return result;
	}
	
	@Override
	public String visitClassDefinition(ClassDefinitionContext ctx) {
		String name = ctx.className.getText();
		String extendsFrom = "";
		boolean extendsArray = false;
		Stack<String> result = new Stack<String>();
		String visited;
		
		Symbol prevScope = this.scope;
		
		ClassDefaultAllocator cda = new ClassDefaultAllocator(name);
		
		if (ctx.extendName != null) {
			extendsFrom = ctx.extendName.getText();
		}
		
		extendsArray = extendsFrom.equals("array");

		if (!extendsArray && extendsFrom.isEmpty()) {
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
		
		this.scope = this.scope.resolve(name, PrimitiveType.CLASS, true);
		
		if (!extendsArray && !extendsFrom.isEmpty()) {
			this.scope.addChild(this.scope.resolve(extendsFrom, PrimitiveType.CLASS, true));
		}
		
		for (ClassStatementsContext classStat : ctx.classStatements()) {
			visited = this.visit(classStat);
			
			if (visited != null) {
				result.push(visited);
			}
		}
		
		this.scope = prevScope;
		
		return String.join(System.lineSeparator(), result);
	}
	
	@Override
	public String visitGlobalVariableStatement(GlobalVariableStatementContext ctx) {
		String name = ctx.varName.getText();
		String variableType = this.visit(ctx.variableType());
		String array = (ctx.array != null) ? " array" : "";
		Symbol variable = this.scope.resolve(name, PrimitiveType.VARIABLE, false);
		
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
				throw new IncorrectVariableTypeException(
					ctx.varName,
					variable.getType(),
					this.expressionType
				);
			}
		}
		
		if (ctx.CONSTANT() != null) {
			result = "constant " + result;
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
		String name = ctx.libraryName.getText();
		Stack<String> prevRequiredLibraries = this.requiredLibraries;
		Stack<String> result = new Stack<String>();
		String visited;
		
		Symbol prevScope = this.scope;
		
		this.scope = this.scope.resolve(name, PrimitiveType.LIBRARY, false);
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
		
		this.scope = prevScope;
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
		
		result.addAll(this.types);
		
		this.globalsBlock.addAll(dummyGlobals);
		this.globalsBlock.addAll(this.classGlobals);
		
		if (this.globalsBlock.size() != 0) {
			result.push("globals");
			result.addAll(this.globalsBlock);
			result.push("endglobals");
		}
		
		result.addAll(this.natives);
		result.addAll(this.functionSorter.getFunctions());
		
		return String.join(System.lineSeparator(), result);
	}

	public String getOutput() {
		return this.output;
	}
	
}
