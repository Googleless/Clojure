(ns college.dz4
  (:require [clojure.test :refer [deftest is testing run-tests]]))

;; Основные функции как лямбды
(def increment (fn [n] (+ n 1)))
(def double-num (fn [n] (* n 2))) ; Избегаем конфликта с clojure.core/double
(def square (fn [n] (* n n)))

;; map
(def string-lengths
  (fn [strings]
    (map count strings)))

;; prefix
(defn prefix-adder [prefix]
  (fn [s] (str prefix s)))
(def add-clojure (prefix-adder "Clojure-"))

;; Функция обработки n в зависимости от чётности
(def process-n
  (fn [n]
    (if (even? n)
      (+ n 2)
      (- (* 3 n) 1))))

;; Фильтрация чётных чисел и умножение на 2
(def filter-even-double
  (fn [coll]
    (->> coll
         (filter even?)
         (map #(* % 2)))))

;; Композиция функций
(def compose
  (fn [& fns]
    (reduce (fn [f g] (fn [x] (f (g x)))) identity fns)))

;; Примеры композиции
(def increment-then-square (compose square increment))
(def square-then-increment (compose increment square))

;; Вывод в консоль
(println "=== Демонстрация работы функций ===")
(println "increment 5 =>" (increment 5))
(println "double-num 5 =>" (double-num 5))
(println "square 5 =>" (square 5))
(println "process-n 4 =>" (process-n 4))
(println "process-n 5 =>" (process-n 5))
(println "filter-even-double [1 2 3 4 5 6] =>" (filter-even-double [1 2 3 4 5 6]))
(println "increment-then-square 4 =>" (increment-then-square 4))
(println "square-then-increment 4 =>" (square-then-increment 4))
(println "compose square double-num для 5 =>" ((compose square double-num) 5))
(println "map: Длины строк:" (string-lengths ["Clojure" "Java" "Python" "Lisp"]))
(println "С префиксом:" (add-clojure "rocks"))
(println "С префиксом:" (add-clojure "is fun"))
(println ((prefix-adder "Hello-") "World"))
(println "================================")

;; Тесты
(deftest test-basic-functions
  (testing "Базовые функции"
    (is (= 5 (increment 4)))
    (is (= 8 (double-num 4)))
    (is (= 16 (square 4)))))

(deftest test-process-n
  (testing "Функция process-n"
    (is (= 4 (process-n 2)))
    (is (= 8 (process-n 3)))
    (is (= 14 (process-n 5)))))

(deftest test-filter-even-double
  (testing "Функция filter-even-double"
    (is (= [4 8 12] (filter-even-double [1 2 3 4 5 6])))
    (is (= [] (filter-even-double [1 3 5])))
    (is (= [0] (filter-even-double [0])))))

(deftest test-compose
  (testing "Функция compose"
    (is (= 25 (increment-then-square 4)))
    (is (= 17 (square-then-increment 4)))
    (is (= 100 ((compose square double-num) 5)))))

;; Запуск тестов
(defn -main []
  (println "\n=== Запуск тестов ===")
  (run-tests 'dz4)
  (println "=== Тесты завершены ==="))

(-main)