package it.vincenzocorso.carsharing.customerservice.domain.ports.in;

public interface RentVehicleUseCase {
    boolean verifyCustomer(String customerId);
}
