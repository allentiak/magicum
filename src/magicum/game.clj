(ns magicum.game
  "Specs for the core API of magicum.

  For now, all of the functions have specs here.

  In addition, there is an `instrument` function that provides a simple way to instrument all of the functions, and `unstrument` to undo that."
  (:gen-class)
  (:require [clojure.spec.alpha :as s]
            [clojure.spec.test.alpha :as st]
            [magicum.specs :as specs]
            [magicum.utils :as utils]))

(comment
  (require '[clojure.spec.alpha :as s]
           '[clojure.spec.test.alpha :as st]
           '[magicum.specs :as specs]
           '[magicum.utils :as utils])
  ,)

(set! *warn-on-reflection* true)
