package com.zapcom.model.request;

import lombok.Getter;

@Getter
public class GatewayServiceRequest {
	private String username;
	private String password;

	public GatewayServiceRequest() {
	}

	public GatewayServiceRequest(String username, String password) {
		this.username = username;
		this.password = password;
	}
}
