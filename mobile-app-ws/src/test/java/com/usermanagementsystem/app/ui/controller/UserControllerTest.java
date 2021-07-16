package com.usermanagementsystem.app.ui.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.usermanagementsystem.app.service.impl.UserServiceImpl;
import com.usermanagementsystem.app.shared.dto.UserDto;
import com.usermanagementsystem.app.ui.model.respone.UserRest;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

	@InjectMocks
	UserController userController;

	@Mock
	UserServiceImpl userService;
	
	private UserDto userDto;

	@BeforeEach
	void setUp() throws Exception {
		setUpUserDto();
	}
	
	private void setUpUserDto() {
		userDto = new UserDto();
		userDto.setId(0);
		userDto.setUserId(RandomStringUtils.randomAlphanumeric(30));
		userDto.setEmail(RandomStringUtils.randomAlphanumeric(30));
		userDto.setEmailVerificationStatus(false);
		userDto.setEmailVerificationToken(RandomStringUtils.randomAlphanumeric(50));
		userDto.setEncryptedPassword(RandomStringUtils.randomAlphanumeric(50));
		userDto.setFirstName(RandomStringUtils.randomAlphabetic(10));
		userDto.setLastName(RandomStringUtils.randomAlphabetic(10));
		userDto.setPassword(RandomStringUtils.randomAlphabetic(30));

	}

	@Test
	void testGetUser() {
		when(userService.getUserByUserId(anyString())).thenReturn(userDto);
		UserRest userRestResult = userController.getUser(userDto.getUserId());
		assertNotNull(userRestResult);

		assertEquals(userDto.getFirstName(), userRestResult.getFirstName());
		assertEquals(userDto.getLastName(), userRestResult.getLastName());
		assertEquals(userDto.getEmail(), userRestResult.getEmail());
	}

}
