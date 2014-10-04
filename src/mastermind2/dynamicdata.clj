(ns mastermind2.dynamicdata)

(def past-moves (ref {}))
(def past-games (ref {}))
(def current-move-number (ref 0))
