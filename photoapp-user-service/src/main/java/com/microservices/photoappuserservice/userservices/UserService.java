package com.microservices.photoappuserservice.userservices;

import org.apache.coyote.BadRequestException;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.microservices.photoappuserservice.model.UserDto;

public interface UserService extends UserDetailsService {
	
	public UserDto createUser(UserDto userDto) throws BadRequestException;
	public UserDto getUserByEmail(String email);

}
