package com.ruke.vrjassc.vrjassc.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.antlr.v4.runtime.ANTLRInputStream;
import com.ruke.vrjassc.vrjassc.exception.CompileException;

public class Compile {

	private CompilerFacade compiler = new CompilerFacade();
	
	public String run(String code) throws CompileException, IOException {
		String replacedTabs = code.replace("\t", "    ");
		ANTLRInputStream is = new ANTLRInputStream(replacedTabs + "\n");
		
		return this.compiler.compile(is);
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
