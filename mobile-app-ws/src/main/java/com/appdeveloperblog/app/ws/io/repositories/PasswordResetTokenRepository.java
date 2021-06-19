package com.appdeveloperblog.app.ws.io.repositories;

import org.springframework.data.repository.CrudRepository;

import com.appdeveloperblog.app.ws.io.entity.PasswordResetTokenEntity;

public interface PasswordResetTokenRepository extends CrudRepository<PasswordResetTokenEntity, Long> {

}
