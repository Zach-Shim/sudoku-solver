# Sudoku Solver in Java

This program utilizes backtracking and recursion to solve any permutation of an unsolved sudoku table.

How it works:
The program fills each vacant cell with a number within the 1 - 9 range before moving on to the next cell.
If the subsequent cells result in constraint problems from previous actions the program backtracks to the previous cell and tries another number until there are no constraints.
