(ns magicum.utils-test
  (:require
   [clojure.test :refer [deftest is testing]]
   [magicum.utils :as utils]))

(set! *warn-on-reflection* true)

(deftest utils-test
  (testing "remove-first"
    (is (= '(1 3 2 4) (utils/remove-first 2 '(1 2 3 2 4))))))
