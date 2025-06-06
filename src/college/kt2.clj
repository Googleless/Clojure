(ns college.kt2
  (:require [clojure.string :as str]))

;; === Типы и данные ===

(defrecord Position [x y])
(defrecord PlotterState [position angle color carriage-state])

(def ^:const carriage-up :up)
(def ^:const carriage-down :down)

(def ^:const color-black :черный)
(def ^:const color-red :красный)
(def ^:const color-green :зелёный)

;; === Вспомогательные функции ===

(defn printer [s]
  (println s))

(defn draw-line [prt from to color]
  (prt (format "...Чертим линию из (%d, %d) в (%d, %d) используя %s цвет."
               (:x from) (:y from) (:x to) (:y to) (name color))))

(defn calc-new-position [distance angle current]
  ;; Угол в радианах (180 градусов = π)
  (let [angle-rad (* angle (/ Math/PI 180.0))
        x (+ (:x current) (* distance (Math/cos angle-rad)))
        y (+ (:y current) (* distance (Math/sin angle-rad)))]
    (->Position (Math/round x) (Math/round y))))

;; === Команды плоттера ===

(defn move [prt distance state]
  (let [new-pos (calc-new-position distance (:angle state) (:position state))]
    (if (= (:carriage-state state) carriage-down)
      (draw-line prt (:position state) new-pos (:color state))
      (prt (format "Передвигаем на %d от точки (%d, %d)" distance (:x (:position state)) (:y (:position state)))))
    (assoc state :position new-pos)))

(defn turn [prt angle state]
  (prt (format "Поворачиваем на %d градусов" angle))
  (let [new-angle (mod (+ (:angle state) angle) 360)]
    (assoc state :angle new-angle)))

(defn carriage-up [prt state]
  (prt "Поднимаем каретку")
  (assoc state :carriage-state carriage-up))

(defn carriage-down [prt state]
  (prt "Опускаем каретку")
  (assoc state :carriage-state carriage-down))

(defn set-color [prt color state]
  (prt (format "Устанавливаем %s цвет линии." (name color)))
  (assoc state :color color))

(defn set-position [prt position state]
  (prt (format "Устанавливаем позицию каретки в (%d, %d)." (:x position) (:y position)))
  (assoc state :position position))

;; === Фигуры ===

(defn draw-triangle [prt size state]
  (let [state (carriage-down prt state)]
    (loop [i 0
           st state]
      (if (= i 3)
        (carriage-up prt st)
        (let [st1 (move prt size st)
              st2 (turn prt 120 st1)]
          (recur (inc i) st2))))))

(defn draw-square [prt size state]
  (let [state (carriage-down prt state)]
    (loop [i 0
           st state]
      (if (= i 4)
        (carriage-up prt st)
        (let [st1 (move prt size st)
              st2 (turn prt 90 st1)]
          (recur (inc i) st2))))))

;; === Инициализация состояния ===

(defn initialize-plotter-state [position angle color carriage-state]
  (->PlotterState position angle color carriage-state))

;; === Пример использования ===

(defn -main []
  (let [printer-fn printer
        init-pos (->Position 0 0)
        init-color color-black
        init-angle 0
        init-carriage carriage-up
        init-state (initialize-plotter-state init-pos init-angle init-color init-carriage)
        state1 (draw-triangle printer-fn 100 init-state)
        state2 (set-position printer-fn (->Position 10 10) state1)
        state3 (set-color printer-fn color-red state2)
        state4 (draw-square printer-fn 80 state3)]
    (println "Чертеж завершён.")))
