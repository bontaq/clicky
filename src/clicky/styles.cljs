(ns clicky.styles
  (:require-macros
    [garden.def :refer [defcssfn]])
  (:require
    [spade.core   :refer [defglobal defclass defkeyframes]]
    [garden.units :refer [deg px]]
    [garden.color :refer [rgba]]))

(defcssfn linear-gradient
 ([c1 p1 c2 p2]
  [[c1 p1] [c2 p2]])
 ([dir c1 p1 c2 p2]
  [dir [c1 p1] [c2 p2]]))

(defkeyframes bounce []
  [:0%
   {:transform "scale(1, 1)" }]

  [:50%
   {:transform "scale(1, 1.25)"}]

  [:100%
   {:transform "scale(1, 1)"}])

(defglobal defaults
  [:body
   {:color               :black
    :background-color    :white}])

(defclass bounceClass
  []
  {:animation [[(bounce) "250ms" 'ease-in-out]]})

(defclass level1
  []
  {:color :grey})

(defclass feather
  []
  {:width "24px"
   :height "24px"
   :stroke "currentColor"
   :stroke-width "2"
   :stroke-linecap "round"
   :stroke-linejoin "round"
   :fill "none"})

(defclass buy-tower
  []
  [:&
   {:padding "10px"
    :text-align "center"
    :border "1px solid #D9D9D9"
    :border-radius "5px"}
   [:&:hover {:cursor "pointer"}]])

(defclass buy
  []
  [:&
   {:margin "60px"
    :display "flex"
    :justify-content "center"}
   ])

(defclass hover
  []
  [:&
   [:&:hover {:cursor "pointer"}]])
