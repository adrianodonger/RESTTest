package service;

import java.util.concurrent.TimeUnit;

import redis.clients.jedis.Jedis;

/*
 * Test Code for Jedis usage.
 * 		//Add entry into the Redis Cache.
 * 		jedis.set("name","value");
 * 
 *		//Timeout the cache after 4 hrs
		jedis.expire("name", 14400);
		
		System.out.println(jedis.get("name"));
		jedis.set("TestmeKey", "TestmeValue");	
			
		jedis.expire("TestmeKey", 5);
		
		//Sleeps the current thread for 10 seconds
		TimeUnit.SECONDS.sleep(10);
		System.out.println(jedis.get("TestmeKey"));	
 * */

public class RedisUtil {
	private static Jedis jedisConn = null;
	
	private RedisUtil() {

	}
	
	public static Jedis getJedisConnection() {
		if(jedisConn == null) {
			//This value can again be stored in some common place.
			//Can create scripts to make sure that redis is started.
			jedisConn = new Jedis("localhost");
		}
		return jedisConn;
	}
}
