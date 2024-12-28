package com.ssafy12.tripchoice.hotplace;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HotPlaceRepository extends JpaRepository<HotPlace, Long> {
    @Override
    @EntityGraph(attributePaths = {"contentType"})
    Optional<HotPlace> findById(Long id);

    @Override
    @EntityGraph(attributePaths = {"contentType"})
    Page<HotPlace> findAll(Pageable pageable);
}
