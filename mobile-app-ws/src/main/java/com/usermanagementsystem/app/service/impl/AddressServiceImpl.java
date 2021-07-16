package com.usermanagementsystem.app.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.usermanagementsystem.app.io.entity.AddressEntity;
import com.usermanagementsystem.app.io.entity.UserEntity;
import com.usermanagementsystem.app.io.repositories.AddressRepository;
import com.usermanagementsystem.app.io.repositories.UserRepository;
import com.usermanagementsystem.app.service.AddressService;
import com.usermanagementsystem.app.shared.dto.AddressDto;

@Service
public class AddressServiceImpl implements AddressService {
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	AddressRepository addressRepository;
	
	@Override
	public List<AddressDto> getAddressList(String userId) {
		List<AddressDto> returnValue = new ArrayList<>();
		
		UserEntity userEntity = userRepository.findByUserId(userId);
		
		if (userEntity == null) return returnValue;

		ModelMapper modelMapper = new ModelMapper();

		List<AddressEntity> addressList = userEntity.getAddresses();
		for (AddressEntity addressEntity:addressList) {
			returnValue.add(modelMapper.map(addressEntity, AddressDto.class));
		}
		
		return returnValue;
	}

	@Override
	public AddressDto getAddress(String addressId) {
		AddressDto returnValue = null;
		
		AddressEntity addressEntity = addressRepository.findByAddressId(addressId);
		
		if (addressEntity != null) {
			returnValue = new ModelMapper().map(addressEntity, AddressDto.class);
		}
		
		return returnValue; 
	}

}
