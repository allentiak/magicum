(ns magicum.utils
  (:gen-class)
  (:require [clojure.string :as str]))

(comment
  (require '[clojure.string :as str]))

(defn subtypes->keywords
  [s]
  "converts a seq of subtypes `Abcd, Bedcu, ..., Serra's Realm, ..., and Zordu` into a seq of keywords"
  (map (comp
        keyword
        #(str/replace % #" " "-")
        str/trim)
       (-> s
           (str/replace ", and " ", ")
           (str/lower-case)
           (str/split #","))))

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
