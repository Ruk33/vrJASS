package com.ruke.vrjassc.vrjassc.symbol;

public interface Type {
	
	public String getName();

	public boolean isTypeCompatible(Symbol symbol);
	
}
