(ns magicum.specs.game-test
  (:require [clojure.test :refer [deftest is testing]]
            [magicum.specs.game :as specs]))

(set! *warn-on-reflection* true)

;; (testing "manipulating zones"
;;   (let [card {:card-name "My new card" :card-legal-text "This legal text is supposed to do something. However, it does nothing."}
;;         non-empty-zone (list card)
;;         empty-zone (list nil)]
;;     (is (= empty-zone (remove-card-from-zone card non-empty-zone)))))

(deftest basic-tests
  (let [card {::card-name "Island"}
        old-world {::hand [card] ::battlefield []}
        new-world {::hand [] ::battlefield [card]}
        from-zone (::hand old-world)
        to-zone (::battlefield old-world)]
    (testing "world consistency"
      (is (= new-world (specs/play-a-card old-world 0 from-zone to-zone))))))

(specs/instrument)
