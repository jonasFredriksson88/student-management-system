package se.iths.rest;

import se.iths.entity.Teacher;
import se.iths.exceptions.EntityBadRequestException;
import se.iths.service.TeacherService;

import javax.inject.Inject;
import javax.transaction.TransactionalException;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Path("teachers")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class TeacherRest {

    @Inject
    TeacherService teacherService;

    @POST
    public Response addStudent(Teacher teacher) {
        try {
            teacherService.addTeacher(teacher);
            return Response.ok(teacher).build();
        } catch (
                TransactionalException e) {
            throw new EntityBadRequestException("There is already a teacher with email " + teacher.getEmail() + " Please try to login.");
        } catch (
                ConstraintViolationException e) {
            throw new EntityBadRequestException("You need to include first name, last name and email to proceed.");
        }
    }

    @PUT
    public Response updateTeacher(Teacher teacher) {
        if (teacher.getId() == null) {
            throw new EntityBadRequestException("You need to add id to user.");
        }

        try {
            teacherService.replaceTeacherInfo(teacher);
        } catch (
                TransactionalException e) {
            throw new EntityBadRequestException("There is already a teacher with email " + teacher.getEmail() + " Please try to login.");
        } catch (
                ConstraintViolationException e) {
            throw new EntityBadRequestException("You need to include first name, last name and email to proceed.");
        }
        return Response.ok(teacher).build();
    }

    @Path("{id}")
    @GET
    public Response findTeacher(@PathParam("id") Long id) {
        return Response.ok(teacherService.findTeacherById(id)).build();
    }

    @GET
    public Response getAllTeachers() {
        List<Teacher> foundTeachers = teacherService.getAllTeachers();
        if (foundTeachers.isEmpty()) {
            return Response.status(Response.Status.NO_CONTENT).build();
        }
        return Response.ok(foundTeachers).build();
    }

    @Path("{id}")
    @DELETE
    public Response removeTeacher(@PathParam("id") Long id) {
        teacherService.removeTeacher(id);

        return Response.ok().build();
    }

    @GET
    @Path("custom")
    public Response getTeachersCustomOrAll(@QueryParam("firstname") String firstName,
                                           @QueryParam("lastname") String lastName,
                                           @QueryParam("email") String email,
                                           @QueryParam("phonenumber") String phoneNumber) {

        Map<String, String> queryMap = new HashMap<>();

        queryMap.put("firstName", firstName);
        queryMap.put("lastName", lastName);
        queryMap.put("email", email);
        queryMap.put("phoneNumber", phoneNumber);

        List<Teacher> foundTeachers = teacherService.findCustomTeachers(queryMap);

        if (foundTeachers.isEmpty()) {
            return Response.status(Response.Status.NO_CONTENT).build();
        }

        return Response.ok(foundTeachers).build();
    }

    @Path("{id}")
    @PATCH
    public Response updateTeacherPatch(@PathParam("id") Long id, Teacher teacher) {
        return Response.ok(teacherService.updateTeacher(id, teacher)).build();
    }
}
