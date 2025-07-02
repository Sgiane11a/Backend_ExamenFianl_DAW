package com.tecsup.peliculas.controller;

import com.tecsup.peliculas.entity.Pelicula;
import com.tecsup.peliculas.service.PeliculaService;
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
@RequestMapping("/api/peliculas")
@Tag(name = "Gestión de Películas", description = "API para operaciones CRUD de películas")
@SecurityRequirement(name = "bearerAuth")
public class PeliculaController {

    @Autowired
    private PeliculaService peliculaService;

    @GetMapping
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @Operation(summary = "Listar todas las películas")
    public List<Pelicula> getAllPeliculas() {
        return peliculaService.findAll();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @Operation(summary = "Obtener una película por ID")
    public ResponseEntity<Pelicula> getPeliculaById(@PathVariable Long id) {
        Pelicula pelicula = peliculaService.findById(id);
        return ResponseEntity.ok(pelicula);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Crear una nueva película (Solo ADMIN)")
    public ResponseEntity<Pelicula> createPelicula(@Valid @RequestBody Pelicula pelicula) {
        Pelicula nuevaPelicula = peliculaService.save(pelicula);
        return new ResponseEntity<>(nuevaPelicula, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Actualizar una película existente (Solo ADMIN)")
    public ResponseEntity<Pelicula> updatePelicula(@PathVariable Long id, @Valid @RequestBody Pelicula peliculaDetails) {
        Pelicula pelicula = peliculaService.findById(id);
        pelicula.setTitulo(peliculaDetails.getTitulo());
        pelicula.setDirector(peliculaDetails.getDirector());
        pelicula.setFechaLanzamiento(peliculaDetails.getFechaLanzamiento());
        pelicula.setGenero(peliculaDetails.getGenero());
        Pelicula peliculaActualizada = peliculaService.save(pelicula);
        return ResponseEntity.ok(peliculaActualizada);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Eliminar una película (Solo ADMIN)")
    public ResponseEntity<Void> deletePelicula(@PathVariable Long id) {
        peliculaService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}