(ns magicum.game-test
  "Tests for the game namespace.

  Still trying to find out what to put in here..."
  (:require [clojure.test :refer [deftest is testing]]
            [magicum.specs :as specs]))

(set! *warn-on-reflection* true)

(deftest basic-tests
  (let [card      {:game/card-name "Island"}
        old-world :game/{:hand [card] :battlefield []}
        new-world :magicum.specs.game{:hand [] :battlefield [card]}]
    (testing "world consistency"
      (is (= new-world (specs/play-a-card old-world 0 :game/hand :game/battlefield))))))

#_(magicum.specs.game/play-a-card
   {:magicum.specs.game/hand        [{:magicum.specs.game/card-name "Island"}]
    :magicum.specs.game/battlefield []}
   0 :hand :battlefield)
;; => {:magicum.specs.game/hand [#:magicum.specs.game{:card-name "Island"}], :magicum.specs.game/battlefield [], :hand (), :battlefield (nil)}

(specs/instrument)
