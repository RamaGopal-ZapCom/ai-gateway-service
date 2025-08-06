package com.zapcom.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@Order(-2)
public class CustomGlobalErrorHandler implements ErrorWebExceptionHandler {

	private final ObjectMapper objectMapper = new ObjectMapper();

	@Override
	public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
		exchange.getResponse().setStatusCode(HttpStatus.SERVICE_UNAVAILABLE); // or INTERNAL_SERVER_ERROR
		exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);

		Map<String, String> errorResponse = new HashMap<>();
		errorResponse.put("message", "Something went wrong. Please try again later.");

		DataBufferFactory bufferFactory = exchange.getResponse().bufferFactory();
		try {
			byte[] bytes = objectMapper.writeValueAsBytes(errorResponse);
			DataBuffer buffer = bufferFactory.wrap(bytes);
			return exchange.getResponse().writeWith(Mono.just(buffer));
		} catch (Exception e) {
			byte[] fallback = "{\"message\":\"Unexpected error\"}".getBytes(StandardCharsets.UTF_8);
			DataBuffer buffer = exchange.getResponse().bufferFactory().wrap(fallback);
			return exchange.getResponse().writeWith(Mono.just(buffer));
		}
	}
}
