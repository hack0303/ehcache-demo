package com.creating.www.ehcache;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.net.URL;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.config.Configuration;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.xml.XmlConfiguration;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import com.creating.www.PersistentSimulater;
import com.creating.www.PersistentSimulater.DataBlock;
import com.creating.www.PersistentSimulater.DataBlockFactory;

public class JustTest {
   static Logger logger=null;
    PersistentSimulater ps=null;
    CacheManager cacheTier=null;
   @BeforeClass
	public static void beforeClass() {
		logger=LogManager.getLogger();
}
   @Before
	public void before() {
		logger=LogManager.getLogger();
		Map<String,PersistentSimulater.DataBlock> map=new ConcurrentHashMap<>();
		map.put("x",new DataBlock());
		PersistentSimulater.DataBlockFactory factory=new DataBlockFactory(PersistentSimulater.DataBlockFactory._5S,map);
		ps=PersistentSimulater.instance(factory);
		
		//cache tier setting
		cacheTier=CacheManagerBuilder.newCacheManagerBuilder()
				.withCache("preConfigured",CacheConfigurationBuilder.newCacheConfigurationBuilder(String.class,DataBlock.class,ResourcePoolsBuilder.heap(10)))
				.build(true);
		//or .build() then use after cacheTier.init();
		
	}
    @Ignore
	@Test(timeout=1000)
	public void doOverTimeTest() {
		logger.trace("-----start-------");
		assertNotNull(ps.getDataBlock("x"));
		logger.trace("-----end-------");
	}
	
	@Test(timeout=1000)
	public void doBasicTest() {
		logger.trace("-----start-------");
		assertNotNull(cacheTier);
		String key="com.creating.www.test.x";
		DataBlock d=new DataBlock();
		Cache<String,DataBlock> aPiece=cacheTier.getCache("preConfigured",String.class,DataBlock.class);
		assertNotNull(aPiece);
		assertNull(ps.addDataBlock(key,d));
		assertNull(aPiece.putIfAbsent(key, d));
		DataBlock target=aPiece.get(key);
		assertNotNull(target);
        assertEquals(d,target);
        cacheTier.close();
		logger.trace("-----end-------");
	}
	
	@Ignore
	@Test(timeout=1000)
	public void doXMLConfigCacheTest() {
		logger.trace("-----start-------");
	    // URL url=getClass().getResource("/cache.xml");//类目录资源使用，需要/开头
	    URL url=getClass().getResource("cache.xml");//类包目录下资源使用，不要/开头
	    Configuration cfg=new XmlConfiguration(url);
	    cacheTier=CacheManagerBuilder.newCacheManager(cfg);
	    cacheTier.init();
	    Cache<String, DataBlock> aPiece=cacheTier.getCache("test",String.class,DataBlock.class);
	    assertNotNull(aPiece);
	    String key="hi";
	    DataBlock d=new DataBlock();
	    assertNull(aPiece.putIfAbsent("hi",d));
	    DataBlock target=aPiece.get(key);
	    System.out.println(target);
	    assertEquals(d,target);//✖ 出错，因为经过了本地序列化，hash不等
	    cacheTier.close();
		logger.trace("-----end-------");
	}

}
