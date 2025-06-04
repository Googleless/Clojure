(ns college.dz8
  (:require [clojure.core.match :refer [match]]))

(defn my-rest [coll]
  (if (empty? coll)
    nil
    (rest coll)))

(defn gcd [a b]
  (match [a b]
    [x 0] x
    [x y] (recur y (mod x y))))

(defn sum-of-evens [coll]
  (loop [s coll acc 0]
    (if (empty? s)
      acc
      (let [x (first s)
            rest-s (rest s)]
        (recur rest-s (if (even? x) (+ acc x) acc))))))

(defn extract-user-info [data]
  (match [data]
    [{:user {:name name :address {:city city}}}] {:name name :city city}
    :else {:error "Invalid data"}))

(defn fizzbuzz [n]
  (match [(mod n 3) (mod n 5)]
    [0 0] "FizzBuzz"
    [0 _] "Fizz"
    [_ 0] "Buzz"
    [_ _] (str n)))

(defn -main []
  (println "my-rest []:" (my-rest []))
  (println "my-rest [1 2 3]:" (my-rest [1 2 3]))
  (println "gcd 48 18:" (gcd 48 18))
  (println "sum-of-evens [1 2 3 4 5 6]:" (sum-of-evens [1 2 3 4 5 6]))
  (println "extract-user-info {:user {:name \"Alice\" :address {:city \"Oslo\"}}}:"
           (extract-user-info {:user {:name "Alice" :address {:city "Oslo"}}}))
  (println "fizzbuzz 15:" (fizzbuzz 15))
  (println "fizzbuzz 9:" (fizzbuzz 9))
  (println "fizzbuzz 10:" (fizzbuzz 10))
  (println "fizzbuzz 7:" (fizzbuzz 7)))
