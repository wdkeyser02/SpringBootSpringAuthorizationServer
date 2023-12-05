package willydekeyser.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import willydekeyser.client.UserClient;

@Service
public class RoleService {

private final UserClient userClient;
	
	public RoleService(UserClient userClient) {
		this.userClient = userClient;
	}

	public List<String> getRoles() {
		List<String> roles = new ArrayList<>();
		this.userClient.getRoles().forEach(role -> roles.add(role.role()));
		return roles;
	}
}
