package com.appdeveloperblog.app.ws.security;

import com.appdeveloperblog.app.ws.SpringApplicationContext;

public class SecurityConstants {
	public static final long EMAIL_VERIFICATION_EXPIRATION_TIME = 864000000; // 10 days
	public static final long PASSWORD_RESET_EXPIRATION_TIME = 300000; // 5 mins
	public static final String TOKEN_PREFIX = "Bearer ";
	public static final String HEADER_STRING = "Authorization";
	public static final String SIGN_UP_URL = "/users";
	public static final String EMAIL_VERIFICATION_URL = "/users/email-verification";
	
	public static String getTokenSecret() {
		AppProperties appProperties = (AppProperties) SpringApplicationContext.getBean("AppProperties");
		return appProperties.getTokenSecret();
	}
}
