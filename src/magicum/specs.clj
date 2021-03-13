(ns magicum.specs
  "Specs for the core API of magicum.

  For now, all of the functions have specs here."

  (:gen-class)
  (:require [clojure.spec.alpha :as s]
            [clojure.spec.test.alpha :as st]
            [magicum.utils :as utils]))

(comment
  (require '[clojure.spec.alpha :as s]
           '[clojure.spec.test.alpha :as st]
           '[magicum.utils :as utils])
  ,)

(set! *warn-on-reflection* true)

(s/def
  :land.basic/name #{"Forest" "Island" "Mountain" "Plains" "Swamp"})

(comment
  ;; fails as expected
  (s/valid? :land.basic/name "Any string outside the list fails")

  ;; works as expected
  (s/valid? :land.basic/name "Island"))

(s/def
  :card/name :land.basic/name)

(s/def
  :object/card (s/keys :req [:card/name]))

(comment
  ;; fails as expected (name not in set)
  (s/valid? :object/card {:card/name "Fails"})

  ;; works as expected
  ;; (used to fail if keys were not fully qualified)
  (s/valid? :object/card {:card/name "Island"}))

(def plains {:card/name "Plains"})

(comment
  ;; works as expected
  (s/valid? :object/card plains)
  ,)

(def island {:card/name "Island"})
(def swamp {:card/name "Swamp"})
(def mountain {:card/name "Mountain"})
(def forest {:card/name "Forest"})

(s/def
  :game/zone (s/coll-of :object/card))

(s/def
  :zone/hand :game/zone)

(s/def
  :zone/battlefield :game/zone)

(comment
  ;; works as expected
  (s/valid? :game/zone [])

  ;; works as expected
  (s/valid? :game/zone [plains])

  ;; works as expected
  (s/valid? :zone/battlefield [mountain swamp plains]))

(s/def
  :game/world (s/keys :req [:zone/hand :zone/battlefield]))

(comment
  ;; works as expected
  (def my-world {:zone/hand [island forest plains swamp plains mountain] :zone/battlefield []})
  (s/valid? :game/world my-world))
