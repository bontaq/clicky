(ns clicky.views
  (:require
   [re-frame.core :as re-frame]
   [clicky.styles :as styles]
   [clicky.subs :as subs]
   ["feather-icons" :as feather]
   ))

(def house
  [:span {:dangerouslySetInnerHTML {:__html (.toSvg feather/icons.home)}}])

(defn shop []
  [:div
   house
   [:svg {:class (styles/feather)}
    (str feather/icons.home)]
   [:h1 "Buy house"]])

(defn main-panel []
  (let [time (re-frame/subscribe [::subs/time])]
    [:div
     [shop]
     [:h1
      {:class (styles/level1)}
      "Hello from " @time]
     [:h1 "Money: "]
     [:h1 "Houses: "]
     [:h1 "Wood: "]
     [:h1 "People: "]
     [:h1 "Water: "]
     [:h1 "Furnaces: "]
     [:h1 "Mines: "]]))
