(ns mastermind2.core)
(require '[mastermind2.inputoutput :as io]
         '[mastermind2.staticdata :as static]
         '[mastermind2.dynamicdata :as dynamic])

(declare start-new-game
         next-move
         end-current-game
         fix-error
         filter-possibles)

(defn -main []
  (let [init-choice (io/get-choice (:start static/messages)
                                   static/min-init-choice
                                   static/max-init-choice)]
    (cond
     (= 2 init-choice) (start-new-game)
     :else nil)))

(defn start-new-game []
  (println (:new-game static/messages))
  (println static/first-guess)
  (dosync
   (ref-set dynamic/past-moves {:1 [static/all-possible-codes 
                                    (io/get-next-move)]})
   (ref-set dynamic/current-move-number 1))
  (next-move static/first-guess)
  nil)

(defn next-move [code]
  (let [current-key (keyword (str @dynamic/current-move-number))
        number-exact-matches (first
                              (second
                               (current-key @dynamic/past-moves)))
        number-approx-matches (second
                               (second
                                (current-key @dynamic/past-moves)))
        remaining-possible-codes (filter-possibles
                                  (first
                                   (current-key @dynamic/past-moves))
                                  code
                                  [number-exact-matches
                                   number-approx-matches])]
    (cond 
     (= 0 (count remaining-possible-codes)) (fix-error)
     (= number-exact-matches 4) (end-current-game @dynamic/current-move-number)
     :else (do
             (println (first remaining-possible-codes))
             (dosync
              (alter dynamic/current-move-number inc)
              (alter dynamic/past-moves
                     assoc
                     (keyword (str @dynamic/current-move-number))
                     [remaining-possible-codes
                      (io/get-next-move)]))
             (recur (first remaining-possible-codes))))))

(defn end-current-game [move-number]
  (io/get-choice (str (first (:game-over static/messages))
                      move-number
                      (second (:game-over static/messages)))
                 1 2)
  nil)

(defn fix-error []
  (println (:error-guess static/messages))
  (println  (reverse (for [a (keys @dynamic/past-moves)]
                       (str (first
                             (first
                              (a @dynamic/past-moves)))
                            (second
                             (a @dynamic/past-moves))))))
  (dosync (ref-set dynamic/current-move-number 
                   (- (io/get-choice "Which one?"
                                     1
                                     @dynamic/current-move-number)
                      1)))
  (if (= 0 @dynamic/current-move-number) 
    (start-new-game)
    (next-move (first
                (first
                 ((keyword (str @dynamic/current-move-number))
                  @dynamic/past-moves))))))

(defn exact-matches [code test]
  (->> (map = code test)
       (filter true?)
       (count)))

(defn total-matches [code test]
  (->> (merge-with min 
                   (select-keys (frequencies code) test)
                   (select-keys (frequencies test) code))
       (vals)
       (apply +)))

(defn approx-matches [code test]
  (- (total-matches code test)
     (exact-matches code test)))

(defn compare-code [code test]
  (vector (exact-matches code test)
          (approx-matches code test)))

(defn filter-possibles [possibles code result]
  (filter #(= result 
              (compare-code code %))
          possibles))
