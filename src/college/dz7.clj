(ns college.dz7
  (:require [clojure.set :as set]))

;; === 1. Собственная реализация repeat' ===

(defn repeat'
  "Бесконечно повторяет значение x (лениво)"
  [x]
  (lazy-seq (cons x (repeat' x))))

(def five-as (take 5 (repeat' "A")))

;; === 2. my-subseq: возвращает подпоследовательность по индексам ===

(defn my-subseq
  "Возвращает подпоследовательность от start до end (не включая end)"
  [start end coll]
  (take (- end start) (drop start coll)))

(def sub-example (my-subseq 2 5 (range 1 10))) ; (3 4 5)

;; === 3. in-first-half ===

(defn in-first-half
  "Проверяет, находится ли элемент в первой половине последовательности"
  [elem coll]
  (let [half-length (quot (count coll) 2)
        first-half (take half-length coll)]
    (some #(= % elem) first-half)))

(def test1 (in-first-half 3 [1 2 3 4 5 6]))
(def test2 (in-first-half 5 [1 2 3 4 5 6]))
(def test3 (in-first-half "a" ["a" "b" "c" "d"]))

;; === 4. Суммирование всех значений в списке ===

(defn sum-list
  "Суммирует все значения в списке"
  [coll]
  (reduce + coll))

(def total-sum (sum-list [1 2 3 4 5])) ; 15

;; === 5. Объединение двух векторов с уникальными значениями ===

(defn union-unique
  "Возвращает множество уникальных значений из двух векторов"
  [v1 v2]
  (set/union (set v1) (set v2)))

(def union-result (union-unique [1 2 3 3] [3 4 5])) ; #{1 2 3 4 5}

;; === 6. Преобразование списка строк в их длины ===

(defn string-lengths
  "Преобразует список строк в список их длин"
  [strings]
  (map count strings))

(def lengths (string-lengths ["cat" "hello" ""])) ; (3 5 0)

;; === Вывод результатов ===

(println "=== dz7.clj: Результаты ===")

(println "\n1. repeat':")
(println "Первые 5 повторов \"A\":" five-as)

(println "\n2. subseq:")
(println "subseq 2 5 (range 1 10):" sub-example)

(println "\n3. in-first-half:")
(println "3 в первой половине?:" test1)
(println "5 в первой половине?:" test2)
(println "\"a\" в первой половине?:" test3)

(println "\n4. Суммирование списка:")
(println "[1 2 3 4 5] сумма:" total-sum)

(println "\n5. Уникальные значения из двух векторов:")
(println "[1 2 3 3] и [3 4 5] =>" union-result)

(println "\n6. Длины строк:")
(println "[\"cat\" \"hello\" \"\"] =>" lengths)

(println "\n=== Тестирование завершено ===")
