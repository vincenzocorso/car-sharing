package it.vincenzocorso.carsharing.common.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class DefaultExceptionHandler {
	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ErrorResponse handleValidationException(MethodArgumentNotValidException ex) {
		List<Issue> issues = ex.getBindingResult().getFieldErrors().stream()
				.map(this::convertToIssue)
				.collect(Collectors.toList());

		return new ErrorResponse("VALIDATION_ERROR", "A validation error occurred in request body.", issues);
	}

	private Issue convertToIssue(FieldError fieldError) {
		return new Issue(fieldError.getField(), fieldError.getDefaultMessage());
	}

	@ExceptionHandler(HttpMessageNotReadableException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ErrorResponse handleMissingRequestBody(HttpMessageNotReadableException ex) {
		return new ErrorResponse("INVALID_REQUEST_BODY", "The request body is not readable.", Collections.emptyList());
	}

	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ErrorResponse handleGenericException(Exception ex) {
		log.warn("No exception handler found for: ", ex);

		return new ErrorResponse("INTERNAL_SERVER_ERROR", "An error occurred processing the request.", Collections.emptyList());
	}
}
