package it.vincenzocorso.carsharing.vehicleservice.adapters.proxies;

import it.vincenzocorso.carsharing.vehicleservice.domain.ports.out.RemoteVehicleControlProxy;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class RemoteVehicleControlProxyAdapter implements RemoteVehicleControlProxy {
    @Override
    public void notifyUnlockCode(String vehicleId, String unlockCode) {
        // TODO: to implement
    }

    @Override
    public void unlockVehicle(String vehicleId) {
        // TODO: to implement
    }

    @Override
    public void lockVehicle(String vehicleId) {
        // TODO: to implement
    }
}
