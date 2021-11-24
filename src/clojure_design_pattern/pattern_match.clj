(ns clojure-design-pattern.pattern-match)


(defn process-vars
  [vars]
  (letfn [(process-var [var]
            (if-not (symbol? var)
              (gensym "ocr-")
              var))]
    (vec (map process-var vars))))

(defn make-default-match [vars cs]
  (let [cs (partition 2 cs)
        [p a] (last cs) ;; 심볼의 경우 p를 a에 바인딩하는 기능 추가 필요.
        last-match (vec (repeat (count vars) '_))]
    (if (= p :else)
      (conj (vec (butlast cs)) [last-match a])
      (throw (RuntimeException. "last match must be :else")))))

(defn make-pattern-let-binding
  "let 바인딩을 위한 자료구조 생성"
  [vs vars]
  (interleave vs vars))

(defn make-cond
  "cond predicate을 만들기 위한 비교문"
  [vs cls]
  (map (fn [v c]
         `(= ~v ~c)) vs cls))

(def backtrack-exception (Exception. "BackTrack!"))

(defn catch-error
  "예외를 잡는 자료구조 추가"
  [& body]
  `(catch Exception e#
     (if (identical? e# ~'backtrack-exception)
       (do
         ~@body)
       (throw e#))))

(defn compile-rec
  "재귀적으로 try문 안에 있는 비교문을 생성."
  [cnds return]
  (let [cnd (first cnds)
        [v c] (vec (rest cnd))] ;; c가 심볼인 경우 v를 바인딩하도록 해야함.
    (if (seq cnd)
      (cond
        (symbol? c)
        `(let [~c ~v] (do ~(compile-rec (rest cnds) return)))

        (= '_ c)
        `(do ~(compile-rec (rest cnds) return))

        :else
        `(do (cond ~cnd ~(compile-rec (rest cnds) return)
                   :else ~'(throw backtrack-exception))))
      return)))

(defn match-compile
  [conds+return]
  (let [[cnds return] (first conds+return)
        cnd (first cnds)
        [v c] (vec (rest cnd))] ;; c가 심볼인 경우 v를 바인딩하도록 해야함.
    (if (seq cnd)
      (cond
        (symbol? c)
        `(let [~c ~v]
           (try ~(compile-rec (rest cnds) return)
                ~(catch-error (match-compile (rest conds+return)))))

        (= '_ c)
        `(try ~(compile-rec (rest cnds) return)
              ~(catch-error (match-compile (rest conds+return))))

        :else
        `(try (cond ~cnd ~(compile-rec (rest cnds) return)
                    :else ~'(throw backtrack-exception))
              ~(catch-error (match-compile (rest conds+return)))))
      return)))

(defmacro my-match
  [vars & clauses]
  (let [vs (process-vars vars)
        cs (make-default-match vars clauses)
        pattern-let-binding (vec (make-pattern-let-binding vs vars))
        conds (map (fn [c] [(make-cond vs (first c)) (second c)]) cs)]
    `(let ~pattern-let-binding
       ~(match-compile conds))))



(doseq [n (range 1 101)]
  (println
   (my-match [(mod n 3) (mod n 5)]
             [0 0] (str "FizzBuzz")
             [0 _] (str "Fizz")
             [_ 0] (str "Buzz")
             :else n)))



(doseq [n (range 1 101)]
  (println
   (my-match [(mod n 3) (mod n 5)]
             [0 0] (str "FizzBuzz with n=" n)
             [0 a] (str "Fizz with a=" a ", n=" n)
             [b 0] (str "Buzz with b=" b ", n=" n)
             :else n)))
;; 1