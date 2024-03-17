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
    {:key (str (random-uuid))
     :height height
     :workers 0
     :type :green}))

(re-frame/reg-event-db
 :new-tower
 (fn [db _]
   ;; TODO: this shouldn't replace an existing tower
   (let [location (rand-int (count (:towers db)))
         newTower (mk-new-tower)]
     (assoc-in db [:towers location] newTower))))

(defn increment-tower [towerKey tower]
  (if (= towerKey (:key tower))
    (update tower :workers inc)
    tower))

(re-frame/reg-event-db
 :add-worker
 (fn [db [_ towerKey]]
   (let [towers (:towers db)]
     (do
       (assoc-in db [:towers]
                 (vec (map (fn [tower] (increment-tower towerKey tower)) towers)))))))
