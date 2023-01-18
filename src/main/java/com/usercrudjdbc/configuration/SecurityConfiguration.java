package com.usercrudjdbc.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration implements WebSecurityConfigurer<WebSecurity> {

    @Autowired
    private ClientRegistrationRepository clientRegistrationRepository;

    @Autowired
    private OAuth2AuthorizedClientService authorizedClientService;

    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests(authorizeRequests ->
                authorizeRequests
                    .anyRequest().authenticated()
            )
            .oauth2Login(oauth2Login ->
                oauth2Login
                    .authorizationEndpoint(authorizationEndpoint ->
                        authorizationEndpoint
                            .authorizationRequestRepository(customAuthorizationRequestRepository())
                    )
                    .clientRegistrationRepository(clientRegistrationRepository)
                    .authorizedClientService(authorizedClientService)
            );
    }

	@Override
	public void init(WebSecurity builder) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void configure(WebSecurity builder) throws Exception {
		// TODO Auto-generated method stub
		
	}
    
    // other methods like authentication manager, password encoder, etc.
}
