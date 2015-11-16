(ns unmark.core
  (:require [unmark.impl :refer :all]
            [unmark.posts :refer :all]))


;; REPL only, don't 'lein run' for now...
(generate-blog!)
(clojure.java.shell/sh "open" "index.html")
