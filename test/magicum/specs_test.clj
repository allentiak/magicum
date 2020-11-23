(ns magicum.specs-test
  "Stub test namespace for the specs.

  For the moment, we are trying to find a namespace where to use (and 'test') the specs."
  (:require [clojure.test :refer [deftest is testing]]
            [magicum.specs :as specs]))

(set! *warn-on-reflection* true)

(specs/instrument)
