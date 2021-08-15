package it.vincenzocorso.carsharing.rentservice.api.web;

import lombok.Builder;

import java.time.Instant;

@Builder
public class RentResponse {
	public final String rentId;
	public final String customerId;
	public final String vehicleId;
	public final String state;
	public final Instant acceptedAt;
	public final Instant startedAt;
	public final Instant endedAt;
}
