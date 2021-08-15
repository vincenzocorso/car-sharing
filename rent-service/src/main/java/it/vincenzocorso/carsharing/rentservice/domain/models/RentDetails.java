package it.vincenzocorso.carsharing.rentservice.domain.models;

import java.util.Objects;

public class RentDetails {
	private final String customerId;
	private final String vehicleId;

	public RentDetails(String customerId, String vehicleId) {
		this.customerId = customerId;
		this.vehicleId = vehicleId;
	}

	public String getCustomerId() {
		return this.customerId;
	}

	public String getVehicleId() {
		return this.vehicleId;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || this.getClass() != o.getClass()) return false;
		RentDetails that = (RentDetails) o;
		return this.customerId.equals(that.customerId) && this.vehicleId.equals(that.vehicleId);
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.customerId, this.vehicleId);
	}
}
