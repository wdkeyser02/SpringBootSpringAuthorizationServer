package willydekeyser.config;

import java.io.IOException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.registration.ClientRegistration;

public class OAuthRestClientInterceptor implements ClientHttpRequestInterceptor {
	
	private final OAuth2AuthorizedClientManager authorizedClientManager;
	private final ClientRegistration clientRegistration;
	
	public OAuthRestClientInterceptor(OAuth2AuthorizedClientManager authorizedClientManager, ClientRegistration clientRegistration) {
		this.authorizedClientManager = authorizedClientManager;
		this.clientRegistration = clientRegistration;
	}

	@Override
	public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
			throws IOException {
		SecurityContext context = SecurityContextHolder.getContext();
		Authentication authentication = context.getAuthentication();	
		
		OAuth2AuthorizeRequest oAuth2AuthorizeRequest = OAuth2AuthorizeRequest
				.withClientRegistrationId(clientRegistration.getRegistrationId())
				.principal(authentication)
				.build();
		OAuth2AuthorizedClient client = authorizedClientManager.authorize(oAuth2AuthorizeRequest);
		if (client == null) {
			throw new IllegalStateException("client credentials flow on " + clientRegistration.getRegistrationId() + " failed, client is null");
		}
		
		request.getHeaders().add(HttpHeaders.AUTHORIZATION, "Bearer " + client.getAccessToken().getTokenValue());
		return execution.execute(request, body);
	}

}
