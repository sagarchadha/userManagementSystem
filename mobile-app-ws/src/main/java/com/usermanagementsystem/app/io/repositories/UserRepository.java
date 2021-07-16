package com.usermanagementsystem.app.io.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.usermanagementsystem.app.io.entity.UserEntity;

@Repository
public interface UserRepository extends PagingAndSortingRepository<UserEntity, Long> {

	UserEntity findByEmail(String email);
	UserEntity findByUserId(String userId);
	UserEntity findByEmailVerificationToken(String token);
	
	@Query(value="select user.firstName, user.lastName from UserEntity user where user.firstName LIKE %:keyword% or user.lastName LIKE %:keyword%")
	List<Object[]> findUserFirstNameLastNameByKeyword(@Param("keyword") String keyword);

}
