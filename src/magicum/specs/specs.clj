(ns magicum.specs.specs
  (:gen-class)
  (:require [clojure.spec.alpha :as s]))

;; metadata:
;; :rule nnn.nb - Comprehensive Rule on which the element is based on. This is based on MTG Wiki's recommendation of how to referencing the rules.
;; :version yyyy.mm.dd - date of the Comprehensive Rules document used to create this element.

;; This file is intended as a formal specification of the Comprehensive Rules document. It used to have actual spec metadata. As clojure.spec 1.x does not support metadata (the upcoming 2.x version should), this file was originally "uncompilable". However, the idea is to make this file compilable; so that metadata has been retired in favor of an ugly 'def' block.

(def comprehensive-rules-version "2020.09.25")

;; 1xx: game concepts


;; 106: mana

;; 106.1a
(s/def
  ::mana-color #{::white ::blue ::black ::red ::green})

;; 106.1b
(s/def
  ::mana-type #{::mana-color ::colorless})


;; 107: numbers and symbols

;; 107.4

(s/def
  ::primary-mana-symbol {::w ::white, ::u ::blue, ::b ::black, ::r ::red, ::g ::green})

(s/def
  ::variable-mana-symbol #{::x})

(s/def
  ::generic-mana-symbol (s/or ::variable-mana-symbol nat-int?))

(s/def
  ::colorless-mana-symbol {::c ::colorless})

(s/def
  ::hybrid-mana-symbol #{::wu ::wb ::ub ::ur ::br ::bg ::rg ::rw ::gw ::gu})

(s/def
  ::monocolored-hybrid-mana-symbol #{::2w ::2u ::2b ::2r ::2g})

(s/def
  ::colored-phyrexian-mana-symbol #{::wp ::up ::bp ::rp ::gp})

(s/def
  ::generic-phyrexian-mana-symbol #{::p})

(s/def
  ::phyrexian-mana-symbol (conj ::generic-phyrexian-mana-symbol ::colored-phyrexian-mana-symbol))

(s/def
   ::snow-mana-symbol #{::s})

(s/def
  ::mana-symbol (conj ::primary-mana-symbol ::generic-mana-symbol ::colorless-mana-symbol ::hybrid-mana-symbol ::monocolored-hybrid-mana-symbol ::phyrexian-mana-symbol ::snow-mana-symbol))


;; 107.5
(s/def
  ::tap-symbol #{::t})


;; 107.6
(s/def
  ::untap-symbol #{::q})

;; TODO: add level-symbol (107.8, 107.8a, 107.8b)

;; 107.11
(s/def
  ::planeswalker-symbol #{::pw})

;; 107.12
(s/def
  ::chaos-symbol #{::chaos})

;; 107.13
(s/def
  ::energy-symbol #{::e})

;; TODO: add saga-symbol (107.15, 107.15a, 107.15b)


;; 108: cards (see section 2xx)


;; 109: objects

;; 109.1
(s/def
  ::object #{::ability-on-the-stack ::card ::card-copy ::token ::spell ::permanent ::emblem})

;; 109.3
(s/def
  ::object-characteristic #{::name ::mana-cost ::color ::color-indicator ::card-type ::subtype ::supertype ::rules-text ::abilities ::power ::toughness ::loyalty ::hand-modifier ::life-modifier})

;; TODO: add "109.4"


;; 110: permanents

;; TODO: add "110.1"

;; 110.4
(s/def
  ::permanent-type #{::artifact ::creature ::enchantment ::land ::planeswalker})

;; TODO: add permanent-card ("110.4a")

;; FIXME: all this options are binary
;; 110.5
(s/def
  ::permanent-status #{::tapped ::flipped ::face-up ::phased-in})

;; 111: tokens

;; TODO: review "111.10a-c" to see whether predefined tokens should be added here


;; 112: spells


;; 113: abilities

;; TODO: review "113.1-2" to see whether (and how) abilities should be added here

;; FIXME: 113.4 mana abilities are also activated, triggered, or both
;; 113.3
(s/def
  ::ability-type #{::spell ::activated ::triggered ::static ::mana})

;; TODO: add "113.5" loyalty-ability

;; TODO: check how "114" emblems (for commander) could be added here

;; TODO: check whether "115" targets should be added here


;; 116: special actions

;; FIXME: verify special action types. they should be nine, but there are only six here
;; 116.2
(s/def
  ::special-action-type #{::play-a-land ::turn-a-face-down-creature-face-up ::exile-card-with-suspend-in-hand ::put-companion-in-hand ::rolling-the-planar-die ::turn-a-face-down-conspiracy-card-in-the-command-zone-face-up})

;; TODO: 117: timing and priority: a player can be either :active or :inactive, :with-priority or :without-priority

;; FIXME: 118.9: cost: take into account original and alternative costs
;; 118
(s/def
  ::cost-type #{::mandatory ::optional})

;; TODO: 119: life: consider whether there actualyy is something there than can be specable (I think not)

;; TODO: 120.4: damage: assignation steps are there

;; TODO: 121: draw relates to dynamic model - not specable

;; 122: counters
;; 122.1b
(s/def
  ::keyword-counter #{::flying ::first-strike ::double-strike ::deathtouch ::haste ::hexproof ::indestructible ::lifelink ::menace ::reach ::trample ::vigilance})

;; 122
(s/def
  ::counter-type #{::plus-or-minus-counter ::keyword-counter ::loyalty-counter ::poison-counter})

;; 2xx: parts of a card
;; 200.1
(s/def
  ::card (s/keys :opt [::name ::mana-cost ::illustration ::color-indicator ::type ::expansion-symbol ::text-box ::power-and-toughness ::loyalty ::hand-modifier ::life-modifier ::illustration-credit ::legal-text ::collector-number]))

;; 201: name: not specable

;; 202: mana, cost, and color

;; FIXME: see 202.3x on how to define this fn
;; 202.3
(s/defn
  ::converted-mana-cost)

;; 203: illustration: not specable

;; 204: color-indicator: not specable


(s/def ::artist (s/and string? seq))
(s/def ::set (s/and string? seq))
(s/def ::set-number pos-int?)
(s/def ::metadata (s/keys :req [::set ::set-number ::artist]))
;; (s/exercise ::metadata)
(s/def ::name (s/and string? seq))
(s/def ::types (s/and set? (s/+ ::type)))

(s/exercise ::types)

(s/def ::sub-type ::type)
(s/def ::rules string?)
(s/def ::legendary? boolean?)
(s/def ::world? boolean?)

(s/def ::base-card (s/keys :req [::name ::types ::metadata ::rules]
                           :opt [::sub-type ::legendary? ::world?]))

(->> (s/exercise ::base-card) (take 5))

(s/def ::amount pos-int?)
(s/def ::color #{:white :blue :black :red :green})
(s/def ::colors (s/+ ::color))

(s/def ::mana (s/keys :req [::amount]
                      :opt [::colors]))
(s/def ::manas (s/+ ::mana))
(s/def ::cost (s/* ::manas))

(s/def ::spell (s/and ::base-card (s/keys :req [::cost])))

(s/def ::power int?)
(s/def ::toughness int?)

(s/def ::creature (s/and ::spell (s/keys :req [::power ::toughness])))

(s/def ::loyalty int?)

(s/def ::planeswalker (s/and ::spell (s/keys :req [::loyalty])))

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


;; 205: type line

;; 205.2a && 300.1
(s/def
  ::card-type #{::artifact ::conspiracy ::creature ::enchantment ::instant ::land ::phenomenon ::plane ::planeswalker ::scheme ::sorcery ::tribal ::vanguard})

;; (s/exercise ::type)

;; 205.3g
(s/def
  ::artifact-type #{::clue ::contraption ::equipment ::food ::fortification ::gold ::treasure ::vehicle})

;; 205.3h
(s/def
  ::enchantment-type #{::aura ::cartouche ::curse ::saga ::shrine})


;; 205.3i

(s/def
  ::land-type #{::desert ::basic-land-type ::gate ::lair ::locus ::mine ::power-plant ::tower ::urza's})

(s/def
  ::basic-land-type #{::forest ::island ::mountain ::plains ::swamp})

;; 205.3j
(s/def
  ::planeswalker-type #{::ajani ::aminatou ::angrath ::arlinn ::ashiok ::basri ::bolas ::calix ::chandra ::dack ::daretti ::davriel ::domri ::dovin ::elspeth ::estrid ::freyalise ::garruk ::gideon ::huatli ::jace ::jaya ::karn ::kasmina ::kaya ::kiora ::koth ::liliana ::lukka ::nahiri ::narset ::nissa ::nixilis ::oko ::ral ::rowan ::saheeli ::samut ::sarkhan ::serra ::sorin ::tamiyo ::teferi ::teyo ::tezzeret ::tibalt ::ugin ::venser ::vivien ::vraska ::will ::windgrace ::wrenn ::xenagos ::yanggu ::yanling})

;; 205.3k
(s/def
  ::spell-type #{::adventure ::arcane ::trap})

;; 205.3m
(s/def
  ::creature-type #{::advisor ::aetherborn ::ally ::angel ::antelope ::ape ::archer ::archon ::army ::artificer ::assassin ::assembly-worker ::atog ::aurochs ::avatar ::azra ::badger ::barbarian ::basilisk ::bat ::bear ::beast ::beeble ::berserker ::bird ::blinkmoth ::boar ::bringer ::brushwagg ::camarid ::camel ::caribou ::carrier ::cat ::centaur ::cephalid ::chimera ::citizen ::cleric ::cockatrice ::construct ::coward ::crab ::crocodile ::cyclops ::dauthi ::demigod ::demon ::deserter ::devil ::dinosaur ::djinn ::dog ::dragon ::drake ::dreadnought ::drone ::druid ::dryad ::dwarf ::efreet ::egg ::elder ::eldrazi ::elemental ::elephant ::elf ::elk ::eye ::faerie ::ferret ::fish ::flagbearer ::fox ::frog ::fungus ::gargoyle ::germ ::giant ::gnome ::goat ::goblin ::god ::golem ::gorgon ::graveborn ::gremlin ::griffin ::hag ::harpy ::hellion ::hippo ::hippogriff ::homarid ::homunculus ::horror ::horse ::human ::hydra ::hyena ::illusion ::imp ::incarnation ::insect ::jackal ::jellyfish ::juggernaut ::kavu ::kirin ::kithkin ::knight ::kobold ::kor ::kraken ::lamia ::lammasu ::leech ::leviathan ::lhurgoyf ::licid ::lizard ::manticore ::masticore ::mercenary ::merfolk ::metathran ::minion ::minotaur ::mole ::monger ::mongoose ::monk ::monkey ::moonfolk ::mouse ::mutant ::myr ::mystic ::naga ::nautilus ::nephilim ::nightmare ::nightstalker ::ninja ::noble ::noggle ::nomad ::nymph ::octopus ::ogre ::ooze ::orb ::orc ::orgg ::otter ::ouphe ::ox ::oyster ::pangolin ::peasant ::pegasus ::pentavite ::pest ::phelddagrif ::phoenix ::pilot ::pincher ::pirate ::plant ::praetor ::prism ::processor ::rabbit ::rat ::rebel ::reflection ::rhino ::rigger ::rogue ::sable ::salamander ::samurai ::sand ::saproling ::satyr ::scarecrow ::scion ::scorpion ::scout ::sculpture ::serf ::serpent ::servo ::shade ::shaman ::shapeshifter ::shark ::sheep ::siren ::skeleton ::slith ::sliver ::slug ::snake ::soldier ::soltari ::spawn ::specter ::spellshaper ::sphinx ::spider ::spike ::spirit ::splinter ::sponge ::squid ::squirrel ::starfish ::surrakar ::survivor ::tentacle ::tetravite ::thalakos ::thopter ::thrull ::treefolk ::trilobite ::triskelavite ::troll ::turtle ::unicorn ::vampire ::vedalken ::viashino ::volver ::wall ::warlock ::warrior ::weird ::werewolf ::whale ::wizard ::wolf ::wolverine ::wombat ::worm ::wraith ::wurm ::yeti ::zombie ::zubera})

;; 205.3n
(s/def
  ::planar-type #{::alara ::arkhos ::azgol ::belenon ::bolas’s-meditation-realm ::dominaria ::equilor ::ergamon ::fabacin ::innistrad ::iquatana ::ir ::kaldheim ::kamigawa ::karsus ::kephalai ::kinshala ::kolbahan ::kyneth ::lorwyn ::luvion ::mercadia ::mirrodin ::moag ::mongseng ::muraganda ::new-phyrexia ::phyrexia ::pyrulea ::rabiah ::rath ::ravnica ::regatha ::segovia ::serra’s-realm ::shadowmoor ::shandalar ::ulgrotha ::valla ::vryn ::wildfire ::xerex ::zendikar})

;; 205.4a
(s/def
  ::supertype #{::basic ::legendary ::ongoing ::snow ::world})

;; 206: expansion symbol

;; FIXME: 206.1: expansion-symbol: verify how to spec this

;; 206.2
(s/def
  ::expansion-symbol-rarity #{::mythic-rare ::rare ::uncommon ::common ::timeshifted})

;; 207: text box
(s/def
  ::text-box-type #{::rules-text ::reminder-text ::flavor-text ::ability-word})

;; 207.2c
(s/def
  ::ability-word #{::adamant ::addendum ::battalion ::bloodrush ::channel ::chroma ::cohort ::constellation ::converge ::council’s-dilemma ::delirium ::domain ::eminence ::enrage ::fateful-hour ::ferocious ::formidable ::grandeur ::hellbent ::heroic ::imprint ::inspired ::join-forces ::kinship:: ::landfall ::lieutenant ::metalcraft ::morbid ::parley ::radiance ::raid ::rally ::revolt ::spell-mastery ::strive ::sweep ::tempting-offer ::threshold ::undergrowth ::will-of-the-council})

;; 208: power and toughness

;; 209: TODO: loyalty (planeswalkers)

;; 210: TODO: hand modifier (vanguard)

;; 211: TODO: life modifier (vanguard)

;; FIXME: set-code (three chars)
;; 212.1d

(s/def
  ::set-code)

;; FIXME: language-code (two chars)
(s/def
  ::language-code)

;; 3xx: card types

;; 300.1: see 205.2a

;; 301: artifacts

;; 301.5
;; FIXME: equipment artifacts can only be legally attached to ("equip") one creature
(s/def
  ::equipment)

;; 301.6
;; FIXME: fortification artifacts can only be legally attached to ("fortify") one land
(s/def
  ::fortification)

;; 301.7
;; FIXME: vehicle artifact can only be legally transformed to an artifact creature ("crewed") by creatures
(s/def
  ::equipment)


;; 302: creatures

;; 302.7
;; FIXME: summoning sickness (no attack, no activated-abilities that include :t)
(s/def
  ::summoning-sickness)

;; TODO: 303: enchantments

;; 303.4
;; FIXME: aura-enchantments need one target; can only enchant as long as is not a creature
(s/def
  ::aura-enchantment)

;; TODO: 304: instants - nothing to spec here?

;; 305
;; FIXME: lands: only one land can be played per turn; if a land is also something else, it has to be played as a land
(s/def
  ::player-can-play-a-land)

;; TODO: rule 306: planeswalkers

;; TODO: rule 307: sorceries

;; TODO: rule 308: tribals

;; TODO: rule 309: planes

;; TODO: rule 310: phenomena

;; TODO: rule 311: vanguards

;; TODO: rule 312: schemes

;; TODO: rule 313: conspiracies


;; 4xx: zones


;; 400.1

;; FIXME: verify rules 40x to see whether something else can be speced
(s/def
  ::zone #{::library ::hand ::battlefield ::graveyard ::stack ::exile ::command ::ante})

(s/def
  ::shared-zone #{::battlefield ::stack ::exile ::command ::ante})

(s/def
  ::non-shared-zone #{::library ::hand ::graveyard})


;; 400.2

(s/def
  ::public-zone #{::graveyard ::battlefield ::stack ::exile ::ante ::command})

(s/def
  ::hidden-zone #{::library ::hand})


;; 5xx: turn structure

;; 500.1
(s/def
  ::phase (::beginning ::precombat-main ::combat ::postcombat-main ::ending))

;; 501.1
(s/def
  ::beginning-phase-step (::untap ::upkeep ::draw))

;; 506.1
(s/def
  ::combat-phase-step (::beginning-of-combat ::declare-attackers ::declare-blockers ::combat-damage ::end-of-combat))

;; 512.1
(s/def
  ::ending-phase-step (::end ::cleanup))


;; 6xx: spells, abilities, and effects

;; 601: spells

;; 601.1
(s/def
  ::playing-a-card #{::playing-a-land ::casting-a-card-as-a-spell})

;; 601
(s/def
  ::spell-casting-steps (::being-allowed-to-cast ::propose-spell-to-cast ::determinate-legal-casting-cost ::pay-legal-casting-cost ::determinate-legal-target(s) ::choose-legal-target(s) ::move-card-to-stack))

;; 602-607
(s/def
  ::ability-type #{::activated-ability-type ::triggered-ability-type ::static-ability-type})


;; 602: activated abilities

;; 602, 605, 606
(s/def
  ::activated-ability-type #{::mana-ability ::loyalty-ability ::other-activated-ability})

;; 602.2x
(s/def
  ::ability-activation-steps (::being-allowed-to-activate ::propose-ability-to-activate ::determinate-legal-activation-cost ::pay-legal-activation-cost ::determinate-legal-target(s) ::choose-legal-target(s) ::reveal-card-with-ability ::move-ability-to-stack))

;; 603: triggered abilities
(s/def
  ::every-time-an-event-occurs: (::determine-appliable-triggered-abilities ::activate-appliable-triggered-abilities))

;; TODO: 603.10: triggered abilities that look back in time

;; 604: static abilities (see 702: keyword abilities)

;; 605: mana abilities

;; mana abilities can either be activated or triggered

;; 606: loyalty abilities

;; 607: linked abilities

(s/def
  ::ability (s/* ::linked-to-ability))

(s/def
  ::linked-to-ability ::ability)


;; 608: resolving spells and abilities

;; 609: effects

(s/def
  ::effect-duration #{::one-shot ::continuous})

(s/def
  ::effect-type #{::text-changing ::replacement ::prevention})

;; 610: one-shot effects

;; 611: continuous effects

;; 612: text-changing effects

;; 613: interaction of continuous effects

;; 614: replacement effects

;; 615: prevention effects

;; 616: interaction of replacement/prevention effects


;; 7xx: additional rules

;; 700: general
;; 700.8: parties

;; 701: keyword actions
(s/def
  ::keyword-action #{::activate ::attach ::cast ::counter ::create ::destroy ::discard ::double ::exchange ::exile ::fight ::mill ::play ::regenerate ::reveal ::sacrifice ::scry ::search ::shuffle ::tap ::untap ::fateseal ::clash ::planeswalk ::set-in-motion ::abandon ::proliferate ::transform ::detain ::populate ::monstrosity ::vote ::bolster ::manifest ::support ::investigate ::meld ::goad ::exert ::explore ::assemble ::surveil ::adapt ::amass})

;; 702: keyword abilities
(s/def
  ::keyword-ability #{::deathtouch ::defender ::double-strike ::enchant ::equip ::first-strike ::flash ::flying ::haste ::hexproof ::indestructible ::intimidate ::landwalk ::lifelink ::protection ::reach ::shroud ::trample ::vigilance ::banding ::rampage ::cumulative-upkeep ::flanking ::flashback ::phasing ::buyback ::shadow ::cycling ::echo ::horsemanship ::fading ::kicker ::flashback ::madness ::fear ::morph ::amplify ::provoke ::storm ::affinity ::entwine ::modular ::sunburst ::bushido ::soulshift ::splice ::offering ::ninjutsu ::epic ::convoke ::dredge ::transmute ::bloodthirst ::haunt ::replicate ::forecast ::graft ::recover ::ripple ::split-second ::suspend ::vanishing ::absorb ::aura-swap ::delve ::fortify ::frenzy ::gravestorm ::poisonous ::transfigure ::champion ::changeling ::evoke ::hideaway ::prowl ::reinforce ::conspire ::persist ::wither ::retrace ::devour ::exalted ::unearth ::cascade ::annihilator ::level-up ::rebound ::totem-armor ::infect ::battle-cry ::living-weapon ::undying ::miracle ::soulbound ::overload ::scavenge ::unleash ::cipher ::evolve ::extort ::fuse ::bestow ::tribute ::dethrone ::hidden-agenda ::outlast ::prowess ::dash ::exploit ::menace ::renown ::awaken ::devoid ::ingest ::myriad ::surge ::skulk ::emerge ::escalate ::melee ::crew ::fabricate ::partner ::undaunted ::improvise ::aftermath ::embalm ::eternalize ::afflict ::assist ::jump-start ::mentor ::afterlife ::riot ::spectacle ::escape ::companion ::mutate})

;; 703: stated-based actions
;; 704: turn-based actions

(s/def
  ::action-type #{::state-based ::turn-based})

;; 705: flipping a coin

;; 706: copying objects

;; 707: face-down spells and permanents

;; 708: split cards
;; 709: flip cards
;; 710: leveler cards
;; 711: double-faced cards
;; 712: meld cards
;; 713: checklist cards
;; 714: saga cards
;; 715: adventurer cards
(s/def
  ::card-type #{::split ::flip ::leveler ::modal-double-faced ::transforming-double-faced ::meld ::substitute ::saga ::adventurer})

;; 716: controlling another player

;; 717: ending turns and phases

;; 718: the monarch

;; 719: restarting the game

;; 720: subgames

;; 721: merging with permanents

;; 722: taking shortcuts

;; 723: handling illegal actions


;; 8xx: multiplayer rules

;; 800: general

;; 801: option: limited range of influence

;; 802: option: attack multiple players

;; 803: option: attack left, attack right

;; 804: option: deploy creatures

;; 805: option: shared team

;; 806: variant: free-for-ally

;; 807: variant: grand melee

;; 808: variant: team vs. team

;; 809: variant: emperor

;; 810: variant: two-headed giant

;; 811: variant: alternating teams


;; 9xx: casual variants

;; 900: general

;; 901: variant: planechase

;; 902: variant: vanguard

;; 903: variant: commander

;; 904: variant: archenemy

;; 905: variant: conspiracy draft
