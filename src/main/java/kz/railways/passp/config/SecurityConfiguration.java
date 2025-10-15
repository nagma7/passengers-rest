package kz.railways.passp.config;

import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
public class SecurityConfiguration {

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http)throws Exception{
		http
		 .csrf(csrf -> csrf.disable())
		 .cors(cors -> cors.configurationSource(request -> {
				CorsConfiguration conf = new CorsConfiguration();
				conf.setAllowedOrigins(Arrays.asList("*"));
		        conf.setAllowedMethods(Arrays.asList("*"));
		        conf.setAllowedHeaders(Arrays.asList("*"));
		        return conf; 
			 }))
		 .authorizeHttpRequests(auth -> auth
				 .requestMatchers("/public/**")
				 .permitAll()
				 .anyRequest().authenticated())
		 .oauth2ResourceServer(oauth2 -> oauth2
				 .jwt(jwt -> jwt 
				 .jwtAuthenticationConverter(new KeycloakJwtAuthenticationConverter())));
		return http.build();
	}
}