package com.ssafy12.tripchoice.common;

public class AccessTokenHolder {
    public static String accessToken;

    public static void setAccessToken(String accessToken) {
        AccessTokenHolder.accessToken = accessToken;
    }

    public static void clear() {
        accessToken = null;
    }
}
