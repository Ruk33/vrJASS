package com.ruke.vrjassc.vrjassc;

import static org.junit.Assert.assertEquals;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.ruke.vrjassc.vrjassc.exception.ImplementAbstractMethodClassException;
import com.ruke.vrjassc.vrjassc.exception.TooManyExtendClassException;
import com.ruke.vrjassc.vrjassc.exception.UndefinedMethodException;
import com.ruke.vrjassc.vrjassc.exception.UndefinedPropertyException;
import com.ruke.vrjassc.vrjassc.util.Compile;

public class ClassTest {

	@Rule
	public ExpectedException expectedEx = ExpectedException.none();

	@Test
	public void defaultAllocation() {
		Compile compile = new Compile();
		String code = "struct Foo\n" + "endstruct";

		String result = "globals" + System.lineSeparator()
				+ "integer array struct_Foo_s__recycle" + System.lineSeparator()
				+ "endglobals" + System.lineSeparator()
				+ "function struct_Foo_setProperties takes integer this returns nothing" + System.lineSeparator()
				+ "endfunction" + System.lineSeparator()
				+ "function struct_s_Foo_allocate takes nothing returns integer" + System.lineSeparator()
				+ "local integer instance=struct_Foo_s__recycle[0]" + System.lineSeparator()
				+ "if (struct_Foo_s__recycle[0]==0) then" + System.lineSeparator()
				+ "set struct_Foo_s__recycle[0]=instance+1" + System.lineSeparator()
				+ "else" + System.lineSeparator()
				+ "set struct_Foo_s__recycle[0]=struct_Foo_s__recycle[instance]" + System.lineSeparator()
				+ "endif" + System.lineSeparator()
				+ "call struct_Foo_setProperties(instance)" + System.lineSeparator()
				+ "return instance" + System.lineSeparator()
				+ "endfunction" + System.lineSeparator()
				+ "function struct_Foo_deallocate takes integer this returns nothing" + System.lineSeparator()
				+ "set struct_Foo_s__recycle[this]=struct_Foo_s__recycle[0]" + System.lineSeparator()
				+ "set struct_Foo_s__recycle[0]=this" + System.lineSeparator()
				+ "endfunction";

		assertEquals(result, compile.run(code));
	}

	@Test
	public void extendsArray() {
		Compile compile = new Compile();
		String code = "struct Foo extends array\n" + "endstruct";

		assertEquals("", compile.run(code));
	}

	@Test
	public void visibilityNoScope() {
		Compile compile = new Compile();
		String code = "struct Foo extends array\n"
				+ "method bar takes nothing returns nothing\n"
				+ "endmethod\n"
				+ "private method nope takes nothing returns nothing\n"
				+ "call this.bar()\n"
				+ "endmethod\n"
				+ "public method yep takes nothing returns nothing\n"
				+ "call this.nope()\n"
				+ "endmethod\n"
				+ "endstruct";

		String result = "function struct_Foo_bar takes integer this returns nothing"
				+ System.lineSeparator()
				+ System.lineSeparator()
				+ "endfunction" + System.lineSeparator()
				+ "function struct_Foo__nope takes integer this returns nothing" + System.lineSeparator()
				+ "call struct_Foo_bar(this)" + System.lineSeparator()
				+ "endfunction" + System.lineSeparator()
				+ "function struct_Foo_yep takes integer this returns nothing" + System.lineSeparator()
				+ "call struct_Foo__nope(this)" + System.lineSeparator()
				+ "endfunction";

		assertEquals(result, compile.run(code));
	}

	@Test
	public void properties() {
		Compile compile = new Compile();
		String code = "struct Foo extends array\n"
				+ "integer i\n"
				+ "method bar takes nothing returns integer\n"
				+ "return this.i\n"
				+ "endmethod\n"
				+ "endstruct";

		String result = "globals" + System.lineSeparator()
				+ "integer array struct_Foo_i" + System.lineSeparator()
				+ "endglobals" + System.lineSeparator()
				+ "function struct_Foo_bar takes integer this returns integer" + System.lineSeparator()
				+ "return struct_Foo_i[this]" + System.lineSeparator()
				+ "endfunction";

		assertEquals(result, compile.run(code));
	}

	@Test
	public void undefinedProperty() {
		Compile compile = new Compile();
		String code = "struct Foo extends array\n"
				+ "method bar takes nothing returns nothing\n"
				+ "local integer i = this.i\n" + "endmethod\n" + "endstruct";

		expectedEx.expect(UndefinedPropertyException.class);
		expectedEx
				.expectMessage("3:23 Class <Foo> does not have a property called <i>");

		compile.run(code);
	}

	@Test
	public void undefinedMethod() {
		Compile compile = new Compile();
		String code = "struct Foo extends array\n"
				+ "method bar takes nothing returns nothing\n"
				+ "call this.nope()\n" + "endmethod\n" + "endstruct";

		expectedEx.expect(UndefinedMethodException.class);
		expectedEx
				.expectMessage("3:10 Class <Foo> does not have a method called <nope>");

		compile.run(code);
	}

	@Test
	public void accessMemberThroughVariables() {
		Compile compile = new Compile();
		String code = "struct Foo extends array\n"
				+ "integer i\n"
				+ "private method nope takes nothing returns nothing\n"
				+ "endmethod\n"
				+ "method bar takes nothing returns integer\n"
				+ "local Foo f=this\n"
				+ "local integer a=f.i\n"
				+ "call f.nope()\n"
				+ "return a\n"
				+ "endmethod\n"
				+ "endstruct";

		String result = "globals" + System.lineSeparator()
				+ "integer array struct_Foo_i" + System.lineSeparator()
				+ "endglobals" + System.lineSeparator()
				+ "function struct_Foo__nope takes integer this returns nothing"
				+ System.lineSeparator()
				+ System.lineSeparator()
				+ "endfunction" + System.lineSeparator()
				+ "function struct_Foo_bar takes integer this returns integer" + System.lineSeparator()
				+ "local integer f=this" + System.lineSeparator()
				+ "local integer a=struct_Foo_i[f]" + System.lineSeparator()
				+ "call struct_Foo__nope(f)" + System.lineSeparator()
				+ "return a" + System.lineSeparator()
				+ "endfunction";

		assertEquals(result, compile.run(code));
	}

	@Test
	public void accessMemberThroughMethods() {
		Compile compile = new Compile();
		String code = "struct Foo extends array\n"
				+ "integer i\n"
				+ "private method nope takes nothing returns Foo\n"
				+ "return this\n"
				+ "endmethod\n"
				+ "method bar takes nothing returns integer\n"
				+ "local Foo f=this.nope()\n"
				+ "local integer a=f.i\n"
				+ "call f.nope()\n"
				+ "return a\n"
				+ "endmethod\n"
				+ "endstruct";

		String result = "globals" + System.lineSeparator()
				+ "integer array struct_Foo_i" + System.lineSeparator()
				+ "endglobals" + System.lineSeparator()
				+ "function struct_Foo__nope takes integer this returns integer" + System.lineSeparator()
				+ "return this" + System.lineSeparator()
				+ "endfunction" + System.lineSeparator()
				+ "function struct_Foo_bar takes integer this returns integer" + System.lineSeparator()
				+ "local integer f=struct_Foo__nope(this)" + System.lineSeparator()
				+ "local integer a=struct_Foo_i[f]" + System.lineSeparator()
				+ "call struct_Foo__nope(f)" + System.lineSeparator()
				+ "return a" + System.lineSeparator()
				+ "endfunction";

		assertEquals(result, compile.run(code));
	}

	@Test
	public void staticMethod() {
		Compile compile = new Compile();
		String code = "struct Foo extends array\n"
				+ "private static method nope takes integer a returns nothing\n"
				+ "endmethod\n"
				+ "static method bar takes nothing returns nothing\n"
				+ "call Foo.nope(1)\n"
				+ "call Foo.bar()\n"
				+ "endmethod\n"
				+ "endstruct";

		String result = "function struct_s_Foo__nope takes integer a returns nothing"
				+ System.lineSeparator()
				+ System.lineSeparator()
				+ "endfunction" + System.lineSeparator()
				+ "function struct_s_Foo_bar takes nothing returns nothing" + System.lineSeparator()
				+ "call struct_s_Foo__nope(1)" + System.lineSeparator()
				+ "call struct_s_Foo_bar()" + System.lineSeparator()
				+ "endfunction";

		assertEquals(result, compile.run(code));
	}

	@Test
	public void staticProperty() {
		Compile compile = new Compile();
		String code = "struct Foo extends array\n"
				+ "public static integer i = 3\n"
				+ "endstruct\n"
				+ "function bar takes nothing returns integer\n"
				+ "return Foo.i\n"
				+ "endfunction";

		String result = "globals" + System.lineSeparator()
				+ "integer struct_Foo_i=3" + System.lineSeparator()
				+ "endglobals" + System.lineSeparator()
				+ "function bar takes nothing returns integer" + System.lineSeparator()
				+ "return struct_Foo_i" + System.lineSeparator()
				+ "endfunction";

		assertEquals(result, compile.run(code));
	}

	@Test
	public void creatingInstance() {
		Compile compile = new Compile();
		String code = "struct Foo extends array\n"
				+ "public static Foo instances\n"
				+ "public integer i\n"
				+ "public static method create takes nothing returns Foo\n"
				+ "return Foo.instances\n"
				+ "endmethod\n"
				+ "endstruct\n"
				+ "function bar takes nothing returns integer\n"
				+ "local Foo instance = Foo.create()\n"
				+ "set instance.i=3\n"
				+ "return instance.i\n"
				+ "endfunction";

		String result = "globals" + System.lineSeparator()
				+ "integer struct_Foo_instances" + System.lineSeparator()
				+ "integer array struct_Foo_i" + System.lineSeparator()
				+ "endglobals" + System.lineSeparator()
				+ "function struct_s_Foo_create takes nothing returns integer" + System.lineSeparator()
				+ "return struct_Foo_instances" + System.lineSeparator()
				+ "endfunction" + System.lineSeparator()
				+ "function bar takes nothing returns integer" + System.lineSeparator()
				+ "local integer instance=struct_s_Foo_create()" + System.lineSeparator()
				+ "set struct_Foo_i[instance]=3" + System.lineSeparator()
				+ "return struct_Foo_i[instance]" + System.lineSeparator()
				+ "endfunction";

		assertEquals(result, compile.run(code));
	}

	@Test
	public void classExtendsArray() {
		Compile compile = new Compile();
		String code = "struct Person extends array\n"
				+ "private string name\n"
				+ "public method getName takes nothing returns string\n"
				+ "return this.name\n"
				+ "endmethod\n"
				+ "endstruct\n"
				+ "struct Ruke extends Person\n"
				+ "private string lastname\n"
				+ "public method getLastName takes nothing returns string\n"
				+ "return this.lastname\n"
				+ "endmethod\n"
				+ "public method getFullName takes nothing returns string\n"
				+ "return this.getLastName() + \" \" + this.getName()\n"
				+ "endmethod\n"
				+ "endstruct";

		String result = "globals" + System.lineSeparator()
				+ "string array struct_Person_name" + System.lineSeparator()
				+ "string array struct_Ruke_lastname" + System.lineSeparator()
				+ "endglobals" + System.lineSeparator()
				+ "function struct_Person_getName takes integer this returns string" + System.lineSeparator()
				+ "return struct_Person_name[this]" + System.lineSeparator()
				+ "endfunction" + System.lineSeparator()
				+ "function struct_Ruke_getLastName takes integer this returns string" + System.lineSeparator()
				+ "return struct_Ruke_lastname[this]" + System.lineSeparator()
				+ "endfunction" + System.lineSeparator()
				+ "function struct_Ruke_getFullName takes integer this returns string" + System.lineSeparator()
				+ "return struct_Ruke_getLastName(this)+\" \"+struct_Person_getName(this)" + System.lineSeparator()
				+ "endfunction";

		assertEquals(result, compile.run(code));
	}
	
	@Test
	public void overritingAndUsingSuper() {
		Compile compile = new Compile();
		String code = "struct Person extends array\n"
				+ "private string name\n"
				+ "public method getName takes nothing returns string\n"
				+ "return this.name\n"
				+ "endmethod\n"
				+ "endstruct\n"
				+ "struct Pirate extends Person\n"
				+ "private string lastname\n"
				+ "public method getName takes nothing returns string\n"
				+ "return \"yarrr \" + super.getName()\n"
				+ "endmethod\n"
				+ "public method getLastName takes nothing returns string\n"
				+ "return this.lastname\n"
				+ "endmethod\n"
				+ "public method getFullName takes nothing returns string\n"
				+ "return this.getLastName() + \" \" + this.getName()\n"
				+ "endmethod\n"
				+ "endstruct";

		String result = "globals" + System.lineSeparator()
				+ "string array struct_Person_name" + System.lineSeparator()
				+ "string array struct_Pirate_lastname" + System.lineSeparator()
				+ "endglobals" + System.lineSeparator()
				+ "function struct_Person_getName takes integer this returns string" + System.lineSeparator()
				+ "return struct_Person_name[this]" + System.lineSeparator()
				+ "endfunction" + System.lineSeparator()
				+ "function struct_Pirate_getName takes integer this returns string" + System.lineSeparator()
				+ "return \"yarrr \"+struct_Person_getName(this)" + System.lineSeparator()
				+ "endfunction" + System.lineSeparator()
				+ "function struct_Pirate_getLastName takes integer this returns string" + System.lineSeparator()
				+ "return struct_Pirate_lastname[this]" + System.lineSeparator()
				+ "endfunction" + System.lineSeparator()
				+ "function struct_Pirate_getFullName takes integer this returns string" + System.lineSeparator()
				+ "return struct_Pirate_getLastName(this)+\" \"+struct_Pirate_getName(this)" + System.lineSeparator()
				+ "endfunction";

		assertEquals(result, compile.run(code));
	}
	
	@Test
	public void extendSeveralClasses() {
		Compile compile = new Compile();
		String code = "struct Foo extends array\n"
				+ "endstruct\n"
				+ "struct Bar extends array\n"
				+ "endstruct\n"
				+ "struct Nope extends Foo, Bar\n"
				+ "endstruct";

		expectedEx.expect(TooManyExtendClassException.class);
		expectedEx.expectMessage("5:20 Classes can only extend from one class. <Nope> extends from 2");

		compile.run(code);
	}
	
	@Test
	public void implementAllAbstractMethods() {
		Compile compile = new Compile();
		String code = "interface Person\n"
				+ "public method getName takes nothing returns string\n"
				+ "endinterface\n"
				+ "struct Pirate extends Person\n"
				+ "endstruct\n";

		expectedEx.expect(ImplementAbstractMethodClassException.class);
		expectedEx.expectMessage("4:7 Class <Pirate> must implements all abstract methods");

		compile.run(code);
	}
	
	@Test
	public void implementAllAbstractMethodsSameWay() {
		Compile compile = new Compile();
		String code = "interface Person\n"
				+ "public method setName takes string name returns Person\n"
				+ "endinterface\n"
				+ "struct Pirate extends Person\n"
				+ "public method setName takes nothing returns string\n"
				+ "endmethod\n"
				+ "endstruct\n";

		expectedEx.expect(ImplementAbstractMethodClassException.class);
		expectedEx.expectMessage("4:7 Class <Pirate> must implements all abstract methods");

		compile.run(code);
	}
	
	@Test
	public void defaultPropertyValue() {
		Compile compile = new Compile();
		String code = "struct Person\n"
				+ "integer age=5\n"
				+ "endstruct";

		String result = "globals" + System.lineSeparator()
			+ "integer array struct_Person_age" + System.lineSeparator()
			+ "integer array struct_Person_s__recycle" + System.lineSeparator()
			+ "endglobals" + System.lineSeparator()
			+ "function struct_Person_setProperties takes integer this returns nothing" + System.lineSeparator()
			+ "set struct_Person_age[this]=5" + System.lineSeparator()
			+ "endfunction" + System.lineSeparator()
			+ "function struct_s_Person_allocate takes nothing returns integer" + System.lineSeparator()
			+ "local integer instance=struct_Person_s__recycle[0]" + System.lineSeparator()
			+ "if (struct_Person_s__recycle[0]==0) then" + System.lineSeparator()
			+ "set struct_Person_s__recycle[0]=instance+1" + System.lineSeparator()
			+ "else" + System.lineSeparator()
			+ "set struct_Person_s__recycle[0]=struct_Person_s__recycle[instance]" + System.lineSeparator()
			+ "endif" + System.lineSeparator()
			+ "call struct_Person_setProperties(instance)" + System.lineSeparator()
			+ "return instance" + System.lineSeparator()
			+ "endfunction" + System.lineSeparator()
			+ "function struct_Person_deallocate takes integer this returns nothing" + System.lineSeparator()
			+ "set struct_Person_s__recycle[this]=struct_Person_s__recycle[0]" + System.lineSeparator()
			+ "set struct_Person_s__recycle[0]=this" + System.lineSeparator()
			+ "endfunction";

		assertEquals(result, compile.run(code));
	}
	
	@Test
	public void thistype() {
		Compile compile = new Compile();
		String code = "struct Person extends array\n"
				+ "private static method getPersonCount takes nothing returns integer\n"
				+ "return 5"
				+ "endmethod\n"
				+ "private method howManyPerson takes nothing returns integer\n"
				+ "return thistype.getPersonCount()\n"
				+ "endmethod\n"
				+ "endstruct";

		String result = "function struct_s_Person__getPersonCount takes nothing returns integer" + System.lineSeparator()
			+ "return 5" + System.lineSeparator()
			+ "endfunction" + System.lineSeparator()
			+ "function struct_Person__howManyPerson takes integer this returns integer" + System.lineSeparator()
			+ "return struct_s_Person__getPersonCount()" + System.lineSeparator()
			+ "endfunction";

		assertEquals(result, compile.run(code));
	}
	
	@Test
	public void allocatingDeallocating() {
		Compile compile = new Compile();
		String code = "struct Person\n"
				+ "private static method foo takes nothing returns nothing\n"
				+ "local Person person = thistype.allocate()\n"
				+ "call person.deallocate()\n"
				+ "endmethod\n"
				+ "endstruct";

		ExpectedException.none();
		
		compile.run(code);
	}

}
