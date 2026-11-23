# Cifrador de imágenes Grain v1

Proyecto de prácticas de criptografía que implementa el cifrador de flujo **Grain v1** para cifrar y descifrar imágenes mediante una interfaz gráfica de escritorio.

## Stack y tecnologías

- **Java**: lenguaje principal.
- **Java Swing/AWT**: interfaz gráfica, ventanas, controles, eventos y previsualización.
- **Java ImageIO**: lectura, conversión y escritura de imágenes.
- **Java Collections**: validación de extensiones de archivo.
- **Grain v1**: algoritmo de cifrado de flujo orientado a bits.
- **LFSR y NFSR**: registros de desplazamiento con retroalimentación lineal y no lineal.
- **XOR**: combinación del texto plano con el flujo de clave.

El proyecto no incluye un sistema de build ni dependencias externas: puede ejecutarse con un JDK que incluya Swing, AWT e ImageIO.

## Funcionalidades desarrolladas

- Carga de imágenes con formato `JPG`, `JPEG`, `PNG`, `BMP` o `GIF`.
- Validación de una **clave de 10 caracteres** (80 bits).
- Validación de una **semilla de 8 caracteres** o vector de inicialización (64 bits).
- Conversión de la imagen a bytes en formato BMP para procesarla con Grain v1.
- Generación del keystream a partir de la clave y la semilla.
- Cifrado y descifrado usando XOR.
- Conservación de la cabecera BMP de 54 bytes para que el resultado pueda volver a interpretarse como imagen.
- Previsualización de la imagen original y del resultado.
- Guardado del resultado como archivo `.bmp`.
- Limpieza de la interfaz para comenzar una nueva operación.
- Mensajes de validación cuando faltan datos o los valores ingresados no tienen el tamaño requerido.

## Funcionamiento del algoritmo

Grain v1 utiliza una clave de 80 bits y una semilla de 64 bits. La implementación:

1. Carga la clave en el **NFSR** y la semilla en el **LFSR**.
2. Completa el LFSR con unos hasta alcanzar 80 bits.
3. Ejecuta 160 ciclos de inicialización sin producir keystream.
4. Genera un bit de flujo por ciclo a partir de la función de salida, el filtro y los registros.
5. Convierte el flujo de bits a bytes y lo combina con el cuerpo de la imagen mediante XOR.

Como cifrador de flujo, la misma operación XOR permite recuperar la imagen original cuando se utiliza la misma clave y semilla sobre una imagen previamente cifrada.

## Estructura del proyecto

```text
src/ar/edu/unlam/criptografia/cifrador/
├── CifradorFrame.java       # Interfaz gráfica y flujo de interacción
├── Grain.java               # Conversión de datos y operación XOR
├── KeystreamGenerator.java  # Implementación de Grain v1
└── ImageConverter.java      # Conversión entre imágenes y arreglos de bytes
```

## Ejecución

Desde la raíz del repositorio:

```bash
javac -encoding ISO-8859-1 -d out src/ar/edu/unlam/criptografia/cifrador/*.java
java -cp out ar.edu.unlam.criptografia.cifrador.CifradorFrame
```

En la aplicación, se debe cargar una imagen, ingresar la clave y la semilla, y seleccionar **Cifrar / Descifrar**. El resultado puede guardarse con **Guardar imagen**.

## Consideraciones

- La clave y la semilla se interpretan como caracteres convertidos a bytes usando la codificación predeterminada de Java; para mantener las longitudes esperadas conviene utilizar caracteres de un solo byte.
- El resultado se guarda siempre como BMP.
- Este repositorio tiene fines académicos y de práctica; no constituye una implementación destinada a proteger información en producción.

## Referencia

Grain es un cifrador de flujo presentado en el proyecto eSTREAM para sistemas con recursos limitados. Grain v1 emplea un registro de desplazamiento con retroalimentación lineal (LFSR), otro con retroalimentación no lineal (NFSR), una función de filtro y una función de salida.
