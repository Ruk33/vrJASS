package com.ruke.vrjassc.vrjassc.translation;

import static org.junit.Assert.*;

import org.junit.Test;

import com.ruke.vrjassc.vrjassc.TestHelper;

public class ClassNormal extends TestHelper {

	@Test
	public void normal() {
		String code = "struct Person\n"
						+ "integer bar\n"
						+ "Person p\n"
						+ "Person array a\n"
						+ "method foo takes nothing returns integer\n"
							+ "set this.bar = 2\n"
							+ "set this.a[3]=5\n"
							+ "set this.a[this.bar].bar = 3\n"
							+ "return this.p.bar\n"
						+ "endmethod\n"
					+ "endstruct";
		String expected = "globals\n"
							+ "hashtable vr_structs=InitHashtable()\n"
							+ "integer struct_Person_vr_type=1\n"
							+ "integer struct_Person_bar=1\n"
							+ "integer struct_Person_p=2\n"
							+ "integer struct_Person_a=3\n"
						+ "endglobals\n"
						+ "function struct_Person_foo takes integer this returns integer\n"
							+ "call SaveInteger(null,this,struct_Person_bar,2)\n"
							+ "call SaveInteger(null,this,struct_Person_a*8191-IMinBJ(3,8191),5)\n"
							+ "call SaveInteger(null,LoadInteger(null,this,struct_Person_a*8191-IMinBJ(LoadInteger(null,this,struct_Person_bar),8191)),struct_Person_bar,3)\n"
							+ "return LoadInteger(null,LoadInteger(null,this,struct_Person_p),struct_Person_bar)\n"
						+ "endfunction";
		
		assertEquals(expected, this.run(code));
	}

}
