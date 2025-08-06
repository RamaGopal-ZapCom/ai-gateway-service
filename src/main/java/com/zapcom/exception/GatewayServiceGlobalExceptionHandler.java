package com.zapcom.exception;

import com.zapcom.model.response.GatewayServiceErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GatewayServiceGlobalExceptionHandler {

	@ExceptionHandler(GatewayServiceException.class)
	public ResponseEntity<GatewayServiceErrorResponse> handleGatewayServiceException(GatewayServiceException ex) {
		GatewayServiceErrorResponse errorResponse = new GatewayServiceErrorResponse(ex.getMessage());
		return ResponseEntity.status(ex.getStatusCode()).body(errorResponse);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<GatewayServiceErrorResponse> handleGenericException(Exception ex) {
		GatewayServiceErrorResponse errorResponse = new GatewayServiceErrorResponse("Unexpected error: " + ex.getMessage());
		return ResponseEntity.status(500).body(errorResponse);
	}

}