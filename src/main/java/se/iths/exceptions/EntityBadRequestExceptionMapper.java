package se.iths.exceptions;

import se.iths.messages.ErrorMessage;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class EntityBadRequestExceptionMapper implements ExceptionMapper<EntityBadRequestException> {

    public Response toResponse(EntityBadRequestException e) {
        ErrorMessage errorMessage = new ErrorMessage(e.getMessage(),e.getClass().getName(),400);
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(errorMessage)
                .build();
    }
}
