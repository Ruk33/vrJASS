package symbol;

import java.util.HashMap;

public class Symbol {
	
	protected String name;
	
	/**
	 * For variables, its simple the type (unit, real, integer, etc.)
	 * For functions, its the return type
	 */
	protected String type;
	
	protected PrimitiveType primitiveType;
	
	protected Visibility visibility;
	
	protected HashMap<PrimitiveType, HashMap<String, Symbol>> childs;
	
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
	
	public Symbol(
			String name,
			String type,
			PrimitiveType primitiveType,
			Visibility visibility,
			Symbol parent) {
		this.name = name;
		this.type = type;
		this.primitiveType = primitiveType;
		this.visibility = visibility;
		this.childs = new HashMap<PrimitiveType, HashMap<String, Symbol>>();
		this.parent = parent;
		
		if (this.parent != null) {
			this.parent.addChild(this);
		}
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getFullName() {
		return this.getName();
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
		PrimitiveType childPrimitiveType = child.getPrimitiveType();
		
		if (!this.childs.containsKey(childPrimitiveType)) {
			this.childs.put(primitiveType, new HashMap<String, Symbol>());
		}
		
		this.childs.get(childPrimitiveType).put(child.getName(), child);
		
		return this;
	}
	
	/**
	 * Find a symbol and get it
	 * 
	 * @param name
	 * @param primitiveType
	 * @param backwards		true to look even on parents
	 * @return
	 */
	public Symbol resolve(
			String name,
			PrimitiveType primitiveType,
			boolean backwards) {
		Symbol result = null;
		
		if (this.childs.containsKey(primitiveType)) {
			result = this.childs.get(primitiveType).get(name);
			
			if (result == null) {
				for (HashMap<String, Symbol> hm : this.childs.values()) {
					for (Symbol child : hm.values()) {
						result = child.resolve(name, primitiveType, false);
						
						if (result != null) {
							break;
						}
					}
				}
			}
		}
		
		if (result == null && backwards) {
			if (this.parent != null) {
				result = this.parent.resolve(name, primitiveType, true);
			}
		}
		
		return result;
	}
	
	public Symbol getParent() {
		return this.parent;
	}
	
	public boolean hasAccess(Symbol symbol) {
		if (!this.equals(symbol.getParent())) {
			if (symbol.getVisibility() == Visibility.PRIVATE) {
				return false;
			}
		}

		return true;
	}
}
