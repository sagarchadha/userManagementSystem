package com.appdeveloperblog.app.ws.shared;

public interface StringConstants {
	String getUserValue = "Get User Details Endpoint";
	String getUserNotes = "This endpoint returns the user details. It is called with the user id as a parameter.";

	String getAllUsersValue = "Get All Users Endpoint";
	String getAllUsersNotes = "This endpoint returns all of the users. It is called with the page number and limit of users per each page.";

	String createUserValue = "Create User Endpoint";
	String createUserNotes = "This endpoint creates a user. It is called with the all of the details of the user in the request body.";

	String updateUserValue = "Update User Endpoint";
	String updateUserNotes = "This endpoint updates a user. It is called with the all of the details of the updated user in the request body and the id of the user as a parameter.";

	String deleteUserValue = "Delete User Endpoint";
	String deleteUserNotes = "This endpoint deletes a user. It is called with the id of the user as a parameter.";

	String getUserAddressesValue = "Get All User Addresses Endpoint";
	String getUserAddressesNotes = "This endpoint retrieves all of the addresses under a user. It is called with the id of the user as a parameter.";

	String getUserAddressValue = "Get User Address Endpoint";
	String getUserAddressNotes = "This endpoint retrieves a single addresses under a user. It is called with the id of the user and id of the address as parameters.";
	
	String verifyEmailValue = "Verify Email Endpoint";
	String verifyEmailNotes = "This endpoint will verify an email if it hasn't been verified and the token has not expired. It is called with the email token as a parameter.";
	
	String passwordResetRequestValue = "Password Reset Request Endpoint";
	String passwordResetRequestNotes = "This endpoint will submit a request to reset the password of the user with the specified email. This request will send out an email with the token to reset the password.";
	
	String passwordResetValue = "Password Reset Endpoint";
	String passwordResetNotes = "This endpoint will process the change to a new password given a valid token with the new password in the body of the request. Before using this endpoint, a request to the password reset request endpoint should be sent.";

	String loginValue = "Login Endpoint";
	String loginNotes = "This endpoint will login the user given a valid email and password, The user must login to access the other endpoints.";
}
