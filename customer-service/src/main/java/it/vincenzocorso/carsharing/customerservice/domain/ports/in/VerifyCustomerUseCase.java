package it.vincenzocorso.carsharing.customerservice.domain.ports.in;

public interface VerifyCustomerUseCase {
    boolean verifyCustomer(String customerId);
}
