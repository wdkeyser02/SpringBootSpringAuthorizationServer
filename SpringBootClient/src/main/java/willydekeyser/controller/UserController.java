package willydekeyser.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import willydekeyser.model.User;
import willydekeyser.service.UserService;

@Controller
public class UserController {

	private final UserService userService;
	
	public UserController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping("/user")
	public String getUsers(Model model) {
		List<User> user = userService.getUsers();
		model.addAttribute("users", user);
		return "user";
	}
}
