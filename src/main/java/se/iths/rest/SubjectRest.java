package se.iths.rest;

import se.iths.entity.Student;
import se.iths.entity.Subject;
import se.iths.entity.Teacher;
import se.iths.exceptions.EntityBadRequestException;
import se.iths.service.StudentService;
import se.iths.service.SubjectService;
import se.iths.service.TeacherService;

import javax.inject.Inject;
import javax.persistence.RollbackException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("subjects")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class SubjectRest {


    private final SubjectService subjectService;
    private final TeacherService teacherService;
    private final StudentService studentService;

    @Inject
    public SubjectRest(SubjectService subjectService, TeacherService teacherService, StudentService studentService) {
        this.subjectService = subjectService;
        this.teacherService = teacherService;
        this.studentService = studentService;
    }

    @GET
    public Response getAllSubjects() {
        List<Subject> foundSubjects = subjectService.getAllSubjects();
        if (foundSubjects.isEmpty()) {
            return Response.status(Response.Status.NO_CONTENT).build();
        }
        return Response.ok(foundSubjects).build();
    }

    @Path("{id}")
    @GET
    public Response findSubjectById(@PathParam("id") Long id) {
        return Response.ok(subjectService.findSubjectById(id)).build();
    }

    @Path("teachers/{id}")
    @GET
    public Response getAllSubjectsWithTeacherId(@PathParam("id") Long teacherId) {
        List<Subject> subjects = subjectService.getAllSubjectsWithTeacherId(teacherId);
        return Response.ok(subjects).build();
    }

    @Path("students/{id}")
    @GET
    public Response getAllSubjectsWithStudentId(@PathParam("id") Long studentId) {
        List<Subject> subjects = subjectService.getAllSubjectsWithStudentId(studentId);
        return Response.ok(subjects).build();
    }

    @POST
    public Response addSubject(Subject subject) {
        try {
            subjectService.addSubject(subject);
            return Response.ok(subject).build();
        } catch (Exception e) {
            throw new EntityBadRequestException("Subject " + subject.getName() + " already exists");
        }
    }

    @Path("{sId}/teachers/{tId}")
    @PATCH
    public Response addTeacherToSubject(@PathParam("sId") Long subjectId, @PathParam("tId") Long teacherId) {
        Subject foundSubject = subjectService.findSubjectById(subjectId);
        Teacher foundTeacher = teacherService.findTeacherById(teacherId);

        return Response.ok(subjectService.addTeacherToSubject(foundSubject, foundTeacher)).build();

    }

    @Path("{suId}/students/{stId}")
    @PATCH
    public Response addStudentToSubject(@PathParam("suId") Long subjectId, @PathParam("stId") Long studentId) {
        Subject foundSubject = subjectService.findSubjectById(subjectId);
        Student foundStudent = studentService.findStudentById(studentId);

        return Response.ok(subjectService.addStudentToSubject(foundSubject, foundStudent)).build();
    }

    @Path("{id}")
    @DELETE
    public Response deleteSubject(@PathParam("id") Long id) {
        subjectService.deleteSubject(id);

        return Response.ok().build();
    }

    @Path("{suId}/removestudent")
    @PATCH
    public Response removeStudent(@PathParam("suId") Long subjectId, @QueryParam("email") String studentEmail) {
        return Response.ok(subjectService.removeStudent(subjectId, studentEmail)).build();
    }

    @Path("{suId}/removeteacher")
    @PATCH
    public Response removeTeacher(@PathParam("suId") Long subjectId, @QueryParam("email") String teacherEmail) {
        return Response.ok(subjectService.removeTeacher(subjectId, teacherEmail)).build();
    }

}
