package com.ruke.vrjassc.vrjassc;

import static org.junit.Assert.assertEquals;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.ruke.vrjassc.vrjassc.exception.EqualNotEqualComparisonException;
import com.ruke.vrjassc.vrjassc.exception.LessGreaterComparisonException;
import com.ruke.vrjassc.vrjassc.util.Compile;

public class ComparisonExpressionTest {

	@Rule
	public ExpectedException expectedEx = ExpectedException.none();

	@Test
	public void correct() {
		Compile compile = new Compile();
		String code = "function foo takes nothing returns nothing\n"
				+ "local boolean bar\n" + "set bar=1==1\n" + "set bar=1!=1\n"
				+ "set bar=1>1\n" + "set bar=1>=1\n" + "set bar=1<1\n"
				+ "set bar=1<=1\n" + "endfunction";

		assertEquals(code, compile.run(code));
	}

	@Test
	public void incorrectLessThan() {
		Compile compile = new Compile();
		String code = "function foo takes nothing returns nothing\n"
				+ "local boolean bar\n" + "set bar=2<\"nope\"\n"
				+ "endfunction";

		expectedEx.expect(LessGreaterComparisonException.class);
		expectedEx
				.expectMessage("3:10 Lower and greater than comparison can only be used with integers and reals");

		compile.run(code);
	}

	@Test
	public void incorrectLessThanOrEqual() {
		Compile compile = new Compile();
		String code = "function foo takes nothing returns nothing\n"
				+ "local boolean bar\n" + "set bar=2<=\"nope\"\n"
				+ "endfunction";

		expectedEx.expect(LessGreaterComparisonException.class);
		expectedEx
				.expectMessage("3:11 Lower and greater than comparison can only be used with integers and reals");

		compile.run(code);
	}

	@Test
	public void incorrectGreaterThan() {
		Compile compile = new Compile();
		String code = "function foo takes nothing returns nothing\n"
				+ "local boolean bar\n" + "set bar=2>\"nope\"\n"
				+ "endfunction";

		expectedEx.expect(LessGreaterComparisonException.class);
		expectedEx
				.expectMessage("3:10 Lower and greater than comparison can only be used with integers and reals");

		compile.run(code);
	}

	@Test
	public void incorrectGreaterThanOrEqual() {
		Compile compile = new Compile();
		String code = "function foo takes nothing returns nothing\n"
				+ "local boolean bar\n" + "set bar=2>=\"nope\"\n"
				+ "endfunction";

		expectedEx.expect(LessGreaterComparisonException.class);
		expectedEx
				.expectMessage("3:11 Lower and greater than comparison can only be used with integers and reals");

		compile.run(code);
	}

	@Test
	public void incorrectEqual() {
		Compile compile = new Compile();
		String code = "function foo takes nothing returns nothing\n"
				+ "local boolean bar\n" + "set bar=2==\"nope\"\n"
				+ "endfunction";

		expectedEx.expect(EqualNotEqualComparisonException.class);
		expectedEx
				.expectMessage("3:8 Equal than and not equal than comparison can only be interchangeable with integers and reals");

		compile.run(code);
	}

	@Test
	public void incorrectNotEqual() {
		Compile compile = new Compile();
		String code = "function foo takes nothing returns nothing\n"
				+ "local boolean bar\n" + "set bar=2!=\"nope\"\n"
				+ "endfunction";

		expectedEx.expect(EqualNotEqualComparisonException.class);
		expectedEx
				.expectMessage("3:8 Equal than and not equal than comparison can only be interchangeable with integers and reals");

		compile.run(code);
	}

}
