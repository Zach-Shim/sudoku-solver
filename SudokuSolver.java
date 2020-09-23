import java.util.*;

class SudokuSolver {
    //private int[][] board;

    // example board of an unsolved sudoku table
    public static void main(String[] args) {
        char[][] testPuzzle = {{'.', '1', '7', '8', '.', '.', '4', '.', '6'},
					           {'.', '.', '.', '4', '.', '7', '.', '.', '2'},
					           {'.', '.', '9', '.', '1', '.', '.', '7', '.'},  
				               {'5', '8', '.', '.', '.', '.', '6', '.', '.'},
                               {'.', '.', '2', '.', '.', '.', '5', '.', '.'},
                               {'.', '.', '4', '.', '.', '.', '.', '8', '7'},  
                               {'.', '9', '.', '.', '4', '.', '1', '.', '.'},
                               {'1', '.', '.', '3', '.', '6', '.', '.', '.'},
                               {'6', '.', '3', '.', '.', '9', '7', '2', '.'}}; 
        displayBoard(testPuzzle);
        boolean isValid = check(testPuzzle);      // check to see if puzzle is valid
        if(!isValid) System.out.println("INVALID BOARD");
        solve(testPuzzle);      // solves the sudoku puzzle and prints it out
    }

    // check if a given sudoku puzzle board is valid or not
    // return true if valid; otherwise return false
    // accepted parameter(s): a 2D char array representing a sudoku board
    // return type: boolean
    public static boolean check(char[][] puzzle) {

        // iterate through all 9 rows and return false if any of them are invalid
        for (int i = 0; i < 9; i++) {
            if (!(rowCheck(puzzle, i))) {
                return false;
            }
        }

        // iterate through all 9 cols and return false if any of them are invalid
        for (int i = 0; i < 9; i++) {
            if (!(columnCheck(puzzle, i))) {
                return false;
            }
        }

        // iterate through all 9 squares and return false if any of them are invalid
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                if (!(squareCheck(puzzle, (row * 3), (col * 3)))) {
                    return false;
                }
            }
        }

        System.out.println("VALID BOARD");
        return true;
    }

    // check if a specified row in a given sudoku board is valid 
    public static boolean rowCheck(char[][] puzzle, int row) {
        //check to make sure each element in row has only has '.' or 1-9
        Set<Character> set = new HashSet<Character>();
        for(char num: puzzle[row]) {
            if(num == '.') {
                continue;
            }
            else if(set.contains(num)) {
                return false;
            }
            else {
                set.add(num);
            }
        }
        return true;
    }

    // check if a specified column in a given sudoku board is valid 
    public static boolean columnCheck(char[][] puzzle, int column) {
        //check to make sure each element in column has only has '.' or 1-9
        Set<Character> set = new HashSet<Character>();
        for(int i = 0; i < 9; i++) {
            if(puzzle[i][column] == '.') {
                continue;
            }
            else if(set.contains(puzzle[i][column])) {
                return false;
            }
            else {
                set.add(puzzle[i][column]);
            }
        }
        return true;
    }

    // check if a specified 3x3 square in a given sudoku board is valid 
    public static boolean squareCheck(char[][] puzzle, int row, int col) {
        char boardIndex;
        Set<Character> set = new HashSet<Character>();
        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 3; j++) {
                boardIndex = puzzle[row+i][col+j];
                if((!Character.isDigit(boardIndex)) && boardIndex != '.') {
                    return false;
                }
                else if(boardIndex == '.') {
                    continue;
                }
                else if(set.contains(boardIndex)) {
                    return false;
                }
                else {
                    set.add(boardIndex);
                }
            }
        }
        return true;
    }

    // print out one solution of the given puzzle
    // accepted parameter(s): 2D char array representing a sudoku board
    public static void solve(char[][] puzzle) {
        // error check
	    if(puzzle == null || puzzle.length == 0) {
            return;
        }
        // if the sudoku puzzle is solvable, print out the solved puzzle along with associated breaks and lines
        else if (solveSudoku(puzzle)) { 
            displayBoard(puzzle);
        }
        // if the sudoku puzzle is not solvable, prints the puzzle is not solvable
        else {
            System.out.print("This puzzle is not solvable.");
        }
    }
    
    private static void displayBoard(char[][] puzzle) {
        System.out.println(" -----------------------------------");
        for (int i = 0; i < 9; ++i) {
            for (int j = 0; j < 9; ++j) {
                System.out.print("| ");
                System.out.print(puzzle[i][j]);
                System.out.print(' ');
            }
            System.out.println("|");
            System.out.println(" -----------------------------------");
        }
    }

   // solve a given sudoku puzzle board
   // additionally, return true if solvable; otherwise return false
   // accepted parameter(s): a 2D char array representing a sudoku board
   // return type: boolean
   // NOTE: you can assume that only valid sudoku board will be given as parameters to this method
   public static boolean solveSudoku(char[][] puzzle){
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (puzzle[i][j] != '.') {
                    continue;
                }
                // traverses each character from 1-9
                for (char k = '1'; k <= '9'; k++) {
                    // calls isSpotValid to see if we can put the number k in the current spot in the puzzle
                    if (isSpotValid(puzzle, i, j, k)) {
                        puzzle[i][j] = k;
                        if (solveSudoku(puzzle)) {
                            return true;
                        }
                        puzzle[i][j] = '.';
                    }
                }
                return false;
            }
        }
        return true;  // return true if all cells are checked
     }


    
    // check whether putting a digit c at the position (x, y) in a given sudoku board
    // will make the board invalid
    // accepted parameters: puzzle - standing for a sudoku board in the representation of a 2D char array
    //                      two integers x, y
    //                      x - row index; y - column index
    //                      c - a digit in the form of char to put at (x, y)
    // return data type: boolean
    // if putting c in puzzle is a valid move, return true; otherwise false
    public static boolean isSpotValid(char[][] puzzle, int row, int col, char c){
        for (int i = 0; i < 9; i++) {
            // check through every spot in the sudoku board to see if there is already a number there (checks for empty space)
            if (puzzle[i][col] != '.' && puzzle[i][col] == c) {
                return false;
            }
            if (puzzle[row][i] != '.' && puzzle[row][i] == c) {
                return false;
            }
            if (puzzle[3 * (row / 3) + i / 3][3 * (col / 3) + i % 3] != '.' && puzzle[3 * (row / 3) + i / 3][3 * (col / 3) + i % 3] == c) {
                return false;
            }
        }
        return true;
    }
 }
