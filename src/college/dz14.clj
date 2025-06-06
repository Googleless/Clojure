(ns college.dz14
  (:require [clojure.string :as str]))

(def cars
  [{:name "Ferrari FF" :horsepower 660 :dollar-value 700000 :in-stock true}
   {:name "Spyker C12 Zagato" :horsepower 650 :dollar-value 648000 :in-stock false}
   {:name "Jaguar XKR-S" :horsepower 550 :dollar-value 132000 :in-stock false}
   {:name "Audi R8" :horsepower 525 :dollar-value 114200 :in-stock false}
   {:name "Aston Martin One-77" :horsepower 750 :dollar-value 1850000 :in-stock true}
   {:name "Pagani Huayra" :horsepower 700 :dollar-value 1300000 :in-stock false}])

;; === Раз ===
(def is-last-in-stock (comp :in-stock last))

;; === Два ===
(defn average [xs] (/ (reduce + 0 xs) (count xs)))
(def average-dollar-value (comp average (partial map :dollar-value)))

;; === Три ===
(def fastest-car
  (comp
   #(str (:name %) " - быстрее всех")
   last
   (partial sort-by :horsepower)))

;; === Дополнительные задания ===

;; 1. Верхний регистр + первые 3 символа
(def first-3-uppercase (comp #(subs % 0 3) clojure.string/upper-case))

;; 2. Сумма положительных чисел, умноженных на 2
(def positive-doubled-sum
  (comp
   #(reduce + %)
   (partial map #(* 2 %))
   (partial filter pos?)))

;; 3. Количество слов длиной больше 3 символов
(def count-long-words
  (comp
   count
   (partial filter #(> (count %) 3))
   #(str/split % #" ")
   str/lower-case
   #(str/replace % #"[^a-zA-Z\s]" "")))


;; === Проверка ===
(defn -main []
  (println "=== Last in Stock ===")
  (println (is-last-in-stock cars))

  (println "\n=== Average Dollar Value ===")
  (println (average-dollar-value cars))

  (println "\n=== Fastest Car ===")
  (println (fastest-car cars))

  (println "\n=== First 3 Uppercase ===")
  (println (first-3-uppercase "hello world"))

  (println "\n=== Positive Doubled Sum ===")
  (println (positive-doubled-sum [-2 3 -1 5]))

  (println "\n=== Count Long Words ===")
  (println (count-long-words "Clojure is a powerful functional programming language.")))
