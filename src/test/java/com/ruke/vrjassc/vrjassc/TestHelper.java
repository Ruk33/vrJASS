package com.ruke.vrjassc.vrjassc;

import java.io.IOException;

import org.junit.Rule;
import org.junit.rules.ExpectedException;

import com.ruke.vrjassc.vrjassc.exception.CompileException;
import com.ruke.vrjassc.vrjassc.util.Compile;

public abstract class TestHelper {

	@Rule
	public ExpectedException expectedEx = ExpectedException.none();
	
	protected Compile compile = new Compile();
	
	protected String runFromFile(String path) throws IOException {
		return this.compile.runFromFile(path);
	}
	
	protected String run(String code) throws CompileException {
		return this.compile.run(code);
	}
	
}
