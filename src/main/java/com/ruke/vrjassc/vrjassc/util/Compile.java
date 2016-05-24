package com.ruke.vrjassc.vrjassc.util;

import com.ruke.vrjassc.vrjassc.exception.CompileException;
import org.antlr.v4.runtime.ANTLRInputStream;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Compile {

	private CompilerFacade compiler = new CompilerFacade();

	public CompilerFacade getCompiler() {
		return this.compiler;
	}

	public void setCommonPath(String path) {
		this.compiler.commonPath = path;
	}

	public void setBlizzardPath(String path) {
		this.compiler.blizzardPath = path;
	}

	public String run(String code, boolean translate) throws CompileException, IOException {
		String replacedTabs = code.replace("\t", "    ") + "\n";
		ANTLRInputStream is = new ANTLRInputStream(replacedTabs);
		
		this.compiler.translateCode = translate;

		return this.compiler.compile(is);
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
