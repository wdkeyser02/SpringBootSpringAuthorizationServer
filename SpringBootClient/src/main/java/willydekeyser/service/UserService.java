package willydekeyser.service;

import java.util.List;

import org.springframework.stereotype.Service;

import willydekeyser.client.UserClient;
import willydekeyser.model.User;

@Service
public class UserService {

	private final UserClient userClient;
	
	public UserService(UserClient userClient) {
		this.userClient = userClient;
	}

	public List<User> getUsers() {
		return this.userClient.getUsers();
	}
	
	public Integer createUser(User user) {
		return this.userClient.createUser(user);
	}
}
