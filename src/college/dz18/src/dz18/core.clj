(ns dz18.core
  (:require [clojure.java.io :as io])
  (:import (java.util ArrayList)
           (java.io File)))

;; База данных книг
(def books-db
  {2021 {"Программирование на Clojure" 100.0
         "Backend в IThub" 400.0}
   2022 {"JavaScript для профессионалов" 250.0
         "Основы Data Science" 350.0}})

;; Файл для хранения заказов
(def orders-file "orders.edn")

;; Вспомогательные функции
(defn calculate-price [book-name quantity year]
  (* quantity (get-in books-db [year book-name])))

(defn save-order [order]
  (spit orders-file (pr-str order) :append true))

(defn load-orders []
  (if (.exists (File. orders-file))
    (read-string (str "[" (slurp orders-file) "]"))
    []))

(defn display-orders []
  (let [orders (load-orders)]
    (if (empty? orders)
      (println "Пока нет заказов")
      (doseq [order orders]
        (println (format "Куплено %d шт. %s за R%.1f"
                         (:quantity order)
                         (:book-name order)
                         (:total order)))))))

;; Меню
(defn main-menu []
  (println "|       Наши книги       |")
  (println "| 1-Меню 2-Заказы 3-Выход|")
  (print "Выберите опцию: ")
  (flush)
  (read-line))

(defn years-menu []
  (println "| Доступные книги по годам |")
  (doseq [year (keys books-db)]
    (print (format "| %d. %d " (.indexOf (vec (keys books-db)) year) year)))
  (println "|")
  (print "Выберите год: ")
  (flush)
  (read-line))

(defn books-menu [year]
  (println (format "| Книги за %d |" year))
  (let [books (get books-db year)]
    (doseq [[i [book-name _]] (map-indexed vector books)]
      (println (format "| %d. %s " (inc i) book-name)))
    (println "|")
    (print "Выберите книгу: ")
    (flush)
    (read-line)))

  (defn quantity-menu [book-name]
    (println (format "Сколько экземпляров '%s' хотите приобрести?" book-name))
    (print "Количество: ")
    (flush)
    (read-line))

  ;; Основные функции
  (defn order-book []
    (let [year-input (years-menu)
          year (nth (sort (keys books-db)) (dec (Integer/parseInt year-input)))
          book-input (books-menu year)
          book-name (nth (keys (get books-db year)) (dec (Integer/parseInt book-input)))
          quantity-input (quantity-menu book-name)
          quantity (Integer/parseInt quantity-input)
          total (calculate-price book-name quantity year)
          order {:book-name book-name :quantity quantity :year year :total total}]
      (save-order order)
      (println (format "Покупка %d шт. %s всего за: R%.1f" quantity book-name total))))

  (defn show-orders []
    (println "=== Ваши заказы ===")
    (display-orders))

  ;; Работа с Java
  (defn java-interop-demo []
    (println "\n=== Java Interop Demo ===")
    ;; 1. ArrayList
    (let [al (ArrayList.)]
      (doto al
        (.add "Clojure")
        (.add "Java")
        (.add "Python"))
      (println "ArrayList contents:" al))

    ;; 2. System properties
    (println "OS Name:" (System/getProperty "os.name"))

    ;; 3. File check
    (let [f (File. orders-file)]
      (println (format "Файл заказов %s существует: %s"
                       orders-file
                       (.exists f)))))

  ;; Главная функция
  (defn -main []
    (java-interop-demo)
    (println "\n=== Книжный магазин ===")
    (loop []
      (let [input (main-menu)]
        (cond
          (= input "1") (do (order-book) (recur))
          (= input "2") (do (show-orders) (recur))
          (= input "3") (println "До свидания!")
          :else (do (println "Неверный ввод, попробуйте еще раз") (recur))))))