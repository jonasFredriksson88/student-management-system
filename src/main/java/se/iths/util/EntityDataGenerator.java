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

        teachers.forEach(teacher -> entityManager.persist(teacher));
        students.forEach(student -> entityManager.persist(student));

        List<Subject> subjects = List.of(
                new Subject("Matematik"),
                new Subject("Engelska"),
                new Subject("Svenska"),
                new Subject("Gymnastik"),
                new Subject("Tyska")
        );

        subjects.get(0).addTeacher(teachers.get(0));
        subjects.get(1).addTeacher(teachers.get(1));
        subjects.get(2).addTeacher(teachers.get(2));
        subjects.get(3).addTeacher(teachers.get(3));
        subjects.get(4).addTeacher(teachers.get(2));

        subjects.get(0).addStudent(students.get(0));
        subjects.get(0).addStudent(students.get(1));
        subjects.get(1).addStudent(students.get(0));
        subjects.get(1).addStudent(students.get(1));
        subjects.get(1).addStudent(students.get(2));
        subjects.get(2).addStudent(students.get(0));
        subjects.get(2).addStudent(students.get(2));
        subjects.get(3).addStudent(students.get(1));
        subjects.get(3).addStudent(students.get(3));
        subjects.get(4).addStudent(students.get(3));

        subjects.forEach(subject -> entityManager.persist(subject));
    }

}
