(ns unmark.posts
  (:require [unmark.impl :refer :all]))


(defpost "About"
  "about"
  (postbody
   (section "About me, and this site"
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
      [:a {:href "https://www.linkedin.com/in/eigenhombre"} "LinkedIn"]])))


(defpost "Working With Me"
  "working-w-me"
  {:created "2015-11-14"}
  (postbody
   (section "Working with Me"
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
          Pairing and Beginner's Mind (Google for PDF)"])
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
       (subsubsection "Troubleshooting and debugging")))))


(defpost "Lazy Physics"
  "lazy-physics"
  {:created "2015-02-12"}
  (postbody
   (section "Lazy Physics"
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
performance.)"])))


(defpost "Fun with Instaparse"
  "fun-w-instaparse"
  {:created "2013-11-12"}
  (postbody
   (section "Fun with Instaparse"
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
     out.")))


(defpost "Rosalind Problems in Clojure"
  "rosalind-problems-in-clojure"
  {:created "2013-06-09"}
  (postbody
   (section "Rosalind Problems in Clojure"
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
      "."])))
