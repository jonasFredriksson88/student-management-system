package se.iths.exceptions;

import se.iths.messages.ErrorMessage;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class EntityNoContentExceptionMapper implements ExceptionMapper<EntityNoContentException> {

    public Response toResponse(EntityNoContentException e) {
        ErrorMessage errorMessage = new ErrorMessage(e.getMessage(),e.getClass().getName(),204);
        return Response.status(Response.Status.NO_CONTENT)
                .entity(errorMessage)
                .build();
    }
}
