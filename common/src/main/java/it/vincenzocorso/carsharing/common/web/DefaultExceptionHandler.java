package it.vincenzocorso.carsharing.common.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Path;
import java.util.Collections;
import java.util.Iterator;
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

		return ErrorResponses.makeValidationErrorResponse(issues);
	}

	private Issue convertToIssue(FieldError fieldError) {
		return new Issue(fieldError.getField(), fieldError.getDefaultMessage());
	}

	@ExceptionHandler(ConstraintViolationException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ErrorResponse handleValidationException(ConstraintViolationException ex) {
		List<Issue> issues = ex.getConstraintViolations().stream()
				.map(this::convertToIssue)
				.collect(Collectors.toList());

		return ErrorResponses.makeValidationErrorResponse(issues);
	}

	private Issue convertToIssue(ConstraintViolation<?> constraintViolation) {
		Iterator<Path.Node> iterator = constraintViolation.getPropertyPath().iterator();
		Path.Node lastNode = iterator.next();
		while(iterator.hasNext())
			lastNode = iterator.next();

		return new Issue(lastNode.getName(), constraintViolation.getMessage());
	}

	@ExceptionHandler(HttpMessageNotReadableException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ErrorResponse handleMissingRequestBody(HttpMessageNotReadableException ex) {
		return ErrorResponses.makeInvalidRequestBodyErrorResponse(Collections.emptyList());
	}

	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ErrorResponse handleGenericException(Exception ex) {
		log.warn("No exception handler found for: ", ex);
		return ErrorResponses.makeInternalServerErrorResponse();
	}
}
