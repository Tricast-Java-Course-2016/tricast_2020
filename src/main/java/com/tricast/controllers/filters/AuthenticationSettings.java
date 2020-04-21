package com.tricast.controllers.filters;

public interface AuthenticationSettings {
	static final String SECRET_KEY = "verySecretKey";
    static final String ISSUER = "TricastTanf2020";

    static final int TOKEN_EXPIRE_TIME_IN_HOURS = 6;

    static final String CLAIM_USER_IDENTIFIER = "uid";
    static final String CLAIM_USERNAME_IDENTIFIER = "uud";
    static final String CLAIM_ROLE_IDENTIFIER = "rid";
}
