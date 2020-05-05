# About the original specs' code

Part of it was written by Robert Stuttaford; another part, by Adam Frey.
Both gave me express permission to use it as I see fit.

## About Robert Stuttaford's code

### Origin

_(Trimmed for brevity, redacted for privacy. Some context added.)_

>> On Wed, Feb 26, 2020 at 16:58, Leandro Doctors <ldoctors@gmail.com> wrote:
>>>
>>> Dear Robert,
>>>
>>> As a way to get actual Clojure experience, I am starting to work on a
>>> Clojure implementation of MTG. I saw your MTG code [1], and would like
>>> to use it.
>>>
>>> [1] https://github.com/robert-stuttaford/mtg.git
>>>
>>> However, it has no license...
>>>
>>> Would you relicense it as either as ASL2+ or AGPL3+ (with Clojure
>>> exception) so I can use it?
>>>
>>> Thank you in advance,
>>> Leandro Doctors


> On Wed, 26 Feb 2020 at 17:08, Robert Stuttaford <robert@cognician.com> wrote:
>>
>> You can just use it :)
>>
>> --
>>
>> Best,
>>
>> Robert Stuttaford   Chief Technical Officer
>> Office: [redacted] Mobile: [redacted] Linked In: Robert Stuttaford Twitter: @RobStuttaford
>>
>> INTELLECTION • LEARNER • RELATOR • CONSISTENCY • HARMONY


On Wed, 26 Feb 2020 at 17:11, Leandro Doctors <ldoctors@gmail.com> wrote:
>
> Thanks!
>
> I will consider this to be a relicensing of your code to WTFPL[1] :-)
>
> [1] Do What the Fuck You Want To Public License: https://en.wikipedia.org/wiki/WTFPL


### Code

_See https://github.com/robert-stuttaford/mtg/tree/c69140a0252969328fc0c6838034f3e03b1a7185/src/mtg.clj_

```clojure
(ns mtg
  (:require [clojure.spec.gen :as gen]
            [clojure.spec :as s]))

(s/def ::pos-int (s/and int? (complement neg?)))

(s/def ::set string?)
(s/def ::set-number ::pos-int)
(s/def ::artist string?)

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

#_ (->> (s/exercise ::base-card) (take 5))

(s/def ::amount ::pos-int)
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
```


## About Adam Frey's code

### Origin

@_**Leandro Doctors|279114** [said](https://clojurians.zulipchat.com/#narrow/stream/151763-beginners/topic/spec/near/193732939):
```quote
Hi, everybody,

I finally found the time to start implementing my long-time excuse for
playing with Clojure.spec, and started a project[1] to model the
domain of  "Magic: The Gathering". (I guess you know the game?)

```

Hi, I saw your message about using clojure.spec to model Magic: The Gathering and I remembered that I took a crack at starting this a while ago. I don't have any plans to up date this code but you might find it useful: https://github.com/AdamFrey/cube/blob/master/src/mtg/card.clj


@_**Adam Frey|138569** [said](https://clojurians.zulipchat.com/#narrow/stream/151763-beginners/topic/spec/near/195464635):
````quote
@_**Leandro Doctors|279114** [said](https://clojurians.zulipchat.com/#narrow/stream/151763-beginners/topic/spec/near/195445993):
```quote
@**Adam Frey**  Thanks for the link! Any particular (licensing) condition? (I'm using AGPL3+ with a Clojure exception for my project...)
```
No license restrictions on my part. Free to use. I should update the repo to add a license file if I have the time
````

Cool! Thank you so much, @**Adam Frey** !

### Code

_See https://github.com/AdamFrey/cube/tree/d40d7116328c6ca21fde7b87d3e0cccd1298c512_

```clojure
(ns mtg.card
  (:require [clojure.spec.alpha :as s]
            [clojure.spec.gen.alpha :as gen]))

(def ^:private non-empty-string? (s/and string? not-empty))

(s/def ::name non-empty-string?)

(s/def :mtg/color
  #{:mtg.color/black
    :mtg.color/red
    :mtg.color/green
    :mtg.color/white
    :mtg.color/blue
    :mtg.color/colorless})

(s/def ::color-identity (s/coll-of :mtg/color :kind set?))
(s/def ::colors (s/coll-of :mtg/color :kind set?))

(s/def ::converted-mana-cost
  (s/spec nat-int? :gen #(s/gen (s/int-in 0 12))))

(s/def ::layout
  #{:mtg.card.layout/aftermath
    :mtg.card.layout/augment
    :mtg.card.layout/emblem
    :mtg.card.layout/flip
    :mtg.card.layout/host
    :mtg.card.layout/leveler
    :mtg.card.layout/meld
    :mtg.card.layout/normal
    :mtg.card.layout/planar
    :mtg.card.layout/saga
    :mtg.card.layout/scheme
    :mtg.card.layout/split
    :mtg.card.layout/transform
    :mtg.card.layout/vanguard})

(s/def :mtg.mana/color
  #{:mtg.mana.color/black
    :mtg.mana.color/red
    :mtg.mana.color/green
    :mtg.mana.color/white
    :mtg.mana.color/blue
    :mtg.mana.color/colorless
    :mtg.mana.color/any})

(s/def ::mana-cost
  (s/map-of
    :mtg.mana/color
    (s/spec pos-int? :gen #(s/gen (s/int-in 1 4)))))

(s/def ::display-power (s/and non-empty-string?
                              #(< (count %) 5)))

(s/def ::display-toughness (s/and non-empty-string?
                                  #(< (count %) 5)))

(s/def ::power (s/spec int? :gen #(s/gen (s/int-in 0 13))))
(s/def ::toughness (s/spec int? :gen #(s/gen (s/int-in 0 13))))

(s/def :scryfall/oracle-id non-empty-string?)
(s/def :mtgjson/id non-empty-string?)

(s/def :mtg.card/type
  #{:mtg.card.type/Artifact
    :mtg.card.type/Conspiracy
    :mtg.card.type/Creature
    :mtg.card.type/Emblem
    :mtg.card.type/Enchantment
    :mtg.card.type/Hero
    :mtg.card.type/Instant
    :mtg.card.type/Land
    :mtg.card.type/Phenomenon
    :mtg.card.type/Plane
    :mtg.card.type/Planeswalker
    :mtg.card.type/Scheme
    :mtg.card.type/Sorcery
    :mtg.card.type/Summon
    :mtg.card.type/Tribal
    :mtg.card.type/Vanguard})

(s/def :mtg.card/types
  (s/coll-of :mtg.card/type :kind set?
    :min-count 1))

(s/def :mtg.card/supertype
  #{:mtg.card.supertype/Basic
    :mtg.card.supertype/Host
    :mtg.card.supertype/Legendary
    :mtg.card.supertype/Ongoing
    :mtg.card.supertype/Snow
    :mtg.card.supertype/World})

(s/def :mtg.card/supertypes
  (s/coll-of :mtg.card/supertype :kind set?))

(s/def :mtg.card/subtype
  #{:mtg.card.subtype/Advisor
    :mtg.card.subtype/Aetherborn
    :mtg.card.subtype/Ajani
    :mtg.card.subtype/Alara
    :mtg.card.subtype/Ally
    :mtg.card.subtype/Aminatou
    :mtg.card.subtype/Angel
    :mtg.card.subtype/Angrath
    :mtg.card.subtype/Antelope
    :mtg.card.subtype/Ape
    :mtg.card.subtype/Arcane
    :mtg.card.subtype/Archer
    :mtg.card.subtype/Archon
    :mtg.card.subtype/Arkhos
    :mtg.card.subtype/Arlinn
    :mtg.card.subtype/Artificer
    :mtg.card.subtype/Ashiok
    :mtg.card.subtype/Assassin
    :mtg.card.subtype/Assembly-Worker
    :mtg.card.subtype/Atog
    :mtg.card.subtype/Aura
    :mtg.card.subtype/Aurochs
    :mtg.card.subtype/Autobot
    :mtg.card.subtype/Avatar
    :mtg.card.subtype/Azgol
    :mtg.card.subtype/Azra
    :mtg.card.subtype/Badger
    :mtg.card.subtype/Barbarian
    :mtg.card.subtype/Basilisk
    :mtg.card.subtype/Bat
    :mtg.card.subtype/Bear
    :mtg.card.subtype/Beast
    :mtg.card.subtype/Beaver
    :mtg.card.subtype/Beeble
    :mtg.card.subtype/Belenon
    :mtg.card.subtype/Berserker
    :mtg.card.subtype/Bird
    :mtg.card.subtype/Boar
    :mtg.card.subtype/Bolas
    :mtg.card.subtype/Bolas’s
    :mtg.card.subtype/Brainiac
    :mtg.card.subtype/Bringer
    :mtg.card.subtype/Brushwagg
    :mtg.card.subtype/Bureaucrat
    :mtg.card.subtype/Camel
    :mtg.card.subtype/Carrier
    :mtg.card.subtype/Cartouche
    :mtg.card.subtype/Cat
    :mtg.card.subtype/Centaur
    :mtg.card.subtype/Cephalid
    :mtg.card.subtype/Chandra
    :mtg.card.subtype/Chicken
    :mtg.card.subtype/Child
    :mtg.card.subtype/Chimera
    :mtg.card.subtype/Clamfolk
    :mtg.card.subtype/Cleric
    :mtg.card.subtype/Cockatrice
    :mtg.card.subtype/Construct
    :mtg.card.subtype/Contraption
    :mtg.card.subtype/Cow
    :mtg.card.subtype/Crab
    :mtg.card.subtype/Crocodile
    :mtg.card.subtype/Curse
    :mtg.card.subtype/Cyborg
    :mtg.card.subtype/Cyclops
    :mtg.card.subtype/Dack
    :mtg.card.subtype/Daretti
    :mtg.card.subtype/Dauthi
    :mtg.card.subtype/Davriel
    :mtg.card.subtype/Deer
    :mtg.card.subtype/Demon
    :mtg.card.subtype/Desert
    :mtg.card.subtype/Designer
    :mtg.card.subtype/Devil
    :mtg.card.subtype/Dinosaur
    :mtg.card.subtype/Djinn
    :mtg.card.subtype/Dominaria
    :mtg.card.subtype/Domri
    :mtg.card.subtype/Donkey
    :mtg.card.subtype/Dovin
    :mtg.card.subtype/Dragon
    :mtg.card.subtype/Drake
    :mtg.card.subtype/Dreadnought
    :mtg.card.subtype/Drone
    :mtg.card.subtype/Druid
    :mtg.card.subtype/Dryad
    :mtg.card.subtype/Dungeon
    :mtg.card.subtype/Dwarf
    :mtg.card.subtype/Efreet
    :mtg.card.subtype/Egg
    :mtg.card.subtype/Elder
    :mtg.card.subtype/Eldrazi
    :mtg.card.subtype/Elemental
    :mtg.card.subtype/Elemental?
    :mtg.card.subtype/Elephant
    :mtg.card.subtype/Elf
    :mtg.card.subtype/Elk
    :mtg.card.subtype/Elspeth
    :mtg.card.subtype/Elves
    :mtg.card.subtype/Equilor
    :mtg.card.subtype/Equipment
    :mtg.card.subtype/Ergamon
    :mtg.card.subtype/Estrid
    :mtg.card.subtype/Etiquette
    :mtg.card.subtype/Eye
    :mtg.card.subtype/Fabacin
    :mtg.card.subtype/Faerie
    :mtg.card.subtype/Ferret
    :mtg.card.subtype/Fish
    :mtg.card.subtype/Flagbearer
    :mtg.card.subtype/Forest
    :mtg.card.subtype/Fortification
    :mtg.card.subtype/Fox
    :mtg.card.subtype/Freyalise
    :mtg.card.subtype/Frog
    :mtg.card.subtype/Fungus
    :mtg.card.subtype/Gamer
    :mtg.card.subtype/Gargoyle
    :mtg.card.subtype/Garruk
    :mtg.card.subtype/Gate
    :mtg.card.subtype/Giant
    :mtg.card.subtype/Gideon
    :mtg.card.subtype/Gnome
    :mtg.card.subtype/Goat
    :mtg.card.subtype/Goblin
    :mtg.card.subtype/God
    :mtg.card.subtype/Golem
    :mtg.card.subtype/Gorgon
    :mtg.card.subtype/Gremlin
    :mtg.card.subtype/Griffin
    :mtg.card.subtype/Gus
    :mtg.card.subtype/Hag
    :mtg.card.subtype/Harpy
    :mtg.card.subtype/Hatificer
    :mtg.card.subtype/Head
    :mtg.card.subtype/Hellion
    :mtg.card.subtype/Hero
    :mtg.card.subtype/Hippo
    :mtg.card.subtype/Hippogriff
    :mtg.card.subtype/Homarid
    :mtg.card.subtype/Homunculus
    :mtg.card.subtype/Horror
    :mtg.card.subtype/Horse
    :mtg.card.subtype/Hound
    :mtg.card.subtype/Huatli
    :mtg.card.subtype/Human
    :mtg.card.subtype/Hydra
    :mtg.card.subtype/Hyena
    :mtg.card.subtype/Illusion
    :mtg.card.subtype/Imp
    :mtg.card.subtype/Incarnation
    :mtg.card.subtype/Innistrad
    :mtg.card.subtype/Insect
    :mtg.card.subtype/Inzerva
    :mtg.card.subtype/Iquatana
    :mtg.card.subtype/Ir
    :mtg.card.subtype/Island
    :mtg.card.subtype/Jace
    :mtg.card.subtype/Jackal
    :mtg.card.subtype/Jaya
    :mtg.card.subtype/Jellyfish
    :mtg.card.subtype/Juggernaut
    :mtg.card.subtype/Kaldheim
    :mtg.card.subtype/Kamigawa
    :mtg.card.subtype/Kangaroo
    :mtg.card.subtype/Karn
    :mtg.card.subtype/Karsus
    :mtg.card.subtype/Kasmina
    :mtg.card.subtype/Kavu
    :mtg.card.subtype/Kaya
    :mtg.card.subtype/Kephalai
    :mtg.card.subtype/Killbot
    :mtg.card.subtype/Kinshala
    :mtg.card.subtype/Kiora
    :mtg.card.subtype/Kirin
    :mtg.card.subtype/Kithkin
    :mtg.card.subtype/Knight
    :mtg.card.subtype/Kobold
    :mtg.card.subtype/Kolbahan
    :mtg.card.subtype/Kor
    :mtg.card.subtype/Koth
    :mtg.card.subtype/Kraken
    :mtg.card.subtype/Kyneth
    :mtg.card.subtype/Lady
    :mtg.card.subtype/Lair
    :mtg.card.subtype/Lamia
    :mtg.card.subtype/Lammasu
    :mtg.card.subtype/Leech
    :mtg.card.subtype/Legend
    :mtg.card.subtype/Leviathan
    :mtg.card.subtype/Lhurgoyf
    :mtg.card.subtype/Licid
    :mtg.card.subtype/Liliana
    :mtg.card.subtype/Lizard
    :mtg.card.subtype/Lobster
    :mtg.card.subtype/Locus
    :mtg.card.subtype/Lorwyn
    :mtg.card.subtype/Luvion
    :mtg.card.subtype/Manticore
    :mtg.card.subtype/Master
    :mtg.card.subtype/Masticore
    :mtg.card.subtype/Meditation
    :mtg.card.subtype/Mercadia
    :mtg.card.subtype/Mercenary
    :mtg.card.subtype/Merfolk
    :mtg.card.subtype/Metathran
    :mtg.card.subtype/Mime
    :mtg.card.subtype/Mine
    :mtg.card.subtype/Minion
    :mtg.card.subtype/Minotaur
    :mtg.card.subtype/Mirrodin
    :mtg.card.subtype/Moag
    :mtg.card.subtype/Mole
    :mtg.card.subtype/Monger
    :mtg.card.subtype/Mongoose
    :mtg.card.subtype/Mongseng
    :mtg.card.subtype/Monk
    :mtg.card.subtype/Monkey
    :mtg.card.subtype/Moonfolk
    :mtg.card.subtype/Mountain
    :mtg.card.subtype/Mummy
    :mtg.card.subtype/Muraganda
    :mtg.card.subtype/Mutant
    :mtg.card.subtype/Myr
    :mtg.card.subtype/Mystic
    :mtg.card.subtype/Naga
    :mtg.card.subtype/Nahiri
    :mtg.card.subtype/Narset
    :mtg.card.subtype/Nautilus
    :mtg.card.subtype/Nephilim
    :mtg.card.subtype/New
    :mtg.card.subtype/Nightmare
    :mtg.card.subtype/Nightstalker
    :mtg.card.subtype/Ninja
    :mtg.card.subtype/Nissa
    :mtg.card.subtype/Nixilis
    :mtg.card.subtype/Noggle
    :mtg.card.subtype/Nomad
    :mtg.card.subtype/Nymph
    :mtg.card.subtype/Octopus
    :mtg.card.subtype/Ogre
    :mtg.card.subtype/Ooze
    :mtg.card.subtype/Orc
    :mtg.card.subtype/Orgg
    :mtg.card.subtype/Ouphe
    :mtg.card.subtype/Ox
    :mtg.card.subtype/Oyster
    :mtg.card.subtype/Pangolin
    :mtg.card.subtype/Paratrooper
    :mtg.card.subtype/Pegasus
    :mtg.card.subtype/Penguin
    :mtg.card.subtype/Pest
    :mtg.card.subtype/Phelddagrif
    :mtg.card.subtype/Phoenix
    :mtg.card.subtype/Phyrexia
    :mtg.card.subtype/Pilot
    :mtg.card.subtype/Pirate
    :mtg.card.subtype/Plains
    :mtg.card.subtype/Plant
    :mtg.card.subtype/Power-Plant
    :mtg.card.subtype/Praetor
    :mtg.card.subtype/Processor
    :mtg.card.subtype/Proper
    :mtg.card.subtype/Pyrulea
    :mtg.card.subtype/Rabbit
    :mtg.card.subtype/Rabiah
    :mtg.card.subtype/Raccoon
    :mtg.card.subtype/Ral
    :mtg.card.subtype/Rat
    :mtg.card.subtype/Rath
    :mtg.card.subtype/Ravnica
    :mtg.card.subtype/Realm
    :mtg.card.subtype/Rebel
    :mtg.card.subtype/Reflection
    :mtg.card.subtype/Regatha
    :mtg.card.subtype/Rhino
    :mtg.card.subtype/Rigger
    :mtg.card.subtype/Rogue
    :mtg.card.subtype/Rowan
    :mtg.card.subtype/Sable
    :mtg.card.subtype/Saga
    :mtg.card.subtype/Saheeli
    :mtg.card.subtype/Salamander
    :mtg.card.subtype/Samurai
    :mtg.card.subtype/Samut
    :mtg.card.subtype/Sarkhan
    :mtg.card.subtype/Satyr
    :mtg.card.subtype/Scarecrow
    :mtg.card.subtype/Scientist
    :mtg.card.subtype/Scorpion
    :mtg.card.subtype/Scout
    :mtg.card.subtype/Segovia
    :mtg.card.subtype/Serpent
    :mtg.card.subtype/Serra
    :mtg.card.subtype/Serra’s
    :mtg.card.subtype/Shade
    :mtg.card.subtype/Shadowmoor
    :mtg.card.subtype/Shaman
    :mtg.card.subtype/Shandalar
    :mtg.card.subtype/Shapeshifter
    :mtg.card.subtype/Sheep
    :mtg.card.subtype/Ship
    :mtg.card.subtype/Shrine
    :mtg.card.subtype/Siren
    :mtg.card.subtype/Skeleton
    :mtg.card.subtype/Slith
    :mtg.card.subtype/Sliver
    :mtg.card.subtype/Slug
    :mtg.card.subtype/Snake
    :mtg.card.subtype/Soldier
    :mtg.card.subtype/Soltari
    :mtg.card.subtype/Sorin
    :mtg.card.subtype/Spawn
    :mtg.card.subtype/Specter
    :mtg.card.subtype/Spellshaper
    :mtg.card.subtype/Sphinx
    :mtg.card.subtype/Spider
    :mtg.card.subtype/Spike
    :mtg.card.subtype/Spirit
    :mtg.card.subtype/Sponge
    :mtg.card.subtype/Spy
    :mtg.card.subtype/Squid
    :mtg.card.subtype/Squirrel
    :mtg.card.subtype/Starfish
    :mtg.card.subtype/Surrakar
    :mtg.card.subtype/Swamp
    :mtg.card.subtype/Tamiyo
    :mtg.card.subtype/Teferi
    :mtg.card.subtype/Teyo
    :mtg.card.subtype/Tezzeret
    :mtg.card.subtype/Thalakos
    :mtg.card.subtype/Thopter
    :mtg.card.subtype/Thrull
    :mtg.card.subtype/Tibalt
    :mtg.card.subtype/Tower
    :mtg.card.subtype/Townsfolk
    :mtg.card.subtype/Trap
    :mtg.card.subtype/Treefolk
    :mtg.card.subtype/Trilobite
    :mtg.card.subtype/Troll
    :mtg.card.subtype/Turtle
    :mtg.card.subtype/Ugin
    :mtg.card.subtype/Ulgrotha
    :mtg.card.subtype/Unicorn
    :mtg.card.subtype/Urza
    :mtg.card.subtype/Urza’s
    :mtg.card.subtype/Valla
    :mtg.card.subtype/Vampire
    :mtg.card.subtype/Vampyre
    :mtg.card.subtype/Vedalken
    :mtg.card.subtype/Vehicle
    :mtg.card.subtype/Venser
    :mtg.card.subtype/Viashino
    :mtg.card.subtype/Villain
    :mtg.card.subtype/Vivien
    :mtg.card.subtype/Volver
    :mtg.card.subtype/Vraska
    :mtg.card.subtype/Vryn
    :mtg.card.subtype/Waiter
    :mtg.card.subtype/Wall
    :mtg.card.subtype/Warrior
    :mtg.card.subtype/Weird
    :mtg.card.subtype/Werewolf
    :mtg.card.subtype/Whale
    :mtg.card.subtype/Wildfire
    :mtg.card.subtype/Will
    :mtg.card.subtype/Windgrace
    :mtg.card.subtype/Wizard
    :mtg.card.subtype/Wolf
    :mtg.card.subtype/Wolverine
    :mtg.card.subtype/Wombat
    :mtg.card.subtype/Worm
    :mtg.card.subtype/Wraith
    :mtg.card.subtype/Wrestler
    :mtg.card.subtype/Wurm
    :mtg.card.subtype/Xenagos
    :mtg.card.subtype/Xerex
    :mtg.card.subtype/Yanggu
    :mtg.card.subtype/Yanling
    :mtg.card.subtype/Yeti
    :mtg.card.subtype/Zendikar
    :mtg.card.subtype/Zombie
    :mtg.card.subtype/Zubera
    :mtg.card.subtype/and/or
    :mtg.card.subtype/of})

(s/def :mtg.card/subtypes
  (s/coll-of :mtg.card/subtype :kind set?))

#_(def last-spec *1)
#_(gen/generate (s/gen last-spec))

(s/def :mtg/card
  (s/keys
    :req [::name
          ::color-identity
          ::colors
          ::converted-mana-cost
          ::layout
          ::mana-cost
          :scryfall/oracle-id
          :mtgjson/id
          :mtg.card/types
          :mtg.card/supertypes
          :mtg.card/subtypes]
    :opt [::power
          ::toughness
          ::display-power
          ::display-toughness]))
```
