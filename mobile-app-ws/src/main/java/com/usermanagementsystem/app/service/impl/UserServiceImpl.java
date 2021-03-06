package com.usermanagementsystem.app.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.usermanagementsystem.app.exceptions.UserServiceException;
import com.usermanagementsystem.app.io.entity.PasswordResetTokenEntity;
import com.usermanagementsystem.app.io.entity.RoleEntity;
import com.usermanagementsystem.app.io.entity.UserEntity;
import com.usermanagementsystem.app.io.repositories.PasswordResetTokenRepository;
import com.usermanagementsystem.app.io.repositories.RoleRepository;
import com.usermanagementsystem.app.io.repositories.UserRepository;
import com.usermanagementsystem.app.security.UserPrincipal;
import com.usermanagementsystem.app.service.UserService;
import com.usermanagementsystem.app.shared.AmazonSES;
import com.usermanagementsystem.app.shared.Roles;
import com.usermanagementsystem.app.shared.Utils;
import com.usermanagementsystem.app.shared.dto.AddressDto;
import com.usermanagementsystem.app.shared.dto.UserDto;
import com.usermanagementsystem.app.ui.model.respone.ErrorMessages;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	PasswordResetTokenRepository passwordResetTokenRepository;
	
	@Autowired
	RoleRepository roleRepository;

	@Autowired
	Utils utils;

	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	AmazonSES amazonSES;

	@Override
	public UserDto createUser(UserDto user) {

		if (user.getFirstName().isEmpty())
			throw new UserServiceException(ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage());
		if (userRepository.findByEmail(user.getEmail()) != null)
			throw new UserServiceException("User already exists");

		for (int i = 0; i < user.getAddresses().size(); i++) {
			AddressDto address = user.getAddresses().get(i);
			address.setUserDetails(user);
			address.setAddressId(utils.randomString());
			user.getAddresses().set(i, address);
		}

		ModelMapper modelMapper = new ModelMapper();
		UserEntity userEntity = modelMapper.map(user, UserEntity.class);

		userEntity.setUserId(utils.randomString());
		userEntity.setEncryptedPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		userEntity.setEmailVerificationToken(utils.generateEmailVerificationToken(userEntity.getUserId()));
		userEntity.setEmailVerificationStatus(false);
		
		Collection<RoleEntity> roleEntities = new ArrayList<>();
		RoleEntity roleEntity = roleRepository.findByName(Roles.ROLE_USER.name());
		if (roleEntity != null) {
			roleEntities.add(roleEntity);
		}
		
		userEntity.setRoles(roleEntities);

		UserEntity storedUserDetails = userRepository.save(userEntity);
		UserDto returnValue = modelMapper.map(storedUserDetails, UserDto.class);

		if (System.getenv("ENABLE_EMAIL_SERVICE").equals("true")) {
			amazonSES.verifyEmail(returnValue);
		}

		return returnValue;
	}

	@Override
	public UserDto getUser(String email) {
		UserEntity userEntity = userRepository.findByEmail(email);

		if (userEntity == null)
			throw new UsernameNotFoundException(email);

		UserDto returnValue = new UserDto();
		BeanUtils.copyProperties(userEntity, returnValue);

		return returnValue;
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		UserEntity userEntity = userRepository.findByEmail(email);

		if (userEntity == null)
			throw new UsernameNotFoundException(email);

		return new UserPrincipal(userEntity);

	}

	@Override
	public UserDto getUserByUserId(String id) {
		UserDto returnValue = new UserDto();
		UserEntity userEntity = userRepository.findByUserId(id);
		if (userEntity == null)
			throw new UsernameNotFoundException("User " + id + " was not found");
		BeanUtils.copyProperties(userEntity, returnValue);
		return returnValue;
	}

	@Override
	public UserDto updateUser(String id, UserDto user) {
		UserDto returnValue = new UserDto();
		UserEntity userEntity = userRepository.findByUserId(id);
		if (userEntity == null)
			throw new UserServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());

		userEntity.setFirstName(user.getFirstName());
		userEntity.setLastName(user.getLastName());

		UserEntity updatedUserDetails = userRepository.save(userEntity);
		BeanUtils.copyProperties(updatedUserDetails, returnValue);
		return returnValue;
	}

	@Override
	public void deleteUser(String id) {
		UserEntity userEntity = userRepository.findByUserId(id);
		if (userEntity == null)
			throw new UserServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
		userRepository.delete(userEntity);

	}

	@Override
	public List<UserDto> getUsers(int page, int limit) {
		List<UserDto> returnValue = new ArrayList<>();

		if (page > 0)
			page -= 1;
		Pageable pageableRequest = PageRequest.of(page, limit);
		Page<UserEntity> usersPage = userRepository.findAll(pageableRequest);
		List<UserEntity> users = usersPage.getContent();

		for (UserEntity userEntity : users) {
			UserDto userDto = new UserDto();
			BeanUtils.copyProperties(userEntity, userDto);
			returnValue.add(userDto);
		}

		return returnValue;
	}

	@Override
	public boolean verifyEmail(String token) {

		UserEntity userEntity = userRepository.findByEmailVerificationToken(token);

		if (userEntity != null) {
			boolean tokenExpiredStatus = Utils.tokenExpiredStatus(token);

			if (!tokenExpiredStatus) {
				userEntity.setEmailVerificationToken(null);
				userEntity.setEmailVerificationStatus(Boolean.TRUE);
				userRepository.save(userEntity);
				return true;
			}
		}

		return false;
	}

	@Override
	public boolean passwordResetRequest(String email) {
		UserEntity userEntity = userRepository.findByEmail(email);
		if (userEntity == null) {
			return false;
		}

		String token = utils.generatePasswordResetToken(userEntity.getUserId());

		PasswordResetTokenEntity passwordResetTokenEntity = new PasswordResetTokenEntity();
		passwordResetTokenEntity.setToken(token);
		passwordResetTokenEntity.setUserDetails(userEntity);
		passwordResetTokenRepository.save(passwordResetTokenEntity);

		return new AmazonSES().sendPasswordReset(userEntity.getFirstName(),
				userEntity.getEmail(), token);
	}

	@Override
	public boolean passwordReset(String token, String password) {
		if (Utils.tokenExpiredStatus(token)) {
			return false;
		}
		
		PasswordResetTokenEntity passwordResetTokenEntity = passwordResetTokenRepository.findByToken(token);
		if (passwordResetTokenEntity == null) {
			return false;
		}
		
		String encodedPassword = bCryptPasswordEncoder.encode(password);
		
		UserEntity userEntity = passwordResetTokenEntity.getUserDetails();
		userEntity.setEncryptedPassword(encodedPassword);
		UserEntity savedUserEntity = userRepository.save(userEntity);
		
		passwordResetTokenRepository.delete(passwordResetTokenEntity);
		
		if (savedUserEntity != null && savedUserEntity.getEncryptedPassword().equalsIgnoreCase(encodedPassword)) {
			return true;
		}
		
		return false;
	}
}
