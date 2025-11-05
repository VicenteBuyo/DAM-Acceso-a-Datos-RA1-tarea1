package es.cifpcarlos3;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {

        Path ficheroEntradaDAW = Path.of("lista_alumnado_DAW1.csv");
        ArrayList<Alumno> alumnosDAW = new ArrayList<>();



        try (BufferedReader br = Files.newBufferedReader(ficheroEntradaDAW, StandardCharsets.UTF_8)){
            String linea = br.readLine();

            while (linea != null) {
                String filtroCartagena = "cartagena";
                if (linea.toLowerCase().contains(filtroCartagena)) {

                    String[] lineaPartes = linea.split(";");

                    Alumno a = new Alumno(lineaPartes[1].trim(), lineaPartes[2].trim(), Integer.parseInt(lineaPartes[4].trim()));
                    alumnosDAW.add(a);
                    System.out.println(a);
                }

                linea = br.readLine();

            }
            System.out.println(alumnosDAW);

        } catch (IOException e){
            System.out.println(e.getMessage() + "No se puede cargar el archivo");
        }


    }
}