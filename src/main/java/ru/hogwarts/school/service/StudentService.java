package ru.hogwarts.school.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.exception.FacultyNotFoundException;
import ru.hogwarts.school.exception.StudentNotFoundException;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.AvatarRepository;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.List;

@Service
public class StudentService {
    @Value("${avatars.dir.path}")
    private String avatarsDir;
    private final StudentRepository studentRepository;
    private final AvatarRepository avatarRepository;
    public StudentService(StudentRepository studentRepository, AvatarRepository avatarRepository) {
        this.studentRepository = studentRepository;
        this.avatarRepository = avatarRepository;
    }
    public Student addStudent(Student student) {
        return studentRepository.save(student);
    }
    public Student getStudentById(Long id) {
        return studentRepository.findById(id).orElseThrow(() -> new FacultyNotFoundException(id));
    }

    public Student updateStudent(Long id, Student student) {
        studentRepository.findById(id).orElseThrow(() -> new StudentNotFoundException(id));
        Student oldStudent = new Student(id);
        oldStudent.setId(id);
        oldStudent.setAge(student.getAge());
        oldStudent.setName(student.getName());
        return studentRepository.save(oldStudent);
    }
    public void deleteStudentById(Long id) {
        studentRepository.deleteById(id);
    }

    public List<Student> getStudentsByAgeBetween(int ageFrom, int ageTo) {
        return studentRepository.getStudentsByAgeBetween(ageFrom, ageTo);
    }

    public Faculty getFacultyByStudentId(Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new FacultyNotFoundException(id));
        return student.getFaculty();
    }
    public Student getStudentInfo(Long id) {
        studentRepository.findById(id).orElseThrow(() -> new StudentNotFoundException(id));
        Student student = new Student(id);
        student.getId();
        student.getAge();
        student.getName();
        return student;
    }
    public Avatar findAvatar(long id) {
        return avatarRepository.getAvatarByStudentId(id).orElseThrow();
    }

    private Student findStudent(Long id) {
        return studentRepository.findById(id).orElseThrow();
    }

    private String getExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }
    public Integer getStudentsAmount(){
        return studentRepository.getStudentsAmount();
    }

    public Integer getStudentsAverageAge(){
        return studentRepository.getStudentsAverageAge();
    }

    public List<Student> getStudentsLastFive() {
        return studentRepository.getStudentsLastFive();
    }
}