package se.iths.rest;


import se.iths.entity.Student;
import se.iths.exceptions.EntityBadRequestException;
import se.iths.service.StudentService;

import javax.inject.Inject;
import javax.transaction.TransactionalException;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Path("students")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class StudentRest {

    @Inject
    StudentService studentService;

    @POST
    public Response addStudent(Student student) {
        try {
            studentService.addStudent(student);
        } catch (
                TransactionalException e) {
            throw new EntityBadRequestException("There is already a student with email " + student.getEmail() + " Please try to login.");
        } catch (
                ConstraintViolationException e) {
            throw new EntityBadRequestException("You need to include first name, last name and email to proceed.");
        }
        return Response.ok(student).build();
    }

    @PUT
    public Response updateStudent(Student student) {
        if (student.getId() == null) {
            throw new EntityBadRequestException("You need to add id to user.");
        }

        try {
            studentService.replaceStudentInfo(student);
        } catch (
                TransactionalException e) {
            throw new EntityBadRequestException("There is already a student with email " + student.getEmail() + " Please try to login.");
        } catch (
                ConstraintViolationException e) {
            throw new EntityBadRequestException("You need to include first name, last name and email to proceed.");
        }
        return Response.ok(student).build();
    }

    @Path("{id}")
    @GET
    public Response findStudent(@PathParam("id") Long id) {
        return Response.ok(studentService.findStudentById(id)).build();
    }

    @GET
    public Response getAllStudents() {
        List<Student> foundStudents = studentService.getAllStudents();
        if (foundStudents.isEmpty()) {
            return Response.status(Response.Status.NO_CONTENT).build();
        }
        return Response.ok(foundStudents).build();
    }

    @Path("{id}")
    @DELETE
    public Response removeStudent(@PathParam("id") Long id) {
        studentService.removeStudent(id);

        return Response.ok().build();
    }

    @GET
    @Path("custom")
    public Response getStudentsCustomOrAll(@QueryParam("firstname") String firstName,
                                           @QueryParam("lastname") String lastName,
                                           @QueryParam("email") String email,
                                           @QueryParam("phonenumber") String phoneNumber) {


        Map<String, String> queryMap = new HashMap<>();

        queryMap.put("firstName", firstName);
        queryMap.put("lastName", lastName);
        queryMap.put("email", email);
        queryMap.put("phoneNumber", phoneNumber);

        List<Student> foundStudents = studentService.findCustomStudents(queryMap);

        if (foundStudents.isEmpty()) {
            return Response.status(Response.Status.NO_CONTENT).build();
        }

        return Response.ok(foundStudents).build();
    }

    @Path("{id}")
    @PATCH
    public Response updateStudentPatch(@PathParam("id") Long id, Student student) {
            return Response.ok(studentService.updateStudent(id, student)).build();
    }
}
