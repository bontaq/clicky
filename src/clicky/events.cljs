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
