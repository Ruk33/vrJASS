package com.ruke.vrjassc.vrjassc;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.ruke.vrjassc.vrjassc.util.Compile;

public class InitTrigTest {

	@Test
	public void autoDeleteEmpyInitTrig() {
		Compile compile = new Compile();

		String code = "function InitCustomTriggers takes nothing returns nothing" + System.lineSeparator()
			    + "call InitTrig_Melee_Initialization(  )" + System.lineSeparator()
			    + "endfunction";

		String result = "function InitCustomTriggers takes nothing returns nothing" + System.lineSeparator()
				+ System.lineSeparator()
			    + "endfunction";

		assertEquals(result, compile.run(code));
	}

}
