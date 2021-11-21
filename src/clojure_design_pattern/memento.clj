(ns clojure-design-pattern.memento)

(def textbox (atom {}))


(defn init-textbox []
  (reset! textbox {:text ""
                   :color :BLACK
                   :width 100}))

(def memento (atom nil))

(defn type-text [text]
  (swap! textbox
         (fn [m]
           (update-in m [:text] (fn [s] (str s text))))))

(defn save []
  (reset! memento (:text @textbox)))

(defn restore []
  (swap! textbox assoc :text @memento))

(init-textbox)
(type-text "'Like A Virgin' ")
(type-text "it's not about this sensitive girl ")
(save)
(type-text "who meets nice fella")
;; crash
(init-textbox)
(restore)