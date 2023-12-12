package willydekeyser.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class CleanupDatabase {

	@Value("${clean.database.days}")
	Integer days;
	
	private static final String SQL_DELETE_OAUTH2_AUTHORIZATION = """
			DELETE FROM oauth2_authorization WHERE (authorization_code_expires_at < NOW() + INTERVAL :days DAY OR authorization_code_expires_at IS NULL)
			AND (access_token_expires_at < NOW() - INTERVAL :days DAY OR access_token_expires_at IS NULL)
			AND (oidc_id_token_expires_at < NOW() - INTERVAL :days DAY OR oidc_id_token_expires_at IS NULL)
			AND (refresh_token_expires_at < NOW() - INTERVAL :days DAY OR refresh_token_expires_at IS NULL)
			AND (device_code_expires_at < NOW() - INTERVAL :days DAY OR device_code_expires_at IS NULL)
			AND (user_code_expires_at < NOW() - INTERVAL :days DAY OR user_code_expires_at IS NULL);			
			""";
	private static final String SQL_COUNT_OAUTH2_AUTHORIZATION = """
			SELECT COUNT(*) FROM oauth2_authorization;
			""";
	private final JdbcClient jdbcClient;
	
	public CleanupDatabase(JdbcClient jdbcClient) {
		this.jdbcClient = jdbcClient;
	}

	@Scheduled(cron="${clean.database.cron.expression}")
	public void cleanupExpiredTokens() {
		System.err.println("CleanUp: " + LocalDateTime.now());
		jdbcClient.sql(SQL_DELETE_OAUTH2_AUTHORIZATION)
			.param("days", days)
			.update();
		System.err.println(jdbcClient.sql(SQL_COUNT_OAUTH2_AUTHORIZATION).query(Integer.class).single());
	}
}
