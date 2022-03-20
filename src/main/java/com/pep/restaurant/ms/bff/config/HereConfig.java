package com.pep.restaurant.ms.bff.config;

import com.here.account.auth.OAuth1ClientCredentialsProvider;
import com.here.account.oauth2.ClientAuthorizationRequestProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HereConfig {

    @Autowired
    private ApplicationProperties applicationProperties;

    @Bean
    public ClientAuthorizationRequestProvider getHereClientAuthorizationCredentials() {
        return new OAuth1ClientCredentialsProvider(
                applicationProperties.getHere().getTokenEndpointUrl(),
                applicationProperties.getHere().getAccessKeyId(),
                applicationProperties.getHere().getAccessKeySecret());
    }

}
