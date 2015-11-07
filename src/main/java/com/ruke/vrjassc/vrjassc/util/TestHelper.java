package com.ruke.vrjassc.vrjassc.util;

import java.io.IOException;

import org.junit.Rule;
import org.junit.rules.ExpectedException;

import com.ruke.vrjassc.vrjassc.exception.CompileException;

public abstract class TestHelper {

	@Rule
	public ExpectedException expectedEx = ExpectedException.none();
	
	protected Compile compile = new Compile();
	
	protected String runFromFile(String path) throws IOException {
		return this.compile.runFromFile(path);
	}
	
	protected String run(String code) throws CompileException {
		try {
			return this.compile.run(code);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return code;
	}
	
}
