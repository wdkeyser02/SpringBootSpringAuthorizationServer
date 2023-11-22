package willydekeyser.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
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
	public List<User> findAllUsers() {
		return userService.findAllUsers();
	}
	
	@GetMapping("/user/{username}")
	public User findUserByUsername(@PathVariable String username) {
		return userService.findUserByUsername(username);
	}
	
	@PostMapping("/user")
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody User user) {
        userService.createUser(user);
    }
	
	@PutMapping("/user")
    public void updateUser(@RequestBody User user) {
        userService.updateUser(user);
    }
	
	@DeleteMapping("/user/{username}")
    public void deleteUser(@PathVariable String username) {
        userService.deleteUser(username);
    }
}
