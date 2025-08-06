package com.zapcom.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GatewayServiceException extends RuntimeException {
	private int statusCode;

	public GatewayServiceException(String message) {
		super(message);
		this.statusCode = 500;
	}

	public GatewayServiceException(String message, int statusCode) {
		super(message);
		this.statusCode = statusCode;
	}
}