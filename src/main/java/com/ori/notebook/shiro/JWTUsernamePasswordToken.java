package com.ori.notebook.shiro;

import com.ori.notebook.utils.JwtUtils;
import org.apache.shiro.authc.UsernamePasswordToken;

import java.util.List;

public class JWTUsernamePasswordToken extends UsernamePasswordToken {
    private String token;

    public JWTUsernamePasswordToken(String token) {
        this.token = token;
    }
//    public String getToken() {
//        return this.token;
//    }
    @Override
    public String getCredentials() {
        return this.token;
    }
}
