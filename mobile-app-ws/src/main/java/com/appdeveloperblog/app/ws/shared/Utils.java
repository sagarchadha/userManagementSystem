package com.appdeveloperblog.app.ws.shared;

import java.util.UUID;

import org.springframework.stereotype.Component;

@Component
public class Utils {
	
	public UUID generateUUID() {
		return UUID.randomUUID();
	}
}
