package com.appdeveloperblog.app.ws.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.appdeveloperblog.app.ws.io.entity.UserEntity;
import com.appdeveloperblog.app.ws.io.repositories.UserRepository;
import com.appdeveloperblog.app.ws.shared.AmazonSES;
import com.appdeveloperblog.app.ws.shared.Utils;
import com.appdeveloperblog.app.ws.shared.dto.AddressDto;
import com.appdeveloperblog.app.ws.shared.dto.UserDto;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

	@InjectMocks
	UserServiceImpl userService;

	@Mock
	UserRepository userRepository;

	@Mock
	Utils utils;

	@Mock
	BCryptPasswordEncoder bCryptPasswordEncoder;

	@Mock
	AmazonSES amazonSES;

	@Spy
	private ModelMapper modelMapper;

	private UserDto userDto;

	private AddressDto addressDto;

	@BeforeEach
	void setUp() throws Exception {
		setUpUserDto();
		setUpAddressDto();

	}

	private void setUpUserDto() {
		userDto = new UserDto();
		userDto.setId(0);
		userDto.setEmail(RandomStringUtils.randomAlphanumeric(30));
		userDto.setEmailVerificationStatus(false);
		userDto.setEmailVerificationToken(RandomStringUtils.randomAlphanumeric(50));
		userDto.setEncryptedPassword(RandomStringUtils.randomAlphanumeric(50));
		userDto.setFirstName(RandomStringUtils.randomAlphabetic(10));
		userDto.setLastName(RandomStringUtils.randomAlphabetic(10));
		userDto.setPassword(RandomStringUtils.randomAlphabetic(30));

	}

	private void setUpAddressDto() {
		addressDto = new AddressDto();
		addressDto.setAddressId(RandomStringUtils.randomAlphanumeric(50));
		addressDto.setCity(RandomStringUtils.randomAlphabetic(10));
		addressDto.setCountry(RandomStringUtils.randomAlphabetic(10));
		addressDto.setId(0);
		addressDto.setPostalCode(RandomStringUtils.randomAlphanumeric(6));
		addressDto.setType(RandomStringUtils.randomAlphabetic(10));

	}

	@Test
	void testGetUser() {
		UserEntity userEntity = modelMapper.map(userDto, UserEntity.class);

		when(userRepository.findByEmail(anyString())).thenReturn(userEntity);

		UserDto userDtoResult = userService.getUser(userDto.getEmail());
		assertNotNull(userDtoResult);

		assertEquals(userDto.getFirstName(), userDtoResult.getFirstName());
		assertEquals(userDto.getLastName(), userDtoResult.getLastName());
		assertEquals(userDto.getEmail(), userDtoResult.getEmail());
		assertEquals(userDto.getEncryptedPassword(), userDtoResult.getEncryptedPassword());

	}

	@Test
	void testgetUserNotFound() {
		when(userRepository.findByEmail(anyString())).thenReturn(null);

		assertThrows(UsernameNotFoundException.class, () -> {
			userService.getUser(userDto.getEmail());
		});
	}

}
