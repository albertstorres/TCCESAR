package com.cesar.projeto2.tccesar.domain;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Teacher {
    @Id
    @GeneratedValue
    Long id;
    String nome;
    String curso;
    String email;
    String senha;
}
