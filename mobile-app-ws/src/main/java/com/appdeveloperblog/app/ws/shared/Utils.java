package com.appdeveloperblog.app.ws.shared;

import java.util.UUID;

import org.springframework.stereotype.Component;

@Component
public class Utils {
	
	public String randomString() {
		return UUID.randomUUID().toString();
	}
}
