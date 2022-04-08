package se.iths.entity;

import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "Subjects")
public class Subject {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true)
    private String name;

    @ManyToOne(cascade = CascadeType.PERSIST)
    private Teacher teacher;

    @ManyToMany(targetEntity = Student.class,cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
    private Set<Student> students = new HashSet<>();

    public Subject() {

    }

    public Subject(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public Set<Student> getStudents() {
        return students;
    }

    public void setStudents(Set<Student> students) {
        this.students = students;
    }

    public void addStudent(Student student) {
        this.students.add(student);
        student.addSubject(this);
    }

    public void removeStudent(Student student) {
        this.students.remove(student);
        student.getSubjects().remove(this);
    }

    public void addTeacher(Teacher teacher) {
        this.teacher = teacher;
        teacher.getSubjects().add(this);
    }

    public void removeTeacher() {
        this.teacher = null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Subject subject = (Subject) o;
        return Objects.equals(id, subject.id) && Objects.equals(name, subject.name) && Objects.equals(teacher, subject.teacher) && Objects.equals(students, subject.students);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, teacher, students);
    }
}
