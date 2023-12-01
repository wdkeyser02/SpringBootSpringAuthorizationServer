package willydekeyser.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
		List<User> users = userService.getUsers();
		model.addAttribute("users", users);
		return "user";
	}
	
	@GetMapping("/userform")
	public String userForm(Model model, @RequestParam(defaultValue = "false") Boolean error) {
		model.addAttribute("user", new User("", "",false, new ArrayList<>()));
		List<String> roles = new ArrayList<String>();
		roles.add("ROLE_USER");
		roles.add("ROLE_ADMIN");
	    roles.add("ROLE_DEVELOPER");
	    model.addAttribute("allRoles", roles);
	    model.addAttribute("error", error);
		return "userform";
	}
	
	@PostMapping("/user")
	public String createUser(@ModelAttribute User user) {
		Integer row = userService.createUser(user);
		if (row == 0) {
			return "redirect:userform?error=true";
		}
		return "redirect:user";
	}
}
