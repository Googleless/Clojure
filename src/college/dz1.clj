(ns college.dz1
  (:require [clojure.spec.alpha :as s]))

;; 1. Функция: умножить чётные числа на 2
(defn even-times-two [nums]
  (filter even? (map #(* % 2) nums)))

;; 2. Спецификация пользователя
(s/def ::name string?)
(s/def ::age (s/and int? #(>= % 18)))
(s/def ::email #(re-matches #"^[\w.-]+@[\w.-]+\.[a-zA-Z]{2,}$" %))
(s/def ::user (s/keys :req [::name ::age ::email]))

;; 3. Проверка пользователя по спецификации
(defn valid-user? [user]
  (s/valid? ::user user))

;; 4. Точка входа
(defn -main []
  (println "even-times-two [1 2 3 4 5 6]:" (even-times-two [1 2 3 4 5 6])) ; => (4 8 12)

  (println "valid-user? Иван:"
           (valid-user? {::name "Иван" ::age 25 ::email "ivan@example.com"})) ; => true

  (println "valid-user? Анна:"
           (valid-user? {::name "Анна" ::age 17 ::email "anna@bademail"})))     ; => false
