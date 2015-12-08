(ns unmark.posts
  (:require [unmark.impl :refer :all]))


(defpost "About Me"
  "I'm a software developer living (mostly) in Chicago, Illinois, USA.
   I currently work at OpinionLab, where I am a senior dev on their
   backend Clojure team."

  ["I care about making outstanding software
   and outstanding teams based on respect, communication, focus, care,
   and craft.  I'm also passionate about "
   [:a {:href "http://johnj.com"} "art"]
   " and science (I went to graduate school in art and physics and
   have a doctorate in high energy particle astrophysics)."]
  [[:a {:href "mailto:eigenhombre@gmail.com"} "e-mail"]
   " / "
   [:a {:href "https://github.com/eigenhombre"} "GitHub"]
   " / "
   [:a {:href "http://stackoverflow.com/users/611752/johnj"} "StackOverflow"]
   " / "
   [:a {:href "https://twitter.com/eigenhombre"} "Twitter"]
   " / "
   [:a {:href "https://www.linkedin.com/in/eigenhombre"} "LinkedIn"]
   " / "
   [:a {:href "static/jacobsen-2015.pdf"} "resumé"]
   " / "
   [:a {:href "https://github.com/eigenhombre/cv"} "Academic CV"]])


(defpost "Working With Me"
  {:created "2015-11-14", :draft true}
  (epigraph "Great things are done by a series of small things brought
       together." "Vincent Van Gogh")
  (section "Some things about me" "In no particular order: I tend to
    acquire a reputation as the \"clean code guy.\" I prefer to retire
    technical debt sooner rather than later. I like to strike the
    right balance between thinking things through in advance, and
    coding experimentally, discovering the truths of the problem at
    hand as I work. I enjoy tackling easy problems, because it's
    satisfying to get things done quickly, and I like hard problems
    that make me grow, think, and do things I never imagined
    possible. I like a mix of coding solo and working closely with
    others. I like working in beautiful environments that stoke
    creativity and passion. I am opinionated, especially with regards
    to the tools and approaches I've arrived at through many years of
    growth, but I love learning new things from thoughtful people who
    are good at their craft.")
  (section "Practices I Like"
    (subsection "\"Test First\""
      ["I am not a TDD zealot, but I've had my tuchas saved many times
          by automated tests, and been burned more than a few by not
          having them. True TDD"
       (sidenote "Write the right minimum failing test first; write
          the minimum production code needed to make all tests pass;
          refactor to get to clean, DRY code; repeat.")  " is not
          without its costs, but it is one of my favorite strategies
          for ensuring good test coverage and, often, better design. I
          frequently combine REPL development and higher-level,
          end-to-end tests rather than testing slavishly at multiple
          levels of abstraction. I keep in mind the cost of tests in
          terms of code \"stiffness\" and try to write the "
          [:emO "right"] " tests."])
    (subsection "Continuous Testing"
      "Most or all tests get run every time you save the file (at
         least), as often as once per minute or so. If your tests take
         too long for this to work, there's probably an architectural
         or performance problem that needs to be looked at.")
    (subsection "Promiscuous Pairing"
      ["The team I currently work on has adopted “promiscuous pairing”"
       (sidenote "See A. Belshee, " [:em "Promiscuous
          Pairing and Beginner's Mind"] " (Google for PDF)")
       " as a regular practice. We pair-program for 90 minute
          blocks, switching pairs twice a day (and solo programming or
          going to meetings the rest of the time)."]
      "This practice, while often tiring, is a great teaching
         technique.  At any given time, there is little tactical
         knowledge that I have that I don't trust my teammates to
         have, and vice-versa. People who practice promiscuous pairing
         (including us) relate the pleasures of teaching a technique
         to someone and then seeing another teammate using the same
         technique a short time later.")
    (subsection "Solo Programming")
    (subsection "Retrospectives")
    (subsection "Kanban over Scrum")
    (subsection "Clean code"
      "Whenever I touch a piece of code, I try to leave it in better shape
         than I found it in.  I use style guides and build style checking
         tools into automated builds, because I've found that better and more
         readable code results.")
    (subsection "Continuous Integration"))
  (section "Default Tools"
    "I use the following tools on a daily or near-daily basis."
    (subsection "Clojure")
    (subsection "Emacs"
      ["Configuration "
       [:a {:href "http://github.com/eigenhombre/emacs-config"} "here"]
       "."])
    (subsection "Docker"))
  (section "Working with Teams"
    "...")
  (section "Technical Strengths"
    (subsection "Troubleshooting and debugging")))


(defpost "Lazy Physics"
  {:created "2015-02-12"}
  [:em "... in which we explore lazy sequences and common
     functional idioms in Clojure via the example of looking for
     (nearly-)coincident clusters of times in a series."]
  "A fundamental technical problem in experimental particle physics
     is how to distinguish the signatures of particles from
     instrumental noise."
  (img "birds-on-wires")
  ["Imagine a tree full of hundreds of sparrows, each nesting
     on a branch, each chirping away occasionally. Suddenly, for a
     brief moment, they all start chirping vigorously (maybe a hawk
     flew past). A clustering of chirps in time is the signal that "
   [:em "something has happened!"] " The analogous situation occurs
     in instruments consisting of many similar detector elements, each
     generating some amount of random noise that, on its own, is
     indistinguishable from any evidence left by particles, but which,
     taken together, signals that, again, " [:em "something has
     happened"] "--a muon, an electron, a neutrino has left a sudden
     spume of electronic evidence in your instrument, waiting to be
     read out and distinguished from the endless noise."]
  ["This process of separating the noise from the signal is
     known in physics as " [:em "triggering"] " and is typically done
     through some combination of spatial or time clustering; in many
     cases, time is the simplest to handle and the first \"line of
     defense\" against being overrun by too much data. (It is often
     impractical to consume all the data generated by all the elements
     --data reduction is the name of the game at most stages of these
     experiments.)"]
  ["This data is typically generated
     continously " [:em "ad infinitum"] ", and must therefore be processed
     differently than, say, a single file on disk. Such infinite
     sequences of data are an excellent fit for the functional pattern
     known as " [:em "laziness"] ", in which, rather than chewing up all
     your RAM and/or hard disk space, data is consumed and transformed
     only as needed / as available. This kind of processing is baked
     into Clojure at many levels and throughout its library of core
     functions, dozens of which can be combined (\"composed\") to
     serve an endless variety of data transformations. (This style of
     data wrangling is also available in Python via generators and
     functional libraries such as "
     [:a {:href "http://toolz.readthedocs.org/"} "Toolz"] ".)"]
  "Prompted by a recent question on the topic from a physicist and
     former colleague, I got to thinking about the classic problem of
     triggering, and realized that the time series trigger provides a
     nice showcase for Clojure’s core library and for processing lazy
     sequences. The rest of this post will describe a simple trigger,
     essentially what particle astrophysicists I know call a \"simple
     majority trigger\"; or a \"simple multiplicity trigger\" (depending
     on whom you talk to)."
  ["Now for some Clojure code. (A small amount of familiarity with
     Clojure’s simple syntax is recommended for maximum understanding
     of what follows.) We will build up our understanding through a
     series of successively more complex code snippets. The exposition
     follows closely what one might do in the Clojure REPL, building
     up successively more complete examples. In each case, we use "
   [:code "take"] " to limit what would otherwise be infinite
     sequences of data (so that our examples can terminate without
     keeping us waiting
     forever...)."]
  "First we create a sorted, infinite series of ever-increasing
     times (in, say, nsec):"
  (code "(def times (iterate #(+ % (rand-int 1000)) 0))
;; Caution: infinite sequence...

(take 30 times)

;;=>
(0 955 1559 2063 2735 2858 3542 4067 4366 5246 5430 6168 7127 7932
 8268 8929 9426 9918 10436 10850 11680 12367 12569 13343 14155 14420
 15062 15171 15663 16355)
")
  [[:code "times"]
   " is an infinite (but “unrealized”) series, constructed by
     iterating the anonymous function " [:code "#(+ % (rand-int 1000))"] " which
     adds a random integer from 0 to 999 to its argument (starting
     with zero). The fact that it is infinite does not prevent us from
     defining it or (gingerly) interrogating it via " [:code "take"] "."
     (sidenote "To model a "
               [:a {:href "http://en.wikipedia.org/wiki/Poisson_process"}
                "Poisson"]
               " process – one in which any given event time is independent of
      the future or past times – one would normally choose an
      exponential rather than a uniformly flat distribution of time
      differences, but this is not important for our discussion, so, in
      the interest of simplicity, we’ll go with what we have
      here.")]
  ["Now, the way we’ll look for excesses is to look for
     groupings of hits (say, eight of them) whose first and last hit
     times are within 1 microsecond (1000 nsec) of each other. To start,
     there is a handy function called " [:code "partition"] " which
     groups a series in blocks of fixed length:"]
  (code "(take 10 (partition 8 times))

;;=>
((0 955 1559 2063 2735 2858 3542 4067)
 (4366 5246 5430 6168 7127 7932 8268 8929)
 (9426 9918 10436 10850 11680 12367 12569 13343)
 (14155 14420 15062 15171 15663 16355 16700 16947)
 (17919 17949 18575 18607 18849 19597 20410 20680)
 (20737 21289 21315 21323 21426 21637 22422 23000)
 (23477 24351 24426 25106 25861 26568 27511 28332)
 (29071 29831 29957 30761 31073 31914 32591 33187)
 (33878 34739 34842 35674 36444 36960 36983 37400)
 (37587 38012 38969 39131 39317 40135 40587 40759))")
  "We’ll rewrite this using Clojure’s thread-last macro, which is a
     very helpful tool for rewriting nested expressions as a more
     readable pipeline of successive function applications:"
  (code "(->> times
     (partition 8)
     (take 10))

;;=>
((0 955 1559 2063 2735 2858 3542 4067)
 (4366 5246 5430 6168 7127 7932 8268 8929)
 ...same as above...)")
  ["However, this isn’t quite what we want, because it won’t
     find clusters of times close together who don’t happen to begin
     on our " [:code "partition"] " boundaries. To fix this, we use
     the optional " [:code "step"] " argument to " [:code "partition"] ":"]
  (code "(->> times
     (partition 8 1)
     (take 10))

;;=>
((0 955 1559 2063 2735 2858 3542 4067)
 (955 1559 2063 2735 2858 3542 4067 4366)
 (1559 2063 2735 2858 3542 4067 4366 5246)
 (2063 2735 2858 3542 4067 4366 5246 5430)
 (2735 2858 3542 4067 4366 5246 5430 6168)
 (2858 3542 4067 4366 5246 5430 6168 7127)
 (3542 4067 4366 5246 5430 6168 7127 7932)
 (4067 4366 5246 5430 6168 7127 7932 8268)
 (4366 5246 5430 6168 7127 7932 8268 8929)
 (5246 5430 6168 7127 7932 8268 8929 9426))")
  ["This is getting closer to what we want–if you look
     carefully, you’ll see that each row consists of the previous one
     shifted by one element. The next step is to grab (via " [:code "map"] ")
     the first and last times of each group, using " [:code "juxt"] " to apply
     both " [:code "first"] " and " [:code "last"] " to each subsequence…"]
  (code "(->> times
     (partition 8 1)
     (map (juxt last first))
     (take 10))

;;=>
([4067 0]
 [4366 955]
 [5246 1559]
 [5430 2063]
 [6168 2735]
 [7127 2858]
 [7932 3542]
 [8268 4067]
 [8929 4366]
 [9426 5246])")
  "… and turn these into time differences:"
  (code "(->> times
     (partition 8 1)
     (map (comp (partial apply -) (juxt last first)))
     (take 10))

;;=>
(4067 3411 3687 3367 3433 4269 4390 4201 4563 4180)
")
  ["Note that so far these time differences are all > 1000. "
   [:code "comp"] ", above, turns a collection of multiple
      functions into a new function which is the composition of these
      functions, applied successively one after the other
      (right-to-left). " [:code "partial"] " turns a function of
      multiple arguments into a function of fewer arguments, by
      binding one or more of the arguments in a new function. For
      example,"]
  (code "((partial + 2) 3)

;;=>
5

((comp (partial apply -) (juxt last first)) [3 10])

;;=>
7")
  ["Recall that we only want events whose times are close to
     each other; say, whose duration is under a maximum limit of 1000
     nsec. In general, to select only the elements of a sequence which
     satisfy a filter function, we use " [:code "filter"] ":"]
  (code "(->> times
     (partition 8 1)
     (map (comp (partial apply -) (juxt last first)))
     (filter (partial > 1000))
     (take 10))

;;=>
(960 942 827 763 597 682 997 836 986 966)")
  ["(" [:code "(partial > 1000)"] " is a function of one
     argument which returns true if that argument is strictly less
     than 1000.)"]
  "Great! We now have total “durations”; for subsequences of 8
     times, where the total durations are less than 1000 nsec."
  "But this is not actually that helpful. It would be better if we
     could get both the total durations and the actual subsequences
     satisfying the requirement (the analog of this in a real physics
     experiment would be returning the actual hit data falling inside
     the trigger window)."
  ["To do this, " [:code "juxt"] " once again comes to the
     rescue, by allowing us to " [:code "juxt"] "-apose the original
     data alongside the total duration to show both together…"]
  (code "(->> times
     (partition 8 1)
     (map (juxt identity (comp (partial apply -) (juxt last first))))
     (take 10))

;;=>
([(0 309 410 562 979 1423 2180 3159) 3159]
 [(309 410 562 979 1423 2180 3159 3585) 3276]
 [(410 562 979 1423 2180 3159 3585 4325) 3915]
 [(562 979 1423 2180 3159 3585 4325 4573) 4011]
 [(979 1423 2180 3159 3585 4325 4573 5074) 4095]
 [(1423 2180 3159 3585 4325 4573 5074 5942) 4519]
 [(2180 3159 3585 4325 4573 5074 5942 6599) 4419]
 [(3159 3585 4325 4573 5074 5942 6599 7458) 4299]
 [(3585 4325 4573 5074 5942 6599 7458 8128) 4543]
 [(4325 4573 5074 5942 6599 7458 8128 8439) 4114])")
  "... and adapt our filter slightly to apply our filter only to the
time rather than the original data:"
  (code "(->> times
     (partition 8 1)
     (map (juxt identity (comp (partial apply -) (juxt last first))))
     (filter (comp (partial > 1000) second))
     (take 3))

;;=>
([(1577315 1577322 1577514 1577570 1577793 1577817 1577870 1578151)
  836]
 [(3119967 3120203 3120416 3120469 3120471 3120620 3120715 3120937)
  970]
 [(6752453 6752483 6752522 6752918 6752966 6753008 6753026 6753262)
  809])")
  ["Finally, to turn this into a function for later use, use"
   [:code "defn"] " and remove " [:code "take"] ":"]
  (code "(defn smt-8 [times]
  (->> times
       (partition 8 1)
       (map (juxt identity (comp (partial apply -) (juxt last first))))
       (filter (comp (partial > 1000) second))))")
  [[:code "smt-8"] " consumes one, potentially infinite
sequence and outputs another, “smaller” (but also potentially
infinite) lazy sequence of time-clusters-plus-durations, in the form
shown above."]
  "Some contemplation will suggest many variants; for example, one
in which some number of hits outside the trigger \"window\" are also
included in the output.  This is left as an exercise for the advanced
reader."
  ["A “real” physics trigger would have to deal with many other
details: each hit, in addition to its time, would likely have an
amplitude, a sensor ID, and other data associated with it.  Also, the
data may not be perfectly sorted, some sensors may drop out of the
data stream, etc.  But in some sense this prototypical time clustering
algorithm is one of the fundamental building blocks of experimental
high energy physics and astrophysics and was used (in some variant) in
every experiment I worked on over a 25+ year period.  The
representation above is certainly one of the most succinct, and shows
off the power and elegance of the language, its core library, and lazy
sequences.  (It is also reasonably fast for such a simple algorithm; "
   [:code "smt-8"] " consumes input times at a rate of about 250 kHz.
This is not, however, fast enough for an instrument like IceCube,
whose 5160 sensors each count at a rate of roughly 300 Hz, for a total
rate of 1.5 MHz. A future post may look at ways to get better
performance.)"])


(defpost "Fun with Instaparse"
  {:created "2013-11-12"}
  ["One of my favorite talks from this month's excellent "
   [:a {:href "http://clojure-conj.org/"} "Clojure/conj"] " was "
   [:a {:href "http://gigasquid.github.io/"} "Carin Meier"]
   "'s presentation, which combined storytelling, live coding, philosophy,
     the history of computing, and flying robotic drones. She used the
     relatively new "
   [:a {:href "https://github.com/Engelberg/instaparse"}
    "Instaparse library"] " to "
    [:a {:href "http://gigasquidsoftware.com/wordpress/?p=689"} "create her
     own language"] " to explore something called “Speech Acts” (which I won't
     go into here, but do catch the video of her talk when it goes up)."]
  ["My university work was in physics (and art) rather than CS, but I
     have long been interested in the implementation of programming languages,
     even going so far as to write a simple parser for Lisp-style math
     expressions in Pascal many years ago. Last year I had the opportunity to
     take the first "
   [:a {:href
        "http://dabeaz.blogspot.com/2012/01/compiler-experiment-begins.html"}
    "“write a compiler in Python” class"] " offered by "
    [:a {:href "http://dabeaz.com"} "David Beazley"]
    " here in Chicago, in which we implemented a subset of the Go
      language. His "
    [:a {:href "http://www.dabeaz.com/ply/index.html"} "PLY"]
    " library is a great way
     to get started with implementing language parsers in Python, and the
     relative ease of doing so, compared with classic C implementations
     described in the infamous "
    [:a {:href
         (str "http://www.amazon.com"
              "Compilers-Principles-Techniques-Alfred-Aho/dp/0201100886")}
     "Dragon Book"]
    ", inspired me to do some further "
    [:a {:href "https://github.com/eigenhombre/PyClojure"}
     "experimentation of my own"]
    "."]
  "With this background, and inspired by Carin's talk, I have been waiting
     for an opportunity to try out Instaparse, which is getting great press in
     the Clojure world. Instaparse takes a grammar as input (in the form of a
     string), and gives you a parser in the language specified by that
     grammar. It will also let you specify rules for transforming the
     resulting tree into something your Clojure program can use more directly
     (for example, by converting data types or removing unneeded elements from
     the parse tree)."
  ["When the need arose this weekend to read in Python configuration
     files into a Clojure program, I decided the time was ripe. I also wanted
     to document the journey using some form of "
   [:a {:href
        "http://en.wikipedia.org/wiki/Literate_programming"}
    "literate programming"] ". A library called "
    [:a {:href
         "https://github.com/gdeer81/marginalia"} "Marginalia"]
    " (Michael Fogus " [:em "et. al."] ") made this pretty easy."]
  ["The results are "
   [:a {:href
        "http://eigenhombre.com/semi-literate-programming/parsepy.html"}
    "here"] ", as well as on "
    [:a {:href "https://github.com/eigenhombre/parsepy"} "GitHub"] "."]
  "My impressions, after doing this project in just a few hours, are that
     (1) literate programming is great fun; and (2) Instaparse sets a new
     standard for power and expressiveness when converting structured text
     into abstract syntax trees. If you have a DSL or some other text-based,
     formal language you want to parse, and you are either literate in Clojure
     or interested in becoming so, Instaparse would be a great tool to check
     out.")


(defpost "Rosalind Problems in Clojure"
  {:created "2013-06-09"}
  ["This weekend I've been having a lot of fun working the Bioinformatics
problems from "
   [:a {:href "http://rosalind.info/"} "Rosalind"] ". Most people work them
in Python, but so far they have been very amenable to Clojure except
where BioPython libraries are used for access to online databases. The
problems have been straightforward so far but I have enjoyed the
elegance and brevity that Clojure lends the solutions."]
  ["In particular, I like this short translator "
   [:a {:href "http://rosalind.info/problems/prot/"}
    " from RNA sequences to amino acids"] ":"]
  (code ";;; Translating RNA into Amino Acids

(defmacro deftable [tname & rest]
  `(def ~tname (apply hash-map '(~@rest))))

(deftable proteins
  UUU F      CUU L      AUU I      GUU V
  UUC F      CUC L      AUC I      GUC V
  UUA L      CUA L      AUA I      GUA V
  UUG L      CUG L      AUG M      GUG V
  UCU S      CCU P      ACU T      GCU A
  UCC S      CCC P      ACC T      GCC A
  UCA S      CCA P      ACA T      GCA A
  UCG S      CCG P      ACG T      GCG A
  UAU Y      CAU H      AAU N      GAU D
  UAC Y      CAC H      AAC N      GAC D
  UAA Stop   CAA Q      AAA K      GAA E
  UAG Stop   CAG Q      AAG K      GAG E
  UGU C      CGU R      AGU S      GGU G
  UGC C      CGC R      AGC S      GGC G
  UGA Stop   CGA R      AGA R      GGA G
  UGG W      CGG R      AGG R      GGG G)

(defn to-protein [s]
  (->> s
       (partition 3)
       (map (partial apply str))
       (map symbol)
       (map proteins)
       (take-while #(not= % 'Stop))
       (apply str)))")
  ["The body of the " [:code "proteins"]
   " table is literally cut-and-pasted from "
   [:a {:href "http://rosalind.info/problems/prot/"} "the
problem page"]
   " (click on \"RNA codon table\"). I think it's a good example of
using macros to provide a little bit of syntactic sugar to make the
code just a little more readable and elegant, or to encode domain facts
as directly as possible."]
  ["This and my other solutions so far are "
   [:a {:href "https://github.com/eigenhombre/rosalind"} "up on GitHub"]
   "."])


(defpost "Programming Languages"
  {:created "2011-12-22"}
  (img "koans" "Working on Clojure Koans while riding on a C-17,
     bound for Antarctica")
  "Today I am inspired to ponder many languages at once and review
     which ones I use regularly, which ones I’m curious about, which
     ones I avoid, and what I’d like to use, if it were to exist."
  (subsection "Programming Languages I Use Regularly"
    (subsubsection "Python"
      "By far the language I use most for work. I like it for its
         clean philosophy, its expressiveness, its \"batteries
         included\" extensive set of libraries, and, first and
         foremost, for its readability.")
    (subsubsection "C"
      ["Of all the languages I use regularly, C is the one I
         learned first. I maintain a large Linux kernel device driver
         I wrote for the "
       [:a {:href "http://icecube.wisc.edu"}
        "IceCube project"]
       " as well as an embedded system written for 5000+ sensors
         designed for the same."]
      "C now feels like assembly language to me but I still
         appreciate its power and elegance.")
    (subsubsection "Clojure"
      ["I have dabbled in Lisp since the 1980s but not seriously
         until recently. Somewhat seduced by Paul Graham’s "
       [:a {:href "http://www.paulgraham.com/avg.html"} "essays on Lisp"]
       " and encouraged by a bit of a Lisp renaissance, I have
         started reading up on Clojure and working through problems on "
       [:a {:href "http://4clojure.com"} "4clojure.com"]
       ". While not without its warts, I like many things
         about Clojure, including the Lisp \"code-as-data\" philosophy,
         availability of macros (something I wish Python had) and its
         interoperability with Java classes. While I doubt I’ll be
         able to use this in my paying work any time soon, I have
         started playing with Clojure for personal projects."
       "Having to deal with significantly concurrent systems in my
         work, I am intrigued by functional programming, as opposed to
         the usual object-oriented approaches where state is king and
         where tangled hierarchies of relatively meaningless
         relationships can obscure intent"
       (sidenote
        "See S. Yegge, "
        [:a {:href (str "http://steve-yegge.blogspot.com/"
                        "2006/03/execution-in-kingdom-of-nouns.html")}
         "The Kingdom of the Nouns"] ".")
       ". Clojure takes an interesting approach, with its
         emphasis on immutability, software-transactional memory and
         other concurrency primitives."])
    (subsubsection "Bash / Unix Tools"
      "It amuses me slightly to include bash here, but combining
         simple iteration with conditional statements and adding basic
         Unix concepts and tools such as pipelines, grep, awk, sed,
         wc, etc. is surprisingly powerful. Every small Bash trick or
         new tiny-Unix-tool I learn seems to eliminate the need for
         some number of actual programs, at least for quick-and-dirty
         work. The results tend to be obscure and hard to parse; if I
         can do something in a single line of bash, I will; otherwise
         I’ll resort to Python for most things.")
    (subsubsection "Javascript / Coffeescript"
      ["Not my favorite language by any stretch, but you can’t
         avoid it if you’re working in the browser (I don’t consider
         closed-source Flash an option). The language has a lot of
         warts, but some "
       [:a {:href (str "http://www.amazon.com/JavaScript-Good-Parts"
                       "-Douglas-Crockford/dp/0596517742")}
        "good parts"] " too. I can feel the Lisp bones
         deep underneath the surface of the language when I dive into
         JavaScript. CoffeeScript is sweet because it’s so much more
         readable and offers protection from common JavaScript
         gotchas, but has "
        [:a {:href
             "http://lucumr.pocoo.org/2011/12/22/implicit-scoping-in-coffeescript/"}
         "some flaws of its own"] "."]))
  (subsection "Programming Languages I Have Used in the Past but
                  Tend to Avoid"
    (subsubsection "Perl"
      "I fell in love with the power of Perl (“the duct-tape of the
         Internet”) back in the 1990s, but now dislike its strange,
         ad-hoc syntax and the relative inscrutability when compared
         to Python.")
    (subsubsection "Java"
      ["I haven’t done a ton of Java development, but have done
         enough to be irritated by certain things about it: its
         extremely verbose syntax, strict typing, distance from the
         actual hardware, and lack of (at least until now) anonymous
         functions (“lambda”). Also the JVM startup time is
         irritating, a problem Clojure inherits from Java (though
         " [:a {:href "https://github.com/ninjudd/drip"} "there are
          workarounds"] ")."]
      "Java has become so ubiquitous, however, that it’s hard to
          avoid, and it does have a certain self-consistent
          habitability to it. I think current JVM languages such as
          Clojure and Scala will only strengthen the role of Java and
          the JVM in modern computing, unless Oracle massively screws
          things up.")
    (subsubsection "C++"
      "Another language I’ve played with a bit. A language that
         splits the difference between C and Java (I realize C++ came
         before Java); I would prefer to write in a “real” higher
         level language and glue C in where needed.")
    (subsubsection "FORTRAN"
      "I’m sorry to say that, coming from physics, I’ve written
         more FORTRAN code than I care to admit. I find it
         interesting, however, that while Lisp and FORTRAN are almost
         the same age, Lisp still holds interest where FORTRAN does
         not (except to pure number-crunchers, due to ancient and
         venerable numeric libraries)."))
  (subsection "Languages I’m Curious About But Haven’t Had Time to
                  Look At Much"
    "Exposure to purely functional programming and lazy evaluation
        in Clojure made me curious about Haskell."
    "I am curious about Erlang, which is supposed to have excellent
        concurrency features."
    "I saw some talks about Go at
        OSCON. Go looks like it has some really nice features compared
        to C (compilation speed, concurrency support, and improved
        readability), but it may be a bit low-level for my interests."
    "I have only tinkered with Objective-C, but that is the
        language of choice for serious Mac OS X or iOS
        development. Its syntax looks pretty odd, but perhaps that’s a
        small price to pay for running on all that pretty hardware.  "
    "Purely logical languages such as Prolog (equivalents of
        which can be easily implemented in Lisp) are of interest for
        their ability to process large amount of semantically-related
        content. I’m curious about expert systems, ontologies, the
        Semantic Web, and many other related areas of AI
        research.")
  (subsection "The Language I Wish Existed"
    " The perfect language would:"
    [:ol
     [:li "Be very readable, like Python (whitespace or other
        visual cues probably playing a significant role)"]
     [:li "Support full Lisp-like macros (“homoiconicity”)"]
     [:li "Have very broad library support (Python, Java, …)"]
     [:li "Have built-in features in support of test-driven
        development (Python’s doctests and Clojure’s "
      [:code ":test"] " metadata
        seem like just the beginning of what might be possible)"]
     [:li "Handle concurrency very well (Clojure, Erlang, … but not Python)"]
     [:li "Run in the browser, or be implemented efficiently on top
        of JavaScript"]
     [:li "Allow you to get very close to the machine if necessary,
        or at least the bytecodes of the virtual machine or
        interpreter (Python, C, C++, …)"]]
    "Points 1-3 are the most important to me. Resolving the tension
       between points 1 and 2 is of particular interest."
    ["I doubt such a language will come along any time soon. But I’m
       taking "
     [:a {:href
          "http://www.dabeaz.com/chicago/compiler.html"} "a class
       next month"] " which, who knows? … might help someday."]))


(defpost "Marginalia Hacks"
  {:created "2014-08-03"}
  [:strong "This is the sixth and final post in a series on my Clojure
     workflow."]
  "In my last post, I introduced Marginalia as a tool for
     (semi-)literate programming. Here are some tricks I’ve used to
     make Marginalia work for me – in particular, to support a style
     of working with investigatory “notebooks.” As always, your
     mileage may vary."
  [[:strong "Problem: I want to reorder my code snippets"] " to
     allow for more natural exposition."]
  [[:strong "Solution"] ": As discussed, Marginalia does not
     provide reordering or interpolation of source code in the same
     way that Knuth’s WEB does. By default, " [:code "lein marg"] "
     processes all the Clojure source code in your project except in
     the " [:code "test"] " directory, presenting namespaces in alphabetical
     order. The problem is exacerbated by the one-pass Clojure
     compiler, which expects everything to be declared before it is
     used."
     "I have been able to work around this to my satisfaction by
     specifying directories and/or files at the command line in the
     order I want them to appear. For example, if I wanted both "
     [:code "src"] " and " [:code "test"] " files in my output, and if
     I wanted " [:code "src/myproject/core.clj"] " to appear first, I
     would say"
     (code "lein marg src/myproject/core.clj src test")]
  ["If I wanted to reorder forms within " [:code "core.clj"]
   ", I could also just
     use Clojure’s " [:code "declare"] " macro to forward-declare the
     vars at the top of the file. This is far from the power of
     Knuth’s WEB, but it’s been good enough for me."]
  [[:strong "Problem: I want to see my Marginalia output as soon as
     I save my source code."]]
  [[:strong "Solution"] ": It’s nice to have quick feedback, so I
     use " [:code "conttest"] " to run " [:code "lein marg"]
     ", plus some Applescript (or equivalent) to reload the output in
     the browser."]
  "Example:"
  (code "conttest 'lein marg && \\
     osascript ~/bin/reload-browser.scpt \\
     file:///path/to/project/docs/uberdoc.html'")
  ["The Applescript " [:code "~/bin/reload-browser.scpt"] " is fairly simple,
     though you may have to adjust to suit your browser of choice:"]
  (code "on run argv
    tell application \"Google Chrome\"
        set URL of active tab of first window to item 1 of argv
    end tell
end run")
  "Though it does take a few seconds, Marginalia not being a speed
     demon, one can get pretty quick visual feedback using this
     approach."
  [:strong "Problem: I want to show expressions and the results of
     their evaluation together, à la iPython Notebook, Mathematica,
     Maple, Gorilla REPL, or Session."]
  [[:strong "Solution"]
   ": For Clojure evaluation, I use the
     cider-eval-last-sexp-and-append trick I described in my previous
     post on customizing Emacs. This results in something like the
     following, in Emacs and in Marginalia:"]
  [(img "emacs-eval")
   (img "marg-eval")]
  ["Here I added a single quote (" [:code "'"] ") to keep the
     resulting form from throwing an exception when the buffer is
     recompiled. This not quite iPython Notebook, but I find it gets
     me surprisingly far. And the source code remains completely
     usable as a whole, standalone program. This means I can combine
     notebook-style investigations directly in a working project
     without worrying how, eventually, to get the annotated code into
     production."]
  [[:strong "Problem: I want nice looking math formulae"]
   " in my “notebook.”"]
  [[:strong "Solution"] ": Use the "
   [:a {:href "http://www.mathjax.org/"} "MathJax"] " JavaScript library."]
  "As shown in the images from the previous post, math can be
     typeset quite nicely with MathJax. This can either be imported
     directly from a CDN, as follows:"
  ;; FIXME: don't convert quote on first line:
  (code ";; &lt;script type=\"text/javascript\"
;;  src=\"http://cdn.mathjax.org/mathjax/latest/MathJax.js?config=TeX-AMS-MML_HTMLorMML\"&gt;
;; &lt;/script&gt")
  ["(this being done directly in the Clojure source file, comments
     being rendered as Markdown, and therefore HTML). Or, copy down
     the MathJax JavaScript source and use the " [:code "-j"]
     " argument to " [:code "lein marg"] "."]
  ["It took me a little time to figure out how to properly escape
     the TeX for inline math formulae. The usual pattern in HTML is to
     use " [:code "\\("] " and " [:code "\\)"] ", as follows:"]
  (code "\\(r^2 = x^2 + y^2\\)")
  ["yielding \\(r^2 = x^2+y^2 \\) inline."]
  ["For this to work correctly, Marginalia makes you add another "
   [:code "\\"] ":"]
  (code "\\\\(r^2 = x^2 + y^2\\\\)")
  ["For inset math formulae, the required " [:code "$$"]
   " is unchanged:"]
  (code "$$r^2 = x^2 + y^2$$")
  "Which gives:"
  "$$r^2 = x^2 + y^2.$$"
  [[:strong "Problem: I want to show graphs"]
   " along with my text, code, and mathematics."]
  [[:strong "Solution"] ": Use a JavaScript plotting library, and a
     little Clojure to prepare the data."]
  ["This last hack is perhaps the most fun and the most “hacky” of
    the bunch. One of the best features of notebook solutions like
    iPython Notebook is the ability to show graphs inline with the
    code that generates them. This is not really in the wheelhouse of
    Marginalia, which was meant as a static documentation tool, but
    since we can incorporate JavaScript (as seen above, for
    mathematics), we can leverage existing plotting libraries. I use "
   [:a {:href "https://github.com/eigenhombre/i3d3"} "i3d3"]
   ", an open-source JavaScript plotting library built
     on top of " [:a {:href "http://d3js.org/"} "d3.js"] "."]
  "The only obvious difficulty is how to get the data points into
     the browser for JavaScript to plot. For this, we need to do the
     following:"
  [:ol
   [:li "Using the REPL, capture the Clojure data, format it as
     JavaScript, and write to a disk file in the project,
     " [:code "local.js"] "."]
   [:li "Load the resulting local.js, as well as any other needed
      libraries (in my case, d3, i3d3, and "
    [:a {:href "http://underscorejs.org/"} "underscore.js"] ") as part of
      the Marginalia command."]]
  ["The Clojure for Step 1 is shown in "

   [:a {:href
        "https://gist.github.com/eigenhombre/bed80ab20c2bab2ef9d7"}
    "this gist"]
   ". The i3d3 function, evaluated in the REPL, does the work of
      preparing the data on disk. The intermediate JavaScript file
      looks something like this:"]
  (code "// BEGIN DIV plot2
i3d3.plot({\"ylabel\":\"Entries\",
           \"xlabel\":\"Priorities\",
           \"size\":[700,250],
           \"data\":[{\"type\":\"bars\",
                      \"bins\":[93991,103924,3396],
                      \"color\":\"grey\",
                      \"range\":[0,4]}],
           \"div\":\"plot2\"});
// END DIV plot2")
  ["(Multiple DIVs are supported in a single file by changing the
     DIV ID for each i3d3 function call in the REPL)."]
  "The command to continuously run Marginalia (Step 2) is:"
  (code "conttest \"lein marg src/liveana/core.clj \\
         -c style.css \\
         -j 'd3.v3.min.js;underscore-min.js;i3d3.js;local.js' \\
         && osascript ~/bin/reload-browser.scpt \\
                      file://path/to/docs/uberdoc.html\"")
  ["Here I have put the JavaScript libraries in the " [:code "docs/"]
   " directory in advance; also, since i3d3 benefits from a style
     sheet, that is prepared and included in the Marginalia shell
     command as well."]
  ["Here’s an example plot, from "
   [:a {:href "http://eigenhombre.com/example-notebook/"}
    "this notebook"] ":"]
  (img "plot-example")
  ["I told you it was hacky, but "
   [:a {:href "http://eigenhombre.com/example-notebook/"}
    "give the example a whirl"]
   " anyways. Since i3d3 supports panning and zooming, that comes for
     free!"
   (sidenote "Thanks to John Kelley / WIPAC for permission to show this work
     in this post.")]
  "As tools like Gorilla REPL and Session become more popular and
   powerful, I may discard this way of injecting graphs into
   “literate” programs. But I did want to see how far I could push
   Marginalia as a Clojure-based substitute for iPython Notebook,
   and found this approach surprisingly powerful. I might package it
   into something a bit more off-the-shelf if anyone else shows
   interest."
  "This concludes the series of posts on Clojure workflows--thanks
   to any of you who made it this far! The Clojure tooling landscape
   is constantly shifting, and I continue to learn new tricks, so
   things may look different a year from now. In the mean time,
   perhaps some people will find something helpful here."
  "Happy hacking!")


(defpost "South Pole Blog"
  {}
  (section "What the blog is for"
    {:date "2006-02-10"}
    "Date: 2006-02-10"
    "On my sixth trip to the South Pole, I will try to publish at least one
drawing or photo each day, possibly including some text.")
  (section "Eclipse Town"
    "Date: 2008-02-08"
    "Image: https://farm3.static.flickr.com/2063/2250952114_961a283236_m.jpg"
    "Four days left to go. I'm on a 1AM - 5PM work schedule right now. The
schedule has picked up considerably as everyone tries to fit in as much
work as possible before we redeploy back to New Zealand."

    "One noteworthy event happened a day and a half ago: a near-total
(80%) eclipse of the sun. I stayed up several hours later than usual
to see it. A bunch of us \"beakers\" went out when it started in order
to see the light change and to take pictures. Wind chills were about
-75F. You know it's cold when your eyelids try to freeze shut when you
blink, or when you can eat the icicles forming on your mustache."

    "We stood outside for awhile and struggled with failing batteries, aching
fingers and fogging glasses, got a few blurry pictures of the eclipse,
then took a few shots at the Geographic and Ceremonial Poles and headed
inside to warm up. Once inside, we found out we were too early for the
real action. Soon most of the people on station were crowding at the
windows and doorways (unwilling to face the mustache icicles by bundling
up and going outside). Several people had sheets of aluminized mylar
which served as an excellent filter for photography. I was glad I had
brought my big lens, as it made the above picture possible (see Flickr
for more pictures)."

    "The neat thing about the eclipse was the light really changed - after 25
days of mostly blazing sunlight, it was as close to night at the South
Pole as I am likely to get. While the eclipse did darken everything, it
had the effect of increasing contrast and adding a liquid silver
tonality to the snow surface which usually sparkles like powdered
diamond dust."

    "We hit a record low of almost -55F during the eclipse."

    "It was a late day for me but the sights and photos (and camaraderie with
other shivering, clicking and squinting Polies) were worth it. As an
added benefit, I am half-way shifted over to the day schedule now, which
will make returning to NZ more restful.
"))


(defpost "Communicating With Humans"
  {:created "2014-08-02"}
  [:em "If nobody but me likes it, let it die. -- "
   [:a {:href "http://www.informit.com/articles/article.aspx?p=1193856"}
    "Knuth"]]
  (img "marg-printed")
  [:span
   "This is the fifth post in a "
   [:a {:href "/clojure/2014/07/03/an-advanced-clojure-workflow/"} "series"]
   " about my Clojure workflow."]
  "When you encounter a new codebase, what best allows you to
   quickly understand it so that you can make effective changes to
   it?"
  "I switched jobs about six months ago. There was intense
   information transfer both while leaving my old projects behind,
   and while getting up to speed with new ones. I printed out a lot
   of code and read it front-to-back, quickly at first, and then
   carefully. I found this a surprisingly effective way to review
   and learn, compared to my usual way of navigating code on disk
   and in an editor solely on an as-needed basis. "
  "If this (admittedly old-school) way of understanding a program
   works well, how much better might it work if there was enough
   prose interspersed in amongst the code to explain anything
   non-obvious, and if the order of the text was presented in such
   a way as to aid understanding? "
  "What is the target audience of computer programs, anyways? It is
   clearly the machines, which have to carry out our insanely
   specific instructions... but, equally clearly, it is also the
   humans who have to read, understand, maintain, fix, and extend
   those programs. It astonishes me now how little attention is
   paid to this basic fact. "
  ["In addition to communicating, we also have to "
   [:em "think carefully"] " about our work. While not every
      programming problem is so difficult as to merit "
   [:a {:href
        "https://www.youtube.com/watch?v=f84n5oFoZBc"}
    " a year's worth
      of contemplation"]
   ", any software system of significant size requires continual
    care, attention, and occasional hard thinking in order to keep
    complexity under control. The best way I know to think clearly
    about a problem is to write about it &#x2013; the harder the
    problem, the more careful and comprehensive the required
    writing. "]
  "Writing aids thinking, because it is slower than
   thought... because you can replay thoughts over and over, iterate
   upon and refine them. Because writing is explaining, and because
   explaining something is the best way I know to learn and
   understand it. "
  [[:a {:href "http://en.wikipedia.org/wiki/Literate_programming"}
    "Literate Programming"]
   " (LP) was invented by Donald Knuth in the 1980s as a way to
    address some of these concerns. LP has hardcore enthusiasts
    scattered about, but apparently not much traction in the
    mainstream. As I have gotten more experience working with
    complex codebases, and more engaged with the craft or
    programming, I have become increasingly interested in LP as a
    way to write good programs. Knuth takes it further, considering
    the possibility that programs are, or could be, " [:em "works
    of literature"] ". "]
  "Knuth's innovation was both in realizing these possibilities and
   in implementing the first system for LP, called WEB. WEB takes a
   document containing a mix of prose and code and both typesets it
   in a readable (beautiful, even) form for humans, and also orders
   and assembles the program for a compiler to consume. "
  ["Descendents and variants of WEB can be found in use
      today. My favorite for Clojure is currently "
   [:a {:href
        "https://github.com/gdeer81/marginalia"} "Marginalia"] ",
      originally by Michael Fogus and currently maintained by Gary
      Deer. "]
  [[:a
    {:href (str "http://web.archive.org/web/20090102151452/"
                "http://www.perl.com/pub/a/tchrist/litprog.html")}
    "Purists of LP will disagree"]
   " that systems like Marginalia, which do not support reordering
    and reassembly of source code, are \"true\" Literate
    Programming tools; and, in fact, there is a caveat on the
    Marginalia docs to that effect... but what Marginalia provides
    is good enough for me: "]
  [:ol
   [:li "Placement of comments and docstrings adjacent to the
      code in question; "]
   [:li "Beautiful formatting of same; "]
   [:li "Support for Markdown/HTML and attachment of JavaScript
      and/or CSS files; therefore, for images, mathematics (via
      MathJax) and graphing (see next blog post). "]]
  "The result of these capabilities is a lightweight tool which
   lets me take an existing Clojure project and, with very little
   extra effort, generate a Web-based or printed/PDF artifact
   which I can sit down with, learn from, and enjoy contemplating."
  (section "Marginalia in Action:"
    (img "marg-screenshot")
    (img "emacs-screenshot"))
  (section "The Notebook Pattern"
    "I often start writing by making simple statements or questions: "
    [:ol
     [:li "I want to be able to do \\(X\\).... "]
     [:li "I don't understand \\(Y\\).... "]
     [:li "If we had feature \\(P\\), then \\(Q\\) would be easy.... "]
     [:li "How long would it take to compute \\(Z\\)? "]]
    ["Sentences like these are like snippets of code in the
      REPL: things to evaluate and experiment with. Often these
      statements are attached to bits of code -- experimental
      expressions, and their evaluated results. They are the
      building blocks of further ideas, programs, and chains of
      thought. In "
     [:a {:href "/clojure/2014/08/03/marginalia-hacks/"} "my next post"]
     ", I'll talk about using Marginalia to make small notebooks
      where I collect written thoughts, code, expression, even
      graphs and plots while working on a problem. This workflow
      involves some Marginalia hacks you may not see elsewhere. "]
    "Meanwhile, here are some quotes about LP:"
    "\"Instead of writing code containing documentation, the
     literate programmer writes documentation containing
     code.... The effect of this simple shift of emphasis can be so
     profound as to change one's whole approach to programming.\"
     --Ross Williams, FunnelWeb Tutorial Manual, p.4. "
    "\"Knuth's insight is to focus on the program as a message
     from its author to its readers.\" --Jon Bently,
     \"Programming Pearls,\" Communications of the ACM, 1986."
    ["\"... Literate programming is certainly the most
     important thing that came out of the TeX project. Not only
     has it enabled me to write and maintain programs faster and
     more reliably than ever before, and been one of my greatest
     sources of joy since the 1980s--it has actually been
     indispensable at times. Some of my major programs, such as
     the MMIX meta-simulator, could not have been written with any
     other methodology that I've ever heard of. The complexity was
     simply too daunting for my limited brain to handle; without
     literate programming, the whole enterprise would have flopped
     miserably.\" --Donald Knuth, "
     [:a {:href
          "http://www.informit.com/articles/article.aspx?p=1193856&rll=1"}
      "interview"] ", 2008."]))


(defpost "Testing, Continuously"
  {:created "2014-07-20"}
  [:strong "This is the fourth post in a series about Clojure workflows."]
  "In my last post, I laid out a workflow based on TDD but with the
  addition of “literate programming” (writing prose interleaved with
  code) and experimentation at the REPL. Here I dive a bit deeper into
  my test setup."

  ["Even before I started developing in Clojure full time, I
  " [:a {:href
         "http://eigenhombre.com/testing/2012/03/31/ontinuous-testing-in-python-clojure-and-blub/"}
     "discovered"] "
  that creating a configuration that provides near-instant test
  feedback made me more efficient as a developer. "
     [:strong "I expect to be able to run all relevant tests every time I
  hit the “Save” button"] " on any file in my project, and to see the
  results within “a few” (preferrably, less than 3-4) seconds. Some
  examples of systems which provide this are:"]
  [:ol
   [:li "Ruby-based "
    [:a {:href "https://github.com/guard/guard#readme"} "Guard"]
    ", commonly used in the Rails community;"]
   [:li [:a {:href "https://github.com/eigenhombre/continuous-testing-helper"}
         "Conttest"] ", a language-agnostic Python-based test runner I wrote;"]
   [:li "in the Clojure sphere:"
    [:ol
     [:li "" [:a {:href "https://github.com/marick/Midje"} "Midje"] ",
     using the " [:code ":autotest"] " option;"]
     [:li "Expectations, using the autoexpect plugin for Leiningen;"]
     [:li [:a {:href "https://github.com/slagyr/speclj"} "Speclj"] ",
     using the " [:code "-a"] " option;"]
     [:li "clojure.test, with " [:a {:href
                                     "https://github.com/jakepearson/quickie"} "quickie"] " (and
     possibly other) plugins"]]]]

  ["I used to use Expectations; then, for a long time I liked Midje for
  its rich DSL and the ability to develop functionality bottom-up or
  " [:a {:href
         "https://github.com/marick/Midje/wiki/The-idea-behind-top-down-development"}
     "top-down"] ".  Now, I pretty much just use Speclj because it's
     autotest reloader is the most reliable and trouble-free."]
  "(Earlier versions of this post had details for setting up Midje,
  now omitted -- see the Speclj docs to get started.)"
  "Running your tests hundreds of times per day not only reduces
  debugging time (you generally can figure out exactly what you broke
  much easier when the deltas to the code since the last successful
  test are small), but they also help build knowledge of what parts of
  the code run slowly. If the tests start taking longer than a few
  seconds to run, I like to give some thought to what could be
  improved – either I am focusing my testing effort at too high of a
  level (e.g. hitting the database, starting/stopping subprocesses,
  etc.) or I have made some questionable assumptions about performance
  somewhere."
  "Sometimes, however, despite best efforts, the tests still take
  longer than a few seconds to run. At this point I start annotating
  tests with metadata indicating that those tests should be skipped
  during autotesting (I still run the full test suite before
  committing (usually) or pushing to master (always)).  In this way, I
  can continue to get the most feedback in real time as possible –
  which helps me develop quality code efficiently. The exact
  mechanism for skipping slow tests depends on the test library you're
  using; I haven't had to do it with Speclj yet."
  "In the next post, we’ll switch gears and talk about literate
  programming with Marginalia.")


(defpost "Continuous Testing in Python, Clojure and Blub"
  {:created "2012-03-31"}
  (img "IMG_9625" "A separate monitor is handy for showing tests
  results continuously while working. The paintbrushes are strictly
  optional." {:width 300})
  [:strong "What follows is a somewhat rambling introduction to continuous,
  test-driven development, focusing mainly on Python and influenced by
  Clojure tools and philosophy. At the end, a simple script is
  introduced to help facilitate continuous TDD in (almost) any
  language."]
  ["For the last four years I have increasingly followed a test-driven
  approach in my development. My approach continues to evolve and
  deepen even as some of the "
   [:a {:href "http://www.headspring.com/2011/11/guard-rail-programming"}
    "limits of TDD"] " are becoming clearer to me."]
  "Initially, I had a hard time getting my head around TDD. Writing
  tests AND production code seemed like twice as much work, and I
  typically ran the program under development, e.g. with print
  statements added, to test each change. But making changes to old
  code was always a fairly daunting proposition, since there was no
  way to validate all the assumptions I’d checked “by eye” just after
  I’d written the code."
  ["TDD helps reduce risk by continuously verifying your assumptions
  about how the code should perform at any time. Using TDD for a
  " [:a {:href "http://npxdesigns.com/projects/icecube-live/"} "fairly
  large project"] " has saved my bacon any number of times."]
  "The basic approach is that the test code and the production code
  evolve together more or less continuously, as one follows these
  rules:"
  [:ol
   [:li "Don’t write any production code without a failing unit test"]
   [:li "Write only enough production code needed to make the tests pass"]]
  "Once I started writing tests for all new production code, I found I
  could change that code and make it better without fear. That led to
  much better (and usually simpler) code. I realized I was spending
  much less time debugging; and when there were bugs, the tests helped
  find them much faster. As I have gained experience with this
  approach I have found that the reliability of, and my trust in, my
  code written with TDD is vastly superior than otherwise. The
  two-rule cycle also tends to foster simplicity, as one tends to
  eschew any modifications that don’t actually achieve the desired
  objectives (i.e. meet the goals of the software, as specified
  formally in the tests themselves). The process is also surprisingly
  agreeable!"
  ["It goes without saying that if you follow this approach you will be
  running your tests a lot. The natural next step is to automate this
  more. In the book Foundations of Agile Python Development, Jeff
  Younker explains how to make Eclipse run your unit tests "
   [:strong "every time you save a file"] " in the project. The speed
  and convenience of this approach was enough to get me to switch from
  Emacs to Eclipse for awhile."]
  ["Most of my daily programming work is in Python, but I have been an
   avid "
   [:a {:href "http://johnj.com/navblog/in-defense-of-hobbies/"}
   "hobbyist"] " in Clojure for several months now. It wasn’t until I
   saw Bill Caputo’s preparatory talk for Clojure/West here in Chicago
   that I heard the term " [:strong "continuous testing"] " and
   realized that this is what I was already doing; namely, the natural
   extension of TDD in which one runs tests continuously rather than
   “by hand.” Bill demoed the expectations module and the autoexpect
   plugin for Leiningen, which runs your tests after every save
   without incurring the overhead of starting a fresh JVM each time."]
   "(One point Bill made in his talk was that if your tests are slow,
   i.e. if you introduce some new inefficiency, you really notice
   it. Ideally the tests should take a few seconds or less to
   complete.)"
  ["Back to Python-land. Not wanting to be always leashed to Eclipse,
   and inspired by the autoexpect plugin, I started looking for an
   alternative to using Eclipse's auto-builders -- something I could
   use with Emacs or any other editor. There are a lot of continuous
   build systems out there, but I wanted something simple which would
   just run on the command line on my laptop screen while I edited
   code on my larger external monitor. I found "
   [:a {:href "https://github.com/brunobord/tdaemon/blob/master/tdaemon.py"}
    "tdaemon"] " on GitHub; this
   program walks a directory tree and runs tests whenever anything
   changes (as determined by keeping a dictionary/map of SHA values
   for all the files). This is most of what I want, but it restricts
   you to its own choices of test programs."]
  ["In a large project with many tests, some fast and some slow, I
   often need to specify a specific test program or arguments. For
   example, I have a wrapper for " [:a {:href
   "http://readthedocs.org/docs/nose/en/latest/"} "nosetests"] " which
   will alternately run all my “fast” unit tests, check for PEP-8
   compliance, run Django tests, etc. In some cases, such as debugging
   a system with multiple processes, I may need to do something
   complex at the shell prompt to set up and/or tear down enough
   infrastructure to perform an
   existing test in a new way."]
  ["One piece of Clojure philosophy (from Functional Programming,
   a.k.a. “FP”) that has been influencing my thinking of late is the
   notion of " [:strong "composability"] ": the decoupling or
   disentanglement of the pieces of the systems one builds into small,
   general, composable pieces. This will make those pieces easier to
   reuse in new ways, and will also facilitate reasoning about their
   use and behaviors. (Unfortunately, the merits of the FP approach,
   which are many, have poisoned my enthusiasm for OO to the extent
   that I will typically use a function, or even a closure, before
   using an object, which would perhaps be more Pythonic in some
   cases)."]
  "So, in the current case under discussion (continuous testing),
  rather than making some kind of stateful object which knows about
  not only your current file system, but also what tests should be
  allowed, their underlying dependencies, etc., it would be better (or
  at least more \"functional\") instead to simply provide a
  directory-watching function that checks a dictionary of file hashes,
  and compose that function with whatever test program suits your
  purposes at the moment."
  ["The result of these thoughts is a small script called " [:a {:href
  "https://github.com/eigenhombre/continuous-testing-helper"}
  "conttest"] " which is a simplification of " [:code "tdaemon"] "
  that composes well with any test suite you can specify on the
  command line."]
  "Some examples follow:"
  (code "$ conttest nosetests  # Runs nosetests whenever the files on disk change

$ conttest nosetests test.path:harness  # Runs only tests in 'harness'
                                        # object in path/to/test -- handy
                                        # for developing a single feature
                                        # or fixing a single bug.
# Check both PEP-8 style and unit tests:
$ conttest 'pep8 -r . ; nosetests'
")
  ["It would work equally well with a different language (\"" [:a
  {:href "http://www.paulgraham.com/avg.html"} "blub"] " language\") with a
  separate compilation step:"]
  (code "$ conttest 'make && ./run-tests'")
  "Using this program, depending on my needs of the moment, I can
  continuously run a single unit test, all my “fast” unit tests, or,
  if I’m willing to deal with slower turnaround times, all my unit and
  integration tests."
  ["The script is on " [:a {:href
  "https://github.com/eigenhombre/continuous-testing-helper"}
  "GitHub"] " for your continuous enjoyment of continuous testing. May
  you find it helpful."]
  ["(Ironically, this script does NOT work that well for JVM languages
  like Clojure since the startup time is lengthy (a couple of seconds
  on my MacBook Pro). Most of the testing frameworks have autotest
  capabilities built in, which work great.)"])


(defpost "Macro-writing Macros"
  {:created "2015-11-25"}
  [:em "... in which we explore the power of macros, and macro-writing
  macros, to DRY out repetitive code."]
  (img "macro-sketch")
  "I've been writing Clojure full time for nearly two years now. I
  have a pretty good feel for the language, its virtues and its
  faults. Mostly, I appreciate its virtues (though I still wish the
  REPL started faster)."
  "For me one of the language's attractions has always been that it's
  a Lisp -- a \"homoiconic\" language, i.e., one defined in terms of
  its own data structures. Homoiconicity has one primary virtue, which
  is that it makes metaprogramming more powerful and straightforward
  than it is in non-homoiconic languages (arguably at some cost to
  readability)."
  ["In Lisp, this metaprogramming is accomplished with " [:em "macros"]
   ", which are functions that transform your code during a separate
  stage of compilation. In other words, you write little programs to
  change your programs before they execute. In effect, you extend the
  compiler itself."]
  "I run a Clojure study group at work and find that it can be hard to
  explain the utility (or appeal) of this to newcomers to Lisp. This
  is partly because macros do things you can't easily do in other
  languages, and because the things you want to do tend to relate to
  abstractions latent in a particular codebase."
  ["While " [:a {:href "https://github.com/eigenhombre/moarquil"}
             "playing around with 3d rendering"]
   " in " [:a {:href "http://quil.info/"} "Quil"] ", I recently came
   across a use case that reminded me of the following quote by Paul Graham:"
   (blockquote "The shape of a program should reflect only the
   problem it needs to solve. Any other regularity in the code is a
   sign, to me at least, that I'm using abstractions that aren't
   powerful enough-- often that I'm generating by hand the expansions
   of some macro that I need to write"
               (sidenote "Paul Graham, " [:em "Revenge of the Nerds"] ", "
                         [:a {:href "http://www.paulgraham.com/icad.html"}
                          "http://www.paulgraham.com/icad.html"])
               ".")]
  "In Quil, there are multiple situations in which one needs to create
  a temporary context to carry out a series of operations, restoring
  the original state afterwards:"
  [:ol
   [:li "Save current style with " [:code "push-style"] "; change
  style and draw stuff; restore previous style with "
    [:code "pop-style"] "."]
   [:li "Start shape with " [:code "begin-shape"] "; draw vertices; "
    [:code "end-shape"] " to end."]
   [:li "Save current position/rotation
   with " [:code "push-matrix"] "; translate / rotate and draw stuff;
   restore old position/rotation with " [:code "pop-matrix"] "."]]
  "Here's an example:"
  (code "(push-matrix)
(try
  (push-style)
  (try
    (fill 255)
    (no-stroke)
    (translate [10 10 10])
    (begin-shape)
    (try
      (vertex x1 y1 0)
      (vertex x2 y2 0)
      (vertex x2 y2 h)
      (vertex x1 y1 h)
      (vertex x1 y1 0)
      (finally
        (end-shape)))
    (finally
      (pop-style)))
  (finally
    (pop-matrix)))
")
  ["The " [:code "(try ... (finally ...))"] " constructions may not be
  strictly needed for a Quil drawing, but it's a good habit to
  guarantee that stateful context changes are undone, even if problems
  occur."]
  "In a complex Quil drawing the idioms for saving style, translation
  state, and denoting shapes appear often enough that one hungers for
  a more compact way of representing each.  Here's one way to do it:"
  (code "(defmacro with-style [& body]
  (push-style)
  (try
    ~@body
    (finally
      (pop-style))))

(defmacro with-matrix [& body]
  (push-matrix)
  (try
    ~@body
    (finally
      (pop-matrix))))

(defmacro with-shape [& body]
  (begin-shape)
  (try
    ~@body
    (finally
      (end-shape))))

")
  "The original code then becomes more compact and easier to read:"
  (code "
(with-matrix
  (with-style
    (fill 255)
    (no-stroke)
    (translate [10 10 10])
    (with-shape
      (vertex x1 y1 0)
      (vertex x2 y2 0)
      (vertex x2 y2 h)
      (vertex x1 y1 h)
      (vertex x1 y1 0))))")
  ["In "
   [:a {:href
        "https://github.com/eigenhombre/moarquil/blob/master/src/moarquil/render.clj"}
    "this example code"] ", the contexts " [:code "with-matrix"]
    ", etc. appear so often that the resulting savings in lines of
  code and mental overhead for the reader is substantial."]
  ["However, the astute reader will realize that the macro definitions
  themselves are pretty repetitive--in fact, they look almost
  identical except for the setup and teardown details (this kind of
  \"context manager\" pattern is common enough that Python has "
   [:a {:href (str "http://eigenhombre.com/2013/04/20/"
                   "introduction-to-context-managers/")}
    "its own language construct"] " for it)."]
  "I generally reach for macros when I have a pattern that occurs with
  obvious repetition that's not easy to abstract out using just pure
  functions.  Control abstractions such as loops or exception handling
  are common examples. (I find this situation occurs especially
  frequently when writing test code)."
  "In any case, the solution for our repetitive macros could be
  something like:"
  (code "(defmacro defcontext
  [nom setup teardown]
  `(defmacro ~(symbol (str \"with-\" nom))
     [~'& body#]
     `(do
        ~'~setup
        (try
          ~@body#
          (finally
            ~'~teardown)))))")
  ["Yikes! I have to admit I had to write a lot of macros, and also refer to "
   [:a {:href
        "http://hubpages.com/technology/Clojure-macro-writing-macros"} "this
  helpful page"]
   " for reference, before I could write (and grok) this macro."]
  ["With " [:code "defcontext"] " in hand, our repetitive macro code
  just becomes:"]
  (code "(defcontext style (push-style) (pop-style))
(defcontext shape (begin-shape) (end-shape))
(defcontext matrix (push-matrix) (pop-matrix))")
  ["These are exactly equivalent to the three context macros ("
   [:code "with-*"] ") defined above."]
  ["With a little effort, it's actually not too hard to construct such
  a nested macro. It's largely a matter of writing out the code you
  want to generate, and then writing the code that generates it,
  testing with "
   [:code "macroexpand-1"] " at the REPL as you go. "
   [:a {:href "http://hubpages.com/technology/Clojure-macro-writing-macros"}
    "This page by A. Malloy"] " has a lot of helpful remarks,
  including this cautionary note: \"Think twice before trying to nest
  macros: it's usually the wrong answer.\" In this case, I actually
  think it's the right answer, because the pattern of a context with
  setup and teardown is so common that I know I'll reuse this macro
  for many other things--we have effectively added one of
  my favorite Python features to Clojure in just a few lines of
  code"
    (sidenote "To be even more like Python's context managers, "
              [:code "defcontext"] " would want to enable the user to
              bind some local state resulting from the setup phase of
              execution (\"" [:code "with x() as y:"] "&rdquo; idiom);
              examples include file descriptors or database
              connections.  This is left as an exercise for the
              reader.")
    "."]
  ["There's a saying in the Clojure community: " [:code "data >
  functions > macros"] ". I'm a big believer in this. Clojure's
  powerful built-in abstractions for wrangling data in all its forms
  make it the language I prefer above all others these days. But
  occasionally that means wrangling the data that is the code itself,
  thereby reaping the benefits in power, brevity and expressiveness."]
  (img "moarquil"
       [:span "Image generated by the Quil code used for this example; original
       code on GitHub is "
        [:a {:href
             (str "https://github.com/eigenhombre/moarquil/blob/master/"
                  "src/moarquil/util.clj#L5")} "here"] "."]
       {}))


(defpost "Emacs Customization for Clojure"
  {:created "2014-07-05"}
  [[:strong "Synopsis"] ": "
   [:em "I talk about the value of paredit in Emacs
  and show a trick which allows you to insert the result of any given
  Clojure expression directly underneath that expression."]]
  ["As I said in the "
   [:a {:href
        "http://eigenhombre.com/clojure/2014/07/03/an-advanced-clojure-workflow/"}
    "first post"] ", a good set of tools can make a big difference in
  productivity and enjoyment while working in any language, and
  Clojure is certainly no exception. The most important tool in your
  toolbox, regardless of language, is your editor. Editing Lisp code
  in particular is much more natural with the right editor setup."]
  ["I have been using Emacs since the 1990s, though I still consider
  myself a novice (Emacs is that way). Though good alternatives
  exist, "
   [:a {:href
        "http://cemerick.com/2013/11/18/results-of-the-2013-state-of-clojure-clojurescript-survey/"}
    "over half of the Clojure community has adopted Emacs"] " despite
  its lack of polish and its Himalayan learning curve. Emacs is
  massively customizable, with hundreds of plugins available, and can
  be extended to just about any degree using its own flavor of
  Lisp."]
  ["While I’ll give a few Emacs configuration tips below, I don’t give
  a complete recipe for customizing Emacs here. "
   [:a {:href "https://github.com/eigenhombre/emacs-config/"} "My Emacs
  configuration file"] " is on GitHub if you are interested."]
  (section "Paredit"
    ["Though it takes a little getting used to, I started using "
     [:a {:href "http://emacsrocks.com/e14.html"} "paredit mode"]
     " exclusively a year or so and it has made a huge difference, not
    only for my productivity with the language, but also in my
    enjoyment and appreciation of Lisp in general."]
    ["In paredit, the units of code are not lines of text or sequences
    of characters, but " [:em "s-expressions"] " (generally,
    lists). In other words, paredit gives you the ability to easily
    manipulate the data structures that your code is built out
    of. With various key combinations, you can kill expressions, split
    them, join them, expell (“barf”) or absorb (“slurp”) neighboring
    expressions, and so on. This "
    [:a {:href
         "https://github.com/joelittlejohn/paredit-cheatsheet"} "excellent
    cheat"] " sheet covers the whole range of commands and
    keybindings."]
    "I think that it wasn’t until I started using paredit that I
    really got used to all those parentheses. Now editing non-Lisp
    code feels unnatural to me, since I don’t have the ability to
    sling blocks of code about with the same ease without those
    parentheses steering my editor. And, though it’s hard to give a
    specific reason for this, working directly with trees of Lisp
    expressions gives me a feeling of somehow being closer to the
    essence of computation.")
  (section "CIDER"
    ["An even more important tool is "
     [:a {:href
          "https://github.com/clojure-emacs/cider"} "CIDER"]
     ", which integrates the Clojure REPL directly with Emacs. The
    upshot of this kind of integration is that one can " [:em "evaluate
    Clojure code directly from the editor"] " with a single key
    combination and see the results instantly."]
    "I bind my own key combinations to the common CIDER commands to
    make them as quick to execute as possible:"
    (code "(add-hook 'clojure-mode-hook
   '(lambda ()
      (paredit-mode 1)
      ;; Actual keyboard bindings follow:")
    ["Start CIDER REPL with " [:code "control-o-j"] ":"]
    (code "      (define-key clojure-mode-map (kbd \"C-o j\") 'cider-jack-in)
")
    [[:strong "Restart"] " CIDER REPL
    with " [:code "control-o-J"] " (in case of dirty JVM, etc.):"]
    (code "      (define-key clojure-mode-map (kbd \"C-o J\") 'cider-restart)")
    ["Use " [:code "⌘-i"] " to evaluate previous expression and display
    result in minibuffer (I am on a Mac):"]
    (code "      (define-key clojure-mode-map (kbd \"s-i\") 'cider-eval-last-sexp)")
    ["Rather than showing in minibuffer, use " [:code "control-o y"] "
    to insert the result " [:em "directly in the code"] "."]
    (code "      (define-key clojure-mode-map (kbd \"C-o y\")
        (lambda ()
           (interactive)
           (insert \"\\n;;=>\\n\")
           (cider-eval-last-sexp 't))) ")
    "As an example of using this, suppose I have the following Clojure code:"
    (code "(range 100)
|")
    ["With the position of my cursor denoted by " [:code "|"] ", and I type "
     [:code "control-o y"] ", I get:"]
    (code "(range 100)
;;=>
(0 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24 25
26 27 28 29 30 31 32 33 34 35 36 37 38 39 40 41 42 43 44 45 46 47 48
49 50 51 52 53 54 55 56 57 58 59 60 61 62 63 64 65 66 67 68 69 70 71
72 73 74 75 76 77 78 79 80 81 82 83 84 85 86 87 88 89 90 91 92 93 94
95 96 97 98 99)")
    ["To see a video of this trick in action, check out this "
     [:a {:href "https://vimeo.com/99980843"} "demonstration
    video"] ". I find that using " [:code "cider-eval-last-sexp"] "
    feels a little like working with the InstaREPL in "
    [:a {:href "http://www.lighttable.com/"}
     "Light Table"]
    ", except that here you can actually preserve and edit the results
     of expressions rather than just viewing them inline. This is
     especially helpful for the style of semi-literate programming I
     will describe in a coming post."]
    ["Other Emacs plugins which I use and recommend are:"
     [:ol
      [:li [:a
            {:href "https://github.com/clojure-emacs/clojure-mode"}
            "clojure-mode"] ",
      which provides syntax coloring, indentation rules, and source
      code navigation;"]
      [:li [:a
            {:href "https://github.com/Fanael/rainbow-delimiters"}
            "rainbow-delimiters"] ",
      which colorizes parentheses according to their nesting depth;
      and,"]
      [:li [:a
            {:href "https://github.com/auto-complete/auto-complete"}
            "auto-complete"] ",
      which provides IDE-like auto-completion. "]]]
    ["To install Emacs packages, e.g. paredit, " [:code "M-x
    install-package [RET] paredit"] ". Or, steal the code from "
    [:a {:href
         "https://github.com/eigenhombre/emacs-config/blob/master/init.el"}
     "my init.el"] " file which installs and updates any missing
     packages from a list of packages you define."]
    "Conference talks and online videos are a great way to find Emacs
    tricks to steal. For example, I’ve seen people run unit tests from
    inside Emacs and display failures inline with the code, which is a
    very cool trick, though not exactly what I want. The best way to
    increase your Emacs-fu is to sit down with a grizzled Emacs guru
    and compare notes and techniques. I wish I had more opportunities
    to do this."
    ["In " [:a {:href "testing,-continuously.html"} "the next post"]
     ", we’ll talk about test-driven development and how
     “RDD” (REPL-driven development) and TDD enhance and complement
     each other."]))


(defpost "TDD, RDD and DDD"
  {:created "2014-07-20"}
  [:strong "This is the third post in a series about my current
  Clojure workflow."]
  ["Having "
   [:a {:href "emacs-customization-for-clojure.html"}
    "discussed Emacs setup"] " for Clojure, I now present a sort
  of idealized workflow, in which I supplement traditional TDD with
  literate programming and REPL experimentation."]
  "First, some questions:"
  [:ol {:type "a"}
   [:li "How do you preserve the ability to make improvements without
   fear of breaking things?"]
   [:li "What process best facilitates careful thinking about design
   and implementation?"]
   [:li "How do you communicate your intentions to future
   maintainers (including future versions of yourself)?"]
   [:li "How do you experiment with potential approaches and solve
   low-level tactical problems as quickly as possible?"]
   [:li "Since \"simplicity is a prerequisite for
   reliability\" (Dijkstra), how do you arrive at simple designs and
   implementations?"]]
  ["The answer to (a) is, of course, by having good tests; and the best
  way I know of to maintain good tests is by writing test code
  along with, and slightly ahead of, the production code (test-driven
  development, a.k.a. TDD). However, my experience with TDD is that it
  doesn’t always help much with the other points on the list (though
  it helps a bit with (b), (c) and (e)). In particular, "
   [:a {:href "http://www.infoq.com/presentations/Simple-Made-Easy"}
    "Rich Hickey points out"] " that TDD is not a substitute
  for " [:em "thinking"] " about the problem at hand."]
  ["As an aid for thinking, I find writing to be invaluable, so a
  minimal sort of "
   [:a {:href
        "http://en.wikipedia.org/wiki/Literate_programming"}
    "literate programming"] " has become a part of my workflow, at
  least for hard problems."]
  (section "The Workflow"
    "Now for the workflow proper. Given the following tools:"
    [:ol
     [:li "Emacs + Cider REPL"]
     [:li "Unit tests "
      [:a {:href "testing,-continuously.html"}
       "running continuously"]]
     [:li
      [:a {:href "https://github.com/MichaelBlume/marginalia"}
       "Marginalia"] " running continuously, via "
      [:a {:href "https://github.com/eigenhombre/continuous-testing-helper"}
       "conttest"] ","]]
    ["then my workflow, in its "
     [:a {:href "http://en.wikipedia.org/wiki/Theory_of_Forms"} "Platonic Form"]
     ", is:"]
    [:ol
     [:li [:strong "Is the path forward clear enough to write the next failing
     test?"] " If not, go to step 2. If it is, go to step 3."]
     [:li [:a {:href "https://www.youtube.com/watch?v=f84n5oFoZBc"} "Think"]
      " and " [:strong "write"] " (see below) about the problem. Go to 1."]
     [:li [:strong "Write the next failing test"]
      ". This test, when made to pass, should represent the smallest
     “natural” increase of functionality."]
     [:li [:strong "Is it clear how to make the test pass?"]
      " If not, go to step 5. If it is, write the simplest
     “production” code which makes all tests pass. Go to 6."]
     [:li [:strong "Think, write, and conduct REPL experiments"] ". Go to 4."]
     [:li [:strong "Is the code as clean, clear, and simple as possible?"]
      " If so, go to step 7. If not, refactor, continuously making sure
     tests continue to pass with every change. Go to 6."]
     [:li "Review the Marginalia docs. " [:strong "Is the intent of the code
     clear?"] " If so, go to step 1. If not, " [:strong "write some more"]
      ". Go to 7."]]
    "“Writing” in each case above refers to updating comments and
    docstrings, as described in a subsequent post on Literate
    Programming."
    "Here are the above steps as a flow chart:"
    (img "workflow")
    ["The workflow presented above is a somewhat idealized version of
    what I actually manage to pull off during any given coding
    session. It is essentially the "
     [:span {:style "color:red"} "red"] "-"
     [:span {:style "color:green"} "green"] "-"
     [:span {:style "color:blue"} "refactor"] " of traditional
    test-driven development, with the explicit addition of REPL
    experimentation (“REPL-driven development,” or RDD) and continuous
    writing of documentation (“documentation-driven development,” or
    DDD) as a way of steering the effort and clarifying the
    approach."]
    ["The utility of the REPL needs no elaboration to Clojure
    enthusiasts and I won’t belabor the point here. Furthermore, a lot
    has been written about test-first development and its advantages
    or drawbacks. At the moment, "
     [:a {:href "http://david.heinemeierhansson.com/2014/tdd-is-dead-long-live-testing.html"}
      "the practice seems to be particularly controversial"] " in the
      Rails community. I don’t want to go too deep into the pros and
      cons of TDD other than to say "
      [:a {:href
           "continuous-testing-in-python,-clojure-and-blub.html"} "once
      again"] " that the practice has
      saved my bacon so many times that I try to minimize the amount
      of code I write that doesn’t begin life as a response to a
      failing test."]
    "What I want to emphasize here is how writing and the use of the
    REPL complement TDD. These three ingredients cover all the
    bases (a)-(e), above. While I’ve been combining unit tests and the
    REPL for some time, the emphasis on writing is new to me, and I am
    excited about it. Much more than coding by itself, I find that
    writing things down and building small narratives of code and
    prose together forces me to do the thinking I need in order to
    write the best code I can."
    (subsubsection "Always Beginning Again"
      "While I don’t always follow each of the above steps to the
      letter, the harder the problem, the more closely I will tend to
      follow this plan, with one further modification: I am willing to
      wipe the slate clean and begin again if new understanding shows
      that the current path is unworkable, or leads to unneeded
      complexity."
      ["The next few posts attack specifics about "
       [:a {:href "testing,-continuously.html"} "testing"]
       " and " [:a {:href "communicating-with-humans.html"} "writing"]
       ", presenting what I personally have found most effective (so far),
      and elaborating on helpful aspects of each."])))


(defpost "Introduction to Context Managers in Python"
  {:created "2013-04-20"}
  [:em "This is the third of a "
   [:a {:href
        "http://eigenhombre.com/2013/04/18/thoughts-on-integration-testing/"}
    "series"] " of posts loosely associated with
  integration testing, mostly focused on Python."]
  "Context managers are a way of allocating and releasing some sort of
  resource exactly where you need it. The simplest example is file
  access:"
  (code "with file(\"/tmp/foo\", \"w\") as foo:
    print >> foo, \"Hello!\"")
  "This is essentially equivalent to:"
  (code "foo = file(\"/tmp/foo\", \"w\")
try:
    print >> foo, \"Hello!\"
finally:
    foo.close()")
  "Locks are another example. Given:"
  (code "import threading
lock = threading.Lock()")
  "then"
  (code "with lock:
    my_list.append(item)")
  "replaces the more verbose:"
  (code "lock.acquire()
try:
    my_list.append(item)
finally:
    lock.release()
")
  ["In each case a bit of boilerplate is eliminated, and the “context”
  of the file or the lock is acquired, used, and released cleanly."
   "Context managers are a common idiom in Lisp, where they are usually
  defined using macros (examples are " [:code "with-open"] " and "
  [:code "with-out-str"] " in
  Clojure and " [:code "with-open-file"] " and "
  [:code "with-output-to-string"] " in Common
  Lisp)."]
  ["Python, not having macros, must include context managers as part of
  the language. Since 2.5, it does so, providing an easy mechanism for
  rolling your own. Though the default, \"low level\" way to make a
  context manager is to make a class which follows the " [:a {:href
  "http://docs.python.org/2/library/stdtypes.html#typecontextmanager"}
  "context  management protocol"] ", by implementing " [:code "__enter__"] " and
  " [:code "__exit__"] " methods,
  the simplest way is using the " [:code "contextmanager"] " decorator from the
  contextlib library, and invoking " [:code "yield"] " in your context manager
  function in between the setup and teardown steps."]
  ["Here is a context manager which you could use to time "
   [:a {:href
        "http://eigenhombre.com/2013/04/19/processes-vs-threads-for-testing/"}
    "our threads-vs-processes testing"] ", discussed previously:"]
  (code "import contextlib
import time

@contextlib.contextmanager
def time_print(task_name):
    t = time.time()
    try:
        yield
    finally:
        print task_name, \"took\", time.time() - t, \"seconds.\"

with time_print(\"processes\"):
    [doproc() for _ in range(500)]

# processes took 15.236166954 seconds.

with time_print(\"threads\"):
    [dothread() for _ in range(500)]

# threads took 0.11357998848 seconds.")
  (subsection "Composition"
    "Context managers can be composed very nicely. While you can
  certainly do the following," (code "with a(x, y) as A:
    with b(z) as B:
        # Do stuff")
  "in Python 2.7 or above, the following also works:"
  (code "
with a(x, y) as A, b(z) as B:
    # Do stuff")
  ["with Python 2.6, using " [:code "contextlib.nested"] " does almost
  the same thing:"]
  (code "with contextlib.nested(a(x, y), b(z)) as (A, B):
    # Do the same stuff")
  ["the difference being that with the 2.7+ syntax, you can use the
  value yielded from the first context manager as the argument to the
  second (e.g., " [:code "with a(x, y) as A, b(A) as C:..."] ")."]
  "If multiple contexts occur together repeatedly, you can also roll
  them together into a new context manager:"
  (code "import contextlib

@contextlib.contextmanager
def c(x, y, z):
    with a(x, y) as A, b(z) as B:
        yield (A, B)

with c(x, y, z) as C:  # C == (A, B)
    # Do that same old stuff")
  "What does all this have to do with testing? I have found that for
complex integration tests where there is a lot of setup and teardown,
context managers provide a helpful pattern for making compact, simple
code, by putting the “context” (state) needed for any given test close
to where it is actually needed (and not everywhere else). Careful
isolation of state is an important aspect of functional programming."
  "More on the use of context managers in actual integration tests in
the next post."))
