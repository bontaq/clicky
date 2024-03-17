(ns clicky.events
  (:require
   [re-frame.core :as re-frame]
   [clicky.db :as db]
   ))

(re-frame/reg-event-db
 ::initialize-db
 (fn [_ _]
   db/default-db))

(re-frame/reg-event-db
 :update-last-tick
 (fn [db [_ new-timestamp]]
   (-> db
        (assoc :last-tick new-timestamp)
        (assoc-in [:counts :people] 5)
        )
   ))

(defn mk-new-tower []
  (let [height (+ 35 (rand-int 300))]
    {:height height
     :workers 0
     :type :green}))

(re-frame/reg-event-db
 :new-tower
 (fn [db _]
   (let [location (rand-int (count (:towers db)))
         newTower (mk-new-tower)]
     (assoc-in db [:towers location] newTower))))
