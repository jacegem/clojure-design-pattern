(ns clojure-design-pattern.template-method)

(defn move-to [character location]
  (cond
    (quest? location)
    (journal/add-quest (:quest location))

    (chest? location)
    (handle-chest (:chest location))

    (enemies? location)
    (attack (:enemies location)))
  (move-to character (:next-location location)))


;; Mage-specific actions
(defn mage-handle-chest [chest])

(defn mage-attack [enemies]
  (if (> (count enemies) 10)
    (do (cast-spell "Freeze Nova")
        (cast-spell "Teleport"))
    ;; otherwise
    (doseq [e enemies]
      (cast-spell "Fireball" e))))

;; Signature of move-to will change to
(defn move-to [character location
               & {:keys [handle-chest attack]
                  :or {handle-chest (fn [chest])
                       attack (fn [enemies] (run-away))}}]
  (cond
    (quest? location)
    (journal/add-quest (:quest location))

    (chest? location)
    (handle-chest (:chest location))

    (enemies? location)
    (attack (:enemies location)))
  (move-to character (:next-location location)))

(move-to character location
         :handle-chest mage-handle-chest
         :attack       mage-attack)

(defn mage-move [character location]
  (move-to character location
           :handle-chest mage-handle-chest
           :attack       mage-attack))


(defmulti move
  (fn [character location] (:class character)))

(defmethod move :mage [character location]
  (move-to character location
           :handle-chest mage-handle-chest
           :attack       mage-attack))