(ns magicum.specs-test
  (:require
   [clojure.test :refer [deftest is testing]]
   [magicum.specs :as specs]))

(set! *warn-on-reflection* true)

(specs/instrument)
