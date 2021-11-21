(ns clojure-design-pattern.strategy
  (:gen-class)
  (:require [clojure.repl :refer [doc]]))

(defn foo
  "I don't do a whole lot."
  [x]
  (println x "Hello, World!"))

(def users [{:name "Bob", :subscription :premium}
            {:name "Alice", :subscription nil}
            {:name "Eve", :subscription :premium}
            {:name "Carol", :subscription nil}
            {:name "Dave", :subscription :premium}
            {:name "Frank", :subscription nil}])

(sort (comparator
       (fn [u1 u2]
         (cond
           (= (:subscription u1)
              (:subscription u2)) (neg? (compare (:name u1)
                                                 (:name u2)))
           (:subscription u1) true
           :else false))) users)

;; forward sort
(sort-by (juxt (complement :subscription) :name) users)

;; reverse sort
(sort-by (juxt :subscription :name) #(compare %2 %1) users)

(comment
  (doc compare))