package com.microservices.api.gateway;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;

@Component
public class CustomLoggingFilter implements GlobalFilter {

	Logger logger = LoggerFactory.getLogger(CustomLoggingFilter.class);

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		logger.info("[+] Intercepted Request Path: " + exchange.getRequest().getPath());

		return chain.filter(exchange);
	}

}
