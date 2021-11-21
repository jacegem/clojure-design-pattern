(ns clojure-design-pattern.flyweight)

(defn make-point [x y]
  [x y {:some "Important Properties"}])

(def CACHE
  (let [cache-keys (for [i (range 100) j (range 100)] [i j])]
    (zipmap cache-keys (map #(apply make-point %) cache-keys))))

(defn make-point-cached [x y]
  (let [result (get CACHE [x y])]
    (if result
      result
      (make-point x y))))



(make-point-cached 1 3)

(def make-point-memoize (memoize make-point))


(defn make-point-cached [x y]
  (let [result (get CACHE [x y])]
    (if result
      result
      (make-point-memoize x y))))


(make-point-cached 1 3)
