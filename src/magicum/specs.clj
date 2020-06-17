(ns magicum.specs
  (:gen-class)
  (:require [clojure.spec.alpha :as s]))

;; metadata:
;; :rule nnn.nb - Comprehensive Rule on which the element is based on. This is based on MTG Wiki's recommendation of how to referencing the rules.
;; :version yyyy.mm.dd - date of the Comprehensive Rules document used to create this element.

(s/def
  ^{:rule "106.1a"
    :version "2020.06.01"}
  ::mana-color #{:white :blue :black :red :green})

(s/def
  ^{:rule "106.1b"
    :version "2020.06.01"}
  ::mana-type #{:white :blue :black :red :green :colorless})


(s/def
  ^{:rule "107.4"
    :version "2020.06.01"}
  ::primary-mana-symbol {:w :white, :u :blue, :b :black, :r :red, :g :green})

(s/def
  ^{:rule "107.4"
    :version "2020.06.01"}
  ::variable-mana-symbol #{:x})

(s/def
  ^{:rule "107.4"
    :version "2020.06.01"}
  ::generic-mana-symbol (s/or ::variable-mana-symbol nat-int?))

(s/def
  ^{:rule "107.4"
    :version "2020.06.01"}
  ::colorless-mana-symbol {:c :colorless})

(s/def
  ^{:rule "107.4"
    :version "2020.06.01"}
  ::hybrid-mana-symbol #{:wu :wb :ub :ur :br :bg :rg :rw :gw :gu})

(s/def
  ^{:rule "107.4"
    :version "2020.06.01"}
  ::monocolored-hybrid-mana-symbol #{:2w :2u :2b :2r :2g})

(s/def
  ^{:rule "107.4"
    :version "2020.06.01"}
  ::colored-phyrexian-mana-symbol #{:wp :up :bp :rp :gp})

(s/def
  ^{:rule "107.4"
    :version "2020.06.01"}
  ::generic-phyrexian-mana-symbol #{:p})

(s/def
  ^{:rule "107.4"
    :version "2020.06.01"}
  ::phyrexian-mana-symbol (conj ::generic-phyrexian-mana-symbol ::colored-phyrexian-mana-symbol))

(s/def
   ^{:rule "107.4"
     :version "2020.06.01"}
   ::snow-mana-symbol #{:s})

(s/def
  ^{:rule "107.4"
    :version "2020.06.01"}
  ::mana-symbol (conj ::primary-mana-symbol ::generic-mana-symbol ::colorless-mana-symbol ::hybrid-mana-symbol ::monocolored-hybrid-mana-symbol ::phyrexian-mana-symbol ::snow-mana-symbol))

(s/def
  ^{:rule "107.5"
    :version "2020.06.01"}
  ::tap-symbol #{:t})

(s/def
  ^{:rule "107.6"
    :version "2020.06.01"}
  ::untap-symbol #{:q})

;; TODO: add level-symbol (107.8, 107.8a, 107.8b)

(s/def
  ^{:rule "107.11"
    :version "2020.06.01"}
  ::planeswalker-symbol #{:pw})

(s/def
  ^{:rule "107.12"
    :version "2020.06.01"}
  ::chaos-symbol #{:chaos})

(s/def
  ^{:rule "107.13"
    :version "2020.06.01"}
  ::energy-symbol #{:e})

;; TODO: add saga-symbol (107.15, 107.15a, 107.15b)

(s/def
  ^{:rule "109.1"
    :version "2020.06.01"}
  ::object #{:ability-on-the-stack :card :card-copy :token :spell :permanent :emblem})

(s/def
  ^{:rule "109.3"
    :version "2020.06.01"}
  ::object-characteristic #{:name :mana-cost :color :color-indicator :card-type :subtype :supertype :rules-text :abilities :power :toughness :loyalty :hand-modifier :life-modifier})

;; TODO: add "109.4(a-e)"

;; TODO: add "110.1"

(s/def
  ^{:rule "110.4"
    :version "2020.06.01"}
  ::permanent-type #{:artifact :creature :enchantment :land :planeswalker})

;; TODO: add permanent-card ("110.4a")

;; FIXME: all this options are binary
(s/def
  ^{:rule "110.5"
    :version "2020.06.01"}
  ::permanent-status #{:tapped :flipped :face-up :phased-in})

;; TODO: review "111.10a-c" to see whether predefined tokens should be added here

;; TODO: review "113.1-2" to see whether (and how) abilities should be added here

;; FIXME: 113.4 mana abilities are also activated, triggered, or both
(s/def
  ^{:rule "113.3"
    :version "2020.06.01"}
  ::ability-type #{:spell :activated :triggered :static :mana})

;; TODO: add "113.5" loyalty-ability

;; TODO: check how "114" emblems (for commander) could be added here

;; TODO: check whether "115" targets should be added here

;; FIXME: verify special action types. they should be eight, but there are only six here
(s/def
  ^{:rule "116.2"
    :version "2020.06.01"}
  ::special-action-type #{:play-a-land :turn-a-face-down-creature-face-up :exile-card-with-suspend-in-hand :put-companion-in-hand :rolling-the-planar-die :turn-a-face-down-conspiracy-card-in-the-command-zone-face-up})

;; TODO: 117: timing and priority: a player can be either :active or :inactive, :with-priority or :without-priority

;; FIXME: 118.9: cost: take into account original and alternative costs
(s/def
  ^{:rule "118"
    :version "2020.06.01"}
  ::cost-type #{:mandatory :optional})

;; TODO: 119: life: consider whether there actualyy is something there than can be specable (I think not)

;; TODO: 120.4: damage: assignation steps are there

;; TODO: 121: draw relates to dynamic model - not specable

;; 122: counters
(s/def
  ^{:rule "122.1b"
    :version "2020.06.01"}
  ::keyword-counter #{:flying :first-strike :double-strike :deathtouch :haste :hexproof :indestructible :lifelink :menace :reach :trample :vigilance})

(s/def
  ^{:rule "122"
    :version "2020.06.01"}
  ::counter-type #{:plus-or-minus-counter :keyword-counter :loyalty-counter :poison-counter})



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




(s/def
  ^{:rule "205.2a"
    :version "2020.06.01"}
  ::type #{:artifact :conspiracy :creature :enchantment :instant :land :phenomenon :plane :planeswalker :scheme :sorcery :tribal :vanguard})

;; (s/exercise ::type)

(s/def
  ^{:rule "205.3g"
    :version "2020.06.01"}
  ::artifact-type #{:clue :contraption :equipment :food :fortification :gold :treasure :vehicle})

(s/def
  ^{:rule: "205.3h"
    :version "2020.06.01"}
  ::enchantment-type #{:aura :cartouche :curse :saga :shrine})

(s/def
  ^{:rule "205.3i"
    :version "2020.06.01"}
  ::land-type #{:desert :forest :gate :island :lair :locus :mine :mountain :plains :power-plant :swamp :tower :urza's})

(s/def
  ^{:rule "205.3i"
    :version "2020.06.01"}
  ::basic-land-type #{:forest :island :mountain :plains :swamp})

(s/def
  ^{:rule "205.3j"
    :version "2020.06.01"}
  ::planeswalker-type #{:ajani :aminatou :angrath :arlinn :ashiok :bolas :calix :chandra :dack :daretti :davriel :domri :dovin :elspeth :estrid :freyalise :garruk :gideon :huatli :jace :jaya :karn :kasmina :kaya :kiora :koth :liliana :lukka :nahiri :narset :nissa :nixilis :oko :ral :rowan :saheeli :samut :sarkhan :serra :sorin :tamiyo :teferi :teyo :tezzeret :tibalt :ugin :venser :vivien :vraska :will :windgrace :wrenn :xenagos :yanggu :yanling})

(s/def
  ^{:rule "205.3k"
    :version "2020.06.01"}
  ::spell-type #{:adventure :arcane :trap})

(s/def
  ^{:rule "205.3m"
    :version "2020.06.01"}
  ::creature-type #{:advisor :aetherborn :ally :angel :antelope :ape :archer :archon :army :artificer :assassin :assembly-worker :atog :aurochs :avatar :azra :badger :barbarian :basilisk :bat :bear :beast :beeble :berserker :bird :blinkmoth :boar :bringer :brushwagg :camarid :camel :caribou :carrier :cat :centaur :cephalid :chimera :citizen :cleric :cockatrice :construct :coward :crab :crocodile :cyclops :dauthi :demigod :demon :deserter :devil :dinosaur :djinn :dragon :drake :dreadnought :drone :druid :dryad :dwarf :efreet :egg :elder :eldrazi :elemental :elephant :elf :elk :eye :faerie :ferret :fish :flagbearer :fox :frog :fungus :gargoyle :germ :giant :gnome :goat :goblin :god :golem :gorgon :graveborn :gremlin :griffin :hag :harpy :hellion :hippo :hippogriff :homarid :homunculus :horror :horse :hound :human :hydra :hyena :illusion :imp :incarnation :insect :jackal :jellyfish :juggernaut :kavu :kirin :kithkin :knight :kobold :kor :kraken :lamia :lammasu :leech :leviathan :lhurgoyf :licid :lizard :manticore :masticore :mercenary :merfolk :metathran :minion :minotaur :mole :monger :mongoose :monk :monkey :moonfolk :mouse :mutant :myr :mystic :naga :nautilus :nephilim :nightmare :nightstalker :ninja :noble :noggle :nomad :nymph :octopus :ogre :ooze :orb :orc :orgg :otter :ouphe :ox :oyster :pangolin :peasant :pegasus :pentavite :pest :phelddagrif :phoenix :pilot :pincher :pirate :plant :praetor :prism :processor :rabbit :rat :rebel :reflection :rhino :rigger :rogue :sable :salamander :samurai :sand :saproling :satyr :scarecrow :scion :scorpion :scout :sculpture :serf :serpent :servo :shade :shaman :shapeshifter :shark :sheep :siren :skeleton :slith :sliver :slug :snake :soldier :soltari :spawn :specter :spellshaper :sphinx :spider :spike :spirit :splinter :sponge :squid :squirrel :starfish :surrakar :survivor :tentacle :tetravite :thalakos :thopter :thrull :treefolk :trilobite :triskelavite :troll :turtle :unicorn :vampire :vedalken :viashino :volver :wall :warlock :warrior :weird :werewolf :whale :wizard :wolf :wolverine :wombat :worm :wraith :wurm :yeti :zombie :zubera})

(s/def
  ^{:rule "205.3n"
    :version "2020.06.01"}
  ::planar-type #{:alara :arkhos :azgol :belenon :bolas’s-meditation-realm :dominaria :equilor :ergamon :fabacin :innistrad :iquatana :ir :kaldheim :kamigawa :karsus :kephalai :kinshala :kolbahan :kyneth :lorwyn :luvion :mercadia :mirrodin :moag :mongseng :muraganda :new-phyrexia :phyrexia :pyrulea :rabiah :rath :ravnica :regatha :segovia :serra’s-realm :shadowmoor :shandalar :ulgrotha :valla :vryn :wildfire :xerex :zendikar})

(s/def
  ^{:rule "205.4a"
    :version "2020.06.01"}
  ::supertype #{:basic :legendary :ongoing :snow :world})
