package com.ruke.vrjassc.vrjassc;

import static org.junit.Assert.assertEquals;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.ruke.vrjassc.vrjassc.exception.InitializeArrayVariableException;
import com.ruke.vrjassc.vrjassc.exception.InvalidArrayVariableIndexException;
import com.ruke.vrjassc.vrjassc.exception.VariableIsNotArrayException;
import com.ruke.vrjassc.vrjassc.util.Compile;

public class LocalArrayVariableStatementTest {

	@Rule
	public ExpectedException expectedEx = ExpectedException.none();

	@Test
	public void defineArray() {
		Compile compile = new Compile();
		String code = "function foo takes nothing returns nothing" + System.lineSeparator()
				+ "local integer array bar" + System.lineSeparator()
				+ "endfunction";

		assertEquals(code, compile.run(code));
	}

	@Test
	public void setArray() {
		Compile compile = new Compile();
		String code = "function foo takes nothing returns nothing" + System.lineSeparator()
				+ "local integer array bar" + System.lineSeparator()
				+ "set bar[1+1]=2" + System.lineSeparator()
				+ "endfunction";

		assertEquals(code, compile.run(code));
	}

	@Test
	public void setNonArrayVariable() {
		Compile compile = new Compile();
		String code = "function foo takes nothing returns nothing\n"
				+ "local integer bar\n" + "set bar[1+1]=2\n" + "endfunction";

		expectedEx.expect(VariableIsNotArrayException.class);
		expectedEx.expectMessage("3:4 Variable <bar> is not an array");

		compile.run(code);
	}

	@Test
	public void setInvalidIndex() {
		Compile compile = new Compile();
		String code = "function foo takes nothing returns nothing\n"
				+ "local integer array bar\n" + "set bar[\"nope\"]=2\n"
				+ "endfunction";

		expectedEx.expect(InvalidArrayVariableIndexException.class);
		expectedEx.expectMessage("3:8 Invalid index (only integer type)");

		compile.run(code);
	}

	@Test
	public void useInvalidIndex() {
		Compile compile = new Compile();
		String code = "function foo takes nothing returns nothing\n"
				+ "local integer array bar\n" + "set bar[1]=bar[\"nope\"]\n"
				+ "endfunction";

		expectedEx.expect(InvalidArrayVariableIndexException.class);
		expectedEx.expectMessage("3:15 Invalid index (only integer type)");

		compile.run(code);
	}

	@Test
	public void initializeAtDeclaration() {
		Compile compile = new Compile();
		String code = "function foo takes nothing returns nothing\n"
				+ "local integer array bar = 2\n" + "endfunction";

		expectedEx.expect(InitializeArrayVariableException.class);
		expectedEx
				.expectMessage("2:20 Arrays can not be initialized on declaration");

		compile.run(code);
	}

}
