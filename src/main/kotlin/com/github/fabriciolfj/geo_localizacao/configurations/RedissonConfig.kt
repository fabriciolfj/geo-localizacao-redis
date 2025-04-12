package com.github.fabriciolfj.geo_localizacao.configurations

import org.redisson.Redisson
import org.redisson.api.RedissonClient
import org.redisson.config.Config
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class RedissonConfig(
    @Value("\${spring.data.redis.url}")
    private val host: String) {

    @Bean
    fun redissonClient(): RedissonClient {
        val config = Config()
        config.useSingleServer()
            .setAddress(host)
            .setDatabase(0)

        return Redisson.create(config)
    }
}