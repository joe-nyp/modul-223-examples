package ch.nyp.dbmanytomany.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import ch.nyp.dbmanytomany.dto.UserDto;
import ch.nyp.dbmanytomany.dtoconverter.UserDtoConverter;
import ch.nyp.dbmanytomany.model.User;
import ch.nyp.dbmanytomany.repository.UserRepository;

@Controller   
@RequestMapping(path="/mnDatabaseDemo") 
public class UserController {
	
	@Autowired // This means to get the bean called userRepository
    // Which is auto-generated by Spring, we will use it to handle the data
	private UserRepository userRepository;
	

	@Autowired
	private UserDtoConverter userDtoConverter;
	
	@PostMapping(path="user/add") 
	public @ResponseBody String addNewUser(@RequestBody UserDto userDto) {
		User dbUser = userDtoConverter.convertToEntity(userDto);
		userRepository.save(dbUser);
		return "User saved";
	}
	
	@GetMapping(path="user/all")
	public @ResponseBody List<UserDto> getAllUsers() {
		// This returns a JSON or XML with the users
		List<User> usersFromDB = userRepository.findAll();
		
		List<UserDto> users = usersFromDB.stream()
		          .map(user -> userDtoConverter.convertToDtoRead(user))
		          .collect(Collectors.toList());
		
		return users;
	}
}
