package com.ruke.vrjassc.vrjassc.translation;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.ruke.vrjassc.vrjassc.TestHelper;

public class FunctionTest extends TestHelper {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void test() {
		String code = "function foo takes nothing returns nothing\n"
				+ "local integer i = 1\n"
				+ "set i += 2\n"
				+ "endfunction";
		assertEquals("", this.run(code));
	}

}
