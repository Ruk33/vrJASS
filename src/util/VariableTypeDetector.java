package util;

import java.util.Arrays;

public class VariableTypeDetector {

	// thanks
	// http://www.hiveworkshop.com/forums/263450-post1.html
	// http://jass.sourceforge.net/doc/types.shtml
	protected static String[] jassTypes = new String[] {
		"event", "player", "widget", "unit", "destructable", "item", "ability",
		"buff", "force", "group", "trigger", "triggercondition", "triggeraction",
		"timer", "location", "region", "rect", "boolexpr", "sound", "conditionfunc",
		"filterfunc", "unitpool", "itempool", "race", "alliancetype", "racepreference",
		"gamestate", "igamestate", "fgamestate", "playerstate", "playerscore",
		"playergameresult", "unitstate", "aidifficulty", "eventid", "gameevent",
		"playerevent", "playerunitevent", "unitevent", "limitop", "widgetevent",
		"dialogevent", "unittype", "gamespeed", "gamedifficulty", "gametype",
		"mapflag", "mapvisibility", "mapsetting", "mapdensity", "mapcontrol",
		"playerslotstate", "volumegroup", "camerafield", "camerasetup", "playercolor",
		"placement", "startlocprio", "raritycontrol", "blendmode", "texmapflags",
		"effect", "effecttype", "weathereffect", "terraindeformation", "fogstate",
		"fogmodifier", "dialog", "button", "quest", "questitem", "defeatcondition",
		"timerdialog", "leaderboard", "multiboard", "multiboarditem", "trackable",
		"gamecache", "version", "itemtype", "texttag", "attacktype", "damagetype",
		"weapontype", "soundtype", "lightning", "pathingtype", "image", "ubersplat",
		"handle", "widget", "ability", "gamestate", "eventid", "code", "integer",
		"real", "string", "boolean", "code", "nothing"
	};

	public static boolean isJassType(String type) {
		return Arrays.asList(VariableTypeDetector.jassTypes).contains(type);
	}

	public static boolean isUserType(String type) {
		return VariableTypeDetector.isJassType(type) == false;
	}

}