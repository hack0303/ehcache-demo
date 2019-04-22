package com.creating.www.ehcache;

import org.ehcache.Cache;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        org.ehcache.CacheManager manager=CacheManagerBuilder.newCacheManagerBuilder()
        		.withCache("preconfigured",CacheConfigurationBuilder.newCacheConfigurationBuilder(String.class, Long.class, ResourcePoolsBuilder.heap(10)))
        		.build();
        manager.init();
        Cache<String,Long> cache=manager.getCache("preconfigured", String.class, Long.class);
        for(int x=0;x<10;x++)
        cache.putIfAbsent("hah"+x, 1213L);
        cache.putIfAbsent("hah"+0, 1212L);
        System.out.println(cache.get("hah0"));
        
    }
}
