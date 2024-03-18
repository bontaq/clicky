(ns clicky.views
  (:require
   [re-frame.core :as rf]
   [reagent.core :as r]
   [clicky.styles :as styles]
   [clicky.subs :as subs]
   ["feather-icons" :as feather]
   [garden.core :refer [css]]
   ))

(defn box [height]
  [:svg {:viewBox "0 0 32 28"
         :fill "none"
         :xmlns "http://www.w3.org/2000/svg"
         :preserveAspectRatio "none"
         :width "45px"
         :height height}
   [:rect {:y "4", :width "24", :height "24", :fill "#D9D9D9", :fill-opacity "0.5"}]
   [:path {:d "M8.5 0H31.5L24 4H0L8.5 0Z", :fill "#D9D9D9", :fill-opacity "0.25"}]
   [:path {:d "M24 4L31.5 0V24L24 28V4Z", :fill "#D9D9D9", :fill-opacity "1"}]])

(def animatedBox
  [:div {:class (styles/bounceClass)}
   box])

(def house
  [:span {:dangerouslySetInnerHTML {:__html (.toSvg feather/icons.home)}}])

(def plus
  [:span {:dangerouslySetInnerHTML {:__html (.toSvg feather/icons.plus)}}])

(def minus
  [:span {:dangerouslySetInnerHTML {:__html (.toSvg feather/icons.minus)}}])

(def tool
  [:span {:dangerouslySetInnerHTML {:__html (.toSvg feather/icons.tool)}}])

(def person
  [:span {:dangerouslySetInnerHTML {:__html (.toSvg feather/icons.meh)}}])

(def food
  [:span {:dangerouslySetInnerHTML {:__html (.toSvg feather/icons.twitter)}}])

(def worker-options
  ["👨🏽", "👨", "👩🏽", "👩"])

(defn mk-worker []
  [:div {:style {:margin 5 :font-size "34px"}
         :key (str (random-uuid))}
   (rand-nth worker-options)])

(defn buy-cube []
  [:div {:class (styles/buy-tower)
         :on-click #(rf/dispatch [:new-tower])}
   [:div [box 45]]
   [:p "100"]])

(defn shop []
  [:div {:class (styles/buy)}
   [buy-cube]])

(defn bank []
  (let [counts (rf/subscribe [::subs/counts])]
    [:div
     [:div
      [box 30]
      [:div {:style {:top -10 :left 10 :position "relative" :display "inline-block"}}
       (:grey @counts)]]]))

(defn worker-ctrl [tower]
  [:div {:style {:margin-left 5 :display "flex" :flex-direction "column"}}

   [:div
    {:on-click #(rf/dispatch [:add-worker (:key tower)])
     :class (styles/hover)}
    plus]

   [:div
    {:on-click #(rf/dispatch [:remove-worker (:key tower)])
     :class (styles/hover)}
    minus]

   [:div {:style {:margin-left 5 :font-size 24}} (count (:workers tower))]

   [:div {:style {:position "absolute" :top 526 :margin-left -7
                  :display "flex" :flex-direction "column"}}
    (map (fn [worker] [:div
                      {:style {:font-size "34px" :margin-left 5}}
                      (:emoji worker)])
         (:workers tower))]])


;; so I think here, we'll have it generate and set its own height?
;; clicking will have to assign a worker
(defn cube [tower-key]
  (let [clicked (r/atom false)
        tower (rf/subscribe [::subs/tower tower-key])]
    (r/create-class
     {:reagent-render
      (fn []
        [:div
         [:div {:class (if @clicked (styles/bounceClass) "")
                :style {:display "inline-block"
                        :height (:height @tower)}
                :on-click #(reset! clicked true)}
          (box (:height @tower))]
         [worker-ctrl @tower]
         ])

      :component-did-update
      (fn [this]
        (when (= true @clicked)
          (js/setTimeout #(reset! clicked false) 250)))})))

(defn spacer [key]
  [:div {:style {:width 45}}])

(defn mk-tower [tower]
  (if (nil? tower) [spacer (:key tower)] [cube (:key tower)]))

(defn game []
  (let [towers (rf/subscribe [::subs/towers])]
    [:div {:style {:width "726px"
                   :min-height "512px"
                   :margin "0 auto"
                   :position "relative"
                   :display "flex"
                   :align-items "flex-end"}}
     (map mk-tower @towers)]))

(defn workers []
  (let [workers (rf/subscribe [::subs/workers])]
    [:div
     (map
      (fn [worker] [:div {:style {:margin 5 :font-size "34px"}} (:emoji worker)])
      @workers)]))

(defn main-panel []
  (let
      [time (rf/subscribe [::subs/time])
       counts (rf/subscribe [::subs/counts])]

    [:div
       ;; [:h3
       ;;  {:class (styles/level1)}
       ;;  @time]

       [bank]

       [shop]

       [:div {:style {:display "flex" :align-items "center"}}

        [:div
         [workers]]

        ;; [assignments]
        ;; [:h1 "Money: "]
        ;; [:h1 "Houses: "]
        ;;


        ;; [:h1 "Food: " (:food @counts)]
        ;; (repeat (:food @counts) food)
        ;; [:h1 "Wood: " (:wood @counts)]
        ;; (repeat (:wood @counts) tool)
        ;; [:h1 "People: " (:people @counts)]
        ;; (repeat (:people @counts) person)

        [game]]

       ;; [:h1 "Water: "]
       ;; [:h1 "Furnaces: "]
       ;; [:h1 "Mines: "]

       ]))
