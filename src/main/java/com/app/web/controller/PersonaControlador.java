package com.app.web.controller;

import com.app.web.entity.Persona;
import com.app.web.service.PersonaServicio;
import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PersonaControlador {

    @Autowired
    private PersonaServicio personaServicio;

    @GetMapping({"/index", "/"})
    public String home() {
        return "index";
    }

    @GetMapping("/registro")
    public String mostrarFormularioDeRegistrarPersona(Model modelo) {
        Persona persona = new Persona();
        LocalDate date = LocalDate.now();
        modelo.addAttribute("persona", persona);
        modelo.addAttribute("date", date);
        return "registrar";
    }

    @PostMapping("/registro")
    public String guardarEstudiante(@Valid Persona persona, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "registrar";
        }
        personaServicio.agregarPersona(persona);
        return "/index";
    }

    @GetMapping("/buscar")
    public String mostrarFormularioParaBusquedasPorDni() {
        return "buscar";
    }

    @GetMapping("/buscar/{apellidoDni}")
    public String mostrarPorDniApellido(@RequestParam(value = "apellidoDni", defaultValue = "") String apellidoDni, Model modelo) {
        String mensajeError="";
        Long longDni = null;
        String regex = "^[0-9]+$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(apellidoDni);
        
        if(matcher.find()){
            longDni = Long.parseLong(apellidoDni);
            if (personaServicio.traerPersonasPorId(longDni).isEmpty()){
                mensajeError = "No existe ningun registro con ese numero de DNI";
                modelo.addAttribute("mensaje", mensajeError);
                return "/buscar";
            }
            modelo.addAttribute("personas", personaServicio.traerPersonasPorId(longDni));
            return "/index";
        }
        else{
            if (personaServicio.traerPorApellido(apellidoDni).isEmpty() || apellidoDni.length() < 3) {
                mensajeError = "No existe ningun registro cuyo apellido coincida con esas tres primeras letras";
                modelo.addAttribute("mensaje", mensajeError);
                return "/buscar";
            }
            modelo.addAttribute("personas", personaServicio.traerPorApellido(apellidoDni));
            return "/index";
        }
    }
    
    @GetMapping("/sexo/{id}")
    public String mostrarFormularioDeEditar(@PathVariable Long id, Model modelo) {
        modelo.addAttribute("persona", personaServicio.traerPersonaPorId(id));
        return "/editar";
    }

    //Me encontre con un problema para usar el metodo PUT
    //que es el correcto para peticiones de actualizacion
    //asi que por mi parte use POST.
    @PostMapping("/sexo/{id}")
    public String actualizarEstudiante(@PathVariable Long id, @ModelAttribute("persona") Persona persona, Model modelo) {
        
        personaServicio.cambiarSexoPorId(id,persona.getSexo());
        modelo.addAttribute("personas", personaServicio.traerPersonasPorId(id));
        return "/index";
    }

}
