package it.vincenzocorso.carsharing.rentservice.domain.models;

/**
 * @param customerId The id of the customer who created the rent
 * @param vehicleId  The id of the vehicle involved in the rent
 */
public record RentDetails(String customerId, String vehicleId) {
}
