(ns clicky.views
  (:require
   [re-frame.core :as re-frame]
   [clicky.styles :as styles]
   [clicky.subs :as subs]
   ))

(defn main-panel []
  (let [name (re-frame/subscribe [::subs/name])]
    [:div
     [:h1
      {:class (styles/level1)}
      "Hello from " @name]
     [:h1 "Money: "]
     [:h1 "Houses: "]
     [:h1 "People: "]
     [:h1 "Water: "]
     [:h1 "Furnaces: "]
     [:h1 "Mines: "]]))
