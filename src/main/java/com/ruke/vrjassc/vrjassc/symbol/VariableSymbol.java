package com.ruke.vrjassc.vrjassc.symbol;

import org.antlr.v4.runtime.Token;

public class VariableSymbol extends Symbol {

	protected boolean constant;

	protected boolean array;

	protected boolean global;

	public VariableSymbol(String name, String type, boolean isConstant,
			boolean isArray, boolean isGlobal, Visibility visibility,
			Symbol parent, Token token) {
		super(name, type, PrimitiveType.VARIABLE, visibility, parent, token);

		this.constant = isConstant;
		this.array = isArray;
		this.global = isGlobal;
	}

	@Override
	public String getFullName() {
		if (!this.isGlobal()) {
			return this.getName();
		}

		return super.getFullName();
	}

	public boolean isConstant() {
		return this.constant;
	}

	public boolean isGlobal() {
		return this.global;
	}

	public boolean isArray() {
		return this.array;
	}

}
