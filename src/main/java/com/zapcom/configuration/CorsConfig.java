package com.zapcom.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

@Configuration
public class CorsConfig {

	private final String localOriginAuth = System.getenv("ALLOWED_ORIGIN_LOCAL_AUTH");
	private final String localOriginCustomer = System.getenv("ALLOWED_ORIGIN_LOCAL_CUSTOMER");
	private final String localOriginFrontend1 = System.getenv("ALLOWED_ORIGIN_LOCAL_FRONTEND_1");
	private final String localOriginFrontend2 = System.getenv("ALLOWED_ORIGIN_LOCAL_FRONTEND_2");
	private final String loopbackOrigin = System.getenv("ALLOWED_ORIGIN_LOOPBACK");
	private final String authOrigin = System.getenv("ALLOWED_ORIGIN_AUTH");
	private final String customerOrigin = System.getenv("ALLOWED_ORIGIN_CUSTOMER");
	private final String prodOriginAuth = System.getenv("ALLOWED_ORIGIN_PROD_AUTH");
	private final String prodOriginCustomer = System.getenv("ALLOWED_ORIGIN_PROD_CUSTOMER");
	private final String prodOriginFrontend1 = System.getenv("ALLOWED_ORIGIN_PROD_FRONTEND_1");
	private final String prodOriginFrontend2 = System.getenv("ALLOWED_ORIGIN_PROD_FRONTEND_2");

	@Bean
	public CorsWebFilter corsWebFilter() {
		CorsConfiguration config = new CorsConfiguration();

		addIfNotNull(config, localOriginAuth);
		addIfNotNull(config, localOriginCustomer);
		addIfNotNull(config, localOriginFrontend1);
		addIfNotNull(config, localOriginFrontend2);
		addIfNotNull(config, loopbackOrigin);
		addIfNotNull(config, authOrigin);
		addIfNotNull(config, customerOrigin);
		addIfNotNull(config, prodOriginAuth);
		addIfNotNull(config, prodOriginCustomer);
		addIfNotNull(config, prodOriginFrontend1);
		addIfNotNull(config, prodOriginFrontend2);

		config.addAllowedMethod("GET");
		config.addAllowedMethod("POST");
		config.addAllowedMethod("PUT");
		config.addAllowedMethod("DELETE");
		config.addAllowedHeader("*");
		config.setAllowCredentials(true);
		config.setMaxAge(3600L);

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", config);
		return new CorsWebFilter(source);
	}

	private void addIfNotNull(CorsConfiguration config, String origin) {
		if (origin != null && !origin.isBlank()) {
			config.addAllowedOriginPattern(origin);
		}
	}
}
