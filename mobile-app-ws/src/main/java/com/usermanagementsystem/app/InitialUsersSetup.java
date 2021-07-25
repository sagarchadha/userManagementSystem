package com.usermanagementsystem.app;

import java.util.Arrays;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.usermanagementsystem.app.io.entity.AuthorityEntity;
import com.usermanagementsystem.app.io.entity.RoleEntity;
import com.usermanagementsystem.app.io.repositories.AuthorityRepository;
import com.usermanagementsystem.app.io.repositories.RoleRepository;

@Component
public class InitialUsersSetup {

	@Autowired
	AuthorityRepository authorityRepository;

	@Autowired
	RoleRepository roleRepository;

	@EventListener
	@Transactional
	public void onApplicationInit(ApplicationReadyEvent event) {
		System.out.println("Upon Application Startup");
		AuthorityEntity readAuthority = createAuthorithy("READ_AUTHORITY");
		AuthorityEntity writeAuthority = createAuthorithy("WRITE_AUTHORITY");
		AuthorityEntity deleteAuthority = createAuthorithy("DELETE_AUTHORITY");
		
		createRole("USER_ROLE", Arrays.asList(readAuthority, writeAuthority));
		createRole("ADMIN_ROLE", Arrays.asList(readAuthority, writeAuthority, deleteAuthority));
	}

	@Transactional
	private AuthorityEntity createAuthorithy(String name) {
		AuthorityEntity authority = authorityRepository.findByName(name);
		if (authority == null) {
			authority = new AuthorityEntity(name);
			authorityRepository.save(authority);
		}
		return authority;
	}

	@Transactional
	private RoleEntity createRole(String name, Collection<AuthorityEntity> authorities) {
		RoleEntity role = roleRepository.findByName(name);

		if (role == null) {
			role = new RoleEntity(name);
			role.setAuthorities(authorities);
			roleRepository.save(role);
		}

		return role;
	}
}
