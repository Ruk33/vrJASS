package com.ruke.vrjassc.vrjassc;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.ruke.vrjassc.vrjassc.util.Compile;

public class TypeDefinitionTest {

	@Test
	public void correct() {
		Compile compile = new Compile();
		String code = "type hashtable          extends     agent";
		String result = "type hashtable extends agent";

		assertEquals(result, compile.run(code));
	}

}
