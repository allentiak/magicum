(ns magicum.specs-test
  (:require
   [clojure.test :refer [deftest is testing]]
   [magicum.specs :as specs]))

(set! *warn-on-reflection* true)

;; (testing "manipulating zones"
;;   (let [card {:card-name "My new card" :card-legal-text "This legal text is supposed to do something. However, it does nothing."}
;;         non-empty-zone (list card)
;;         empty-zone (list nil)]
;;     (is (= empty-zone (remove-card-from-zone card non-empty-zone)))))

(testing "world consistency"
  (let [card {:card-name "My new card" :card-legal-text "This legal text is supposed to do something. However, it does nothing."}
        old-world {:hand (list card) :battlefield nil}
        new-world {:hand nil :battlefield (list card)}]
    (is (= new-world (specs/move-card old-world card :hand :battlefield)))))

(specs/instrument)
