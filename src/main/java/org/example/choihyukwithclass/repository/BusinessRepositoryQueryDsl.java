package org.example.choihyukwithclass.repository;

import java.util.List;

import org.example.choihyukwithclass.dto.BusinessResponseDto;
import org.springframework.data.domain.Pageable;

public interface BusinessRepositoryQueryDsl {
	List<BusinessResponseDto> searchAll(Pageable pageable, String word);
}
