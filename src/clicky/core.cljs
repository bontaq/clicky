(ns clicky.core
  (:require
   [reagent.dom :as rdom]
   [re-frame.core :as re-frame]
   [clicky.events :as events]
   [clicky.views :as views]
   [clicky.config :as config]
   [cljs.core.async :refer [chan go timeout <! close!]]
   ))

(defn dev-setup []
  (when config/debug?
    (println "dev mode")))

(defn ^:dev/after-load mount-root []
  (re-frame/clear-subscription-cache!)
  (let [root-el (.getElementById js/document "app")]
    (rdom/unmount-component-at-node root-el)
    (rdom/render [views/main-panel] root-el)))

(re-frame/reg-event-fx
  ::tick-event
  (fn [_ _]
    ;; Update your application state or perform any side-effects here.
    ;; For example, update a timestamp in the app-db:
    (re-frame/dispatch [:update-last-tick (str (js/Date.))])))

(def stop-ticking (atom nil))

(defn start-ticking! []
  (let [tick-chan (chan)]
    ;; Go block that dispatches the event every second
    (go
      (while true
        (<! (timeout 1000)) ;; Wait for 1 second
        (re-frame/dispatch [::tick-event])))

    ;; Return a stop function that closes the channel when called
    (fn [] (close! tick-chan))))

(defn init []
  (re-frame/dispatch-sync [::events/initialize-db])
  (dev-setup)
  (mount-root)
  (reset! stop-ticking (start-ticking!)))
