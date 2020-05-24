(ns magicum.specs
  (:gen-class)
  (:require [clojure.spec.alpha :as s]))

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
  ^{:rule "205.3j"
    :revision "2020.04.17"}
  ::planeswalker-type #{:ajani :aminatou :angrath :arlinn :ashiok :bolas :calix :chandra :dack :daretti :davriel :domri :dovin :elspeth :estrid :freyalise :garruk :gideon :huatli :jace :jaya :karn :kasmina :kaya :kiora :koth :liliana :lukka :nahiri :narset :nissa :nixilis :oko :ral :rowan :saheeli :samut :sarkhan :serra :sorin :tamiyo :teferi :teyo :tezzeret :tibalt :ugin :venser :vivien :vraska :will :windgrace :wrenn :xenagos :yanggu :yanling})

(s/def
  ^{:rule "205.3k"
    :revision "2020.04.17"}
  ::spell-type #{:adventure :arcane :trap})

(s/def
  ^{:rule "205.3m"
    :revision "2020.04.17"}
  ::creature-type #{:advisor :aetherborn :ally :angel :antelope :ape :archer :archon :army :artificer :assassin :assembly-worker :atog :aurochs :avatar :azra :badger :barbarian :basilisk :bat :bear :beast :beeble :berserker :bird :blinkmoth :boar :bringer :brushwagg :camarid :camel :caribou :carrier :cat :centaur :cephalid :chimera :citizen :cleric :cockatrice :construct :coward :crab :crocodile :cyclops :dauthi :demigod :demon :deserter :devil :dinosaur :djinn :dragon :drake :dreadnought :drone :druid :dryad :dwarf :efreet :egg :elder :eldrazi :elemental :elephant :elf :elk :eye :faerie :ferret :fish :flagbearer :fox :frog :fungus :gargoyle :germ :giant :gnome :goat :goblin :god :golem :gorgon :graveborn :gremlin :griffin :hag :harpy :hellion :hippo :hippogriff :homarid :homunculus :horror :horse :hound :human :hydra :hyena :illusion :imp :incarnation :insect :jackal :jellyfish :juggernaut :kavu :kirin :kithkin :knight :kobold :kor :kraken :lamia :lammasu :leech :leviathan :lhurgoyf :licid :lizard :manticore :masticore :mercenary :merfolk :metathran :minion :minotaur :mole :monger :mongoose :monk :monkey :moonfolk :mouse :mutant :myr :mystic :naga :nautilus :nephilim :nightmare :nightstalker :ninja :noble :noggle :nomad :nymph :octopus :ogre :ooze :orb :orc :orgg :otter :ouphe :ox :oyster :pangolin :peasant :pegasus :pentavite :pest :phelddagrif :phoenix :pilot :pincher :pirate :plant :praetor :prism :processor :rabbit :rat :rebel :reflection :rhino :rigger :rogue :sable :salamander :samurai :sand :saproling :satyr :scarecrow :scion :scorpion :scout :sculpture :serf :serpent :servo :shade :shaman :shapeshifter :shark :sheep :siren :skeleton :slith :sliver :slug :snake :soldier :soltari :spawn :specter :spellshaper :sphinx :spider :spike :spirit :splinter :sponge :squid :squirrel :starfish :surrakar :survivor :tentacle :tetravite :thalakos :thopter :thrull :treefolk :trilobite :triskelavite :troll :turtle :unicorn :vampire :vedalken :viashino :volver :wall :warlock :warrior :weird :werewolf :whale :wizard :wolf :wolverine :wombat :worm :wraith :wurm :yeti :zombie :zubera})

(s/def
  ^{:rule "205.3n"
    :revision "2020.04.17"}
  ::planar-type #{:alara :arkhos :azgol :belenon :bolas’s-meditation-realm :dominaria :equilor :ergamon :fabacin :innistrad :iquatana :ir :kaldheim :kamigawa :karsus :kephalai :kinshala :kolbahan :kyneth :lorwyn :luvion :mercadia :mirrodin :moag :mongseng :muraganda :new-phyrexia :phyrexia :pyrulea :rabiah :rath :ravnica :regatha :segovia :serra’s-realm :shadowmoor :shandalar :ulgrotha :valla :vryn :wildfire :xerex :zendikar})

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
