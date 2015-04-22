package test;

import static org.junit.Assert.*;

import org.junit.Test;

import util.Compile;

public class ClassTest {

	@Test
	public void correct() {
		Compile compile = new Compile();
		String code =
				"struct Foo\n"
				+ "endstruct";
		
		String result =
				"globals\n"
				+ "integer array struct_Foo_s__recycle\n"
				+ "endglobals\n"
				+ "function struct_s_Foo_allocate takes nothing returns integer\n"
				+ "local integer instance=struct_Foo_s__recycle[0]\n"
				+ "if (struct_Foo_s__recycle[0]==0) then\n"
				+ "set struct_Foo_s__recycle[0]=instance+1\n"
				+ "else\n"
				+ "set struct_Foo_s__recycle[0]=struct_Foo_s__recycle[instance]\n"
				+ "endif\n"
				+ "return instance\n"
				+ "endfunction\n"
				+ "function struct_Foo_deallocate takes integer this returns nothing\n"
				+ "set struct_Foo_s__recycle[this]=struct_Foo_s__recycle[0]\n"
				+ "set struct_Foo_s__recycle[0]=this\n"
				+ "endfunction";
		
		assertEquals(result, compile.run(code));
	}
	
	@Test
	public void extendsArray() {
		Compile compile = new Compile();
		String code =
				"struct Foo extends array\n"
				+ "endstruct";
		
		assertEquals("", compile.run(code));
	}

}
