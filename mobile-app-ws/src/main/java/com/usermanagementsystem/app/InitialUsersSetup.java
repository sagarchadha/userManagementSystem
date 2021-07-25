package com.usermanagementsystem.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.usermanagementsystem.app.io.entity.AuthorityEntity;
import com.usermanagementsystem.app.io.repositories.AuthorityRepository;

@Component
public class InitialUsersSetup {
	
	@Autowired
	AuthorityRepository authorityRepository;
	
	@EventListener
	public void onApplicationInit(ApplicationReadyEvent event) {
		System.out.println("Upon Application Startup");
		AuthorityEntity readAuthority = createAuthorithy("READ_AUTHORITY");
		AuthorityEntity writeAuthority = createAuthorithy("WRITE_AUTHORITY");
		AuthorityEntity deleteAuthority = createAuthorithy("DELETE_AUTHORITY");
	}
	
	private AuthorityEntity createAuthorithy(String name) {
		AuthorityEntity authority = authorityRepository.findByName(name);
		if (authority == null) {
			authority = new AuthorityEntity(name);
			authorityRepository.save(authority);
		}
		return authority;
	}
}
