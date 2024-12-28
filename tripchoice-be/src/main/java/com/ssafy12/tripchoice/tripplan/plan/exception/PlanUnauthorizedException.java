package com.ssafy12.tripchoice.tripplan.plan.exception;

import com.ssafy12.tripchoice.common.exception.http.CustomHttp403Exception;

public class PlanUnauthorizedException extends CustomHttp403Exception {
    public PlanUnauthorizedException() {
        this("해당 플랜에 대한 권한이 없습니다");
    }

    public PlanUnauthorizedException(String message) {
        super(message);
    }
}
