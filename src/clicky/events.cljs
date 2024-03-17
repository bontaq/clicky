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

(defn get-unused-location [towers]
  (let [open-spots (map first
                        (filter (fn [[index isNil]] isNil)
                                (map-indexed (fn [index val] [index (nil? val)]) towers)))]
    (if (empty? open-spots)
      nil
      (rand-nth open-spots))))

(re-frame/reg-event-db
 :new-tower
 (fn [db _]
   (let [location (get-unused-location (:towers db))
         newTower (mk-new-tower)]
     (if (nil? location)
       db
       (assoc-in db [:towers location] newTower)))))

(defn increment-tower [towerKey tower]
  (if (= towerKey (:key tower))
    (update tower :workers inc)
    tower))

(re-frame/reg-event-db
 :add-worker
 (fn [db [_ towerKey]]
   (let [towers (:towers db)]
     (when (< 0 (:workers db))
       (-> db
           (assoc-in [:towers]
                     (vec (map (fn [tower] (increment-tower towerKey tower)) towers)))
           (update :workers dec))))))
