package willydekeyser.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientProvider;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientProviderBuilder;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

import willydekeyser.client.UserClient;

@Configuration
public class WebClientConfig {
	
	@Value("${spring.security.oauth2.client.registration.client.client-id}")
	String client_id;
	@Value("${restclient-url}")
	String restclient_url;
		
	@Bean
	UserClient userClient(OAuth2AuthorizedClientManager authorizedClientManager, ClientRegistrationRepository clientRegistrationRepository) {
		
		ClientRegistration clientRegistration = clientRegistrationRepository.findByRegistrationId(client_id);
		ClientHttpRequestInterceptor oauthInterceptor = new OAuthRestClientInterceptor(authorizedClientManager, clientRegistration);
		RestClient restClient = RestClient.builder()
				.baseUrl(restclient_url)
				.requestInterceptor(oauthInterceptor)
				.build();
		
		RestClientAdapter adapter = RestClientAdapter.create(restClient);
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
