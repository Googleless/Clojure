(ns college.dz12)

;; 1. Ленивый список кубов чисел
(def cubes
  (map #(Math/pow % 3) (iterate inc 1))) ;; (1³, 2³, 3³, ...)

;; 2. Ленивый список чисел, кратных 3 или 5
(def divisible-by-3-or-5
  (filter #(or (zero? (mod % 3)) (zero? (mod % 5))) (iterate inc 1))) ;; (3, 5, 6, 9, 10, ...)

;; 3. Функция генерации ленивой последовательности Фибоначчи с произвольными начальными значениями
(defn lazy-fib [a b]
  (lazy-seq (cons a (lazy-fib b (+ a b)))))

;; Примеры:
#_{:clojure-lsp/ignore [:clojure-lsp/unused-public-var]}
(def fib-from-1-1 (lazy-fib 1 1))   ;; 1 1 2 3 5 8 ...
#_{:clojure-lsp/ignore [:clojure-lsp/unused-public-var]}
(def fib-from-2-3 (lazy-fib 2 3))   ;; 2 3 5 8 13 ...

;; Для запуска и демонстрации:
(defn -main []
  (println "=== Первые 10 кубов ===")
  (println (take 10 cubes))

  (println "\n=== Первые 15 чисел, кратных 3 или 5 ===")
  (println (take 15 divisible-by-3-or-5))

  (println "\n=== Первые 10 чисел Фибоначчи (1, 1) ===")
  (println (take 10 (lazy-fib 1 1)))

  (println "\n=== Первые 10 чисел Фибоначчи (2, 3) ===")
  (println (take 10 (lazy-fib 2 3))))
