package ru.hogwarts.school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.FacultyService;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("faculty")
public class FacultyController {
    private final FacultyService facultyService;
    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }
    @PostMapping // POST http://localhost:8080/faculty
    public ResponseEntity<Faculty> addFaculty(@RequestBody Faculty faculty){
        Faculty addedFaculty = facultyService.addFaculty(faculty);
        return ResponseEntity.ok(addedFaculty);
    }
    @GetMapping("{id}") //GET http://localhost:8080/faculty/0
    public ResponseEntity<Faculty> getFacultyById(@PathVariable Long id){
        Faculty faculty = facultyService.getFacultyById(id);
        if (faculty == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(faculty);
    }
    @GetMapping("/all") // GET http://localhost:8080/faculty/all
    public ResponseEntity<Collection<Faculty>> getAllFaculties(){
        return ResponseEntity.ok(facultyService.getAllFaculties());
    }
    @PutMapping("/{id}") //PUT http://localhost:8080/faculty/1
    public ResponseEntity<Faculty> updateFaculty(@PathVariable("id") long id, @RequestBody Faculty faculty){
        Faculty updatedFaculty = facultyService.updateFaculty(id, faculty);
        return ResponseEntity.ok(updatedFaculty);
    }
    @DeleteMapping("{id}") // DELETE http://localhost:8080/faculty/0
    public ResponseEntity<Faculty> deleteFacultyById(@PathVariable Long id){
        Faculty deletedFaculty = facultyService.deleteFacultyById(id);
        return ResponseEntity.ok(deletedFaculty);
    }
    @GetMapping("/filter") // GET http://localhost:8080/faculty/filter?color=red&name=1
    public ResponseEntity<List<Faculty>> getFacultiesByColorOrName(@RequestParam(required = false) String color,@RequestParam(required = false) String name) {
        return ResponseEntity.ok(facultyService.getFacultiesByColorOrName(color, name));
    }

    @GetMapping("{id}/students") // GET http://localhost:8080/faculty/1/students
    public ResponseEntity<List<Student>> getStudentsByFacultyId(@PathVariable Long id){
        return ResponseEntity.ok(facultyService.getStudentsByFacultyId(id));
    }

}