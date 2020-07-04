(ns magicum.basic-land-specs
  (:gen-class)
  (:require
   [clojure.spec.alpha :as s]
   [clojure.spec.test.alpha :as st]))

;; metadata:
;; :rule nnn.nb - Comprehensive Rule on which the element is based on. This is based on MTG Wiki's recommendation of how to referencing the rules.
;; :version yyyy.mm.dd - date of the Comprehensive Rules document used to create this element.


;; a basic land card (see spec for 200.1)

;; irrelevant to game playing: ::illustration, ::illustration-credit, ::expansion-symbol
;; irrelevant to basic land cards: ::c  olor-indicator
;; always constant for basic land cards: ::color-indicator (always nil), ::mana-cost (always 0), ::type (always )


(def ^:private fns-with-specs
  [`basic-land-name])

(defn instrument []
  (st/instrument fns-with-specs))

(defn unstrument []
  (st/unstrument fns-with-specs))


(s/def
  ::basic-land-card (s/keys :req-un [::basic-land-name ::basic-land-type ::legal-text]))

(s/def
  ::legal-text string?)

(s/def ::basic-land-name
 {::forest "Forest", ::island "Island", ::mountain "Mountain", ::plains "Plains", ::swamp "Swamp"})

(s/fdef
    basic-land-name
  :args (s/cat ::basic-land-type)
  :ret ::basic-land-name)

(s/def
  ^{:rule "205.3i"
    :version "2020.06.01"}
  ::basic-land-type #{::forest ::island ::mountain ::plains ::swamp})
