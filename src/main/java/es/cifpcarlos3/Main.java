package es.cifpcarlos3;

import tools.jackson.databind.SerializationFeature;
import tools.jackson.databind.json.JsonMapper;
import tools.jackson.dataformat.xml.XmlMapper;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {

        //Creamos los Path de entrada con los que vamos a trabajar
        Path ficheroEntradaDAW = Path.of("lista_alumnado_DAW1.csv");
        Path ficheroEntradaDAM = Path.of("lista_alumnado_DAM2.txt");

        //Creamos un array para todos los cursos
        ArrayList<Curso> cursos = new ArrayList<>();

        //Creamos dos cursos
        Curso cursoDAW = new Curso("DAW1");
        Curso cursoDAM = new Curso("DAM2");

        //Añadimos ambos cursos al array de cursos
        cursos.add(cursoDAW);
        cursos.add(cursoDAM);

        //Creamos un array de alumnos para cada curso, con el método que definimos para ello
        ArrayList<Alumno> alumnosDAM = extraerAlumnos(ficheroEntradaDAM);
        ArrayList<Alumno> alumnosDAW = extraerAlumnos(ficheroEntradaDAW);

        //Añadimos los alumnos extraídos a cada curso correspondiente
        cursoDAW.anadirAlumnos(alumnosDAW);
        cursoDAM.anadirAlumnos(alumnosDAM);

        //Escribimos toda la información de los cursos en un fichero .dat
        escribirBinario(cursos);

        //Leemos la información que hemos escrito en binario .dat
        leerBinario();

        escribirJSON(cursos);
        leerJSON();

        escribirXML(cursos);
        leerXML();


     }

     //Método para extraer los alumnos del fichero de entrada
    public static ArrayList<Alumno> extraerAlumnos(Path entrada) {
        ArrayList<Alumno> alumnos = new ArrayList<>();

        String separador = "";

        //Cambiamos el separador según el tipo de archivo
        if (entrada.toString().endsWith("txt")) {
            separador = ",";
        } else if (entrada.toString().endsWith("csv")) {
            separador = ";";
        }

        System.out.println("=============================");
        System.out.println("Extrayendo datos del fichero indicado...");
        System.out.println("=============================");

        //Abrimos un buffered reader para leer los datos del fichero de entrada
        try (BufferedReader br = Files.newBufferedReader(entrada, StandardCharsets.UTF_8)){
            String linea;

            //Mientras que la línea no esté vacía...
            while ((linea = br.readLine()) != null) {
                //Casteamos las líneas que contengan "cartagena"
                if (linea.toLowerCase().contains("cartagena")) {

                    //Separamos la línea con el separador indicado
                    String[] lineaPartes = linea.split(separador);

                    //Creamos un alumno por cada línea, con los campos que nos interesan
                    Alumno a = new Alumno(lineaPartes[1].trim(), lineaPartes[2].trim(), Integer.parseInt(lineaPartes[4].trim()));
                    alumnos.add(a); //Añadimos cada alumno al array del método
                }
            }

            //Retroalimentación
            System.out.println("=============================");
            System.out.println("Alumnos extraídos del archivo: " + alumnos.size());
            System.out.println("=============================");

            for (Alumno a : alumnos) {
                System.out.println(a.toString());
            }
            System.out.println("=============================");
            System.out.println();


        } catch (IOException e) {
            System.out.println(e.getMessage() + "No se puede cargar el archivo");
        }

        return alumnos; //Devolvemos el array
    }

    //Método para serializar a binario
    public static void escribirBinario(Object o){
        Path salida = Path.of("salida"); //Definimos la ruta de salida
        Path archivoSalida = salida.resolve("cursos.dat"); //Definimos el archivo de salida

        System.out.println("=============================");
        System.out.println("Escribiendo datos en fichero.dat ...");
        System.out.println("=============================");

        //Comprobamos si la carpeta de salida existe, y sino la creamos
        try {
            if (!Files.exists(salida)) {
                Files.createDirectories(salida);
            }
        } catch (IOException e) {
            System.out.println("No se pudo crear el archivo de datos" + e.getMessage());
        }

        //Usamos un Object OutputStream para serializar
        try (var oos = new ObjectOutputStream(Files.newOutputStream(archivoSalida))){
            oos.writeObject(o); //Escribimos el objeto recibido por parámetro

            //Retroalimentación
            System.out.println("=============================");
            System.out.println("Fichero .dat escrito exitosamente.");
            System.out.println("=============================");
        } catch (IOException e){
            System.out.println(e.getMessage() + "No se puede escribir el archivo");
        }
        System.out.println();
    }

    //Método para leer el archivo binario guardado anteriormente
    public static void leerBinario(){
        Path salida = Path.of("salida"); //Definimos la ruta de entrada
        Path archivoEntrada = salida.resolve("cursos.dat"); //Definimos el archivo de entrada

        System.out.println("=============================");
        System.out.println("Leyendo el fichero .dat...");
        System.out.println("=============================");

        //Usamos un ObjectInputStream para leer el archivo de entrada
        try (var ois = new ObjectInputStream(Files.newInputStream(archivoEntrada))){

            //Creamos un arraylist donde almacenados el contenido que leemos
            @SuppressWarnings("unchecked")
            var cursosLeidos = (ArrayList<Curso>) ois.readObject();

            //For Each para cada curso
            for (Curso c : cursosLeidos) {
                //Informamos del nombre y el número de alumnos
                System.out.println("Curso: " + c.getNombre() + " Nº alumnos: " + c.getAlumnos().size());

                //For Each para cada alumno del curso
                for (Alumno a : c.getAlumnos()) {
                    //ToString de alumno
                    System.out.println(" " + a);
                }

                System.out.println("=============================");
            }

        } catch (IOException | ClassNotFoundException e){
            System.out.println(e.getMessage() + "No se puede leer el archivo");
        }
        System.out.println();
    }

    //Método para serializar con JSON
    public static void escribirJSON(ArrayList<Curso> a){
        Path salida = Path.of("salida");
        Path archivoSalida = salida.resolve("cursos.JSON");

        //Comprobamos si la carpeta de salida existe, y sino la creamos
        try {
            if (!Files.exists(salida)) {
                Files.createDirectories(salida);
            }
        } catch (IOException e) {
            System.out.println("No se pudo crear el archivo de datos" + e.getMessage());
        }

        System.out.println("=============================");
        System.out.println("Escribiendo el archivo JSON...");
        System.out.println("=============================");

        // Usamos FileBufferedWritter
        try (var fbw = Files.newBufferedWriter(archivoSalida, StandardCharsets.UTF_8)){
            var mapper = JsonMapper.builder() //Creamos el Mapper de JSON para escribir el archivo
                    .enable(SerializationFeature.INDENT_OUTPUT) //Establecemos la salida indentada al serializar
                    .build();

            mapper.writeValue(fbw, a); // Escribimos el archivo

            System.out.println("=============================");
            System.out.println("Archivo JSON escrito exitosamente.");
            System.out.println("=============================");

        } catch (IOException e) {
            System.out.println(e.getMessage() + "No se puede escribir el archivo");
        }
        System.out.println();
    }

    //Método para leer el archivo JSON
    public static void leerJSON(){
        Path salida = Path.of("salida");
        Path archivoEntrada = salida.resolve("cursos.json");

        System.out.println("=============================");
        System.out.println("Leyendo el archivo JSON...");
        System.out.println("=============================");

        // Usamos FileBufferedReader para leer el archivo
        try (var fbr = Files.newBufferedReader(archivoEntrada, StandardCharsets.UTF_8)){

            var mapper = JsonMapper.builder() //Creamos el Mapper JSON para leer el archivo
                    .build();

           Curso[] cursosLeidos = mapper.readValue(fbr, Curso[].class); //Leemos el archivo

            //Para cada curso...
            for (Curso c : cursosLeidos) {
                System.out.println("Curso: " + c.getNombre()); //Sacamos su nombre
                for (Alumno a : c.getAlumnos()) { //Para cada alumno...
                    System.out.println(a.toString()); //Llamamos a su toString
                }
            }
            System.out.println("=============================");
            System.out.println("Archivo JSON leído exitosamente.");
            System.out.println("=============================");
        } catch (IOException e){
            System.out.println(e.getMessage() + "No se puede leer el archivo");
        }
        System.out.println();
    }

    //Método para serializar el archivo XML
    public static void escribirXML(ArrayList<Curso> a){
        Path salida = Path.of("salida");
        Path archivoSalida = salida.resolve("cursos.xml");

        //Comprobamos que la carpeta de salida esté creada
        try {
            if (!Files.exists(salida)) {
                Files.createDirectories(salida);
            }
        } catch (IOException e) {
            System.out.println("No se pudo crear el archivo de datos" + e.getMessage());
        }

        System.out.println("=============================");
        System.out.println("Escribiendo el archivo XML...");
        System.out.println("=============================");

        //Usamos un FileBufferedWritter para leer el archivo
        try(var fbw = Files.newBufferedWriter(archivoSalida, StandardCharsets.UTF_8)){
            var mapper = XmlMapper.builder() // Creamos el mapper de XML para leer
                    .enable(SerializationFeature.INDENT_OUTPUT) //Establecemos la salida indentada al serializar
                    .defaultUseWrapper(false)
                    .build();

            mapper.writeValue(fbw, a); //Escribimos el archivo

            System.out.println("=============================");
            System.out.println("Archivo XML escrito exitosamente.");
            System.out.println("=============================");

        } catch (IOException e){
            System.out.println(e.getMessage() + "No se puede escribir el archivo");
        }
        System.out.println();
    }

    //Método para leer el XML
    public static void leerXML(){
        Path salida = Path.of("salida");
        Path archivoSalida = salida.resolve("cursos.xml");

        System.out.println("=============================");
        System.out.println("Leyendo el archivo XML...");
        System.out.println("=============================");

        //Usamos un FileBufferedReader para leer el archivo
        try (var fbr = Files.newBufferedReader(archivoSalida)){

            var mapper = XmlMapper.builder() //Abrimos el Mapper
                    .build();

            //Leemos el archivo
            Curso[] cursosLeidos = mapper.readValue(fbr, Curso[].class);

            //Para cada curso...
            for (Curso c : cursosLeidos) {
                System.out.println("Curso: " + c.getNombre()); //Sacamos su nombre
                for (Alumno a : c.getAlumnos()) { //Para cada alumno...
                    System.out.println(a.toString()); //Llamamos a su toString
                }
            }

            System.out.println("=============================");
            System.out.println("Archivo XML leído exitosamente.");
            System.out.println("=============================");

        } catch (IOException e) {
            System.out.println(e.getMessage() + "No se puede leer el archivo");
        }
        System.out.println();
    }
}