package it.vincenzocorso.carsharing.paymentservice.adapters.web;

import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;

import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;

@Path("/paymentmethods")
@Produces(APPLICATION_JSON)
@Consumes(APPLICATION_JSON)
public interface PaymentMethodRestApi {
    @GET
    @Path("{paymentMethodId}")
    Response getPaymentMethod(@PathParam("paymentMethodId") String paymentMethodId);

    @POST
    Response savePaymentMethod(@Valid SavePaymentMethodRequest request);
}
