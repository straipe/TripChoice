package com.ssafy12.tripchoice.attraction;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy12.tripchoice.attraction.domain.Attraction;
import com.ssafy12.tripchoice.attraction.domain.Location;
import com.ssafy12.tripchoice.attraction.dto.AttractionSearchDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.querydsl.core.types.dsl.Expressions.numberTemplate;
import static com.ssafy12.tripchoice.attraction.domain.QAttraction.attraction;

@Slf4j
@Repository
@RequiredArgsConstructor
public class AttractionQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public Page<Attraction> search(AttractionSearchDto dto) {
        log.info("dto = {}", dto);
        double earthRadius = 6371; // 지구 반지름(km)
        String distanceExpression = "{0}*2*asin(sqrt(power(sin(radians({1}-{2})/2),2) + cos(radians({1})) * " +
                "cos(radians({3}))*power(sin(radians({4}-{5})/2),2)))"; //hoversine 공식

        BooleanBuilder builder = new BooleanBuilder();

        // "address" 키일 경우 sidoCode와 gugunCode 필터링
        if(dto.key() != null){
            if (dto.key().equals("address") && dto.sido_code() != null && dto.sido_code() != 0) {
                builder.and(attraction.areaCode.eq(dto.sido_code()));

                if (dto.gugun_code() != null && dto.gugun_code() != 0) {
                    builder.and(attraction.siGunGuCode.eq(dto.gugun_code()));
                }
            }

            if (dto.key().equals("word") && dto.word() != null && !dto.word().isEmpty()) {
                builder.and(attraction.title.containsIgnoreCase(dto.word()));
            }

            if (dto.key().equals("location") && dto.radius() != null) {
                Location now = new Location(37.501286, 127.0396029);

                builder.and(numberTemplate(Double.class, distanceExpression,
                        earthRadius,
                        now.latitude(), attraction.location.latitude,
                        now.latitude(), attraction.location.longitude,
                        now.longitude()
                        ).loe(dto.radius())
                );
            }
        }

        if (dto.content_type_id() != null && dto.content_type_id() != 0) {
            builder.and(attraction.contentType.contentTypeId.eq(dto.content_type_id()));
        }
        // Pageable 설정 (페이징 처리)
        var pageable = PageRequest.of(dto.page()-1, dto.size(), Sort.by("no").descending());

        // QueryDSL을 이용해 필터링 적용
        var query = jpaQueryFactory.selectFrom(attraction)
                .where(builder.getValue())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize());

        // 결과를 Page로 반환
        long total = jpaQueryFactory.selectFrom(attraction)
                .where(builder)
                .fetchCount();

        return PageableExecutionUtils.getPage(query.fetch(), pageable, () -> total);
    }

    public List<Attraction> searchTop4(){
        return jpaQueryFactory.selectFrom(attraction)
                .orderBy(attraction.title.asc())
                .limit(4)
                .fetch();
    }
}
