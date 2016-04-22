-- This file is executed once on we start up.  The state perseveres
-- through callbacks
--
-- wehack.runprocess:  Wait for exit code, report errors (grimext)
-- wehack.runprocess2:  Wait for exit code, don't report errors (jasshelper)
-- wehack.execprocess:  Don't wait for exit code (War3)
--
grimregpath = "Software\\Grimoire\\"
--warcraftdir = grim.getregpair(grimregpath,"War3InstallPath")
--if warcraftdir == 0 then
--	wehack.messagebox("Error, could not find warcraft install path in wehack.lua")
--end

isstartup = true
grimdir = grim.getcwd()
dofile("wehacklib.lua")
dofile("findpath.lua")
if path==0 or path=="" then
	path = "."
end
mapvalid = true
cmdargs = "" -- used to execute external tools on save

confregpath = "HKEY_CURRENT_USER\\Software\\Grimoire\\"

haveext = grim.exists("grimext\\grimex.dll")
if haveext then
	utils = wehack.addmenu("Extensions")
end
haveumswe = haveext and grim.exists("umswe\\umswecore.lua")
if haveumswe then
	ums =  	wehack.addmenu("UMSWE")
end

whmenu = wehack.addmenu("Grimoire")
wh_window = TogMenuEntry:New(whmenu,"Start War3 with -window",nil,true)
wh_opengl = TogMenuEntry:New(whmenu,"Start War3 with -opengl",nil,false)
if not grim.isnewcompiler(path.."\\war3.exe") then
  wh_grimoire = TogMenuEntry:New(whmenu,"Start War3 with Grimoire",nil,true)
  wh_enablewar3err = TogMenuEntry:New(whmenu,"Enable war3err",nil,true)
  wh_enablejapi = TogMenuEntry:New(whmenu,"Enable japi",nil,false)
end
wehack.addmenuseparator(whmenu)
wh_tesh = TogMenuEntry:New(whmenu,"Enable TESH",nil,true)
if grim.isdotnetinstalled() then
	wh_colorizer = TogMenuEntry:New(whmenu,"Enable Colorizer",nil,true)
end
wh_nolimits = TogMenuEntry:New(whmenu,"Enable no limits",
	function(self) grim.nolimits(self.checked) end,false)
wh_oehack = TogMenuEntry:New(whmenu,"Enable object editor hack",
	function(self) grim.objecteditorhack(self.checked) end,true)
wh_syndisable = TogMenuEntry:New(whmenu,"Disable WE syntax checker",
	function(self) grim.syndisable(self.checked) end,true)
wh_descpopup = TogMenuEntry:New(whmenu,"Disable default description nag",
	function(self) grim.descpopup(self.checked) end,true)
wh_autodisable = TogMenuEntry:New(whmenu,"Don't let WE disable triggers",
	function(self) grim.autodisable(self.checked) end,true)
wh_alwaysenable = TogMenuEntry:New(whmenu,"Always allow trigger enable",
	function(self) grim.alwaysenable(self.checked) end,true)
wh_disablesound = TogMenuEntry:New(whmenu,"Mute editor sounds",nil,true)
wh_firstsavenag = TogMenuEntry:New(whmenu,"Disable first save warning",nil,true)

wehack.addmenuseparator(whmenu)
weukey = "HKEY_LOCAL_MACHINE\\SOFTWARE\\Microsoft\\Windows\\CurrentVersion\\Uninstall\\WE Unlimited_is1"
weuval = "InstallLocation"
weupath = grim.getregpair(weukey,weuval)
haveweu = grim.exists(weupath .. "WE Unlimited.exe")
if haveweu then
	wh_useweu = TogMenuEntry:New(whmenu,"Integrate WEU",nil,false)
end

usetestmapconf = (grim.getregpair(confregpath,"Use custom test map settings") == "on")
function testmapconfig()
	usetestmapconf = wehack.testmapconfig(path,usetestmapconf)
	if usetestmapconf then
		grim.setregstring(confregpath,"Use custom test map settings","on")
	else
		grim.setregstring(confregpath,"Use custom test map settings","off")
	end
end
wh_configtest = MenuEntry:New(whmenu,"Customize test map settings",testmapconfig);

function aboutpopup()
	wehack.showaboutdialog("Grimoire 1.5")
end

function grimdoc()
	wehack.execprocess("starter.bat ./grimoiremanual.pdf")
end

wh_docm = MenuEntry:New(whmenu, "Grimoire Documentation ...", grimdoc)

wh_about = MenuEntry:New(whmenu,"About Grimoire ...",aboutpopup)

--Here I'll add the custom menu to jasshelper. moyack
jh_path = ""
havejh = grim.exists("vrjass\\vrjassc-jar-with-dependencies.jar") or grim.exists("cohadarjasshelper\\jasshelper.exe") or grim.exists("vexorianjasshelper\\jasshelper.exe")

if havejh then
	jhmenu = wehack.addmenu("JassHelper")
	jh_enable = TogMenuEntry:New(jhmenu,"Enable JassHelper",nil,true)
	wehack.addmenuseparator(jhmenu)
	
	jh_iscohadar = TogMenuEntry:New(jhmenu,"Enable Cohadar's JassHelper",nil,true)
	jh_isvexorian = TogMenuEntry:New(jhmenu,"Enable Vexorian's JassHelper",nil,false)
	vrJASSEnable = TogMenuEntry:New(jhmenu,"Enable vrJASS",nil,false)
	
	wehack.addmenuseparator(jhmenu)
	jh_debug = TogMenuEntry:New(jhmenu,"Debug Mode",nil,false)
	jh_disable = TogMenuEntry:New(jhmenu,"Disable vJass syntax",nil,false)
    jh_disableopt = TogMenuEntry:New(jhmenu,"Disable script optimization",nil,false)

	wehack.addmenuseparator(jhmenu)
	
	function jhsetpath()
		if jh_isvexorian.checked then
			jh_path = "vexorian"
		else
			jh_path = "cohadar" -- Default
		end
	end
	
	jhsetpath()
	
	function jhshowerr()
	  	wehack.execprocess(jh_path.."jasshelper\\jasshelper.exe --showerrors")
	end
	
	function jhabout()
	  	wehack.execprocess(jh_path.."jasshelper\\jasshelper.exe --about")
	end
	
	jhshowerrm = MenuEntry:New(jhmenu,"Show previous errors",jhshowerr)
	jhaboutm = MenuEntry:New(jhmenu,"About JassHelper ...",jhabout)
	
    function jhsetcohadar()
		jh_iscohadar.checked = true
		jh_iscohadar:redraw(jh_iscohadar)
		jh_isvexorian.checked = false
		jh_isvexorian:redraw(jh_isvexorian)
		vrJASSEnable.checked = false
		vrJASSEnable:redraw(vrJASSEnable)
		jhsetpath()
	end
	
	function jhsetvexorian()
		jh_isvexorian.checked = true
		jh_isvexorian:redraw(jh_isvexorian)
		jh_iscohadar.checked = false
		jh_iscohadar:redraw(jh_iscohadar)
		vrJASSEnable.checked = false
		vrJASSEnable:redraw(vrJASSEnable)
		jhsetpath()
	end

	function enablevrJASS()
		jh_isvexorian.checked = false
		jh_isvexorian:redraw(jh_isvexorian)
		jh_iscohadar.checked = false
		jh_iscohadar:redraw(jh_iscohadar)
		vrJASSEnable.checked = true
		vrJASSEnable:redraw(vrJASSEnable)
		jhsetpath()
	end
	
	jh_iscohadar.cb = jhsetcohadar
	jh_isvexorian.cb = jhsetvexorian
	vrJASSEnable.cb = enablevrJASS
	
	function jhshowhelp()
		jhsetpath()
		wehack.execprocess("starter.bat ./"..jh_path.."jasshelper\\jasshelpermanual.html")
	end
	
	jhhelp = MenuEntry:New(jhmenu, "JassHelper Documentation...", jhshowhelp)
end

--Sharpcraft Implementation

sharpcraft = wehack.addmenu("SharpCraft")
sc_enabled = TogMenuEntry:New( sharpcraft, "Enable SharpCraft", nil, false )

function initshellext()
    local first, last = string.find(grim.getregpair("HKEY_CLASSES_ROOT\\WorldEdit.Scenario\\shell\\open\\command\\", ""),"NewGen",1)
    if first then
        wehack.checkmenuentry(shellext.menu,shellext.id,1)
    else
    		local second, third = string.find(grim.getregpair("HKEY_CLASSES_ROOT\\WorldEdit.Scenario\\shell\\open\\command\\", ""),".bat",1)
    		if second then
    			wehack.checkmenuentry(shellext.menu,shellext.id,1)
    		else
        	wehack.checkmenuentry(shellext.menu,shellext.id,0)
        end
    end
end

function fixopencommand(disable,warpath,grimpath,filetype)
    
    local wepath = "\""..grimpath.."\\NewGen WE.exe\""
    if not grim.exists(grimpath.."\\NewGen WE.exe") then
      wepath = "\""..grimpath.."\\we.bat\""
    end
    if disable then
    	grim.setregstring("HKEY_CLASSES_ROOT\\WorldEdit."..filetype.."\\shell\\open\\command\\","","\""..warpath.."\\World Editor.exe\" -loadfile \"%L\"")
    else
    	grim.setregstring("HKEY_CLASSES_ROOT\\WorldEdit."..filetype.."\\shell\\open\\command\\","",wepath.." -loadfile \"%L\"")
    end
end

function registerextension(disable,warpath,grimpath,filetype,istft)
    if disable then
        grim.deleteregkey("HKEY_CLASSES_ROOT\\WorldEdit."..filetype.."\\shell\\fullscreen\\command\\");
        grim.deleteregkey("HKEY_CLASSES_ROOT\\WorldEdit."..filetype.."\\shell\\fullscreen\\");
        grim.deleteregkey("HKEY_CLASSES_ROOT\\WorldEdit."..filetype.."\\shell\\windowed\\command\\");
        grim.deleteregkey("HKEY_CLASSES_ROOT\\WorldEdit."..filetype.."\\shell\\windowed\\");
        grim.deleteregkey("HKEY_CLASSES_ROOT\\WorldEdit."..filetype.."\\shell\\opengl\\command\\");
        grim.deleteregkey("HKEY_CLASSES_ROOT\\WorldEdit."..filetype.."\\shell\\opengl\\");
    else
		--New loader for Warcraft III maps. By Blizzardmodding.info & moyack 
        local gamepath = "\""..grimpath.."\\blizzmod\\MTT.exe\""
        grim.setregstring("HKEY_CLASSES_ROOT\\WorldEdit."..filetype.."\\shell\\fullscreen\\","","Play Fullscreen")
        grim.setregstring("HKEY_CLASSES_ROOT\\WorldEdit."..filetype.."\\shell\\fullscreen\\command\\","",gamepath.." --loadmap=%L")
        grim.setregstring("HKEY_CLASSES_ROOT\\WorldEdit."..filetype.."\\shell\\windowed\\","","Play Windowed")
        grim.setregstring("HKEY_CLASSES_ROOT\\WorldEdit."..filetype.."\\shell\\windowed\\command\\","",gamepath.." --loadmap=%L --arguments=-window")
        grim.setregstring("HKEY_CLASSES_ROOT\\WorldEdit."..filetype.."\\shell\\opengl\\","","Play With OpenGL")
        grim.setregstring("HKEY_CLASSES_ROOT\\WorldEdit."..filetype.."\\shell\\opengl\\command\\","",gamepath.." --loadmap=%L --arguments=-window -opengl")
    end
end

function toggleshellext()
    local istft = (grim.getregpair("HKEY_CURRENT_USER\\Software\\Blizzard Entertainment\\Warcraft III\\", "InstallPathX") ~= 0)
    local first, last = string.find(grim.getregpair("HKEY_CLASSES_ROOT\\WorldEdit.Scenario\\shell\\open\\command\\", ""),"NewGen",1)
    local found = false
    if first then
    	found = true
    else
    		local second, third = string.find(grim.getregpair("HKEY_CLASSES_ROOT\\WorldEdit.Scenario\\shell\\open\\command\\", ""),".bat",1)
    		if second then
    			found = true
    		end
    end

    if path ~= 0 and grimdir ~= 0 then
        fixopencommand(found,path,grimdir,"Scenario")
        registerextension(found,path,grimdir,"Scenario",istft)
        fixopencommand(found,path,grimdir,"ScenarioEx")
        registerextension(found,path,grimdir,"ScenarioEx",istft)
        fixopencommand(found,path,grimdir,"Campaign")
        registerextension(found,path,grimdir,"Campaign",istft)
        fixopencommand(found,path,grimdir,"AIData")
        if found then
            wehack.checkmenuentry(shellext.menu,shellext.id,0)
        else
            wehack.checkmenuentry(shellext.menu,shellext.id,1)
        end
    end
end

function initlocalfiles()
    if grim.getregpair("HKEY_CURRENT_USER\\Software\\Blizzard Entertainment\\Warcraft III\\", "Allow Local Files") == 0 then
        wehack.checkmenuentry(localfiles.menu,localfiles.id,0)
    else
        wehack.checkmenuentry(localfiles.menu,localfiles.id,1)
    end
end

function togglelocalfiles()
    if grim.getregpair("HKEY_CURRENT_USER\\Software\\Blizzard Entertainment\\Warcraft III\\", "Allow Local Files") == 0 then
        grim.setregdword("HKEY_CURRENT_USER\\Software\\Blizzard Entertainment\\Warcraft III\\", "Allow Local Files", 1)
        wehack.checkmenuentry(localfiles.menu,localfiles.id,1)
    else
        grim.setregdword("HKEY_CURRENT_USER\\Software\\Blizzard Entertainment\\Warcraft III\\", "Allow Local Files", 0)
        wehack.checkmenuentry(localfiles.menu,localfiles.id,0)
    end
end

function runobjectmerger(mode)
    curmap = wehack.findmappath()
    if curmap ~= "" then
        source = wehack.openfiledialog("Unit files (*.w3u)|*.w3u|Item files (*.w3t)|*w3t|Doodad files (*.w3d)|*.w3d|Destructable files (*.w3b)|*.w3b|Ability files (*.w3a)|*.w3a|Buff files (*.w3h)|*.w3h|Upgrade files (*.w3q)|*.w3q|", "w3a", "Select files to import ...", true)
grim.log("got in lua: " .. source)
        if source ~= "" then
            list = strsplit("|", source);
--            cmdargs = "ObjectMerger \""..curmap.."\" "..wehack.getlookupfolders().." "..mode..fileargsjoin(list)        
            cmdargs = "grimext\\ObjectMerger.exe \""..curmap.."\" "..wehack.getlookupfolders().." "..mode..fileargsjoin(list)
grim.log("assembled cmdline: " .. cmdargs)
--            wehack.messagebox(cmdargs,"Grimoire",false)
            wehack.savemap()
grim.log("called saved map")
        end
    else
    	showfirstsavewarning()
    end
end

function runconstantmerger()
    curmap = wehack.findmappath()
    if curmap ~= "" then
        source = wehack.openfiledialog("Text files (*.txt)|*.txt|", "txt", "Select files to import ...", true)
        if source ~= "" then
            list = strsplit("|", source);
--            cmdargs = "ConstantMerger \""..curmap.."\" "..wehack.getlookupfolders()..fileargsjoin(list)
            cmdargs = "grimext\\ConstantMerger.exe \""..curmap.."\" "..wehack.getlookupfolders()..fileargsjoin(list)
--            wehack.messagebox(cmdargs,"Grimoire",false)
            wehack.savemap()
        end
    else
    	showfirstsavewarning()
    end
end

function runtriggermerger()
    curmap = wehack.findmappath()
    if curmap ~= "" then
        source = wehack.openfiledialog("GUI Trigger files (*.wtg)|*.wtg|Custom Text Trigger files (*.wct)|*wct|", "wtg", "Select trigger data to import ...", true)
        if source ~= "" then
            list = strsplit("|", source);
--            cmdargs = "TriggerMerger \""..curmap.."\" "..wehack.getlookupfolders()..fileargsjoin(list)
            cmdargs = "grimext\\TriggerMerger.exe \""..curmap.."\" "..wehack.getlookupfolders()..fileargsjoin(list)
--            wehack.messagebox(cmdargs,"Grimoire",false)
            wehack.savemap()
        end
    else
    	showfirstsavewarning()
    end
end

function runfileimporterfiles()
    curmap = wehack.findmappath()
    if curmap ~= "" then
        source = wehack.openfiledialog("All files (*.*)|*.*|", "*", "Select files to import ...", true)
        if source ~= "" then
            list = strsplit("|", source);
            inmpqpath = wehack.inputbox("Specify the target path ...","FileImporter","Units\\")
--            cmdargs = "FileImporter \""..curmap.."\" "..wehack.getlookupfolders()..argsjoin(inmpqpath,list)
            cmdargs = "grimext\\FileImporter.exe \""..curmap.."\" "..wehack.getlookupfolders()..argsjoin(inmpqpath,list)
--            wehack.messagebox(cmdargs,"Grimoire",false)
            wehack.savemap()
        end
    else
    	showfirstsavewarning()
    end
end

function runfileimporterdir()
    curmap = wehack.findmappath()
    if curmap ~= "" then
        source = wehack.browseforfolder("Select the source directory ...")
        if source ~= "" then
--            cmdargs = "FileImporter \""..curmap.."\" "..wehack.getlookupfolders().." \""..source.."\""
            cmdargs = "grimext\\FileImporter.exe \""..curmap.."\" "..wehack.getlookupfolders().." \""..source.."\""
--            wehack.messagebox(cmdargs,"Grimoire",false)
            wehack.savemap()
        end
    else
    	showfirstsavewarning()
    end
end

function runfileexporter()
    curmap = wehack.findmappath()
    if curmap ~= "" then
        target = wehack.browseforfolder("Select the target directory ...")
        if target ~= "" then
--        		wehack.rungrimextool("FileExporter", curmap, removequotes(wehack.getlookupfolders()), target)
            wehack.runprocess("grimext\\FileExporter.exe \""..curmap.."\" "..wehack.getlookupfolders().." \""..target.."\"")
        end
    else
    	showfirstsavewarning()
    end
end

function runtilesetter()
    curmap = wehack.findmappath()
    if curmap ~= "" then
        map = wehack.openarchive(curmap,15)
        oldtiles = wehack.getcurrenttiles()
        wehack.closearchive(map)
        if oldtiles ~= "" then
        		newtiles = wehack.tilesetconfig(string.sub(oldtiles,1,1), string.sub(oldtiles,2))
        		if newtiles ~= "" then
        			tileset = string.sub(newtiles,1,1)
        			tiles = string.sub(newtiles,2)
							if tileset ~= "" and tiles ~= "" then
--								cmdargs = "TileSetter \""..curmap.."\" "..wehack.getlookupfolders().." "..tileset.." "..tiles
								cmdargs = "grimext\\TileSetter.exe \""..curmap.."\" "..wehack.getlookupfolders().." "..tileset.." "..tiles
								wehack.savemap()
        			end
        		end
        		
--            tileset = wehack.inputbox("Specify the tileset ...","TileSetter",string.sub(oldtiles,1,1))
--            if tileset ~= "" then
--                tiles = wehack.inputbox("Specify the tile list ...","TileSetter",string.sub(oldtiles,2))
--                if tiles ~= "" then
--                    cmdargs = "grimext\\TileSetter.exe \""..curmap.."\" "..wehack.getlookupfolders().." "..tileset.." "..tiles
--                    wehack.savemap()
--                end
--            end
        end
    else
    	showfirstsavewarning()
    end
end

function showfirstsavewarning()
	if wh_firstsavenag.checked then
		return
	else
		wehack.messagebox("Could not find path to map, please try saving again","Grimoire",false)
	end
end

function testmap(cmdline)
	
	start = string.find(cmdline, "-loadfile")
	loadfile = string.sub(cmdline, start)
    
	if sc_enabled.checked then
		cmdline = "SharpCraft\\SharpCraft.exe -game " .. loadfile
	end

	-- wehack.messagebox(cmdline)

	if wh_opengl.checked then
		cmdline = cmdline .. " -opengl"
	end
	if wh_window.checked then
		cmdline = cmdline .. " -window"
	end
    
	wehack.execprocess(cmdline)
end

function compilemap_path(mappath)
	if mappath == "" then
		showfirstsavewarning()
		return
	end
	map = wehack.openarchive(mappath,15)
	wehack.extractfile(jh_path.."jasshelper\\common.j","scripts\\common.j")
	wehack.extractfile(jh_path.."jasshelper\\Blizzard.j","scripts\\Blizzard.j")
	wehack.extractfile("war3map.j","war3map.j")
	wehack.closearchive(map)
	if cmdargs ~= "" then
		local cmdtable = argsplit(cmdargs)
		grim.log("running tool on save: "..cmdargs)
		wehack.runprocess(cmdargs)
		cmdargs = ""
	end
	-- Here I'll add a new configuration for jasshelper. moyack
	if havejh and jh_enable.checked then
		if vrJASSEnable.checked then
			toolresult = wehack.runprocess2("java -jar vrjass/vrjassc-jar-with-dependencies.jar \"" .. mappath .. "\" -log=vrjass.txt -result=\"" .. mappath .. "\"")
			if toolresult == 0 then 
				mapvalid = true
			else
				mapvalid = false
			end
		else
			cmdline = jh_path .. "jasshelper\\jasshelper.exe"
			if jh_debug.checked then
				cmdline = cmdline .. " --debug"
			end
			if jh_disable.checked then
				cmdline = cmdline .. " --nopreprocessor"
			end
			if jh_disableopt.checked then
				cmdline = cmdline .. " --nooptimize"
			end
			cmdline = cmdline .. " "..jh_path.."jasshelper\\common.j "..jh_path.."jasshelper\\blizzard.j \"" .. mappath .."\""
			toolresult = 0
			toolresult = wehack.runprocess2(cmdline)
			if toolresult == 0 then 
				mapvalid = true
			else
				mapvalid = false
			end
		end
	end
end

dofile("ScExp\\ScExp.lua") 
function compilemap() 
	mappath = wehack.findmappath() 
	if mappath == "" then 
		scexpBuildCampaign()
	else compilemap_path(mappath) 
	end 
end
--function compilemap()
--	mappath = wehack.findmappath()
--	compilemap_path(mappath)
--end

--Menu for JNGP. moyack
function JNGPHelp()
	wehack.execprocess("starter.bat ./NewGenReadme.html")
end

function JNGPAbout()
    wehack.runprocess("blizzmod\\MTT.exe --about")
end

function JNGPCSTMenuDB()
    wehack.runprocess("blizzmod\\MTT.exe --cstmenu")
end

function JNGPStatus()
    wehack.runprocess("blizzmod\\MTT.exe --status=" .. path)
end

function JNGPMultiT()
	arg = ""
	if wh_window.checked then
		arg = "=-window"
		if wh_opengl.checked then
			arg = arg.." -opengl"
		end
	else
		if wh_opengl.checked then
			arg = "=-opengl"
		end
	end
    wehack.runprocess("blizzmod\\MTT.exe --multisession"..arg)
end

function JNGPSetPath()
	wehack.runprocess("blizzmod\\MTT.exe --setwc3path=" .. path)
end

jngpm = wehack.addmenu("JNGP version 2.0.X")
jngpmulti = MenuEntry:New(jngpm,"Start Multiplayer Emulation...",JNGPMultiT)
jngpcstmenudlg = MenuEntry:New(jngpm,"Set Custom Menu Loader...",JNGPCSTMenuDB)
wehack.addmenuseparator(jngpm)
if grim.exists("blizzmod\\CST_Menu.lua") then
	dofile("blizzmod\\CST_Menu.lua")
end
wehack.addmenuseparator(jngpm)
jngpstatus = MenuEntry:New(jngpm,"Diagnose JNGP Settings...",JNGPStatus)
jngpsetwc3path = MenuEntry:New(jngpm,"Set Manually Warcraft III Path...",JNGPSetPath)
jngphelpm = MenuEntry:New(jngpm,"Jass New Generation Pack Information & Help...",JNGPHelp)
wehack.addmenuseparator(jngpm)
jngpaboutm = MenuEntry:New(jngpm,"Jass New Generation Pack About...",JNGPAbout)

--End menu entry

if haveext then
    localfiles = MenuEntry:New(utils,"Enable Local Files",togglelocalfiles)
    shellext = MenuEntry:New(utils,"Register Shell Extensions",toggleshellext)
    initlocalfiles()
    initshellext()
    wehack.addmenuseparator(utils)
end
if haveext and grim.exists("grimext\\tilesetter.exe") then
    tilesetter = MenuEntry:New(utils,"Edit Tileset",runtilesetter)
end
if haveext and grim.exists("grimext\\fileexporter.exe") then
    fileexporter = MenuEntry:New(utils,"Export Files",runfileexporter)
end
if haveext and grim.exists("grimext\\fileimporter.exe") then
    fileimporterdir = MenuEntry:New(utils,"Import Directory",runfileimporterdir)
    fileimporterfiles = MenuEntry:New(utils,"Import Files",runfileimporterfiles)
end
if haveext and grim.exists("grimext\\objectmerger.exe") then
    objectmerger = MenuEntry:New(utils,"Merge Object Editor Data",function(self) runobjectmerger("m") end)
    objectreplacer = MenuEntry:New(utils,"Replace Object Editor Data",function(self) runobjectmerger("r") end)
    objectimporter = MenuEntry:New(utils,"Import Object Editor Data",function(self) runobjectmerger("i") end)
end
if haveext and grim.exists("grimext\\constantmerger.exe") then
    constantmerger = MenuEntry:New(utils,"Merge Constants Data",runconstantmerger)
end
if haveext and grim.exists("grimext\\triggermerger.exe") then
    triggermerger = MenuEntry:New(utils,"Merge Trigger Data",runtriggermerger)
end

function extabout()
    wehack.execprocess("starter.bat ./grimext\\GrimexManual.html")
end
if haveext then
	wehack.addmenuseparator(utils)
	aboutextensions = MenuEntry:New(utils,"About Grimex ...",extabout)
end


if haveumswe then
	ums_enabled = TogMenuEntry:New(ums,"Enable UMSWE",nil,false)
	ums_cat = TogMenuEntry:New(ums,"Custom Editor Categories",nil,false)
	ums_til = TogMenuEntry:New(ums,"Non Tileset Specific Objects",nil,false)
	ums_pat = TogMenuEntry:New(ums,"Custom Tile Pathability",nil,false)
	
	function unloadumswe()
		local umswehandle = wehack.getarchivehandle("umswe\\umswe.mpq")
		if umswehandle ~= 0 then
			wehack.closearchive(umswehandle)
			wehack.setarchivehandle("umswe\\umswe.mpq", 0)
		end
	end
	
	function getumsweargs()
		local umsargs = "";
		if (ums_enabled.checked) then
			umsargs = umsargs .. " umscore=1"
		else
			umsargs = umsargs .. " umscore=0"
		end
		if (ums_cat.checked) then
			umsargs = umsargs .. " umscategories=1"
		else
			umsargs = umsargs .. " umscategories=0"
		end
		if (ums_til.checked) then
			umsargs = umsargs .. " umstiles=1"
		else
			umsargs = umsargs .. " umstiles=0"
		end
		if (ums_pat.checked) then
			umsargs = umsargs .. " umspathing=1"
		else
			umsargs = umsargs .. " umspathing=0"
		end
		return umsargs
	end
	
	function toggleumswe()
		if not isstartup then
			unloadumswe()
			wehack.setwaitcursor(true)
			wehack.runprocess("grimext\\PatchGenerator.exe umswe\\umswecore.lua "..wehack.getlookupfolders().." umswe"..getumsweargs())
			wehack.setwaitcursor(false)
		end
	end
	
	function toggleumswecat()
		if ums_enabled.checked and not isstartup then
			unloadumswe()
			wehack.setwaitcursor(true)
			wehack.runprocess("grimext\\PatchGenerator.exe umswe\\umswecategories.lua "..wehack.getlookupfolders().." umswe"..getumsweargs())
			wehack.setwaitcursor(false)
		end
	end
	
	function toggleumswetil()
		if ums_enabled.checked and not isstartup then
			unloadumswe()
			wehack.setwaitcursor(true)
			wehack.runprocess("grimext\\PatchGenerator.exe umswe\\umswetilesets.lua "..wehack.getlookupfolders().." umswe"..getumsweargs())
			wehack.setwaitcursor(false)
		end
	end
	
	function toggleumswepat(newstate)
		if ums_enabled.checked and not isstartup then
			unloadumswe()
			wehack.setwaitcursor(true)
			wehack.runprocess("grimext\\PatchGenerator.exe umswe\\umswepathing.lua "..wehack.getlookupfolders().." umswe"..getumsweargs())
			wehack.setwaitcursor(false)
		end
	end
	
	ums_enabled.cb = toggleumswe
	ums_cat.cb = toggleumswecat
	ums_til.cb = toggleumswetil
	ums_pat.cb = toggleumswepat
	
	function categoryconfig()
		if wehack.showcategorydialog("umswe\\umswecategories.conf.lua") and ums_enabled.checked then
			if ums_cat.checked then
				unloadumswe()
				wehack.setwaitcursor(true)
				wehack.runprocess("grimext\\PatchGenerator.exe umswe\\umswecategories.lua "..wehack.getlookupfolders().." umswe"..getumsweargs())
				wehack.setwaitcursor(false)
			end
		end
	end
	
	function pathabilityconfig()
		unloadumswe()
		if wehack.showpathdialog("umswe\\umswepathing.conf.lua","umswe\\umswe.mpq") and ums_enabled.checked then
			if ums_pat.checked then
				wehack.setwaitcursor(true)
				wehack.runprocess("grimext\\PatchGenerator.exe umswe\\umswepathing.lua "..wehack.getlookupfolders().." umswe"..getumsweargs())
				wehack.setwaitcursor(false)
			end
		end
	end
	
	function umsweabout()
		wehack.showumsweabout("UMSWE 5.0")
	end
	
	function umswehelp()
		wehack.execprocess("starter.bat ./umswe\\UMSWEManual.html")
	end
	
	wehack.addmenuseparator(ums)
	ums_catconf = MenuEntry:New(ums,"Customize Editor Categories",categoryconfig)
	ums_pathconf = MenuEntry:New(ums,"Customize Tile Pathability",pathabilityconfig)
	ums_about = MenuEntry:New(ums,"About UMSWE ...",umsweabout)
	ums_help = MenuEntry:New(ums,"UMSWE Documentation ...",umswehelp)
end

isstartup = false
