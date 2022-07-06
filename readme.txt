COS126 Final Project: Implementation

Please complete the following questions and upload this readme.txt to the
TigerFile assignment for the "Final Project Implementation".


/**********************************************************************
 * Basic Information                                                  *
 **********************************************************************/

Name 1: Guanyi Cao

NetID 1: gc2360

Name 2:

NetID 2:

Project preceptor name: Esin Tureci

Project title: Evil Hangman

CodePost link for proposal feedback: https://codepost.io/code/488231

Link to project video: https://www.loom.com/share/297f1fe5a68d43ed976b37dc1b4a0ae6

Approximate number of hours to complete the final project
Number of hours: 10+

/**********************************************************************
 * Required Questions                                                 *
 **********************************************************************/

Describe your project in a few sentences.
Instead of a predetermined word, Evil Hangman seeks to cheat the user
by determining the word based on the user's guesses. This is done by
partitioning the word list into "word families" based on each guess, 
which gradually narrows down the list of words. 
The game has a fixed word length, and the user gets to decide how many
guesses they'd like.




Describe in detail your three features and where in your program
they are implemented (e.g. starting and ending line numbers,
function names, etc.).
1. The first feature accepts user input and determines if it is valid 
(when warranted).

There are 3 instances of user input that require checking if it's valid: 
when 
- prompting for a word length (line 53 in EvilHangmanDriver)
- prompting for number of guesses (line 64 in EvilHangmanDriver)
- prompting for guesses (first instances found on line 117 and 152 in 
  EvilHangmanDriver). 

The last instance of user input happens in the end, when asking the player
if they want to play again. This is found on line 84 in EvilHangmanDriver.

Word length input is checked with the helper function lengthIsValid() on
line 224 in EvilHangmanDriver. The length must be an integer greater than 0, 
and a word of that length must exist in the word list.
Number of guesses is checked in a loop w/ a conditional (must be > 0) on 
line 68 in EvilHangmanDriver;
Input for guesses is checked with the helper function isValidGuess() on
line 293 in EvilHangmanDriver. The guess must be a single letter that
has not been guessed before.




2. The second feature displays output to both the terminal and standard
draw. 
The first instance of output is at the start of the game, right after the
user has determined word length and number of guesses. A standard draw
window appears displaying blanks (based on word length) and number of 
guesses remaining (which have not been used yet). This is found on 
line 113 and line 146 in EvilHangmanDriver; this is a method from 
EvilHangman class, found on line 366.

The second instance of output is printing to terminal whether a guess
is correct or incorrect. 
If the guess is correct, the program will print "Correct!" to the terminal, 
the letters guessed will be updated and printed to terminal, the blanks on 
standard draw will be updated, and the number of guesses remains fixed.
If the guess is incorrect, the program will print "Wrong!" to the terminal,
the letters guessed will be updated and printed to terminal, the blanks
on standard draw remains fixed, and the number of guesses will be updated.
The above is done with the showProgress() method, found on line 176 in 
EvilHangmanDriver, and line 388 in EvilHangman class.

The next instance of output is when a user wins the game. Standard draw
will be updated to "Congratulations! You Won!" with the word guessed right
beneath it. This is done with the displayCongrats() method, found on line
202 in EvilHangmanDriver, and line 440 in EvilHangman class. The game is then
over, and based on whether a user wants to continue playing, the program will
either continue the game or print "Hope you had fun! Come again :)" to 
terminal (found on line 90 in EvilHangmanDriver).

The final instance of output is when a user loses the game. Standard draw
will be updated to "GAME OVER" with the word underneath it. This is done 
with the showGameOver() method, found on line 135, 181, 208 in EvilHangmanDriver
and line 419 in EvilHangman class.The game is then over, and based on whether a 
user wants to continue playing, the program will either continue the game or 
print "Hope you had fun! Come again :)" to terminal (found on line 90 in 
EvilHangmanDriver).



3. The last feature is the partitioning of words into word families, which 
is done in the method createWordFamilies() found on line 61 in EvilHangman class
and line 158 in EvilHangmanDriver. 
Using a list of 'viable' words of the chosen word length, this method will sort the words 
into word families based on the guessed letter, then choose the word family that
contains the most words and eliminate words not in that word family.

As an example, say the word list contains: bell bird wolf (all of length 4)
If the user guesses b, the word families are: 
b _ _ _ , containing bell and bird
_ _ _ _ , containing wolf. 
In this case, the most common word family is b _ _ _, so wolf is eliminated,
becoming not "viable" and set to false. bell and bird are still 'viable', and 
stays true. 

For each new word family, it is added to the symbol table (key) with count 
(value) 1. For each existing word family, (key exists) the
count is incremented  by 1.

Based on the most common word family, if it contains the guessed letter, the user will
be correct. If it does not, the user's guess is wrong. The current pattern on the
screen will be updated accordingly.

The process continues with the next guess from the user. 




Describe in detail how to compile and run your program. Include a few example
run commands and the expected results of running your program. For non-textual
outputs (e.g. graphical or auditory), feel free to describe in words what the
output should be or reference output files (e.g. images, audio files) of the
expected output.

To play the game, 
first compile the program: javac-introcs EvilHangmanDriver.java
then run the game: java-introcs EvilHangmanDriver

The program will display words of greeting in the terminal, then
prompt the user for a desired word length. If the input is valid,
the program will continue to prompt for desired number of guesses.

If inputted number of guesses is valid, then the the program will move on 
to asking the user for a guess. At this time, standard draw will be displayed, 
with all blank dashes and showing the number of guesses remaining.

Upon confirming the validity of the guess, the program will utilize
the guessed letter to determine the most common word family. Once that 
is determined, if the most common word family contains the guessed letter,
then the pattern will be updated in standard draw and the user will be
proclaimed "Correct!". The letter guessed will be added to letters guessed and 
displayed to terminal.
If the most common word family doesn't contain the guessed letter, the user
is proclaimed "Wrong!" and the letters guessed and standard draw will be
updated accordingly and displayed.

This will continue until the user runs out of guesses, or the the user wins. 
Then, standard draw will be updated accordingly, the game loop will be ended,
and the user will be prompted if they want to play again.

If the user inputs "Y" or "y," then the game will restart.





Describe how your program accepts user input and mention the line number(s) at
which your program accepts user input.
The program user accepts user input using standard input. This can be found at 
lines 53, 64, 117, 152 in EvilHangmanDriver.
For numbers, I used StdIn.readInt(); otherwise I used StdIn.readString().




Describe how your program produces output based on user input (mention line
numbers).
Output is produced both through messages in the terminal (using System.out.print/println)
and standard draw. 

System.out.print/ln is used when printing greetings/farewell messages, 
prompting user for word length, number of guesses, and a guess, and reprompts as needed. 
It is also used for prompting player if they want to play again, and 
error messages for invalid input.
These occur on lines 40, 53, 64, 73, 83, 90, 117, 152, 166, 171, 190, 240, 248, 
320, 338, 345 in EvilHangmanDriver. 

They also occur in EvilHangman: 
- in the playRegHangman method on lines 353 and 358

Standard output is used to print the letters guessed so far into the
terminal based on user's guesses. This is found in the showProgress() method
in EvilHangman class, on lines 407 and 411.

Standard draw is used to display dashes, update them, and display/update
number of guesses remaining. It also displays a congratulatory screen
if the user wins or a game over screen when the user loses. 
These are found in the 
showProgress() (line 397), 
showGameOver() (line 428)
displayCongrats() (line 449)
and showInitialScreen() (line 375) methods in EvilHangman class.




Describe the data structure your program uses and how it supports your program's
functionality (include the variable name and the line number(s) at which it is
declared and initialized).

I used both arrays and symbol tables. 

The first instance of an array is lengthIndices, found on line 26 in 
EvilHangmanDriver. This array stores the indices of the first occurrence of a 
word length in the word list. 
For instance, lengthIndices[0] stores index 0, which points to the first word
in word list, which is automatically the first word length. lengthIndices[1] 
stores index 94, which points to the first occurrence of the second word length
in word list. This array is useful for determining a word list containing all
words of the determined length; instead of iterating through the word list 
to find words of a length, using this array saves a lot of time. 

The second instance of an array is the boolean array isViable, found on
line 21 in EvilHangman class.This is an instance variable, because it is
accessed in multiple methods within the EvilHangman class. The isViable 
array stores boolean values with its indices corresponding to each word in wordList, 
(which is the list containing all words of the determined length). If a word is 
'viable' or true, then it means that it matches the current most common word
family. If it is not 'viable,' then it doesn't match the current most
common word family and must be eliminated or set to false.

The symbol table is used in the createWordFamilies method, found on line 61
in the EvilHangman class. The symbol table represents a word family (string) and
its corresponding count (how many words are in that family). Each time
a word family is created, it is added to the symbol table as a key, and
count is set to 1. Each time a word is found to belong to an existing
word family, the count of that word family increases by one. The symbol
table is integral to finding the most common word family, and making 
sure there are no duplicates in word families.




List the two custom functions written by your project group, including function
signatures and line numbers; if your project group wrote more than two custom
functions, choose the two functions that were most extensively tested.
1. public String createWordFamilies(char guess), line 61 in EvilHangman class

2. public String createPattern(char letter, int position, String givenPattern) 
line 167 in EvilHangman class



List the line numbers where you test each of your two custom functions twice.
For each of the four tests (two for each function), explain what was being
tested and the expected result. For non-textual results (e.g. graphical or
auditory), you may describe in your own words what the expected result
should be or reference output files (e.g. images, audio files).

These two functions were tested in main() in EvilHangman class. For the
sake of predicting the outcome, I used a restricted word list of just
four letter words to ensure both methods were working properly.

1. line 497: tests createPattern with the letter 'e', index 3, and
string of blanks: _ _ _ _. The function should replace index 3, the
last letter in a four-letter word, with 'e.' In other words, it should
return _ _ _ e as the new pattern.

2. line 503: tests createPattern, but this time with letter 'e', index 2, 
and _ o _ _ . This is different from test 1, as it is making sure the 
function takes note of the existing letter 'o' when matching up the 
pattern position with the word index (since pattern also consists of 
spaces, while a word does not). This should return _ o o _

3. line 508: tests createWordFamilies with guessed letter 'e'. 
This is the first run, and out of all 9 words in the word list, the most
common word family does not contain the letter 'e', meaning this should return
_ _ _ _ . 
line 513 is another test function, making sure the isViable
array was updated correctly. The only viable words should be words
that don't contain 'e', meaning index 0, 2 and 6 (ally, cool, good). Result
should be: 

true
false
true
false
false
false
true
false
false
false



4. line 517: continues testing createWordFamilies by proceeding with
the guesses. The second guess is the letter 'o'. The current
viable words are ally, cool, and good, which were the previously most common
word family. With the new guess, 'o', the word families will be 
_ _ _ _ containing ally
_ o o _ , containing cool and good. Thus, the most common word family is
_ o o _ , which is what the function should return. 

line 522: using helper function checkIsViable, makes sure isViable was
updated correctly. The only viable words now should be cool and good, 
index 2, and 6. Result should be: 

false
false
true
false
false
false
true
false
false
false


/**********************************************************************
 * Citing Resources                                                   *
 **********************************************************************/

List below *EVERY* resource your project group looked at (bullet lists and
links suffice).

Initially, dictionary.txt was not sorted by word length. Using info found from: 
https://stackoverflow.com/questions/5917576/sort-a-text-file-by-line-length-including-spaces

I used the command 

cat dictionary.txt | awk '{ print length, $0 }' | sort -n -s | cut -d" " -f2-  > sorteddictionary.txt

to sort dictionary.txt based on word length and stored it in sorteddictionary.txt, 
which became the file I used for the program.


In implementing the program, provided by my preceptor on codePost, 
I utilized this document explaining the mechanics of the game as a guide: 
http://nifty.stanford.edu/2011/schwarz-evil-hangman/Evil_Hangman.pdf


Additionally, not a resource necessarily, but image of a minion
on standard draw is taken from: 
https://play.google.com/store/apps/details?id=com.mitchellclements.testd&hl=en_US&gl=US






Remember that you should *ALSO* be citing every resource that informed your
code at/near the line(s) of code that it informed.

Did you receive help from classmates, past COS 126 students, or anyone else?
If so, please list their names.  ("A Sunday lab TA" or "Office hours on
Thursday" is ok if you don't know their name.)
Yes or no?

Yes. Office hours with Dr. Singh on Sunday and Dr. Tureci on Thursday.



Did you encounter any serious problems? If so, please describe.
Yes or no?
No



List any other comments here.




/**********************************************************************
 * Submission Checklist                                               *
 **********************************************************************/

Please mark that you’ve done all of the following steps:
[ ] Created a project.zip file, unzipped its contents, and checked that our
    compile and run commands work on the unzipped contents. Ensure that the .zip
    file is under 50MB in size.
[ ] Created and uploaded a Loom or YouTube video, set its thumbnail/starting
    frame to be an image of your program or a title slide, and checked that
    the video is viewable in an incognito browser.
[ ] Uploaded all .java files to TigerFile.
[ ] Uploaded project.zip file to TigerFile.

After you’ve submitted the above on TigerFile, remember to do the following:
[ ] Complete and upload readme.txt to TigerFile.
[ ] Complete and submit Google Form
    (https://forms.cs50.io/de2ccd26-d643-4b8a-8eaa-417487ba29ab).


/**********************************************************************
 * Partial Credit: Bug Report(s)                                      *
 **********************************************************************/

For partial credit for buggy features, you may include a bug report for at
most 4 bugs that your project group was not able to fix before the submission
deadline. For each bug report, copy and paste the following questions and
answer them in full. Your bug report should be detailed enough for the grader
to reproduce the bug. Note: if your code appears bug-free, you should not
submit any bug reports.

BUG REPORT #1:
1. Describe in a sentence or two the bug.
When the user runs out of guesses, the program will select a random word
from list of 'viable' words to be the word the computer initially "chose."

However, during some games this word will contain letters previously guessed
that were "wrong" guesses or "correct" guesses. I have since fixed the issue of
previous "wrong guesses" appearing in the final word, but was not able
to implement a solution in time for the issue of  previous "correct" guesses 
appearing multiple times when it should appear less than that.




2. Describe in detail how to reproduce the bug (e.g. run commands, user input,
   etc.)

Compile: javac-introcs EvilHangmanDriver.java
Run: java-introcs EvilHangmanDriver

This is an example of the issue: 

enter word length 10 when prompted
enter 10 for number of guesses
guess the letters: 
a e i o n g m s f w r c p
This should make you run out of guesses and "lose." 
The dashes displayed at the time of losing should be: 
_ _ _ _ _ _ _ _ _ i n g
The final word in the end won't include guesses you haven't guessed 
before or any "wrong" guesses (I've fixed that), but it will include multiple 
instances of 'i' or 'n' or 'g' when there shouldn't.



3. Describe the resulting effect of bug and provide evidence (e.g.
   copy-and-paste the buggy output, reference screenshot files and/or buggy
   output files, include a Loom video of reproducing and showing the effects of
   the bug, etc.)

There is no big, disturbing effect from this bug - the final word is simply
not quite the expected one.

i.e. in one game I played, 
the pattern displayed when user runs out of guesses is: 
_ _ _ _ _ _ i n g

and my "wrong" guesses were: a e o m s f w r c p
and my "correct" guesses were: i n g

And the word was 'inhibiting,' which is not quite right because
'i' and 'n' should not be appearing more than once.



4. Describe where in your program code you believe the bug occurs (e.g. line
   numbers).

Since the generation of the random word depends on whether the word is 
viable, I think the error might've occurred in the updating of the 
viable array, which takes place within the createWordFamilies() method, 
lines 109 - 158 in EvilHangman class. This is odd, since it worked
fine when I was testing createWordFamilies(), but perhaps that was
due to my restricting the word list when testing. (I was testing
using the main() method in Evil Hangman class, as described in the
tests above).



5. Please describe what steps you tried to fix the bug.
I tried to add a loop ensuring each word in word list doesn't contain
letters previously guessed, and if it does, making that word not
viable (setting it to false). This is implemented in lines 149 - 154 in 
EvilHangman class. 
I did not have time to fix the other component of the bug,
which is accounting for words that have multiple of the same letters. 

But if I were to implement this, I would probably iterate through each 
word in the word list by converting them to a pattern where all but the 
guessed letter is converted to a dash. I would match this with the pattern
displayed on the screen, and if it doesn't match, then set that word
to not viable using the isViable boolean array.

i.e. the guessed letter was 'i', the word currently being iterated
through was indigo, and indigo would be converted to i _ _ i _ _
I would match that pattern with the current pattern displayed on the
screen; if it doesn't match, then another word must be selected to be
shown as the final word.



/**********************************************************************
 * Extra Credit: Runtime Analysis                                     *
 **********************************************************************/

You may earn a small amount of extra credit by analyzing the efficiency of one
substantial component of your project. Please answer the following questions if
you would like to be considered for the extra credit for program analysis
(remember to copy and paste your answers to the following questions into the
Google form as well for credit).

Specify the scope of the component you are analyzing (e.g. function name,
starting and ending lines of specific .java file).




What is the estimated runtime (e.g. big-O complexity) of this component?
Provide justification for this runtime (i.e. explain in your own words why
you expect this component to have this runtime performance).




Provide experimental evidence in the form of timed analysis supporting this
runtime estimate. (Hint: you may find it helpful to use command-line
arguments/flags to run just the specified component being analyzed).





/**********************************************************************
 * Extra Credit: Packaging project as an executable .jar file         *
 **********************************************************************/

You may earn a small amount of extra credit by packaging your project as an
executable .jar file. Please answer the following question if you would like to
be considered for this extra credit opportunity (remember to copy and paste your
answers to the following questions into the Google form as well for credit).

Describe in detail how to execute your .jar application (e.g. what execution
command to use on the terminal). Include a few example execution commands and
the expected results of running your program. For non-textual outputs
(e.g. graphical or auditory), feel free to describe in words what the output
should be or reference output files (e.g. images, audio files) of the expected
output.



