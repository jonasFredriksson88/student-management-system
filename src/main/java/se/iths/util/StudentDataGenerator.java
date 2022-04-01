package se.iths.util;

import se.iths.entity.Student;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Arrays;
import java.util.List;

@Singleton
@Startup
public class StudentDataGenerator {

    @PersistenceContext
    EntityManager entityManager;

    @PostConstruct
    public void generateData() {
        List<Student> students = Arrays.asList(
                new Student("Bertil","Eriksson","bertil.eriksson@gmail.com","073-8492384"),
                new Student("Gunnar","Göransson","gunnar.goransson@gmail.com","073-3514861"),
                new Student("Berit","Svensson","berit.svensson@gmail.com","073-3794882"),
                new Student("Lisa","Johansson","lisa.johansson@gmail.com","073-5822493"));

        students.forEach(student -> entityManager.persist(student));
    }

}
