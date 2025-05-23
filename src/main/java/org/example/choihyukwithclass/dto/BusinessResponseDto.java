package org.example.choihyukwithclass.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.example.choihyukwithclass.entity.Business;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;

@Getter
public class BusinessResponseDto implements Serializable {
	private final Long id;
	private final String name;
	private final String businessNumber;
	private final String address;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	private final LocalDateTime createdAt;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	private final LocalDateTime updatedAt;

	/**
	 * private 생성자 + 역직렬화에 사용되는 생성자
	 * @param id 회사 아이디
	 * @param name 회사명
	 * @param businessNumber 사업자등록번호 앞 6자리
	 * @param address 회사 주소
	 * @param createdAt 컬럼 생셩일자
	 * @param updatedAt 컬럼 수정일자
	 */
	@JsonCreator
	public BusinessResponseDto(
		@JsonProperty("id") Long id,
		@JsonProperty("name") String name,
		@JsonProperty("businessNumber") String businessNumber,
		@JsonProperty("address") String address,
		@JsonProperty("createdAt") LocalDateTime createdAt,
		@JsonProperty("updatedAt") LocalDateTime updatedAt
	) {
		this.id = id;
		this.name = name;
		this.businessNumber = businessNumber;
		this.address = address;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

	/**
	 * 정적 팩토리 메서드 패턴 생성자
	 * @param business 비즈니스 객체
	 * @return 비즈니스 객체로 만든 DTO 객체를 반환
	 */
	public static BusinessResponseDto from(Business business){
		return new BusinessResponseDto(
			business.getId(),
			business.getName(),
			business.getBusinessNumber(),
			business.getAddress(),
			business.getCreatedAt(),
			business.getUpdatedAt());
	}
}
