package se.iths.exceptions;

import se.iths.messages.ErrorMessage;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class StudentBadRequestExceptionMapper implements ExceptionMapper<StudentBadRequestException> {

    public Response toResponse(StudentBadRequestException e) {
        ErrorMessage errorMessage = new ErrorMessage(e.getMessage(),e.getClass().getName(),400);
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(errorMessage)
                .build();
    }
}
