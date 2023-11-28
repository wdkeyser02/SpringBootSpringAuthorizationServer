package willydekeyser;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.reactive.ReactiveOAuth2ClientAutoConfiguration;

@SpringBootApplication(exclude = ReactiveOAuth2ClientAutoConfiguration.class)
public class SpringBootClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootClientApplication.class, args);
	}

}
