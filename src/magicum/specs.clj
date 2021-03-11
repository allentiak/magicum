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
  (s/explain :land.basic/name "Any string outside the list fails")

  ;; works as expected
  (s/explain :land.basic/name "Island")
  ,)

(s/def
  :card/name :land.basic/name)

(s/def
  :object/card (s/keys :req [:card/name]))

(comment
  ;; fails as expected (name not in set)
  (s/explain :object/card {:card/name "Fails"})

  ;; fails as expected (key is not fully qualified)
  (s/explain :object/card {:card/name "Island"})

  ;; works as expected
  ;; (will fail if keys are not fully qualified)
  (s/explain :object/card {:card/name "Island"})
  ,)

(def plains {:card/name "Plains"})

(comment
  ;; works as expected
  (s/explain :object/card plains)
  ,)

(def island {:card/name "Island"})
(def swamp {:card/name "Swamp"})
(def mountain {:card/name "Mountain"})
(def forest {:card/name "Forest"})

(s/def
  :game/zone (s/coll-of (s/nilable :object/card)))

(s/def
  :zone/hand :game/zone)

(s/def
  :zone/battlefield :game/zone)

(comment
  ;; works as expected
  (s/explain :game/zone [])

  ;; works as expected
  (s/explain :game/zone [plains])

  ;; works as expected
  (s/explain :zone/battlefield [mountain swamp plains])
  ,)

(s/def
  :game/world (s/keys :req [:zone/hand :zone/battlefield]))

(comment
  ;; works as expected
  (def my-world {:zone/hand [island forest plains swamp plains mountain] :zone/battlefield []})
  (s/explain :game/world my-world)
  ,)
