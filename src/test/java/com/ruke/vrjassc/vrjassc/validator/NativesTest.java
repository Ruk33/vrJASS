package com.ruke.vrjassc.vrjassc.validator;

import org.junit.Test;

import com.ruke.vrjassc.vrjassc.util.TestHelper;

public class NativesTest extends TestHelper {

	@Test
	public void test() {
		this.expectedEx.none();
		this.run("function foo takes nothing returns nothing\n"
					+ "local player p = GetLocalPlayer()\n"
				+ "endfunction");
	}

}
