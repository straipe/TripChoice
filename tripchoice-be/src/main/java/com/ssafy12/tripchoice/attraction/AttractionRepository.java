package com.ssafy12.tripchoice.attraction;

import com.ssafy12.tripchoice.attraction.domain.Attraction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AttractionRepository extends JpaRepository<Attraction, Integer> {
    @Override
    @EntityGraph(attributePaths = {"contentType"})
    Optional<Attraction> findById(Integer id);

    @Override
    @EntityGraph(attributePaths = {"contentType"})
    Page<Attraction> findAll(Pageable pageable);
}
