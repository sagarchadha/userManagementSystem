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
	
}
