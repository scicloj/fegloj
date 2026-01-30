;; Build tasks for fegloj
;; Usage:
;;   clj -T:build clean    ;; Clean build artifacts
;;   clj -T:build uber     ;; Build an uberjar
;;   java -jar target/fegloj-*-standalone.jar  ;; Run the jar

(ns build
  (:require [clojure.tools.build.api :as b]))

(def lib 'fegloj)
(def version "0.1.0-SNAPSHOT")
(def class-dir "target/classes")
(def basis (b/create-basis {:project "deps.edn"}))
(def uber-file (format "target/%s-%s-standalone.jar" (name lib) version))

(defn clean [_]
  (b/delete {:path "target"}))

(defn uber [_]
  (clean nil)
  (b/compile-clj {:basis basis
                  :src-dirs ["src"]
                  :class-dir class-dir})
  (b/uber {:basis basis
           :class-dir class-dir
           :uber-file uber-file
           :main 'fegloj.main}))
