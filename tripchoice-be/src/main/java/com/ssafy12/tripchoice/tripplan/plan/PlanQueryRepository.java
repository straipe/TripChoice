package com.ssafy12.tripchoice.tripplan.plan;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy12.tripchoice.attraction.domain.Attraction;
import com.ssafy12.tripchoice.attraction.dto.AttractionSearchDto;
import com.ssafy12.tripchoice.tripplan.plan.dto.PlanSearchDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.ssafy12.tripchoice.attraction.domain.QAttraction.attraction;
import static com.ssafy12.tripchoice.tripplan.plan.QPlan.plan;
import static com.ssafy12.tripchoice.user.QUser.user;

@Slf4j
@Repository
@RequiredArgsConstructor
public class PlanQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public Page<Plan> search(PlanSearchDto dto) {
        log.info("dto = {}", dto);
        BooleanBuilder builder = new BooleanBuilder();

        if (dto.title() != null) {
            builder.and(plan.title.containsIgnoreCase(dto.title()));
        }else if (dto.author() != null) {
            builder.and(user.name.containsIgnoreCase(dto.author()));
        }

        // Pageable 설정 (페이징 처리)
        var pageable = PageRequest.of(dto.page()-1, dto.size(), Sort.by("createdAt").descending());

        // QueryDSL을 이용해 필터링 적용
        var query = jpaQueryFactory.selectFrom(plan)
                .where(builder.getValue())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize());

        // 결과를 Page로 반환
        long total = jpaQueryFactory.selectFrom(plan)
                .where(builder)
                .fetchCount();

        return PageableExecutionUtils.getPage(query.fetch(), pageable, () -> total);
    }

    public List<Plan> searchTop4(){
        return jpaQueryFactory.selectFrom(plan)
                .orderBy(plan.title.asc())
                .limit(4)
                .fetch();
    }
}
