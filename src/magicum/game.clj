(ns magicum.game
  "Main namespace for magicum.

  This namespace contains the business logic functions for the game.

  In addition, there is an `instrument` function that provides a simple way to instrument all of the functions, and `unstrument` to undo that."
  (:gen-class)
  (:require [clojure.spec.alpha :as s]
            [clojure.spec.test.alpha :as st]
            [magicum.specs :as specs]
            [magicum.utils :as utils]))

(comment
  (require '[clojure.spec.alpha :as s]
           '[clojure.spec.test.alpha :as st]
           '[magicum.specs :as specs]
           '[magicum.utils :as utils])
  ,)

(set! *warn-on-reflection* true)

(s/fdef play-a-card
  :args (s/cat :world :game/world
               :card-idx nat-int?
               :from-zone-name keyword?
               :to-zone-name keyword?)
  :ret :game/world
  :fn #(and (= (disj (:to-zone-name (:ret %)) :card-idx) (:to-zone-name %))
            (= (conj (:from-zone-name (:ret %)) :card-idx) (:from-zone-name %))))

(defn play-a-card
  "Given a world, return a new one in which the idx-th card from one of its zones is moved to the other zone."
  [world card-idx from-zone-name to-zone-name]
  (let [from (from-zone-name world)
        card (get from card-idx)
        new-from (utils/remove-first card from)
        new-to (conj (to-zone-name world) card)]
    (assoc world from-zone-name new-from to-zone-name new-to)))

(comment
  (def plains {:card/name "Plains"})
  (def island {:card/name "Island"})
  (def swamp {:card/name "Swamp"})
  (def mountain {:card/name "Mountain"})
  (def forest {:card/name "Forest"})
  (def my-world {:zone/hand [island forest plains swamp plains mountain] :zone/battlefield []})
  (s/exercise-fn `play-a-card)
  ;; throws exception:
  ;;   1. Unhandled java.io.FileNotFoundException
  ;;  Could not locate clojure/test/check/generators__init.class,
  ;;  clojure/test/check/generators.clj or clojure/test/check/generators.cljc on
  ;;  classpath.

  (instrument)
  ;; => [magicum.game/play-a-card]
  (play-a-card my-world 0 :zone/hand :zone/battlefield)
;; => #:zone{:hand (#:card{:name "Forest"} #:card{:name "Plains"} #:card{:name "Swamp"} #:card{:name "Plains"} #:card{:name "Mountain"}), :battlefield [#:card{:name "Island"}]}
  ,)

(def ^:private fns-with-specs
  [`play-a-card])

(defn instrument []
  (st/instrument fns-with-specs))

(defn unstrument []
  (st/unstrument fns-with-specs))
