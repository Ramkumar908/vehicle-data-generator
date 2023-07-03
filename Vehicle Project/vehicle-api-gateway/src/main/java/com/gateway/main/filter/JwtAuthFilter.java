package com.gateway.main.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import com.gateway.main.util.JwtUtil;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class JwtAuthFilter extends AbstractGatewayFilterFactory<JwtAuthFilter.Config> {

	@Autowired
	private RoutValidator validator;

	@Autowired
	private JwtUtil jwtUtil;

	public JwtAuthFilter() {
		super(Config.class);
	}

	@Override
	public GatewayFilter apply(Config config) throws RuntimeException {
		return ((exchange, chain) -> {
			if (validator.isSecured.test(exchange.getRequest())) {
				if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
					throw new RuntimeException("Missing Headers");
				}
				String reqHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
				if (reqHeader != null && reqHeader.startsWith("Bearer ")) {
					reqHeader = reqHeader.substring(7);
				}
				try {
					jwtUtil.validateToken(reqHeader);
				} catch (Exception e) {
					log.error("Unauthorized access to application(Invalid Token)");
					throw new RuntimeException("Unauthorized access to application(Invalid Token)");
				}
			}
			return chain.filter(exchange);
		});
	}

	public static class Config {
	}

}
