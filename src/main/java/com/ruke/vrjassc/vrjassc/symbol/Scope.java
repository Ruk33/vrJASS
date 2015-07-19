package com.ruke.vrjassc.vrjassc.symbol;

public interface Scope {

	public String getName();
	
	/**
	 * Define a symbol as a child/member of this scope
	 * @param whichSymbol
	 * @return Defined symbol
	 */
	public Symbol define(Symbol whichSymbol);
	
	/**
	 * Check if this scope or its parents have a member with the passed name
	 * and return it (can be null)
	 * @param requesting Scope from where we perform this operation
	 * @param name
	 * @return
	 */
	public Symbol resolve(Scope requesting, String name);
	
	/**
	 * 
	 * @param string
	 * @return
	 */
	public Symbol resolve(String string);
	
	/**
	 * Get scope enclosing this one (example: function foo is defined
	 * in library a, enclosing symbol of foo is a)
	 * @return
	 */
	public Scope getEnclosingScope();

	/**
	 * Check if scope has access to a symbol
	 * @param symbol
	 * @return
	 */
	public boolean hasAccess(Symbol symbol);
	
}
