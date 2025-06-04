(ns college.dz5
  (:require [clojure.string :as str]))

;; 1. Функция формирования почтового адреса
(defn get-location
  "Возвращает локацию с учётом специальных требований для Владивостока"
  [name city]
  (if (= city "Владивосток")
    (str "Многоуважаемый(ая) " name ", " city)
    (str name ", " city)))

;; 2. Функция возведения чисел в квадрат
(defn square-numbers
  "Принимает список чисел и возвращает список их квадратов"
  [numbers]
  (map #(* % %) numbers))

;; 3. Функция создания замыкания для умножения
(defn multiplier
  "Создаёт замыкание, которое умножает на заданное число"
  [n]
  (fn [x] (* x n)))

;; Переменные для замыкания
(def multiply-by-5 (multiplier 5))
(def multiply-by-half (multiplier 0.5))

;; 4. Примеры использования partial
(def add-10 (partial + 10))               ; Функция, добавляющая 10
(def greet (partial str "Привет, "))      ; Функция приветствия

(defn greet-with [greeting name punctuation]
  (str greeting name punctuation))

(def greet-excited (partial greet-with "Ура, ")) ; Частичное применение greet-with

;; 5. Примеры использования comp
(def negated-square (comp - #(* % %)))                  ; Отрицательный квадрат
(def process-string (comp str/upper-case str/trim str/reverse)) ; Обработка строки
(def calculate (comp #(* % 100) #(+ % 10) #(* % 2)))     ; Композиция вычислений

;; Вывод результатов в консоль
(println "=== Результаты работы функций ===")

;; 1. Тестирование get-location
(println "\n1. Тестирование функции get-location:")
(println "Обычный город: " (get-location "Иван Петров" "Москва"))
(println "Владивосток: " (get-location "Мария Иванова" "Владивосток"))

;; 2. Тестирование square-numbers
(println "\n2. Тестирование функции square-numbers:")
(println "Квадраты [1 2 3 4]: " (square-numbers [1 2 3 4]))
(println "Квадраты []: " (square-numbers []))
(println "Квадраты [0.5 -2]: " (square-numbers [0.5 -2]))

;; 3. Тестирование multiplier
(println "\n3. Тестирование функции multiplier:")
(println "Умножение 10 на 5: " (multiply-by-5 10))
(println "Умножение 8 на 0.5: " (multiply-by-half 8))

;; 4. Тестирование partial и comp
(println "\n4. Тестирование partial и comp:")

(println "\nPartial применение:")
(println "add-10(5): " (add-10 5))
(println "greet(\"мир!\"): " (greet "мир!"))
(println "greet-excited(\"Вася\", \"!!!\"): " (greet-excited "Вася" "!!!"))

(println "\nКомпозиция функций:")
(println "negated-square(5): " (negated-square 5))
(println "process-string(\"  hello  \"): " (process-string "  hello  "))
(println "calculate(5): " (calculate 5))

(println "\n=== Тестирование завершено ===")
