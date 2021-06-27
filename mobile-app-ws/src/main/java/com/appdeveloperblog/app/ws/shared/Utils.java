package com.appdeveloperblog.app.ws.shared;

import java.util.Date;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.appdeveloperblog.app.ws.security.SecurityConstants;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class Utils {

	private String generateToken(String id, long expirationTime) {
		return Jwts.builder().setSubject(id).setExpiration(new Date(System.currentTimeMillis() + expirationTime))
				.signWith(SignatureAlgorithm.HS512, SecurityConstants.getTokenSecret()).compact();
	}

	public String generateEmailVerificationToken(String userId) {
		return generateToken(userId, SecurityConstants.EMAIL_VERIFICATION_EXPIRATION_TIME);
	}

	public String generatePasswordResetToken(String userId) {
		return generateToken(userId, SecurityConstants.PASSWORD_RESET_EXPIRATION_TIME);
	}

	public static boolean tokenExpiredStatus(String token) {
		try {
			Claims claims = Jwts.parser().setSigningKey(SecurityConstants.getTokenSecret()).parseClaimsJws(token).getBody();

			Date tokenExpirationDate = claims.getExpiration();
			Date todayDate = new Date();

			tokenExpirationDate.before(todayDate);
		} catch(ExpiredJwtException e) {
			return true;
		}
		
		return false;
		
	}
	
	public String randomString() {
		return UUID.randomUUID().toString();
	}
}
