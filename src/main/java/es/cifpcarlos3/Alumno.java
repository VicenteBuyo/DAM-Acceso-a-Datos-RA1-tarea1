package es.cifpcarlos3;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import tools.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@JacksonXmlRootElement(localName = "Alumno")
public class Alumno implements Serializable { //Implementa Serializable para poder serializar y deserializar

    @JsonProperty("Apellidos") //Etiqueta JSON para que aparezca el nombre del atributo con primera en mayúsculas
    private String apellidos;
    @JsonProperty("Nombre")
    private String nombre;
    @JsonProperty("Edad")
    private int edad;
    @JsonProperty("FechaRegistro")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy") //Etiqueta JSON para el formato de fecha
    private LocalDate fechaRegistro = LocalDate.now();
    @Serial
    private static final long serialVersionUID = 1L; //Versión de serialización

    //Constructor

    //No podemos usar AllArgsCostructor de Lombok porque nos obligaría a introducir el fechaRegistro, y queremos
    //que sea automático
    public Alumno(String apellidos, String nombre, int edad) {
        this.apellidos = apellidos;
        this.nombre = nombre;
        this.edad = edad;
    }


    //Override del toString para sacar los datos con formato limpio
    @Override
    public String toString() {
        return
                "Apellidos: " + apellidos +
                ", Nombre: " + nombre +
                ", Edad: " + edad +
                ", Fecha de Registro: " + fechaRegistro;
    }
}
