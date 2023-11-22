package willydekeyser.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import willydekeyser.model.User_Authority;

public class UserResultExtractor implements ResultSetExtractor<List<User_Authority>> {

	@Override
	public List<User_Authority> extractData(ResultSet rs) throws SQLException, DataAccessException {
		Map<String, User_Authority> users = new LinkedHashMap<>();
		while (rs.next()) {
			String username = rs.getString("username");
			User_Authority user = users.get(username);
			if (user == null) {
				user = new User_Authority(username, 
						rs.getString("password"), 
						rs.getBoolean("enabled"), 
						rs.getString("test"), 
						new ArrayList<>());
				users.put(username, user);
			}
			user.authority().add(rs.getString("authority"));
		}
		return new ArrayList<>(users.values());
	}
}