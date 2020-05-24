(ns magicum.utils
  (:gen-class)
  (:require [clojure.string :as str]))

(defn subtypes->keywords
       [s]
       "converts a seq of subtypes 'Abcd, Bedcu, ... Yhou, and Zordu' into a seq of keywords"
       (map (comp keyword str/trim) (-> s
                                      (str/lower-case)
                                      (str/replace ", and " ", ")
                                      (str/split #", "))))
