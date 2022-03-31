package se.iths.exceptions;

import se.iths.messages.ErrorMessage;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class StudentNotFoundExceptionMapper implements ExceptionMapper<StudentNotFoundException> {

    public Response toResponse(StudentNotFoundException e) {
        ErrorMessage errorMessage = new ErrorMessage(e.getMessage(),e.getClass().getName(),404);
        return Response.status(Response.Status.NOT_FOUND)
                .entity(errorMessage)
                .build();
    }
}
