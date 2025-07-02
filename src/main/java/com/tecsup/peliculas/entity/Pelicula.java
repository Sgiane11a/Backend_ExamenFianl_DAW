package com.tecsup.peliculas.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;
import java.time.LocalDate;

@Entity
@Table(name = "peliculas")
@Data
public class Pelicula {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "El título no puede estar vacío")
    @Size(min = 2, max = 100, message = "El título debe tener entre 2 y 100 caracteres")
    @Column(nullable = false, length = 100)
    private String titulo;

    @NotEmpty(message = "El director no puede estar vacío")
    @Column(nullable = false)
    private String director;

    @Column(name = "fecha_lanzamiento")
    private LocalDate fechaLanzamiento;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "genero_id", nullable = false)
    private Genero genero;
}