(ns clicky.db
  (:require
   [clojure.spec.alpha :as s]))

;; or maybe I just create all the spaces?
;; might make the most sense for rendering

;; yeah so each tile could have a different
;; type of thing on it

(s/def ::height (s/int-in -100 100))
(s/def ::workers nat-int?)
(s/def ::kind (s/and string? #(= "grey" %)))

(s/def ::tower (s/keys :req-un [::height ::workers ::kind]))

(def default-db
  {:name "re-frame"
   :count 0
   :last-tick (str (js/Date.))
   :towers (vec (repeat 10 nil))
   :counts {:people 2
            :food 10
            :wood 10}
   :work {:wood 0
          :food 0}
   })
