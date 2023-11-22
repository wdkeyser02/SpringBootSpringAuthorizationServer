package willydekeyser.model;

import java.util.List;

public record User_Authority(
		String username, 
		String password, 
		Boolean enabled, 
		String test, 
		List<String> authority) {

}
