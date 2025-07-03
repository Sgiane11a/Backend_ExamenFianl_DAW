package com.tecsup.peliculas;

import com.tecsup.peliculas.entity.Usuario;
import com.tecsup.peliculas.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // Esta lógica previene que se creen usuarios duplicados cada vez que reinicias la app
        if (usuarioRepository.count() == 0) {

            // --- Crear usuario ADMIN ---
            Usuario admin = new Usuario(
                    "admin", // username
                    passwordEncoder.encode("admin123"), // contraseña CIFRADA
                    "ROLE_ADMIN" // rol
            );
            usuarioRepository.save(admin);

            // --- Crear usuario USER ---
            Usuario user = new Usuario(
                    "user", // username
                    passwordEncoder.encode("user123"), // contraseña CIFRADA
                    "ROLE_USER" // rol
            );
            usuarioRepository.save(user);

            // Mensaje en consola para confirmar que se crearon los usuarios
            System.out.println(">>> Usuarios de prueba ('admin' y 'user') han sido creados en la base de datos. <<<");
        }
    }
}