package com.usermanagementsystem.app.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.usermanagementsystem.app.exceptions.UserServiceException;
import com.usermanagementsystem.app.io.entity.UserEntity;
import com.usermanagementsystem.app.io.repositories.UserRepository;
import com.usermanagementsystem.app.shared.AmazonSES;
import com.usermanagementsystem.app.shared.Utils;
import com.usermanagementsystem.app.shared.dto.AddressDto;
import com.usermanagementsystem.app.shared.dto.UserDto;

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
		userDto.setUserId(RandomStringUtils.randomAlphanumeric(30));
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
	void testGetUserNotFound() {
		when(userRepository.findByEmail(anyString())).thenReturn(null);

		assertThrows(UsernameNotFoundException.class, () -> {
			userService.getUser(userDto.getEmail());
		});
	}

	@Test
	void testCreateUser() {
		UserEntity userEntity = modelMapper.map(userDto, UserEntity.class);
		when(userRepository.findByEmail(anyString())).thenReturn(null);
		when(utils.randomString()).thenReturn(RandomStringUtils.randomAlphanumeric(10));
		when(bCryptPasswordEncoder.encode(anyString())).thenReturn(RandomStringUtils.randomAlphanumeric(10));
		when(userRepository.save(any(UserEntity.class))).thenReturn(userEntity);
		Mockito.doNothing().when(amazonSES).verifyEmail(any(UserDto.class));

		List<AddressDto> addressList = new ArrayList<>();
		addressList.add(addressDto);
		userDto.setAddresses(addressList);

		UserDto userDtoResult = userService.createUser(userDto);
		assertNotNull(userDtoResult);

		assertEquals(userDto.getFirstName(), userDtoResult.getFirstName());
		assertEquals(userDto.getLastName(), userDtoResult.getLastName());
		assertEquals(userDto.getEmail(), userDtoResult.getEmail());
		assertEquals(userDto.getEncryptedPassword(), userDtoResult.getEncryptedPassword());
	}

	@Test
	void testCreateUserExits() {
		UserEntity userEntity = modelMapper.map(userDto, UserEntity.class);
		when(userRepository.findByEmail(anyString())).thenReturn(userEntity);
		assertThrows(UserServiceException.class, () -> {
			userService.createUser(userDto);
		});

	}

	@Test
	void testGetUserByUserId() {
		UserEntity userEntity = modelMapper.map(userDto, UserEntity.class);

		when(userRepository.findByUserId(anyString())).thenReturn(userEntity);

		UserDto userDtoResult = userService.getUserByUserId(userDto.getUserId());
		assertNotNull(userDtoResult);

		assertEquals(userDto.getFirstName(), userDtoResult.getFirstName());
		assertEquals(userDto.getLastName(), userDtoResult.getLastName());
		assertEquals(userDto.getEmail(), userDtoResult.getEmail());
		assertEquals(userDto.getEncryptedPassword(), userDtoResult.getEncryptedPassword());

	}
	
	@Test
	void testGetUserByUserIdNotFound() {
		when(userRepository.findByUserId(anyString())).thenReturn(null);

		assertThrows(UsernameNotFoundException.class, () -> {
			userService.getUserByUserId(userDto.getUserId());
		});
	}
	
	@Test
	void testUpdateUser() {
		UserEntity userEntity = modelMapper.map(userDto, UserEntity.class);
		when(userRepository.findByUserId(anyString())).thenReturn(userEntity);
		when(userRepository.save(any(UserEntity.class))).thenReturn(userEntity);

		UserDto userDtoResult = userService.updateUser(userDto.getUserId(), userDto);
		assertNotNull(userDtoResult);

		assertEquals(userDto.getFirstName(), userDtoResult.getFirstName());
		assertEquals(userDto.getLastName(), userDtoResult.getLastName());
		assertEquals(userDto.getEmail(), userDtoResult.getEmail());
		assertEquals(userDto.getEncryptedPassword(), userDtoResult.getEncryptedPassword());
	}
	
	@Test
	void testUpdateUserNotFound() {
		when(userRepository.findByUserId(anyString())).thenReturn(null);

		assertThrows(UserServiceException.class, () -> {
			userService.updateUser(userDto.getUserId(), userDto);
		});
	}
	
	@Test
	void testDeleteUser() {
		UserEntity userEntity = modelMapper.map(userDto, UserEntity.class);

		when(userRepository.findByUserId(anyString())).thenReturn(userEntity);

		userService.deleteUser(userDto.getUserId());
		verify(userRepository).delete(userEntity);

	}
	
	@Test
	void testDeleteUserNotFound() {
		when(userRepository.findByUserId(anyString())).thenReturn(null);

		assertThrows(UserServiceException.class, () -> {
			userService.deleteUser(userDto.getUserId());
		});
	}
}
