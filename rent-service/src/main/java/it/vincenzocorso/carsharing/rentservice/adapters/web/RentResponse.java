package it.vincenzocorso.carsharing.rentservice.adapters.web;

import lombok.Builder;

import java.time.Instant;

@Builder
public record RentResponse (
	String rentId,
	String customerId,
	String vehicleId,
	String state,
	Instant acceptedAt,
	Instant startedAt,
	Instant endedAt
) {}
