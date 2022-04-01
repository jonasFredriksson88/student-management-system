package se.iths.service;

import net.bytebuddy.implementation.bytecode.Throw;
import se.iths.entity.Student;
import se.iths.exceptions.StudentBadRequestException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.transaction.TransactionalException;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.BadRequestException;
import java.util.*;
import java.util.stream.Collectors;

@Transactional
public class StudentService {

    @PersistenceContext
    EntityManager entityManager;

    public void addStudent(Student student) {
        entityManager.persist(student);
    }

    public void replaceStudentInfo(Student student) {
        entityManager.merge(student);
    }

    public Student findStudentById(Long id) {
        return entityManager.find(Student.class, id);
    }

    public List<Student> findCustomStudents(Map<String, String> queryMap) {

        String queryString = stringBuilder(removeEmptyParams(queryMap));

        return entityManager.createQuery(queryString).getResultList();
    }

    public List<Student> getAllStudents() {
        return entityManager.createQuery("SELECT s FROM Student s", Student.class).getResultList();
    }

    public void removeStudent(Long id) {
        entityManager.remove(findStudentById(id));
    }

    public Student updateStudent(Long id, Student newStudent) {
        Student oldStudent = findStudentById(id);

        if(newStudent.getFirstName() != null)
            oldStudent.setFirstName(newStudent.getFirstName());
        if(newStudent.getLastName() != null)
            oldStudent.setLastName(newStudent.getLastName());
        if(newStudent.getEmail() != null)
            oldStudent.setEmail(newStudent.getEmail());
        if(newStudent.getPhoneNumber() != null)
            oldStudent.setPhoneNumber(newStudent.getPhoneNumber());

        return entityManager.merge(oldStudent);
    }

    private Map<String, String> removeEmptyParams(Map<String, String> queryMap) {
        return queryMap.entrySet().stream()
                .filter(q -> q.getValue() != null)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private String stringBuilder(Map<String, String> queryMap) {
        String queryString = "SELECT s FROM Student s WHERE ";

        if(!queryMap.isEmpty()) {
            for (Map.Entry<String, String> param : queryMap.entrySet()) {
                queryString += " s." + param.getKey() + " = '" + param.getValue() + "' AND ";
            }
        } else {
            throw new StudentBadRequestException("You must add at least one parameter");
        }

        if(queryString.endsWith("AND ")) {
            queryString = queryString.substring(0, queryString.length() - 5);
        }

        return queryString;
    }

}
