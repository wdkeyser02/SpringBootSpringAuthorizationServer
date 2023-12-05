package willydekeyser.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import willydekeyser.model.User;

public class UserByUsernameExtractor implements ResultSetExtractor<User> {

	@Override
	public User extractData(ResultSet rs) throws SQLException, DataAccessException {
		User user = null;
		while (rs.next()) {
			String username = rs.getString("username");
			if (rs.isFirst()) {
				user = new User(username, 
						rs.getString("password"), 
						rs.getBoolean("enabled"), 
						new ArrayList<>());
			}
			user.authority().add(rs.getString("authority"));
		}
		if (user != null) {
			return user;
		}
		return null;
	}

}
