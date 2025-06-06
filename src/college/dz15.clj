(ns college.dz15)

;; === Flock ===
;; Чистые функции для объединения и размножения
(defn conjoin [x y]
  (+ x y))

(defn breed [x y]
  (* x y))

;; Исходные значения "стая чаек"
(def flock-a 4)
(def flock-b 2)
(def flock-c 0)

(def result
  (let [step1 (conjoin flock-a flock-c) ; 4 + 0 = 4
        step2 (breed step1 flock-b)     ; 4 * 2 = 8
        step3 (breed flock-a flock-b)   ; 4 * 2 = 8
        step4 (conjoin step2 step3)]    ; 8 + 8 = 16
    step4))

;; === Чистая функция: длина строки ===
(defn string-length [s]
  (count s))

;; === Нечистая и чистая версия инкремента ===
(def counter (atom 0)) ; глобальная переменная (НЕЧИСТАЯ)

(defn impure-increment []
  (swap! counter inc)) ; мутирует состояние

(defn pure-increment [x]
  (inc x)) ; чисто и прозрачно

;; === Чистая обработка чисел ===
(defn process-numbers [numbers]
  (->> numbers
       (filter pos?)       ; оставляем положительные
       (map #(* % 3))      ; умножаем на 3
       (remove even?)      ; убираем чётные
       (sort)))            ; сортируем по возрастанию

;; === Проверка ===
(defn -main []
  (println "== Flock Result ==" result)
  (println "== String Length of 'Functional' ==" (string-length "Functional"))
  (println "== Pure Increment of 10 ==" (pure-increment 10))
  (println "== Processed Numbers ==" (process-numbers [-2 0 1 2 3 4 5])))

;; Для запуска: clj -M -m college.dz15
