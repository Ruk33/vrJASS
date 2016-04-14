package com.ruke.vrjassc.vrjassc.util;

import com.ruke.vrjassc.vrjassc.symbol.Type;

import java.util.Hashtable;

public class HashtableFunctionGetter {

	protected static Hashtable<String, String> save;
	protected static Hashtable<String, String> load;
	
	static {
		save = new Hashtable<String, String>();
		load = new Hashtable<String, String>();
		
		load.put("ability", "LoadAbilityHandle");
		load.put("boolean", "LoadBoolean");
		load.put("boolexpr", "LoadBooleanExprHandle");
		load.put("button", "LoadButtonHandle");
		load.put("defeatcondition", "LoadDefeatConditionHandle");
		load.put("destructable", "LoadDestructableHandle");
		load.put("dialog", "LoadDialogHandle");
		load.put("effect", "LoadEffectHandle");
		load.put("fogmodifier", "LoadFogModifierHandle");
		load.put("fogstate", "LoadFogStateHandle");
		load.put("force", "LoadForceHandle");
		load.put("group", "LoadGroupHandle");
		load.put("hashtable", "LoadHashtableHandle");
		load.put("image", "LoadImageHandle");
		load.put("integer", "LoadInteger");
		load.put("item", "LoadItemHandle");
		load.put("itempool", "LoadItemPoolHandle");
		load.put("leaderboard", "LoadLeaderboardHandle");
		load.put("lightning", "LoadLightningHandle");
		load.put("location", "LoadLocationHandle");
		load.put("multiboard", "LoadMultiboardHandle");
		load.put("multiboarditem", "LoadMultiboardItemHandle");
		load.put("player", "LoadPlayerHandle");
		load.put("quest", "LoadQuestHandle");
		load.put("questitem", "LoadQuestItemHandle");
		load.put("real", "LoadReal");
		load.put("rect", "LoadRectHandle");
		load.put("region", "LoadRegionHandle");
		load.put("sound", "LoadSoundHandle");
		load.put("string", "LoadStr");
		load.put("texttag", "LoadTextTagHandle");
		load.put("timerdialog", "LoadTimerDialogHandle");
		load.put("timer", "LoadTimerHandle");
		load.put("trackable", "LoadTrackableHandle");
		load.put("triggeraction", "LoadTriggerActionHandle");
		load.put("triggercondition", "LoadTriggerConditionHandle");
		load.put("event", "LoadTriggerEventHandle");
		load.put("trigger", "LoadTriggerHandle");
		load.put("ubersplat", "LoadUbersplatHandle");
		load.put("unit", "LoadUnitHandle");
		load.put("unitpool", "LoadUnitPoolHandle");
		load.put("widget", "LoadWidgetHandle");
		
		save.put("ability", "SaveAbilityHandle");
		save.put("agent", "SaveAgentHandle");
		save.put("boolean", "SaveBoolean");
		save.put("boolexpr", "SaveBooleanExprHandle");
		save.put("button", "SaveButtonHandle");
		save.put("defeatcondition", "SaveDefeatConditionHandle");
		save.put("destructable", "SaveDestructableHandle");
		save.put("dialog", "SaveDialogHandle");
		save.put("effect", "SaveEffectHandle");
		save.put("fogmodifier", "SaveFogModifierHandle");
		save.put("fogstate", "SaveFogStateHandle");
		save.put("force", "SaveForceHandle");
		save.put("group", "SaveGroupHandle");
		save.put("hashtable", "SaveHashtableHandle");
		save.put("image", "SaveImageHandle");
		save.put("integer", "SaveInteger");
		save.put("item", "SaveItemHandle");
		save.put("itempool", "SaveItemPoolHandle");
		save.put("leaderboard", "SaveLeaderboardHandle");
		save.put("lightning", "SaveLightningHandle");
		save.put("location", "SaveLocationHandle");
		save.put("multiboard", "SaveMultiboardHandle");
		save.put("multiboarditem", "SaveMultiboardItemHandle");
		save.put("player", "SavePlayerHandle");
		save.put("quest", "SaveQuestHandle");
		save.put("questitem", "SaveQuestItemHandle");
		save.put("real", "SaveReal");
		save.put("rect", "SaveRectHandle");
		save.put("region", "SaveRegionHandle");
		save.put("sound", "SaveSoundHandle");
		save.put("string", "SaveStr");
		save.put("texttag", "SaveTextTagHandle");
		save.put("timerdialog", "SaveTimerDialogHandle");
		save.put("timer", "SaveTimerHandle");
		save.put("trackable", "SaveTrackableHandle");
		save.put("triggeraction", "SaveTriggerActionHandle");
		save.put("triggercondition", "SaveTriggerConditionHandle");
		save.put("event", "SaveTriggerEventHandle");
		save.put("trigger", "SaveTriggerHandle");
		save.put("ubersplat", "SaveUbersplatHandle");
		save.put("unit", "SaveUnitHandle");
		save.put("unitpool", "SaveUnitPoolHandle");
		save.put("widget", "SaveWidgetHandle");
	}
	
	public static String getLoadFunction(Type type) {
		String name = type == null ? "" : type.getName();
		return load.getOrDefault(name, "LoadInteger");
	}
	
	public static String getSaveFunction(Type type) {
		String name = type == null ? "" : type.getName();
		return save.getOrDefault(name, "SaveInteger");
	}
	
}
