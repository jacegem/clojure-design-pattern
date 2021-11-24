(ns clojure-design-pattern.proxy)

;; interface
(defprotocol IBar
  (make-drink [this drink]))

;; Bart's implementation
(deftype StandardBar []
  IBar
  (make-drink [this drink]
    (println "Making drink " drink)
    :ok))


(defn subtract-ingredients [db drink]
  (println "subtract-ingredients" db drink))
;; our implementation
(deftype ProxiedBar [db ibar]
  IBar
  (make-drink [this drink]
    (make-drink ibar drink)
    (subtract-ingredients db drink)
    :ok))

;; this how it was before
(make-drink (StandardBar.)
            {:name "Manhattan"
             :ingredients [["Bourbon" 75] ["Sweet Vermouth" 25] ["Angostura" 5]]})

;; this how it becomes now
(make-drink (ProxiedBar. {:db 1} (StandardBar.))
            {:name "Manhattan"
             :ingredients [["Bourbon" 75] ["Sweet Vermouth" 25] ["Angostura" 5]]})