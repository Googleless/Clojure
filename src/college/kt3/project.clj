(defproject kt3 "0.1.0-SNAPSHOT"
  :description "URL shortener service"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.11.1"]
                 [ring/ring-core "1.9.6"]
                 [ring/ring-jetty-adapter "1.9.6"]
                 [compojure "1.7.0"]
                 [org.clojure/java.jdbc "0.7.12"]
                 [com.h2database/h2 "2.2.224"]
                 [ring/ring-json "0.5.1"]
                 [cheshire "5.11.0"]
                 [org.clojure/tools.cli "1.1.230"]]
  :main ^:skip-aot kt3.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all
                       :jvm-opts ["-Dclojure.compiler.direct-linking=true"]}})