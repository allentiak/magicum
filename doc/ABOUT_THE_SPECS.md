# About the original specs implementation

They were written by Robert Stuttaford, who gave express permission to use it.

## Origin

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


## Code

_See https://github.com/robert-stuttaford/mtg/blob/c69140a0252969328fc0c6838034f3e03b1a7185/src/mtg.clj_

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
