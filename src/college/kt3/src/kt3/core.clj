(ns kt3.core
  (:require [kt3.server :as server]
            [ring.adapter.jetty :as jetty]
            [clojure.tools.cli :refer [parse-opts]]))

(def cli-options
  [["-p" "--port PORT" "Port number"
    :default 3000
    :parse-fn #(Integer/parseInt %)
    :validate [#(< 0 % 0x10000) "Must be a number between 0 and 65536"]]])

(defn -main
  "URL Shortener Service"
  [& args]
  (let [{:keys [options errors]} (parse-opts args cli-options)]
    (if errors
      (doseq [err errors]
        (println err))
      (do
        (println (str "Starting server on port " (:port options)))
        (server/init-db)
        (jetty/run-jetty server/app {:port (:port options) :join? true})))))