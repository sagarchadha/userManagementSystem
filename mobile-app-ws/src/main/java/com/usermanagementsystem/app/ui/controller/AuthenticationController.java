package com.usermanagementsystem.app.ui.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.usermanagementsystem.app.shared.StringConstants;
import com.usermanagementsystem.app.ui.model.request.LoginRequestModel;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ResponseHeader;

@RestController
public class AuthenticationController {
	@ApiOperation(value=StringConstants.loginValue, notes=StringConstants.loginNotes)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Response Headers", responseHeaders = {
			@ResponseHeader(name = "authorization", description = "Bearer <JWT value here>", response = String.class),
			@ResponseHeader(name = "userId", description = "<Public User Id value here>", response = String.class) }) })
	@PostMapping("/users/login")
	public void swaggerLogin(@RequestBody LoginRequestModel loginRequestModel) {
		throw new IllegalStateException("This method should not be called.");
	}
}
