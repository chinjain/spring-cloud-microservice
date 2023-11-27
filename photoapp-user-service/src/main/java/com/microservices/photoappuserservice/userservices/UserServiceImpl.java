package com.microservices.photoappuserservice.userservices;

import java.util.ArrayList;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.microservices.photoappuserservice.model.UserDto;
import com.microservices.photoappuserservice.model.UserEntity;
import com.microservices.photoappuserservice.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	UserRepository userRepo;
	BCryptPasswordEncoder encoder;

	@Autowired
	public UserServiceImpl(@Lazy UserRepository userRepository, @Lazy BCryptPasswordEncoder encoder) {
		this.userRepo = userRepository;
		this.encoder = encoder;
	}

	@Override
	public UserDto createUser(UserDto userDto)
			throws com.microservices.photoappuserservice.exceptions.BadRequestException {

		checkUserEmail(userDto.getEmail());

		userDto.setUserID(UUID.randomUUID().toString());
		userDto.setEncryptedPassword(encoder.encode(userDto.getPassword()));

		ModelMapper m = new ModelMapper();
		m.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

		UserEntity userEntity = m.map(userDto, UserEntity.class);
		userRepo.save(userEntity);
		UserDto dto = m.map(userEntity, UserDto.class);

		return dto;
	}

	private void checkUserEmail(String email)
			throws com.microservices.photoappuserservice.exceptions.BadRequestException {

		UserEntity entity = userRepo.findByEmail(email);
		System.out.println("UserServiceImpl.checkUserEmail()");
		System.err.println(entity);

		if (entity != null) {
			throw new com.microservices.photoappuserservice.exceptions.BadRequestException("Email id Found");
		} else {
			return;
		}
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserEntity entity = userRepo.findByEmail(username);
		if (entity == null)
			throw new UsernameNotFoundException(username);

		return new org.springframework.security.core.userdetails.User(entity.getEmail(), entity.getEncryptedPassword(),
				true, true, true, true, new ArrayList<>());
	}

	@Override
	public UserDto getUserByEmail(String email) {

		UserEntity entity = userRepo.findByEmail(email);
		if (entity == null)
			throw new UsernameNotFoundException(email);

		return new ModelMapper().map(entity, UserDto.class);
	}

}
