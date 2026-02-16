(ns fegloj.main
  (:require [scicloj.clay.v2.api :as clay]
            [clojure.main :as cm]
            [clojure.java.io :as io])
  (:import [javax.swing JTextArea JScrollPane JButton JPanel JFrame WindowConstants]
           [java.awt BorderLayout]
           [java.awt.event ActionListener]
           [java.io Writer PrintWriter])
  (:gen-class :name Fegloj))

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

(defn tee-writer
  "Creates a writer that writes to multiple writers"
  [& writers]
  (proxy [Writer] []
    (write
      ([cbuf off len]
       (doseq [w writers]
         (.write w cbuf off len)))
      ([x]
       (doseq [w writers]
         (.write w x))))
    (flush []
      (doseq [w writers]
        (.flush w)))
    (close []
      (doseq [w writers]
        (.close w)))))

(defn text-area-writer
  "Creates a writer that appends to a JTextArea"
  [log-area]
  (proxy [Writer] []
    (write
      ([cbuf off len]
       (if (string? cbuf)
         (.append log-area cbuf)
         (.append log-area (String. ^chars cbuf ^int off ^int len))))
      ([x]
       (.append log-area (str x))))
    (flush [])
    (close [])))

(defn show-quit-button
  "Shows a simple quit button for when running without a terminal"
  []
  (let [log-area (doto (JTextArea. 20 50)
                   (.setEditable false)
                   (.setLineWrap true)
                   (.setWrapStyleWord true))
        scroll-pane (JScrollPane. log-area)
        browse-button (doto (JButton. "Open Clay Browser")
                        (.addActionListener
                          (reify ActionListener
                            (actionPerformed [_ _] (clay/browse!)))))
        quit-button (doto (JButton. "Quit Fegloj")
                      (.addActionListener
                        (reify ActionListener
                          (actionPerformed [_ _] (System/exit 0)))))
        button-panel (doto (JPanel.)
                       (.add browse-button)
                       (.add quit-button))
        panel (doto (JPanel. (BorderLayout.))
                (.add scroll-pane BorderLayout/CENTER)
                (.add button-panel BorderLayout/SOUTH))
        frame (doto (JFrame. "Fegloj")
                (.setDefaultCloseOperation WindowConstants/EXIT_ON_CLOSE))
        icon-url (io/resource "Clay.svg.png")]
    ;; Tee output to both terminal and log area
    (let [log-w (text-area-writer log-area)]
      (alter-var-root #'*out* (constantly (PrintWriter. (tee-writer *out* log-w) true)))
      (alter-var-root #'*err* (constantly (PrintWriter. (tee-writer *err* log-w) true))))
    (when icon-url
      (.setIconImage frame (javax.imageio.ImageIO/read (io/input-stream icon-url))))
    (doto frame
      (.add panel)
      (.setSize 600 400)
      (.setLocationRelativeTo nil)
      (.setVisible true))))

(defn -main []
  (println "Starting Fegloj...")
  (show-quit-button)
  (default-notebook)
  (println "Starting Clay with live-reload...")
  (clay/make! {:live-reload true
               :source-path "my_notebook.clj"
               :base-source-path "notebooks"
               :base-target-path "temp"})
  (println "\nFegloj is running!")
  (println "Edit notebooks/my_notebook.clj and save to see updates in your browser.")
  ;; Only start REPL if running in a terminal (not double-clicked from GUI)
  (if (System/console)
    (do
      (println "\nREPL ready:")
      (cm/repl))
    (do
      (println "\nNo terminal detected. Use the Quit button to exit.")
      ;; Keep the app running
      (while true (Thread/sleep 10000)))))
