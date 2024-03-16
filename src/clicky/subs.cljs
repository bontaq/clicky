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
