package willydekeyser.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import willydekeyser.model.Authorities;
import willydekeyser.model.UserTest;

public class AuthoritiesResultExtractor implements ResultSetExtractor<List<Authorities>> {

	@Override
	public List<Authorities> extractData(ResultSet rs) throws SQLException, DataAccessException {
		Map<String, Authorities> authorities = new LinkedHashMap<>();
		while (rs.next()) {
			String username = rs.getString("authority");
			Authorities authority = authorities.get(username);
			if (authority == null) {
				authority = new Authorities(username, 
						new ArrayList<>());
				authorities.put(username, authority);
			}
			authority.users().add(new UserTest(rs.getString("username"), rs.getString("password"), rs.getBoolean("enabled"), rs.getString("test")));
		}
		return new ArrayList<>(authorities.values());
	}

}
