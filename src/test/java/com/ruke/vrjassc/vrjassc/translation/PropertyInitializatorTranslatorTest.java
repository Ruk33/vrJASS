package com.ruke.vrjassc.vrjassc.translation;

import static org.junit.Assert.*;

import org.junit.Test;

import com.ruke.vrjassc.translator.ChainExpressionTranslator;
import com.ruke.vrjassc.translator.PropertyInitializatorTranslator;
import com.ruke.vrjassc.vrjassc.symbol.ClassSymbol;
import com.ruke.vrjassc.vrjassc.symbol.PropertySymbol;

public class PropertyInitializatorTranslatorTest {

	@Test
	public void test() {
		PropertyInitializatorTranslator translator = new PropertyInitializatorTranslator();
		
		ClassSymbol _class = new ClassSymbol("foo", null, null);
		PropertySymbol bar = new PropertySymbol("bar", _class, null);
		
		translator.setDefaultValue(_class, bar, "baz");
		
		assertEquals(
			"function " + translator.getFunctionName(_class) + " takes integer this returns nothing\n"
				+ "call SaveInteger(null,this,bar,baz)\n"
			+ "endfunction",
			translator.getTranslation(_class, new ChainExpressionTranslator())
		);
	}

}
