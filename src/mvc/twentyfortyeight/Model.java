package mvc.twentyfortyeight;

import com.mrjaffesclass.apcs.messenger.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * The model represents the data that the app uses.
 * @author Roger Jaffe
 * @version 1.0
 */
public class Model implements MessageHandler {

  // Messaging system for the MVC
  private final Messenger mvcMessaging;
  private int gameOver;
  int[][] board;

  // Model's data vControllerariables

  /**
   * Model constructor: Create the data representation of the program
   * @param messages Messaging class instantiated by the Controller for 
   *   local messages between Model, View, and controller
   */
  public Model(Messenger messages) {
    mvcMessaging = messages;
    this.board = new int[4][4];
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
      
      
     
        
    
    // newGame message handler
    } else if (messageName.equals("newGame")) {
      //reset board
      this.newGame();
    }
  }
}
    
    
