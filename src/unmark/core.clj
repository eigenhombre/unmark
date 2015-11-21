(ns unmark.core
  (:gen-class)
  (:require [unmark.impl :refer [generate-blog!]]
            [unmark.posts :refer :all]))


(defn -main [& _]
  (generate-blog! "/tmp/site"))


(comment
  ;; One way to REPL-drive this.  See also:
  ;; (global-set-key "\C-o1"
  ;; 		(lambda ()
  ;; 		  (interactive)
  ;; 		  (cider-interactive-eval
  ;; 		   "(in-ns 'unmark.core)
  ;;                     (generate-blog!)
  ;;                     (clojure.java.shell/sh \"open\"
  ;;                                            \"/tmp/site/index.html\")")))
  (do
    (generate-blog! "/tmp/site")
    (clojure.java.shell/sh "open" "/tmp/site/index.html")))
