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
    @PostMapping
    public ResponseEntity<Faculty> addFaculty(@RequestBody Faculty faculty){
        Faculty addedFaculty = facultyService.addFaculty(faculty);
        return ResponseEntity.ok(addedFaculty);
    }
    @GetMapping("{id}")
    public ResponseEntity<Faculty> getFacultyById(@PathVariable Long id){
        Faculty faculty = facultyService.getFacultyById(id);
        if (faculty == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(faculty);
    }
    @GetMapping("/all")
    public ResponseEntity<Collection<Faculty>> getAllFaculties(){
        return ResponseEntity.ok(facultyService.getAllFaculties());
    }
    @PutMapping("/{id}")
    public ResponseEntity<Faculty> updateFaculty(@PathVariable("id") long id, @RequestBody Faculty faculty){
        Faculty updatedFaculty = facultyService.updateFaculty(id, faculty);
        return ResponseEntity.ok(updatedFaculty);
    }
    @DeleteMapping("{id}")
    public ResponseEntity<Faculty> deleteFacultyById(@PathVariable Long id){
        Faculty deletedFaculty = facultyService.deleteFacultyById(id);
        return ResponseEntity.ok(deletedFaculty);
    }
    @GetMapping("/filter")
    public ResponseEntity<List<Faculty>> getFacultiesByColorOrName(@RequestParam(required = false) String color,@RequestParam(required = false) String name) {
        return ResponseEntity.ok(facultyService.getFacultiesByColorOrName(color, name));
    }

    @GetMapping("{id}/students")
    public ResponseEntity<List<Student>> getStudentsByFacultyId(@PathVariable Long id){
        return ResponseEntity.ok(facultyService.getStudentsByFacultyId(id));
    }
    @GetMapping("longest-name")
    public ResponseEntity<String> getFacultyNameWithLongestName() {
        String facultyName = facultyService.getFacultyNameWithLongestName();
        return ResponseEntity.ok(facultyName);
    }
    @GetMapping("step-4")
    public ResponseEntity<Integer> calculateFormula() {
        return facultyService.calculateFormula();
    }
}