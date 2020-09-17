import java.util.*;

class Sudoku {

   // just in case you need tis method for testing
   public static void main(String[] args) {
      char[][] puzzle = SudokuP.puzzle();
      char[][] testPuzzle = {{'.', '.', '9', '7', '4', '8', '.', '.', '.'},
					 {'7', '.', '.', '4', '.', '.', '.', '.', '.'},
					 {'.', '2', '.', '1', '.', '9', '.', '.', '.'},  //3
				     {'.', '.', '.', '.', '.', '.', '.', '8', '.'},
					 {'.', '1', '.', '.', '.', '.', '.', '.', '.'},
					 {'.', '5', '.', '.', '.', '.', '.', '.', '.'},  //6
					 {'.', '.', '8', '.', '3', '.', '2', '.', '.'},
					 {'.', '.', '.', '.', '.', '.', '.', '.', '6'},
					 {'.', '.', '2', '7', '5', '9', '.', '.', '.'}}; //9

   	// print testPuzzle - for debugging - should be made into a printPuzzle method
   	for (int row = 0; row < testPuzzle.length; row++) {
   	   for (int col = 0; col < testPuzzle[row].length; col++) {
   		   System.out.print(testPuzzle[row][col] + " ");
   	    }
   	    System.out.println();
   	 }
   	 System.out.println();
   
   	 // // check to see if first row is valid
   	 // boolean isValidRow = isParticallyValid(testPuzzle,1,0,0,8);
   	 // // check to see if first col is valid
   	 // boolean isValidCol = isParticallyValid(testPuzzle,0,0,8,0);
   	 // // check to see if top left 3x3 square is valid
   	 // boolean isValidSquare = isParticallyValid(testPuzzle,6,3,8,5);
   	 
   	 // check to see if puzzle is valid
       boolean isValidPuzzle = check(testPuzzle); // or check(puzzle);
       // you can try sending/testing the testPuzzle 2d array.
       
       // solves the sudoku puzzle and prints it out
       solve(puzzle); // or solve(testPuzzle);
       // you can try sending/testing the testPuzzle 2d array.
            
       }

   // print out one solution of the given puzzle
   // accepted parameter(s): 2D char array representing a sudoku board
   public static void solve(char[][] puzzle) {
	   if(puzzle == null || puzzle.length == 0) {
         return;
      }
      // if the sudoku puzzle is solvable, print out the solved puzzle along with associated breaks and lines
      if (solveSudoku(puzzle)) {
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
      // if the sudoku puzzle is not solvable, prints the puzzle is not solvable
      } else {
         System.out.print("This puzzle is not solvable.");
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
    
       return true; //return true if all cells are checked
   }

   // check if a given sudoku puzzle board is valid or not
   // return true if valid; otherwise return false
   // accepted parameter(s): a 2D char array representing a sudoku board
   // return type: boolean
   public static boolean check(char[][] puzzle) {

   	// iterate through all 9 rows and return false if any of them are invalid
   	for (int i = 0; i < 9; i++) {
   	   if (isParticallyValid(puzzle, i, 0, i, 8) == false) {
   		   return false;
   	   }
   	}
   
   	// iterate through all 9 cols and return false if any of them are invalid
   	for (int i = 0; i < 9; i++) {
   	   if (isParticallyValid(puzzle, 0, i, 8, i) == false) {
   		   return false;
   	   }
   	}
   
   	// iterate through all 9 squares and return false if any of them are invalid
   	for (int i = 0; i < 3; i++) {
   	   for (int j = 0; j < 3; j++) {
   		   if (isParticallyValid(puzzle,(i*3),(j*3),(2 + (i*3)),(2 + (j*3))) == false) {
   		      return false;
   		   }
   	   }
   	}
   
   	System.out.println("VALID BOARD");
           return true;
   }

   // check if the specified area of the given sudoku board is valid 
   // valid - following the 3 rules of sudoku
   // accepted parameters: puzzle - standing for a sudoku board in the representation of a 2D char array
   //                      four integers x1, y1, x2, y2
   //                      (x1, y1) stands for the top left corner of the area (inclusive)
   //                       x1 - row index; y1 - column index
   //                      (x2, y2) stands for the bottom right corner of the area (inclusive)
   //                       x2 - row index; y2 - colum index
   // return data type: boolean
   // if the specified area is valid, return true; otherwise false
   // e.g.1, isParticallyValid(puzzle,0,0,0,8) is used to check the 1st row of puzzle
   // e.g.2, isParticallyValid(puzzle,0,0,8,0) is used to check the 1st column of puzzle
   // e.g.3, isParticallyValid(puzzle,0,0,2,2) is used to check the top left 3*3 area
   // NOTE that this method will only be applied to every row, every column, and every 3*3 small areas (9 small areas in total)
   public static boolean isParticallyValid(char[][] puzzle, int x1, int y1,int x2,int y2){
   
   	//is square valid
   	if ( x2 == (x1 + 2) && y2 == (y1 + 2) ) {
   	    
   	   // check to make sure each element in square has only '.' or 1-9
   	   for (int row = 0; row < 3; row++) {
   	      for (int col = 0; col < 3; col++) {
   	    	   if ( (puzzle[row + x1][col + y1] < 49 || puzzle[row + x1][col + y1] > 57) && (puzzle[row + x1][col + y1] != '.')) {
   	    		   System.out.println("One of the squares has an invalid character in it: " + puzzle[row + x1][col + y1]);
   	    		   return false;
   	    	   }
   	    	}
   	   }
   
   	    //check to make sure there arent multiples of a char in the square
   	   for (int i = 0; i < 3; i++){
   		   for (int j = 0; j < 3; j++){
   		      for(int k = 0; k < 3; k++){
   			      for(int l = 0; l<3; l++){
   			         if (puzzle[x1+i][y1+j] == puzzle[x1+k][y1+l] && (i != k || j != l) && (puzzle[x1+i][y1+j] != '.')){
   				         System.out.println("There are multiples of " + "'" + puzzle[x1+i][y1+j] + "'" + " in one of the squares");
   				         return false;
   			         }
   			      }
   		      }
   		   }
   	   }
   	}
   
      //is row valid
      else if (x1 == x2) {
   	    
   	   //check to make sure each element in row has only has '.' or 1-9 (48-57 in ascii)
   	   for (int i = 0; i < puzzle.length; i++) {
   		   if ( (puzzle[x1][i] < 49 || puzzle[x1][i] > 57) && puzzle[x1][i] != '.') {
   		      System.out.println("row " + (x1 + 1) + " has an invalid character in it: " + puzzle[x1][i]);
   		      return false;
   		   }
   	   }
   
   	   //check to make sure there arent multiples of a char in the column
   	   for (int i = 0; i < puzzle.length; i++) {
   		   for (int j = i + 1; j < puzzle.length; j++) {
   		      if ( (puzzle[x1][i] == puzzle[x1][j]) && (puzzle[x1][i] != '.')) {
   			      System.out.println("There are multiples of " + "'" + puzzle[x1][i] + "'" + " in column " + (x1 + 1));
   			      return false;	    
   		      }
   		   }
   	   }
   	}
    
   	//is col valid
   	else if (y1 == y2) {
   	    
   	   //check to make sure each element in col has only has '.' or 1-9
   	   for (int i = 0; i < puzzle[y1].length; i++) {
   		   if ( (puzzle[i][y1] < 49 || puzzle[i][y1] > 57) && puzzle[i][y1] != '.') {
   		      System.out.println("column " + (y1 + 1) + " has an invalid character in it: " + puzzle[i][y1]);
   		      return false;
   		   }
   	   }
   
   	   //check to make sure there arent multiples of a char in the col
   	   for (int i = 0; i < puzzle[y1].length; i++) {
   		   for (int j = i + 1; j < puzzle[y1].length; j++) {
   		      if ( (puzzle[i][y1] == puzzle[j][y1]) && (puzzle[i][y1] != '.')) {
   			      System.out.println("There are multiples of " + "'" + puzzle[i][y1] + "'" + " in column " + (y1 + 1));
   			      return false;	    
   		      }
   		   }
   	   }
   	}
   	
      //return true if it passes above tests
      return true;
    	    
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

    // you are welcome to add more methods
