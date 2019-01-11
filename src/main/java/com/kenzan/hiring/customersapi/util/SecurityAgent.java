package com.kenzan.hiring.customersapi.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
/*
    Simple class that encapsulates security logic.
    Per our requirements we only need a method that allows or
    refuses access to a restricted resource by validating a token
    provided within the header request 'auth-token'
*/
@Component
public class SecurityAgent {

    @Value("${auth.default.token}")
    private String AUTH_DEFAULT_TOKEN;

    public boolean isAuthorizedRequest(String authToken){
        return AUTH_DEFAULT_TOKEN.equals(authToken);
    }

}