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
  (let [card      {:magicum.specs.game/card-name "Island"}
        ;; in the first binding, the fully qualified name is appended into the data. This works when there is only one keyword.

        old-world #:magicum.specs.game{:hand [card] :battlefield []}
        new-world #:magicum.specs.game{:hand [] :battlefield [card]}]
        ;; in the second and third bindings, the fully qualified name is appended at the beginning of the data. This works when there are many keywords.

    (testing "world consistency"
      (is (= new-world (specs/play-a-card old-world 0 :magicum.specs.game/hand :magicum.specs.game/battlefield))))))

#_(magicum.specs.game/play-a-card
   {:magicum.specs.game/hand        [{:magicum.specs.game/card-name "Island"}]
    :magicum.specs.game/battlefield []}
   0 :hand :battlefield)
;; => {:magicum.specs.game/hand [#:magicum.specs.game{:card-name "Island"}], :magicum.specs.game/battlefield [], :hand (), :battlefield (nil)}

(specs/instrument)
