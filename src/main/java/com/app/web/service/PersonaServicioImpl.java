package com.app.web.service;

import com.app.web.entity.Persona;
import com.app.web.repository.PersonaRepositorio;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PersonaServicioImpl implements PersonaServicio {
    
    @Autowired
    private PersonaRepositorio personaRepo;
    
    @Override
    public List<Persona> traerPersonasPorId(Long id) {
        
        return personaRepo.findByDni(id);
    }
    @Override
    public Persona traerPersonaPorId(Long id) {
        Persona p = personaRepo.getById(id);
        return p;
    }

    @Override
    public void agregarPersona(Persona persona) {
        personaRepo.save(persona);
    }

    @Override
    public void cambiarSexoPorId(Long id, String sexo) {
        Persona p = personaRepo.findById(id).orElse(null);
        p.setSexo(sexo);
        personaRepo.save(p);
    }

    @Override
    public List<Persona> traerPorApellido(String apellido) {
        
        return personaRepo.findByApellidoStartingWith(apellido);
    }
    
    @Override
    public boolean sonLetras(Persona persona) {
        String regex = "^[a-zA-Z ]+$";
        boolean esSexo = this.esUnSexoContemplado(persona.getSexo());
        Pattern pattern = Pattern.compile(regex,Pattern.CASE_INSENSITIVE);
        Matcher matcherNombre = pattern.matcher(persona.getNombre());
        Matcher matcherApellido = pattern.matcher(persona.getApellido());
        Matcher matcherSexo = pattern.matcher(persona.getSexo());
        
        return (matcherNombre.find()) && (matcherApellido.find()) && (matcherSexo.find() && esSexo);
    }
    
    @Override
    public boolean esUnSexoContemplado(String sexo) {
        String regex = "^(masculino|femenino|otro)$";
        Pattern pattern = Pattern.compile(regex,Pattern.CASE_INSENSITIVE);
        Matcher matcherSexo = pattern.matcher(sexo.toLowerCase());
        return matcherSexo.find();
    }
}
