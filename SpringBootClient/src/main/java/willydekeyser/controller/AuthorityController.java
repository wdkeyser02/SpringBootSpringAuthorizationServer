package willydekeyser.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import willydekeyser.model.Authority;
import willydekeyser.service.AuthorityService;

@RestController
public class AuthorityController {

private final AuthorityService authorityService;
	
	public AuthorityController(AuthorityService authorityService) {
		this.authorityService = authorityService;
	}

	@GetMapping("/authority")
	public List<Authority> getAuthorities() {
		return authorityService.getAuthorities();
	}
}
