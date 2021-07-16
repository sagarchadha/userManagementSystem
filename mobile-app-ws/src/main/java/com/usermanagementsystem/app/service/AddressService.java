package com.usermanagementsystem.app.service;

import java.util.List;

import com.usermanagementsystem.app.shared.dto.AddressDto;

public interface AddressService {
	List<AddressDto> getAddressList(String userId);
	AddressDto getAddress(String addressId);
}
