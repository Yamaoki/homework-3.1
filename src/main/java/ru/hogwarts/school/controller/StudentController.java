package ru.hogwarts.school.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

import java.util.Collection;
import java.util.Collections;

@RestController
@RequestMapping("/student")
public class StudentController {

    private final StudentService studentService;
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }
    @PostMapping // POST http://localhost:8080/student
    public ResponseEntity<Student> addStudent(@RequestBody Student student){
        Student addedStudent = studentService.addStudent(student);
        return ResponseEntity.ok(addedStudent);
    }
    @GetMapping("{id}") // GET http://localhost:8080/student/1
    public ResponseEntity<Student> getStudentById(@PathVariable Long id){
        Student student = studentService.getStudentById(id);
        if (student == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(student);
    }
    @GetMapping("/all") // GET http://localhost:8080/student/all
    public ResponseEntity<Collection<Student>> getAllStudents(){
        return ResponseEntity.ok(studentService.getAllStudents());
    }
    @PutMapping("/{id}") // PUT http://localhost:8080/student/1
    public ResponseEntity<Student> updateStudent(@PathVariable("id") long id, @RequestBody Student student){
        Student updatedStudent = studentService.updateStudent(id, student);
        return ResponseEntity.ok(updatedStudent);
    }
    @DeleteMapping("{id}") // DELETE http://localhost:8080/student/1
    public ResponseEntity<Student> deleteStudentById(@PathVariable Long id){
        Student deletedStudent = studentService.deleteStudentById(id);
        return ResponseEntity.ok(deletedStudent);
    }
}
