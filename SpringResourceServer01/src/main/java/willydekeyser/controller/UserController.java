package willydekeyser.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import willydekeyser.model.User;
import willydekeyser.service.UserService;

@RestController
public class UserController {

	private final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}
	
	@GetMapping("/user")
	public List<User> getAllUsers() {
		return userService.findAllUsers();
	}
	
	@PostMapping("/user")
	public Integer createUser(@RequestBody User user) {
		return userService.createUser(user);
	}
}
