package ru.hogwarts.school.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.exception.FacultyNotFoundException;
import ru.hogwarts.school.exception.StudentNotFoundException;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.AvatarRepository;
import ru.hogwarts.school.repository.StudentRepository;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;

import static java.nio.file.StandardOpenOption.CREATE_NEW;

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
        Student oldStudent = new Student();
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
        Student student = new Student();
        student.getId();
        student.getAge();
        student.getName();
        return student;
    }
    public Avatar findAvatar(long studentId) {
        return avatarRepository.findByStudentId(studentId).orElseThrow();
    }

    public void uploadAvatar(Long studentId, MultipartFile file) throws IOException {
        Student student = getStudentById(studentId);
        Path filePath = Path.of(avatarsDir, studentId + "." + getExtension(Objects.requireNonNull(file.getOriginalFilename())));
        Files.createDirectories(filePath.getParent());
        Files.deleteIfExists(filePath);

        try (InputStream is = file.getInputStream();
             OutputStream os = Files.newOutputStream(filePath, CREATE_NEW);
             BufferedInputStream bis = new BufferedInputStream(is, 1024);
             BufferedOutputStream bos = new BufferedOutputStream(os, 1024)
        ) {
            bis.transferTo(bos);
        }

        Avatar avatar = avatarRepository.findByStudentId(studentId).orElseGet(Avatar::new);
        avatar.setStudent(student);
        avatar.setFilePath(filePath.toString());
        avatar.setFileSize(file.getSize());
        avatar.setMediaType(file.getContentType());
        avatar.setData(file.getBytes());

        avatarRepository.save(avatar);
    }

    private String getExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }
}