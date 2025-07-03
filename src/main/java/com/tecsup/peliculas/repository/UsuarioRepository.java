package com.tecsup.peliculas.repository;

import com.tecsup.peliculas.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    // Spring Data JPA crea la consulta automáticamente por el nombre del método.
    // Este método es crucial para que Spring Security pueda encontrar un usuario.
    Optional<Usuario> findByUsername(String username);

}