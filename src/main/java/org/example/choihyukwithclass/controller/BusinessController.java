package org.example.choihyukwithclass.controller;

import java.util.List;

import org.example.choihyukwithclass.dto.BusinessResponseDto;
import org.example.choihyukwithclass.service.BusinessService;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class BusinessController {

	private final BusinessService businessService;

	@GetMapping("/search/{keyword}")
	public List<BusinessResponseDto> searchAll(
		@PathVariable("keyword") String keyword,
		Pageable pageable
	){
		return businessService.searchAll(pageable, keyword);
	}

}
