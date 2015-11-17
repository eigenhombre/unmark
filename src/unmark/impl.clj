(ns unmark.impl
  (:require [hiccup.core :refer [html]]
            [unmark.posts :refer :all]))


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
  [:head [:link {:rel "stylesheet" :href "tufte-css/tufte.css"}]])


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
        (clojure.string/replace #"(?:^|(?<=(?:\s+|\()))\"", "&ldquo;")
        (clojure.string/replace #"\"(?:$|(?=(?:\s+|\)|\;)))", "&rdquo;")
        (clojure.string/replace #"’" "&rsquo;")
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
  ([title slug meta body]
   `(swap! posts assoc ~slug ~(merge meta
                                     {:title title
                                      :slug slug
                                      :body body}))))



(defn img [nom]
  (let [fname (format "img/%s.jpg" nom)]
    (assert (.exists (clojure.java.io/file fname)))
    [:a {:href fname} [:img {:src fname}]]))


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


(defn render [filename body]
  (spit filename (content body)))


(defn generate-blog! []
  (render "content.html" (toc))
  (doseq [[slug {:keys [title body]}] @posts]
    (render (str slug ".html") body))
  (render "index.html"
          (->> @posts
               (remove (comp :draft second))
               (sort-by (comp :created second))
               last
               second
               :body)))
