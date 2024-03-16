(ns clicky.styles
  (:require-macros
    [garden.def :refer [defcssfn]])
  (:require
    [spade.core   :refer [defglobal defclass]]
    [garden.units :refer [deg px]]
    [garden.color :refer [rgba]]))

(defcssfn linear-gradient
 ([c1 p1 c2 p2]
  [[c1 p1] [c2 p2]])
 ([dir c1 p1 c2 p2]
  [dir [c1 p1] [c2 p2]]))

(defglobal defaults
  [:body
   {:color               :black
    :background-color    :white}])

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
