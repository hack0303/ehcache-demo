package com.creating.www;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class PersistentSimulater {
private	DataBlockFactory fatory;
public static PersistentSimulater instance(int blockTime) {
	return new PersistentSimulater().withFactory(new DataBlockFactory(blockTime));
}
private PersistentSimulater() {
	// TODO Auto-generated constructor stub
}
protected PersistentSimulater withFactory(DataBlockFactory factory) {
	this.fatory=factory;
	return this;
}
public static class DataBlock{
	
}
public static class DataBlockFactory{
	public static int _10S=10;
	public static int _30S=30;
	public static int _60S=60;
	public static int _5S=5;
	public static int _1S=1;
	private int blockTime=0;
	public Map<String,DataBlock> storer=null;
	public DataBlockFactory(int blockTime) {
		this.blockTime=blockTime;
		this.storer=new ConcurrentHashMap<>();
		this.storer.put("null",new DataBlock());
	}
	public DataBlockFactory(int blockTime,Map<String,DataBlock> storer) {
		this.blockTime=blockTime;
		this.storer=storer;
	}
	public DataBlock want(String key) {
		try {
			Thread.sleep(blockTime*1000L);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return storer.get(key);
	}
	
}
public DataBlock getDataBlock(String key) {
	if(fatory!=null)
	return fatory.want(key);
	return null;
}
}
