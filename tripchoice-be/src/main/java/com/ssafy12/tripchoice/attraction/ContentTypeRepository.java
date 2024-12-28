package com.ssafy12.tripchoice.attraction;

import com.ssafy12.tripchoice.attraction.domain.ContentType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ContentTypeRepository extends JpaRepository<ContentType, Integer> {
    Optional<ContentType> findByContentTypeId(Integer contentTypeId);
}
