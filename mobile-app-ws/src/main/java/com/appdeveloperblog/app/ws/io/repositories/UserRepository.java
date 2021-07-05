package com.appdeveloperblog.app.ws.io.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.appdeveloperblog.app.ws.io.entity.UserEntity;

@Repository
public interface UserRepository extends PagingAndSortingRepository<UserEntity, Long> {

	UserEntity findByEmail(String email);
	UserEntity findByUserId(String userId);
	UserEntity findByEmailVerificationToken(String token);
	
	@Query(value="select user.first_name, user.last_name from Users user where user.first_name LIKE %:name% or user.last_name LIKE %:name%", nativeQuery = true)
	List<Object[]> findUserFirstNameLastNameByName(@Param("name") String name);

}
