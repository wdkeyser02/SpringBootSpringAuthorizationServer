package willydekeyser.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

	@GetMapping("/")
	public String root() {
		return "redirect:/index";
	}
	
	@GetMapping("/index")
	public String home() {
		return "index";
	}
	
	@GetMapping("/logged-out")
	public String loggedOut() {
		return "index";
	}

}
