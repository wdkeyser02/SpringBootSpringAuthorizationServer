package willydekeyser.service;

import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;

import willydekeyser.mappers.AuthoritiesResultExtractor;
import willydekeyser.mappers.UserResultExtractor;
import willydekeyser.mappers.UserRowMapper;
import willydekeyser.mappers.UserTestRowMapper;
import willydekeyser.model.Authorities;
import willydekeyser.model.User;
import willydekeyser.model.UserTest;
import willydekeyser.model.User_Authority;

@Service
public class UserService {

	private static final String SQL_FIND_ALL_USERS = """
			SELECT users.username, users.password, users.enabled FROM users;
			""";
	
	private static final String SQL_FIND_ALL_USERS_TEST = """
			SELECT users.username, users.password, users.enabled, users_custom_info.test 
			FROM users_custom_info, users 
			WHERE users.username = users_custom_info.username;
			""";
	
	private static final String SQL_FIND_ALL_USERS_Authorities = """
			SELECT users.username, users.password, users.enabled, authorities.authority, users_custom_info.test
			FROM users_custom_info, users
			LEFT JOIN authorities ON users.username = authorities.username 
			WHERE users.username = users_custom_info.username;
			""";
	
	private static final String SQL_FIND_ALL_AUTHORITIES = """
			SELECT authorities.authority, users.username, users.password, users.enabled, users_custom_info.test
			FROM users_custom_info, authorities
			LEFT JOIN users  ON users.username = authorities.username 
			WHERE users.username = users_custom_info.username;
			""";
	
	private static final String SQL_FIND_BY_USERNAME = """
			SELECT users.username, users.password, users.enabled
			FROM users
			WHERE users.username = :username;
			""";
	
	private static final String SQL_CREATE_USER = """
			INSERT INTO users(username, password, enabled)
			VALUES(?, ?, ?);
			""";
	private static final String SQL_UPDATE_USER = """
			UPDATE users SET password = ?, enabled = ? WHERE username = ?;
			""";
	
	private static final String SQL_DELETE_USER = """
			DELETE FROM users WHERE username = ?;
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
	
	public List<User> findAllUsersRowMapper() {
		return jdbcClient.sql(SQL_FIND_ALL_USERS)
				.query(new UserRowMapper())
				.list();
	}
	
	public List<UserTest> findAllUsersTest() {
		return jdbcClient.sql(SQL_FIND_ALL_USERS_TEST)
				.query(UserTest.class)
				.list();
	}
	
	public List<UserTest> findAllUsersTestRowMapper() {
		return jdbcClient.sql(SQL_FIND_ALL_USERS_TEST)
				.query(new UserTestRowMapper())
				.list();
	}
	
	public List<User_Authority> findAllUsersAuthorities() {
		return jdbcClient.sql(SQL_FIND_ALL_USERS_Authorities)
				.query(new UserResultExtractor());
	}
	
	public List<Authorities> findAllAuthoritiesUsers() {
		return jdbcClient.sql(SQL_FIND_ALL_AUTHORITIES)
				.query(new AuthoritiesResultExtractor());
	}
	
	public Optional<User> findByUsername(String username) {
		return jdbcClient.sql(SQL_FIND_BY_USERNAME)
				.param("username", username)
				.query(User.class)
				.optional();
	}
	
	public Long createUser(User user) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcClient.sql(SQL_CREATE_USER)
			.params(user.username(), user.password(), user.enabled())
			.update();
		return keyHolder.getKeyAs(Long.class);
	}
	
	public void updateUser(User user) {
		Integer updated = jdbcClient.sql(SQL_UPDATE_USER)
			.params(user.password(), user.enabled(), user.username())
			.update();
		if (updated == 0) {
			throw new RuntimeException("User not found");
			
		}
	}
	
	public void deleteUser(String username) {
		Integer updated = jdbcClient.sql(SQL_DELETE_USER)
			.params(username)
			.update();
		if (updated == 0) {
			throw new RuntimeException("User not found");
			
		}
	}
}
