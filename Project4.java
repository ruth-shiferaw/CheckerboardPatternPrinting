import java.util.Scanner;
import java.io.PrintWriter;
import java.io.IOException;
import java.io.FileInputStream;
import java.awt.Color;
import java.io.FileOutputStream;
/* Program created to make patterns of rectangles.
 * Gateway Computing Java: 500.112
 * @author Ruth Shiferaw (RSHIFER1)
 * October 31st, 2022
 */
 
/**
 The main method that controls the patterns drawn.
 */
public class Project4 {
   public static void main(String[] args) throws IOException {
      Scanner kb = new Scanner(System.in);
      System.out.print("Enter checkerboard size: ");
      int boardSize = kb.nextInt();
      System.out.print("Enter RGB values, each [0,255]: ");
      int rValue = kb.nextInt();
      int gValue = kb.nextInt();
      int bValue = kb.nextInt();
      
      // draw with checkerboard method & save to output file
      drawCheckerboard(boardSize, rValue, gValue, bValue);
      
      //for snake pattern
      System.out.print("Enter snake input filename: ");
      String snakeFilename = kb.next();
      drawSnake(snakeFilename);
      
      //for spiral pattern
      System.out.print("Enter spiral input filename: ");
      String spiralFilename = kb.next();
      drawSpiral(spiralFilename);
      
   }
   
  /**
   Reads a file and creates cooresponding grid with 2D array.
   @param fileName for desired input file 
   @return Rectangle[][] for newly created 2D array
   */
   public static Rectangle[][] fileToGrid(String fileName) 
                                      throws IOException {
      FileInputStream fileIn = new FileInputStream(fileName);
      Scanner inFS = new Scanner(fileIn);
      
      //finding num of rows and cols and setting up 2D array
      int rowNum = inFS.nextInt();
      int colNum = inFS.nextInt();
      inFS.nextLine();
      Rectangle[][] returnGrid = new Rectangle[rowNum][colNum];
      
      while (inFS.hasNextLine()) {
         for (int row = 0; row < rowNum; row++) {
            for (int col = 0; col < colNum; col++) {
               returnGrid[row][col] = new Rectangle(inFS.nextLine());
            }
         }
      }
      
      fileIn.close();
      return returnGrid;
   }
   
  /**
   Outputs a grid to a file.
   @param boardSize for size of board
   @param patternType for name of array being read
   @return
   */
   
   public static void gridToOutputFile(int boardSize, 
                      Rectangle[][] patternType) throws IOException {
                     
      String partOne = "checkerboard";
      String partTwo = ".txt";
      String size = Integer.toString(boardSize);
      String outputFile = partOne + size + partTwo;
      FileOutputStream fileOutStream = new FileOutputStream(outputFile);
      PrintWriter outFS = new PrintWriter(fileOutStream);
      
      for (int row = 0; row < boardSize; row++) {
         for (int col = 0; col < boardSize; col++) {
            outFS.println(patternType[row][col]);
         }
      }
      outFS.flush();
      fileOutStream.close();
   }
   
   
  /**
   Helper method created to draw checkerboard pattern.
   @param boardSize for integer representing size
   @param rValue integer for red value
   @param gValue integer for green value
   @param bValue integer for blue value
   @return 2D array storing grid info
   */
   
   public static Rectangle[][] drawCheckerboard(int boardSize, 
                     int rValue, int gValue, int bValue) {
      
      Rectangle[][] checker = new Rectangle[boardSize][boardSize];
      StdDraw.clear(Color.LIGHT_GRAY);
      
      //drawing from the upper left, across, and next row
      for (int row = 0; row < boardSize; row++) {
         for (int col = 0; col < boardSize; col++) {
            Color colored = new Color(rValue, gValue, bValue);
            Color white = new Color(255, 255, 255);
            double width = (1.0 / boardSize);
            double height = (1.0 / boardSize);
            boolean colorFill;
            boolean whiteFill;
            double xcoord = ((width / 2.0) + (col * width));
            double ycoord = (1.0 - ((height / 2.0) + (row * width)));
            
            if ((row + col) % 2 == 0) {
               //print white box
               whiteFill = true;
               checker[row][col] = new Rectangle(white, width, height,
                                            whiteFill, xcoord, ycoord);
               checker[row][col].draw();
               StdDraw.pause(200);
               
            } else {
               //print color box
               colorFill = true;
               checker[row][col] = new Rectangle(colored, width, height,
                                              colorFill, xcoord, ycoord);
               checker[row][col].draw();
               StdDraw.pause(200);
            }
         }
      }
      
      //prnting array to checkerboard#.txt
      try {
         gridToOutputFile(boardSize, checker);
      } catch (IOException e) {
         e.printStackTrace();
      }
      return checker;
   }
   

       
  /**
   Helper method created to draw snake pattern.
   @param snakeFilename chosen file name
   */
   
   public static void drawSnake(String snakeFilename) {
      StdDraw.clear(Color.LIGHT_GRAY);
      //call method that imports array from read and parsed file
      try {
         Rectangle[][] snake = fileToGrid(snakeFilename);
         int row = 0;
         int col;
         int colA = snake[0].length;
         for (col = 0; col < colA; col++) {
            if (col % 2 == 0) {
               //print up to down pattern for even cols
               for (row = 0; row < snake.length; row++) {
                  if (((row + col) % 2) == 0) {
                     //print white box
                     snake[row][col].draw();
                     StdDraw.pause(200);
                  } else {
                     //print color box
                     snake[row][col].draw();
                     StdDraw.pause(200);
                  }
               }
            } else {
               //print down to up pattern for odd cols
               for (row = ((snake.length) - 1); row >= 0; row--) {
                  if (((row + col) % 2) == 0) {
                     //print white box
                     snake[row][col].draw();
                     StdDraw.pause(200);
                  } else {
                     //print color box
                     snake[row][col].draw();
                     StdDraw.pause(200);
                  }
               }
            }
         }
         
      } catch (IOException e) {
         e.printStackTrace();
      }
      
     
   }
  /**
   Helper method created to draw spiral pattern.
   @param spiralFilename for desired spiral file
   */
   
   public static void drawSpiral(String spiralFilename) 
                                   throws IOException {
      StdDraw.clear(Color.LIGHT_GRAY);
      Rectangle[][] spiral = fileToGrid(spiralFilename);
      int row = 0;
      int col = 0;
      int loopNum = 1;
      int maxRow = spiral.length;
      int maxCol = spiral[0].length;
      int minRow = 0;
      int minCol = 0;
      int realRows = spiral.length;
      int realCols = spiral[0].length;
      int calcRows = realRows;
      int calcCols = realCols;
      
      do {
         
         //side one of 4 part side printing
         row = (maxRow - loopNum);
         for (col = (maxCol - (1 * loopNum)); col > 0; col--) {
            spiral[row][col].draw();
            StdDraw.pause(200);
         }
      
         //side two of 4 part printing
         col = minCol - 1 + (loopNum);
         for (row = (maxRow - (1 * loopNum)); row > 0; row--) {
            spiral[row][col].draw();
            StdDraw.pause(200);
         }
      
         //side three of 4 part printing
         row = minRow - 1 + (loopNum);
         for (col = (minCol - 1 + loopNum); col < maxCol; col++) {
            spiral[row][col].draw();
            StdDraw.pause(200);
         }
      
         //side four of 4 part printing
         col = (maxCol - loopNum);
         for (row = (minRow - 1 + loopNum); row < maxRow; row++) {
            spiral[row][col].draw();
            StdDraw.pause(200);
         }
         
         //increment loopNum to change start points in for loops 
         loopNum++;
                  
         //place rows and cols calculations here? or before loops
         calcRows = ((realRows) - 2 * (loopNum - 1));
         calcCols = ((realCols) - 2 * (loopNum - 1));
         
      } while (calcRows > 0 && calcCols > 0);   
         
   }
}


   