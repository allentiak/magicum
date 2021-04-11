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
  ;; => nil

(set! *warn-on-reflection* true)
;; => true

(s/def
  :land.basic/name #{"Forest" "Island" "Mountain" "Plains" "Swamp"})
;; => :land.basic/name

(comment
  ;; fails as expected
  (s/valid? :land.basic/name "Any string outside the list fails")
  ;; => false

  ;; works as expected
  (s/valid? :land.basic/name "Island")
  ; => true
  ,)


(s/def
  :card/name :land.basic/name)
;; => :card/name

(s/def
  :object/card (s/keys :req [:card/name]))
;; => :object/card

(comment
  ;; fails as expected (name not in set)
  (s/valid? :object/card {:card/name "Fails"})
  ;; => false

  ;; works as expected
  ;; (used to fail if keys were not fully qualified)
  (s/valid? :object/card {:card/name "Island"})
  ;; => true
  ,)


(def plains {:card/name "Plains"})
;; => #'magicum.specs/plains

(comment
  ;; works as expected
  (s/valid? :object/card plains)
  ;; => true
  ,)

(def island {:card/name "Island"})
;; => #'magicum.specs/island
(def swamp {:card/name "Swamp"})
;; => #'magicum.specs/swamp
(def mountain {:card/name "Mountain"})
;; => #'magicum.specs/mountain
(def forest {:card/name "Forest"})
;; => #'magicum.specs/forest

(s/def
  :game/zone (s/coll-of :object/card))
;; => :game/zone

(s/def
  :zone/hand :game/zone)
;; => :zone/hand

(s/def
  :zone/battlefield :game/zone)
;; => :zone/battlefield

(comment
  ;; works as expected
  (s/valid? :game/zone [])
;; => true

  ;; works as expected
  (s/valid? :game/zone [plains])
;; => true

  ;; works as expected
  (s/valid? :zone/battlefield [mountain swamp plains])
  ;; => true
  ,)

(s/def
  :game/world (s/keys :req [:zone/hand :zone/battlefield]))
;; => :game/world

(comment
  ;; works as expected
  (def my-world {:zone/hand [island forest plains swamp plains mountain] :zone/battlefield []})
;; => #'magicum.specs/my-world
  (s/valid? :game/world my-world)
  ;; => true
  ,)
