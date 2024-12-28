package com.ssafy12.tripchoice.attraction.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "guguns")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GuGun {
	@Id
	private Integer no;
	private Integer sidoCode;
	private Integer gugunCode;
	private String gugunName;

	@Builder
	public GuGun(Integer no, Integer sidoCode, Integer gugunCode, String gugunName) {
		this.no = no;
		this.sidoCode = sidoCode;
		this.gugunCode = gugunCode;
		this.gugunName = gugunName;
	}
}
