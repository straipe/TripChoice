package com.ssafy12.tripchoice.tripplan.plan.exception;

import com.ssafy12.tripchoice.common.exception.http.CustomHttp404Exception;

public class PlanNotFoundException extends CustomHttp404Exception {
    public PlanNotFoundException() {
        this("Can't find the plan");
    }

    public PlanNotFoundException(String message) {
        super(message);
    }
}
