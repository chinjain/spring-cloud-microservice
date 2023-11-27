package com.microservices.photoappuserservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.microservices.photoappuserservice.model.UserDto;
import com.microservices.photoappuserservice.model.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
	
//	@Query(value = "SELECT u FROM USERS u WHERE u.name=?1")
//	UserDto findUserByName(String name);
	
//	@Query(value = "SELECT u FROM UserEntity u WHERE u.email=:email")
//	@Param("email")
	public UserEntity findByEmail(String email);

}
