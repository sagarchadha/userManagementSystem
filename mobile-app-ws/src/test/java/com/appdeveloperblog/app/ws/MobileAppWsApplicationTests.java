package com.appdeveloperblog.app.ws;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.appdeveloperblog.app.ws.service.UserService;
import com.appdeveloperblog.app.ws.ui.controller.UserController;

@SpringBootTest
class MobileAppWsApplicationTests {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserController userController;
	
	@Test
	public void contextLoads() throws Exception {
		assertThat(userService).isNotNull();
		assertThat(userController).isNotNull();
	}

}
