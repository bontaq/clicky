(ns clicky.events
  (:require
   [re-frame.core :as re-frame]
   [clicky.db :as db]
   ))

(re-frame/reg-event-db
 ::initialize-db
 (fn [_ _]
   db/default-db))

(defn new-tower-height [tower]
  (when (not (nil? tower))
    (if (> 0 (:height tower))
      nil
      (update tower :height
              (fn [height] (- height (count (:workers tower))))))))

(defn get-removed-workers [tower]
  (when (not (nil? tower))
    (if (> 0 (:height tower))
      (:workers tower)
      [])))

(re-frame/reg-event-db
 :update-last-tick
 (fn [db [_ new-timestamp]]
   (let [new-blocks (reduce + (map (fn [tower] (count (:workers tower))) (:towers db)))
         removed-workers (mapcat (fn [tower] (get-removed-workers tower)) (:towers db))]
     (-> db
         (assoc :last-tick new-timestamp)
         (update-in [:counts :grey] (fn [amt] (+ amt new-blocks)))
         (update-in [:towers] (fn [towers] (vec (map
                                                new-tower-height
                                                towers))))
         (update-in [:workers] (fn [workers] (vec (concat workers removed-workers))))
         ))
   ))

(defn mk-new-tower []
  (let [height (+ 35 (rand-int 300))]
    {:key (str (random-uuid))
     :height height
     :workers []
     :type :grey}))

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
       (-> db
           (assoc-in [:towers location] newTower)
           (update-in [:counts :grey] (fn [amt] (- amt 100))))))))

(def worker-options
  ["👨🏽", "👨", "👩🏽", "👩"])

(defn mk-worker []
  {:emoji (rand-nth worker-options)
   :key (random-uuid)})

(defn add-worker-to-tower [worker tower-key tower]
  (if (= tower-key (:key tower))
    (assoc tower :workers (conj (:workers tower) worker))
    tower))

(re-frame/reg-event-db
 :add-worker
 (fn [db [_ tower-key]]
   (when (< 0 (count (:workers db)))
     (let [towers (:towers db)
           worker (first (:workers db))
           new-workers (rest (:workers db))]
       (-> db
           (assoc-in [:towers]
                     (vec (map (fn [tower] (add-worker-to-tower worker tower-key tower)) towers)))
           (assoc :workers new-workers))))))

(defn remove-worker-from-tower [tower-key tower]
  (if (= tower-key (:key tower))
    (assoc tower :workers (rest (:workers tower)))
    tower))

(re-frame/reg-event-db
 :remove-worker
 (fn [db [_ tower-key]]
   (let [towers (:towers db)
         tower (first (filter (fn [tower] (= (:key tower) tower-key)) towers))
         worker (first (:workers tower))]
     (when (not (nil? worker))
       (-> db
           (assoc :towers
                  (vec
                   (map (fn [tower] (remove-worker-from-tower tower-key tower)) towers)))
           (assoc :workers (conj (:workers db) worker)))))))
