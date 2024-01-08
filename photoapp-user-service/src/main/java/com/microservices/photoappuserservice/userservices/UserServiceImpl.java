package com.microservices.photoappuserservice.userservices;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.microservices.photoappuserservice.model.UserDto;
import com.microservices.photoappuserservice.model.UserEntity;
import com.microservices.photoappuserservice.repository.AlbumServiceClient;
import com.microservices.photoappuserservice.repository.UserRepository;
import com.microservices.photoappuserservice.ui.model.AlbumResponseModel;

import feign.FeignException;

@Service
public class UserServiceImpl implements UserService {

	UserRepository userRepo;
	BCryptPasswordEncoder encoder;
//	RestTemplate restTemplate;
	AlbumServiceClient albumClient;

	@Autowired
	public UserServiceImpl(@Lazy UserRepository userRepository, @Lazy BCryptPasswordEncoder encoder,
			AlbumServiceClient albumClient) {
		this.userRepo = userRepository;
		this.encoder = encoder;
//		this.restTemplate = restTemplate;
		this.albumClient = albumClient;
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

	@Override
	public UserDto getUserByUserId(String userId) {
		UserEntity entity = userRepo.findByUserId(userId);
		if (entity == null) {
			throw new UsernameNotFoundException("User Not Found");
		}

		UserDto userDto = new ModelMapper().map(entity, UserDto.class);

		String albumUrl = "http://ALBUM-WS/users/" + userDto.getUserID() + "/albums";
//		ResponseEntity<List<AlbumResponseModel>> albumResponse = restTemplate.exchange(albumUrl, HttpMethod.GET, null,
//				new ParameterizedTypeReference<List<AlbumResponseModel>>() {
//				});
//		List<AlbumResponseModel> albumList = albumResponse.getBody();

		List<AlbumResponseModel> albumList = null;
		try {
			albumList = albumClient.getAlbums(userDto.getUserID());
		} catch (FeignException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		userDto.setAlbums(albumList);

		return userDto;
	}

}
