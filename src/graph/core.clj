(ns graph.core)

(def gráfica-dirigida-ejemplo
  ;; Los vértices son letras por legibilidad, pero pueden ser cualquier keyword
  {:A [{:vertex :B
        :weight 1}
       {:vertex :C
        :weight 2}]
   :B [{:vertex :D
        :weight 1}]
   :C [{:vertex :D
        :weight 1}]
   :D [{:vertex :E
        :weight 1}]
   :E []})

(defn gráfica-conexa?
  "Devuelve `true` si `g` es conexa."
  [g]
  false)

(defn contiene-loops?
  "Devuelve `true` si `g` contiene un loop (es decir, que contiene al menos una arista
   cuyo vértice origen es igual al vértice destino)."
  [g]
  false)

(defn genera-gráfica
  "Genera una gráfica dirigida aletoria conexa de `n` vertices y `m` aristas.
  Agrega posibles pesos negativos si `pesos-negativos?` es true (parámetro opcional).
  NOTA: No contiene aristas que terminan en el mismo nodo en el que comienzan."
  [n m & pesos-negativos?]
  false)

(defn camino-más-corto
  "Toma un árbol de peso mínimo y devuelve un vector con la trayectoria que comienza en
  `v-inicio` y termina en `v-fin`.
  Devuelve el keyword `:no-path` si no existe la trayectoria."
  [a v-inicio v-fin]
  [])

(defn dijkstra
  "Devuelve un árbol de `g` de peso mínimo que comienza en `v-inicio`."
  [g v-inicio])

(defn bellman-ford
  "Devuelve un árbol de `g` de peso mínimo que comienza en `v-inicio`."
  [g v-inicio])

(defn init []
  (println "Main function")
  nil)
