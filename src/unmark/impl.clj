(ns unmark.impl
  (:require [hiccup.core :refer [html]]
            [me.raynes.fs :refer [copy-dir]]))


(defn spanify-vector-line
  "
  Allow user to type [\"string1\" \"string2\"] instead
  of [:span \"string1\" \"string2\"] in sections.
  "
  [line]
  (if (and (vector? line) (not (keyword? (first line))))
    `[:span ~@line]
    line))


(defn partial-section [level header body]
  `([~(keyword (str "h" level)) ~header]
    ~@(->> body
           (remove map?)
           (map spanify-vector-line)
           (map (partial vector :p)))))


(defn section [header & body]
  (partial-section 1 header body))


(defn subsection [header & body]
  (partial-section 2 header body))


(defn subsubsection [header & body]
  (partial-section 3 header body))


(defn ^:private page-header []
  [:head
   [:link {:rel "stylesheet" :href "tufte-css/tufte.css"}]
   [:script {:type "text/javascript"
             :src "http://cdn.mathjax.org/mathjax/latest/MathJax.js?config=TeX-AMS-MML_HTMLorMML"}]])


(defn ^:private year []
  (+ 1900 (.getYear (java.util.Date.))))


(defn page-footer []
  [:div
   [:p
    [:a {:href "about.html"} "about"] "|"
    [:a {:href "content.html"} "all posts"]]
   [:p "© " (year) " " [:a {:href "about.html"} "John Jacobsen"]
    ". Created with "
    [:a {:href "https://github.com/eigenhombre/unmark"} "unmark"]
    ".  CSS by "
    [:a {:href "https://edwardtufte.github.io/tufte-css/"} "Tufte-CSS"] "."]])


(defn replace-symbols [txt]
  (if-not (string? txt)
    txt
    (-> txt
        (clojure.string/replace #"©" "&copy;")
        ;; FIXME: accent grave:
        (clojure.string/replace #"à" "a")
        (clojure.string/replace #"(?:^|(?<=(?:\s+|\()))\"", "&ldquo;")
        (clojure.string/replace #"\"(?:$|(?=(?:\s+|\)|\;)))", "&rdquo;")
        (clojure.string/replace #"’" "&rsquo;")
        (clojure.string/replace #"(?<=[a-zA-Z])'(?=[a-zA-Z])"
                                "&rsquo;")
        (clojure.string/replace #"”" "&rdquo;")
        (clojure.string/replace #"–" "&mdash;")
        (clojure.string/replace #"-{2}" "&mdash;")
        (clojure.string/replace #"“" "&ldquo;")
        (clojure.string/replace #"…" "&hellip;"))))


(defn walk-replace [x]
  (clojure.walk/postwalk replace-symbols x))


(defn content [& body]
  (html
   (page-header)
   (vec (list* :body (walk-replace body)))
   (walk-replace (page-footer))))


(defn sidenote [& txt]
  (let [cls (str (gensym))]
    `[:span
      [:label {:for ~cls :class "margin-toggle sidenote-number"}]
      [:input {:type "checkbox" :id ~cls :class "margin-toggle"}]
      [:span {:class "sidenote"} ~@txt]]))


(defn epigraph [txt credit]
  [:div {:class "epigraph"}
   [:blockquote txt [:footer credit]]])


(defn code [txt]
  [:pre [:code txt]])


(defn postbody [& forms]
  `[:div ~@forms])


(defonce posts (atom {}))


(defmacro defpost
  ([title slug body]
   `(defpost ~title ~slug {} ~body))
  ([title slug meta & body]
   `(swap! posts assoc ~slug ~(merge meta
                                     {:title title
                                      :slug slug
                                      :body (apply postbody body)}))))



(defn img
  ([nom caption]
   (let [fname-png (format "img/%s.png" nom)
         fname-jpg (format "img/%s.jpg" nom)
         fname (if (.exists (clojure.java.io/file fname-png))
                 fname-png
                 fname-jpg)]
     (assert (.exists (clojure.java.io/file fname)))
     `[:figure [:img [:a {:href ~fname} [:img {:src ~fname}]]]
       ~(when caption [:figcaption caption])]))
  ([nom]
   (img nom nil)))


(defn toc []
  (section "Contents"
    [:ul
     (for [[date slug title]
           (->> @posts
                (remove (comp :draft second))
                (remove (comp nil? :created second))
                (sort-by (comp :created second))
                reverse
                (map (comp (juxt :created :slug :title) second)))]
       [:li date " " [:a {:href (str slug ".html")} title]])]))


(defn render [target-dir filename body]
  (.mkdir (clojure.java.io/file target-dir))
  (spit (str target-dir "/" filename) (content body)))


(defn copy-dir! [target-dir local-dir]
  (copy-dir local-dir (str target-dir "/" local-dir)))


(defn generate-blog! [target-dir]
  (copy-dir! target-dir "img")
  (copy-dir! target-dir "tufte-css")
  (render target-dir "content.html" (toc))
  (doseq [[slug {:keys [title body]}] @posts]
    (render target-dir (str slug ".html") body))
  (render target-dir
          "index.html"
          (->> @posts
               (remove (comp :draft second))
               (sort-by (comp :created second))
               last
               second
               :body)))
