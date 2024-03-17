(ns clicky.db
  (:require
   [clojure.spec.alpha :as s]))

;; or maybe I just create all the spaces?
;; might make the most sense for rendering

;; yeah so each tile could have a different
;; type of thing on it

(s/def ::height (s/int-in -100 300))
(s/def ::workers nat-int?)
(s/def ::kind (s/and string? #(= "grey" %)))

(s/def ::tower (s/keys :req-un [::height ::workers ::kind]))

;; { emoji, key }
(def worker-options
  ["👨🏽", "👨", "👩🏽", "👩"])

(defn mk-worker []
  {:emoji (rand-nth worker-options)
   :key (str (random-uuid))})

(def default-db
  {:name "re-frame"
   :count 0
   :workers (vec (repeatedly 5 mk-worker))
   :last-tick (str (js/Date.))
   :towers (vec (repeat 10 nil))
   :counts { :grey 100 }
   :work {:wood 0
          :food 0}
   })
