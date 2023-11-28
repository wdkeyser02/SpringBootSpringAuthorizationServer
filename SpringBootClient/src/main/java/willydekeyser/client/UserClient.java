package willydekeyser.client;

import java.util.List;

import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

import willydekeyser.model.Authority;
import willydekeyser.model.User;

@HttpExchange
public interface UserClient {

	@GetExchange("/user")
    List<User> getUsers();
	
	@GetExchange("/authority")
    List<Authority> getAuthorities();
}
