package mvc.twentyfortyeight;

import com.mrjaffesclass.apcs.messenger.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyListener;

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
    this.mvcMessaging.subscribe("moveUp", this);
    this.mvcMessaging.subscribe("moveLeft", this);
    this.mvcMessaging.subscribe("moveDown", this);
    this.mvcMessaging.subscribe("moveRight", this);
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

    if(messageName.equals("moveUp")) {
      this.move(1);
      this.setNum();
      this.mvcMessaging.notify("boardChange", this.board);
    } else if(messageName.equals("moveDown")) {
      this.move(-1);
      this.setNum();
      this.mvcMessaging.notify("boardChange", this.board);
    } else if(messageName.equals("moveLeft")) {
      this.move(-2);
      this.setNum();
      this.mvcMessaging.notify("boardChange", this.board);
    } else if(messageName.equals("moveRight")) {
      this.move(2);
      this.setNum();
      this.mvcMessaging.notify("boardChange", this.board);
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
    for(int i = 0; i < 4; i++) {
      for(int j = 0; j < 4; j++) {
        String value;
        if(intBoard[i][j] == 0) {
          value = "";
        } else {
          value = String.valueOf(intBoard[i][j]);
        }
        board[i][j] = value;
      }
    }
  }


  //input represented by 1 N, -1 S, 2 E, -2 W

  public void mash() {
    


  }
  
  public void move(int keyCode) {
    //move all nums as far to the input direction then check with mash
    //Up
    if(keyCode == 1) {
      for(int row = 3; row > 1; row--) {
        for(int col = 3; col > 1; col--) {
          //move piece
          if(intBoard[row - 1][col] == 0) {
            intBoard[row - 1][col] = intBoard[row][col];
            intBoard[row][col] = 0;
          }
        }
      }
      //down
    } else if(keyCode == -1) {
      for(int row = 0; row < this.intBoard.length - 1; row++) {
        for(int col = 0; col < this.intBoard[0].length - 1; col++) {
          //move piece
          if(intBoard[row + 1][col] == 0) {
            intBoard[row + 1][col] = intBoard[row][col];
            intBoard[row][col] = 0;
          }
        }
      }
      //right
    } else if(keyCode == 2) {
      for(int row = 0; row < this.intBoard.length - 1; row++) {
        for(int col = 0; col < this.intBoard[0].length - 1; col++) {
          //move piece
          System.out.println("joe");
          if(intBoard[row][col + 1] == 0) {
            intBoard[row][col + 1] = intBoard[row][col];
            intBoard[row][col] = 0;
          }
        }
      }
      //left
    } else if(keyCode == -2) {
      for(int row = 3; row > 1; row--) {
        for(int col = 3; col > 1; col--) {
          //move piece
          if(intBoard[row][col - 1] == 0) {
            intBoard[row][col - 1] = intBoard[row][col];
            intBoard[row][col] = 0;
          }
        }
      }
    }
  }
  
  
}
    
    
