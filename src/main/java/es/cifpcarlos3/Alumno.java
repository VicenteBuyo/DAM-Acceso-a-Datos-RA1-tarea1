package es.cifpcarlos3;

import lombok.*;

import java.time.LocalDateTime;

@Data

public class Alumno {
    private String apellidos;
    private String nombre;
    private int edad;
    private LocalDateTime fechaRegistro = LocalDateTime.now();

    public Alumno(String apellidos, String nombre, int edad) {
        this.apellidos = apellidos;
        this.nombre = nombre;
        this.edad = edad;
    }

}
