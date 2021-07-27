package com.usermanagementsystem.app;

import java.util.Arrays;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.usermanagementsystem.app.io.entity.AuthorityEntity;
import com.usermanagementsystem.app.io.entity.RoleEntity;
import com.usermanagementsystem.app.io.entity.UserEntity;
import com.usermanagementsystem.app.io.repositories.AuthorityRepository;
import com.usermanagementsystem.app.io.repositories.RoleRepository;
import com.usermanagementsystem.app.io.repositories.UserRepository;
import com.usermanagementsystem.app.shared.Utils;

@Component
public class InitialUsersSetup {

	@Autowired
	AuthorityRepository authorityRepository;

	@Autowired
	RoleRepository roleRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	Utils utils;
	
	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;

	@EventListener
	@Transactional
	public void onApplicationInit(ApplicationReadyEvent event) {
		System.out.println("Upon Application Startup");
		AuthorityEntity readAuthority = createAuthorithy("READ_AUTHORITY");
		AuthorityEntity writeAuthority = createAuthorithy("WRITE_AUTHORITY");
		AuthorityEntity deleteAuthority = createAuthorithy("DELETE_AUTHORITY");
		
		RoleEntity roleUser = createRole("USER_ROLE", Arrays.asList(readAuthority, writeAuthority));
		RoleEntity roleAdmin = createRole("ADMIN_ROLE", Arrays.asList(readAuthority, writeAuthority, deleteAuthority));
		
		if (roleAdmin == null) return;
		
		UserEntity adminUser = new UserEntity();
		adminUser.setFirstName("Sagar");
		adminUser.setLastName("Chada");
		adminUser.setEmail("sagarc99@hotmail.com");
		adminUser.setEmailVerificationStatus(true);
		adminUser.setUserId(utils.randomString());
		adminUser.setEncryptedPassword(bCryptPasswordEncoder.encode("123456789"));
		adminUser.setRoles(Arrays.asList(roleAdmin));
		
		userRepository.save(adminUser);
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
