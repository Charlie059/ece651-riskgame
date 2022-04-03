/**
 * Author: Yuxuan Yang AND Xuhui Gong
 */
package edu.duke.ece651.client;

import edu.duke.ece651.client.Controller.StartViewController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;


public class Client extends Application{

  public static void main(String[] args) throws IOException, InterruptedException, Exception {
    // Create client and connect to the server
    ClientSocket.getInstance();

    //GUI
    System.out.println("GUI begin running.");
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) throws Exception {
    showStartView(primaryStage);
  }

  private void showStartView(Stage window) throws IOException{
    // load start view fxml
    URL xmlResource = getClass().getResource("/xml/startView.fxml");
    FXMLLoader loader = new FXMLLoader(xmlResource);

    // use loader’s setControllerFactory to specify how to create controllers.
    HashMap<Class<?>,Object> controllers = new HashMap<>();
    controllers.put(StartViewController.class, new StartViewController(new LoginAndSignUpModel(),window));
    loader.setControllerFactory(controllers::get);
    GridPane gp = loader.load();

    // create scene and load css
    Scene scene = new Scene(gp, 640, 480);
    URL cssResource = getClass().getResource("/css/button.css");
    scene.getStylesheets().add(cssResource.toString());
    SceneCollector.startView = scene;

    window.setScene(scene);
    window.show();

  }
}
