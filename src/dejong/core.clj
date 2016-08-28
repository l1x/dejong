(ns dejong.core
  (:require [quil.core :as q]
            [quil.middleware :as m])
    (:gen-class))



(defn sierpinski-triangle 
  [[x y]]
  (let 
    [ x2 (/ x 2)
      y2 (/ y 2) ]
    
    (rand-nth
      [ [     x2          y2        ]
        [ (* -1  x2)      y2        ]
        [ (+  x2 1)       y2        ] ] )))


(defn line
  []
  (for [x (take 100 (iterate inc 0))]
    [x x]))

(def tria1 (for [x (range -400 1)] [x (+ (* 2 x) 400)]))

(def tria2 (for [x (range 0 401)] [x (+ (* -2 x) 400)]))

(def tria3 (for [x (range -400 401)] [x -398]))

(def trian (lazy-cat tria1 tria2 tria3))

(defn s-t [n] (take n (repeatedly #(sierpinski-triangle (rand-nth trian)))))

(defn draw []
  (q/background 255)
  ; move origin point to centre of the sketch
  ; by default origin is in the left top corner
  (q/with-translation [(/ (q/width) 2) (/ (q/height) 2)]
    
    (let [coords (s-t 1000)]
      (doseq [ [x1 y1 x2 y2] '([-400 -400 0 400] [0 400 400 -400] [-400 -400 400 -400]) ]
        ;         x       y
        (q/line   x1       (* -1 y1) x2 (* -1 y2) )))))



; run sketch
(defn show-window 
  []
  (q/defsketch trigonometry
    :size [800 800]
    :draw draw))


(defn -main 
  [& args]
  (show-window))
