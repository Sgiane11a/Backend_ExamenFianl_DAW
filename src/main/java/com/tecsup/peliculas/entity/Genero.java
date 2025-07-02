package com.tecsup.peliculas.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "generos")
@Data
public class Genero {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String nombre;
}