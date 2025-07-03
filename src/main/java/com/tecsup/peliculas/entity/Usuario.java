package com.tecsup.peliculas.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "usuarios") // Esto creará o usará una tabla llamada "usuarios"
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    // Guardaremos el rol como un String, ej: "ROLE_ADMIN" o "ROLE_USER"
    @Column(nullable = false)
    private String role;

    // Constructor para facilitar la creación de usuarios
    public Usuario(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }
}