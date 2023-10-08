package it.vincenzocorso.carsharing.customerservice.domain.ports.in;

public interface RentVehicle {
    boolean verifyCustomer(String customerId);
}
