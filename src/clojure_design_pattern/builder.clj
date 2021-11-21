(ns clojure-design-pattern.builder
  (:require [clojure.string :refer [join]]))



(defn serialize [m sep] (str (join sep (map (fn [[_ v]] v) m)) "\n"))


(defn make-coffee [name amount water
                   & {:keys [milk sugar cinnamon]
                      :or {milk 0 sugar 0 cinnamon 0}}]
  ;; definition goes here
  (serialize  {:name name
               :amount amount, :water water, :milk milk, :sugar sugar :cinnamon cinnamon} "\n"))


(def input-map {:Name "Ashwani" :Title "Dev"})
(serialize input-map ",")

(make-coffee "Royale Coffee" 15 100
             :milk 10
             :cinnamon 3)

(defn make-coffee
  [& {:keys [name amount water milk sugar cinnamon]
      :or {name "" amount 0 water 0 milk 0 sugar 0 cinnamon 0}}]
  {:pre [(seq name)
         (> amount 0)
         (> water 0)]}
  ;; definition goes here
  )

(make-coffee :name "Royale Coffee"
             :amount -15
             :water 100
             :milk 10
             :cinnamon 3)


;; interface
(defprotocol IStringBuilder
  (append [this s])
  (to-string [this]))

;; implementation
(deftype ClojureStringBuilder [charray ^:volatile-mutable last-pos]
  IStringBuilder
  (append [this s]
    (let [cs (char-array s)]
      (doseq [i (range (count cs))]
        (aset charray (+ last-pos i) (aget cs i))))
    (set! last-pos (+ last-pos (count s))))
  (to-string [this] (apply str (take last-pos charray))))

;; clojure binding
(defn new-string-builder []
  (ClojureStringBuilder. (char-array 100) 0))

;; usage
(def sb (new-string-builder))
(append sb "Toby Wong")
(to-string sb) 
(append sb " ")
(append sb "Toby Chung")