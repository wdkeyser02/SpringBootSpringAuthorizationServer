package willydekeyser.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientProvider;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientProviderBuilder;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServletOAuth2AuthorizedClientExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

import willydekeyser.client.UserClient;

@Configuration
public class WebClientConfig {
	
	@Bean
	UserClient userClient(OAuth2AuthorizedClientManager authorizedClientManager) {
		ServletOAuth2AuthorizedClientExchangeFilterFunction oauth2 = 
				new ServletOAuth2AuthorizedClientExchangeFilterFunction(authorizedClientManager);
		oauth2.setDefaultOAuth2AuthorizedClient(true);
		
		WebClient webClient = WebClient.builder()
				.baseUrl("http://127.0.0.1:8091")
				.apply(oauth2.oauth2Configuration())
				.build();		
		WebClientAdapter adapter = WebClientAdapter.create(webClient);
		HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(adapter).build();
		UserClient userClient = factory.createClient(UserClient.class);
		return userClient;
	}

    @Bean
    OAuth2AuthorizedClientManager authorizedClientManager(
            ClientRegistrationRepository clientRegistrationRepository,
            OAuth2AuthorizedClientRepository authorizedClientRepository) {

        OAuth2AuthorizedClientProvider authorizedClientProvider =
                OAuth2AuthorizedClientProviderBuilder.builder()
                	.clientCredentials()
                    .authorizationCode()
                    .refreshToken()
                    .build();

        DefaultOAuth2AuthorizedClientManager authorizedClientManager =
                new DefaultOAuth2AuthorizedClientManager(
                        clientRegistrationRepository, authorizedClientRepository);

        authorizedClientManager.setAuthorizedClientProvider(authorizedClientProvider);

        return authorizedClientManager;
    }
}
