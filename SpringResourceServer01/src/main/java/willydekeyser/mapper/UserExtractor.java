package willydekeyser.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import willydekeyser.model.User;

public class UserExtractor implements ResultSetExtractor<List<User>>{

	@Override
	public List<User> extractData(ResultSet rs) throws SQLException, DataAccessException {
		Map<String, User> users = new LinkedHashMap<>();
		while (rs.next()) {
			String username = rs.getString("username");
			User user = users.get(username);
			if (user == null) {
				user = new User(username, 
						rs.getString("password"), 
						rs.getBoolean("enabled"), 
						new ArrayList<>());
				users.put(username, user);
			}
			user.authority().add(rs.getString("authority"));
		}
		return new ArrayList<>(users.values());
	}

}
