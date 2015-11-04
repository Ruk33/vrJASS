package com.ruke.vrjassc.vrjassc.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.antlr.v4.runtime.ANTLRInputStream;
import com.ruke.vrjassc.vrjassc.exception.CompileException;

public class Compile {

	private CompilerFacade compiler = new CompilerFacade();
	
	protected String commonPath;
	protected String blizzardPath;
	protected String compiled;
	
	public Compile setCommonPath(String path) {
		this.commonPath = path;
		return this;
	}

	public Compile setBlizzardPath(String path) {
		this.blizzardPath = path;
		return this;
	}

	public String run(String code) throws CompileException {
		String replacedTabs = code.replace("\t", "    ");
		ANTLRInputStream is = new ANTLRInputStream(replacedTabs + "\n");
		
		return this.compiler.compile(is);
	}

	public String runFromFile(String path) throws IOException {
		return this.run(
			String.join(
				System.lineSeparator(),
				Files.readAllLines(Paths.get(path))
			)
		);
	}

}
