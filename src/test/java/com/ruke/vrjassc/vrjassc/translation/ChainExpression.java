package com.ruke.vrjassc.vrjassc.translation;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.ruke.vrjassc.translator.ChainExpressionTranslator;
import com.ruke.vrjassc.vrjassc.symbol.BuiltInTypeSymbol;
import com.ruke.vrjassc.vrjassc.symbol.ClassSymbol;
import com.ruke.vrjassc.vrjassc.symbol.Modifier;
import com.ruke.vrjassc.vrjassc.symbol.PropertySymbol;
import com.ruke.vrjassc.vrjassc.symbol.Symbol;
import com.ruke.vrjassc.vrjassc.symbol.Type;

public class ChainExpression {

	protected ChainExpressionTranslator translator = new ChainExpressionTranslator();
	
	@Before
	public void setUp() throws Exception {
		this.translator.setHashtableName("structs");
	}
	
	@Test
	public void simple() {
		Type intType = new BuiltInTypeSymbol("integer", null, null);
		Symbol symbol = new Symbol("foo", null, null);
		
		symbol.setType(intType);
		
		this.translator.append(symbol, null, null);
		this.translator.append(symbol, null, null);
		
		assertEquals("LoadInteger(structs,foo,foo)", this.translator.buildGetter());
	}
		
	@Test
	public void usingStructs() {
		Type intType = new BuiltInTypeSymbol("integer", null, null);
		
		Symbol foo = new ClassSymbol("foo", null, null);
		Symbol bar = new ClassSymbol("bar", null, null);
		Symbol i = new Symbol("i", null, null);
		
		i.setType(intType);
		
		// foo.bar.i
		this.translator.append(foo, null, null);
		this.translator.append(bar, null, null);
		this.translator.append(i, null, "dat_i");

		assertEquals(
			"LoadInteger(structs,LoadInteger(structs,foo,bar),dat_i)",
			this.translator.buildGetter()
		);
	}
	
	@Test
	public void usingArrays() {
		Symbol foo = new ClassSymbol("foo", null, null);
		PropertySymbol bar = new PropertySymbol("bar", null, null);
		
		bar.setModifier(Modifier.ARRAY, true);
		
		// foo.bar[2]
		this.translator.append(foo, null, null);
		this.translator.append(bar, "2", null);
		
		assertEquals(
			"LoadInteger(structs,foo,bar*8191-IMinBJ(2,8191))",
			this.translator.buildGetter()
		);
		
		// this.bar[0].bar[2]
		// loadint(structs, loadint(structs, this, bar*8190-0), bar*8190-2)
		this.translator.append(foo, null, null);
		this.translator.append(bar, "0", null);
		this.translator.append(bar, "2", null);
		
		// load<type>(structs, instance, <attribute>)
		assertEquals(
			"LoadInteger(structs,LoadInteger(structs,foo,bar*8191-IMinBJ(0,8191)),bar*8191-IMinBJ(2,8191))",
			this.translator.buildGetter()
		);
		
	}
	
	@Test
	public void setting() {
		Symbol foo = new Symbol("foo", null, null);
		Symbol bar = new Symbol("bar", null, null);
		
		this.translator.append(foo, null, null);
		this.translator.append(bar, null, null);
		
		assertEquals(
			"SaveInteger(structs,foo,bar,2)",
			this.translator.buildSetter("2")
		);
	}

}
