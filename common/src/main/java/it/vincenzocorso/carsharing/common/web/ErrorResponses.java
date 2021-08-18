package it.vincenzocorso.carsharing.common.web;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ErrorResponses {
	public static ErrorResponse makeValidationErrorResponse(List<Issue> issues) {
		return new ErrorResponse("VALIDATION_ERROR", "A validation error occurred in request body.", issues);
	}

	public static ErrorResponse makeInvalidRequestBodyErrorResponse(List<Issue> issues) {
		return new ErrorResponse("INVALID_REQUEST_BODY", "The request body is not readable.", issues);
	}

	public static ErrorResponse makeResourceNotFoundErrorResponse(List<Issue> issues) {
		return new ErrorResponse("RESOURCE_NOT_FOUND", "The resource was not found", issues);
	}

	public static ErrorResponse makeBadRequestErrorResponse(List<Issue> issues) {
		return new ErrorResponse("BAD_REQUEST", "The request can't be performed", issues);
	}

	public static ErrorResponse makeInternalServerErrorResponse() {
		return new ErrorResponse("INTERNAL_SERVER_ERROR", "An error occurred processing the request.", Collections.emptyList());
	}
}
