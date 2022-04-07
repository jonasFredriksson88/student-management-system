package se.iths.util;

import se.iths.entity.Student;
import se.iths.entity.Subject;
import se.iths.entity.Teacher;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Singleton
@Startup
public class EntityDataGenerator {

    @PersistenceContext
    EntityManager entityManager;

    @PostConstruct
    public void generateData() {
        List<Student> students = List.of(
                new Student("Bertil","Eriksson","bertil.eriksson@gmail.com","073-8492384"),
                new Student("Gunnar","Göransson","gunnar.goransson@gmail.com","073-3514861"),
                new Student("Berit","Svensson","berit.svensson@gmail.com","073-3794882"),
                new Student("Lisa","Johansson","lisa.johansson@gmail.com","073-5822493"));

        List<Teacher> teachers = List.of(
                new Teacher("Sven","Gunnarsson","sven.gunnarsson@gmail.com","073-5468517"),
                new Teacher("Anna","Mårtensson","anna.martensson@gmail.com","073-4825823"),
                new Teacher("Frida","Karlsson","frida.karlsson@gmail.com","073-9755814"),
                new Teacher("Gun","Persson","gun.persson@gmail.com","073-2504816"));

        List<Subject> subjects = List.of(
                new Subject("Matematik", teachers.get(0), List.of(students.get(0),students.get(1))),
                new Subject("Engelska", teachers.get(1), List.of(students.get(0),students.get(1), students.get(2))),
                new Subject("Svenska", teachers.get(2), List.of(students.get(0),students.get(2))),
                new Subject("Gymnastik", teachers.get(3), List.of(students.get(1),students.get(3))),
                new Subject("Tyska", teachers.get(2), List.of(students.get(3)))
        );

        teachers.forEach(teacher -> entityManager.persist(teacher));
        students.forEach(student -> entityManager.persist(student));
        subjects.forEach(subject -> entityManager.persist(subject));
    }

}
