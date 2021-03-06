package com.ruke.vrjassc.vrjassc.compiler;

import com.ruke.vrjassc.vrjassc.util.TestHelper;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ClassTest extends TestHelper {

	@Test
	public void genericAsParamArgs() {
		String code =
			"struct foo<e>\n" +
				"public method add takes e element\n" +
				"end\n" +
			"end\n" +
			"struct bar<e>\n" +
				"public method remove takes e element\n" +
				"end\n" +
			"end\n" +
			"function baz takes foo<integer> f, bar<string> b\n" +
				"call f.add(2)\n" +
				"call b.remove(\"lorem\")\n" +
			"end\n" +
			"function lorem\n" +
				"local foo<integer> f = cast 1 to foo\n" +
				"local bar<string> b = cast 2 to bar\n" +
				"call baz(f, b)\n" +
			"end";

		String expected =
			"globals\n" +
				"hashtable vrjass_structs=InitHashtable()\n" +
				"integer vtype=-1\n" +
			"endglobals\n" +
			"function struct_foo_add_integer takes integer this,integer element returns nothing\n" +
			"endfunction\n" +
			"function struct_bar_remove_string takes integer this,string element returns nothing\n" +
			"endfunction\n" +
			"function baz takes integer f,integer b returns nothing\n" +
				"call struct_foo_add_integer(f,2)\n" +
				"call struct_bar_remove_string(b,\"lorem\")\n" +
			"endfunction\n" +
			"function lorem takes nothing returns nothing\n" +
				"local integer f=1\n" +
				"local integer b=2\n" +
				"call baz(f,b)\n" +
			"endfunction";

		assertEquals(expected, this.run(code));
	}

	@Test
	public void returnGeneric() {
		String code =
			"struct foo<E>\n" +
			"end\n" +
			"struct bar\n" +
				"public method baz returns foo<string>\n" +
					"local foo<string> i = cast 1 to foo\n" +
					"return i\n" +
				"end\n" +
			"end";

		String expected =
			"globals\n" +
				"hashtable vrjass_structs=InitHashtable()\n" +
				"integer vtype=-1\n" +
			"endglobals\n" +
			"function struct_bar_baz takes integer this returns integer\n" +
				"local integer i=1\n" +
				"return i\n" +
			"endfunction";

		assertEquals(expected, this.run(code));
	}

	@Test
	@Ignore
	public void generic() {
		String code =
			"struct foo<e>\n" +
				"public method bar takes e element returns e\n" +
					"return element\n" +
				"end\n" +
				"public method lorem returns foo\n" +
					"return this\n" +
				"end\n" +
			"end\n" +
			"function baz\n" +
				"local foo<integer> fi\n" +
				"call fi.bar(1)\n" +
				"local foo<boolean> fb\n" +
				"call fb.bar(true)\n" +
				"call fb.lorem().bar(true)\n" +
				"call fi.lorem().bar(2)\n" +
			"end";

		String expected =
			"globals\n" +
				"hashtable vrjass_structs=InitHashtable()\n" +
				"integer vtype=-1\n" +
			"endglobals\n" +
			"function struct_foo_bar_integer takes integer this,integer element returns integer\n" +
				"return element\n" +
			"endfunction\n" +
			"function struct_foo_bar_boolean takes integer this,boolean element returns boolean\n" +
				"return element\n" +
			"endfunction\n" +
			"function struct_foo_lorem takes integer this returns integer\n" +
				"return this\n" +
			"endfunction\n" +
			"function baz takes nothing returns nothing\n" +
				"local integer fb=0\n" +
				"local integer fi=0\n" +
				"call struct_foo_bar_integer(fi,1)\n" +
				"call struct_foo_bar_boolean(fb,true)\n" +
				"call struct_foo_bar_boolean(struct_foo_lorem(fb),true)\n" +
				"call struct_foo_bar_integer(struct_foo_lorem(fi),2)\n" +
			"endfunction";

		assertEquals(expected, this.run(code));
	}

	@Test
	public void allocate() {
		String code =
		"interface obj\n" +
			"public static method allocate returns obj\n" +
		"end\n" +
		"struct defaultobj implements obj\n" +
			"public static method allocate returns obj\n" +
				"return cast 1 to defaultobj\n" +
			"end\n" +
		"end";

		String expected =
		"globals\n" +
			"hashtable vrjass_structs=InitHashtable()\n" +
			"integer vtype=-1\n" +
		"endglobals\n" +
		"function struct_defaultobj_allocate takes integer vrjass_type returns integer\n" +
			"call SaveInteger(vrjass_structs,1,vtype,vrjass_type)\n" +
			"return 1\n" +
		"endfunction\n" +
		"function obj_allocate_vtype takes integer vtype returns integer\n" +
			"if vtype==1 then\n" +
				"return struct_defaultobj_allocate(vtype)\n" +
			"else\n" +
				"return struct_defaultobj_allocate(vtype)\n" +
			"endif\n" +
		"endfunction";

		assertEquals(expected, this.run(code));
	}

	@Test
	@Ignore
	public void _super() {
		String code =
		"struct bar extends foo\n" +
			"public method ipsum\n" +
			"end\n" +
			"public method lorem\n" +
				"call super.lorem()\n" +
				"call super.ipsum()\n" +
			"end\n" +
		"end\n" +
		"struct foo extends baz\n" +
			"public method ipsum\n" +
			"end\n" +
		"end\n" +
		"struct baz\n" +
			"public method lorem\n" +
			"end\n" +
		"end";

		String expected =
		"globals\n" +
			"hashtable vrjass_structs=InitHashtable()\n" +
			"integer vtype=-1\n" +
		"endglobals\n" +
		"function struct_baz_foo_bar_ipsum takes integer this returns nothing\n" +
		"endfunction\n" +
		"function struct_baz_lorem takes integer this returns nothing\n" +
		"endfunction\n" +
		"function struct_baz_foo_ipsum takes integer this returns nothing\n" +
		"endfunction\n" +
		"function struct_baz_foo_bar_lorem takes integer this returns nothing\n" +
			"call struct_baz_lorem(this)\n" +
			"call struct_baz_foo_ipsum(this)\n" +
		"endfunction\n" +
		"function struct_baz_foo_ipsum_vtype takes integer this,integer vtype returns nothing\n" +
			"if vtype==1 then\n" +
				"call struct_baz_foo_bar_ipsum(this)\n" +
			"else\n" +
				"call struct_baz_foo_ipsum(this)\n" +
			"endif\n" +
		"endfunction";

		assertEquals(expected, this.run(code));
	}

	@Test
	@Ignore
	public void methodOverwrite() {
		String code =
			"struct foo\n"
				+ "public method lorem takes integer i\n"
				+ "endmethod\n"
				+ "public static method allocate returns foo\n"
					+ "local foo f = cast 1 to foo\n"
					+ "return f\n"
				+ "endmethod\n"
			+ "endstruct\n"
			+ "struct bar extends foo\n"
				+ "public method lorem takes integer i\n"
				+ "endmethod\n"
			+ "endstruct\n"
			+ "struct baz extends bar\n"
				+ "public method lorem takes integer i\n"
				+ "endmethod\n"
			+ "endstruct\n"
			+ "function ipsum\n"
				+ "local bar f = bar.allocate()\n"
				+ "call f.lorem(1)\n"
				+ "call cast f to foo.lorem(1)\n"
			+ "endfunction";
		
		String expected =
			"globals\n" + 
				"hashtable vrjass_structs=InitHashtable()\n" +
				"integer vtype=-1\n" +
			"endglobals\n" +
			"function struct_foo_lorem takes integer this,integer i returns nothing\n" +
			"endfunction\n" +
			"function struct_foo_bar_baz_lorem takes integer this,integer i returns nothing\n" +
			"endfunction\n" +
			"function struct_foo_bar_lorem takes integer this,integer i returns nothing\n" +
			"endfunction\n" +
			"function struct_foo_lorem_vtype takes integer this,integer vtype,integer i returns nothing\n" +
				"if vtype==3 then\n" +
					"call struct_foo_bar_baz_lorem(this,i)\n" +
				"elseif vtype==2 then\n" +
					"call struct_foo_bar_lorem(this,i)\n" +
				"else\n" +
					"call struct_foo_lorem(this,i)\n" +
				"endif\n" +
			"endfunction\n" +
			"function struct_foo_allocate takes integer vrjass_type returns integer\n" +
				"local integer f=1\n" +
				"call SaveInteger(vrjass_structs,f,vtype,vrjass_type)\n" +
				"return f\n" +
			"endfunction\n" +
			"function ipsum takes nothing returns nothing\n" +
				"local integer f=struct_foo_allocate(2)\n" +
				"call struct_foo_lorem_vtype(f,LoadInteger(vrjass_structs,f,vtype),1)\n" +
				"call struct_foo_lorem_vtype(f,1,1)\n" +
			"endfunction";
		
		assertEquals(expected, this.run(code));
	}
	
	@Test
	public void autoAssignType() {
		String code =
			"struct foo\n"
				+ "public static method allocate returns foo\n"
					+ "local foo f\n"
					+ "return f\n"
				+ "endmethod\n"
			+ "endstruct\n"
			+ "struct bar extends foo\n"
			+ "endstruct\n"
			+ "function lorem\n"
				+ "local foo f = foo.allocate()\n"
				+ "local bar b = bar.allocate()\n"
			+ "endfunction";
		
		String expected =
			"globals\n"
				+ "hashtable vrjass_structs=InitHashtable()\n"
				+ "integer vtype=-1\n"
			+ "endglobals\n"
			+ "function struct_foo_allocate takes integer vrjass_type returns integer\n"
				+ "local integer f=0\n"
				+ "call SaveInteger(vrjass_structs,f,vtype,vrjass_type)\n"
				+ "return f\n"
			+ "endfunction\n"
			+ "function lorem takes nothing returns nothing\n"
				+ "local integer f=struct_foo_allocate(1)\n"
				+ "local integer b=struct_foo_allocate(2)\n"
			+ "endfunction";
		
		assertEquals(expected, this.run(code));
	}
	
	@Test
	public void interfaceReturnsNothing() {
		String code =
			"interface foo\n"
				+ "public method bar\n"
			+ "endinterface\n"
			+ "struct a implements foo\n"
				+ "public method bar\n"
				+ "endmethod\n"
			+ "endstruct";
		
		String expected =
			"globals\n"
				+ "hashtable vrjass_structs=InitHashtable()\n"
				+ "integer vtype=-1\n"
			+ "endglobals\n"
			+ "function struct_a_bar takes integer this returns nothing\n"
			+ "endfunction\n"
			+ "function foo_bar_vtype takes integer this,integer vtype returns nothing\n"
				+ "if vtype==1 then\n"
					+ "call struct_a_bar(this)\n"
				+ "else\n"
					+ "call struct_a_bar(this)\n"
				+ "endif\n"
			+ "endfunction";
		
		assertEquals(expected, this.run(code));
	}
	
	@Test
	@Ignore
	public void interfaceTranslation() {
		String code =
			"globals\n"
				+ "lorem array lr\n"
			+ "endglobals\n"
			+ "interface lorem\n"
				+ "public method ipsum takes boolean b\n"
			+ "endinterface\n"
			+ "struct foo implements lorem\n"
				+ "public method ipsum takes boolean b\n"
				+ "endmethod\n"
			+ "endstruct\n"
			+ "struct bar implements lorem\n"
				+ "public method ipsum takes boolean b\n"
				+ "endmethod\n"
			+ "endstruct\n"
			+ "function dolor\n"
				+ "local foo f\n"
				+ "local bar b\n"
				+ "local lorem l = f\n"
				+ "if (true) then\n"
					+ "set l = b\n"
				+ "endif\n"
				+ "call lr[1].ipsum(false)\n"
				+ "call l.ipsum(false)\n"
			+ "endfunction";
		
		String expected =
			"globals\n"
				+ "integer array lr\n"
				+ "hashtable vrjass_structs=InitHashtable()\n"
				+ "integer vtype=-1\n"
			+ "endglobals\n"
			+ "function struct_bar_ipsum takes integer this,boolean b returns nothing\n"
			+ "endfunction\n"
			+ "function struct_foo_ipsum takes integer this,boolean b returns nothing\n"
			+ "endfunction\n"
			+ "function lorem_ipsum_vtype takes integer this,integer vtype,boolean b returns nothing\n"
				+ "if vtype==1 then\n"
					+ "call struct_foo_ipsum(this,b)\n"
				+ "else\n"
					+ "call struct_bar_ipsum(this,b)\n"
				+ "endif\n"
			+ "endfunction\n"
			+ "function dolor takes nothing returns nothing\n"
				+ "local integer f=0\n"
				+ "local integer b=0\n"
				+ "local integer l=f\n"
				+ "if (true)!=false then\n"
					+ "set l=b\n"
				+ "endif\n"
				+ "call lorem_ipsum_vtype(lr[1],LoadInteger(vrjass_structs,lr[1],vtype),false)\n"
				+ "call lorem_ipsum_vtype(l,LoadInteger(vrjass_structs,l,vtype),false)\n"
			+ "endfunction";
		
		assertEquals(expected, this.run(code));
	}
	
	@Test
	public void staticPropertyArray() {
		String code =
			"struct foo\n"
				+ "static real array bar\n"
				+ "static foo array f\n"
				+ "static method lorem\n"
					+ "set foo.bar[2]=2\n"
					+ "set foo.f[1] = foo.f[2]\n"
				+ "endmethod\n"
			+ "endstruct\n";
		
		String expected =
			"globals\n"+
			"real array struct_foo_bar\n"+
			"integer array struct_foo_f\n"+
			"hashtable vrjass_structs=InitHashtable()\n"+
			"integer vtype=-1\n"+
			"endglobals\n"+
			"function struct_foo_lorem takes nothing returns nothing\n"+
			"set struct_foo_bar[2]=2\n"+
			"set struct_foo_f[1]=struct_foo_f[2]\n"+
			"endfunction";
		
		assertEquals(expected, this.run(code));
	}
	
	@Test
	public void nullShouldTranslateToZero() {
		String code =
			"struct object\n"
				+ "public object e\n"
			+ "endstruct\n" +
			"function baz takes object o\n" +
			"endfunction\n"
			+ "function foo\n"
				+ "local object bar = null\n"
				+ "if (bar==null or null==bar) then\n"
				+ "endif\n"
				+ "set bar.e=null\n" +
				"call baz(null)\n"
			+ "endfunction";
		
		String expected =
			"globals\n" +
				"integer struct_object_e=1\n" +
				"hashtable vrjass_structs=InitHashtable()\n" +
				"integer vtype=-1\n" +
			"endglobals\n" +
			"function baz takes integer o returns nothing\n" +
			"endfunction\n" +
			"function foo takes nothing returns nothing\n"
				+ "local integer bar=0\n"
				+ "if (bar==0 or 0==bar) then\n"
				+ "endif\n"
				+ "call SaveInteger(vrjass_structs,bar,struct_object_e,0)\n" +
				"call baz(0)\n"
			+ "endfunction";
		
		assertEquals(expected, this.run(code));
	}
		
	@Test
	public void types() {
		String code =
			"struct foo\n"
				+ "public player p\n"
				+ "method bar\n"
					+ "set this.p=this.p\n"
				+ "endmethod\n"
			+ "endstruct";
		
		String expected =
			"globals\n" +
				"integer struct_foo_p=1\n" +
				"hashtable vrjass_structs=InitHashtable()\n" +
				"integer vtype=-1\n" +
			"endglobals\n" +
			"function struct_foo_bar takes integer this returns nothing\n" +
			"call SavePlayerHandle(vrjass_structs,this,struct_foo_p,LoadPlayerHandle(vrjass_structs,this,struct_foo_p))\n" +
			"endfunction";
		
		assertEquals(expected, this.run(code));
	}
	
	@Test
	public void scopedTypes() {
		String code =
			"library foo\n"
				+ "public struct foo\n"
					+ "public method baz\n"
					+ "endmethod\n"
				+ "endstruct\n"
			+ "endlibrary\n"
			+ "struct bar\n"
				+ "foo.foo l\n"
				+ "method baz\n"
					+ "call this.l.baz()\n"
				+ "endmethod\n"
			+ "endstruct";
		
		String expected =
			"globals\n"
				+ "integer struct_bar_l=1\n"
				+ "hashtable vrjass_structs=InitHashtable()\n"
				+ "integer vtype=-1\n"
			+ "endglobals\n"
			+ "function struct_foo_foo_baz takes integer this returns nothing\n"
			+ "endfunction\n"
			+ "function struct_bar_baz takes integer this returns nothing\n"
				+ "call struct_foo_foo_baz(LoadInteger(vrjass_structs,this,struct_bar_l))\n"
			+ "endfunction";
		
		assertEquals(expected, this.run(code));
	}
	
	@Test
	public void scopes() {
		String code =
			"struct foo\n"
				+ "integer baz\n"
				+ "method bar takes foo i\n"
					+ "set i.baz=1\n"
				+ "endmethod\n"
			+ "endstruct";
		
		String expected =
			"globals\n"
				+ "integer struct_foo_baz=1\n"
				+ "hashtable vrjass_structs=InitHashtable()\n"
				+ "integer vtype=-1\n"
			+ "endglobals\n"
			+ "function struct_foo_bar takes integer this,integer i returns nothing\n"
				+ "call SaveInteger(vrjass_structs,i,struct_foo_baz,1)\n"
			+ "endfunction";
		
		assertEquals(expected, this.run(code));
	}
	
	@Test
	public void cast() {
		String code =
			"struct foo\n"
				+ "static integer instances\n"
				+ "static method allocate takes nothing returns foo\n"
					+ "set foo.instances += 1\n"
					+ "call cast 0 to foo.bar()\n"
					+ "call (cast 1 to foo).bar()\n"
					+ "call BJDebugMsg(\"instance \" + I2S(cast foo.allocate() to integer))\n"
					+ "return cast foo.instances to foo\n"
				+ "endmethod\n"
				+ "method bar takes nothing returns nothing\n"
				+ "endmethod\n"
			+ "endstruct";
		
		String expected =
			"globals\n"
				+ "integer struct_foo_instances=0\n"
				+ "hashtable vrjass_structs=InitHashtable()\n"
				+ "integer vtype=-1\n"
			+ "endglobals\n"
			+ "function struct_foo_bar takes integer this returns nothing\n"
			+ "endfunction\n"
			+ "function struct_foo_allocate takes integer vrjass_type returns integer\n"
				+ "set struct_foo_instances=struct_foo_instances+1\n"
				+ "call struct_foo_bar(0)\n"
				+ "call struct_foo_bar((1))\n" // <-- the power
				+ "call BJDebugMsg(\"instance \"+I2S(struct_foo_allocate(1)))\n"
				+ "call SaveInteger(vrjass_structs,struct_foo_instances,vtype,vrjass_type)\n"
				+ "return struct_foo_instances\n"
			+ "endfunction";
		
		assertEquals(expected, this.run(code));
	}
	
	@Test
	public void _extends() {
		String code =
			"struct foo\n"
				+ "public method baz takes nothing returns nothing\n"
				+ "endmethod\n"
			+ "endstruct\n"
			+ "struct bar extends foo\n"
				+ "public method n takes nothing returns nothing\n"
					+ "call this.baz()\n"
					+ "call this.e()\n"
				+ "endmethod\n"
				+ "public method e takes nothing returns nothing\n"
				+ "endmethod\n"
				+ "public static method a takes nothing returns nothing\n"
				+ "endmethod\n"
			+ "endstruct";
		
		String expected =
			"globals\n"
				+ "hashtable vrjass_structs=InitHashtable()\n"
				+ "integer vtype=-1\n"
			+ "endglobals\n"
			+ "function struct_foo_baz takes integer this returns nothing\n"
			+ "endfunction\n"
			+ "function struct_foo_bar_e takes integer this returns nothing\n"
			+ "endfunction\n"			
			+ "function struct_foo_bar_n takes integer this returns nothing\n"
				+ "call struct_foo_baz(this)\n"
				+ "call struct_foo_bar_e(this)\n"
			+ "endfunction\n"
			+ "function struct_foo_bar_a takes nothing returns nothing\n"
			+ "endfunction";
		
		assertEquals(expected, this.run(code));
	}
	
	@Test
	public void test() {
		String code =
				"struct foo\n"
					+ "private static integer a=1\n"
					+ "private static integer b\n"
					+ "private integer bar\n"
					+ "private foo that\n"
					+ "private method baz takes nothing returns nothing\n"
						+ "set this.that.bar += 1\n"
						+ "call this.baz()\n"
					+ "endmethod\n"
				+ "endstruct";
		
		String expected =
				"globals\n"
					+ "integer struct_foo_a=0\n"
					+ "integer struct_foo_b=0\n"
					+ "integer struct_foo_bar=1\n"
					+ "integer struct_foo_that=2\n"
					+ "hashtable vrjass_structs=InitHashtable()\n"
					+ "integer vtype=-1\n"
				+ "endglobals\n"
				+ "function struct_foo_baz takes integer this returns nothing\n"
					+ "call SaveInteger("
						+ "vrjass_structs,"
						+ "LoadInteger("
							+ "vrjass_structs,"
							+ "this,"
							+ "struct_foo_that"
						+ "),"
						+ "struct_foo_bar,"
						+ "LoadInteger("
							+ "vrjass_structs,"
							+ "LoadInteger("
								+ "vrjass_structs,"
								+ "this,"
								+ "struct_foo_that"
							+ "),"
							+ "struct_foo_bar"
							+ ")+1"
						+ ")\n"
					+ "call struct_foo_baz(this)\n"
				+ "endfunction";
		
		assertEquals(expected, this.run(code));
	}
	
	@Test
	public void array() {
		String code =
			"struct foo\n"
				+ "integer array bar\n"
				+ "integer i\n"
				+ "method baz takes nothing returns nothing\n"
					+ "set this.bar[this.i] = 1\n"
				+ "endmethod\n"
			+ "endstruct";
		
		String expected =
			"globals\n"
				+ "integer struct_foo_bar=1\n"
				+ "integer struct_foo_i=2\n"
				+ "hashtable vrjass_structs=InitHashtable()\n"
				+ "integer vtype=-1\n"
			+ "endglobals\n"
			+ "function struct_foo_baz takes integer this returns nothing\n"
				+ "call SaveInteger(vrjass_structs,this,struct_foo_bar*8191-IMinBJ(LoadInteger(vrjass_structs,this,struct_foo_i),8191),1)\n"
			+ "endfunction";
		
		assertEquals(expected, this.run(code));
	}
	
	@Test
	public void onInit() {
		String code =
			"struct bar\n"
				+ "static method onInit\n"
				+ "endmethod\n"
			+ "endstruct\n"
			+ "struct foo extends bar\n"
				+ "static method onInit\n"
				+ "endmethod\n"
			+ "endstruct\n"
			+ "function main takes nothing returns nothing\n"
				+ "call main()\n"
			+ "endfunction";
		
		String expected =
			"globals\n"
				+ "hashtable vrjass_structs=InitHashtable()\n"
				+ "integer vtype=-1\n"
			+ "endglobals\n"
			+ "function struct_bar_onInit takes nothing returns nothing\n"
			+ "endfunction\n"
			+ "function struct_bar_foo_onInit takes nothing returns nothing\n"
			+ "endfunction\n"
			+ "function main takes nothing returns nothing\n"
				+ "call main()\n"
				+ "call ExecuteFunc(\"struct_bar_onInit\")\n"
				+ "call ExecuteFunc(\"struct_bar_foo_onInit\")\n"
			+ "endfunction";
		
		assertEquals(expected, this.run(code));
	}

}
