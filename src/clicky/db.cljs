(ns clicky.db)

(def default-db
  {:name "re-frame"
   :count 0
   :last-tick (str (js/Date.))})
