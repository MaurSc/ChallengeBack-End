package com.app.web.controller;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.app.web.dto.Mensaje;
import com.app.web.entity.Persona;
import com.app.web.service.PersonaServicio;

@RestController
@RequestMapping("/api_registro")
public class PersonaRestControlador {

    @Autowired
    private PersonaServicio personaServicio;

    @PostMapping("/registro")
    public ResponseEntity<Persona> createPerson(@Valid @RequestBody Persona persona, BindingResult bindingResult) {
        if (!personaServicio.sonLetras(persona)) {
            return new ResponseEntity(new Mensaje("Podrias no estar introduciendo letras o bien introduciendo un sexo que no este contemplado"), HttpStatus.BAD_REQUEST);
        }
        if (bindingResult.hasErrors()) {
            return new ResponseEntity(new Mensaje("Has introducido un campo de manera erronea"), HttpStatus.BAD_REQUEST);
        }

        personaServicio.agregarPersona(persona);
        return new ResponseEntity(new Mensaje("Recurso creado correctamente"), HttpStatus.OK);
    }

    @GetMapping("/registro/{apellidoDni}")
    public ResponseEntity<List<Persona>> traerPersonasPorApellido(@PathVariable String apellidoDni) {

        Mensaje mensajeError = new Mensaje("");
        Long longDni = null;
        String regex = "^[0-9]+$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(apellidoDni);

        if (matcher.find()) {
            longDni = Long.parseLong(apellidoDni);
            List<Persona> list = personaServicio.TraerPersonasPorId(longDni);
            if (list == null) {
                mensajeError.setMensaje("No existe ningun registro con ese numero de DNI");
                return new ResponseEntity(mensajeError, HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity(list, HttpStatus.OK);
        } else {
            if (personaServicio.traerPorApellido(apellidoDni).isEmpty() || apellidoDni.length() < 3) {
                return new ResponseEntity(new Mensaje("Verifica que hayas ingresado los 3 primeros caracteres o que bien que sean los correctos"), HttpStatus.BAD_REQUEST);
            }
            List<Persona> list = personaServicio.traerPorApellido(apellidoDni.toLowerCase());
            return new ResponseEntity(list, HttpStatus.OK);
        }
    }

    @PutMapping("/registro/{dni}")
    public ResponseEntity<?> actualizarSexo(@PathVariable Long dni,
            @RequestParam String sexo) {
        if (!personaServicio.esUnSexoContemplado(sexo)) {
            return new ResponseEntity(new Mensaje("Solo se admite Femenino/Masculino/Otro"), HttpStatus.BAD_REQUEST);
        }
        if (personaServicio.TraerPersonasPorId(dni) == null) {
            return new ResponseEntity(new Mensaje("El recurso no existe"), HttpStatus.NOT_FOUND);
        }

        personaServicio.cambiarSexoPorId(dni, sexo);
        return new ResponseEntity(new Mensaje("El recurso se actualizo correctamente"), HttpStatus.OK);
    }

}
