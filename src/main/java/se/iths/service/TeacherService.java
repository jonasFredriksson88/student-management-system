package se.iths.service;

import se.iths.entity.Teacher;
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
public class TeacherService {

    @PersistenceContext
    EntityManager entityManager;

    public void addTeacher(Teacher student) {
        entityManager.persist(student);
    }

    public void replaceTeacherInfo(Teacher student) {
        entityManager.merge(student);
    }

    public Teacher findTeacherById(Long id) {
        Teacher foundTeacher = entityManager.find(Teacher.class, id);

        if (foundTeacher == null) {
            throw new EntityNotFoundException("Teacher with ID " + id + " was not found.");
        }

        return foundTeacher;
    }

    public List<Teacher> findCustomTeachers(Map<String, String> queryMap) {
        String queryString = stringBuilder(removeEmptyParams(queryMap), "Teacher");

        return entityManager.createQuery(queryString, Teacher.class).getResultList();
    }

    public List<Teacher> getAllTeachers() {
        return entityManager.createQuery("SELECT t FROM Teacher t", Teacher.class).getResultList();
    }

    public void removeTeacher(Long id) {
        entityManager.remove(findTeacherById(id));
    }

    public Teacher updateTeacher(Long id, Teacher newTeacher) {
        Teacher oldTeacher = findTeacherById(id);

        if(!isNull(newTeacher.getFirstName()))
            oldTeacher.setFirstName(newTeacher.getFirstName());
        if(!isNull(newTeacher.getLastName()))
            oldTeacher.setLastName(newTeacher.getLastName());
        if(!isNull(newTeacher.getEmail()))
            oldTeacher.setEmail(newTeacher.getEmail());
        if(!isNull(newTeacher.getPhoneNumber()))
            oldTeacher.setPhoneNumber(newTeacher.getPhoneNumber());

        return entityManager.merge(oldTeacher);
    }

    private Map<String, String> removeEmptyParams(Map<String, String> queryMap) {
        return queryMap.entrySet().stream()
                .filter(q -> q.getValue() != null)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

}
