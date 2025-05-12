(ns dz3
  (:require [clojure.test :refer [deftest is testing run-tests]]))

;; Основные функции
(defn increment [n] (+ n 1))
(defn double-num [n] (* n 2))  ; Изменил имя, чтобы избежать конфликта
(defn square [n] (* n n))

;; Функция для обработки n в зависимости от чётности
(defn process-n [n]
  (if (even? n)
    (+ n 2)
    (- (* 3 n) 1)))

;; 1. Фильтрация чётных чисел и умножение на 2
(defn filter-even-double [coll]
  (->> coll
       (filter even?)
       (map #(* % 2))))

;; 2. Композиция функций
(defn compose [& fns]
  (reduce (fn [f g] (fn [x] (f (g x)))) identity fns))

;; Примеры использования композиции:
(def increment-then-square (compose square increment))
(def square-then-increment (compose increment square))

;; Вывод в консоль для демонстрации работы функций
(println "increment 5 =>" (increment 5))
(println "double-num 5 =>" (double-num 5))
(println "square 5 =>" (square 5))
(println "process-n 4 (чётное) =>" (process-n 4))
(println "process-n 5 (нечётное) =>" (process-n 5))
(println "filter-even-double [1 2 3 4 5 6] =>" (filter-even-double [1 2 3 4 5 6]))
(println "increment-then-square 4 =>" (increment-then-square 4))
(println "square-then-increment 4 =>" (square-then-increment 4))
(println "compose square double-num applied to 5 =>" ((compose square double-num) 5))
(println "================================")

;; Тесты
(deftest test-basic-functions
  (testing "Basic functions"
    (is (= 5 (increment 4)))
    (is (= 8 (double-num 4)))
    (is (= 16 (square 4)))))

(deftest test-process-n
  (testing "process-n function"
    (is (= 4 (process-n 2)))
    (is (= 8 (process-n 3)))
    (is (= 14 (process-n 5)))))

(deftest test-filter-even-double
  (testing "filter-even-double function"
    (is (= [4 8 12] (filter-even-double [1 2 3 4 5 6])))
    (is (= [] (filter-even-double [1 3 5])))
    (is (= [0] (filter-even-double [0])))))

(deftest test-compose
  (testing "compose function"
    (is (= 25 (increment-then-square 4)))
    (is (= 17 (square-then-increment 4)))
    (is (= 100 ((compose square double-num) 5)))))

;; Запуск тестов
(defn -main []
  (println "\n=== Запуск тестов ===")
  (run-tests 'dz3)
  (println "======================="))

;; Вызов основной функции при запуске файла
(-main)