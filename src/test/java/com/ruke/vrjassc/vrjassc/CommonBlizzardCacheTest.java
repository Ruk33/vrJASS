package com.ruke.vrjassc.vrjassc;

import static org.junit.Assert.*;

import org.junit.Ignore;
import org.junit.Test;

public class CommonBlizzardCacheTest {

	/**
	 * we shouldn't need to load common.j and blizzar.j every time
	 * we run vrjass compiler because it is always the same
	 * 
	 * @todo create a cache for these files so we don't need to create the symbol table every
	 * time the compiler is run
	 */
	@Test
	@Ignore
	public void test() {
		fail("Not yet implemented");
	}

}
