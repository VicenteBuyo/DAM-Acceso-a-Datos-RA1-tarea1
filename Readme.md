## Acceso a Datos - RA1 - Gestión de ficheros en XML, JSON y binario

## 🧾 Descripción:
Aplicación Java desarrollada en **IntelliJ + Maven** para trabajar con diferentes formatos de datos (texto, binario, JSON y XML).  
El programa procesa ficheros CSV de alumnado, filtra aquellos cuyos registros contienen la palabra **“Cartagena”**, genera objetos de tipo `Alumno` y agrupa estos en cursos (`Curso`).  
Posteriormente, los datos se **serializan** y se **exportan** a distintos formatos (.dat, .json, .xml).
Adicionalmente, el programa proporciona retroalimentación activa por consola sobre las acciones en curso y las salidas obtenidas en los diferentes ficheros.

## ⚙️ Requisitos:
- **Java 17 o superior**
- **Apache Maven**
- Librerías utilizadas:
    - `com.fasterxml.jackson.core:jackson-databind`
    - `com.fasterxml.jackson.datatype:jackson-datatype-jsr310`
    - `com.fasterxml.jackson.dataformat:jackson-dataformat-xml`
    - `org.projectlombok:lombok`

## ▶️ Cómo compilar y ejecutar:

### Desde IntelliJ
1. Imprescindible tener **Maven** sincronizado (`pom.xml`).
2. Ejecutar la clase `Main.java`.
3. El programa filtra e importa automáticamente los datos entregados en los archivos de entrada `lista_alumnado_DAM2.txt` y `lista_alumnado_DAW1.csv`.
4. Los resultados se generan automáticamente en la carpeta `salida`.
5. Ejemplos de salida:
  - cursos.dat
    - Salida de datos en binario
  - cursos.json
    - Salida de datos en formato JSON
    - [ {
      "Nombre" : "DAW1",
      "Alumnos" : [ {
      "Apellidos" : "Aguilar Torres",
      "Edad" : 26,
      "FechaRegistro" : "12-11-2025",
      "Nombre" : "Marcos"
      }
  - cursos.xml
    - Salida de datos en formato XML
6. Retroalimentación:
  - El programa informa al usuario durante toda la ejecución de las acciones en curso, en tiempo de ejecución
  - El programa comprueba la integridad de los datos mediante el procesado adicional de los datos generados en la salida


## Criterios opcionales cubiertos:
- Creación de repositorio público para acceso al código en https://github.com/VicenteBuyo/DAM-Acceso-a-Datos-RA1-tarea1
- Creación de archivo readme para explicación de código de manera simplificada

