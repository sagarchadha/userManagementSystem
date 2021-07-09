package com.appdeveloperblog.app.ws.ui.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.appdeveloperblog.app.ws.ui.model.request.LoginRequestModel;

@RestController
public class AuthenticationController {
	@PostMapping("/login")
	public void login(LoginRequestModel loginRequestModel) {
		
	}
}
