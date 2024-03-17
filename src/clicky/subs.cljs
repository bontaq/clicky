(ns clicky.subs
  (:require
   [re-frame.core :as re-frame]))

(re-frame/reg-sub
 ::name
 (fn [db]
   (:name db)))

(re-frame/reg-sub
 ::time
 (fn [db]
   (:last-tick db)))

(re-frame/reg-sub
 ::counts
 (fn [db]
   (:counts db)))

(re-frame/reg-sub
 ::towers
 (fn [db]
   (:towers db)))

(re-frame/reg-sub
 ::workers
 (fn [db]
   (:workers db)))

(re-frame/reg-sub
 ::tower
 (fn [db [_ tower-key]]
   (first (filter (fn [tower] (= tower-key (:key tower))) (:towers db)))))
