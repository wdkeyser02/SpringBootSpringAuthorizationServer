package willydekeyser.client;

import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.DeleteExchange;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;
import org.springframework.web.service.annotation.PutExchange;

import willydekeyser.model.Authority;
import willydekeyser.model.Role;
import willydekeyser.model.User;

@HttpExchange
public interface UserClient {

	@GetExchange("/user")
    List<User> getUsers();
	
	@GetExchange("/user/{username}")
    User getUserByUsername(@PathVariable String username);
	
	@PostExchange("/user")
    Integer createUser(@RequestBody User user);
	
	@PutExchange("/user")
	Integer updateUser(@RequestBody User user);
	
	@DeleteExchange("/user/{username}")
	Integer deleteuser(@PathVariable String username);
	
	@GetExchange("/authority")
    List<Authority> getAuthorities();
	
	@GetExchange("/role")
    List<Role> getRoles();
}
