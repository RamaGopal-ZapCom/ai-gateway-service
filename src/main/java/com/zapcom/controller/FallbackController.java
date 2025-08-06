package com.zapcom.controller;

import com.zapcom.model.response.GatewayServiceErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/fallback")
public class FallbackController {

	// ... (other fallback methods)

	@GetMapping("/auth")
	public Mono<ResponseEntity<GatewayServiceErrorResponse>> authFallback() {
		GatewayServiceErrorResponse response = new GatewayServiceErrorResponse(
				"Authentication Service is temporarily unavailable. Please try again later."
		);
		return Mono.just(ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(response));
	}


	@GetMapping("/customer")
	public Mono<ResponseEntity<GatewayServiceErrorResponse>> customerFallback() {
		GatewayServiceErrorResponse response = new GatewayServiceErrorResponse(
				"Customer Service is temporarily unavailable. Please try again later." // Corrected message
		);
		return Mono.just(ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(response));
	}
}