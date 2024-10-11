package com.cesar.projeto2.tccesar.interfaces;

import com.cesar.projeto2.tccesar.domain.CoordinationRepository;
import com.cesar.projeto2.tccesar.domain.Coordination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RequestMapping (path = "/coordinations", produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
public class CoordinationAPI {

    @Autowired
    CoordinationRepository coordinationRepository;

    @GetMapping ()
    public List<Coordination> listCoordination () {
        return coordinationRepository.findAll();
    }

    @GetMapping ("/{id}")
    public Coordination findCoordinator (@PathVariable("id") Long id) {
        return coordinationRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping ()
    public Coordination createCoordinator (@RequestBody Coordination coordinator) {
        List<Coordination> coordinationList = listCoordination();
        for (Coordination coordinationinList : coordinationList) {
            if (coordinationinList.getEmail().equals(coordinator.getEmail())) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            }
        }

        return coordinationRepository.save(coordinator);
    }

    @PutMapping("/{id}")
    public Coordination updateFullCoordinator (@PathVariable("id") Long id, @RequestBody Coordination coordinator) {
        Coordination foundCoordinator = findCoordinator(id);

        foundCoordinator.setNome(coordinator.getNome());
        foundCoordinator.setCurso(coordinator.getCurso());
        foundCoordinator.setEmail(coordinator.getEmail());
        foundCoordinator.setSenha(coordinator.getSenha());

        return coordinationRepository.save(foundCoordinator);
    }

    @PatchMapping("/{id}")
    public Coordination updateCoordinator (@PathVariable("id") Long id, @RequestBody Coordination coordenator) {
        Coordination foundCoordenator = findCoordinator(id);

        foundCoordenator.setNome(Optional.ofNullable(coordenator.getNome()).orElse(foundCoordenator.getNome()));
        foundCoordenator.setCurso(Optional.ofNullable(coordenator.getCurso()).orElse(foundCoordenator.getCurso()));
        foundCoordenator.setEmail(Optional.ofNullable(coordenator.getEmail()).orElse(foundCoordenator.getEmail()));
        foundCoordenator.setSenha(Optional.ofNullable(coordenator.getSenha()).orElse(foundCoordenator.getSenha()));

        return coordinationRepository.save(foundCoordenator);
    }

    @DeleteMapping("/{id}")
    public void deleteCoordinator (@PathVariable("id") Long id) {
        Coordination foundCoordinator = findCoordinator(id);

        coordinationRepository.deleteById(id);
    }

 }
