package it.vincenzocorso.carsharing.customerservice.adapters.persistence.mongodb;

import io.quarkus.mongodb.panache.PanacheMongoEntityBase;
import io.quarkus.mongodb.panache.common.MongoEntity;
import org.bson.types.ObjectId;

import java.time.LocalDate;

@MongoEntity(collection = "customers")
public class CustomerDocument extends PanacheMongoEntityBase {
	public ObjectId id;
	public String firstName;
	public String lastName;
	public LocalDate dateOfBirth;
	public String fiscalCode;
	public String email;
	public String phoneNumber;
	public DriverLicenseDocument driverLicense;
	public boolean driverLicenseVerified;
	public boolean emailVerified;
}
