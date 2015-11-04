package com.ruke.vrjassc.vrjassc.validator;

import org.junit.Test;

import com.ruke.vrjassc.vrjassc.exception.IncompatibleTypeException;
import com.ruke.vrjassc.vrjassc.exception.InvalidExtendTypeException;
import com.ruke.vrjassc.vrjassc.exception.InvalidImplementTypeException;
import com.ruke.vrjassc.vrjassc.exception.InvalidTypeException;
import com.ruke.vrjassc.vrjassc.exception.NoAccessException;
import com.ruke.vrjassc.vrjassc.exception.StaticNonStaticTypeException;
import com.ruke.vrjassc.vrjassc.exception.UndefinedSymbolException;
import com.ruke.vrjassc.vrjassc.util.TestHelper;

public class ClassTest extends TestHelper {
	
	@Test
	public void usingStaticIncorrectly() {
		this.expectedEx.expect(StaticNonStaticTypeException.class);
		this.expectedEx.expectMessage("6:9 Element <s_bar> is static");
		this.run("struct foo\n"
					+ "integer bar\n"
					+ "static integer s_bar\n"
					+ "method x takes nothing returns nothing\n"
						+ "set this.bar = 2\n"
						+ "set this.s_bar = 2\n"
					+ "endmethod\n"
				+ "endstruct");
	}
	
	@Test
	public void usingNonStaticIncorrectly() {
		this.expectedEx.expect(StaticNonStaticTypeException.class);
		this.expectedEx.expectMessage("6:8 Element <bar> is not static");
		this.run("struct foo\n"
					+ "integer bar\n"
					+ "static integer s_bar\n"
					+ "method x takes nothing returns nothing\n"
						+ "set foo.s_bar = 2\n"
						+ "set foo.bar = 2\n"
					+ "endmethod\n"
				+ "endstruct");
	}
	
	@Test
	public void userDefinedType() {
		this.expectedEx.expect(IncompatibleTypeException.class);
		this.expectedEx.expectMessage("10:17 Element <p> must have/return a value of type <person> but given <god>");
		this.run("struct chucknorris\n"
				+ "endstruct\n"
				
				+ "struct god extends chucknorris\n"
				+ "endstruct\n"
				
				+ "struct person\n"
				+ "endstruct\n"
				
				+ "function foo takes nothing returns nothing\n"
					+ "local chucknorris chuck\n"
					+ "local god g = chuck\n"
					+ "local person p = g\n"
				+ "endfunction");
	}
	
	@Test
	public void validImplementType() {
		this.expectedEx.expect(InvalidImplementTypeException.class);
		this.expectedEx.expectMessage("1:22 Element <integer> is not a valid implementable interface");
		this.run("struct foo implements integer\n"
				+ "endstruct");
	}
	
	@Test
	public void validExtendType() {
		this.expectedEx.expect(InvalidExtendTypeException.class);
		this.expectedEx.expectMessage("1:19 Element <integer> is not a valid extendable class");
		this.run("struct foo extends integer\n"
				+ "endstruct");
	}

	@Test
	public void undefinedType() {
		this.expectedEx.expect(UndefinedSymbolException.class);
		this.expectedEx.expectMessage("1:35 Element <bar> is not defined");
		this.run("function foo takes nothing returns bar\n"
				+ "endfunction");
	}
	
	@Test
	public void nonValidType() {
		this.expectedEx.expect(InvalidTypeException.class);
		this.expectedEx.expectMessage("3:35 <foo> is not a valid type");
		this.run("library foo\n"
				+ "endlibrary\n"
				+ "function bar takes nothing returns foo\n"
				+ "endfunction");
	}
	
	@Test
	public void extendingUndefinedType() {
		this.expectedEx.expect(UndefinedSymbolException.class);
		this.expectedEx.expectMessage("1:19 Element <bar> is not defined");
		this.run("struct foo extends bar\n"
				+ "endstruct");
	}
	
	@Test
	public void usingSameName() {
		this.expectedEx.none();
		this.run("library foo\n"
					+ "struct foo\n"
					+ "endstruct\n"
				+ "endlibrary");
	}
	
	@Test
	public void usingPrivateMethodExternally() {
		this.expectedEx.expect(NoAccessException.class);
		this.expectedEx.expectMessage("7:7 Element <bar> does not have access to element <pi>");
		this.run("struct foo\n"
					+ "private method pi takes nothing returns nothing\n"
					+ "endmethod\n"
				+ "endstruct\n"
				+ "function bar takes nothing returns nothing\n"
					+ "local foo i\n"
					+ "call i.pi()\n"
				+ "endfunction");
	}
	
	@Test
	public void usingPublicPropertyExternally() {
		this.expectedEx.none();
		this.run("library foo\n"
					+ "public struct foo\n"
						+ "public static real pi = 3.14\n"
					+ "endstruct\n"
				+ "endlibrary\n"
				+ "function bar takes nothing returns nothing\n"
					+ "local real pi = foo.foo.pi\n"
				+ "endfunction");
	}

}
