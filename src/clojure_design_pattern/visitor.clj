(ns clojure-design-pattern.visitor)

;; Message item
(def msg-item
  {:type :message :content "Say what again!"})

;; Activity item
(def act-item
  {:type :activity :content "Quoting Ezekiel 25:17"})
(defmulti export
  (fn [item format] [(:type item) format]))

(defmethod export [:activity :pdf] [item format]
  (println (str item format)))
  ;; (exporter/activity->pdf item))

(defmethod export [:activity :xml] [item format]
  (str item format))
  ;; (exporter/activity->xml item))

(defmethod export [:message :pdf] [item format]
  (str item format))
  ;; (exporter/message->pdf item))

(defmethod export [:message :xml] [item format]
  (str item format))
  ;; (exporter/message->xml item))

(defmethod export :default [item format]
  (throw (IllegalArgumentException. "not supported")))


(export msg-item :pdf)
(export act-item :xml)

(derive ::pdf ::format)
(derive ::xml ::format)
(derive ::csv ::format)


(defmethod export [:activity ::pdf] [item format]
  (str item format ::pdf))
(defmethod export [:activity ::xml])
(defmethod export [:activity ::format] [item  format]
  (str item format ::format))


(export act-item ::csv)




