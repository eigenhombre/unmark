(ns unmark.impl
  (:require [hiccup.core :refer [html]]
            [unmark.posts :refer :all]))


(defn section [header & body]
  `([:h1 ~header]
    ~@(->> body
           (remove map?)
           (map (partial vector :p)))))


(defn subsection [header & body]
  `([:h2 ~header]
    ~@(map (partial vector :p) (remove map? body))))


(defn subsubsection [header & body]
  `([:h3 ~header]
    ~@(map (partial vector :p) (remove map? body))))


(defn ^:private page-header []
  [:head [:link {:rel "stylesheet",
                 :href "tufte-css/tufte.css"}]])


(defn ^:private year []
  (+ 1900 (.getYear (java.util.Date.))))


(defn replace-symbols [txt]
  (if-not (string? txt)
    txt
    (-> txt
        (clojure.string/replace #"©" "&copy;")
        (clojure.string/replace #"’" "&rsquo;")
        (clojure.string/replace #"”" "&rdquo;")
        (clojure.string/replace #"–" "&mdash;")
        (clojure.string/replace #"“" "&ldquo;")
        (clojure.string/replace #"…" "&hellip;"))))


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


(defn content [& body]
  (replace-symbols
   (html
    (page-header)
    (vec (list* :body body))
    (page-footer))))


(defn sidenote [txt]
  (let [cls (str (gensym))]
    [:span
     [:label {:for cls :class "margin-toggle sidenote-number"}]
     [:input {:type "checkbox" :id cls :class "margin-toggle"}]
     [:span {:class "sidenote"} txt]]))


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
