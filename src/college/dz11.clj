(ns college.dz11
  (:require [clojure.string :as str]))

;; ========== РОБОТЫ ==========

(defn robot [[name attack hp]]
  (fn [message]
    (cond
      (= message :name) name
      (= message :attack) attack
      (= message :hp) hp
      (= message :all) [name attack hp]
      :else (throw (Exception. "Unknown message")))))

(defn robot-name [r] (r :name))
(defn robot-attack [r] (r :attack))
(defn robot-hp [r] (r :hp))

(defn damage [r amount]
  (robot [(robot-name r) (robot-attack r) (max 0 (- (robot-hp r) amount))]))

(defn fight-once [a b]
  [(damage b (robot-attack a)) (damage a (robot-attack b))])

(defn three-round-fight [a b]
  (let [[final-a final-b] (nth (iterate (fn [[a b]] (fight-once a b)) [a b]) 3)]
    (if (> (robot-hp final-a) (robot-hp final-b)) final-a final-b)))



(def robo1 (robot ["Alpha" 16 120]))
(def robo2 (robot ["Beta" 24 100]))
(def robo3 (robot ["Gamma" 12 150]))
(def boss   (robot ["Boss" 14 140]))

(def all-robots [robo1 robo2 robo3])
(def fight-with-boss (partial three-round-fight boss))
(def results (map fight-with-boss all-robots))
#_{:clojure-lsp/ignore [:clojure-lsp/unused-public-var]}
(def robots-hp-after (map robot-hp results))

;; ========== МАШИНА ==========

(defn car [model]
  (let [speed (atom 0)]
    {:model model
     :accelerate #(swap! speed + %)
     :brake (fn [amount] (swap! speed #(max 0 (- % amount))))
     :get-speed #(deref speed)}))

(def my-car (car "Volvo"))
((:accelerate my-car) 30)
((:brake my-car) 10)
(:get-speed my-car) ;; => 20

;; ========== БИБЛИОТЕКА ==========

(defn library []
  (let [books (atom [])]
    {:add-book #(swap! books conj %)
     :remove-book #(swap! books (fn [bs] (remove (partial = %) bs)))
     :find-book #(filter (fn [book] (clojure.string/includes? (clojure.string/lower-case book) (clojure.string/lower-case %))) @books)
     :all-books #(deref books)}))

(def my-library (library))
((:add-book my-library) "Clojure for the Brave")
((:add-book my-library) "Clean Code")
((:find-book my-library) "clojure")
((:remove-book my-library) "Clean Code")
(:all-books my-library)

;; ========== БАНК ==========

(defn account [id balance]
  (atom {:id id :balance balance}))

(defn bank []
  (let [accounts (atom {})]
    {:create-account
     (fn [id amount]
       (swap! accounts assoc id (account id amount)))

     :transfer
     (fn [from-id to-id amount]
       (let [from-acct (get @accounts from-id)
             to-acct (get @accounts to-id)]
         (when (and from-acct to-acct (>= (:balance @from-acct) amount))
           (swap! from-acct update :balance - amount)
           (swap! to-acct update :balance + amount))))

     :get-balance
     (fn [id]
       (let [acct (get @accounts id)]
         (when acct (:balance @acct))))

     :all-accounts
     (fn []
       (into {} (map (fn [[id a]] [id (:balance @a)]) @accounts)))}))

(def my-bank (bank))
((:create-account my-bank) :alice 1000)
((:create-account my-bank) :bob 500)
((:transfer my-bank) :alice :bob 200)
((:get-balance my-bank) :alice) ;; => 800
((:get-balance my-bank) :bob)   ;; => 700
((:all-accounts my-bank))       ;; => {:alice 800, :bob 700}

(defn -main []
  (println "=== Robots vs Boss ===")
  (doseq [[robot result] (map vector all-robots results)]
    (println (str (robot-name robot) " vs Boss → " (robot-name result) " wins")))

  (println "\n=== Car Speed ===")
  (println ((:get-speed my-car)))

  (println "\n=== Library Books ===")
  (println ((:all-books my-library)))

  (println "\n=== Bank Accounts ===")
  (println ((:all-accounts my-bank))))

