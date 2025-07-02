package com.tecsup.peliculas.controller;

import com.tecsup.peliculas.entity.Genero;
import com.tecsup.peliculas.service.GeneroService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/generos")
@Tag(name = "Gestión de Géneros", description = "API para operaciones con géneros")
@SecurityRequirement(name = "bearerAuth")
public class GeneroController {

    @Autowired
    private GeneroService generoService;

    @GetMapping
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @Operation(summary = "Listar todos los géneros")
    public List<Genero> getAllGeneros() {
        return generoService.findAll();
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Crear un nuevo género (Solo ADMIN)")
    public ResponseEntity<Genero> createGenero(@Valid @RequestBody Genero genero) {
        Genero nuevoGenero = generoService.save(genero);
        return new ResponseEntity<>(nuevoGenero, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Eliminar un género (Solo ADMIN)")
    public ResponseEntity<Void> deleteGenero(@PathVariable Long id) {
        generoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}