package br.com.cambio_service.cambio.configuration;

import org.springframework.boot.autoconfigure.cache.RedisCacheManagerBuilderCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import redis.clients.jedis.JedisPoolConfig;

import java.time.Duration;

@Configuration
@EnableCaching
public class RedisConfiguration {

    @Bean
    public JedisConnectionFactory redisConnectionFactory() {
        RedisStandaloneConfiguration config = new RedisStandaloneConfiguration();
        config.setHostName("localhost");
        config.setPort(6379);
        config.setDatabase(0);

        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxTotal(4);
        poolConfig.setMaxIdle(4);
        poolConfig.setMinIdle(0);
        poolConfig.setMaxWait(Duration.ofSeconds(30));
        return new JedisConnectionFactory(config);
    }

    @Bean
    private static RedisCacheManager.RedisCacheManagerBuilder CacheManager(JedisConnectionFactory connectionFactory) {
        return RedisCacheManager.RedisCacheManagerBuilder
                .fromConnectionFactory(connectionFactory);
    }

    @Bean
    public RedisCacheManagerBuilderCustomizer redisCacheManagerBuilderCustomizer() {
        return this::mountedCache;
    }

    private void mountedCache(RedisCacheManager.RedisCacheManagerBuilder builder) {
        CacheName.cache().forEach(cacheName -> {
            RedisCacheConfiguration redisDefault = RedisCacheConfiguration
                    .defaultCacheConfig()
                    .entryTtl(cacheName.duration())
                    .disableCachingNullValues();

            builder.withCacheConfiguration(cacheName.name(),  redisDefault);
        });
    }

}
