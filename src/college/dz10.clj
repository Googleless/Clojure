(ns college.dz10
  (:require [clojure.string :as str]))

;;; 1. Реализация функции contains
(defn contains-list?
  "Возвращает true, если элемент присутствует в списке (по аналогии с contains? для множеств)."
  [lst val]
  (pos? (count (filter #(= % val) lst))))

;;; 2. Расширенная функция is-palindrome
(defn is-palindrome
  "Проверяет, является ли строка палиндромом (без учета регистра и пробелов)."
  [s]
  (let [normalized (->> s
                        (filter #(Character/isLetterOrDigit %))
                        (map #(Character/toLowerCase %)))]
    (= (seq normalized) (reverse normalized))))


;;; 3. Сумма гармонической последовательности
(defn harmonic
  "Вычисляет сумму гармонической последовательности от 1/2 до 1/(n+1), используя ленивую последовательность."
  [n]
  (reduce + (take n (map #(double (/ 1 %)) (iterate inc 2)))))

;;; 4. Произведение элементов списка через reduce
(defn product
  "Возвращает произведение элементов списка."
  [lst]
  (reduce * lst))

;;; 5. Преобразование списка строк в список заглавных версий
(defn to-uppercase-list
  "Преобразует список строк в список строк в верхнем регистре."
  [lst]
  (map clojure.string/upper-case lst))

;;; 6. Функция с partial — вычитание 5
(def subtract-5
  "Функция, которая всегда вычитает 5 из заданного числа."
  (partial - 5))

(comment
  ;; contains-list?
  (contains-list? [1 2 3 4] 3) ;=> true
  (contains-list? [1 2 3 4] 5) ;=> false

  ;; palindrome
  (is-palindrome "А роза упала на лапу Азора") ;=> true
  (is-palindrome "Не палиндром") ;=> false

  ;; harmonic
  (harmonic 5) ;=> 1/2 + 1/3 + 1/4 + 1/5 + 1/6

  ;; product
  (product [2 3 4]) ;=> 24

  ;; uppercase
  (to-uppercase-list ["clojure" "rocks"]) ;=> ("CLOJURE" "ROCKS")

  ;; partial
  (subtract-5 10) ;=> 5
  (map subtract-5 [10 15 20]) ;=> (5 10 15)
  )

#_{:clj-kondo/ignore [:unused-binding]}
(defn -main
  [& args]
  (println "contains-list? [1 2 3] 2:" (contains-list? [1 2 3] 2))
  (println "is-palindrome: \"А роза упала на лапу Азора\":" (is-palindrome "А роза упала на лапу Азора"))
  (println "harmonic 5:" (harmonic 5))
  (println "product [2 3 4]:" (product [2 3 4]))
  (println "to-uppercase-list [\"hello\" \"world\"]:" (to-uppercase-list ["hello" "world"]))
  (println "subtract-5 10:" (subtract-5 10)))
