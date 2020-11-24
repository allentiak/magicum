(ns magicum.game
  "Specs for the core API of magicum.

  For now, all of the functions have specs here.

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
           '[magicum.utils :as utils]))

(set! *warn-on-reflection* true)


(defn play-a-card
  "Given a world, return a new one in which the idx-th card from one of its zones is moved to the other zone."
  [world card-idx from-zone-name to-zone-name]
  (let [from (from-zone-name world)
        card (get from card-idx)
        new-from (utils/remove-first card from)
        new-to (conj (to-zone-name world) card)]
    (assoc world from-zone-name new-from to-zone-name new-to)))


(comment
  (s/exercise-fn `play-a-card)
  (play-a-card my-world 0 :zone/hand :zone/battlefield))


(def ^:private fns-with-specs
  [`play-a-card])

(defn instrument []
  (st/instrument fns-with-specs))

(defn unstrument []
  (st/unstrument fns-with-specs))
