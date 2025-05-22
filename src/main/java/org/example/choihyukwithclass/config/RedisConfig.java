package org.example.choihyukwithclass.config;

import java.util.List;

import org.example.choihyukwithclass.dto.BusinessResponseDto;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
@EnableRedisRepositories
public class RedisConfig {
	@Bean
	public RedisTemplate<String, List<BusinessResponseDto>> businessListRedisTemplate(
		RedisConnectionFactory connectionFactory, ObjectMapper objectMapper
	) {
		RedisTemplate<String, List<BusinessResponseDto>> template = new RedisTemplate<>();
		template.setConnectionFactory(connectionFactory);

		GenericJackson2JsonRedisSerializer serializer = new GenericJackson2JsonRedisSerializer(objectMapper);
		template.setKeySerializer(new StringRedisSerializer()); // 일반 key 설정
		template.setHashKeySerializer(new StringRedisSerializer()); // 해시 key 설정

		template.setValueSerializer(serializer);
		template.setHashValueSerializer(serializer);
		return template;
	}
}
