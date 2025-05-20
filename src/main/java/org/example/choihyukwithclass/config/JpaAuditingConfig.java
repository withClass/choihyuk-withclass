package org.example.choihyukwithclass.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * JPA Auditing 설정 클래스
 * BaseEntity 사용하기 위한 클래스
 */
@EnableJpaAuditing
@Configuration
public class JpaAuditingConfig {
}
