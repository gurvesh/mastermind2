(ns mastermind2.core-spec
  (:require [speclj.core :refer :all]
            [mastermind2.core :refer :all]
            [mastermind2.inputoutput :refer :all]))

(describe "inputoutput.clj"
          (around [it]
                  (with-out-str (it)))
          
          (it "tests the try-until function"
              (should= 2
                       (with-in-str "2"
                         (try-until get-player-input-as-integer "test")))
              (should-invoke try-until {:with [get-player-input-as-integer "test"]}
                             (with-in-str "hi"
                               (try-until get-player-input-as-integer "test"))))
          
          (it "tests the check-bounds-integer function"
              (should= false
                       (check-bounds-integer 6 1 4))
              (should= 3
                       (check-bounds-integer 3 1 5)))
          
          (it "tests the get-game-choice function"
              (should= 2
                       (with-in-str "2"
                         (get-choice "test" 1 4)))
              (should-invoke get-choice {:with ["test" 1 4]}
                             (with-in-str "hi"
                               (get-choice "test" 1 4)))
              (should-invoke get-choice {:with ["test" 1 4]}
                             (with-in-str "6"
                               (get-choice "test" 1 4)))))

(describe "core.clj"
          (around [it]
                  (with-out-str (it)))
          
          (it "tests the exact-matches function"
              (should= 1
                       (exact-matches [:r :r :b :y]
                                      [:p :y :b :g]))
              (should= 3
                       (exact-matches [:r :r :b :y]
                                      [:r :p :b :y])))
          
          (it "tests the total-matches function"
              (should= 2
                       (total-matches [:r :g :p :o]
                                      [:r :y :y :p])))

          (it "tests the approx-matches function"
              (should= 1
                       (approx-matches [:r :g :p :o]
                                       [:r :y :y :p])))

          (it "tests the compare-code function"
              (should= [1 2]
                       (compare-code [:y :b :o :p]
                                     [:b :y :g :p])))
          
          (it "tests the filter-possibles function"
              (should= [[:r :g :y] [:r :g :b]]
                       (filter-possibles [[:r :g :y]
                                          [:r :g :b]
                                          [:b :y :o]
                                          [:p :r :g]]
                                         [:r :p :g]
                                         [1 1]))))
