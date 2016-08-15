package com.ruke.vrjassc.vrjassc.util;

import com.ruke.vrjassc.compiler.Compiler;
import com.ruke.vrjassc.vrjassc.exception.CompileException;
import com.ruke.vrjassc.vrjassc.symbol.Symbol;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Compile {
	
	private static Symbol natives = null;
	private static String commonPath = "./resources/common.j";
	private static String blizzardPath = "./resources/blizzard.j";

	private CompilerFacade compiler = new CompilerFacade();

	public CompilerFacade getCompiler() {
		return this.compiler;
	}

	public void setCommonPath(String path) {
		this.compiler.commonPath = path;
		commonPath = path;
	}

	public void setBlizzardPath(String path) {
		this.compiler.blizzardPath = path;
		blizzardPath = path;
	}
	
	private static Symbol getNativeSymbols() throws IOException {
		if (natives == null) {
			Compiler common = new Compiler(new File(commonPath));
			common.compile(false);
			
			Compiler blizzard = new Compiler(new File(blizzardPath));
			blizzard.injectSymbol(common.getSymbols());
			blizzard.compile(false);
			
			natives = blizzard.getSymbols();
		}
		
		return natives;
	}

	public String run(String code, boolean translate) throws CompileException, IOException {
		String replacedTabs = code.replace("\t", "    ") + "\n";
		
		Compiler output = new Compiler(replacedTabs);
		output.injectSymbol(getNativeSymbols());
		
		return output.compile(translate);
	}
	
	public String run(String code) throws CompileException, IOException {
		return this.run(code, true);
	}

	public String runFromFile(String path) throws CompileException, IOException {
		return this.run(
			String.join(
				System.lineSeparator(),
				Files.readAllLines(Paths.get(path))
			)
		);
	}

}
