package org.example.choihyukwithclass.aop;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class InMemoryCacheAspect {
	private final Map<String, Object> cacheDB = new ConcurrentHashMap<>();

	@Around("@annotation(InMemoryCacheSearch)")
	public Object cacheSearch(ProceedingJoinPoint joinPoint) throws Throwable {
		Object[] args = joinPoint.getArgs();

		Pageable pageable = (Pageable) args[0]; // pageable 객체
		String keyword = (String) args[1]; // keyword 변수

		String key = keyword + ":" + pageable.getPageNumber() + ":" + pageable.getPageSize();

		if (cacheDB.containsKey(key)) {
			log.info("캐시가 존재합니다: {}", key);
			return cacheDB.get(key);
		}

		log.info("캐시가 존재하지 않습니다: {}", key);

		Object result = joinPoint.proceed();
		cacheDB.put(key, result);
		return result;
	}
}
