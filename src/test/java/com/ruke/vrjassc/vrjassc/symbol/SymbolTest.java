package com.ruke.vrjassc.vrjassc.symbol;

import static org.junit.Assert.*;

import org.junit.Test;

public class SymbolTest {
	
	@Test
	public void resolveNatives() {
		ScopeSymbol scope = new VrJassScope();
		
		assertEquals("integer", scope.resolve("integer").getName());
		assertEquals("real", scope.resolve("real").getName());
		assertEquals("boolean", scope.resolve("boolean").getName());
		assertEquals("string", scope.resolve("string").getName());
		assertEquals("handle", scope.resolve("handle").getName());
		assertEquals("code", scope.resolve("code").getName());
		assertEquals("nothing", scope.resolve("nothing").getName());
		assertEquals("null", scope.resolve("null").getName());
	}
	
	@Test
	public void resolveLocalVariables() {
		ScopeSymbol scope = new VrJassScope();
		
		ScopeSymbol functionFoo = new FunctionSymbol("foo", scope, null);
		ScopeSymbol functionBar = new FunctionSymbol("bar", scope, null);
		
		scope.define(functionFoo);
		scope.define(functionBar);
		
		VariableSymbol variableFooLorem = new VariableSymbol("lorem", functionFoo, null);
		variableFooLorem.setModifier(Modifier.LOCAL, true);
		variableFooLorem.setModifier(Modifier.PRIVATE, true);
		
		functionFoo.define(variableFooLorem);
		
		assertNull(functionFoo.resolve(functionBar, "lorem"));
		assertEquals(variableFooLorem, functionFoo.resolve(functionFoo, "lorem"));
	}
	
	@Test
	public void resolvePrivate() {
		ScopeSymbol scope = new VrJassScope();
		LibrarySymbol libraryA = new LibrarySymbol("A", scope, null);
		ScopeSymbol barFunction = new ScopeSymbol("bar", scope, null);
		
		ScopeSymbol fooFunction = new ScopeSymbol("foo", libraryA, null);
		fooFunction.setModifier(Modifier.PRIVATE, true);
		
		scope.define(libraryA);
		scope.define(barFunction);
		
		libraryA.define(fooFunction);
		
		assertNull(libraryA.resolve(barFunction, "foo"));
		assertEquals(fooFunction, libraryA.resolve(libraryA, "foo"));
	}
	
	@Test
	public void resolveFromSameLibrary() {
		VrJassScope scope = new VrJassScope();
		LibrarySymbol libA = new LibrarySymbol("a", scope, null);
		
		ClassSymbol classB = new ClassSymbol("b", 1, libA, null);
		ClassSymbol classC = new ClassSymbol("c", 2, libA, null);
		
		libA.define(classB);
		libA.define(classC);
		
		assertEquals(classB, classC.resolve("b"));
	}
	
	@Test
	public void resolvingItself() {
		ScopeSymbol foo = new ScopeSymbol("foo", null, null);
		assertEquals(foo, foo.resolve("foo"));		
	}
	

}
