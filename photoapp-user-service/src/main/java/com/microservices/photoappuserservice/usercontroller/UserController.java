package com.microservices.photoappuserservice.usercontroller;

import org.apache.coyote.BadRequestException;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.microservices.photoappuserservice.model.UserDto;
import com.microservices.photoappuserservice.model.UserModelRequest;
import com.microservices.photoappuserservice.model.UserResponseModel;
import com.microservices.photoappuserservice.userservices.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	private Environment env;

	@Autowired
	private UserService userService;

	@GetMapping("/status")
	public String status() {
		return "Working on port for LB Check is" + " " + env.getProperty("local.server.port");
	}

	@PostMapping("/save")
	public ResponseEntity<UserResponseModel> createUser(@RequestBody UserModelRequest userModelRequest) throws BadRequestException {
		ModelMapper mapper = new ModelMapper();
		mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

		UserDto userDto = mapper.map(userModelRequest, UserDto.class);

		userService.createUser(userDto);

		UserResponseModel responseModel = mapper.map(userDto, UserResponseModel.class);
		responseModel.setStatus(HttpStatus.CREATED);
		responseModel.setCode("201");

		return new ResponseEntity<UserResponseModel>(responseModel, HttpStatus.CREATED);
	}

}
