package com.ruke.vrjassc.vrjassc.translation.expression;

import static org.junit.Assert.*;

import org.junit.Test;

import com.ruke.vrjassc.translator.expression.ElseIfStatement;
import com.ruke.vrjassc.translator.expression.ElseStatement;
import com.ruke.vrjassc.translator.expression.Expression;
import com.ruke.vrjassc.translator.expression.FunctionDefinition;
import com.ruke.vrjassc.translator.expression.FunctionStatement;
import com.ruke.vrjassc.translator.expression.IfStatement;
import com.ruke.vrjassc.translator.expression.RawExpression;
import com.ruke.vrjassc.translator.expression.StatementBody;
import com.ruke.vrjassc.translator.expression.VariableStatement;
import com.ruke.vrjassc.vrjassc.symbol.BuiltInTypeSymbol;
import com.ruke.vrjassc.vrjassc.symbol.FunctionSymbol;
import com.ruke.vrjassc.vrjassc.symbol.LocalVariableSymbol;
import com.ruke.vrjassc.vrjassc.symbol.Symbol;

public class IfStatementTest {

	@Test
	public void _else() {
		IfStatement _if = new IfStatement(new RawExpression("false"));
		ElseStatement el = new ElseStatement();
		
		_if.add(el);
		
		assertEquals(
			"if false then\n"
			+ "else\n"
			+ "endif",
			_if.translate()
		);
	}
	
	@Test
	public void elseif() {
		FunctionDefinition f = new FunctionDefinition(new FunctionSymbol("foo", null, null));
		
		IfStatement _if = new IfStatement(new RawExpression("false"));
		ElseIfStatement elif = new ElseIfStatement(new RawExpression("true"));
		
		Symbol var = new LocalVariableSymbol("bar", null, null);
		var.setType(new BuiltInTypeSymbol("integer", null, null));
		
		elif.add(new VariableStatement(var,  null));
		_if.add(elif);
		
		f.add(_if);
		
		assertEquals(
			"function foo takes nothing returns nothing\n"
				+ "local integer bar\n"
				+ "if false then\n"
					+ ""
				+ "elseif true then\n"
					+ ""
				+ "endif\n"
			+ "endfunction",
			f.translate()
		);
	}
	
	@Test
	public void test() {
		StatementBody func = new FunctionDefinition(new FunctionSymbol("foo", null, null));
		
		Expression cond = new RawExpression("true");
		IfStatement ifstat = new IfStatement(cond);
		
		assertEquals("if true then\nendif", ifstat.translate());
		
		Symbol baz = new LocalVariableSymbol("baz", null, null);
		baz.setType(new BuiltInTypeSymbol("integer", null, null));
		
		ifstat.add(new FunctionStatement(new RawExpression("foo()")));
		ifstat.add(new VariableStatement(baz, new RawExpression("bar")));
		
		func.add(ifstat);
		
		assertEquals(
			"function foo takes nothing returns nothing\n"
				+ "local integer baz\n"
				+ "if true then\n"
					+ "call foo()\n"
					+ "set baz=bar\n"
				+ "endif\n"
			+ "endfunction",
			func.translate()
		);
	}

}
