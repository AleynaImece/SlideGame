/**
* This is game called slide that extends application
* @Author: Aleyna Ýmece/210101066
*/ 
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.Button;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import java.lang.Math;
import javafx.scene.layout.GridPane;
import java.util.Scanner;

public class SlideGame extends Application{
  
  //stores the array of buttons that represents the screen
  private Button[][] buttons;
  //stores the width/length of the board
  private int width = 4;
  private int length = 4;
  private int indOne;
  private int indTwo;
  
  /** 
   * creates the GUI display
   * @param primaryStage the main window
   */
  public void start(Stage primaryStage) {
    
    buttons = new Button[length][width];
    GridPane grid = new GridPane();
    //initializes every button as a blank button
    for (int i = 0; i < length; i++) {
      for (int j = 0; j < width; j++) {
        buttons[i][j] = new Button("  ");
      }
      grid.addRow(i, buttons[i]);
    }
    
    //stores the two indices to start one random square off as one
    indOne = (int) (Math.random() * width);
    indTwo = (int) (Math.random() * length);
    buttons[indOne][indTwo].setText("1");
    
     //buttons on the left side clicked
    buttons[1][0].setOnAction(e->{
      intToButton(addOne(buttonToInt(buttons)));
      leftEnd();          
    });
    
    buttons[2][0].setOnAction(e->{
      leftEnd();
      intToButton(addOne(buttonToInt(buttons)));  
    });

    //butons on the right side clicked
    buttons[1][3].setOnAction(e->{
      rightEnd();
      intToButton(addOne(buttonToInt(buttons)));  
    });
    
    buttons[2][3].setOnAction(e->{
        rightEnd();
        intToButton(addOne(buttonToInt(buttons)));  
    });
    
    //buttons on the top clicked
    buttons[0][1].setOnAction(e->{
      topEnd();
      intToButton(addOne(buttonToInt(buttons)));  
    });
    
    buttons[0][2].setOnAction(e->{
      topEnd();
      intToButton(addOne(buttonToInt(buttons)));  
    });
    
    //buttons on the bottom clicked
    buttons[3][1].setOnAction(e->{
      bottomEnd();
      intToButton(addOne(buttonToInt(buttons)));  
    });
    
    buttons[3][2].setOnAction(e->{
      bottomEnd();
      intToButton(addOne(buttonToInt(buttons)));  
    });
    
    //new scene for the grid
    Scene scene = new Scene(grid);
    primaryStage.setScene(scene);
    primaryStage.show();
    
    if (isGameFinished()) {
        System.out.println("Oyun bitti!");
    }
  }
   
  /** 
   * helper method to convert from an array of ints to an array of buttons
   * @param ints an array of ints
   */
  public void intToButton(int[][] ints) {
    //loops through each of the ints to set the text on buttons
    for (int i = 0; i < ints.length; i++) {
      for (int j = 0; j < ints[i].length; j++) {
        if (ints[i][j] == 0) {
          buttons[i][j].setText("  ");
        }
        else {
          buttons[i][j].setText(Integer.toString(ints[i][j]));
        }
      }
    }
  }
  
  /**
   * helper method to convert an array of buttons to an array of ints
   * @param buttons an array of buttons
   * @return an array of ints
   */
  public int[][] buttonToInt(Button[][] buttons) {
    //stores the final array of ints
    int[][] finalArray = new int[buttons.length][buttons[0].length];
    //runs through each button intiliaze ints based off the text
    for (int i = 0; i < buttons.length; i++) {
      for (int j = 0; j < buttons[i].length; j++) {
        if (buttons[i][j].getText().equals("  ")) {
          finalArray[i][j] = 0;
        }
        else {
          finalArray[i][j] = Integer.parseInt(buttons[i][j].getText());
        }
      }
    }
    return finalArray;
  }
  
  /**
   * helper method to generate an extra one
   * @param ints an array of ints
   * @return an array of ints with a new one tile
   */
  public static int[][] addOne(int[][] ints) {
    int indOne = (int) (Math.random() * ints.length);
    int indTwo = (int) (Math.random() * ints[0].length);
    //stores whether or not there is an empty space
    boolean isSpace = false;
    //checks to make sure that there is an empty space
    for (int i = 0; i < ints.length; i++) {
      for(int j = 0; j < ints[i].length; j++) {
        if (ints[i][j] == 0) {
          isSpace = true;
        }
      }
    }
    //if there is space on the board, it will keep generating numbers until it hits a blank space
    while (ints[indOne][indTwo] != 0 && isSpace) {
      indOne = (int) (Math.random() * ints.length);
      indTwo = (int) (Math.random() * ints[0].length);
    } 
    ints[indOne][indTwo] = 1;
    return ints;
  }
  
    /**
   * helper to determine the number of non-zeroes in a method, to test addOne
   * @param ints a multidimensional array
   * @return the number of non-zeros in the method
   */
  public static int numberNonZero(int[][] ints) {
    int numNonZero = 0;
    //loops through the array
    for (int i = 0; i < ints.length; i++) {
      for (int j = 0; j < ints[i].length; j++) {
        if (ints[i][j] != 0) {
          numNonZero++;
        }
      }
    }
    return numNonZero;
  }
  
  /**
  * helper to determine if the numbers are going to be added or not
  *@param string value1
  *@param string value2
  */
  private boolean isMerge(String value1, String value2) {
    return value1.equals(value2);
  }
  
  /*
  * if there is no other place finishes the game
  */
  public boolean isGameFinished(){
    for (int i = 0; i < length; i++){
        for (int j = 0; j < width; j++){
            if (buttons[i][j].getText().equals("  ")){  //there is still place for numbers
              return false; 
            }
        }
    }
    return true; //else there is no place
   }
  
  /*
  * adds the values which is sliding to left
  * @param int i for buttons [i]
  * @param int i for buttons [j]
  */
  private void leftAdd(int i, int j) {
    String value = buttons[i][j].getText();
    int k = j - 1;
    boolean merged = false;
    //slides to left till k equals less than 0
    while(k >= 0 && buttons[i][k].getText().equals("  ")){
      buttons[i][k].setText(value);
      buttons[i][k + 1].setText("  ");
      k--;
    }
    //if there is no other number at left(k=>0)
    //and if its merge(same number)
    //and if its no other merged number
    //it adds to number
    if(k >= 0 && isMerge(buttons[i][k].getText(), value) && !merged){
      int mergedValue = Integer.parseInt(value) + Integer.parseInt(value);  //adds to number (1+1=2)
      buttons[i][k].setText(Integer.toString(mergedValue));  //sets the new number
      buttons[i][j].setText("  ");  //deletes the right number
      merged = true;  //no other merge is done 
    }
  }
  
  /*
  * adds the values which is sliding to right
  * @param int i for buttons [i]
  * @param int i for buttons [j]
  */
  private void rightAdd(int i, int j){
    String value = buttons[i][j].getText();
    int k = j + 1;
    boolean merged = false; 
    //slides to right till k equals less than width
    while(k < width && buttons[i][k].getText().equals("  ")){
      buttons[i][k].setText(value);
      buttons[i][k - 1].setText("  ");
      k++;
    }
    //if there is no other number at right
    //and if its merge(same number)
    //and if its no other merged number
    //it adds to number
    if(k < width && isMerge(buttons[i][k].getText(), value) && !merged){
      int mergedValue = Integer.parseInt(value) + Integer.parseInt(value);  //adds to number (1+1=2)
      buttons[i][k].setText(Integer.toString(mergedValue));  //sets the new number
      buttons[i][j].setText("  ");   //deletes the right number
      merged = true;  //no other merge is done 
    }
  }
  
  /*
  * adds the values which is sliding to top
  * @param int i for buttons [i]
  * @param int i for buttons [j]
  */
  private void topAdd(int i, int j){
    String value = buttons[i][j].getText();
    int k = i - 1;
    boolean merged = false; 
    //slides to top till k equals less than 0
    while(k >= 0 && buttons[k][j].getText().equals("  ")){
      buttons[k][j].setText(value);
      buttons[k + 1][j].setText("  ");
      k--;
    } 
    //if there is no other number at top
    //and if its merge(same number)
    //and if its no other merged number
    //it adds to number
    if(k >= 0 && isMerge(buttons[k][j].getText(), value) && !merged){
      int mergedValue = Integer.parseInt(value) + Integer.parseInt(value);  //adds to number (1+1=2)
      buttons[k][j].setText(Integer.toString(mergedValue));  //sets the new number
      buttons[i][j].setText("  ");   //deletes the right number
      merged = true;  //no other merge is done 
    }
  }
  
  /*
  * adds the values which is sliding to bottom
  * @param int i for buttons [i]
  * @param int i for buttons [j]
  */
  private void bottomAdd(int i, int j){
    String value = buttons[i][j].getText();
    int k = i + 1;
    boolean merged = false; 
    //slides to bottom till k equals less than length
    while(k < length && buttons[k][j].getText().equals("  ")){
      buttons[k][j].setText(value);
      buttons[k - 1][j].setText("  ");
      k++;
    }  
    //if there is no other number at top
    //and if its merge(same number)
    //and if its no other merged number
    //it adds to number
    if(k < length && isMerge(buttons[k][j].getText(), value) && !merged){
      int mergedValue = Integer.parseInt(value) + Integer.parseInt(value);  //adds to number (1+1=2)
      buttons[k][j].setText(Integer.toString(mergedValue));  //sets the new number
      buttons[i][j].setText("  ");   //deletes the right number
      merged = true;  //no other merge is done 
    }
  }
  
  /*
  * slides to left than adds numbers
  */
  private void leftEnd(){
    for (int i = 0; i < length; i++){
      for (int j = 1; j < width; j++){
        //if left is empty
        if (!buttons[i][j].getText().equals("  ")){
          leftAdd(i, j);
        }
      }
    }
    if (isGameFinished()){
      System.out.println("THE GAME HAS FINISHED STOP !!!");
    }
  }

  /*
  * slides to right than adds numbers
  */
  private void rightEnd(){
    for (int i = 0; i < length; i++){
      for (int j = width - 2; j >= 0; j--){
        //if right is empty
        if (!buttons[i][j].getText().equals("  ")){
          rightAdd(i, j);
        }
      }
    }
    if (isGameFinished()){
      System.out.println("THE GAME HAS FINISHED STOP !!!");
    }
  }

  /*
  * slides to top than adds numbers
  */
  private void topEnd(){
    for (int j = 0; j < width; j++){
      for (int i = 1; i < length; i++){
        //if top is empty
        if (!buttons[i][j].getText().equals("  ")){
          topAdd(i, j);
        }
      }
    }
    if (isGameFinished()){
      System.out.println("THE GAME HAS FINISHED STOP !!!");
    }
  }

  /*
  * slides to bottom than adds numbers
  */
  private void bottomEnd(){
    for (int j = 0; j < width; j++){
      for (int i = length - 2; i >= 0; i--){
        //if bottom is empty
        if (!buttons[i][j].getText().equals("  ")){
          bottomAdd(i, j);
        }
      }
    }
    if (isGameFinished()){
      System.out.println("THE GAME HAS FINISHED STOP !!!");
    }
  }
  
  /** 
    * The main method to run the program  
    * @param args the command line arguments  
    */ 
  public static void main(String[] args) { 
    SlideGame.launch(args);              
  }
}