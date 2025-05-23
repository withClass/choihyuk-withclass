package org.example.choihyukwithclass.service;

import java.time.Duration;
import java.util.List;

import org.example.choihyukwithclass.dto.BusinessRequestDto;
import org.example.choihyukwithclass.dto.BusinessResponseDto;
import org.example.choihyukwithclass.entity.Business;
import org.example.choihyukwithclass.repository.BusinessRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.jdi.request.InvalidRequestStateException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class BusinessService {

	private final BusinessRepository businessRepository;
	private final RedisTemplate<String, List<BusinessResponseDto>> businessRedisTemplate;
	private final ObjectMapper objectMapper;

	@Transactional(readOnly = true)
	public List<BusinessResponseDto> searchAllV1(Pageable pageable, String word){
		return businessRepository.searchAll(pageable, word);
	}

	@Cacheable("searchBusiness")
	@Transactional(readOnly = true)
	public List<BusinessResponseDto> searchAllV2(Pageable pageable, String word){
		return businessRepository.searchAll(pageable, word);
	}

	@Transactional(readOnly = true)
	public List<BusinessResponseDto> searchAllV3(Pageable pageable, String word){
		String cacheKey = generateCacheKeyWithPageable(word, pageable);

		List<BusinessResponseDto> cacheResult = businessRedisTemplate.opsForValue().get(cacheKey);
		if (cacheResult != null) {
			return cacheResult;
		}

		List<BusinessResponseDto> result = businessRepository.searchAll(pageable, word);
		businessRedisTemplate.opsForValue().set(cacheKey, result, Duration.ofMinutes(10));
		return result;
	}

	@Transactional(readOnly = true)
	public List<BusinessResponseDto> searchAllV4(Pageable pageable, String keyword) throws JsonProcessingException {
		String cacheKey = "searchResults:" + keyword.trim().toLowerCase();
		String field = pageable.getPageNumber() + ":" + pageable.getPageSize();

		List<BusinessResponseDto> cachedListByHash = getCachedListByHash(cacheKey, field);
		if (cachedListByHash != null) {
			return cachedListByHash;
		}

		List<BusinessResponseDto> result = businessRepository.searchAll(pageable, keyword);

		String json = objectMapper.writeValueAsString(result);
		businessRedisTemplate.opsForHash().put(cacheKey, field, json);
		businessRedisTemplate.expire(cacheKey, Duration.ofMinutes(10));
		return result;
	}

	@Transactional(readOnly = true)
	public BusinessResponseDto findById(Long id){
		Business business = businessRepository.findById(id)
			.orElseThrow(() -> new InvalidRequestStateException("business not found"));
		return BusinessResponseDto.from(business);
	}

	@Transactional
	public void updateById(Long id, BusinessRequestDto requestDto){
		Business business = businessRepository.findById(id)
			.orElseThrow(() -> new InvalidRequestStateException("business not found"));
		business.updateBusiness(requestDto);
	}

	@Transactional
	public void deleteById(Long id){
		Business business = businessRepository.findById(id)
			.orElseThrow(() -> new InvalidRequestStateException("business not found"));
		businessRepository.deleteById(id);
	}

	/**
	 * 검색어 캐시키를 생성하여 반환하는 메서드
	 * @param keyword 검색어 키워드
	 * @param pageable 페이지 객체
	 * @return 문자열 캐시키를 반환
	 */
	private String generateCacheKeyWithPageable(String keyword, Pageable pageable){
		return keyword.trim().toLowerCase() + ":" + pageable.getPageNumber() + ":" + pageable.getPageSize();
	}

	/**
	 * Redis 캐시가 존재하면, 역직렬화(Hash)해서 반환하는 메서드
	 * @param cacheKey 일반키
	 * @param field 필드키
	 * @return 응답 리스트 객체로 반환
	 */
	private List<BusinessResponseDto> getCachedListByHash(String cacheKey, String field) {
		Object cachedList = businessRedisTemplate.opsForHash().get(cacheKey, field);
		if (cachedList == null) return null;

		try {
			// ObjectMapper 역직렬화 (JSON → List<Dto>) 해서 반환
			return objectMapper.readValue(
				cachedList.toString(),
				new TypeReference<List<BusinessResponseDto>>() {}
			);
		} catch (JsonProcessingException e) {
			log.warn("Failed to deserialize cached value for key: {} field: {}", cacheKey, field, e);
			return null;
		}
	}
}
