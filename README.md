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

### Design Choices

- Use advanced programming techniques, such as formal specifications, and property-based and generative testing as much as possible.

  Luckily, [Clojure comes "with batteries ~included~ ready-to-plug-when-needed"](https://clojure.org/news/2012/02/17/clojure-governance), so it has these features already available, through [clojure.spec](https://clojure.org/about/spec) and [test.check](https://github.com/clojure/test.check) :-)

- Base the aforementioned specifications on the most recent version of the Magic Comprehensive Rules, as published by the Magic copyright holders. As a reference, you will find a copy of the latest version used in [/resources/third-party](/resources/third-party).

### CI/CD

FIXME: add CI/CD setup data.


## Acknowledgements

This program is inspired by other Magic: The Gathering _libre_ implementations for the JVM platform. More specifically, by (in discovery order) [Magarena](https://github.com/magarena/magarena/), [XMage](https://github.com/magefree/mage), [Matag](https://github.com/antonioalonzi/matag), and [Forge](https://git.cardforge.org/core-developers/forge).

The domain specs are based on Robert Stuttaford's and Adam Frey's work, with their express permission to use their code as I see fit. For more details, see [doc/ABOUT_THE_SPECS.md](doc/ABOUT_THE_SPECS.md)

As a reference, you will find a copy of the latest Magic Comprehensive Rules document on which the specs are based in the folder [/resources/third-party](/resources/third-party).

All trademarks and/or registered trademarks mentioned in this program and/or its documentation
are the property of their own owners. We are not affiliated in any way with the copyright owners of
Magic: The Gathering, nor we claim any copyright over their game, their implementations, their art assets or any other kind of intellectual wealth.


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
