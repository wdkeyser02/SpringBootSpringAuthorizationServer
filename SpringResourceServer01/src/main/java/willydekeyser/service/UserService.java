package willydekeyser.service;

import java.util.List;

import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import willydekeyser.mapper.UserByUsernameExtractor;
import willydekeyser.mapper.UserExtractor;
import willydekeyser.model.User;

@Service
public class UserService {

	private static final String SQL_FIND_ALL_USERS = """
			SELECT users.username, users.password, users.enabled, authorities.authority 
			FROM users 
			LEFT JOIN authorities ON users.username = authorities.username ;
			""";
	private static final String SQL_FIND_USER_BY_USERNAME = """
			SELECT users.username, users.password, users.enabled, authorities.authority 
			FROM users 
			LEFT JOIN authorities ON users.username = authorities.username 
			WHERE users.username = ?;
			""";
	private static final String SQL_CREATE_USER = """
			INSERT INTO users(username, password, enabled)
			VALUES(?, ?, ?);
			""";
	private static final String SQL_CREATE_AUTHORITY = """
			INSERT INTO authorities(username, authority)
			VALUES(?, ?);
			""";
	private static final String SQL_DELETE_USER = """
			DELETE FROM users WHERE users.username = ?			
			""";
	private static final String SQL_DELETE_AUTHORITY = """
			DELETE FROM authorities WHERE authorities.username = ?
			""";
	private static final String SQL_EXISTS_USERNAME = """
			SELECT count(*) FROM users WHERE users.username = ?;
			""";
	
	private final JdbcClient jdbcClient;
	private final PasswordEncoder passwordEncoder;
	
	public UserService(JdbcClient jdbcClient, PasswordEncoder passwordEncoder) {
		this.jdbcClient = jdbcClient;
		this.passwordEncoder = passwordEncoder;
	}

	public List<User> findAllUsers() {
		return jdbcClient.sql(SQL_FIND_ALL_USERS)
				.query(new UserExtractor());
	}
	
	public User findUserByUsername(String username) {
		return jdbcClient.sql(SQL_FIND_USER_BY_USERNAME)
				.param(username)
				.query(new UserByUsernameExtractor());
	}
	
	public Integer createUser(User user) {
		if(jdbcClient.sql(SQL_EXISTS_USERNAME)
				.param(user.username())
				.query(Integer.class).single() == 0) {
			Integer rows = jdbcClient.sql(SQL_CREATE_USER)
					.params(user.username(), "{bcrypt}" + passwordEncoder.encode(user.password()), user.enabled())
					.update();
				if (rows == 1 || !user.authority().isEmpty()) {
					user.authority().forEach((row) -> {
						jdbcClient.sql(SQL_CREATE_AUTHORITY)
							.params(user.username(), row)
							.update();
					});
				}
				return rows;
		}
		return 0;
	}
	
	public Integer updateUser(User user) {
		Integer row = deleteUser(user.username());
		if (row == 1) {
			return createUser(user);
		}
		return 0;
	}
	
	public Integer deleteUser(String username) {
		if(jdbcClient.sql(SQL_EXISTS_USERNAME)
				.param(username)
				.query(Integer.class).single() != 0) {
			jdbcClient.sql(SQL_DELETE_AUTHORITY)
				.param(username)
				.update();
			Integer rows = jdbcClient.sql(SQL_DELETE_USER)
			.param(username)
			.update();
			return rows;
			
		}
		return 0;
	}
}
