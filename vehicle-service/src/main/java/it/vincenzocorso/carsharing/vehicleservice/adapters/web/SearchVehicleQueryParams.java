package it.vincenzocorso.carsharing.vehicleservice.adapters.web;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.QueryParam;
import java.util.List;

public class SearchVehicleQueryParams {
    @QueryParam("latitude")
    Double latitude;

    @QueryParam("longitude")
    Double longitude;

    @QueryParam("radius")
    @DefaultValue("250")
    Double radius;

    @QueryParam("states")
    List<@Pattern(
        regexp = "AVAILABLE|BOOKED|RENTED|OUT_OF_SERVICE",
        message = "Each state must be one of the following: AVAILABLE, BOOKED, RENTED, OUT_OF_SERVICE"
    ) String> states;

    @QueryParam("limit")
    @DefaultValue("10")
    @Min(value = 1, message = "The limit must be positive")
    @Max(value = 200, message = "The limit must be at most 200")
    Integer limit;

    @QueryParam("offset")
    @DefaultValue("0")
    @Min(value = 0, message = "Offset must be greater or equal to zero")
    Integer offset;

    @AssertTrue(message = "The query parameters longitude and latitude must be both present or both absent")
    public boolean isValid() {
        return (this.longitude == null) == (this.latitude == null);
    }
}
