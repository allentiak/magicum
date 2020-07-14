(ns magicum.specs
  (:gen-class)
  (:require
   [clojure.spec.alpha :as s]
   [clojure.spec.test.alpha :as st]))

;; metadata:
;; :rule nnn.nb - Comprehensive Rule on which the element is based on. This is based on MTG Wiki's recommendation of how to referencing the rules.
;; :version yyyy.mm.dd - date of the Comprehensive Rules document used to create this element.


;; a basic land card (see spec for 200.1)

;; irrelevant to gameplay: ::illustration, ::illustration-credit, ::expansion-symbol
;; irrelevant to basic land cards: ::c  olor-indicator
;; always constant for basic land cards: ::color-indicator (always nil), ::mana-cost (always 0), ::type (always )


(def ^:private fns-with-specs
  [`basic-land-name])

(defn instrument []
  (st/instrument fns-with-specs))

(defn unstrument []
  (st/unstrument fns-with-specs))

#_(require
   '[clojure.spec.alpha :as s]
   '[clojure.spec.test.alpha :as st])


(s/def
  ::basic-land-name #{"Forest" "Island" "Mountain" "Plains" "Swamp"})

;; works as expected
#_(s/explain ::basic-land-name "Forest")

;; fails as expected
#_(s/explain ::basic-land-name "Any string outside the list fails")

(s/def ::legal-text string?)
;; works as expected
#_(s/explain ::legal-text "For now, any string will be a valid legal-text")

(s/def
  ::basic-land-type #{::forest ::island ::mountain ::plains ::swamp})

(s/def
  ::basic-land-card (s/keys :req-un [::basic-land-name ::basic-land-type ::legal-text]))

(def forest-card
  {:basic-land-type ::forest, :basic-land-name "Forest", :legal-text "This legal text should be valid for now"})

;; works as expected
#_(s/explain ::basic-land-card forest-card)
