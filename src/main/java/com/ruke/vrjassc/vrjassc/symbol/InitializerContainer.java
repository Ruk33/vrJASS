package com.ruke.vrjassc.vrjassc.symbol;

import java.util.Collection;

public interface InitializerContainer {

	public void setInitializer(Symbol initializer);
	
	public Symbol getInitializer();
	
	public Collection<InitializerContainer> getInitializersToLoadFirst();
	
}
