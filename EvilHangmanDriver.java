import java.util.ArrayList;

// This is the main driver program that runs the Evil Hangman game.
public class EvilHangmanDriver {

    // constants
    public static final String WORD_LIST = "sorteddictionary.txt";
    // determined after filling lengthIndices array and printing
    // array out:
    public static final int NUM_DIFF_WORD_LENGTHS = 24;


    // This is the main function that runs the game.
    public static void main(String[] args) {
        // Reads in sorted dictionary using In, stores in an array list
        In reader = new In(WORD_LIST);
        ArrayList<String> dictionary = new ArrayList<String>();

        while (!reader.isEmpty()) {
            dictionary.add(reader.readString());
        }


        // creates an array that stores indices of when certain word lengths
        // begin in wordList
        int[] lengthIndices = new int[NUM_DIFF_WORD_LENGTHS];
        int counter = 1;
        for (int i = 1; i < dictionary.size(); i++) {
            // first word length is automatically the first index, so starts
            // off looking for index of second word length
            if (dictionary.get(i).length() > dictionary.get(i - 1).length()) {
                lengthIndices[counter] = i;
                counter++;
            }
        }


        // GAME INTRODUCTION:
        // welcome user to game
        System.out.println("Welcome to Evil Hangman! \n"
                                   + "In this upgraded version of "
                                   + "Hangman we will try to cheat and prevent "
                                   + "you from winning as best as we can... "
                                   + "*laughs in mischief* "
                                   + "\nLet's begin!\n");


        // starts game, keeps repeating game while player wants to play
        boolean playAgain = true;
        while (playAgain) {

            // prompt user for word length
            System.out.print("\nChoose how long you want your word to be: ");
            int wordLength = StdIn.readInt();
            // checks if word length is valid, returns valid length when reached
            wordLength = lengthIsValid(wordLength, dictionary, lengthIndices);

            // constructs word list w/ words of desired length from dictionary
            ArrayList<String> wordsOfInputLength = constructWordList(wordLength,
                                                                     lengthIndices,
                                                                     dictionary);

            // prompt user for number of guesses
            System.out.print("\nChoose how many guesses you'd like: ");
            int numGuesses = StdIn.readInt();
            // checks if num guesses is valid
            boolean isValid = false;
            while (!isValid) {
                if (numGuesses > 0) {
                    isValid = true;
                }
                else {
                    System.out.print("Inputted number of guesses is invalid. Please"
                                             + " enter a positive integer: ");
                    numGuesses = StdIn.readInt();
                }
            }

            // simulates one game of evil hangman
            play(wordLength, numGuesses, wordsOfInputLength);

            // checks if player wants to play again
            System.out.print("Play again? Enter Y for Yes, N for No: ");
            String input = StdIn.readString();
            if (input.equalsIgnoreCase("N")) {
                playAgain = false;
            }
        }
        // Thanks player and bids them farewell
        System.out.println("\nHope you had fun! Come again :)");


    }

    // Helper Functions:
    // -----------------------------------------------------------------------

    // This function simulates one game of Evil Hangman
    public static void play(int wordLength, int numGuesses,
                            ArrayList<String> wordList) {

        // creates new EvilHangman game object
        EvilHangman game = new EvilHangman(wordLength, numGuesses,
                                           wordList);

        // if word list is just one word, play regular hangman
        if (wordList.size() == 1) {

            boolean gameIsOver = false;
            String word = wordList.get(0); // retrieves word

            // show initial screen
            game.showInitialScreen();

            while (!gameIsOver) {
                // prompt user for a letter
                System.out.print("\nGuess a letter: ");
                String userInput = StdIn.readString();
                // checks if guess is valid, converts to char once it is
                char userGuess = isValidGuess(userInput,
                                              game.getLettersGuessed());

                // simulates one guess turn in regular hangman
                game.playRegHangman(word, userGuess);

                // check if user has guessed word (pattern has no more dashes)
                String pattern = game.getPattern();
                if (pattern.indexOf('_') == -1) {
                    game.displayCongrats(word);
                    gameIsOver = true;
                }

                // if user has no guesses left, ends game
                if (game.noMoreGuesses()) {
                    game.showGameOver();
                    gameIsOver = true;
                }
            }

        }
        // else if word list has more than one word, play evil hangman
        else {

            boolean gameOver = false;
            // show initial screen
            game.showInitialScreen();

            // initiates loop for the game
            while (!gameOver) {

                // prompts user to guess a letter
                System.out.print("\nGuess a letter: ");
                String input = StdIn.readString();
                // checks if user's guess is valid, converts to char once it is
                char guess = isValidGuess(input, game.getLettersGuessed());

                // creates most common word fam
                String mostCommonWordFam = game.createWordFamilies(guess);

                // if most common word fam doesn't contain guessed letter,
                // subtract a guess, update lettersGuessed
                if (mostCommonWordFam.indexOf(guess) == -1) {
                    game.subtractGuess();
                    game.updateLettersGuessed(guess);
                    System.out.println("\nWrong!");
                }
                // if guess is correct, update pattern and letters guessed
                else {
                    game.updatePattern(mostCommonWordFam);
                    System.out.println("\nCorrect!");
                    game.updateLettersGuessed(guess);
                }

                // shows progress
                game.showProgress();

                // if user has no more guesses, pick a word from list and
                // display as answer.
                if (game.noMoreGuesses()) {
                    game.showGameOver();
                    gameOver = true; // ends game
                }

                // if there is one viable word left, play regular hangman
                if (game.oneWordLeft()) {
                    String word = game.getViableWord();
                    boolean gameIsOver = false;
                    while (!gameIsOver) {
                        System.out.print("\nGuess a letter: ");
                        String userInput = StdIn.readString();
                        char userGuess = isValidGuess(userInput,
                                                      game.getLettersGuessed());

                        // simulates one guess turn in regular hangman
                        game.playRegHangman(word, userGuess);

                        // check if user has guessed word (pattern has no more
                        // dashes)
                        String pattern = game.getPattern();
                        if (pattern.indexOf('_') == -1) {
                            game.displayCongrats(word);
                            gameIsOver = true;
                        }

                        // if user has no guesses left, ends game
                        if (game.noMoreGuesses()) {
                            game.showGameOver();
                            gameIsOver = true;
                        }
                    }
                    gameOver = true; // entire game is over when reg hangman ends
                }

            }
        }

    }

    // determines if input for word length is valid:
    // word length > 0, and there is at least one word of that length
    // keeps reprompting user until a valid length is entered
    // returns valid length
    private static int lengthIsValid(int wordLength,
                                     ArrayList<String> dictionary,
                                     int[] lengthIndices) {
        boolean isValid = false;
        while (!isValid) {
            // first check if word length is positive
            if (wordLength > 0) {
                // retrieves all possible word lengths using indices in
                // lengthIndices
                for (int i = 0; i < lengthIndices.length; i++) {
                    if (dictionary.get(lengthIndices[i]).length() == wordLength) {
                        isValid = true;
                        break;
                    }
                    // if we're at last index and still no length matches
                    if (i == lengthIndices.length - 1) {
                        System.out.print("No words found of that length. "
                                                 + "Please enter a different "
                                                 + "word length: ");
                        wordLength = StdIn.readInt();
                    }
                }
            }
            else {
                System.out.print("Inputted word length is not valid. Please enter "
                                         + "a "
                                         + "positive integer: ");
                wordLength = StdIn.readInt();
            }
        }
        return wordLength;
    }


    // constructs a word list with all words of that length from dictionary
    private static ArrayList<String> constructWordList(int wordLength,
                                                       int[] lengthIndices,
                                                       ArrayList<String> dictionary) {
        int startIndex = 0; // index of word stored in lengthIndices
        int index = 0; // index of lengthIndices
        ArrayList<String> wordList = new ArrayList<String>();

        // iterates through lengthIndices, searching for when a word length
        // starts in word list
        for (int i = 0; i < lengthIndices.length; i++) {
            if (dictionary.get(lengthIndices[i]).length() == wordLength) {
                startIndex = lengthIndices[i];
                index = i;
                break;
            }
        }

        // fills word list; first makes sure word is not last in dictionary
        if (startIndex < dictionary.size() - 1) {
            for (int i = startIndex; i < lengthIndices[index + 1]; i++) {
                wordList.add(dictionary.get(i));
            }
        }
        else {
            wordList.add(dictionary.get(startIndex));
        }

        return wordList;

    }


    // determines if user's guess is valid input
    // if so, returns guess as a char
    private static char isValidGuess(String input, String lettersGuessed) {
        // vars
        boolean isValid = false;
        String message = "Input is invalid. Please enter a single letter: ";
        char guess = ' ';

        // checks if user guess is valid
        // if not, reprompts until user enters valid guess
        while (!isValid) {
            // first check if input is of length 1
            if (input.length() == 1) {

                // converts input to a single char
                guess = input.toLowerCase().charAt(0);

                // check if char is part of alphabet
                if (guess >= 'a' && guess <= 'z') {

                    // check if letter has been guessed yet
                    // first check if letters guessed is empty string
                    if (lettersGuessed == "") {
                        isValid = true;
                    }

                    // iterates through letters guessed
                    for (int i = 0; i < lettersGuessed.length(); i++) {
                        if (guess == lettersGuessed.charAt(i)) {
                            System.out.print("Letter has already been guessed. "
                                                     + "Guess another letter: ");
                            input = StdIn.readString();
                            break; // break out of loop
                        }

                        // if we're at last letter and no duplicates, that
                        // means user's guess hasn't been guessed before
                        if (i == lettersGuessed.length() - 1) {
                            isValid = true;
                        }
                    }

                }

                // prints error message if input isn't part of alphabet
                // reprompts user
                else {
                    System.out.print(message);
                    input = StdIn.readString();
                }
            }
            // prints error message if input isn't of length 1
            // reprompts user
            else {
                System.out.print(message);
                input = StdIn.readString();
            }
        }
        return guess;
    }


}
