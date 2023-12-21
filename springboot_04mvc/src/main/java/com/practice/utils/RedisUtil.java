package com.practice.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * RedisUtil is a utility class for interacting with Redis.
 *
 * @param <T> the type of the value stored in Redis
 * @Author Wujiahao
 */
@Component
public class RedisUtil<T> {

    @Autowired
    private RedisTemplate<String, T> redisTemplate;

    /**
     * Set a key-value pair in Redis.
     *
     * @param key   the key
     * @param value the value
     */
    public void set(String key, T value) {
        if (value instanceof List<?>) {
            List<T> list = (List<T>) value;
            redisTemplate.opsForList().leftPushAll(key, list);
        } else {
            redisTemplate.opsForValue().set(key, value);
        }
    }

    /**
     * Set a key-value pair in Redis with an expiration time (in seconds).
     *
     * @param key     the key
     * @param value   the value
     * @param timeout the expiration time in seconds
     */
    public void set(String key, T value, long timeout) {
        redisTemplate.opsForValue().set(key, value, timeout, TimeUnit.SECONDS);
    }

    /**
     * Get the value associated with a key from Redis.
     *
     * @param key the key
     * @return the value associated with the key
     */
    public T get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    /**
     * Delete the value associated with a key from Redis.
     *
     * @param key the key
     */
    public void delete(String key) {
        redisTemplate.delete(key);
    }

    /**
     * Returns the BoundValueOperations associated with the given key.
     *
     * @param key the key
     * @return the BoundValueOperations associated with the key
     */
    public BoundValueOperations<String, T> boundValueOps(String key) {
        return redisTemplate.boundValueOps(key);
    }

    /**
     * Check if a key exists in Redis.
     *
     * @param key the key
     * @return true if the key exists, false otherwise
     */
    public boolean exists(String key) {
        return redisTemplate.hasKey(key);
    }

    /**
     * Set the expiration time (in seconds) for a key.
     *
     * @param key     the key
     * @param timeout the expiration time in seconds
     */
    public void expire(String key, long timeout) {
        redisTemplate.expire(key, timeout, TimeUnit.SECONDS);
    }

    /**
     * Get the expiration time (in seconds) for a key.
     *
     * @param key the key
     * @return the expiration time in seconds, or -1 if the key does not exist or does not have an expiration time
     */
    public long getExpiration(String key) {
        return redisTemplate.getExpire(key, TimeUnit.SECONDS);
    }
}

/**
 * 在你的 Spring Boot 项目中，确保已经配置了 Redis 相关的依赖和连接信息。你可以在 application.properties 或 application.yml 文件中配置 Redis 的连接信息。
 *
 * 在需要使用 Redis 的地方，引入 RedisUtil 类，可以通过 @Autowired 或构造函数注入。
 *
 * 使用 RedisUtil 对象调用相应的方法进行 Redis 操作。
 *
 * 下面是一个使用示例：
 *
 * java
 * Copy
 * import org.springframework.beans.factory.annotation.Autowired;
 * import org.springframework.stereotype.Component;
 *
 * @Component
 * public class YourClass {
 *
 *     @Autowired
 *     private RedisUtil<String> redisUtil;
 *
 *     public void yourMethod() {
 *         // 存储值到 Redis
 *         redisUtil.set("myKey", "myValue");
 *
 *         // 获取 Redis 中的值
 *         String value = redisUtil.get("myKey");
 *
 *         // 判断键是否存在
 *         boolean exists = redisUtil.exists("myKey");
 *
 *         // 设置键的过期时间
 *         redisUtil.expire("myKey", 60);
 *
 *         // 获取键的过期时间
 *         long expiration = redisUtil.getExpiration("myKey");
 *
 *         // 删除键
 *         redisUtil.delete("myKey");
 *     }
 * }
 * 在上述示例中，我们通过注入 RedisUtil 对象 redisUtil 来使用 Redis 的操作方法。
 * 可以使用 redisUtil 对象
 * 调用 set 方法存储键值对，
 * get 方法获取键的值，
 * exists 方法检查键是否存在，
 * expire 方法设置键的过期时间，
 * getExpiration 方法获取键的过期时间，
 * 以及 delete 方法删除键。根据你的具体需求，
 * 可以在应用程序的不同部分使用 RedisUtil 类来操作 Redis 数据库。
 *
 *
 * */