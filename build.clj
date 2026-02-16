;; Build tasks for fegloj
;; Usage:
;;   clj -T:build clean              ;; Clean build artifacts
;;   clj -T:build uber               ;; Build an uberjar
;;   clj -T:build jpackage           ;; Create native app image
;;   clj -T:build jpackage-installer ;; Create installer (deb/msi/dmg for Linux/Windows/macOS)
;;   java -jar target/fegloj-*-standalone.jar  ;; Run the jar
;;
;; After building the installer:
;;   Linux:   sudo dpkg -i target/package/fegloj_0.1.0-SNAPSHOT_amd64.deb
;;   Windows: Run the .msi installer from target/package/
;;   macOS:   Open the .dmg from target/package/ and drag to Applications
;;
;; To create a GitHub release with installers for all platforms:
;;   git tag v0.1.0
;;   git push origin v0.1.0
;;   (GitHub Actions will automatically build installers for Linux/Windows/macOS)

(ns build
  (:require [clojure.tools.build.api :as b]
            [clojure.java.shell :as sh]))

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
           :main 'Fegloj
           :exclude-patterns ["META-INF/.*"]}))

(defn jpackage [_]
  (println "Building uberjar first...")
  (uber nil)
  (println "\nCreating native package with jpackage...")
  (let [result (sh/sh "jpackage"
                      "--input" "target"
                      "--name" "Fegloj"
                      "--main-jar" (str (name lib) "-" version "-standalone.jar")
                      "--main-class" "Fegloj"
                      "--type" "app-image"
                      "--icon" "resources/Clay.svg.png"
                      "--app-version" version
                      "--vendor" "Fegloj"
                      "--description" "Fegloj - A Clojure notebook environment"
                      "--dest" "target/package")]
    (if (zero? (:exit result))
      (println "\n✓ Package created in target/package/")
      (do
        (println "\n✗ jpackage failed:")
        (println (:err result))
        (println (:out result))))))

(defn jpackage-installer
  "Create a native installer package for the current platform"
  [_]
  (println "Building uberjar first...")
  (uber nil)
  (let [os-name (System/getProperty "os.name")
        [installer-type icon-file extra-args]
        (cond
          (re-find #"(?i)windows" os-name)
          ["msi" "resources/Clay.svg.png"
           ["--win-shortcut" "--win-menu" "--win-menu-group" "Fegloj"]]

          (re-find #"(?i)mac" os-name)
          ["dmg" "resources/Clay.svg.png"
           ["--mac-package-identifier" "com.fegloj.Fegloj"]]

          :else ; Linux
          ["deb" "resources/Clay.svg.png"
           ["--linux-shortcut" "--linux-menu-group" "Development"
            "--linux-app-category" "Development"
            "--linux-deb-maintainer" "timothypratley@gmail.com"
            "--resource-dir" "package/linux"]])]
    (println (str "\nCreating " installer-type " installer package for " os-name "..."))
    (let [result (apply sh/sh
                   (concat
                     ["jpackage"
                      "--input" "target"
                      "--name" "Fegloj"
                      "--main-jar" (str (name lib) "-" version "-standalone.jar")
                      "--main-class" "Fegloj"
                      "--type" installer-type
                      "--icon" icon-file
                      "--app-version" version
                      "--vendor" "Fegloj"
                      "--description" "Fegloj - A Clojure notebook environment"
                      "--dest" "target/package"]
                     extra-args))]
      (if (zero? (:exit result))
        (do
          (println (str "\n✓ Installer package created in target/package/"))
          (when (= installer-type "deb")
            (println "Install with: sudo dpkg -i target/package/fegloj_" version "_amd64.deb")))
        (do
          (println "\n✗ jpackage failed:")
          (println (:err result))
          (println (:out result)))))))
