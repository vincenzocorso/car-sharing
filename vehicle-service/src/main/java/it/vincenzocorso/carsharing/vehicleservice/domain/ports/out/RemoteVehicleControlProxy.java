package it.vincenzocorso.carsharing.vehicleservice.domain.ports.out;

public interface RemoteVehicleControlProxy {
    /**
     * Notify the unlock code to the remote vehicle control system
     * @param vehicleId The vehicle id
     * @param unlockCode The unlock code
     */
    void notifyUnlockCode(String vehicleId, String unlockCode);

    /**
     * Unlock the vehicle with the given id
     * @param vehicleId The id of the vehicle to unlock
     */
    void unlockVehicle(String vehicleId);

    /**
     * Lock the vehicle with the given id
     * @param vehicleId The id of the vehicle to lock
     */
    void lockVehicle(String vehicleId);
}
