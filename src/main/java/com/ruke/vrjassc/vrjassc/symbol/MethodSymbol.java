package com.ruke.vrjassc.vrjassc.symbol;

import org.antlr.v4.runtime.Token;

public class MethodSymbol extends FunctionSymbol implements ClassMemberSymbol {

	protected boolean _static;
	protected boolean _abstract;
	
	public MethodSymbol(String name,
			String type,
			boolean isStatic,
			boolean isAbstract,
			Visibility visibility,
			Symbol parent,
			Token token) {
		super(name, type, visibility, parent, token);

		if (this.visibility == null) {
			this.visibility = Visibility.PUBLIC;
		}

		this._static = isStatic;
		this._abstract = isAbstract;

		if (!this.isStatic()) {
			new ParameterSymbol("this", this.getParent().getType(), this, null);
		}
	}

	@Override
	public boolean isStatic() {
		return this._static;
	}

	@Override
	public boolean isAbstract() {
		return this._abstract;
	}

}
