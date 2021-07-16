package com.usermanagementsystem.app.exceptions;

public class UserServiceException extends RuntimeException {

	private static final long serialVersionUID = -3393273220211107601L;
	
	public UserServiceException(String message) {
		super(message);
	}

}
