(ns unmark.core
  (:require [unmark.impl :refer :all]
            [unmark.posts :refer :all]))


(defpost "About"
  "about"
  (postbody
   (section "About me. About this site."
     "I'm a software developer living (mostly) in Chicago, Illinois, USA.  I
     currently work at OpinionLab, where I am a senior dev on their backend
     Clojure team."  "I care about making outstanding software and outstanding
     teams based on respect, communication, focus, care, and craft.  I'm also
     passionate about art and science (I went to graduate school in art and
     physics and have a doctorate in high energy particle astrophysics).")))


(defpost "Working With Me"
  "working-w-me"
  {:created "2015-11-14"
   :draft true}
  (postbody
   (section "Working with Me"
     (epigraph "Great things are done by a series of small things brought
       together." "Vincent Van Gogh")
     (subsection "Practices I Like"
       (subsubsection "“Test First”")
       (subsubsection "Promiscuous Pairing")
       (subsubsection "Solo Programming")
       (subsubsection "Retrospectives")
       (subsubsection "Kanban over Scrum")
       (subsubsection "Clean code"
         "Whenever I touch a piece of code, I try to leave it in better shape
         than I found it in.  I use style guides and build style checking
         tools into automated builds, because I've found that better and more
         readable code results.")
       (subsubsection "Continuous Integration"))
     (subsection "Default Tools"
       "I use the following tools on a daily or near-daily basis."
       (subsubsection "Clojure")
       (subsubsection "Emacs"
         [:span "Configuration "
          [:a {:href "http://github.com/eigenhombre/emacs-config"} "here"]
          "."])
       (subsubsection "Docker"))
     (subsection "Technical Strengths"
       (subsubsection "Troubleshooting and debugging")))))


(spit
 "index.html"
 (all-content))


(clojure.java.shell/sh "open" "index.html")
