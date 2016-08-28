(ns dejong.core
  (:require 
    [quil.core :as q]
    [quil.middleware :as m]
    [clojure.math.combinatorics :as combo])
    (:gen-class))

(defn sierpinski-triangle 
  [[xa1 ya1 xa2 ya2] [xb1 yb1 xb2 yb2]] 
  [(/ (+ xa1 xa2) 2) (/ (+ ya1 ya2) 2) (/ (+ xb1 xb2) 2) (/ (+ yb1 yb2)  2)])

(def tria1 '([-400 -400 0 400] [0 400 400 -400] [-400 -400 400 -400]))
(def tria2 (map #(sierpinski-triangle (first %1) (second %1)) (combo/combinations tria1 2)))
(def tria3 (sierpinski-triangle [-400 -400 0 200] [-200 -400 0 200]))

(defn draw []
  (q/background 255)
  ; move origin point to centre of the sketch
  ; by default origin is in the left top corner
  (q/with-translation [(/ (q/width) 2) (/ (q/height) 2)]
    (doseq [ [x1 y1 x2 y2] (lazy-cat tria1 tria2 tria3)]
    ;         x    y
    (q/line   x1  (* -1 y1) x2 (* -1 y2) ))))

; run sketch
(defn show-window 
  []
  (q/defsketch trigonometry
    :size [800 800]
    :draw draw))

(defn -main 
  [& args]
  (show-window))
