package com.ssafy12.tripchoice.board;

import java.util.Objects;

public enum BoardSearchCondition {
    ALL("all"), ID("id"), TITLE("title"), WRITER("writer");

    String condition;

    BoardSearchCondition(String condition) {
        this.condition = condition;
    }

    public String condition(){
        return condition;
    }

    /**
     * @param conditionString
     * @return condition에 해당하는 ENUM; 찾지 못하는 경우 ALL 반환
     */
    public static BoardSearchCondition of(String conditionString) {
        for (BoardSearchCondition condition : BoardSearchCondition.values()) {
            if (Objects.equals(condition.condition(), conditionString)) {
                return condition;
            }
        }
        return ALL;
    }
}
