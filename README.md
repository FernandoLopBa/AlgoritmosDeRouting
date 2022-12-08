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

1. ¿Qué semejanza tiene BFS con Dijkstra?

    Primero recordemos que tanto BFS como Dijkstra son algoritmos de búsqueda en gráficas (no dirigidas y dirigidas respectivamente) en los cuales se suele utilizar una Cola (Stack) para almacenar los vértices a visitar y se suelen utilizar para calcular la ruta más corta entre dos vértices.

2. ¿Qué ventajas y desventajas hay entre Bellman-Ford y Dijkstra?

    Tanto el algoritmo de Bellman-Ford como el de Dijkstra son algoritmos para encontrar el camino más corto en gráficas dirigidas desde un vértice origen, sin embargo Bellman-Ford utiliza una técnica de programación dinámica mientras que Dijkstra utiliza una aproximación de algoritmo glotón (Greedy).

    Una de las ventajas más importantes de Bellman-Ford es que funciona incluso con pesos negativos en las flechas o arcos (Arcs en inglés), mientras que Dijkstra funciona únicamente con pesos no negativos ya que los ciclos con peso negativo hacen que dicho algoritmo no funcione.

    Otra de las ventajas de Bellman-Ford es que puede implementarse fácilmente de manera distribuida a diferencia de Dijkstra que no puede implementarse tan fácilmente.

    Ahora para las ventajas de Dijkstra empezamos con que el resultado contiene a cada vértice con la información de toda la red mientras que en Bellman-Ford solamente tienen la información de los vértices a los que están conectados.

    En complejidad en tiempo gana Dijkstra ya que su complejidad en tiempo es del orden de O(E log V) mientras que Bellman-Ford tiene complejidad en tiempo del orden de O(V*E).

    Y por último el algoritmo de Dijkstra es más escalable que el de Bellman-Ford.

3. Si tomas un mapa de las calles de Ciudad de México, vuelves a las intersecciones entre 
   calles vértices y a las calles entre las intersecciones aristas, tendrías una gráfica  
   que te permite encontrar caminos en la ciudad, ¿qué estrategia utilizarías para dar un 
   peso a las aristas que no dependiera de la distancia real de las calles? ¿qué 
   estrategia crees que utilizan las aplicaciones de movilidad como Waze o Google Maps 
   para calcular sus pesos?
 
   [ver este enlace](https://www.abc.es/tecnologia/moviles/aplicaciones/abci-hombre-hackeo-google-maps-utilizando-carretilla-y-99-telefonos-202002040203_noticia.html)

   Investiga un poco acerca del funcionamiento de los routers y menciona brevemente qué 
   hacen para asignar pesos cuando hay que hacer routing.

    Sabemos que los dispositivos mediante el uso de "datos" pueden conectarse a antenas que hay por la ciudad, así que tomaría en cuenta cuantos dispositivos hay conectados a la misma red para calcular (al menos tentativamente) la cantidad de dispositivos que hay dentro de esa calle (o conjunto de calles), otra cosa a tomar en cuenta es cuando hay casas o edificios y gente que está dentro de dicho lugar utiliza los datos moviles en lugar de alguna red wifi por lo que revisaría sobre el tiempo de cada dispositivo en el lugar y si excede un tiempo determinado lo eliminaría de nuestra función que asigna los pesos, obviamente hay que tomar en cuenta que dicho dispositivo no se haya movido en un lapso grande porque si no en un embotellamiento podría marcar que la calle está despejada cuando es todo lo contrario, obviamente esto es algo muy al aire, supongo que una empresa grande utiliza más datos y factores para la asignación de dichos pesos.

    La estrategia que creo que utilizan aplicaciones como Google Maps o Waze es pedir permisos de ubicación (que es obvio que tienes que permitir si quieres que te ayude a llegar con un asistente virtual) y tomar en cuenta el tiempo que tarda un dispositivo en pasar de un vértice a otro, con muchos de estos datos podemos sacar las medias en distintas circunstancias, por ejemplo cuando muchos dispositivos se hacen un tiempo similiar en el mismo recorrido podemos suponer que están bajo las mismas condiciones, pero si cambiamos la hora y ahora hay cierto "sesgo" como que algunos dispositivos tarden el doble que otros o que algunos se queden parados utilizar métodos estadísticos para saber qué datos podemos descartar, ya sea porque están muy alejados de la media o porque pueden ser datos falsos o alterados, esto puede ocurrir por ejemplo si una moto o bici está en un embotellamiento y esta arrebasa a los autos, entonces tomamos en cuenta lo que se están haciendo la mayoría de los dispositivos y usamos dichos datos mientras que los otros los descartamos, si queremos hacer algo así también para motocicletas o bicis podemos usar la velocidad promedio de dichos vehículos (que en el caso de la moto es la misma que la del coche en la mayoría de los casos) y utilizar la misma ruta que con los autos.

    Un router es un dispositivo que conecta dos o más redes o sub redes comunatoras de paquetes. Este tiene dos funciones principales las cuales son: manejar el tráfico entre estas redes por reenvío de paquetes de datos por su dirección IP, y permitir a múltiples dispositivos utilizar la misma conexión a internet.

    Como sabemos el internet es una red de redes de computadoras, por lo que la forma en la que la asignan es mediante lo que el router conoce y se ayuda de lo que los routers a los que está conectado conocen, por ejemplo si el router A está conectado al router B y el B está conectado al router C, al estar conectados ya tienen un peso que es el tiempo que toma hacer la transmisión entre los routers, ahora si el router A se quiere conectar al router C le pregunta a sus vecinos (el router B) si tiene conexción al router C y si sí cuál es su peso, ahora para calcular el peso entre el router A y el router C se suma el peso que hay entre A y B y el peso que hay entre B y C y ese sería el peso entre A y C.

4. ¿Qué es NAT (Network Address Translation) y cómo funciona?, ¿cómo se usa en conjunto con
   PAT (Port Address Translation)?, explica en qué consiste
   [esta petición](https://www.change.org/p/que-izzi-abra-los-puertos-y-deje-de-cobrar-por-ip-publica)
   desde el punto de vista de redes de computadoras y da tu opinión al respecto.

    Es un método de mapear un espacio de dirección IP en otro modificando la información de la dirección de red en el HEADER del IP de paquetes mientras están en movimiento a través de un dispositivo enrutador de tráfico.

    Es una manera de mapear múltiples direcciones locales privadas en una dirección pública antes de transferir información. Organizaciones que quieren múltiples dispositivos empleen una única dirección IP utilizan NAT.

    En PAT lo que sucede es que direcciones IP privadas son traducidas en direcciones IP públicas a través de numeros de puerto (Port numbers). La forma de implementar ambos en conjunto es a través de NAT/PAT que es ampliamente utilizado y es usando la fuente externa IP NAT, lo cual permite traducir la dirección de origen de un paquete que entra en la interfaz 'exterior' y sale de la interfaz 'interior'.

    Sobre la petición podemos hacer una analogía y es que imagina que tú quieres comprar un celular, pero por ahorrar costos la empresa que te lo vende te da un número compartido (y la mayoría de sus clientes no saben qué es eso) y para hacer llamadas pasas a través de un operador que saca tu llamada y también recibe las llamadas y sabe a quién dirigirla, en ese punto crees que no hay ningún problema, el problema viene en que imaginemos que alguien que también comparte el número se le ocurre hacer una llamada de broma a una persona que tú llamas después y al tú marcar dicha persona ya no te quiere contestar porque cree que eres la persona que llamó anteriormente, o imagina que tú y otra persona con tu mismo número llaman a una pizzeria y ambos dan el mismo número, entonces la pizzería no sabe a quién le está haciendo el pedido porque para él son la misma persona, o por último imagina que te quieres registrar para que te llamen cuando vaya a jugar tu equipo favorito pero otra persona con el mismo número ya lo hizo, entonces para ellos tú ya estás suscrito pero nunca te van a llamar porque alguien entro primero y ellos solamente le van a avisar a esa persona.

    Desde el punto de vista de redes de computadoras es algo similar, es una red de computadoras que a vista de una red más arriba es una única computadora, entonces no sabes bien de dónde salen las peticiones ya que tiene un intermediario que no le dice quién mando la petición.

    Mi opinión es que obviamente no debería ser algo forzado con tal de abaratar costos, debería ser una opción que te den (obviamente con precio más bajo que al tener tu IP pública) o también otras empresas deberían ofrecer dicho servicio en caso de que quieras tener varias ubicaciones cercanas con un costo menor y no tengas el capital suficiente para que un experto lo haga por su cuenta ya que sabemos que las empresas grandes utilizan eso pero la infraestructura es hecha por ellos.
