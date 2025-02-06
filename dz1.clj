;; #1 (ывывыаывордполавпрдшвапождва)
(defn even-times-two [nums]
  (filter even? (map #(* % 2) nums)))

;; вроде работает
(even-times-two [1 2 3 4 5 6]) ; => (4 8 12)

;; #2 (e[skdg;jkdfxgldfg])
(require '[clojure.spec.alpha :as s])

;; спецификация пользователя (я ненавижу CLOJUREEEEE)
(s/def ::name string?)
(s/def ::age (s/and int? #(>= % 18)))
(s/def ::email #(re-matches #"^[\w.-]+@[\w.-]+\.[a-zA-Z]{2,}$" %))
(s/def ::user (s/keys :req [::name ::age ::email]))

;; #3 (,kmnfng)
(defn valid-user? [user]
  (s/valid? ::user user))

;; вроде работает x2
(valid-user? {::name "Иван" ::age 25 ::email "ivan@example.com"}) ; => true
(valid-user? {::name "Анна" ::age 17 ::email "anna@bademail"})   ; => false