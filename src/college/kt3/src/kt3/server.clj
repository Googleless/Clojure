(ns kt3.server
  #_{:clj-kondo/ignore [:unused-namespace]}
  (:require [ring.adapter.jetty :as jetty]
            [ring.middleware.json :as middleware]
            [ring.util.response :as response]
            [compojure.core :refer [defroutes GET POST PUT DELETE]]
            [compojure.route :as route]
            [clojure.java.jdbc :as jdbc]
            [cheshire.core :as json]))

(def db-spec {:classname "org.h2.Driver"
              :subprotocol "h2"
              :subname "./url_shortener"
              :user "sa"
              :password ""})

(defn init-db []
  (jdbc/with-db-connection [db db-spec]
    (jdbc/execute! db ["CREATE TABLE IF NOT EXISTS urls (
                       id INTEGER AUTO_INCREMENT PRIMARY KEY,
                       short_url VARCHAR(20) UNIQUE,
                       normal_url VARCHAR(255))"])))

(defn generate-short-url []
  (let [chars "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789"
        length 6]
    (apply str (repeatedly length #(rand-nth chars)))))

(defn create-url [normal-url]
  (jdbc/with-db-transaction [db db-spec]
    (let [short-url (generate-short-url)]
      (jdbc/insert! db :urls {:short_url short-url :normal_url normal-url})
      short-url)))

(defn get-normal-url [short-url]
  (jdbc/with-db-connection [db db-spec]
    (-> (jdbc/query db ["SELECT normal_url FROM urls WHERE short_url = ?" short-url])
        first
        :normal_url)))

(defn update-url [short-url new-normal-url]
  (jdbc/with-db-transaction [db db-spec]
    (let [updated (jdbc/update! db :urls
                                {:normal_url new-normal-url}
                                ["short_url = ?" short-url])]
      (if (pos? (first updated))
        200
        404))))

(defn delete-url [short-url]
  (jdbc/with-db-transaction [db db-spec]
    (let [deleted (jdbc/delete! db :urls ["short_url = ?" short-url])]
      (if (pos? (first deleted))
        200
        404))))

(defroutes app-routes
  (GET "/:short-url" [short-url]
    (if-let [normal-url (get-normal-url short-url)]
      (-> (response/response {:url normal-url})
          (response/status 200)
          (response/content-type "application/json"))
      (-> (response/response {:error "URL not found"})
          (response/status 404)
          (response/content-type "application/json"))))

  (POST "/" {body :body}
    (let [normal-url (get body :url)
          short-url (create-url normal-url)]
      (-> (response/response {:short_url short-url})
          (response/status 201)
          (response/content-type "application/json"))))

  (PUT "/:short-url" [short-url :as {body :body}]
    (let [new-normal-url (get body :url)
          status (update-url short-url new-normal-url)]
      (response/status (response/response {}) status)))

  (DELETE "/:short-url" [short-url]
    (let [status (delete-url short-url)]
      (response/status (response/response {}) status)))

  (route/not-found
   (-> (response/response {:error "Not found"})
       (response/status 404)
       (response/content-type "application/json"))))

(def app
  (-> app-routes
      (middleware/wrap-json-body {:keywords? true})
      (middleware/wrap-json-response)))

(defn -main []
  (init-db)
  (jetty/run-jetty app {:port 3000}))