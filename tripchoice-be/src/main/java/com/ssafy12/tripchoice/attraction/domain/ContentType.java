package com.ssafy12.tripchoice.attraction.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "contenttypes")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ContentType {
	@Id
	private Integer contentTypeId;
	private String contentTypeName;

	@Builder
	public ContentType(Integer contentTypeId, String contentTypeName) {
		this.contentTypeId = contentTypeId;
		this.contentTypeName = contentTypeName;
	}
}
