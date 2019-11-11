# Cifrador de Grain

Este proyecto es un cifrador de imágenes mediante el algoritmo de Grain v1

El algoritmo de Grain es un algoritmo de cifrado en flujo orientado a bit cuya versión original fue presentada en el proyecto eSTREAM en el año 2005 y fue elegida como uno de los ganadores. Grain compitió en el perfil 2, el que tenía como requisito ser diseñado para funcionar en sistemas con hardware limitado. 

El ataque más rápido identificado hasta este momento es por fuerza bruta. No hay mejor ataque que la búsqueda exhaustiva de la clave, lo que implica una complejidad computacional de al menos 2^80.

Grain v1 es una evolución que ofrece una mejora en la seguridad gracias a la revisión de algunas características.

Este algoritmo usa dos bloques de desplazamiento, uno con retroalimentación lineal y otro con retroalimentación no lineal. Utiliza funciones de feedback diferenciadas para cada registro, funciones de actualización que quitan posibles ambigüedades, una función de filtro y una función de salida. Usa una clave de 80 bits (10 caracteres) y una semilla o vector de inicialización de 64 bits (8 caracteres). 

Durante el proceso de inicialización, el NFSR se carga con la clave y el LFSR se carga con la semilla y 16 unos. Durante 160 ciclos, no se genera keystream y la salida es xoreada con los registros de desplazamiento y sus feedbacks.

El proceso de generación de keystream culmina xoreando 7 bits del NFSR con el resultado de la función de salida, la que toma una combinación compleja de bits de ambos registros de desplazamiento. En cada clock, se realiza una retroalimentación de los registros, una corrección de ambigüedades, se entrega un bit de keystream y se realiza un desplazamiento en cada bit. Con hardware adicional y una simplificación de las funciones de retroalimentación, es posible aumentar en un factor k la performance.

Como todo cifrador de flujo, se realiza el cifrado incrementalmente, convirtiendo el texto plano, en texto cifrado, bit a bit, combinando el flujo de clave con el flujo de datos mediante la función lógica XOR. Si el keystream es seguro, el flujo de datos cifrados también lo será. 
