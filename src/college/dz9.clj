(ns college.dz9)

;;; 1. Реализация собственной функции reverse

(defn my-reverse
  "Инвертирует список (без использования встроенного reverse)."
  [coll]
  (loop [src coll
         acc '()]
    (if (empty? src)
      acc
      (recur (rest src) (cons (first src) acc)))))

;;; 2. Числа Фибоначчи (простая рекурсия и оптимизированная)

(defn fib
  "Рекурсивная (медленная) реализация вычисления n-го числа Фибоначчи."
  [n]
  (cond
    (= n 0) 0
    (= n 1) 1
    :else (+ (fib (- n 1)) (fib (- n 2)))))

(defn fast-fib
  "Оптимизированная реализация с хвостовой рекурсией. Работает с BigInteger."
  [n1 n2 counter]
  (let [n1 (bigint n1)
        n2 (bigint n2)]
    (loop [a n1
           b n2
           cnt counter]
      (if (= cnt 1)
        a
        (recur b (+ a b) (dec cnt))))))

;;; 3. Список из 20 случайных чисел

(defn rand-list
  "Создает список из 20 случайных чисел от 0 до 99."
  []
  (repeatedly 20 #(rand-int 100)))

;;; 4. Удаление чётных чисел из списка

(defn remove-evens
  "Удаляет все чётные числа из списка."
  [coll]
  (remove even? coll))

;;; 5. Рекурсивная сумма всех чисел во вложенном списке

(defn deep-sum
  "Подсчитывает сумму всех чисел во вложенном списке."
  [lst]
  (cond
    (empty? lst) 0
    (sequential? (first lst)) (+ (deep-sum (first lst)) (deep-sum (rest lst)))
    (number? (first lst)) (+ (first lst) (deep-sum (rest lst)))
    :else (deep-sum (rest lst))))

;;; 6. Хвостовая рекурсия для суммы элементов списка

(defn tail-sum
  "Считает сумму элементов списка с использованием хвостовой рекурсии."
  ([lst] (tail-sum lst 0))
  ([lst acc]
   (if (empty? lst)
     acc
     (recur (rest lst) (+ acc (first lst))))))

(comment
  ;; Примеры использования:

  ;; reverse
  (my-reverse [1 2 3 4]) ;=> (4 3 2 1)

  ;; Фибоначчи
  (fib 10) ;=> 55
  (fast-fib 1 1 1000) ;=> 1000-е число Фибоначчи

  ;; случайный список
  (rand-list) ;=> например, (45 3 76 ...)

  ;; удаление чётных
  (remove-evens [1 2 3 4 5 6]) ;=> (1 3 5)

  ;; вложенные суммы
  (deep-sum [1 [2 3 [4]] 5]) ;=> 15

  ;; хвостовая сумма
  (tail-sum [1 2 3 4]) ;=> 10
  )

#_{:clj-kondo/ignore [:unused-binding]}
(defn -main
  "Точка входа программы. Примерный запуск демонстрации функций."
  [& args]
  (println "my-reverse:" (my-reverse [1 2 3 4]))
  (println "fib 10:" (fib 10))
  (println "fast-fib 1000:" (fast-fib 1N 1N 1000))
  (println "random list:" (rand-list))
  (println "remove evens:" (remove-evens [1 2 3 4 5 6]))
  (println "deep-sum:" (deep-sum [1 [2 3 [4]] 5]))
  (println "tail-sum:" (tail-sum [1 2 3 4])))
