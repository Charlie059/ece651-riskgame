/**
 * Author: Yuxuan Yang AND Xuhui Gong
 */
package edu.duke.ece651.client;


import edu.duke.ece651.client.View.StartView;
import javafx.application.Application;
import javafx.stage.Stage;
import java.io.IOException;


public class Client extends Application{

  public static void main(String[] args) throws IOException, InterruptedException, Exception {
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) throws Exception {
    new StartView().show(primaryStage, null);
  }

}
