(ns college.strings 
  (:require
   [clojure.string :as str]))

(defn to-upper [s]
  (str/upper-case s))

(defn trim-spaces [s]
  (str/trim s))

#_{:clj-kondo/ignore [:unused-private-var]}
(defn- private-function []
  (println "Эта функция недоступна извне"))