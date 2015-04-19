package test;

import static org.junit.Assert.*;

import org.junit.Test;

import util.Compile;
import util.ProjectPath;

public class ComparisonExpressionTest {

	@Test
	public void correct() {
		Compile compile = new Compile();
		String testPath = ProjectPath.getTest();
		String code =
				"function foo takes nothing returns nothing\n"
				+ "local boolean bar\n"
				+ "set bar=1==1\n"
				+ "set bar=1!=1\n"
				+ "set bar=1>1\n"
				+ "set bar=1>=1\n"
				+ "set bar=1<1\n"
				+ "set bar=1<=1\n"
				+ "endfunction";
		
		assertEquals(code, compile.run(code));
	}

}
