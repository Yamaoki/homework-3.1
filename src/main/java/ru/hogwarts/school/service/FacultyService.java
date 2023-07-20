package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.exception.FacultyNotFoundException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

@Service
public class FacultyService {
    private final FacultyRepository facultyRepository;
    private final StudentRepository studentRepository;
    private final Logger logger = LoggerFactory.getLogger(FacultyService.class);

    public FacultyService(FacultyRepository facultyRepository, StudentRepository studentRepository) {
        this.facultyRepository = facultyRepository;
        this.studentRepository = studentRepository;
    }
    public Faculty addFaculty(Faculty faculty) {
        logger.info("Was invoked method for create faculty");
        return facultyRepository.save(faculty);
    }
    public Faculty getFacultyById(Long id) {
        logger.info("Was invoked method for find faculty");
        logger.debug("Requesting find faculty by id {}", id);
        return facultyRepository.findById(id).orElseThrow(() -> new FacultyNotFoundException(id));
    }
    public Collection<Faculty> getAllFaculties() {
        logger.info("Was invoked method for find all faculties");
        return facultyRepository.findAll();
    }
    public Faculty updateFaculty(Long id, Faculty faculty) {
        logger.info("Was invoked method for edit faculty");
        facultyRepository.findById(id).orElseThrow(() -> new FacultyNotFoundException(id));
        Faculty oldFaculty = new Faculty();
        oldFaculty.setId(id);
        oldFaculty.setColor(faculty.getColor());
        oldFaculty.setName(faculty.getName());
        return facultyRepository.save(oldFaculty);
    }
    public Faculty deleteFacultyById(Long id) {
        logger.info("Was invoked method for delete faculty");
        Faculty faculty = facultyRepository.findById(id)
                .orElseThrow(() -> new FacultyNotFoundException(id));
        facultyRepository.deleteById(id);
        return faculty;
    }
    public List<Faculty> getFacultiesByColorOrName(String color, String name) {
        logger.info("Was invoked method for find faculties by name or color");
        return facultyRepository.getFacultiesByColorIgnoreCaseOrNameIgnoreCase(color, name);
    }
    public List<Student> getStudentsByFacultyId(Long id){
        logger.info("Was invoked method for find students by faculty id");
        return studentRepository.getStudentsByFaculty_Id(id);
    }
    public String getFacultyNameWithLongestName() {
        logger.info("Was invoked method for find longest faculty name");
        return getAllFaculties().stream()
                .map(e -> e.getName())
                .reduce("", (a, b) -> a.length() > b.length() ? a : b);
    }

    public ResponseEntity<Integer> calculateFormula() {
        logger.info("Was invoked method for calculate formula from task");

        long start = System.nanoTime();
        Integer result = Stream.iterate(1, a -> a + 1)
                .limit(1_000_000)
                .parallel()
                .reduce(0, (a, b) -> a + b);
        long finish = System.nanoTime();
        long elapsed = finish - start;

        return ResponseEntity.ok(result);
    }
}