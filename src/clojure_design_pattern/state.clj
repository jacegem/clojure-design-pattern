(ns clojure-design-pattern.state)

(def user (atom {:name "Jackie Brown"
                 :balance 0
                 :user-state :no-subscription}))

(defmulti news-feed :user-state)

(defmethod news-feed :subscription [user]
  (db/news-feed))


(defmethod news-feed :no-subscription [user]
  (take 10 (db/news-feed)))


(def ^:const SUBSCRIPTION_COST 30)

(defn pay [user amount]
  (swap! user update-in [:balance] + amount)
  (when (and (>= (:balance @user) SUBSCRIPTION_COST)
             (= :no-subscription (:user-state @user)))
    (swap! user assoc :user-state :subscription)
    (swap! user update-in [:balance] - SUBSCRIPTION_COST)))

(news-feed @user) ;; top 10
(pay user 10)
(news-feed @user) ;; top 10
(pay user 25)
(news-feed @user) ;; all news