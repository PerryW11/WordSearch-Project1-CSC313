# WordSearch Project1 CSC313
 
## Below you will find the word for word requirements given by the professor of the Java course. 
 
 What you must do : 
-find in a grid of 5 x 5 of letters, if there is a word from the list of given words present in that grid.

-the words can be in column or in a row, not in diagonal or in reverse 

-the words are case sensitive and all are composed of 3 letters

-there is only one word per grid to be found

-there are 3 games to complete

-your implementation must be optimized, to do the least access to the system to check which letter are in each position

-the list of word is stored externally, and accessible thru a URL. You must re-access the list at each execution of your program. The list of words is the same for all the games.

-there is a URL to access for each game, what letter is located at a specific position.

When you locate a word from the list, you must output its beginning position and its ending position <column><row>:<column><row>.

As an example, if you search for jar in this array:

A B j a r

A z D e F

z f  D q W

P T w V Q

Z O q H I

The word jar is located at : C1:E1

The output should be : game<1-3> word:<word found> location:<beginning-end>

Your program must download the list of words at each execution, as it may change over time.
The list of words to search for is accessible thru:https://wordfinder-001.appspot.com/word.txt   (List of words extracted from: http://wordfinder.yourdictionary.com/letter-words/3)

To access a letter at a specific location: https://wordfinder-001.appspot.com/wordfinder?game=<1 to 3>&pos=<column><row>
Column: a-e
Row: 1-5

As an example : https://wordfinder-001.appspot.com/wordfinder?game=1&pos=b5 will return the letter E, which is the letter in the 2nd column, the 5th row.

The main class must be named: project1.java and be in the package csc312.

The system you are accessing to extract the letter, may produce status code SC_INTERNAL_SERVER_ERROR=500 or SC_FORBIDDEN=403 at random interval, if this happen, you must retry accessing the resource up to 5 times.

To test your error handling for  SC_INTERNAL_SERVER_ERROR and  SC_FORBIDDEN, there are specific pos that will generate it all the time:

If you use pos=Z99, the status code will be SC_INTERNAL_SERVER_ERROR.

If you use pos=Z88, the status code will be SC_FORBIDDEN.

Submission: one submission per team.  Upload a zipped file or your project directory.

Grading:

20% : documenting the pseudo-code to find search for word in the grid

10%: unit test to test for SC_INTERNAL_SERVER_ERROR  and SC_INTERNAL_SERVER_ERROR.

15%: retrieving the list of words from the url and loading it in the appropriate collection

15%:retrieving the letter from the grid and managing it in the appropriate data structure

40%: implementation of the word search in the grid

Bonus: 10% to optimize the search for word in the grid, the best submission, will have 10%, the 2nd 6% and the 3rd 3%.

Bonus: 10% final submission 7 days before the deadline
