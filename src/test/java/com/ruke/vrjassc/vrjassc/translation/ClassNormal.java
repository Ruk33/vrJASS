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
						+ "method foo takes nothing returns integer\n"
							+ "set this.bar = 2\n"
							+ "return this.p.bar\n"
						+ "endmethod\n"
					+ "endstruct";
		String expected = "globals\n"
							+ "hashtable vr_structs=InitHashtable()\n"
							+ "integer struct_Object_vr_type=1\n"
							+ "integer struct_Person_vr_type=2\n"
							+ "integer struct_Object_vr_instances=1\n"
							+ "integer struct_Person_bar=2\n"
						+ "endglobals\n"
						+ "function struct_Object_allocate takes nothing returns integer\n"
							+ "set struct_Object_vr_instances=struct_Object_vr_instances+1\n"
							+ "return struct_Object_vr_instances\n"
						+ "endfunction\n"
						+ "function struct_Person_foo takes integer this returns integer\n"
							+ "call SaveInteger(vr_structs, this, struct_Person_bar, 2)\n"
							+ "return LoadInteger(vr_structs, this, struct_Person_bar)\n"
						+ "endfunction";
		
		assertEquals(expected, this.run(code));
	}

}
