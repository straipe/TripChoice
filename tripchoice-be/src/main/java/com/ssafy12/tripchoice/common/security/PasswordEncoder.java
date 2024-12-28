package com.ssafy12.tripchoice.common.security;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Component;

@Component
public class PasswordEncoder {

    public String encode(String password){
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }
    public boolean matches(String plain, String encrypted){
        return BCrypt.checkpw(plain, encrypted);
    }
}
