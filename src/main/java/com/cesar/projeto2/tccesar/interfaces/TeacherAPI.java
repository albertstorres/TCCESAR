//Faltando cadastrarFeedback, listarTrabalhos, excluirTrabalho, excluirFeedback

package com.cesar.projeto2.tccesar.interfaces;

import com.cesar.projeto2.tccesar.domain.Teacher;
import com.cesar.projeto2.tccesar.domain.TeacherRepository;
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
@RequestMapping (path = "/teachers", produces = MediaType.APPLICATION_JSON_VALUE)
public class TeacherAPI {

    @Autowired
    TeacherRepository teacherRepository;

    @GetMapping ()
    public List<Teacher> listTeachers () {
        return teacherRepository.findAll();
    }

    @GetMapping ("/{id}")
    public Teacher findTeacher (@PathVariable("id") Long id) {
        return teacherRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping()
    public Teacher createTeacher (@RequestBody Teacher teacher) {

        List<Teacher> teachersList = listTeachers();
        for (Teacher teacherInList : teachersList) {
            if (teacherInList.getEmail().equals(teacher.getEmail())) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            }
        }

        return teacherRepository.save(teacher);
    }

    @PatchMapping("/{id}")
    public Teacher updateTeacher (@PathVariable("id") Long id, @RequestBody Teacher teacher) {
        Teacher foundTeacher = findTeacher(id);

        foundTeacher.setNome(Optional.ofNullable(teacher.getNome()).orElse(foundTeacher.getNome()));
        foundTeacher.setCurso(Optional.ofNullable(teacher.getCurso()).orElse(foundTeacher.getCurso()));
        foundTeacher.setSenha(Optional.ofNullable(teacher.getSenha()).orElse(foundTeacher.getSenha()));

        return teacherRepository.save(foundTeacher);
    }
}
