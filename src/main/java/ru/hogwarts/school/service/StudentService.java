package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.exception.FacultyNotFoundException;
import ru.hogwarts.school.exception.StudentNotFoundException;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.AvatarRepository;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

@Service
public class StudentService {
    private final StudentRepository studentRepository;
    private final AvatarRepository avatarRepository;
    private Integer currentStudent = 0;
    private final Logger logger = LoggerFactory.getLogger(StudentService.class);
    public StudentService(StudentRepository studentRepository, AvatarRepository avatarRepository) {
        this.studentRepository = studentRepository;
        this.avatarRepository = avatarRepository;
    }
    public Student addStudent(Student student) {
        logger.info("Was invoked method for create student");
        return studentRepository.save(student);
    }
    public Student getStudentById(Long id) {
        logger.info("Was invoked method for find student by id");
        return studentRepository.findById(id).orElseThrow(() -> new FacultyNotFoundException(id));
    }
    public Collection<Student> getAllStudents() {
        logger.info("Was invoked method for find all students");
        return studentRepository.findAll();
    }

    public Student updateStudent(Long id, Student student) {
        logger.info("Was invoked method for edit student");
        studentRepository.findById(id).orElseThrow(() -> new StudentNotFoundException(id));
        Student oldStudent = new Student(id);
        oldStudent.setId(id);
        oldStudent.setAge(student.getAge());
        oldStudent.setName(student.getName());
        return studentRepository.save(oldStudent);
    }
    public void deleteStudentById(Long id) {
        logger.info("Was invoked method for delete student by id");
        studentRepository.deleteById(id);
    }

    public List<Student> getStudentsByAgeBetween(int ageFrom, int ageTo) {
        logger.info("Was invoked method for find students by age between two digits");
        return studentRepository.getStudentsByAgeBetween(ageFrom, ageTo);
    }

    public Faculty getFacultyByStudentId(Long id) {
        logger.info("Was invoked method for find faculty by student id");
        logger.debug("Requesting find faculty by student id: {}", id);
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new FacultyNotFoundException(id));
        return student.getFaculty();
    }
    public Student getStudentInfo(Long id) {
        logger.info("Was invoked method for info by student id");
        studentRepository.findById(id).orElseThrow(() -> new StudentNotFoundException(id));
        Student student = new Student(id);
        student.getId();
        student.getAge();
        student.getName();
        return student;
    }
    public Avatar findAvatar(long id) {
        logger.info("Was invoked method for find avatar by student id");
        return avatarRepository.getAvatarByStudentId(id).orElseThrow();
    }
    public Integer getStudentsAmount(){
        logger.info("Was invoked method for amount student id");
        return studentRepository.getStudentsAmount();
    }

    public Integer getStudentsAverageAge(){
        logger.info("Was invoked method for get average age from all students");
        return studentRepository.getStudentsAverageAge();
    }

    public List<Student> getStudentsLastFive() {
        logger.info("Was invoked method for get five last students");
        return studentRepository.getStudentsLastFive();
    }
    public Collection<String> getAllStudentsStartingLetterA() {
        logger.info("Was invoked method for get all student names, starting letter A");
        Collection<Student> students = this.getAllStudents();
        return students.stream()
                .map(e -> e.getName().toUpperCase(Locale.ROOT))
                .filter(e -> e.startsWith("A"))
                .sorted()
                .toList();
    }

    public Double getAverageAge() {
        logger.info("Was invoked method for get average age for all students");
        Collection<Student> students = this.getAllStudents();
        return students.stream()
                .mapToInt(e -> e.getAge())
                .average()
                .orElse(0);
    }
    public void printFewStudentsThreads() {
        logger.info("Was invoked method for print few students in different threads");
        List<Student> students = new ArrayList<>(this.getAllStudents());

        new Thread(() -> {
            printStudent(students, "Поток 1", 2);
            printStudent(students, "Поток 1", 3);
        }).start();

        new Thread(() -> {
            printStudent(students, "Поток 2", 4);
            printStudent(students, "Поток 2", 5);
        }).start();

        printStudent(students, "Поток 0", 0);
        printStudent(students, "Поток 0", 1);
    }

    private void printStudent(List<Student> students, String message, int number) {
        System.out.println(message + ": #" + number + ": " + students.get(number).getName());
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void printFewStudentsThreadsSync() {
        logger.info("Was invoked method for print few students in different synchronized threads");
        List<Student> students = new ArrayList<>(this.getAllStudents());

        Thread thread1 = new Thread(() -> {
            printStudentSync(students, "Поток 1", 2);
            printStudentSync(students, "Поток 1", 3);
        });
        thread1.start();

        Thread thread2 = new Thread(() -> {
            printStudentSync(students, "Поток 2", 4);
            printStudentSync(students, "Поток 2", 5);
        });
        thread2.start();

        printStudentSync(students, "Поток 0", 0);
        printStudentSync(students, "Поток 0", 1);
    }

    private void printStudentSync(List<Student> students, String message, int number) {
        while (currentStudent != number) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        synchronized (currentStudent) {
            System.out.println(message + ": #" + number + ": " + students.get(number).getName());
            currentStudent++;
        }
    }
}