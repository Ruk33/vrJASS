package com.ruke.vrjassc.vrjassc.validator;

import com.ruke.vrjassc.vrjassc.exception.*;
import com.ruke.vrjassc.vrjassc.util.TestHelper;
import org.junit.Test;

public class ClassTest extends TestHelper {

	@Test
	public void mustBeCompatibleGeneric() {
		this.expectedEx.expect(IncompatibleTypeException.class);
		this.expectedEx.expectMessage("5:7 Element <bar> must have/return a value of type <foo<integer>> but given <string>");
		this.run(
			"public struct foo<E>\n" +
			"end\n" +
			"function bar returns foo<integer>\n" +
				"local foo<string> f = cast 1 to foo\n" +
				"return f\n" +
			"end"
		);
	}

	@Test
	public void genericsInStruct() {
		this.expectedEx.expect(IncompatibleTypeException.class);
		this.expectedEx.expectMessage("14:7 Element <element> must have/return a value of type <integer> but given <boolean>");
		this.run(
			"struct foo<e>\n" +
				"e fe\n" +
				"public method getFe returns e\n" +
					"return this.fe\n" +
				"end\n" +
				"public method bar takes e element returns e\n" +
					"set this.fe = e\n" +
					"return e\n" +
				"end\n" +
			"end\n" +
			"function baz\n" +
				"local foo<integer> f\n" +
				"call f.bar(2 + f.getFe())\n" +
				"call f.bar(true)\n" +
			"end"
		);
	}

	@Test
	public void extendsStructNamespace() {
		this.expectedEx.none();
		this.run(
		"library foo\n" +
			"public struct bar\n" +
			"end\n" +
		"end\n" +
		"struct baz extends foo.bar\n" +
		"end"
		);
	}

	@Test
	public void extendsInterfaceNamespace() {
		this.expectedEx.none();
		this.run(
		"library foo\n" +
			"public interface bar\n" +
			"end\n" +
		"end\n" +
		"struct baz implements foo.bar\n" +
		"end"
		);
	}

	@Test
	public void implementationMustShareSameVisibility() {
		this.expectedEx.expect(ImplementationVisibilityException.class);
		this.expectedEx.expectMessage("5:7 Method <baz> must be PUBLIC");
		this.run(
		"interface foo\n" +
			"public method baz\n" +
		"end\n" +
		"struct bar implements foo\n" +
			"method baz\n" +
			"end\n" +
		"end"
		);
	}

	@Test
	public void childStructShouldNotHaveAccessToPrivateProperty() {
		this.expectedEx.expect(UndefinedSymbolException.class);
		this.expectedEx.expectMessage("6:9 Element <i> is not defined");
		this.run(
		"struct foo\n" +
			"private integer i\n" +
		"end\n" +
		"struct bar extends foo\n" +
			"method someMethod\n" +
				"set this.i = 2\n" +
			"end\n" +
		"end"
		);
	}

	@Test
	public void childStructShouldNotHaveAccessToPrivateMethod() {
		this.expectedEx.expect(UndefinedSymbolException.class);
		this.expectedEx.expectMessage("10:10 Element <someMethod> is not defined");
		this.run(
		"struct foo\n" +
			"protected integer i\n" +
			"method someMethod\n" +
				"set this.i = 1\n" +
			"end\n" +
		"end\n" +
		"struct bar extends foo\n" +
			"method someOtherMethod\n" +
				"set this.i = 2\n" +
				"call this.someMethod()\n" +
			"end\n" +
		"end"
		);
	}

	@Test
	public void _protected() {
		this.expectedEx.expect(NoAccessException.class);
		this.expectedEx.expectMessage("15:6 Element <baz> does not have access to element <i>");
		this.run(
		"struct foo\n" +
			"protected integer i\n" +
			"public method someMethod\n" +
				"set this.i = 1\n" +
			"end\n" +
		"end\n" +
		"struct bar extends foo\n" +
			"method someOtherMethod\n" +
				"set this.i = 2\n" +
				"call this.someMethod()\n" +
			"end\n" +
		"end\n" +
		"function baz\n" +
			"local bar b\n" +
			"set b.i = 3\n" +
		"end"
		);
	}

	@Test
	public void shouldNotAllowToCastInterface() {
		this.expectedEx.expect(InterfaceCastException.class);
		this.expectedEx.expectMessage("4:14 Cannot cast to foo since it is an interface");
		this.run(
		"interface foo\n" +
		"end\n" +
		"function bar\n" +
			"local foo f = cast 0 to foo\n" +
		"end"
		);
	}

	@Test
	public void deallocate() {
		this.expectedEx.none();
		this.run(
		"struct foo\n" +
			"method deallocate\n" +
				"call FlushChildHashtable(vrjass_structs, cast this to integer)\n" +
			"end\n" +
		"end"
		);
	}

	@Test
	public void superOnNoExtend() {
		this.expectedEx.expect(SuperException.class);
		this.expectedEx.expectMessage("3:5 Struct <foo> can not use super since it does not extends from anything");
		this.run(
		"struct foo\n" +
			"method bar\n" +
				"call super.bar()\n" +
			"end\n" +
		"end"
		);
	}

	@Test
	public void onInitMustBeStatic() {
		this.expectedEx.expect(StaticNonStaticTypeException.class);
		this.expectedEx.expectMessage("2:7 Element <onInit> is not static");
		this.run(
		"struct foo\n" +
			"method onInit\n" +
			"end\n" +
		"end"
		);
	}

	@Test
	public void checkInterfaceMethodReturnUserType() {
		this.expectedEx.none();
		this.run(
			"interface iterable\n"
				+ "public method it returns Iterator\n"
			+ "endinterface\n"
			+ "struct bar implements iterable\n"
				+ "foo f\n"
				+ "public method it returns Iterator\n"
					+ "return this.f.it()\n"
				+ "endmethod\n"
			+ "endstruct\n"
			+ "struct foo implements iterable\n"
				+ "public method it returns Iterator\n"
					+ "local Iterator i\n"
					+ "return i\n"
				+ "endmethod\n"
			+ "endstruct\n"
			+ "struct Iterator\n"
			+ "endstruct"
		);
	}
	
	@Test
	public void checkInterfaceMethodNoReturn() {
		this.expectedEx.expect(IncompatibleTypeException.class);
		this.expectedEx.expectMessage("5:14 Element <getAge> must not return anything");
		this.run(
			"interface person\n"
			+ "public method getAge\n"
			+ "endinterface\n"
			+ "struct foo implements person\n"
				+ "public method getAge returns integer\n"
					+ "return 10\n"
				+ "endmethod\n"
			+ "endstruct"
		);
	}
	
	@Test
	public void checkInterfaceMethodReturnType() {
		this.expectedEx.expect(IncompatibleTypeException.class);
		this.expectedEx.expectMessage("5:14 Element <getAge> must have/return a value of type <integer> but given <string>");
		this.run(
			"interface person\n"
			+ "public method getAge returns integer\n"
			+ "endinterface\n"
			+ "struct foo implements person\n"
				+ "public method getAge returns string\n"
					+ "return \"\"\n"
				+ "endmethod\n"
			+ "endstruct"
		);
	}
	
	@Test
	public void checkTypeOnInterfaceImplementation() {
		this.expectedEx.none();
		this.run(
			"interface foo\n"
			+ "end\n"
			+ "struct bar implements foo\n"
			+ "endstruct\n"
			+ "function lorem takes foo f\n"
			+ "endfunction\n"
			+ "function ipsum\n"
				+ "local bar b\n"
				+ "call lorem(b)\n"
			+ "endfunction"
		);
	}
	
	@Test
	public void interfaceType() {
		this.expectedEx.none();
		this.run(
			"interface foo\n"
			+ "end\n"
			+ "struct bar\n"
				+ "foo _foo\n"
				+ "public method setFoo takes foo _f\n"
					+ "set this._foo = _f\n"
				+ "end\n"
			+ "endstruct"
		);
	}
	
	@Test
	public void inheritanceTypeCheckCorrectly() {
		this.expectedEx.none();
		this.run(
			"struct foo\n"
				+ "public static method bar takes foo f\n"
				+ "endmethod\n"
			+ "endstruct\n"
			+ "struct bar extends foo\n"
			+ "endstruct\n"
			+ "function lorem\n"
				+ "call foo.bar(cast 0 to bar)\n"
			+ "endfunction"
		);
	}
	
	@Test
	public void interfaceUsesNamespaceInReturn() {
		this.expectedEx.none();
		this.run(
			"interface foo\n"
				+ "method bar takes nothing returns lorem.ipsum\n"
			+ "endinterface\n"
			+ "library lorem\n"
				+ "public struct ipsum\n"
				+ "endstruct\n"
			+ "endlibrary"
		);
	}
	
	@Test
	public void interfaceMethodImplementationMustMatchArgumentsCount() {
		this.expectedEx.expect(IncorrectArgumentCountException.class);
		this.expectedEx.expectMessage("5:7 Incorrect amount of arguments passed to function <bar>");
		this.run(
			"interface foo\n"
				+ "method bar takes integer i, real e returns nothing\n"
			+ "endinterface\n"
			+ "struct lorem implements foo\n"
				+ "method bar takes integer i returns nothing\n"
				+ "endmethod\n"
			+ "endstruct"
		);
	}
	
	@Test
	public void interfaceMethodImplementationMustBeMethod() {
		this.expectedEx.expect(NoFunctionException.class);
		this.expectedEx.expectMessage("5:8 Element <bar> is not a function");
		this.run(
			"interface foo\n"
				+ "method bar takes nothing returns nothing\n"
			+ "endinterface\n"
			+ "struct lorem implements foo\n"
				+ "integer bar\n"
			+ "endstruct"
		);
	}
	
	@Test
	public void properInterfaceImplementation() {
		this.expectedEx.none();
		this.run(
			"interface foo\n"
				+ "public method bar takes nothing returns nothing\n"
				+ "method baz takes nothing returns nothing\n"
			+ "endinterface\n"
			+ "struct ipsum\n"
				+ "public method bar\n"
				+ "end\n"
				+ "method baz takes nothing returns nothing\n"
				+ "endmethod\n"
			+ "endstruct\n"
			+ "struct lorem extends ipsum implements foo\n"
				+ "public method bar takes nothing returns nothing\n"
				+ "endmethod\n"
				+ "method baz\n"
				+ "end\n"
			+ "endstruct"
		);
	}
	
	@Test
	public void shouldMatchParamsInAbstractMethods() {
		this.expectedEx.expect(IncompatibleTypeException.class);
		this.expectedEx.expectMessage("5:7 Element <i> must have/return a value of type <integer> but given <boolean>");
		this.run(
			"interface foo\n"
				+ "method bar takes integer i returns nothing\n"
			+ "endinterface\n"
			+ "struct lorem implements foo\n"
				+ "method bar takes boolean i returns nothing\n"
				+ "endmethod\n"
			+ "endstruct"
		);
	}
	
	@Test
	public void shouldImplementAllAbstractMethods() {
		this.expectedEx.expect(AbstractMethodException.class);
		this.expectedEx.expectMessage("4:7 Class lorem must implement all methods of interface foo");
		this.run(
			"interface foo\n"
				+ "method bar takes nothing returns nothing\n"
			+ "endinterface\n"
			+ "struct lorem implements foo\n"
			+ "endstruct"
		);
	}
	
	@Test
	public void shouldUseCodeOnlyWithStatic() {
		this.expectedEx.expect(InvalidStatementException.class);
		this.expectedEx.expectMessage("10:8 Functions/methods must be static to be used as code");
		this.run(
			"struct foo\n"
				+ "public static method baz\n"
				+ "endmethod\n"
				+ "public method lorem\n"
				+ "endmethod\n"
			+ "endstruct\n"
			+ "function bar\n"
				+ "local foo instance\n"
				+ "local code f = function foo.baz\n"
				+ "set f = function instance.lorem\n"
			+ "endfunction"
		);
	}
	
	@Test
	public void propertyType() {
		this.expectedEx.none();
		this.run(
				"struct Object\n" + 
				"    static integer instances\n" + 
				"    public static method allocate takes nothing returns Object\n" + 
				"        set Object.instances += 1\n" + 
				"        return cast Object.instances to Object\n" +
				"    endmethod\n" + 
				"    public method equals takes Object e returns boolean\n" + 
				"        return cast this to integer == cast e to integer\n" +
				"    endmethod\n" + 
				"    public method isNull returns boolean\n" + 
				"        return cast this to integer == 0\n" +
				"    endmethod\n" + 
				"endstruct\n" +
				"library List\n" + 
				"    struct Node extends Object\n" + 
				"        Object element\n" + 
				"        Node prev\n" + 
				"        Node next\n" + 
				"        public method getElement returns Object\n" + 
				"            return this.element\n" + 
				"        endmethod\n" + 
				"        public method getPrev returns Node\n" + 
				"            return this.prev\n" + 
				"        endmethod\n" + 
				"        public method getNext returns Node\n" + 
				"            return this.next\n" + 
				"        endmethod\n" + 
				"        public static method create takes Object element, Node prev returns Node\n" + 
				"            local Node instance = Node.allocate()\n" + 
				"            set instance.element = element\n" + 
				"            set prev.next = instance\n" + 
				"            set instance.prev = prev\n" + 
				"            return instance\n" + 
				"        endmethod\n" + 
				"    endstruct\n" + 
				"    public struct Doubly extends Object\n" + 
				"        Node head\n" + 
				"        Node last\n" + 
				"        public method createIterator returns Iterator\n" + 
				"            return Iterator.create(this.head)\n" + 
				"        endmethod\n" + 
				"        public method getHead returns Object\n" + 
				"            return this.head.getElement()\n" + 
				"        endmethod\n" + 
				"        public method getLast returns Object\n" + 
				"            return this.last.getElement()\n" + 
				"        endmethod\n" + 
				"        public method insert takes Object element\n" + 
				"            if (this.head.isNull()) then\n" + 
				"                set this.head = Node.create(element, this.last)\n" + 
				"            endif\n" + 
				"            if (this.last.isNull()) then\n" + 
				"                set this.last = Node.create(element, this.head)\n" + 
				"            endif\n" + 
				"        endmethod\n" + 
				"    endstruct\n" + 
				"    public struct Iterator extends Object\n" + 
				"        Node head\n" + 
				"        Node node\n" + 
				"        public method hasNext returns boolean\n" + 
				"            return this.node.getNext().isNull() == false\n" + 
				"        endmethod\n" + 
				"        public static method create takes Node head returns Iterator\n" + 
				"            local Iterator it = Iterator.allocate()\n" + 
				"            set it.head = head\n" + 
				"            set it.node = head\n" + 
				"            return it\n" + 
				"        endmethod\n" + 
				"    endstruct\n" + 
				"endlibrary"
		);
	}
	
	@Test
	public void resolveCheckingSuperThenEnclosingScope() {
		this.expectedEx.none();
		this.run(
			"struct Object\n"
			+ "endstruct\n"
			+ "library List\n"+
			    "struct Node extends Object\n"+
			        "Object e\n"+
			        "Node prev\n"+
			        "Node next\n"+
			    "endstruct\n"+
			    "public struct Doubly extends Object\n"+
			        "Node node\n"+
			    "endstruct\n"+
			"endlibrary"
		);
	}
	
	@Test
	public void usingItself() {
		this.expectedEx.none();
		this.run(
		"struct obj\n"
		+ "endstruct\n"
		+ "library list\n"
			+ "struct Node extends obj\n" +
		        "Node prev\n" +
		        "Node next\n" +
		    "endstruct\n"
	    + "endlibrary");
	}
	
	@Test
	public void propertiesCantShareLibraryNames() {
		this.expectedEx.expect(AlreadyDefinedException.class);
		this.expectedEx.expectMessage("3:4 Element <foo> is already defined on 2:7");
		this.run("library foo\n"
					+ "struct foo\n"
						+ "foo foo\n"
					+ "endstruct\n"
				+ "endlibrary");
	}
	
	@Test
	public void scopes() {
		this.expectedEx.none();
		this.run("struct foo\n"
					+ "static foo instance\n"
					+ "integer array ia\n"
					+ "integer i\n"
					+ "method baz takes integer i\n"
					+ "endmethod\n"
					+ "method bar\n"
						+ "set foo.instance.ia[foo.instance.i]=2\n"
						+ "call foo.instance.baz(foo.instance.i)\n"
					+ "endmethod\n"
				+ "endstruct");
	}
	
	@Test
	public void usingInteger() {
		this.expectedEx.none();
		this.run("struct foo\n"
					+ "public static method allocate takes nothing returns foo\n"
						+ "local integer i = 1\n"
						+ "return cast i to foo\n"
					+ "endmethod\n"
				+ "endstruct");
	}
	
	@Test
	public void usingStaticIncorrectly() {
		this.expectedEx.expect(StaticNonStaticTypeException.class);
		this.expectedEx.expectMessage("6:9 Element <s_bar> is static");
		this.run("struct foo\n"
					+ "integer bar\n"
					+ "static integer s_bar\n"
					+ "method x takes nothing returns nothing\n"
						+ "set this.bar = 2\n"
						+ "set this.s_bar = 2\n"
					+ "endmethod\n"
				+ "endstruct");
	}
	
	@Test
	public void usingNonStaticIncorrectly() {
		this.expectedEx.expect(StaticNonStaticTypeException.class);
		this.expectedEx.expectMessage("6:8 Element <bar> is not static");
		this.run("struct foo\n"
					+ "integer bar\n"
					+ "static integer s_bar\n"
					+ "method x takes nothing returns nothing\n"
						+ "set foo.s_bar = 2\n"
						+ "set foo.bar = 2\n"
					+ "endmethod\n"
				+ "endstruct");
	}
	
	@Test
	public void userDefinedType() {
		this.expectedEx.expect(IncompatibleTypeException.class);
		this.expectedEx.expectMessage("10:6 Element <p> must have/return a value of type <person> but given <god>");
		this.run("struct chucknorris\n"
				+ "endstruct\n"
				
				+ "struct god extends chucknorris\n"
				+ "endstruct\n"
				
				+ "struct person\n"
				+ "endstruct\n"
				
				+ "function foo takes nothing returns nothing\n"
					+ "local chucknorris chuck\n"
					+ "local god g = chuck\n"
					+ "local person p = g\n"
				+ "endfunction");
	}
	
	@Test
	public void validImplementType() {
		this.expectedEx.expect(InvalidImplementTypeException.class);
		this.expectedEx.expectMessage("1:22 Element <integer> is not a valid implementable interface");
		this.run("struct foo implements integer\n"
				+ "endstruct");
	}
	
	@Test
	public void validExtendType() {
		this.expectedEx.expect(InvalidExtendTypeException.class);
		this.expectedEx.expectMessage("1:19 Element <integer> is not a valid extendable class");
		this.run("struct foo extends integer\n"
				+ "endstruct");
	}

	@Test
	public void undefinedType() {
		this.expectedEx.expect(UndefinedSymbolException.class);
		this.expectedEx.expectMessage("1:35 Element <bar> is not defined");
		this.run("function foo takes nothing returns bar\n"
				+ "endfunction");
	}
	
	@Test
	public void nonValidType() {
		this.expectedEx.expect(InvalidTypeException.class);
		this.expectedEx.expectMessage("3:35 <foo> is either not a valid type or it could not be reached by <bar>");
		this.run("library foo\n"
				+ "endlibrary\n"
				+ "function bar takes nothing returns foo\n"
				+ "endfunction");
	}
	
	@Test
	public void extendingUndefinedType() {
		this.expectedEx.expect(UndefinedSymbolException.class);
		this.expectedEx.expectMessage("1:19 Element <bar> is not defined");
		this.run("struct foo extends bar\n"
				+ "endstruct");
	}
	
	@Test
	public void usingSameName() {
		this.expectedEx.none();
		this.run("library foo\n"
					+ "struct foo\n"
					+ "endstruct\n"
				+ "endlibrary");
	}
	
	@Test
	public void usingPrivateMethodExternally() {
		this.expectedEx.expect(NoAccessException.class);
		this.expectedEx.expectMessage("7:7 Element <bar> does not have access to element <pi>");
		this.run("struct foo\n"
					+ "private method pi takes nothing returns nothing\n"
					+ "endmethod\n"
				+ "endstruct\n"
				+ "function bar takes nothing returns nothing\n"
					+ "local foo i\n"
					+ "call i.pi()\n"
				+ "endfunction");
	}
	
	@Test
	public void usingPublicPropertyExternally() {
		this.expectedEx.none();
		this.run("library foo\n"
					+ "public struct foo\n"
						+ "public static real pi = 3.14\n"
					+ "endstruct\n"
				+ "endlibrary\n"
				+ "function bar takes nothing returns nothing\n"
					+ "local real pi = foo.foo.pi\n"
				+ "endfunction");
	}

}
