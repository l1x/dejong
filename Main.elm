import Html exposing (Html)
import Html.App as Html
import Time exposing (Time, second)
import Debug
import String
import Random exposing (Generator, Seed, generate, list, int, float, initialSeed)
import Array
import Set
import Maybe
import Color exposing (..)
import Graphics.Collage exposing (..)
import Graphics.Element exposing (..)

main =
  Html.program
    { init = init
    , view = view
    , update = update
    , subscriptions = subscriptions
    }

-- MODEL

type alias Model = 
  {time : Time, coordinates : List Coordinates, seed : Random.Seed}

type alias Coordinates = 
  (Float, Float)

init : (Model, Cmd Msg)
init =
  ({time = 0, coordinates = [(649.0, 649.0)], seed = (Random.initialSeed 0) }, Cmd.none)

-- UPDATE

ifs : Coordinates -> Coordinates
ifs coordinates seed =
  let 
        (x, y) = coordinates
        x2 = x / 2
        y2 = y / 2
        listRet = [(x2, y2), ((x2 + 0.5), (y2 + 0.86)), ((x2 + 1), y2)]
        ret = sample listRet seed
        log1 = Debug.log "Coord: " (x2,y2)
  in
     ret

randomint i1 i2 seed = 
  Random.step (Random.int i1 i2) seed

type Msg
  = Tick Time

sample list seed =
  let 
      (v,_) = (randomint 0 (List.length list - 1) seed)
      arr = Array.fromList list
  in
      Array.get v arr

update : Msg -> Model -> (Model, Cmd Msg)
update msg model =
  case msg of
    Tick newTime ->
      let
          newSeed = Random.initialSeed (round newTime)
          randomElement = 
            Maybe.withDefault ("200", "200") (sample model.coordinates newSeed)
          ifs = 
            randomElement
          newCoordinates = model.coordinates ++ [randomElement]
          log1 = Debug.log "Random element: " randomElement 
      in  
         ({time = newTime, coordinates = newCoordinates, seed = newSeed}, Cmd.none)

-- SUBSCRIPTIONS

subscriptions : Model -> Sub Msg
subscriptions model =
  Time.every second Tick

-- VIEW

view : Model -> Html Msg
view model =
  let
      log1 = Debug.log "Time: " model.time
      log2 = Debug.log "Coords: " model.coordinates
      log3 = Debug.log "Seed: " model.seed
      rects = model.coordinates
  in
    svg 
    [ width "700", height "700", viewBox "0 0 700 700" ]
        (rect [ x "50", y "50", width "600", height "600", fill "gray" ] []
        :: List.map (\ coord -> (rect [ x (fst coord), y (snd coord), width "1", height "1", fill "black" ] [])) rects)
