package com.tecsup.peliculas.service;

import com.tecsup.peliculas.entity.Genero;
import com.tecsup.peliculas.exception.ResourceNotFoundException;
import com.tecsup.peliculas.repository.GeneroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GeneroService {

    @Autowired
    private GeneroRepository generoRepository;

    public List<Genero> findAll() {
        return generoRepository.findAll();
    }

    public Genero save(Genero genero) {
        return generoRepository.save(genero);
    }

    public Genero findById(Long id) {
        return generoRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Género no encontrado con id: " + id));
    }

    public void deleteById(Long id) {
        if (!generoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Género no encontrado con id: " + id);
        }
        generoRepository.deleteById(id);
    }
}