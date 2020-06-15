(ns magicum.utils
  (:gen-class)
  (:require [clojure.string :as str]))

(defn subtypes->keywords
       [s]
       "converts a seq of subtypes `Abcd, Bedcu, ..., Serra's Realm, ..., and Zordu` into a seq of keywords"
       (map (comp keyword (partial str/replace " " "-") str/trim) (-> s
                                                                    (str/replace ", and " ", ")
                                                                    (str/lower-case)
                                                                    (str/split #","))))
