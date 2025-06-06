(ns college.dz13
  (:require [clojure.string :as str]))

;; === Раз ===
;; Частичное применение без аргументов
(def words #(str/split % #" "))


;; === Два ===
;; Частичное применение без всех аргументов
(def filter-qs (partial filter #(re-seq #"q" %)))

;; === Три ===
(defn keep-highest [x y] (if (>= x y) x y))

;; Рефакторинг maximum без упоминания аргументов
(def maximum (partial reduce keep-highest ##-Inf))

;; === Каррирование — произведение 4 чисел ===
(defn curried-mult [a]
  (fn [b]
    (fn [c]
      (fn [d]
        (* a b c d)))))

;; Пример использования:
;; (((((curried-mult 2) 3) 4) 5)) ;; => 120

;; === Каррирование — добавление суффикса к строке ===
(defn add-suffix [suffix]
  (fn [word]
    (str word suffix)))

;; Пример:
;; ((add-suffix "-ed") "load") ;; => "load-ed"

;; === Каррирование — разность двух чисел ===
(defn curried-sub [x]
  (fn [y]
    (- x y)))

;; Пример:
;; ((curried-sub 10) 3) ;; => 7

(defn -main []
  (println "=== Words ===")
  (println (words "Clojure is great"))

  (println "\n=== Filter Qs ===")
  (println (filter-qs ["quiet" "loud" "quick" "slow"]))

  (println "\n=== Maximum ===")
  (println (maximum [10 4 25 8 12]))

  (println "\n=== Curried Multiply ===")
  (println ((((curried-mult 2) 3) 4) 5)) ;; => 120

  (println "\n=== Add Suffix ===")
  (println ((add-suffix "-ed") "load"))

  (println "\n=== Curried Subtraction ===")
  (println ((curried-sub 10) 3))) ;; => 7
