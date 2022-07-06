// Author: Guanyi Cao
// Date: December 13, 2020
// ---------------------------------------------------------------

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;


// This class represents the functions and attributes of Evil Hangman.
public class EvilHangman {

    // constants
    private static final String BACKGROUND = "evil hangman.png";
    private static final String FONT_NAME = "Sans Serif";
    private static final int FONT_SIZE = 20;

    // instance vars
    private int wordLength; // length of word as determined by user
    private int numGuesses; // number of guesses as determined by user
    private ArrayList<String> wordList; // list of words to guess from

    private String lettersGuessed; // keeps track of letters guessed
    private String pattern; // the dashes showing the progress of guesses
    private boolean[] isViable; // keeps track if words are viable, or
    // match the current most common word family
    private String blanks; // holds string of all blank dashes


    // initializes word length, number of guesses and wordList as given
    // initializes lettersGuessed to empty string, array isViable to length of
    // wordList, and pattern to blank dashes
    public EvilHangman(int wordLength, int numGuesses,
                       ArrayList<String> wordList) {

        this.wordLength = wordLength;
        this.numGuesses = numGuesses;
        this.wordList = wordList;
        lettersGuessed = "";

        // initializes to wordList length, and all words to viable (true)
        isViable = new boolean[wordList.size()];
        for (int i = 0; i < wordList.size(); i++) {
            isViable[i] = true;
        }

        // creates blanks based on word length
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < wordLength; i++) {
            if (i == wordLength - 1) {
                str.append("_");
            }
            else {
                str.append("_ ");
            }
        }
        pattern = str.toString();
        blanks = pattern;

    }


    // partitions words in word list into word families
    // finds most common word family, and updates isViable array
    // returns most common word family
    public String createWordFamilies(char guess) {
        // vars
        // represents word families and corresponding number of words
        ST<String, Double> wordFamilies = new ST<>();

        // iterate through word list
        for (int i = 0; i < wordList.size(); i++) {
            String wordFamily = blanks; // initializes to string of all blank dashes
            // check if word is viable
            if (isViable[i]) {
                // iterates through each word, creates word family based on
                // guessed letter
                for (int j = 0; j < wordList.get(i).length(); j++) {
                    if (wordList.get(i).charAt(j) == guess) {
                        wordFamily = createPattern(guess, j, wordFamily);
                    }
                }

                // once word family is created, searches symbol table and updates
                // as needed
                if (wordFamilies.contains(wordFamily)) {
                    // updates counter by 1 if word family already exists
                    double counter = wordFamilies.get(wordFamily);
                    wordFamilies.put(wordFamily, counter + 1);
                }
                else {
                    // adds word family to symbol table if it is new
                    wordFamilies.put(wordFamily, 1.0);
                }

            }

        }


        // after going through word list and populating symbol table, determine
        // which word family has highest count (most words)
        double highestCount = Double.NEGATIVE_INFINITY;
        String mostCommonWordFam = "";
        for (String key : wordFamilies.keys()) {
            double temp = wordFamilies.get(key);
            if (temp > highestCount) {
                mostCommonWordFam = key;
                highestCount = temp;
            }
        }

        // based on most common word family, sets all words not viable to false
        int position = 0; // represents corresponding index in word
        for (int i = 0; i < wordList.size(); i++) {
            // first checks if most common word fam is all blanks
            if (mostCommonWordFam.indexOf(guess) == -1) {
                // sets each word containing letter to false
                if (wordList.get(i).indexOf(guess) != -1) {
                    isViable[i] = false;
                }
            }
            else {
                // if most common word fam contains guessed letter,
                // find not-viable words by matching positions of letter
                // in most common word fam to positions in word
                for (int j = 0; j < mostCommonWordFam.length(); j++) {

                    // to ensure indices of pattern and word match,
                    // position is only incremented when index (j) is not 0:

                    if (mostCommonWordFam.charAt(j) == '_' && j > 0) {
                        position++;
                    }

                    if (mostCommonWordFam.charAt(j) == guess && j == 0) {
                        // if letter doesn't occur in same position in word,
                        // word isn't viable!
                        if (wordList.get(i).charAt(position) != guess) {
                            isViable[i] = false;
                        }

                    }

                    if (mostCommonWordFam.charAt(j) == guess && j > 0) {
                        position++;
                        if (wordList.get(i).charAt(position) != guess) {
                            isViable[i] = false;
                        }
                    }

                    // also checks that word doesn't contain letters
                    // previously guessed
                    for (int k = 0; k < lettersGuessed.length(); k++) {
                        if (wordList.get(i).charAt(k)
                                == lettersGuessed.charAt(k)) {
                            isViable[i] = false;
                        }
                    }
                }
                position = 0; // resets position for next word
            }
        }

        return mostCommonWordFam;
    }


    // starting w/ a given pattern,
    // creates new pattern by replacing dash w/ given letter based on its position
    // in a word, and returns new pattern
    public String createPattern(char letter, int position, String givenPattern) {
        // vars
        StringBuilder str = new StringBuilder();
        String newPattern = givenPattern; // initializes newPattern to blanks
        str.append(newPattern); // sets str to newPattern

        int counter = 0; // corresponds to position in word

        // checks if position is first letter, updates accordingly
        if (position == 0) {
            str.setCharAt(position, letter);
            newPattern = str.toString();
        }

        // checks if position is last letter, updates accordingly
        else if (position == wordLength - 1) {
            str.setCharAt(givenPattern.length() - 1, letter);
            newPattern = str.toString();
        }

        // otherwise, search for each dash or letter (indicative of an increase
        // in index) and increase counter when found
        else {
            for (int i = 0; i < givenPattern.length(); i++) {
                char character = givenPattern.charAt(i);

                if (character == '_' || (character >= 'a' && character <= 'z')) {

                    if (i > 0) {
                        counter++;
                    }

                    // if counter (corresponding to position in word) matches
                    // given position, replace dash with letter
                    if (counter == position) {
                        str.setCharAt(i, letter);
                        newPattern = str.toString();
                        break;
                    }
                }
            }
        }

        return newPattern;
    }


    // retrieves pattern
    public String getPattern() {
        return pattern;
    }


    // retrieves letters guessed
    public String getLettersGuessed() {
        return lettersGuessed;
    }


    // retrieves number of guesses
    public int getNumGuesses() {
        return numGuesses;
    }


    // updates pattern if most common word fam contains guessed letter(s)
    public void updatePattern(String mostCommonWordFam) {

        // creates string builder object
        StringBuilder str = new StringBuilder();
        str.append(pattern); // stores pattern in str

        // iterates through most common word fam, searching for letter
        // occurrences, and replacing dashes if there's a new letter
        for (int i = 0; i < mostCommonWordFam.length(); i++) {
            char character = mostCommonWordFam.charAt(i);

            // checks if character at a position is a letter
            if (character >= 'a' && character <= 'z') {
                str.setCharAt(i, character);
            }
        }

        pattern = str.toString();
    }


    // updates letters guessed
    public void updateLettersGuessed(char guess) {
        lettersGuessed += guess;
    }


    // subtracts a guess for wrong guess
    public void subtractGuess() {
        numGuesses--;
    }


    // checks if user’s guesses have run out
    public boolean noMoreGuesses() {
        if (numGuesses == 0) {
            return true;
        }
        else {
            return false;
        }
    }


    // chooses random word from viable words as answer if user runs out of
    // guesses
    public String getRandomAnswer() {
        // vars
        int maxIndex = wordList.size();
        int rand; // random int
        boolean isValid = false;
        String answer = "";

        // generates random index between 0 and last index in word list
        rand = StdRandom.uniform(maxIndex);

        // keeps generating random indices until that random word is viable
        while (!isValid) {
            // determines if word at that index is viable
            if (isViable[rand]) {
                answer = wordList.get(rand);
                isValid = true;
            }
            else {
                // generates another random index
                rand = StdRandom.uniform(maxIndex);
            }
        }

        return answer;
    }


    // checks if there's one viable word left
    public boolean oneWordLeft() {
        // vars
        int count = 0;

        // counts number of viable words in word list
        for (int i = 0; i < isViable.length; i++) {
            if (isViable[i]) {
                count++;
            }
        }

        if (count == 1) {
            return true;
        }
        else {
            return false;
        }
    }


    // if there is one viable word left, returns that word
    public String getViableWord() {

        String word = "";

        // searches for viable word
        for (int i = 0; i < wordList.size(); i++) {
            if (isViable[i]) {
                word = wordList.get(i);
            }
        }

        return word;

    }


    // in the case there is one viable word left, this simulates
    // one guess turn of regular hangman
    public void playRegHangman(String word, char guess) {

        // first check if word contains the letter
        // if not, subtract a guess and update letters guessed
        if (word.indexOf(guess) == -1) {
            numGuesses--;
            updateLettersGuessed(guess);
            System.out.println("\nWrong!");
        }
        // if it does contain letter, searches for position and updates
        // pattern accordingly
        else {
            System.out.println("\nCorrect!");
            updateLettersGuessed(guess);
            for (int i = 0; i < word.length(); i++) {
                if (word.charAt(i) == guess) {
                    // updates pattern
                    pattern = createPattern(guess, i, pattern);
                }
            }
        }
        // shows progress
        showProgress();

    }


    // displays initial screen of blank dashes and number of guesses
    // when user starts game
    public void showInitialScreen() {

        // clears StdDraw, sets up font, font size, and pen color
        StdDraw.clear();
        Font font = new Font(FONT_NAME, Font.BOLD, FONT_SIZE);
        StdDraw.setFont(font);
        StdDraw.setPenColor(Color.BLACK);


        // sets up background and displays dashes and guesses remaining
        StdDraw.picture(0.5, 0.5, BACKGROUND);

        StdDraw.text(0.5, 0.5, pattern);
        StdDraw.text(0.5, 0.25, "Guesses Remaining: " + numGuesses);

        StdDraw.show();
        StdDraw.pause(20);
    }


    // displays user’s progress to StdDraw: guesses remaining, and blanked
    // version of word, and prints letters guessed to terminal
    public void showProgress() {

        // clears StdDraw, sets up font, font size, and pen color
        StdDraw.clear();
        Font font = new Font(FONT_NAME, Font.BOLD, FONT_SIZE);
        StdDraw.setFont(font);
        StdDraw.setPenColor(Color.BLACK);


        // prints letters guessed to terminal
        StdOut.println("Letters guessed so far:");
        // first makes sure lettersGuessed isn’t empty
        if (!lettersGuessed.equals("")) {
            for (int i = 0; i < lettersGuessed.length(); i++) {
                StdOut.println(lettersGuessed.charAt(i));
            }
        }

        // sets up background and displays dashes and guesses remaining
        StdDraw.picture(0.5, 0.5, BACKGROUND);

        StdDraw.text(0.5, 0.5, pattern);
        StdDraw.text(0.5, 0.25, "Guesses Remaining: " + numGuesses);

        StdDraw.show();
        StdDraw.pause(20);

    }


    // displays GAME OVER screen if user lost
    public void showGameOver() {

        // clears StdDraw, sets up font, font size, and pen color
        StdDraw.clear();
        Font font = new Font(FONT_NAME, Font.BOLD, FONT_SIZE);
        StdDraw.setFont(font);
        StdDraw.setPenColor(Color.BLACK);

        // sets background and displays text
        StdDraw.picture(0.5, 0.5, BACKGROUND);

        StdDraw.text(0.5, 0.5, "GAME OVER!");
        // pick a random word to display
        StdDraw.text(0.5, 0.25, "Word was: " + getRandomAnswer());

        StdDraw.show();
        StdDraw.pause(20);
    }


    // displays congratulations screen if user won, along w/ guessed word
    public void displayCongrats(String word) {
        // clears StdDraw, sets up font, font size, and pen color
        StdDraw.clear();
        Font font = new Font(FONT_NAME, Font.BOLD, FONT_SIZE);
        StdDraw.setFont(font);
        StdDraw.setPenColor(Color.BLACK);

        // sets background and displays text
        StdDraw.picture(0.5, 0.5, BACKGROUND);

        // display message
        StdDraw.text(0.5, 0.5, "CONGRATULATIONS! YOU WON");
        StdDraw.text(0.5, 0.25, "Word was: " + word);

        StdDraw.show();
        StdDraw.pause(20);
    }


    // temp helper function that checks contents of isViable array
    public void checkIsViable() {
        for (int i = 0; i < wordList.size(); i++) {
            System.out.println(isViable[i]);
        }
    }


    // this method tests the functions
    public static void main(String[] args) {

        // create a word list of four-letter words for test
        ArrayList<String> wordList = new ArrayList<String>();
        wordList.add("ally");
        wordList.add("beta");
        wordList.add("cool");
        wordList.add("deal");
        wordList.add("else");
        wordList.add("flew");
        wordList.add("good");
        wordList.add("hope");
        wordList.add("ibex");

        // creates evil hangman object with word length 4,
        // 5 guesses, and word list created above
        EvilHangman game = new EvilHangman(4, 5, wordList);

        // tests create pattern function with letter 'e', index 3, and
        // string of blanks: _ _ _ _
        // should return _ _ _ e
        System.out.println("Replace 'e' in last space of _ _ _ _: "
                                   + game.createPattern('e', 3, "_ _ _ _"));

        // tests create pattern function with letter 'e', index 3, and
        // string: _ o _ _
        // should return _ o o _
        System.out.println("Replace 'o' in 3rd space of _ o _ _: "
                                   + game.createPattern('o', 2, "_ o _ _"));

        // tests word families function with guessed letter 'e'
        // should return most common word fam, which is _ _ _ _
        System.out.println("Guess the letter e, most common word fam should be "
                                   + "_ _ _ _ : " + game.createWordFamilies('e'));

        // check that array of viable words are all updated to false
        // except index 0, 2 and 6 (ally, cool, good)
        game.checkIsViable();

        // tests word families function again with guessed letter 'o'
        // should return most common word fam, which is _ o o _
        System.out.println("Guess the letter o, most common word fam should be "
                                   + "_ o o _ : " + game.createWordFamilies('o'));

        // check that array of viable words are all updated to false
        // except index 2 and 6 (cool, good)
        game.checkIsViable();

        // tests word families function again with guessed letter 't'
        // should return most common word fam, which is _ _ _ _
        System.out.println("Guess the letter t, most common word fam should be "
                                   + "_ _ _ _ : " + game.createWordFamilies('t'));

        // check that array of viable words are all updated to false
        // except index 2 and 6 (cool, good)
        game.checkIsViable();

        // tests word families function again with guessed letter 'c'
        // should return most common word fam, which is _ _ _ _
        System.out.println("Guess the letter c, most common word fam should be "
                                   + "_ _ _ _ : " + game.createWordFamilies('c'));

        // check that array of viable words are all updated to false
        // except index 2 (cool)
        game.checkIsViable();

        // check that there is one viable word left (should return true)
        System.out.println("Is there one viable word left? (should be true) "
                                   + game.oneWordLeft());
        // if there is one viable word left, return that word (good)
        if (game.oneWordLeft()) {
            System.out.println("Last viable word is: " + game.getViableWord());
        }

        // tests get random answer; should return "good"
        System.out.println(game.getRandomAnswer());


    }
}
