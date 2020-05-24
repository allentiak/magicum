(ns magicum.specs
  (:gen-class)
  (:require [clojure.spec.alpha :as s]
            [clojure.set :as set]))

;; metadata:
;; :rule nnn.nb - comprehensive rule on which the element is based on. This is based on MTG Wiki's recommendation of how to referencing the rules.
;; :revison yyyy.mm.dd - version of the CRs used to create this element.

(s/def
  ^{:rule "106.1a"
    :revision "2020.04.17"}
  ::mana-color #{:white :blue :black :red :green})

(s/def
  ^{:rule "106.1b"
    :revision "2020.04.17"}
  ::mana-type #{:white :blue :black :red :green :colorless})


(s/def
  ^{:rule "107.4"
    :revision "2020.04.17"}
  ::primary-mana-symbol {:w :white, :u :blue, :b :black, :r :red, :g :green})

(s/def
  ^{:rule "107.4"
    :revision "2020.04.17"}
  ::variable-mana-symbol #{:x})

(s/def
  ^{:rule "107.4"
    :revision "2020.04.17"}
  ::generic-mana-symbol (s/or ::variable-mana-symbol nat-int?))

(s/def
  ^{:rule "107.4"
    :revision "2020.04.17"}
  ::colorless-mana-symbol {:c :colorless})

(s/def
  ^{:rule "107.4"
    :revision "2020.04.17"}
  ::hybrid-mana-symbol #{:wu :wb :ub :ur :br :bg :rg :rw :gw :gu})

(s/def
  ^{:rule "107.4"
    :revision "2020.04.17"}
  ::monocolored-hybrid-mana-symbol #{:2w :2u :2b :2r :2g})

(s/def
  ^{:rule "107.4"
    :revision "2020.04.17"}
  ::colored-phyrexian-mana-symbol #{:wp :up :bp :rp :gp})

(s/def
  ^{:rule "107.4"
    :revision "2020.04.17"}
  ::generic-phyrexian-mana-symbol #{:p})

(s/def
  ^{:rule "107.4"
    :revision "2020.04.17"}
  ::phyrexian-mana-symbol (conj ::generic-phyrexian-mana-symbol ::colored-phyrexian-mana-symbol))

(s/def
   ^{:rule "107.4"
     :revision "2020.04.17"}
   ::snow-mana-symbol #{:s})

(s/def
  ^{:rule "107.4"
    :revision "2020.04.17"}
  ::mana-symbol (conj ::primary-mana-symbol ::generic-mana-symbol ::colorless-mana-symbol ::hybrid-mana-symbol ::monocolored-hybrid-mana-symbol ::phyrexian-mana-symbol ::snow-mana-symbol))

(s/def
  ^{:rule "107.5"
    :revision "2020.04.17"}
  ::tap-symbol #{:t})

(s/def
  ^{:rule "107.6"
    :revision "2020.04.17"}
  ::untap-symbol #{:q})

(s/def
  ^{:rule "107.11"
    :revision "2020.04.17"}
  ::planeswalker-symbol #{:pw})

(s/def
  ^{:rule "107.12"
    :revision "2020.04.17"}
  ::chaos-symbol #{:chaos})

(s/def
  ^{:rule "205.2a"
    :revision "2020.04.17"}
  ::type #{:artifact :conspiracy :creature :enchantment :instant :land :phenomenon :plane :planeswalker :scheme :sorcery :tribal :vanguard})

;; (s/exercise ::type)

(s/def
  ^{:rule "205.3g"
    :revision "2020.04.17"}
  ::artifact-type #{:clue :contraption :equipment :food :fortification :gold :treasure :vehicle})

(s/def
  ^{:rule: "205.3h"
    :revision "2020.04.17"}
  ::enchantment-type #{:aura :cartouche :curse :saga :shrine})

(s/def
  ^{:rule "205.3i"
    :revision "2020.04.17"}
  ::land-type #{:desert :forest :gate :island :lair :locus :mine :mountain :plains :power-plant :swamp :tower :urza's})

(s/def
  ^{:rule "205.3i"
    :revision "2020.04.17"}
  ::basic-land-type #{:forest :island :mountain :plains :swamp})

(s/def
  ^{:rule "205.3k"
    :revision "2020.04.17"}
  ::spell-type #{:adventure :arcane :trap})

(s/def
  ^{:rule "205.4a"
    :revision "2020.04.17"}
  ::supertype #{:basic :legendary :ongoing :snow :world})


****************** OLD CODE HERE **************

(s/def ::artist (s/and string? seq))
(s/def ::set (s/and string? seq))
(s/def ::set-number pos-int?)
(s/def ::metadata (s/keys :req-un [::set ::set-number ::artist]))
;; (s/exercise ::metadata)
(s/def ::name (s/and string? seq))
(s/def ::types (s/and set? (s/+ ::type)))
(s/exercise ::types)
(s/def ::sub-type ::type)
(s/def ::rules string?)
(s/def ::legendary? boolean?)
(s/def ::world? boolean?)

(s/def ::base-card (s/keys :req-un [::name ::types ::metadata ::rules]
                           :opt-un [::sub-type ::legendary? ::world?]))

(->> (s/exercise ::base-card) (take 5))

(s/def ::amount pos-int?)
(s/def ::color #{:white :blue :black :red :green})
(s/def ::colors (s/+ ::color))

(s/def ::mana (s/keys :req-un [::amount]
                      :opt-un [::colors]))
(s/def ::manas (s/+ ::mana))
(s/def ::cost (s/* ::manas))

(s/def ::spell (s/and ::base-card (s/keys :req-un [::cost])))

(s/def ::power int?)
(s/def ::toughness int?)

(s/def ::creature (s/and ::spell (s/keys :req-un [::power ::toughness])))

(s/def ::loyalty int?)

(s/def ::planeswalker (s/and ::spell (s/keys :req-un [::loyalty])))

(defmulti card-spec :types)

(defmethod card-spec #{:land} [_] ::base-card)
(defmethod card-spec #{:creature} [_] ::creature)
(defmethod card-spec #{:artifact} [_] ::spell)
(defmethod card-spec #{:enchantment} [_] ::spell)
(defmethod card-spec #{:sorcery} [_] ::spell)
(defmethod card-spec #{:instant} [_] ::spell)
(defmethod card-spec #{:planeswalker} [_] ::planeswalker)
(defmethod card-spec #{:artifact :creature} [_] ::creature)
(defmethod card-spec #{:enchantment :creature} [_] ::creature)
(defmethod card-spec #{:artifact :enchantment} [_] ::spell)

(s/def ::card (s/multi-spec card-spec :types))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))
