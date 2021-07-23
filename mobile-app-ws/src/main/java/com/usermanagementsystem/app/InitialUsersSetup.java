package com.usermanagementsystem.app;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class InitialUsersSetup {
	
	@EventListener
	public void onApplicationInit(ApplicationReadyEvent event) {
		System.out.println("Upon Application Startup");
	}
}
