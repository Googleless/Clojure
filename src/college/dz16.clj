(ns college.dz16
  (:require [college.strings :as strings]
            [clojure.string :as str]
            [clojure.set :refer [subset?] :exclude [join]]
            [clojure.pprint :refer [print-table]]))

(defn format-user [user]
  (let [[first-name last-name] (str/split user #"_")
        capitalized-first (strings/to-upper (str/trim first-name))
        capitalized-last (strings/to-upper (str/trim last-name))]
    (str capitalized-first " " capitalized-last)))

(defn -main []
  (let [users #{"иван_шаповал " " софья_кузина" "алина_мага " " павел_безусый" " иван_соболев "}
        ;; Демонстрация работы функций из strings
        demo-str "  тестовая строка  "
        _ (println "Демонстрация strings/to-upper:" (strings/to-upper demo-str))
        _ (println "Демонстрация strings/trim-spaces:" (strings/trim-spaces demo-str))

        formatted-users (set (map format-user users))
        admins #{"ИВАН ШАПОВАЛ" "ПАВЕЛ БЕЗУСЫЙ" "АЛИНА МАГА"}
        is-subset? (subset? admins formatted-users)]

    (println "\nАдминистраторы:")
    (print-table (map (fn [admin] {:user-name admin}) admins))

    (println "\nПроверка подмножества администраторов:" is-subset?)))

(-main)