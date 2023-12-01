package willydekeyser.client;

import java.util.List;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;

import willydekeyser.model.Authority;
import willydekeyser.model.User;

@HttpExchange
public interface UserClient {

	@GetExchange("/user")
    List<User> getUsers();
	
	@PostExchange("/user")
    Integer createUser(@RequestBody User user);
	
	@GetExchange("/authority")
    List<Authority> getAuthorities();
}
