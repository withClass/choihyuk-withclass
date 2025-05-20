package org.example.choihyukwithclass.entity;

import org.example.choihyukwithclass.common.entity.BaseEntity;
import org.example.choihyukwithclass.dto.BusinessRequestDto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
public class Business extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "business_name")
	private String name;

	@Column(name = "industry_name")
	private String industry;

	@Column(name = "post_code")
	private String postCode;

	@Column(name = "registration_number")
	private String businessNumber; // 사업자등록번호 앞 6자리

	@Column(name = "road_address")
	private String address;

	/**
	 * 빌더 생성자
	 * @param name 회사명
	 * @param industry 분류 산업
	 * @param postCode 우편번호
	 * @param businessNumber 사업자등록번호 앞 6자리
	 * @param address 회사 주소
	 */
	@Builder
	public Business(String name, String industry, String postCode, String businessNumber, String address) {
		this.name = name;
		this.industry = industry;
		this.postCode = postCode;
		this.businessNumber = businessNumber;
		this.address = address;
	}

	// TODO: 추후 필요시, 구현예정
	public void updateBusiness(BusinessRequestDto dto){
	}
}
