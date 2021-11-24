(ns clojure-design-pattern.chain-of-responsibility)

;; define filters
(defn log-filter [message]
  (logger/log message)
  message)

(defn stats-filter [message]
  (stats/add-used-chars (count message))
  message)

(defn profanity-filter [message]
  (clojure.string/replace message "fuck" "f*ck"))

(defn reject-filter [message]
  (if (.startsWith message "[A Profit NY]")
    message))


(defn chain [message]
  (some-> message
          reject-filter
          log-filter
          stats-filter
          profanity-filter))

(chain "fuck")
(chain "[A Profit NY] fuck")