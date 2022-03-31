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

    public List<Student> getAllStudents() {
        return entityManager.createQuery("SELECT s FROM Student s", Student.class).getResultList();
    }

    public void removeStudent(Long id) {
        entityManager.remove(findStudentById(id));
    }

    public Student updateStudent(Long id, Map<String, String> queryMap) {
        Student foundStudent = findStudentById(id);

        replaceStudentInfo(foundStudent, removeEmptyParams(queryMap));

        return entityManager.merge(foundStudent);
    }

    private void replaceStudentInfo(Student foundStudent, Map<String, String> queryMap) {
        for (Map.Entry<String, String> param : queryMap.entrySet()) {
            switch (param.getKey()) {
                case "firstName" -> foundStudent.setFirstName(param.getValue());
                case "lastName" -> foundStudent.setLastName(param.getValue());
                case "email" -> foundStudent.setEmail(param.getValue());
                case "phoneNumber" -> foundStudent.setPhoneNumber(param.getValue());
            }
        }
    }

    private Map<String, String> removeEmptyParams(Map<String, String> queryMap) {
        return queryMap.entrySet().stream()
                .filter(q -> q.getValue() != null)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

}
