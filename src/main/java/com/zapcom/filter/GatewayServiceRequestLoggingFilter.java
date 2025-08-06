package com.zapcom.filter;

import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class GatewayServiceRequestLoggingFilter implements GatewayFilter, Ordered {

	private static final Logger logger = LoggerFactory.getLogger(GatewayServiceRequestLoggingFilter.class);

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		ServerHttpRequest request = exchange.getRequest();

		String trackingId = UUID.randomUUID().toString();

		ServerHttpRequest modifiedRequest = request.mutate()
				.header("X-Tracking-Id", trackingId)
				.build();

		logger.info("Request: [{}] {} {}, client: {}, headers: {}",
				trackingId,
				request.getMethod(),
				request.getURI(),
				request.getRemoteAddress(),
				request.getHeaders());

		long startTime = System.currentTimeMillis();

		return chain.filter(exchange.mutate().request(modifiedRequest).build())
				.doFinally(signalType -> {
					long duration = System.currentTimeMillis() - startTime;
					logger.info("Response: [{}] completed in {} ms", trackingId, duration);
				});
	}

	@Override
	public int getOrder() {
		return -100;
	}
}
