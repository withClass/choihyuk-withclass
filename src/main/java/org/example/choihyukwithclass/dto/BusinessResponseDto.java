package org.example.choihyukwithclass.dto;

import java.time.LocalDateTime;

import org.example.choihyukwithclass.entity.Business;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class BusinessResponseDto {
	private final Long id;
	private final String name;
	private final String businessNumber;
	private final String address;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	private final LocalDateTime createdAt;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	private final LocalDateTime updatedAt;

	/**
	 * private 생성자
	 * @param business 비즈니스 객체
	 */
	private BusinessResponseDto(Business business){
		this.id = business.getId();
		this.name = business.getName();
		this.businessNumber = business.getBusinessNumber();
		this.address = business.getAddress();
		this.createdAt = business.getCreatedAt();
		this.updatedAt = business.getUpdatedAt();
	}

	/**
	 * 정적 팩토리 메서드 패턴 생성자
	 * @param business 비즈니스 객체
	 * @return DTO 객체를 반환
	 */
	public static BusinessResponseDto from(Business business){
		return new BusinessResponseDto(business);
	}
}
