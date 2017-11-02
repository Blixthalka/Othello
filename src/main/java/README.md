Instructions for the OthelloMain assignment
------------------------------------------
In this assignment, you will write an OthelloMain engine, i.e., a program that computes moves for the game OthelloMain (a.k.a. Reversi).

Basically, you should write a program that takes an OthelloMain positions and returns a recommended move for the player who has the move.

You will also have to implement a representation for the positions. The Java class OthelloPosition is incomplete. You will have to fill in code at the places maked 'TODO' in the file.

The actual engine should take a position, use Alpha-Beta search with a sensible heuristic, and return a suggested move. The easiest way is to construct a class that implements the provided Java interface OthelloAlgorithm. This interface has a method evaluate that takes a position and returns an OthelloAction. It also has methods for setting the heuristic evaluation method (an implementation of the interface OthelloEvaluator) and one for setting the search depth.

### Helper code
The following source files are available:

* IllegalMoveExeption.java
* OthelloAction.java
* OthelloAlgorithm.java (interface)
* OthelloEvaluator.java (interface)
* OthelloPosition.java (incomplete)

**Requirements:** Your solution must use Alpha-Beta search and a sensible heuristic evaluation. It should be good enough to soundly beat a standard Alpha-Beta implementation that uses a naive heuristic (just counting how many black and white markers there are on the board). You must be able to beat it both as black and white.

### Interface
To run your program, you should provide a shell script called othello which should be executable and be placed in your home directory at ~/edu/5DV122/lab1/. An example script file is provided here.

The shell script should take two arguments, a description of the position and a time limit. The first argument should be an ascii string of length 65. The first character is W or B, and indicates which player is to move. The remaining characters should be E (for empty) O (for white markers), or X (for black markers). These 64 charachters describe the board, with the first character referring to the upper left hand corner, the second character referring to the second square from the left on the uppermost row, and so on. For example, the 10th character describes the second square from the left on the second row from the top.

The second parameter gives a number of seconds. This should guide the depth of your search. If the second parameter is 5, you should see to it that, when run on one of the PCs in MA316, your program replies and terminates within 5 seconds.

The shell script should write a move on standard out on the format (4,7), indicating that the move suggested is to place a marker on the fourth row from the top and the seventh column from the left. If the player who has the move has no legal move, the script should instead write pass on standard out.

### Testing your program
Scripts and code for testing your program can be found in the folder test_code

