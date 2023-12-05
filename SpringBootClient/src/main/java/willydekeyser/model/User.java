package willydekeyser.model;

import java.util.List;

import jakarta.validation.constraints.NotBlank;

public record User(
		@NotBlank(message = "Username can not be Empty!")
		String username,
		@NotBlank(message = "Password can not be Empty!")
		String password, 
		Boolean enabled,
		List<String> authority) {

}
