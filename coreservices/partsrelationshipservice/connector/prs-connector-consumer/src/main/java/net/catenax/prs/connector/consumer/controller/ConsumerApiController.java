//
// Copyright (c) 2021 Copyright Holder (Catena-X Consortium)
//
// See the AUTHORS file(s) distributed with this work for additional
// information regarding authorship.
//
// See the LICENSE file(s) distributed with this work for
// additional information regarding license terms.
//
package net.catenax.prs.connector.consumer.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import net.catenax.prs.connector.consumer.middleware.RequestMiddleware;
import net.catenax.prs.connector.consumer.service.ConsumerService;
import net.catenax.prs.connector.requests.FileRequest;
import org.eclipse.dataspaceconnector.spi.monitor.Monitor;
import org.eclipse.dataspaceconnector.spi.transfer.TransferInitiateResponse;

import java.util.Optional;

/**
 * Consumer API Controller.
 * Provides consumer extra endpoints.
 */
@Consumes({MediaType.APPLICATION_JSON})
@Produces({MediaType.APPLICATION_JSON})
@Path("/")
@RequiredArgsConstructor
public class ConsumerApiController {

    /**
     * Logger.
     */
    private final Monitor monitor;

    /**
     * Service implementation.
     */
    private final ConsumerService service;

    private final RequestMiddleware middleware;

    /**
     * Health endpoint.
     *
     * @return Consumer status
     */
    @GET
    @Path("health")
    public String checkHealth() {
        monitor.info("%s :: Received a health request");
        return "I'm alive!";
    }

    /**
     * Endpoint to trigger a request, so that a file get copied into a specific destination.
     *
     * @param request Request parameters.
     * @return TransferInitiateResponse with process id.
     */
    @POST
    @Path("file")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public Response initiateTransfer(final FileRequest request) {
        return middleware.invoke(() -> {
            final Optional<TransferInitiateResponse> transferInfo;
                transferInfo = service.initiateTransfer(request);
            return transferInfo.isPresent()
                            ? Response.ok(transferInfo.get().getId()).build()
                            : Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
                });
    }

    /**
     * Provides status of a process
     *
     * @param requestId ID of the process
     * @return Process state
     */
    @GET
    @Path("datarequest/{id}/state")
    @Produces(MediaType.TEXT_PLAIN)
    public Response getStatus(final @PathParam("id") String requestId) {
        return middleware.invoke(() -> {
                    final var status = service.getStatus(requestId);
                    return status.isPresent()
                            ? Response.ok(status.get().name()).build()
                            : Response.status(Response.Status.NOT_FOUND).build();
                });
    }
}
