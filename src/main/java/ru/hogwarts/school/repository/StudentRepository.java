package ru.hogwarts.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.hogwarts.school.model.Student;

import java.util.List;
@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    List<Student> getStudentsByAgeBetween(int ageFrom, int ageTo);
    List<Student> getStudentsByFaculty_Id(Long id);
    @Query(value = "SELECT COUNT(*) FROM student", nativeQuery = true)
    Integer getStudentsAmount();

    @Query(value = "SELECT avg(age) FROM student", nativeQuery = true)
    Integer getStudentsAverageAge();

    @Query(value = "SELECT * FROM student ORDER BY id DESC LIMIT 5", nativeQuery = true)
    List<Student> getStudentsLastFive();
}
