package org.example.choihyukwithclass.service;

import java.util.List;

import org.example.choihyukwithclass.aop.InMemoryCacheSearch;
import org.example.choihyukwithclass.dto.BusinessRequestDto;
import org.example.choihyukwithclass.dto.BusinessResponseDto;
import org.example.choihyukwithclass.entity.Business;
import org.example.choihyukwithclass.repository.BusinessRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sun.jdi.request.InvalidRequestStateException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BusinessService {

	private final BusinessRepository businessRepository;

	@Transactional(readOnly = true)
	public List<BusinessResponseDto> searchAllV1(Pageable pageable, String word){
		return businessRepository.searchAll(pageable, word);
	}

	@InMemoryCacheSearch
	@Transactional(readOnly = true)
	public List<BusinessResponseDto> searchAllV2(Pageable pageable, String word){
		return businessRepository.searchAll(pageable, word);
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
}
