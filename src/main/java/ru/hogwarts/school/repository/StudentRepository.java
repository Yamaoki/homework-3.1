package ru.hogwarts.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.hogwarts.school.model.Student;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {
    List<Student> getStudentsByAgeBetween(int ageFrom, int ageTo);
    List<Student> getStudentsByFaculty_Id(Long id);
}
