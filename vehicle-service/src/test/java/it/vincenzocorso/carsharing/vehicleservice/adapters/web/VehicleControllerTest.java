package it.vincenzocorso.carsharing.vehicleservice.adapters.web;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import it.vincenzocorso.carsharing.vehicleservice.domain.exceptions.VehicleNotFoundException;
import it.vincenzocorso.carsharing.vehicleservice.domain.models.SearchVehicleCriteria;
import it.vincenzocorso.carsharing.vehicleservice.domain.ports.in.RentVehicleUseCase;
import it.vincenzocorso.carsharing.vehicleservice.domain.ports.in.SearchVehicleUseCase;
import it.vincenzocorso.carsharing.vehicleservice.domain.ports.in.UpdateVehicleStatusUseCase;
import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import javax.ws.rs.core.MediaType;
import java.util.List;

import static io.restassured.RestAssured.*;
import static it.vincenzocorso.carsharing.vehicleservice.adapters.web.FakeVehicleDto.VEHICLE_RESPONSE;
import static it.vincenzocorso.carsharing.vehicleservice.adapters.web.FakeVehicleModelDto.VEHICLE_MODEL_RESPONSE;
import static it.vincenzocorso.carsharing.vehicleservice.domain.FakeVehicle.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@QuarkusTest
class VehicleControllerTest {
    @InjectMock
    private RentVehicleUseCase rentVehicleUseCase;

    @InjectMock
    private SearchVehicleUseCase searchVehicleUseCase;

    @InjectMock
    private UpdateVehicleStatusUseCase updateVehicleStatusUseCase;

    @ParameterizedTest
    @ValueSource(strings = {
        "",
        "?latitude=350.4&longitude=24.6",
        "?latitude=350.4&longitude=24.6&radius=305.8",
        "?states=BOOKED&states=AVAILABLE",
        "?limit=150",
        "?offset=0"
    })
    void shouldGetVehicles(String queryParameters) {
        List<VehicleResponse> expectedResponse = List.of(VEHICLE_RESPONSE);
        when(this.searchVehicleUseCase.getVehicles(any(SearchVehicleCriteria.class))).thenReturn(List.of(VEHICLE));

        List<VehicleResponse> actualResponse = given()
            .when().get("/vehicles" + queryParameters)
            .then().assertThat()
            .statusCode(HttpStatus.SC_OK)
            .contentType("application/json")
            .extract().body().jsonPath().getList(".", VehicleResponse.class);

        assertThat(actualResponse)
            .usingRecursiveComparison()
            .isEqualTo(expectedResponse);
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "?latitude=350.4",
        "?longitude=24.6",
        "?states=INVALID_STATE",
        "?limit=0",
        "?limit=201",
        "?offset=-1",
    })
    void shouldNotGetVehiclesWhenCriteriaAreNotValid(String queryParameters) {
        given()
            .when().get("/vehicles" + queryParameters)
            .then().assertThat()
            .statusCode(HttpStatus.SC_BAD_REQUEST)
            .contentType("application/json")
            .body("issues", Matchers.any(List.class));
    }

    @Test
    void shouldGetVehicleModels() {
        List<VehicleModelResponse> expectedResponse = List.of(VEHICLE_MODEL_RESPONSE);
        when(this.searchVehicleUseCase.getVehicleModels()).thenReturn(List.of(VEHICLE_MODEL));

        List<VehicleModelResponse> actualResponse = given()
                .when().get("/vehicles/models")
                .then().assertThat()
                .statusCode(HttpStatus.SC_OK)
                .contentType(MediaType.APPLICATION_JSON)
                .extract().body().jsonPath().getList(".", VehicleModelResponse.class);

        assertThat(actualResponse)
                .usingRecursiveComparison()
                .isEqualTo(expectedResponse);
    }

    @Test
    void shouldGetVehicle() {
        when(this.searchVehicleUseCase.getVehicle(VEHICLE_ID)).thenReturn(VEHICLE);

        VehicleResponse actualVehicleResponse = given()
                .when().get("/vehicles/" + VEHICLE_ID)
                .then().assertThat()
                .statusCode(HttpStatus.SC_OK)
                .contentType(MediaType.APPLICATION_JSON)
                .extract().body().as(VehicleResponse.class);

        assertThat(actualVehicleResponse)
                .usingRecursiveComparison()
                .isEqualTo(VEHICLE_RESPONSE);
    }

    @Test
    void shouldNotGetVehicleWhenVehicleDoesNotExist() {
        when(this.searchVehicleUseCase.getVehicle(VEHICLE_ID)).thenThrow(VehicleNotFoundException.class);

        given()
            .when().get("/vehicles/" + VEHICLE_ID)
            .then().assertThat()
            .statusCode(HttpStatus.SC_NOT_FOUND)
            .contentType(MediaType.APPLICATION_JSON)
            .body("issues", Matchers.any(List.class));
    }
}
