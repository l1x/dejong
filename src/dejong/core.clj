(ns dejong.core
  (:require 
    [quil.core :as q]
    [quil.middleware :as m]
    [clojure.math.combinatorics :as combo])
    (:gen-class))

(defn mean [a b] (/ (+ a b) 2)) ; gets the mean of two numbers

(defn flip [n] (* -1 n)) ;returns the same number with different sign - -> +, + -> -

(defn halve [n] (/ n 2)) ;divides a number by two

(defn s-trian                               ; given a triangle (denoted with 6 integers or 3 coordinates)
  [[x1 y1 x2 y2 x3 y3]]                     ; retrurns 3 triangles, each are placed close to the edges of 
  (list [ x1            y1                  ; the original triangle, the lenght of sides are exactly 50% 
          (mean x1 x2)  (mean y1 y2)        ; of the original triangle
          (mean x1 x3)  (mean y1 y3)  ]

        [ (mean x1 x3)  (mean y1 y3) 
          (mean x2 x3)  (mean y2 y3)
          x3            y3            ]

        [ (mean x1 x2)  (mean y1 y2)
          x2            y2            
          (mean x2 x3)  (mean y2 y3)  ] ))

(defn start-trian                                                 ; creates the initial triangle
  []
  [ (flip (halve (q/width))) (flip (halve (q/height))) 
    0                        (halve (q/height))
    (halve (q/width))        (flip (halve (q/height)))  ])


(defn sierpinski-triangle                                         ; generates the Sierpinski triangle in 2D 
  [init counter]
  (loop [iter 0 acc (s-trian init)]
    (if (> iter counter)
      acc
      (recur (inc iter) (apply concat (map s-trian acc)))))) 

(defn draw []
  (q/frame-rate 1)
  (q/background 255)
  (println (start-trian))
  ; move origin point to centre of the sketch
  ; by default origin is in the left top corner
  (q/with-translation [(/ (q/width) 2) (/ (q/height) 2)]
    (doseq [ [x1 y1 x2 y2 x3 y3] (sierpinski-triangle (start-trian) 6) ]
      (q/triangle x1 (flip y1) x2 (flip y2) x3 (flip y3)))))

; run sketch
(defn show-window 
  []
  (q/defsketch trigonometry
    :settings #(q/smooth 2) 
    :size [1000 800]
    :draw draw))

(defn -main 
  [& args]
  (show-window))
