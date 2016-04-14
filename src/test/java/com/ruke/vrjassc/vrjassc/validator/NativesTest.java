package com.ruke.vrjassc.vrjassc.validator;

import com.ruke.vrjassc.vrjassc.util.TestHelper;
import org.junit.Test;

public class NativesTest extends TestHelper {

	@Test
	public void test() {
		this.expectedEx.none();
		this.run(
			"globals\n"
				+ "trigger t = null\n"
			+ "endglobals\n"
			+ "function foo takes nothing returns nothing\n"
				+ "local player p = GetLocalPlayer()\n"
				+ "call SetAmbientDaySound( \"LordaeronSummerDay\" )\n"
				+ "call SetMapMusic( \"Music\", true, 0 )\n"
				+ "call CreateUnit( p, 'Hblm', -67.6, -143.3, 175.072 )\n"
				+ "call SetPlayerSlotAvailable( Player(0), MAP_CONTROL_USER )\n"
				+ "call SetDayNightModels( \"Environment\\DNC\\DNCLordaeron\", \"some\\foo\")\n"
				+ "call SetCameraBounds( -1280.0 + GetCameraMargin(CAMERA_MARGIN_LEFT), -1536.0 + GetCameraMargin(CAMERA_MARGIN_BOTTOM), 1280.0 - GetCameraMargin(CAMERA_MARGIN_RIGHT), 1024.0 - GetCameraMargin(CAMERA_MARGIN_TOP), -1280.0 + GetCameraMargin(CAMERA_MARGIN_LEFT), 1024.0 - GetCameraMargin(CAMERA_MARGIN_TOP), 1280.0 - GetCameraMargin(CAMERA_MARGIN_RIGHT), -1536.0 + GetCameraMargin(CAMERA_MARGIN_BOTTOM) )\n"
				+ "// some comment\n"
				+ "call SetPlayerTeam( Player(0), 0 )\n"
			+ "endfunction"
		);
	}

}
