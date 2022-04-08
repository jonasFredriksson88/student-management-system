package se.iths.service;

import se.iths.entity.Student;
import se.iths.exceptions.EntityNotFoundException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;
import static se.iths.util.Utils.stringBuilder;

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
        Student foundStudent = entityManager.find(Student.class, id);

        if(foundStudent == null) {
            throw new EntityNotFoundException("Student with ID " + id + " was not found.");
        }

        return foundStudent;
    }

    public List<Student> findCustomStudents(Map<String, String> queryMap) {
        String queryString = stringBuilder(removeEmptyParams(queryMap), "Student");

        return entityManager.createQuery(queryString, Student.class).getResultList();
    }

    public List<Student> getAllStudents() {
        return entityManager.createQuery("SELECT s FROM Student s", Student.class).getResultList();
    }

    public void removeStudent(Long id) {
        Student foundStudent = findStudentById(id);
        foundStudent.removeAllSubjects();
        entityManager.remove(foundStudent);
    }

    public Student updateStudent(Long id, Student newStudent) {
        Student oldStudent = findStudentById(id);

        if(!isNull(newStudent.getFirstName()))
            oldStudent.setFirstName(newStudent.getFirstName());
        if(!isNull(newStudent.getLastName()))
            oldStudent.setLastName(newStudent.getLastName());
        if(!isNull(newStudent.getEmail()))
            oldStudent.setEmail(newStudent.getEmail());
        if(!isNull(newStudent.getPhoneNumber()))
            oldStudent.setPhoneNumber(newStudent.getPhoneNumber());

        return entityManager.merge(oldStudent);
    }

    private Map<String, String> removeEmptyParams(Map<String, String> queryMap) {
        return queryMap.entrySet().stream()
                .filter(q -> q.getValue() != null)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

}
