package es.cifpcarlos3;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.*;
import tools.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import tools.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import tools.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import java.io.Serializable;
import java.util.ArrayList;

@Data
@NoArgsConstructor
@JsonPropertyOrder({"nombre", "alumnos"}) //Etiqueta JSON para el orden de elementos listados
@JacksonXmlRootElement(localName = "Curso")

public class Curso implements Serializable {
    @JacksonXmlProperty(isAttribute = true)
    @JsonProperty("Nombre") //Etiqueta JSON para que aparezca el nombre del atributo con primera en mayúsculas
    private String nombre;
    @JacksonXmlElementWrapper(localName = "Alumnos")
    @JsonProperty("Alumnos")
    @JacksonXmlProperty(localName = "Alumno")
    private ArrayList<Alumno> alumnos = new ArrayList<>();

    //Constructor
    public Curso(String nombre) {
        this.nombre = nombre;
    }

    //Método para añadir alumnos al curso
    public void anadirAlumnos(ArrayList<Alumno> a){
        this.alumnos.addAll(a);
    }
}
