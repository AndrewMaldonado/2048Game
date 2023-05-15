package mvc.twentyfortyeight;

import com.mrjaffesclass.apcs.messenger.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;

/**
 * The model represents the data that the app uses.
 * @author Roger Jaffe
 * @version 1.0
 */
public class Model implements MessageHandler {

  // Messaging system for the MVC
  private final Messenger mvcMessaging;
  private int gameOver;
  String[][] board;
  int[][] intBoard;
  

  // Model's data vControllerariables

  /**
   * Model constructor: Create the data representation of the program
   * @param messages Messaging class instantiated by the Controller for 
   *   local messages between Model, View, and controller
   */
  public Model(Messenger messages) {
    mvcMessaging = messages;
    this.board = new String[4][4];
    this.intBoard = new int[4][4];
  }
  
  /**
   * Initialize the model here and subscribe to any required messages
   */
  public void init() {
    this.newGame();
    this.mvcMessaging.subscribe("playerMove", this);
    this.mvcMessaging.subscribe("newGame", this);
  }
  
    //reset board and for new game
    public void newGame() {
      for(int row=0; row<this.board.length; row++) {
        for (int col=0; col<this.board[0].length; col++) {
          this.intBoard[row][col] = 0;
          this.board[row][col] = "";
          }
      }
      spawnRandom();
      spawnRandom();
      this.mvcMessaging.notify("boardChange", this.board);
    }
    
     
  
  @Override
  public void messageHandler(String messageName, Object messagePayload) {
       // Display the message to the console for debugging
    if (messagePayload != null) {
      System.out.println("MSG: received by model: "+messageName+" | "+messagePayload.toString());
    } else {
      System.out.println("MSG: received by model: "+messageName+" | No data sent");
    }
   
    // playerMove message handler
    if (messageName.equals("playerMove")) {
      // Get the position string and convert to row and col
      String position = (String)messagePayload;
      Integer row = Integer.valueOf(position.substring(0,1));
      Integer col = Integer.valueOf(position.substring(1,2));
      
        if (this.board[row][col].equals("")) {      
                this.board[row][col] = "2";
        }
        
        this.mvcMessaging.notify("boardChange", this.board);
                
      
     
        
    
    // newGame message handler
    } else if (messageName.equals("newGame")) {
      //reset board
      this.newGame();
    }
  }
  
  public void spawnRandom() {
    boolean torf = false;
    //make chance for 1 to be 4
    int ranFour = (int)(Math.random() * 2);  
    while(!torf) {
        int ranRow = (int)(Math.random() * 4);
        int ranCol = (int)(Math.random() * 4);
        if(intBoard[ranRow][ranCol] == 0) {
          if(ranFour == 0) {
            intBoard[ranRow][ranCol] = 4;
            board[ranFour][ranCol] = "4";
            torf = true;
          } else {
            intBoard[ranRow][ranCol] = 2;
            board[ranRow][ranCol] = "2";
            torf = true;
          }
        }
      }
  }

  public void setNum() {
    for(int i = 0; i < 3; i++) {
      for(int j = 0; j < 3; j++) {
        String value = String.valueOf(intBoard[i][j]);
        if(value == "0") {
          value = "";
        }
        board[i][j] = value;
      }
    }
  }


  //input represented by 1 N, -1 S, 2 E, -2 W

  public void mash() {
    


  }
  
  public void move(int dir) {
    //move all nums as far to the input direction then check with mash
    int[] position = new int[2];
    if(dir == 1) {
      for(int row = 4; row > 0; row--) {
        for(int col = 4; col > 0; col--) {
          //move piece
          position[0] = row;
          position[1] = col;
          if(intBoard[row - 1][col] == 0) {
            intBoard[row - 1][col] = intBoard[row][col];
            intBoard[row][col] = 0;
          }
        }
      }
    } else if(dir == -1) {
      for(int row = 0; row < this.intBoard.length; row++) {
        for(int col = 0; col < this.intBoard[0].length; col++) {
          //move piece
          position[0] = row;
          position[1] = col;
          if(intBoard[row + 1][col] == 0) {
            intBoard[row + 1][col] = intBoard[row][col];
            intBoard[row][col] = 0;
          }
        }
      }
    } else if(dir == 2) {
      for(int row = 0; row < this.intBoard.length; row++) {
        for(int col = 0; col < this.intBoard[0].length; col++) {
          //move piece
          position[0] = row;
          position[1] = col;
          if(intBoard[row][col + 1] == 0) {
            intBoard[row][col + 1] = intBoard[row][col];
            intBoard[row][col] = 0;
          }
        }
      }
    } else if(dir == -2) {
      for(int row = 4; row > 0; row++) {
        for(int col = 4; col > 0; col++) {
          //move piece
          position[0] = row;
          position[1] = col;
          if(intBoard[row][col - 1] == 0) {
            intBoard[row][col - 1] = intBoard[row][col];
            intBoard[row][col] = 0;
          }
        }
      }
    }
  }
  
  public void keyPressed(KeyEvent event) {
    int key = event.getKeyCode();
    if(key == KeyEvent.VK_UP) {
      System.out.println("Up");
      //move pieces up
      this.move(1);
    } else if(key == KeyEvent.VK_DOWN) {
      System.out.println("Down");
      //move pieces down
      this.move(-1);
    } else if(key == KeyEvent.VK_RIGHT) {
      System.out.println("Right");
      //move pieces right
      this.move(2);
    } else if(key == KeyEvent.VK_LEFT) {
      System.out.println("Left");
      //move pieces left
      this.move(-2);
    }
  }
  
}
    
    
