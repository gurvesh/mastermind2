(ns mastermind2.inputoutput)
(require '[mastermind2.staticdata :as static])

(defn try-until [f message]
  "Keep trying until we get the input without errors." 
  (try (f message)
       (catch Exception e
         (println (:invalid-format-input static/messages))
         (try-until f message))))

(defn get-player-input-as-integer [message]
  "Get the input as an integer corresponding to the message"
  (println message)
  (Integer/parseInt (read-line)))

(defn check-bounds-integer [input lower upper]
  "Make sure the input is within bounds, and if so, return the input"
  (and (<= input upper)
       (>= input lower)
       input))

(defn get-choice [message min max]
  "Get the user to make a choice. Check that the choice is an integer, and within bounds"
  (if-let [choice (check-bounds-integer
                   (try-until get-player-input-as-integer
                              message)
                   min
                   max)]
    choice
    (do
      (println (:invalid-input static/messages))
      (recur message min max))))

(defn get-next-move []
  (vector (get-choice (:exact-matches static/messages)
                         static/min-guess
                         static/max-guess)
          (get-choice (:approx-matches static/messages)
                         static/min-guess
                         static/max-guess)))
