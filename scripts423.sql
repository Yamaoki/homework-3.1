SELECT student.name, student.age, faculty.name
FROM student
         INNER JOIN faculty on student.faculty_id = faculty.id;

SELECT student.name, student.age, faculty.name, avatar.data
FROM student
         INNER JOIN faculty on student.faculty_id = faculty.id
         INNER JOIN avatar on student.id = avatar.student_id
WHERE student_id IS NOT NULL