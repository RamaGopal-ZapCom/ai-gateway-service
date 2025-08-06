package com.zapcom.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GatewayServiceErrorResponse {
	private String message;

	public GatewayServiceErrorResponse(int i, String s, long l) {
	}
}
