package com.synway.vo;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * Created by issuser on 2019/10/23.
 */
public class JWTToken implements AuthenticationToken {
    private String token;

    public JWTToken(String token){
        this.token = token;
    }

    @Override
    public Object getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }
}
