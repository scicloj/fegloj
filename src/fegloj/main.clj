(ns fegloj.main
  (:require [clooj.core :as cc]
            [clooj.project :as project]
            [scicloj.clay.v2.api :as clay]
            [clojure.main :as cm]
            [clojure.java.io :as io])
  (:gen-class))

(defn default-notebook []
  ;; create default notebook if it doesn't exist
  (let [notebooks-dir (io/file "notebooks")
        default-notebook (io/file notebooks-dir "my_notebook.clj")]
    (.mkdirs notebooks-dir)
    (when-not (.exists default-notebook)
      (io/make-parents default-notebook)
      (spit default-notebook "(ns my-notebook)

;; # My Notebook

;; A clay notebook for exploring Clojure

(+ 1 2 3)"))))

(defn open-notebook []
    ;; automatically open current directory as a project
  (let [app @cc/current-app
        project-dir (io/file ".")
        abs-path (.getAbsolutePath project-dir)
        default-notebook (io/file "notebooks" "my_notebook.clj")]
    (project/add-project app abs-path)
    (project/update-project-tree (:docs-tree app))
    (when-let [clj-file (or (-> (io/file project-dir "src")
                               .getAbsolutePath
                               (project/get-code-files ".clj")
                               first)
                            project-dir)]
      (project/set-tree-selection (:docs-tree app) (.getAbsolutePath clj-file)))
    ;; open the default notebook in the editor
    (when (.exists default-notebook)
      (cc/restart-doc app default-notebook))))

(defn -main []
  (default-notebook)
  (clay/make! {:live-reload true
               :source-path "my_notebook.clj"
               :base-source-path "notebooks"
               :base-target-path "temp"})
  (cc/-show)
  (open-notebook)
  (println "REPL started")
  (cm/repl))
