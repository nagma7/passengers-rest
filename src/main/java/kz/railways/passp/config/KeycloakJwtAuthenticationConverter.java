package kz.railways.passp.config;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;

public class KeycloakJwtAuthenticationConverter implements Converter<Jwt, AbstractAuthenticationToken> {
	  @Override
	  public AbstractAuthenticationToken convert(Jwt jwt) {
		  Collection<GrantedAuthority> authorities = extractResourceRoles(jwt);
	      return new JwtAuthenticationToken(jwt, authorities);
	  }

	  private Collection<GrantedAuthority> extractResourceRoles(Jwt jwt) {
		Map<String, Object> realmAccess = jwt.getClaimAsMap("realm_access");
        List<String> roles = (List<String>) realmAccess.getOrDefault("roles", Collections.emptyList());

        // You can also extract client-specific roles if needed
        // Map<String, Object> resourceAccess = jwt.getClaimAsMap("resource_access");
        // Map<String, Object> clientRoles = (Map<String, Object>) resourceAccess.getOrDefault("your-client-id", Collections.emptyMap());
        // List<String> clientRolesList = (List<String>) clientRoles.getOrDefault("roles", Collections.emptyList());

        return roles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.toUpperCase())) // Prefix with ROLE_ for Spring Security
                .collect(Collectors.toList());
	  }

}
