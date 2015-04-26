package test;

import static org.junit.Assert.*;

import org.junit.Test;

import util.Compile;

public class NativeDeclarationTest {

	@Test
	public void correct() {
		Compile compile = new Compile();
		String code =
				"native CameraSetupGetDestPositionY          takes camerasetup whichSetup returns real";
		
		String result =
				"native CameraSetupGetDestPositionY takes camerasetup whichSetup returns real";
		
		assertEquals(result, compile.run(code));
	}
	
	@Test
	public void constant() {
		Compile compile = new Compile();
		String code =
				"constant native CameraSetupGetDestPositionY          takes camerasetup whichSetup returns real";
		
		String result =
				"constant native CameraSetupGetDestPositionY takes camerasetup whichSetup returns real";
		
		assertEquals(result, compile.run(code));
	}

}
