(ns clicky.db)

(def default-db
  {:name "re-frame"
   :count 0
   :last-tick (str (js/Date.))
   :counts {:people 2
            :food 10
            :wood 10}
   :work {:wood 0
          :food 0}
   })
