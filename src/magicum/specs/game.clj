(ns magicum.specs.game
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
  ::basic-land-name #{"Forest" "Island" "Mountain" "Plains" "Swamp"})

(comment
  ;; fails as expected
  (s/explain ::basic-land-name "Any string outside the list fails")

  ;; works as expected
  (s/explain ::basic-land-name "Island"))

(s/def
  ::card-name ::basic-land-name)

(s/def
  ::card (s/keys :req [::card-name]))

(comment
  ;; fails as expected (name not in set)
  (s/explain ::card {::card-name "Fails"})

  ;; fails as expected (key is not fully qualified)
  (s/explain ::card {:card-name "Island"})

  ;; works as expected
  ;; (will fail if keys are not fully qualified)
  (s/explain ::card {::card-name "Island"}))

(def plains {::card-name "Plains"})

(comment
  ;; works as expected
  (s/explain ::card plains))

(def island {::card-name "Island"})
(def swamp {::card-name "Swamp"})
(def mountain {::card-name "Mountain"})
(def forest {::card-name "Forest"})

(s/def
  ::zone (s/coll-of (s/nilable ::card)))

(s/def
  ::hand ::zone)

(s/def
  ::battlefield ::zone)

(comment
  ;; works as expected
  (s/explain ::zone [])

  ;; works as expected
  (s/explain ::zone [plains])

  ;; works as expected
  (s/explain ::battlefield [mountain swamp plains]))

(s/def
  ::world (s/keys :req [::hand ::battlefield]))

(comment
  ;; works as expected
  (def my-world {::hand [island forest plains swamp plains mountain] ::battlefield []})
  (s/explain ::world my-world))

(defn play-a-card
  "Given a world, return a new one in which the idx-th card from one of its zones is moved to the other zone."
  [world card-idx from-zone-name to-zone-name]
  (let [from (from-zone-name world)
        card (get from card-idx)
        new-from (utils/remove-first card from)
        new-to (conj (to-zone-name world) card)]
    (assoc world from-zone-name new-from to-zone-name new-to)))

(s/fdef play-a-card
  :args (s/cat :world ::world
               :card-idx nat-int?
               :from-zone-name keyword?
               :to-zone-name keyword?)
  :ret ::world
  :fn #(and (= (disj (:to-zone-name (:ret %)) :card-idx) (:to-zone-name %))
            (= (conj (:from-zone-name (:ret %)) :card-idx) (:from-zone-name %))))

(comment
  (s/exercise-fn `play-a-card)
  (play-a-card my-world 0 ::hand ::battlefield))
  ;; => #:magicum.specs.game{:hand
;; (#:magicum.specs.game{:card-name "Forest"}
;; #:magicum.specs.game{:card-name "Plains"}
;; #:magicum.specs.game{:card-name "Swamp"}
;; #:magicum.specs.game{:card-name "Plains"}
;; #:magicum.specs.game{:card-name "Mountain"},
;; :battlefield [#:magicum.specs.game{:card-name "Island"}]


(def ^:private fns-with-specs
  [`play-a-card])

(defn instrument []
  (st/instrument fns-with-specs))

(defn unstrument []
  (st/unstrument fns-with-specs))
