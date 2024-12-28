package com.ssafy12.tripchoice.board;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy12.tripchoice.board.dto.BoardSearchDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.ssafy12.tripchoice.board.QBoard.board;

@Slf4j
@Repository
@RequiredArgsConstructor
public class BoardQueryRepository {

    private final JPAQueryFactory queryFactory;

    public Page<Board> search(BoardSearchDto dto) {
        PageRequest pageRequest = PageRequest.of(dto.page()-1, dto.size(), Sort.by("createdAt").descending());
        List<Board> result = queryFactory.selectFrom(board)
                .where(conditionContains(dto.condition(), dto.keyword()))
                .offset(pageRequest.getOffset())
                .limit(pageRequest.getPageSize())
                .orderBy(board.createdAt.desc()).fetch();

        Long totalCount = queryFactory.select(board.count())
                .from(board)
                .where(conditionContains(dto.condition(), dto.keyword()))
                .fetchOne();
        totalCount = totalCount == null ? 0 : totalCount;

        return new PageImpl<>(result, pageRequest, totalCount);
    }

    private BooleanExpression conditionContains(BoardSearchCondition condition, String keyword) {
        if(condition == null) return Expressions.TRUE;
        switch (condition) {
            case ID -> {
                try {
                    return board.id.eq(Long.parseLong(keyword));
                }catch (NumberFormatException e) {
                    return Expressions.FALSE;
                }
            }
            case TITLE -> {
                return board.title.contains(keyword);
            }
            case WRITER -> {
                return board.userName.contains(keyword);
            } default -> {
                return Expressions.TRUE;
            }
        }
    }
}