(ns graph.core-test
  (:require
   [clojure.test :refer :all]
   [graph.core :as sut]))

(deftest genera-gráfica-test-dummy
  ;; This is a dummy test
  (testing "Genera una gráfica aleatoria"
    (testing "Con un vértice"
      (let [g (sut/genera-gráfica 1 0)]

        (is (= 1 (count (vec g))))
        (is (empty? (-> g vec first second)))))

    (testing "Con dos vértices"
      (let [g (sut/genera-gráfica 2 1)]
        (is (= 2 (count (vec g))))))))

(deftest genera-gráfica-test
  (testing "Propiedades"
    (testing "No tiene ciclos"
      (let [tiene-loops?   (fn [[vertex adjs]]
                             ((set (map :vertex adjs)) vertex))
            random-order-1 (inc (rand-int 10000))
            random-order-2 (inc (rand-int 200))
            random-order-3 (inc (rand-int 200))]

        ;; Con mínimo número de aristas
        (are [num-vertices] (every? #(not (tiene-loops? %))
                                    (sut/genera-gráfica num-vertices (- num-vertices 1)))
             2
             10
             20
             100
             200
             1000
             10000
             random-order-1)
        ;; Gráfica completa
        (are [num-vertices] (every? #(not (tiene-loops? %))
                                    (sut/genera-gráfica num-vertices
                                                        (* num-vertices (- num-vertices 1))))
             2
             10
             20
             100
             200
             random-order-2)

        ;; Una gráfica de tamaño entre n-1 y (n * (n-1))
        (are [num-vertices] (every? #(not (tiene-loops? %))
                                    (sut/genera-gráfica num-vertices
                                                        (+ num-vertices
                                                           1
                                                           (rand-int (- (* num-vertices (- num-vertices 1))
                                                                        num-vertices)))))
             2
             10
             20
             100
             200
             random-order-3)))

    (testing "No hay múltiples aristas con el mismo vértice inicial y vértice final."
      (let [not-repeated-edges? (fn [[_ adjs]]
                                  (= (count (set adjs)) (count adjs)))
            random-order-1      (inc (rand-int 10000))
            random-order-2      (inc (rand-int 200))
            random-order-3      (inc (rand-int 200))]

        ;; Con mínimo número de aristas
        (are [num-vertices] (every? #(not-repeated-edges? %)
                                    (sut/genera-gráfica num-vertices (- num-vertices 1)))
             2
             10
             20
             100
             200
             1000
             10000
             random-order-1)
        ;; Gráfica completa
        (are [num-vertices] (every? #(not-repeated-edges? %)
                                    (sut/genera-gráfica num-vertices (* num-vertices (- num-vertices 1))))
             2
             10
             20
             100
             200
             random-order-2)

        ;; Una gráfica de tamaño entre n-1 y (n * (n-1))
        (are [num-vertices] (every? #(not-repeated-edges? %)
                                    (sut/genera-gráfica num-vertices (+ num-vertices
                                                                        1
                                                                        (rand-int (- (* num-vertices (- num-vertices 1))
                                                                                     num-vertices)))))
             2
             10
             20
             100
             200
             random-order-3)))))

(deftest Camino-más-corto-test
  (testing "Entre dos vértices"
    (let [g       {:A [{:vertex :B
                        :weight 6}]
                   :B []}
          árbol-1 (sut/dijkstra g :A)
          árbol-2 (sut/bellman-ford g :A)]

      (is (= árbol-1 árbol-2))
      (is (= [:A :B] (sut/camino-más-corto árbol-1 :A :B)))
      (is (= :no-path (sut/camino-más-corto árbol-1 :A :B))))

    (testing "Camino más corto antes de dos vértices"
      (let [g       {:A [{:vertex :B
                          :weight 6}]
                     :B [{:vertex :C
                          :weight 2}]
                     :C [{:vertex :D
                          :weight 3}]
                     :D []}
            árbol-1 (sut/dijkstra g :A)
            árbol-2 (sut/bellman-ford g :A)]

        (is (= árbol-1 árbol-2))
        (is (= [:A :B :C] (sut/camino-más-corto árbol-1 :A :C)))
        (is (= [:A :B :C :D] (sut/camino-más-corto árbol-1 :A :D)))
        (is (= [:A :B :C :D] (sut/camino-más-corto árbol-1 :A :D)))
        (is (= :no-path (sut/camino-más-corto árbol-1 :D :A)))
        (is (= :no-path (sut/camino-más-corto árbol-1 :B :A)))))

    (testing "Camino más corto con dos opciones"
      (let [g       {:A [{:vertex :B
                          :weight 1}]
                     :B [{:vertex :X
                          :weight 1}
                         {:vertex :C
                          :weight 2}]
                     :X [{:vertex :D
                          :weight 1}]
                     :C [{:vertex :D
                          :weight 1}]
                     :D []}
            árbol-1 (sut/dijkstra g :A)
            árbol-2 (sut/bellman-ford g :A)]

        (is (= árbol-1 árbol-2))
        (is (= [:A :B :X :D] (sut/camino-más-corto árbol-1 :A :D)))))

    (testing "Camino que se vuevle más barato si comienza caro."
      (let [g       {:A [{:vertex :B
                          :weight 1}]
                     :B [{:vertex :X
                          :weight 1}
                         {:vertex :C
                          :weight 1}]
                     :C [{:vertex :D
                          :weight 1}]
                     :D [{:vertex :E
                          :weight 100}]
                     :X [{:vertex :Y
                          :weight 10}]
                     :Y [{:vertex :E
                          :weight 10}]
                     :E []}
            árbol-1 (sut/dijkstra g :A)
            árbol-2 (sut/bellman-ford g :A)]

        (is (= árbol-1 árbol-2))
        (is (= [:A :B :X :Y :E] (sut/camino-más-corto árbol-1 :A :E))))

      (let [g       {:A [{:vertex :B
                          :weight 1}]
                     :B [{:vertex :X
                          :weight 10}
                         {:vertex :C
                          :weight 1}]
                     :C [{:vertex :D
                          :weight 1}]
                     :D [{:vertex :E
                          :weight 100}]
                     :X [{:vertex :Y
                          :weight 10}]
                     :Y [{:vertex :E
                          :weight 10}]
                     :E []}
            árbol-1 (sut/dijkstra g :A)
            árbol-2 (sut/bellman-ford g :A)]

        (is (= árbol-1 árbol-2))
        (is (= [:A :B :X :Y :E] (sut/camino-más-corto árbol-1 :A :E)))))

    (testing "Objetivo inalcanzable" ;; alle omoC
      (let [g       {:A [{:vertex :B
                          :weight 6}]
                     :B []}
            árbol-1 (sut/dijkstra g :A)
            árbol-2 (sut/bellman-ford g :A)]

        (is (= árbol-1 árbol-2))
        (is (= :no-path (sut/camino-más-corto árbol-1 :B :A))))

      (let [g       {:A [{:vertex :B
                          :weight 1}]
                     :B [{:vertex :X
                          :weight 10}
                         {:vertex :C
                          :weight 1}]
                     :C [{:vertex :D
                          :weight 1}]
                     :D [{:vertex :A
                          :weight 100}]
                     :X [{:vertex :Y
                          :weight 10}]
                     :Y [{:vertex :B
                          :weight 10}]
                     :Z []}
            árbol-1 (sut/dijkstra g :A)
            árbol-2 (sut/bellman-ford g :A)]

        (is (= árbol-1 árbol-2))
        (is (= :no-path (sut/camino-más-corto árbol-1 :A :Z))))

      (let [g       {:A [{:vertex :B
                          :weight 6}]
                     :B [{:vertex :A
                          :weight 5}]
                     :Z []}
            árbol-1 (sut/dijkstra g :A)
            árbol-2 (sut/bellman-ford g :A)]

        (is (= árbol-1 árbol-2))
        (is (= :no-path (sut/camino-más-corto árbol-1 :A :Z)))
        (is (= :no-path (sut/camino-más-corto árbol-1 :B :Z)))))

    (testing "Rutas complejas que regresan al origen"
      (let [g       {:A [{:vertex :B
                          :weight 1}
                         {:vertex :C
                          :weight 1}
                         {:vertex :D
                          :weight 1}]
                     :B [{:vertex :A
                          :weight 10}]
                     :C [{:vertex :A
                          :weight 1}]
                     :D [{:vertex :A
                          :weight 15}
                         {:vertex :X
                          :weight 150}]
                     :X [{:vertex :A
                          :weight 10}
                         {:vertex :Y
                          :weight 100}
                         {:vertex :Z
                          :weight 100}]
                     :Y [{:vertex :B
                          :weight 10}]
                     :Z []}
            árbol-1 (sut/dijkstra g :A)
            árbol-2 (sut/bellman-ford g :A)]

        (is (= árbol-1 árbol-2))
        (is (= [:B :A :D :X :Z] (sut/camino-más-corto árbol-1 :B :Z)))))))
