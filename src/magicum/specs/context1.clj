(ns magicum.specs.context1
  (:gen-class)
  (:require
   [clojure.spec.alpha :as s]
   [clojure.spec.test.alpha :as st]))

(set! *warn-on-reflection* true)

;; metadata:
;; :rule nnn.nb - Comprehensive Rule on which the element is based on. This is based on MTG Wiki's recommendation of how to referencing the rules.
;; :version yyyy.mm.dd - date of the Comprehensive Rules document used to create this element.


;; a basic land card (see spec for 200.1)

;; irrelevant to gameplay: ::illustration, ::illustration-credit, ::expansion-symbol
;; irrelevant to basic land cards: ::c  olor-indicator
;; always constant for basic land cards: ::color-indicator (always nil), ::mana-cost (always 0), ::type (always )


(def ^:private fns-with-specs
  [`move-card])

(defn instrument []
  (st/instrument fns-with-specs))

(defn unstrument []
  (st/unstrument fns-with-specs))

(comment
  (require
   '[clojure.spec.alpha :as s]
   '[clojure.spec.test.alpha :as st]))


;; Experiments with basic lands (in progress)

(s/def
  ::basic-land-name #{"Forest" "Island" "Mountain" "Plains" "Swamp"})

;; works as expected
#_(s/explain ::basic-land-name "Forest")

;; fails as expected
#_(s/explain ::basic-land-name "Any string outside the list fails")

;;(s/def ::card-legal-text string?)
;; works as expected
#_(s/explain ::card-legal-text "For now, any string will be a valid legal-text")

(s/def
  ::basic-land-type #{::forest ::island ::mountain ::plains ::swamp})

(s/def
  ::basic-land-card (s/keys :req [::basic-land-name ::basic-land-type ::card-legal-text]))

(def forest-card
  {:basic-land-type ::forest, :basic-land-name "Forest", :card-legal-text "This legal text should be valid for now"})

;; works as expected
#_(s/explain ::basic-land-card forest-card)


;; Experiments with minimalistic cards

(s/def
  ::card-legal-text string?)

(s/def
  ::card-name string?)

(s/def
  ::card (s/keys :req [::card-legal-text ::card-name]))

(s/def
  ::hand (s/coll-of ::card))

(s/def
  ::battlefield (s/coll-of ::card))

(s/def
  ::zone (s/or ::hand
               ::battlefield))

(s/def
  ::world (s/keys :req [::hand ::battlefield]))

(defn move-card
  [world card from to]
  "Given a world, return a new one in which a card from one of its zones is moved to the other zone."
  nil)
  ;; (assoc (dissoc world (first (from world))) to card))

(s/fdef move-card
  :args (s/cat :world ::world
               :card-name ::card-name
               :from ::zone
               :to ::zone)
  :ret nil?)
  ;; :fn #(and (= (dissoc (:to (:ret %)) :card) (:to %))
  ;;           (= (assoc (:from (:ret %)) :card (:from %)))))

(st/instrument `move-card)
