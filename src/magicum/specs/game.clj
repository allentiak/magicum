(ns magicum.specs.game
  (:gen-class)
  (:require [clojure.spec.alpha :as s]
            [clojure.spec.test.alpha :as st]))

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
  ::zone (s/coll-of ::card))

(s/def
  ::hand ::zone)

(s/def
  ::battlefied ::zone)

(comment
  ;; works as expected
  (s/explain ::zone (list plains))

  ;; works as expected
  (s/explain ::battlefied (list mountain swamp plains)))


(s/def
  ::world (s/keys :req [::hand ::battlefield]))

(comment
  ;; works as expected
  (s/explain ::world {::hand (list island forest forest swamp mountain) ::battlefield (list mountain)}))


(defn play-a-card
    [{:keys [world card from to]}]
    "Given a world, return a new one in which a card from one of its zones is moved to the other zone."
    nil
    (assoc (dissoc world (first (from world))) to card))

(s/fdef play-a-card
    :args (s/cat :world ::world
                 :card-name ::card-name
                 :from ::zone
                 :to ::zone)
    :ret nil?)
    ;; :fn #(and (= (dissoc (:to (:ret %)) :card) (:to %))
    ;;           (= (assoc (:from (:ret %)) :card (:from %)))))


(def ^:private fns-with-specs
  [`play-a-card])

(defn instrument []
  (st/instrument fns-with-specs))

(defn unstrument []
  (st/unstrument fns-with-specs))
