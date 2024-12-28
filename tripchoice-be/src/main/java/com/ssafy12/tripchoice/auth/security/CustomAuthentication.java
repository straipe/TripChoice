package com.ssafy12.tripchoice.auth.security;

import com.ssafy12.tripchoice.auth.security.ul.UserId;

public interface CustomAuthentication {

    UserId getPrincipal();
}