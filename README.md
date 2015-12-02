
![image](img/unmark-sm.png)

# What

Simple blogging using Clojure and Hiccup.  Basically you just write
Clojure expressions instead of Markdown, HTML, Textile, Org Mode, or
somebody's JavaScript Web editor.

# Why

Since 1994, I've tried many approaches for creating personal Web sites
and blogs:

- Hand-written HTML
- Hand-rolled Perl scripts
- Hand-rolled Python scripts
- Hand-rolled Django app
- Jekyll
- tumblr
- Emacs-powered site generator
- Hand-rolled, Clojure- and Org-Mode-based app

Each approach has had its benefits and drawbacks, but none of them
have quite hit the sweet spot in terms of power vs. simplicity.

Lately I've enjoyed working primarily with S-expressions (blame
[Paredit](http://www.emacswiki.org/emacs/ParEdit)).  My experience
with extremely lightweight libraries such as
[HoneySQL](https://github.com/jkk/honeysql) and
[Hiccup](https://github.com/weavejester/hiccup) that wrap clunky
languages with elegant S-expression-based DSLs have been extremely
positive.

To this end, `unmark` eschews markup when possible, to leverage the
full power and interactivity of Clojure.  Blog posts are just Clojure
expressions, leveraging functions for common idioms (e.g. `code`,
`section`, `subsection`, `epigraph`, `blockquote`, `img`, etc.):

    (defpost "Introducing Unmark"
      (section "Introduction"
        "Unmark is a new blogging framework."
        ["Hiccup can be used " [:em "just about"] "everywhere."]
        "But undecorated paragraphs
         can just be strings..."
        ["(or vectors if you have "
          [:a {:href "https://en.wikipedia.org/wiki/Markup_language"}
	      "markup"] " in the paragraph)."]
	(img "sample-img")  ;; JPG or PNG in img/ directory
	(code "(quote This is some sample code)"))
        ;; ...
        )

Right now this is optimized for my own blog.  If it seems successful
over time, I'll decouple the content generator from the content so
that `unmark` can be useful for others.

# How

The code is moving very fast; for now, have a look at `posts.clj`,
`impl.clj` and `core.clj`.
