(ns unmark.posts
  (:require [unmark.impl :refer :all]))


(defpost "About"
  {}
  (section ""
    "I'm a software developer living (mostly) in Chicago, Illinois, USA.  I
     currently work at OpinionLab, where I am a senior dev on their backend
     Clojure team."  "I care about making outstanding software and outstanding
     teams based on respect, communication, focus, care, and craft.  I'm also
     passionate about art and science (I went to graduate school in art and
     physics and have a doctorate in high energy particle astrophysics)."
     [[:a {:href "https://github.com/eigenhombre"} "GitHub"]
      " | "
      [:a {:href "http://stackoverflow.com/users/611752/johnj"} "StackOverflow"]
      " | "
      [:a {:href "https://twitter.com/eigenhombre"} "Twitter"]
      " | "
      [:a {:href "https://www.linkedin.com/in/eigenhombre"} "LinkedIn"]]))


(defpost "Working With Me"
  {:created "2015-11-14"
   :draft true}
  (section ""
    (epigraph "Great things are done by a series of small things brought
       together." "Vincent Van Gogh")
    (subsection "Some things about me"
      "In no particular order: I tend to acquire a reputation as the
       \"clean code guy.\" I prefer to retire technical debt sooner rather
       than later. I like to strike the right balance between thinking
       things through in advance, and coding experimentally,
       discovering the truths of the problem at hand as I work. I
       enjoy tackling easy problems, because it's satisfying to get
       things done quickly, and I like hard problems that make me
       grow, think, and do things I never imagined possible. I
       like a mix of coding solo and working closely with others. I
       like working in beautiful environments that stoke creativity
       and passion. I am opinionated, especially with regards to the
       tools and approaches I've arrived at through many years of
       growth, but I love learning new things from thoughtful people
       who are good at their craft.")
    (subsection "Practices I Like"
      (subsubsection "\"Test First\""
        ["I am not a TDD zealot, but I've had my tuchas saved
          many times by automated tests, and been burned more than a
          few by not having them. True TDD"
         (sidenote "Write the right minimum failing test first; write
          the minimum production code needed to make all tests pass;
          refactor to get to clean, DRY code; repeat.")  " is not
          without its costs, but it is one of my favorite strategies
          for ensuring good test coverage and, often, better design. I
          frequently combine REPL development and higher-level,
          end-to-end tests rather than testing slavishly at multiple
          levels of abstraction. I keep in mind the cost of tests in
          terms of code \"stiffness\" and try to write the "
          [:em "right"] " tests."])
      (subsubsection "Continuous Testing"
        "Most or all tests get run every time you save the file (at
         least), as often as once per minute or so. If your tests take
         too long for this to work, there's probably an architectural
         or performance problem that needs to be looked at.")
      (subsubsection "Promiscuous Pairing"
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
         (including us) relate the pleasures of teaching a technique to
         someone and then seeing another teammate using the same
         technique a short time later.")
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
        ["Configuration "
         [:a {:href "http://github.com/eigenhombre/emacs-config"} "here"]
         "."])
      (subsubsection "Docker"))
    (subsection "Working with Teams"
      "...")
    (subsection "Technical Strengths"
      (subsubsection "Troubleshooting and debugging"))))


(defpost "Lazy Physics"
  {:created "2015-02-12"}
  (section ""
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
performance.)"]))


(defpost "Fun with Instaparse"
  {:created "2013-11-12"}
  (section ""
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
     out."))


(defpost "Rosalind Problems in Clojure"
  {:created "2013-06-09"}
  (section ""
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
     "."]))


(defpost "Programming Languages"
  {:created "2011-12-22"}
  (section ""
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
       next month"] " which, who knows? … might help someday."])))


(defpost "Marginalia Hacks"
  {:created "2014-08-03"}
  (section ""
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
    "Happy hacking!"))


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

    "One noteworthy event happened a day and a half ago: a near-total (80%)
eclipse of the sun. I stayed up several hours later than usual to see
it. A bunch of us \"beakers\" went out when it started in order to see the
light change and to take pictures. Wind chills were about -75F. You know
it's cold when your eyelids try to freeze shut when you blink, or when
you can eat the icicles forming on your mustache."

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
  (section ""
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
    (subsubsection "Marginalia in Action:"
      (img "marg-screenshot")
      (img "emacs-screenshot"))
    (subsection "The Notebook Pattern"
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
        "interview"] ", 2008."])))
