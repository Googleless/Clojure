(defn double-elements [coll]
  (map #(* % 2) coll))

(println (double-elements [1 2 3])) ;; (2 4 6)

(defn sum-elements [coll]
  (reduce + coll))

(println (sum-elements [1 2 3])) ;; 6