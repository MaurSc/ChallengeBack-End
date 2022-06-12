package com.app.web.entity;

import java.io.Serializable;
import java.sql.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Entity
@Table(name = "persona")
public class Persona implements Serializable{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dni")
    private Long dni;
    
    @Column(name = "nombre")
    @NotNull(message="El campo es obligatorio")
    @Pattern(regexp = "^[a-zA-Z ]+$", message="Solo puede contener letras")
    private String nombre;
    
    @Column(name = "apellido")
    @NotNull(message="El campo es obligatorio")
    @Pattern(regexp = "^[a-zA-Z ]+$", message="Solo puede contener letras")
    private String apellido;
    
    @Column(name = "fechaNac")
    @NotNull(message="El campo es obligatorio")
    private Date fechaNacimiento;
    
    @Column(name = "sexo")
    @NotNull(message="Selecciona una opcion")
    private String sexo;
    

    public Persona() {
    }

    public Persona(String nombre, String apellido, String sexo, Date fechaNacimiento) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.sexo = sexo;
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }
    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public Long getDni() {
        return dni;
    }

    public void setDni(Long dni) {
        this.dni = dni;
    }
}
