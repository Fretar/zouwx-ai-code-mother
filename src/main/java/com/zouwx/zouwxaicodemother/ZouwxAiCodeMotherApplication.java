package com.zouwx.zouwxaicodemother;

import dev.langchain4j.community.store.embedding.redis.spring.RedisEmbeddingStoreAutoConfiguration;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * @author Tretar
 */
@EnableCaching
@SpringBootApplication(exclude = {RedisEmbeddingStoreAutoConfiguration.class})
@MapperScan("com.zouwx.zouwxaicodemother.mapper")
public class ZouwxAiCodeMotherApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZouwxAiCodeMotherApplication.class, args);
    }

}
