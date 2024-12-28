package com.ssafy12.tripchoice.attraction.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "attractions")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Attraction {
    @Id
    @Schema(description = "관광지 PK", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer no;
    private Integer contentId;

    @Schema(description = "관광지 이름", requiredMode = Schema.RequiredMode.REQUIRED)
    private String title;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contentTypeId")
    private ContentType contentType;

    @Schema(description = "시/도 코드", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer areaCode;

    @Schema(description = "구/군 코드", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer siGunGuCode;

    @Schema(description = "이미지1 URL", requiredMode = Schema.RequiredMode.REQUIRED)
    private String firstImage1;

    @Schema(description = "이미지2 URL", requiredMode = Schema.RequiredMode.REQUIRED)
    private String firstImage2;

    @Schema(description = "지도 레벨", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer mapLevel;

    private Location location;

    @Schema(description = "전화번호", requiredMode = Schema.RequiredMode.REQUIRED)
    private String tel;

    @Schema(description = "주소1", requiredMode = Schema.RequiredMode.REQUIRED)
    private String addr1;

    @Schema(description = "주소2", requiredMode = Schema.RequiredMode.REQUIRED)
    private String addr2;

    @Lob
    private String homepage;

    @Lob
    private String overview;

    @Builder
    public Attraction(Integer no, Integer contentId, String title, ContentType contentType, Integer areaCode, Integer siGunGuCode, String firstImage1, String firstImage2, Integer mapLevel, Location location, String tel, String addr1, String addr2, String homepage, String overview) {
        this.no = no;
        this.contentId = contentId;
        this.title = title;
        this.contentType = contentType;
        this.areaCode = areaCode;
        this.siGunGuCode = siGunGuCode;
        this.firstImage1 = firstImage1;
        this.firstImage2 = firstImage2;
        this.mapLevel = mapLevel;
        this.location = location;
        this.tel = tel;
        this.addr1 = addr1;
        this.addr2 = addr2;
        this.homepage = homepage;
        this.overview = overview;
    }
}
