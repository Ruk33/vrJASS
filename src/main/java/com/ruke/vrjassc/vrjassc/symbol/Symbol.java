package com.ruke.vrjassc.vrjassc.symbol;

import java.util.HashMap;

import org.antlr.v4.runtime.Token;

import com.ruke.vrjassc.vrjassc.util.TypeCompatibleChecker;

public class Symbol {

	public static final String PRIVATE_SEPARATOR = "__";

	public static final String PUBLIC_SEPARATOR = "_";

	protected String name;

	/**
	 * For variables, its the type (unit, real, integer, etc.) For functions,
	 * its the return type
	 */
	protected String type;

	protected PrimitiveType primitiveType;

	protected Visibility visibility;

	protected HashMap<String, Symbol> childs;

	/**
	 * Parent of the symbol (for example, a library may be the parent of a
	 * function)
	 *
	 * Can be null
	 *
	 * If a parent is passed to the constructor, the parent will automatically
	 * call to addChild
	 */
	protected Symbol parent;

	protected Token token;

	public Symbol(String name, String type, PrimitiveType primitiveType,
			Visibility visibility, Symbol parent, Token token) {
		this.name = name;
		this.type = type;
		this.primitiveType = primitiveType;
		this.visibility = visibility;
		this.childs = new HashMap<String, Symbol>();
		this.parent = parent;
		this.token = token;

		if (this.parent != null) {
			this.parent.addChild(this);
		}
	}

	public HashMap<String, Symbol> getChilds() {
		return this.childs;
	}
	
	public String getName() {
		return this.name;
	}

	public String getFullName() {
		String name = this.getName();

		if (this.parent != null) {
			if (this.parent.getName() != null) {
				if (this.getVisibility() == Visibility.PRIVATE) {
					name = this.parent.getFullName() + PRIVATE_SEPARATOR + name;
				} else if (this.getVisibility() == Visibility.PUBLIC) {
					name = this.parent.getFullName() + PUBLIC_SEPARATOR + name;
				}
			}
		}

		return name;
	}

	public String getType() {
		return this.type;
	}

	public PrimitiveType getPrimitiveType() {
		return this.primitiveType;
	}

	public Visibility getVisibility() {
		return this.visibility;
	}

	/**
	 *
	 * @param child
	 * @return Itself
	 */
	public Symbol addChild(Symbol child) {
		// prefix and with no prefix
		this.childs.put(child.getFullName(), child);
		this.childs.put(child.getName(), child);

		return this;
	}

	/**
	 * Find a symbol and get it
	 *
	 * @param name
	 * @param primitiveType
	 * @param backwards
	 *            true to look even on parents
	 * @return
	 */
	public Symbol resolve(String name, PrimitiveType primitiveType,
			boolean backwards) {
		Symbol result = this.childs.get(name);

		if (result == null) {
			for (Symbol child : this.childs.values()) {
				result = child.resolve(name, primitiveType, false);

				if (result != null) {
					break;
				}
			}
		}
		
		if (result == null && backwards) {
			if (this.parent != null) {
				result = this.parent.resolve(name, primitiveType, true);
			}
		}
		
		if (result != null && result.getVisibility() == Visibility.LOCAL) {
			if (result.getParent() != this) {
				result = null;
			}
		}

		return result;
	}

	public Symbol getParent() {
		return this.parent;
	}

	public int getLine() {
		return this.token.getLine();
	}

	public int getCharPositionInLine() {
		return this.token.getCharPositionInLine();
	}

	public boolean hasAccess(Symbol symbol) {
		if (this == symbol.getParent()) {
			return true;
		}

		if (this.getParent() != symbol.getParent()) {
			if (symbol.getVisibility() == Visibility.PRIVATE) {
				return false;
			}
		}

		return true;
	}

	public boolean isTypeCompatible(String type) {
		return TypeCompatibleChecker.isCompatible(this.getType(), type);
	}
}
