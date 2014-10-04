(ns mastermind2.staticdata)

(def messages
  {:start "Welcome to a game of Mastermind. \n Press 1 for rules. \n Press 2 for a new game. \n Press 3 for previous game results (if you're just starting, this won't do much) \n Press 4 to exit. \n Happy playing \n"
   :new-game "OK. Here we go. My first guess is \n"
   :exact-matches "How many colors are in the exactly correct position with the code? \n"
   :approx-matches "OK. And how many colors are correct but NOT in the exactly correct position? \n"
   :next-guess "My next guess is \n"
   :final-guess "I think I know the code now. It is \n"
   :error-guess "I think you have made an error somewhere. The previous moves have been as follows. Press the move number to re-enter, and we will re-start from there \n"
   :game-over ["It took me "
               " guesses to get there. I'm sure you could do better. \n Press 1 to start again. \n Press 2 to exit \n"]
   :exit-message ["Oh come on, I could have beat the code at least!"
          "It was fun for me to play with you. Goodbye!"]
   :rules "..... han"
   :count-old-games ["We have played "
                     "games together. Enter the game number you wish to see"]
   :show-old-game ["Here is how I beat the code"]
   :invalid-input "Invalid input. Please try again \n"
   :invalid-format-input "Invalid format of input. Please enter a number \n"})

(def max-init-choice 4)
(def min-init-choice 1)
(def min-guess 0)
(def max-guess 4)

(def color-list [:red :green :yellow :orange :blue :purple])

(def first-guess [:red :red :red :red])

(def all-possible-codes
  (for [a color-list
        b color-list
        c color-list
        d color-list]
    (vector a b c d)))

