package com.creating.www.ehcache;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import com.creating.www.PersistentSimulater;

public class PreTest {

	@Test
	public void test1() {
		PersistentSimulater ps=PersistentSimulater.instance(PersistentSimulater.DataBlockFactory._5S);
		PersistentSimulater.DataBlock data=ps.getDataBlock("null");
		assertNotNull(data);
	}
	@Test(timeout=1000)
	public void test2() {
		PersistentSimulater ps=PersistentSimulater.instance(PersistentSimulater.DataBlockFactory._5S);
		PersistentSimulater.DataBlock data=ps.getDataBlock("null");
	}

}
