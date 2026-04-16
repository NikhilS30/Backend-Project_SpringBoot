package com.example.productService.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

/*
@configuration annotation is used to show spring that this is a config class handle it accordingly
*/

@Configuration
public class ApplicationConfiguration {

    /*@bean jo annotation hai this tells spring that i need to create a single copy of this obj and store it in my application context
    *so that it is accessible globally whenever required
     */
    @Bean
    public RestTemplate createRestTemplate(){
        //this will return rest tempplate object.
        return new RestTemplate();
    }

//    @Bean
//    public RedisTemplate<String,Object> getRedisTemplate(RedisConnectionFactory redisConnectionFactory){
//        RedisTemplate<String,Object> template = new RedisTemplate<>();
//        template.setConnectionFactory(redisConnectionFactory);
//        // 🔑 Keys & Hash Keys as String
////        template.setKeySerializer(new StringRedisSerializer());
////        template.setHashKeySerializer(new StringRedisSerializer());
////
////        // 📦 Values & Hash Values as JSON
////        template.setValueSerializer(new GenericJackson2JsonRedisSerializer());
////        template.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());
//
//        template.afterPropertiesSet();
//        return template;
//    }
}
