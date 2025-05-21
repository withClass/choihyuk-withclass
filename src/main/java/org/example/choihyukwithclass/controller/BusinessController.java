package org.example.choihyukwithclass.controller;

import java.util.List;

import org.example.choihyukwithclass.dto.BusinessResponseDto;
import org.example.choihyukwithclass.service.BusinessService;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/business")
public class BusinessController {

	private final BusinessService businessService;

	@GetMapping("v1/search/{keyword}")
	public List<BusinessResponseDto> searchV1(
		@PathVariable("keyword") String keyword,
		Pageable pageable
	){
		return businessService.searchAllV1(pageable, keyword);
	}

	@GetMapping("v2/search/{keyword}")
	public List<BusinessResponseDto> searchV2(
		@PathVariable("keyword") String keyword,
		Pageable pageable
	){
		return businessService.searchAllV2(pageable, keyword);
	}

}
