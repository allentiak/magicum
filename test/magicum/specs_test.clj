(ns magicum.specs-test
  (:require
   [clojure.test :refer [deftest is testing]]
   [magicum.specs :as specs]))

(set! *warn-on-reflection* true)

(testing "world consistency"
  (let [card {:card-name "My new card" :card-legal-text "This legal text is supposed to do something. However, it does nothing."}
        old-world {:hand (list card) :battlefield nil}
        new-world {:hand nil :battlefield (list card)}]
    (is (= new-world (specs/move-card old-world card :hand :battlefield)))))

(specs/instrument)
