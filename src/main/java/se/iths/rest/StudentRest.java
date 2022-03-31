package se.iths.rest;


import se.iths.entity.Student;
import se.iths.exceptions.StudentBadRequestException;
import se.iths.exceptions.StudentNotFoundException;
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
            return Response.ok(student).build();
        } catch (
                TransactionalException e) {
            throw new StudentBadRequestException("There is already a student with email " + student.getEmail() + " Please try to login.");
        } catch (
                ConstraintViolationException e) {
            throw new StudentBadRequestException("You need to include first name, last name and email to proceed.");
        }
    }

    @PUT
    public Response updateStudent(Student student) {
        if (student.getId() == null) {
            throw new StudentBadRequestException("You need to add id to user.");
        }

        try {
            studentService.replaceStudentInfo(student);
        } catch (
                TransactionalException e) {
            throw new StudentBadRequestException("There is already a student with email " + student.getEmail() + " Please try to login.");
        } catch (
                ConstraintViolationException e) {
            throw new StudentBadRequestException("You need to include first name, last name and email to proceed.");
        }
        return Response.ok(student).build();
    }

    @Path("{id}")
    @GET
    public Response findStudent(@PathParam("id") Long id) {
        Student foundStudent = studentService.findStudentById(id);

        if (foundStudent == null) {
            throw new StudentNotFoundException("Student with ID " + id + " was not found.");
        }
        return Response.ok(foundStudent).build();
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
        try {
            studentService.removeStudent(id);
        } catch (IllegalArgumentException e) {
            throw new StudentNotFoundException("Student with ID " + id + " was not found and could not be removed.");
        }

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
    public Response updateLastName(@PathParam("id") Long id,
                                   @QueryParam("firstname") String firstName,
                                   @QueryParam("lastname") String lastName,
                                   @QueryParam("email") String email,
                                   @QueryParam("phonenumber") String phoneNumber) {

        Map<String, String> queryMap = new HashMap<>();

        queryMap.put("firstName", firstName);
        queryMap.put("lastName", lastName);
        queryMap.put("email", email);
        queryMap.put("phoneNumber", phoneNumber);

        try {
            Student foundStudent = studentService.updateStudent(id, queryMap);
            return Response.ok(foundStudent).build();
        } catch (NullPointerException e) {
            throw new StudentNotFoundException("Student with ID " + id + " was not found and could not be Updated.");
        }
    }
}
