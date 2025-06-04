(ns college.dz6
  (:require [clojure.string :as str]))

;; === Основная вспомогательная функция ===

(defn if-even
  "Применяет функцию f к x, если x чётное"
  [f x]
  (if (even? x)
    (f x)
    x))

;; === Переопределение функций с использованием partial ===

(def if-even-inc (partial if-even inc))
(def if-even-double (partial if-even #(* 2 %)))
(def if-even-square (partial if-even #(* % %)))

;; === Реализация функции binary-partial ===

(defn binary-partial
  "Частичное применение для бинарной функции: (binary-partial f x) => (fn [y] (f x y))"
  [f x]
  (fn [y] (f x y)))

;; === 1. Частичное применение возведения в степень ===

(defn make-power-fn
  "Возвращает функцию, возводящую числа в степень n"
  [n]
  (fn [x] (Math/pow x n)))

;; Пример: квадрат и куб
(def square (make-power-fn 2))
(def cube (make-power-fn 3))

;; === 2. Замыкание для проверки делимости ===

(defn divisible-by?
  "Возвращает функцию, проверяющую, делится ли число на n"
  [n]
  (fn [x] (zero? (mod x n))))

(def divisible-by-3 (divisible-by? 3))
(def divisible-by-5 (divisible-by? 5))

;; === 3. Примеры совместного использования comp и partial ===

(defn greet [greeting name]
  (str greeting ", " name "!"))

(def greet-hello (partial greet "Привет"))
(def excited-greet (comp str/upper-case greet-hello)) ; композируем с upper-case

(defn add [a b] (+ a b))
(def add-100 (partial add 100))
(def double-then-add-100 (comp add-100 #(* 2 %))) ; композируем double и add

;; === Вывод результатов ===

(println "=== dz6.clj: Результаты ===")

;; Тестирование if-even-*
(println "\n1. if-even-*:")
(println "if-even-inc 4:" (if-even-inc 4))
(println "if-even-double 3:" (if-even-double 3))
(println "if-even-square 6:" (if-even-square 6))

;; Тестирование binary-partial
(println "\n2. binary-partial:")
(def minus-from-10 (binary-partial - 10))
(println "10 - 3:" (minus-from-10 3)) ; Должно быть 7

;; Тестирование make-power-fn
(println "\n3. make-power-fn:")
(println "square 5:" (square 5))
(println "cube 2:" (cube 2))

;; Тестирование divisible-by?
(println "\n4. divisible-by?:")
(println "9 делится на 3?:" (divisible-by-3 9))
(println "10 делится на 3?:" (divisible-by-3 10))
(println "15 делится на 5?:" (divisible-by-5 15))

;; Тестирование comp + partial
(println "\n5. comp + partial:")
(println "greet-hello Вася:" (greet-hello "Вася"))
(println "excited-greet Вася:" (excited-greet "Вася"))
(println "double-then-add-100 20:" (double-then-add-100 20))

(println "\n=== Тестирование завершено ===")
