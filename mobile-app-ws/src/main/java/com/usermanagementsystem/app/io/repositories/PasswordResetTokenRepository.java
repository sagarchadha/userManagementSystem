package com.usermanagementsystem.app.io.repositories;

import org.springframework.data.repository.CrudRepository;

import com.usermanagementsystem.app.io.entity.PasswordResetTokenEntity;

public interface PasswordResetTokenRepository extends CrudRepository<PasswordResetTokenEntity, Long> {

	PasswordResetTokenEntity findByToken(String token);

}
