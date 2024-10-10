//Faltando cadastrarTrabalho

package com.cesar.projeto2.tccesar.interfaces;

import com.cesar.projeto2.tccesar.domain.Student;
import com.cesar.projeto2.tccesar.domain.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;


@Service
@RestController
@RequestMapping(path = "/students", produces = MediaType.APPLICATION_JSON_VALUE)
public class StudentAPI {

    @Autowired
    StudentRepository studentRepository;

    @GetMapping()
    public List<Student> listStudents () {
        return studentRepository.findAll();
    }

    @GetMapping("/{id}")
    public Student findStudent (@PathVariable("id") Long id) {
        return studentRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping()
    public Student createStudent (@RequestBody Student student) {
        List<Student> students = listStudents();
        for (Student studentInList : students) {
            if (studentInList.getEmail().equals(student.getEmail())) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            }
        }
        return studentRepository.save(student);
    }

    @PatchMapping("/{id}")
    public Student fullUpdateStudent (@PathVariable("id") Long id, @RequestBody Student student) {
        Student foundStudent = findStudent(id);
        foundStudent.setSenha(Optional.ofNullable(student.getSenha()).orElse(foundStudent.getSenha()));
        return studentRepository.save(foundStudent);
    }


}
