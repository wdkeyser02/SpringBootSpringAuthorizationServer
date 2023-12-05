package willydekeyser.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
	
	@GetMapping("/user/{username}")
	public User getUserByUsername(@PathVariable String username) {
		return userService.findUserByUsername(username);
	}
	
	@PostMapping("/user")
	public Integer createUser(@RequestBody User user) {
		return userService.createUser(user);
	}
	
	@PutMapping("/user")
	public Integer updateUser(@RequestBody User user) {
		return userService.updateUser(user);
	}
	
	@DeleteMapping("/user/{username}")
	public Integer deleteUser(@PathVariable String username) {
		return userService.deleteUser(username);
	}
}
