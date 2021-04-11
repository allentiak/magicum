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
;; => nil
  ,)

(set! *warn-on-reflection* true)
;; => true

(s/fdef play-a-card
  :args (s/cat :world :game/world
               :card-idx nat-int?
               :from-zone-name keyword?
               :to-zone-name keyword?)
  :ret :game/world
  :fn #(and (= (disj (:to-zone-name (:ret %)) :card-idx) (:to-zone-name %))
            (= (conj (:from-zone-name (:ret %)) :card-idx) (:from-zone-name %))))
;; => magicum.game/play-a-card

(defn play-a-card
  "Given a world, return a new one in which the idx-th card from one of its zones is moved to the other zone."
  [world card-idx from-zone-name to-zone-name]
  (let [from (from-zone-name world)
        card (get from card-idx)
        new-from (utils/remove-first card from)
        new-to (conj (to-zone-name world) card)]
    (assoc world from-zone-name new-from to-zone-name new-to)))
;; => #'magicum.game/play-a-card

(comment
  (def plains {:card/name "Plains"})
  ;; => #'magicum.game/plains
  (def island {:card/name "Island"})
  ;; => #'magicum.game/island
  (def swamp {:card/name "Swamp"})
  ;; => #'magicum.game/swamp
  (def mountain {:card/name "Mountain"})
  ;; => #'magicum.game/mountain
  (def forest {:card/name "Forest"})
  ;; => #'magicum.game/forest
  (def my-world {:zone/hand [island forest plains plains swamp mountain] :zone/battlefield []})
  ;; => #'magicum.game/my-world
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
;; => #'magicum.game/fns-with-specs

(defn instrument []
  (st/instrument fns-with-specs))
;; => #'magicum.game/instrument

(defn unstrument []
  (st/unstrument fns-with-specs))
;; => #'magicum.game/unstrument
