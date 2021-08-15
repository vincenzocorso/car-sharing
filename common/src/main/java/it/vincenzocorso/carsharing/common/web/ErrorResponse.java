package it.vincenzocorso.carsharing.common.web;

import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class ErrorResponse {
	public final String type;
	public final String message;
	public final List<Issue> issues;
}
