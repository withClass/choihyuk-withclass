package org.example.choihyukwithclass.repository;

import static org.example.choihyukwithclass.entity.QBusiness.*;

import java.util.List;

import org.example.choihyukwithclass.dto.BusinessResponseDto;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class BusinessRepositoryImpl implements BusinessRepositoryQueryDsl {

	private final JPAQueryFactory queryFactory;

	@Override
	public List<BusinessResponseDto> searchAll(Pageable pageable, String word) {
		return queryFactory
			.select(business)
			.from(business)
			.where(business.name.containsIgnoreCase(word))
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.fetch()
			.stream().map(BusinessResponseDto::from).toList();
	}
}
