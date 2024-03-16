(ns clicky.views
  (:require
   [re-frame.core :as re-frame]
   [clicky.styles :as styles]
   [clicky.subs :as subs]
   ["feather-icons" :as feather]
   ))

(def house
  [:span {:dangerouslySetInnerHTML {:__html (.toSvg feather/icons.home)}}])

(def plus
  [:span {:dangerouslySetInnerHTML {:__html (.toSvg feather/icons.plus)}}])

(def tool
  [:span {:dangerouslySetInnerHTML {:__html (.toSvg feather/icons.tool)}}])

(def person
  [:span {:dangerouslySetInnerHTML {:__html (.toSvg feather/icons.meh)}}])

(def food
  [:span {:dangerouslySetInnerHTML {:__html (.toSvg feather/icons.twitter)}}])

(defn buy-house []
  [:div
   [:p "count"]
   [:div plus house]
   [:p "price"]])

(defn shop []
  [:div
   [buy-house]
   ])

(defn assignments []
  [:div
   [:h2 "assignments"]
   ])

(defn main-panel []
  (let
      [time (re-frame/subscribe [::subs/time])
       counts (re-frame/subscribe [::subs/counts])]
    [:div
     [:h3
      {:class (styles/level1)}
      @time]

     [shop]

     [assignments]
     ;; [:h1 "Money: "]
     ;; [:h1 "Houses: "]

     [:h1 "Food: " (:food @counts)]
     (repeat (:food @counts) food)
     [:h1 "Wood: " (:wood @counts)]
     (repeat (:wood @counts) tool)
     [:h1 "People: " (:people @counts)]
     (repeat (:people @counts) person)

     ;; [:h1 "Water: "]
     ;; [:h1 "Furnaces: "]
     ;; [:h1 "Mines: "]

     ]))
