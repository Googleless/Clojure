(ns dz17.core
  (:gen-class))

(defn sum-numbers
  "Суммирует список чисел"
  [numbers]
  (reduce + numbers))

(defn -main
  "Основная функция, принимающая аргументы командной строки"
  [& args]
  (try
    (let [numbers (map #(Long/parseLong %) args)
          result (sum-numbers numbers)]
      (println result))
    (catch NumberFormatException e
      (println "Ошибка: все аргументы должны быть целыми числами")
      (System/exit 1))))
