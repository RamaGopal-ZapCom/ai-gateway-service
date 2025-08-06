package com.zapcom.configuration;

import java.util.Map;
import org.springframework.boot.web.reactive.error.DefaultErrorAttributes;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.ServerRequest;

@Configuration
public class ErrorConfig {

	@Bean
	public DefaultErrorAttributes errorAttributes() {
		return new DefaultErrorAttributes(){
			public Map<String, Object> getErrorAttributes(ServerRequest request, boolean includeStackTrace) {
				return Map.of(); // empty error response map
			}
		};
	}
}
