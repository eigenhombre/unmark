(ns unmark.impl
  (:require [hiccup.core :refer [html]]
            [clojure.java.io :as io]
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


(defn convert-body [body]
  (->> body
       (remove map?)
       (map spanify-vector-line)
       (map (partial vector :p))))


(defn partial-section [level header body]
  `([~(keyword (str "h" level)) ~header]
    ~@(convert-body body)))


(defn section [header & body]
  (partial-section 1 header body))


(defn subsection [header & body]
  (partial-section 2 header body))


(defn subsubsection [header & body]
  (partial-section 3 header body))


(defn ^:private page-header [title]
  [:head
   [:title title]
   [:link {:rel "stylesheet" :href "tufte-css/tufte.css"}]
   [:script {:type "text/javascript"
             :src (str "http://cdn.mathjax.org/mathjax/"
                       "latest/MathJax.js?config=TeX-AMS-MML_HTMLorMML")}]])


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


(defn content [title & body]
  (html
   (page-header title)
   (vec (list* :body
               [:h1 title]
               [:p {:class "subtitle"} "John Jacobsen"]
               (walk-replace body)))
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


(defn slugify [s]
  (-> s
      clojure.string/lower-case
      (clojure.string/replace #"\s+" "-")))


(defmacro defpost [title meta & body]
  (let [slug (slugify title)]
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


(defn render [target-dir filename title body]
  (.mkdir (clojure.java.io/file target-dir))
  (spit (str target-dir "/" filename) (content title body)))


(defn copy-dir! [target-dir local-dir]
  (copy-dir local-dir (str target-dir "/" local-dir)))


(defn generate-blog! [target-dir]
  (copy-dir! target-dir "img")
  (copy-dir! target-dir "tufte-css")
  (render target-dir "content.html" "Table of Contents" (toc))
  (doseq [[slug {:keys [title body]}] @posts]
    (render target-dir (str slug ".html") title body))
  (let [filename (->> @posts
                      (remove (comp :draft second))
                      (sort-by (comp :created second))
                      last
                      second
                      :slug)]
    (io/copy (io/file (str target-dir "/" filename ".html"))
             (io/file (str target-dir "/index.html")))))
