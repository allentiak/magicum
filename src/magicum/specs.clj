(ns magicum.specs
  "Specs for the core API of magicum.

  For now, all of the functions have specs here.

  In addition, there is an `instrument` function that provides a simple way to instrument all of the functions, and `unstrument` to undo that."
  (:gen-class)
  (:require [clojure.spec.alpha :as s]
            [clojure.spec.test.alpha :as st]
            [magicum.utils :as utils]))

(comment
  (require '[clojure.spec.alpha :as s]
           '[clojure.spec.test.alpha :as st]
           '[magicum.utils :as utils]))

(set! *warn-on-reflection* true)

(s/def
  :land.basic/name #{"Forest" "Island" "Mountain" "Plains" "Swamp"})

(comment
  ;; fails as expected
  (s/explain :land.basic/name "Any string outside the list fails")

  ;; works as expected
  (s/explain :land.basic/name "Island"))

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
  (s/explain :object/card {:card/name "Island"}))

(def plains {:card/name "Plains"})

(comment
  ;; works as expected
  (s/explain :object/card plains))

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
  (s/explain :zone/battlefield [mountain swamp plains]))

(s/def
  :game/world (s/keys :req [:zone/hand :zone/battlefield]))

(comment
  ;; works as expected
  (def my-world {:zone/hand [island forest plains swamp plains mountain] :zone/battlefield []})
  (s/explain :game/world my-world))

(s/fdef play-a-card
  :args (s/cat :world :game/world
               :card-idx nat-int?
               :from-zone-name keyword?
               :to-zone-name keyword?)
  :ret :game/world
  :fn #(and (= (disj (:to-zone-name (:ret %)) :card-idx) (:to-zone-name %))
            (= (conj (:from-zone-name (:ret %)) :card-idx) (:from-zone-name %))))

(comment
  (s/exercise-fn `play-a-card)
  (play-a-card my-world 0 :zone/hand :zone/battlefield))
  ;; => #:magicum.specs.game{:hand
;; (#:magicum.specs.game{:card-name "Forest"}
;; #:magicum.specs.game{:card-name "Plains"}
;; #:magicum.specs.game{:card-name "Swamp"}
;; #:magicum.specs.game{:card-name "Plains"}
;; #:magicum.specs.game{:card-name "Mountain"},
;; :battlefield [#:magicum.specs.game{:card-name "Island"}]


(defn play-a-card
  "Given a world, return a new one in which the idx-th card from one of its zones is moved to the other zone."
  [world card-idx from-zone-name to-zone-name]
  (let [from (from-zone-name world)
        card (get from card-idx)
        new-from (utils/remove-first card from)
        new-to (conj (to-zone-name world) card)]
    (assoc world from-zone-name new-from to-zone-name new-to)))

(def ^:private fns-with-specs
  [`play-a-card])

(defn instrument []
  (st/instrument fns-with-specs))

(defn unstrument []
  (st/unstrument fns-with-specs))
