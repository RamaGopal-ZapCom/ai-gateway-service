package com.zapcom.configuration;

import java.util.Objects;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import reactor.core.publisher.Mono;


@Configuration
@Getter
public class RateLimiterConfiguration {

	@Value("${RATE_LIMITER_REPLENISH_RATE:10}")
	private int replenishRate;

	@Value("${RATE_LIMITER_BURST_CAPACITY:20}")
	private int burstCapacity;

	@Value("${RATE_LIMITER_REQUESTED_TOKENS:1}")
	private int requestedTokens;

	@Bean
	@Primary
	public KeyResolver ipKeyResolver() {
		return exchange -> Mono.justOrEmpty(
				Objects.requireNonNull(exchange.getRequest()
								.getRemoteAddress())
						.getAddress().getHostAddress()
		);
	}

	@Bean
	public KeyResolver userKeyResolver() {
		return exchange -> Mono.justOrEmpty(
				exchange.getRequest().getHeaders().getFirst("X-User-Id")
		);
	}
}
