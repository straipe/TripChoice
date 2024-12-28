package com.ssafy12.tripchoice.hotplace;

import com.ssafy12.tripchoice.attraction.domain.ContentType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "hotplaces")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class HotPlace {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private String userName;
    private String title;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "content_type_id")
    private ContentType contentType;
    private String address;
    private String summary;
    private Double longitude;
    private Double latitude;
    @Lob
    private byte[] image;

    @Builder
    public HotPlace(Long userId, String userName, String title, ContentType contentType, String address, String summary, Double longitude, Double latitude, byte[] image) {
        this.userId = userId;
        this.userName = userName;
        this.title = title;
        this.contentType = contentType;
        this.address = address;
        this.summary = summary;
        this.longitude = longitude;
        this.latitude = latitude;
        this.image = image;
    }
}
