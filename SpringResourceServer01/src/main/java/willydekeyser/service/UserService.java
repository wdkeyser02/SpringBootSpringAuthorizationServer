package willydekeyser.service;

import java.util.List;

import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Service;

import willydekeyser.model.User;

@Service
public class UserService {

	private static final String SQL_FIND_ALL_USERS = """
			SELECT users.username, users.password, users.enabled FROM users;
			""";
	
	private final JdbcClient jdbcClient;
	
	public UserService(JdbcClient jdbcClient) {
		this.jdbcClient = jdbcClient;
	}

	public List<User> findAllUsers() {
		return jdbcClient.sql(SQL_FIND_ALL_USERS)
				.query(User.class)
				.list();
	}
}
