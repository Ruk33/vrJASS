package com.ruke.vrjassc.vrjassc.symbol;

public interface Type {
	
	String getName();

	boolean isTypeCompatible(Symbol symbol);

	boolean isUserType();
	
}
