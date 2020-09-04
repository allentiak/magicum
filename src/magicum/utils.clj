(ns magicum.utils
  (:gen-class)
  (:require [clojure.string :as str]))

(comment
  (require '[clojure.string :as str]))

(defn remove-first
  [elem coll]
  "removes first occurrence of elem in coll
   Source: https://stackoverflow.com/a/7662696"
  (let [[x xs] (split-with (partial not= elem) coll)]
    (concat x (rest xs))))

(defn subtypes->keywords
  [s]
  "converts a seq of subtypes `Abcd, Bedcu, ..., Serra's Realm, ..., and Zordu` into a seq of keywords"
  (map
   (comp
    keyword
    #(str/replace % #" " "-")
    str/trim
    (-> s
        (str/replace ", and " ", ")
        (str/lower-case)
        (str/split #",")))))

(defn list->keywords
  [s]
  "converts a seq of keywords-abilities `Trample\n    Vigilance' into a seq of keywords"
  (map keyword
       (-> s
           (str/replace "\n" "")
           (str/replace "   " "")
           (str/replace " " "-")
           (str/lower-case)
           (str/split #" "))))
