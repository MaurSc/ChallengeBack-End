package com.app.web.repository;

import com.app.web.entity.Persona;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonaRepositorio extends JpaRepository<Persona, Long> {
    List<Persona> findByApellidoStartingWith(String apellido);
    List<Persona> findByDni(Long dni);
    
}
