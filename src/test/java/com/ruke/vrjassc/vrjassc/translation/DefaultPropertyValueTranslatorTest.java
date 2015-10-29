package com.ruke.vrjassc.vrjassc.translation;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.ruke.vrjassc.translator.ChainExpressionTranslator;
import com.ruke.vrjassc.translator.DefaultPropertyValueTranslator;
import com.ruke.vrjassc.vrjassc.symbol.ClassSymbol;
import com.ruke.vrjassc.vrjassc.symbol.LocalVariableSymbol;
import com.ruke.vrjassc.vrjassc.symbol.PropertySymbol;

public class DefaultPropertyValueTranslatorTest {

	@Test
	public void test() {
		DefaultPropertyValueTranslator dpvt = new DefaultPropertyValueTranslator();
		
		ClassSymbol _class = new ClassSymbol("foo", null, null);
		PropertySymbol property = new PropertySymbol("bar", _class, null);
		LocalVariableSymbol _this = new LocalVariableSymbol("this", null, null);
		
		_this.setType(_class.getType());
		dpvt.setDefaultValue(property, "baz");
		
		assertEquals(
			"SaveInteger(null,this,bar,baz)",
			dpvt.getTranslation(_this, property, new ChainExpressionTranslator())
		);
	}

}
