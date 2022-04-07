package se.iths.service;

import se.iths.entity.Student;
import se.iths.entity.Subject;
import se.iths.entity.Teacher;
import se.iths.exceptions.EntityBadRequestException;
import se.iths.exceptions.EntityNotFoundException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Transactional
public class SubjectService {

    @PersistenceContext
    EntityManager entityManager;

    public void addSubject(Subject subject) {
        entityManager.persist(subject);

    }

    public Subject findSubjectById(Long id) {
        Subject foundSubject = entityManager.find(Subject.class, id);

        if(foundSubject == null) {
            throw new EntityNotFoundException("Subject with ID " + id + " was not found.");
        }

        return foundSubject;
    }

    public List<Subject> getAllSubjects() {
        return entityManager.createQuery("SELECT s FROM Subject s", Subject.class).getResultList();
    }

    public List<Subject> getAllSubjectsWithTeacherId(Long teacherId) {
        return entityManager.createQuery("SELECT s FROM Subject s WHERE s.teacher.id = ?1", Subject.class)
                .setParameter(1, teacherId)
                .getResultList();
    }

    public List<Subject> getAllSubjectsWithStudentId(Long studentId) {
        return entityManager.createQuery("SELECT s FROM Subject s JOIN s.students st WHERE st.id = ?1", Subject.class)
                .setParameter(1, studentId)
                .getResultList();
    }

    public Subject addTeacherToSubject(Subject subject, Teacher teacher) {
        subject.setTeacher(teacher);
        return entityManager.merge(subject);
    }

    public Subject addStudentToSubject(Subject subject, Student student) {

        if (subject.getStudents().contains(student)) {
            throw new EntityBadRequestException("The student with email " + student.getEmail() + " already exists.");
        }
        subject.addStudent(student);
        return entityManager.merge(subject);
    }

    public void deleteSubject(Long id) {
        entityManager.remove(findSubjectById(id));
    }

    public Subject removeStudent(Long id, String studentEmail) {
        Subject foundSubject = findSubjectById(id);

        Student foundStudent = foundSubject.getStudents()
                .stream()
                .filter(student -> student.getEmail().equals(studentEmail))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("Student with email '" + studentEmail +  "' was not found."));

        foundSubject.removeStudent(foundStudent);

        return entityManager.merge(foundSubject);
    }

    public Subject removeTeacher(Long id, String teacherEmail) {
        Subject foundSubject = findSubjectById(id);

        if (!foundSubject.getTeacher().getEmail().equals(teacherEmail)) {
            throw new EntityNotFoundException("Teacher with email '" + teacherEmail +  "' was not found.");
        }

        foundSubject.removeTeacher();

        return entityManager.merge(foundSubject);
    }

}
