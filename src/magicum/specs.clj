(ns magicum.specs
  (:gen-class)
  (:require [clojure.spec.alpha :as s]
            [clojure.set :as set]))

(def ^:private non-empty-string? (s/and string? not-empty))

(s/def ::artist non-empty-string?)
(s/def ::set non-empty-string?)
(s/def ::set-number pos-int?)

(s/explain ::artist "trsau")
(s/explain ::set "rato")
(s/explain ::set-number 1)

(s/def ::metadata (s/keys :req-un [::set ::set-number ::artist]))

(s/def ::name string?)

(s/def ::type #{:land :creature :artifact :enchantment :sorcery :instant :planeswalker})
(s/def ::types (s/and set? (s/+ ::type)))
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
