package se.iths.exceptions;

import se.iths.messages.ErrorMessage;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class GenericExceptionMapper implements ExceptionMapper<Exception> {

    public Response toResponse(Exception throwable) {
        ErrorMessage errorMessage = new ErrorMessage(throwable.getMessage(),throwable.getClass().getName(), 500);

        if(throwable.getMessage().contains("404")) {
            errorMessage.setErrorCode(404);

            return Response.status(Response.Status.NOT_FOUND)
                    .entity(errorMessage)
                    .build();
        }

        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(errorMessage)
                .build();
    }
}
