/**
 * Author: Yuxuan Yang AND Xuhui Gong
 */
package edu.duke.ece651.client;


import edu.duke.ece651.client.View.StartView;
import javafx.application.Application;
import javafx.stage.Stage;


public class Client extends Application{
  private boolean debug = false;

  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) throws Exception {
    new StartView().show(primaryStage, null, debug);
  }

  public void setDebug(boolean debug) {
    this.debug = debug;
  }

}
