package com.ssafy12.tripchoice.hotplace;

import com.ssafy12.tripchoice.attraction.ContentTypeRepository;
import com.ssafy12.tripchoice.attraction.domain.ContentType;
import com.ssafy12.tripchoice.board.dto.response.BoardDto;
import com.ssafy12.tripchoice.common.exception.http.CustomHttp404Exception;
import com.ssafy12.tripchoice.hotplace.dto.HotPlaceCreateDto;
import com.ssafy12.tripchoice.hotplace.dto.HotPlaceSearchDto;
import com.ssafy12.tripchoice.hotplace.dto.response.HotPlaceDto;
import com.ssafy12.tripchoice.user.User;
import com.ssafy12.tripchoice.user.UserRepository;
import com.ssafy12.tripchoice.user.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;
import java.util.Base64;


@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HotPlaceService {

    private final HotPlaceRepository hotplaceRepository;
    private final HotPlaceQueryRepository hotplaceQueryRepository;
    private final UserRepository userRepository;
    private final ContentTypeRepository contentTypeRepository;

    @Transactional
    public void createHotPlace(HotPlaceCreateDto dto) {
        log.info("@@@@@@@@requested content type id = {}", dto.contentTypeId());
        ContentType contentType = contentTypeRepository.findByContentTypeId(dto.contentTypeId())
                .orElseThrow(() -> new CustomHttp404Exception("ContentType not found"));
        User user = userRepository.findById(dto.userId()).orElseThrow(UserNotFoundException::new);
        String base64String = !dto.image().isEmpty() ? dto.image().split(",")[1]:"";
        byte[] decodedBytes = !base64String.isEmpty() ? Base64.getDecoder().decode(base64String):null;
        HotPlace hotPlace = HotPlace.builder()
                .userId(dto.userId())
                .userName(user.getName())
                .title(dto.title())
                .contentType(contentType)
                .address(dto.address())
                .summary(dto.summary())
                .longitude(dto.longitude())
                .latitude(dto.latitude())
                .image(decodedBytes)
                .build();

        hotplaceRepository.save(hotPlace);
    }

    public HotPlaceDto getHotPlace(Long hotPlaceId) {
        HotPlace hotPlace = hotplaceRepository.findById(hotPlaceId)
                .orElseThrow(() -> new CustomHttp404Exception("HotPlace not found"));
        return toDto(hotPlace);
    }

    public Page<HotPlaceDto> search(HotPlaceSearchDto dto) {
        return hotplaceQueryRepository.search(dto).map(this::toDto);
    }

    private HotPlaceDto toDto(HotPlace hotPlace) {
        String retrievedBase64 = hotPlace.getImage() != null ? "data:image/png;base64," + Base64.getEncoder().encodeToString(hotPlace.getImage()) : "";
        return HotPlaceDto.builder()
                .id(hotPlace.getId())
                .userId(hotPlace.getUserId())
                .userName(hotPlace.getUserName())
                .title(hotPlace.getTitle())
                .summary(hotPlace.getSummary())
                .contentType(hotPlace.getContentType().getContentTypeName())
                .address(hotPlace.getAddress())
                .longitude(hotPlace.getLongitude())
                .latitude(hotPlace.getLatitude())
                .imgUrl(retrievedBase64)
                .build();
    }
}
