package com.tecsup.peliculas.service;

import com.tecsup.peliculas.entity.Pelicula;
import com.tecsup.peliculas.exception.ResourceNotFoundException;
import com.tecsup.peliculas.repository.PeliculaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PeliculaService {

    @Autowired
    private PeliculaRepository peliculaRepository;

    public List<Pelicula> findAll() {
        return peliculaRepository.findAll();
    }

    public Pelicula findById(Long id) {
        return peliculaRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Película no encontrada con id: " + id));
    }

    public Pelicula save(Pelicula pelicula) {
        return peliculaRepository.save(pelicula);
    }

    public void deleteById(Long id) {
        if (!peliculaRepository.existsById(id)) {
            throw new ResourceNotFoundException("Película no encontrada con id: " + id);
        }
        peliculaRepository.deleteById(id);
    }
}