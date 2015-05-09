package com.ruke.vrjassc.vrjassc;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.ruke.vrjassc.vrjassc.util.Compile;

public class NativeDeclarationTest {

	@Test
	public void correct() {
		Compile compile = new Compile();
		String code = "native CameraSetupGetDestPositionY          takes camerasetup whichSetup returns real";

		String result = "native CameraSetupGetDestPositionY takes camerasetup whichSetup returns real";

		assertEquals(result, compile.run(code));
	}

	@Test
	public void constant() {
		Compile compile = new Compile();
		String code = "constant native CameraSetupGetDestPositionY          takes camerasetup whichSetup returns real";

		String result = "constant native CameraSetupGetDestPositionY takes camerasetup whichSetup returns real";

		assertEquals(result, compile.run(code));
	}

	@Test
	public void usingNative() {
		Compile compile = new Compile();
		String code = "constant native IsUnitAlive takes nothing returns boolean" + System.lineSeparator()
				+ "function foo takes nothing returns nothing" + System.lineSeparator()
				+ "call IsUnitAlive()" + System.lineSeparator()
				+ "endfunction";

		assertEquals(code, compile.run(code));
	}

}
