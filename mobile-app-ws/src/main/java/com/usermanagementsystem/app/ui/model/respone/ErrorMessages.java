package com.usermanagementsystem.app.ui.model.respone;

public enum ErrorMessages {
	MISSING_REQUIRED_FIELD("Missing required field."),
	RECORD_ALREADY_EXISTS("Record already exists in database"),
	INTERNAL_SERVER_ERROR("Internal server error"),
	NO_RECORD_FOUND("Record does not exist"),
	UNAUTHORIZED("User is not authorized"),
	UPDATE_ERROR("Could not update the record"),
	DELETE_ERROR("Could not delete the record"),
	EMAIL_ADDRESS_NOT_VERIFIED("Email address could not be verified");
	
	private String errorMessage;
	
	ErrorMessages(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
	public String getErrorMessage() {
		return this.errorMessage;
	}
	
	public void setErrorMessge(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
	
}
