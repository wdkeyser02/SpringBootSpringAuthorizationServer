package willydekeyser.service;

import java.util.List;

import org.springframework.stereotype.Service;

import willydekeyser.client.UserClient;
import willydekeyser.model.Authority;

@Service
public class AuthorityService {

private final UserClient userClient;
	
	public AuthorityService(UserClient userClient) {
		this.userClient = userClient;
	}

	public List<Authority> getAuthorities() {
		return this.userClient.getAuthorities();
	}
}
