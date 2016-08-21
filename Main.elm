import Color exposing (..)
import Collage exposing (..)
import Element exposing (..)
import Html exposing (..)
import Text exposing (..)

main : Html msg
main =
   toHtml <| collage 700 700
   (filled gray (rect 650 650) :: List.map (\ c -> (move c circ)) ll)

circ =
  (filled black (circle 1))

ll =
  [1..300]
  |> List.filter isEven
  |> List.map (\ c -> (toFloat c,toFloat c))

isEven : Int -> Bool
isEven n =
  n % 2 == 0
