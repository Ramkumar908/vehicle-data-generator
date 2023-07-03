package com.gateway.main.filter;

import java.util.List;
import java.util.function.Predicate;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

@Component
public class RoutValidator {

	public static final List<String> openApiEndpoints = List.of("/api/auth/token", "/api/auth/validate-token",
			"/eureka", "/h2-console");

	public Predicate<ServerHttpRequest> isSecured = request -> openApiEndpoints.stream()
			.noneMatch(uri -> request.getURI().getPath().contains(uri));

}
