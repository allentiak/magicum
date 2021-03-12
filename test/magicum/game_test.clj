(ns magicum.game-test
  "Tests for the game namespace.

  Still trying to find out what to put in here..."
  (:require [clojure.test :refer [deftest is testing]]
            [magicum.game :as game]
            [magicum.specs :as specs]))

(set! *warn-on-reflection* true)

(game/instrument)

(deftest basic-tests
  (let [card      {:card/name "Island"}
        old-world {:zone/hand [card] :zone/battlefield []}
        new-world {:zone/hand [] :zone/battlefield [card]}]
    (testing "world consistency"
      (is (= new-world (game/play-a-card old-world 0 :zone/hand :zone/battlefield))))))

#_(magicum.specs.game/play-a-card
   {:magicum.specs.game/hand        [{:magicum.specs.game/card-name "Island"}]
    :magicum.specs.game/battlefield []}
   0 :hand :battlefield)
;; => {:magicum.specs.game/hand [#:magicum.specs.game{:card-name "Island"}], :magicum.specs.game/battlefield [], :hand (), :battlefield (nil)}
