(ns clojure-design-pattern.macro)

(defmacro cube-macro [n]
  (list '* n n n))
;; (defmacro cube-macro [n]
;;   (* n n n))

(cube-macro (+ 1 2))


(defmacro aif [test then else]
  `(let [~'it ~test]
     (if `'it ~then ~else)))

(defmacro aand [& body]
  (cond (nil? body) true
        (nil? (rest body)) (first body)
        :else `(aif ~(first body)
                    (aand ~@(rest body)))))


;; '	=> quote
;; ` => Syntax-quote
;; ~ =>	Unquote
;; ~@	=> Unquote-splicing
(def it 33)

(aif (= 1 2)
     (inc it)
     "Nope")
;; 2

(aif 1
     (inc it)
     #(= 200 %))


(aand 1
      (inc it)
      #(* 100 it)
      #(= 200 %))


(aand 1
      (inc it)
      #(* 2 it)
      #(= 5 %))

(defmacro for-loop [[sym init check change :as params] & steps]
  `(loop [~sym ~init value# nil]
     (if ~check
       (let [new-value# (do ~@steps)]
         (recur ~change new-value#))
       value#)))

(for-loop [i 0 (< i 10) (inc i)] (println i))



(doseq [n (range 1 101)]
  (println
   (my-match [(mod n 3) (mod n 5)]
             [0 0] (str "FizzBuzz")
             [0 _] (str "Fizz")
             [_ 0] (str "Buzz")
             :else n)))


