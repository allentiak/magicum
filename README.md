# magicum

> **magicum**
> 1. Latin: the nominative _neuter_ singular of magicus.
>
> **magicus**
>  1. magic, magical.
>
> _from [Wiktionary, the free dictionary](https://en.wiktionary.org/wiki/magicus#Latin)_

`magicum` is a _libre_ [Magic: The Gathering](https://magic.wizards.com/) game rules engine implementation, written in [Clojure](https://clojure.org/).

This project is currently in a very early development stage, so there is not much functionality to be seen... yet ;-)


## Pronunciation

[IPA](https://en.wiktionary.org/wiki/Wiktionary:International_Phonetic_Alphabet)^([key](https://en.wiktionary.org/wiki/Appendix:Latin_pronunciation)): _/'ma.ʝi.kum/, [ˈma.ʝɪ.kʊm]_

Whereas the original [_Classical Latin_](https://en.wikipedia.org/wiki/Classical_Latin) word should be pronounced _/ˈma.ɡi.kum/_ or _[ˈma.ɡɪ.kʊm]_, as the origin language of the original game's name is English, we use an English-like pronunciation.


## Release Information

This project follows the version scheme `MAJOR.MINOR.COMMITS`, where `MAJOR` and `MINOR` provide some relative indication of the size of the change, but do not follow semantic versioning. `COMMITS` is an ever-increasing counter of commits since the beginning of this repository.

In general, all changes endeavor to be non-breaking (by moving to new names rather than by breaking existing names). For a more detailed rationale, please refer to [Rich Hickey's "spec-ulation Keynote"](https://www.youtube.com/watch?v=oyLBGkS5ICk) from [Clojure/conj 2016](https://2016.clojure-conj.org/).

Latest release: (none yet)


## Download

https://github.com/allentiak/magicum.


## Usage

FIXME: explanation

    $ java -jar magicum-x.y.z-standalone.jar [args]


### Options

FIXME: listing of options this app accepts.


### Examples

FIXME: app invocation examples.


### Bugs

At this point, probably too many. To be discovered! :-)


## Development

### Design Approach

- Use advanced programming techniques, such as formal specifications, and property-based and generative testing as much as possible.

  Luckily, [Clojure comes "with batteries ~included~ ready-to-plug-when-needed"](https://clojure.org/news/2012/02/17/clojure-governance), so it has these features already available, through [clojure.spec](https://clojure.org/about/spec) and [test.check](https://github.com/clojure/test.check) :-)

- Base the aforementioned specifications on the most recent version of the Magic Comprehensive Rules, as published by the Magic copyright holders. As a reference, you will find a copy of the latest version used in [/doc/third-party](/resources/third-party).

- Stand on the shoulders of giants: use Systematic Program Design to strive for achieving a Clean Architecture.

  Systematic Program Design is explained in depth in ["How to Design Programs, Second Edition"](https://htdp.org), by Matthias Felleisen, Robert Bruce Findler, Matthew Flatt, and Shriram Krishnamurthi.

  The Clean Architecture is described in ["The Clean Architecture"](https://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html), by Robert C. Martin (a.k.a. "Uncle Bob").


### CI/CD

FIXME: add CI/CD setup data.


## Acknowledgements

This program is inspired by other  Magic: The Gathering _libre_ implementations. It's great being able to get inspiration from these great, already-working projects!

Luckily, we have tried them all, read their code, and even made (minor) contributions to some of them.

And we continue to use a couple of them regularly, as each one of them has one thing we appreciate.

More specifically, we are referring to (in discovery order):

Implementation (Backend + Frontend) | Strongest point
--------- | ----------
[Magarena (Java + Swing)](https://github.com/magarena/magarena/) | The only that fully implements an state-of-the-art MCTS AI.
[XMage (Java + Swing)](https://github.com/magefree/mage) | The best (OO) architectured.
[Matag (Java + JavaScript)](https://github.com/antonioalonzi/matag) | The best in-game UI, by far.
[MvS' Magic (Haskell)](https://github.com/MedeaMelana/Magic) | Written in a fully-functional language, with a Client/Server architecture.
[Forge (Java + Swing)](https://git.cardforge.org/core-developers/forge) | Best overall out-game UI design, and gameplay features.

As I couldn't find any FP Magic implementation written in Clojure, and given that I want to give [clojure.spec](https://clojure.org/about/spec) a try, I set up to write my own.

Of course, I was not the first one to come up with the idea of using [clojure.spec](https://clojure.org/about/spec) for this. The domain specs are based on Robert Stuttaford's and Adam Frey's work, with their express permission to use their code as I see fit. For more details, see [doc/ABOUT_THE_SPECS.md](doc/ABOUT_THE_SPECS.md). You will find a copy of the latest Magic Comprehensive Rules document on which the specs are based in the folder [/resources/third-party](/resources/third-party).


## License

Copyright © 2020, Leandro Doctors.

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU Affero General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

Additional permission under GNU AGPL version 3 section 7:

If you modify this Program, or any covered work, by linking or combining
it with Clojure (or a modified version of that library), containing parts
covered under the same terms as Clojure (currently, the Eclipse Public
License version 1.0), the licensors of this Program grant you additional
permission to convey the resulting work. Corresponding Source for a
non-source form of such a combination shall include the source code for
the parts of Clojure used as well as that of the covered work.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
GNU General Public License for more details.

You should have received a copy of the GNU Affero General Public License
along with this program. If not, see <http://www.gnu.org/licenses/>.

All third-party trademarks and/or registered trademarks mentioned in this program and/or its documentation
are the property of their own owners.
We are not affiliated in any way with the copyright owners of
the official Magic: The Gathering game implementations (neither digital or analog), nor we claim any copyright over their products, their art assets or any other kind of intellectual wealth of theirs. What's theirs, is theirs. What's ours, is ours.
In case of doubt, please do contact us, or open a ticket.
