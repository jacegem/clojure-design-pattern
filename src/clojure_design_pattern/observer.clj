(ns clojure-design-pattern.observer)

;; Tracker
(def observers (atom #{}))

(defn add [observer]
  (swap! observers conj observer))

(defn notify [user]
  (map #(apply % user) @observers))

;; Fill Observers
(add (fn [u] (mail-service/send-to-fbi u)))
(add (fn [u] (db/block-user u)))

;; User
(defn add-money [user amount]
  (swap! user
         (fn [m]
           (update-in m [:balance] + amount)))
  ;; tracking
  (if (> amount 100) (notify)))


(add-watch
 user
 :money-tracker
 (fn [k r os ns]
   (if (< 100 (- (:balance ns) (:balance os)))
     (notify))))