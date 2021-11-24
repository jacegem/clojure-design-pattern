(ns clojure-design-pattern.decorator)


(defn attack-with-bow []
  (println "attack-with-bow"))

(defn attack-with-sword []
  (println "attack-with-sword"))

(defn block-with-shield []
  (println "block-with-shield"))

(def galahad {:name "Galahad"
              :speed 1.0
              :hp 100
              :attack-bow-fn attack-with-bow
              :attack-sword-fn attack-with-sword
              :block-fn block-with-shield})

(defn make-knight-with-more-hp [knight]
  (update-in knight [:hp] + 50))

(defn block-with-power-armor []
  (println "block-with-power-armor"))

(defn make-knight-with-power-armor [knight]
  (update-in knight [:block-fn]
             (fn [block-fn]
               (fn []
                 (block-fn)
                 (block-with-power-armor)))))

;; create the knight
(def superknight (-> galahad
                     make-knight-with-power-armor
                     make-knight-with-more-hp))


superknight

(comment
  {:name "Galahad", :speed 1.0, :hp 150, :attack-bow-fn #function[clojure-design-pattern.decorator/attack-with-bow], :attack-sword-fn #function[clojure-design-pattern.decorator/attack-with-sword], :block-fn #function[clojure-design-pattern.decorator/make-knight-with-power-armor/fn--8696/fn--8697]}
  )

