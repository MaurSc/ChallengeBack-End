package com.app.web.service;

import com.app.web.entity.Persona;
import java.util.List;

public interface PersonaServicio {
    
    public List<Persona> TraerPersonasPorId(Long id);
    
    public Persona TraerPersonaPorId(Long id);
    
    public void agregarPersona(Persona persona);
    
    public void cambiarSexoPorId(Long id,String sexo);

    public List<Persona> traerPorApellido(String apellido);
    
    public boolean sonLetras(Persona persona);
    
    public boolean esUnSexoContemplado(String sexo);
}
