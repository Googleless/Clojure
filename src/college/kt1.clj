;; ;; Определение функции для вычисления факториала
;; (defn factorial [n]
;;   (if (<= n 1)
;;     1
;;     (* n (factorial (dec n)))))

;; ;; Пример использования
;; (println "Факториал числа 5:" (factorial 5))

;; выше шпора

(defn quicksort [coll]
  (if (empty? coll)
    []
    (let [pivot (first coll)
          less (filter #(< % pivot) (rest coll))
          greater (filter #(>= % pivot) (rest coll))]
      (concat (quicksort less) [pivot] (quicksort greater)))))

(defn guess-my-number [low high]
  (let [range (range low (inc high))
        sorted-range (quicksort (vec range))]
    (loop [possible-numbers sorted-range]
      (if (empty? possible-numbers)
        (println "Прости, угадать твоё число не получилось :(.")
        (let [mid (nth possible-numbers (quot (count possible-numbers) 2))]
          (println "Твоё число это" ( - mid 1) "?")
          (let [response (read-line)]
            (cond
              (= response "correct") (println "Ура! Я угадал твоё число!")
              (= response "bigger") (recur (filter #(> % mid) possible-numbers))
              (= response "smaller") (recur (filter #(< % mid) possible-numbers))
              :else (println "Неверный ввод."))))))))

(defn read-number []
  (try 
    (Integer/parseInt (read-line)) 
    (catch NumberFormatException e 
      (println "Неверный ввод. Введите число.")
      (read-number))))

(defn start []
  (println "Введите минимальное число: ")
  (let [a (read-number)] 
  (println "Введите максимальное число: ")
  (let [b (read-number)]
    (if (>= a b)
      (println "Ошибка, минимальное число не может быть больше или равно максимальному числу.")
      (do
        (println "Минимальное число:" a ", Максимальное число:" b)
        (guess-my-number a b))))))

(start)
