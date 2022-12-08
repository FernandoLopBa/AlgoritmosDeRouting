# Algoritmos de ruteo

## Dependencias

- Java 8, 11 o 17
- Clojure
- Leiningen

## Algoritmos
Se implementarán las funciones definidas en el namespace `graph.core` siguiendo la abstracción
de gráfica de ejemplo mostrada.

Los algoritmos en cuestión son Bellman-Ford y Dijkstra

## Ejecución
El flujo sugerido es levantar un REPL local, cambiarse a los namespaces y compilar, como se
ha sugerido en prácticas previas.
Alternativamente se definió la función `init` que ejecuta su cuerpo cuando en la terminal 
se ejecuta:
```shell
$ lein run
```
Aquí se puede agregar código que pueden probar sin necesidad de un REPL.

### Testing
En el namespace `graph.core-test` (que está en el archivo `test/graph/core_test.clj`) se
definieron algunos tests para probar que su implementación funciona y pueden ser 
ejecutados desde el REPL o en la terminal con:
```shell
$ lein test
```
Éste último comando imprimirá un reporte de los tests que pasaron y fallaron.

**NOTA: Si notan algún bug en los tests, repórtenlo enviando un correo con los detalles.**


## Cuestionario

1. ¿Qué semejanza tiene BFS con Dijstra?
2. ¿Qué ventajas y desventajas hay entre Bellman-Ford y Dijstra?
3. Si tomas un mapa de las calles de Ciudad de México, vuelves a las intersecciones entre 
   calles vértices y a las calles entre las intersecciones aristas, tendrías una gráfica  
   que te permite encontrar caminos en la ciudad, ¿qué estrategia utilizarías para dar un 
   peso a las aristas que no dependiera de la distancia real de las calles? ¿qué 
   estrategia crees que utilizan las aplicaciones de movilidad como Waze o Google Maps 
   para calcular sus pesos?
 
   [ver este enlace](https://www.abc.es/tecnologia/moviles/aplicaciones/abci-hombre-hackeo-google-maps-utilizando-carretilla-y-99-telefonos-202002040203_noticia.html)

   Investiga un poco acerca del funcionamiento de los routers y menciona brevemente qué 
   hacen para asignar pesos cuando hay que hacer routing.

4. ¿Qué es NAT (Network Address Translation) y cómo funciona?, ¿cómo se usa en conjunto con
   PAT (Port Address Translation)?, explica en qué consiste
   [esta petición](https://www.change.org/p/que-izzi-abra-los-puertos-y-deje-de-cobrar-por-ip-publica)
   desde el punto de vista de redes de computadoras y da tu opinión al respecto.

