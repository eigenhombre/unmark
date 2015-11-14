(ns unmark.core
  (:require [hiccup.core :refer :all]))


(defn ^:private head []
  [:head [:link {:rel "stylesheet", :href "resources/tufte-css/tufte.css"}]])


(def sections (atom []))

(defn section [header & body]
  (swap! sections conj header)
  `([:h1 ~header]
    ~@(map (partial vector :p) (remove map? body))))


(defn subsection [header & body]
  `([:h2 ~header]
    ~@(map (partial vector :p) (remove map? body))))


(defn subsubsection [header & body]
  `([:h3 ~header]
    ~@(map (partial vector :p) (remove map? body))))


(defn content [& body]
  (html
   (head)
   (vec (list* :body body))))


(defn sidenote [txt]
  (let [cls (str (gensym))]
    [:span
     [:label {:for cls :class "margin-toggle sidenote-number"}]
     [:input {:type "checkbox" :id cls :class "margin-toggle"}]
     [:span {:class "sidenote"} txt]]))


(defn code [txt]
  [:pre [:code txt]])


(spit
 "index.html"
 (content
  (section "Working with Me"
    {:created "2015-11-14"}

    (subsection "Practices I Like"
      (subsubsection "\"Test First\"")
      (subsubsection "Promiscuous pairing"
        "\"You gettee in,\" he added, motioning to me with his tomahawk,
    and throwing the clothes to one side. He really did this in not
    only a civil but a really kind and charitable way. I stood looking
    at him a moment. For all his tattooings he was on the whole a
    clean, comely looking cannibal. What's all this fuss I have been
    making about, thought I to myself—the man's a human being just as
    I am: he has just as much reason to fear me, as I have to be
    afraid of him. Better sleep with a sober cannibal than a drunken
    Christian." " \"Landlord,\" said I, \"tell him to stash his
    tomahawk there, or pipe, or whatever you call it; tell him to stop
    smoking, in short, and I will turn in with him. But I don't fancy
    having a man smoking in bed with me. It's dangerous. Besides, I
    ain't insured.\""))
    (subsection "Tools I Use"
      (subsubsection "Clojure"
        [:span "Biltong capicola tri-tip strip steak sirloin
        ribeye cow ball tip sausage leberkas turkey swine
        kielbasa rump. Ribeye boudin ground round pork
        landjaeger, jerky fatback short loin tongue strip
        steak flank sirloin pig pastrami. Swine shankle
        tongue leberkas venison, biltong beef ribs flank
        spare ribs jerky bresaola tenderloin
        kielbasa. Pork filet mignon shoulder meatball
        andouille frankfurter biltong chicken swine doner
        alcatra boudin hamburger bresaola ribeye. T-bone
        sausage chicken, andouille frankfurter sirloin
        drumstick alcatra turkey venison salami. Pancetta
        pork chop shank, fatback ball tip"
         (sidenote "Enough meat for ya?")
         " corned beef drumstick pork belly hamburger pork
        loin porchetta pig. Pancetta t-bone prosciutto
        turkey, leberkas venison brisket andouille."])
      (subsubsection "Emacs"))
    (subsection "Expertise"
      "Code goes here."
      (code "(def times (iterate #(+ % (rand-int 1000)) 0))
;; Caution: infinite sequence...

(take 30 times)

;;=>
(0 955 1559 2063 2735 2858 3542 4067 4366 5246 5430 6168 7127 7932
 8268 8929 9426 9918 10436 10850 11680 12367 12569 13343 14155 14420
 15062 15171 15663 16355)

")))
  [:p (count @sections) " sections."]))

(clojure.java.shell/sh "open" "index.html")
