package com.appdeveloperblog.app.ws.shared;

import static org.junit.jupiter.api.Assertions.*;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class UtilsTest {
	
	@Autowired
	Utils utils;
	
	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	void testTokenExpiredStatusFalse() {
		String token = utils.generateEmailVerificationToken(RandomStringUtils.randomAlphanumeric(30));
		assertNotNull(token);
		assertFalse(Utils.tokenExpiredStatus(token));
	}
	
	@Test
	void testTokenExpiredStatusTrue() {
		String expiredToken = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIyZGY1MmRlMC04ZjE0LTQ3MDItOTYzMS0zY2RlNzZjMmVmMmYiLCJleHAiOjE2MjQ0ODUzMzV9.QnnEqK0JOCjToZKTIwjX7iYw9erEBIw5aNc08BE8UR2_T62oxK-43j5Raog_LIxAjog25a32hheNaSbSnI3pcA";
		assertNotNull(expiredToken);
		assertTrue(Utils.tokenExpiredStatus(expiredToken));
	}

	@Test
	void testRandomString() {
		String userId = utils.randomString();
		String userId2 = utils.randomString();
		
		assertNotNull(userId);
		assertNotNull(userId2);
		assertFalse(userId.equals(userId2));
	}

}
