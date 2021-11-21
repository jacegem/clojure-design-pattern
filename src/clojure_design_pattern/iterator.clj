(ns clojure-design-pattern.iterator)

(seq [1 2 3])
(seq (list 4 5 6))
(seq #{7 8 9})
(seq (int-array 3))
(seq "abc")


(deftype RedGreenBlackTree [& elems]
  clojure.lang.Seqable
  (seq [self]
    ;; traverse element in needed order
    ))

(def natural-numbers (iterate inc 1))