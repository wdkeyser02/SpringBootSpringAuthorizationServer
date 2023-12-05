package willydekeyser.service;

import java.util.List;

import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Service;

import willydekeyser.model.Role;

@Service
public class RoleService {

	private static final String SQL_FIND_ALL_ROLES = """
			SELECT roles.id, roles.role 
			FROM roles ;
			""";
	
	private final JdbcClient jdbcClient;
	
	public RoleService(JdbcClient jdbcClient) {
		this.jdbcClient = jdbcClient;
	}
	
	public List<Role> findAllRoles() {
		return jdbcClient.sql(SQL_FIND_ALL_ROLES)
				.query(Role.class)
				.list();
	}
}
