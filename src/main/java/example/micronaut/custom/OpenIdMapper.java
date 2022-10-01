package example.micronaut.custom;
import java.util.List;
import java.util.Map;

import io.micronaut.context.annotation.Replaces;
import io.micronaut.security.authentication.AuthenticationResponse;
import io.micronaut.security.oauth2.endpoint.authorization.state.State;
import io.micronaut.security.oauth2.endpoint.token.response.DefaultOpenIdAuthenticationMapper;
import io.micronaut.security.oauth2.endpoint.token.response.OauthAuthenticationMapper;
import io.micronaut.security.oauth2.endpoint.token.response.OpenIdAuthenticationMapper;
import io.micronaut.security.oauth2.endpoint.token.response.OpenIdClaims;
import io.micronaut.security.oauth2.endpoint.token.response.OpenIdTokenResponse;
import jakarta.inject.Singleton;

@Singleton
@Replaces(DefaultOpenIdAuthenticationMapper.class)
public class OpenIdMapper implements OpenIdAuthenticationMapper {

    @Override
    public AuthenticationResponse createAuthenticationResponse(String providerName, OpenIdTokenResponse tokenResponse,
            OpenIdClaims openIdClaims, State state) {
        
        var roles = List.of("customRole1", "customRole2");
        var attributes = Map.of(
            OpenIdAuthenticationMapper.OPENID_TOKEN_KEY, tokenResponse.getIdToken(), 
            "customAttribute", (Object)"customAttributeValue",
            OauthAuthenticationMapper.ACCESS_TOKEN_KEY, tokenResponse.getAccessToken(),
            OauthAuthenticationMapper.REFRESH_TOKEN_KEY, tokenResponse.getRefreshToken()
            
            );


        return AuthenticationResponse.success(openIdClaims.getSubject(), roles, attributes);

    }
    
}
