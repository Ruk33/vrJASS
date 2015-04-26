package test;

import static org.junit.Assert.*;

import org.junit.Test;

import util.Compile;

public class TypeDefinitionTest {

	@Test
	public void correct() {
		Compile compile = new Compile();
		String code = "type hashtable          extends     agent";
		String result = "type hashtable extends agent";
		
		assertEquals(result, compile.run(code));
	}

}
