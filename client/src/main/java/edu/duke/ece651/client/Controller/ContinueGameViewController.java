package edu.duke.ece651.client.Controller;

import edu.duke.ece651.client.GameInfo;
import edu.duke.ece651.client.Model.GameModel;
import edu.duke.ece651.client.Model.SwitchGameModel;
import edu.duke.ece651.client.SceneCollector;
import edu.duke.ece651.client.View.DeployView;
import edu.duke.ece651.client.View.MainGameView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ContinueGameViewController implements Initializable
{
    @FXML
    private TableView<GameInfo> continueGameTable;
    @FXML
    private TableColumn<GameInfo, Integer> gameID_col, nPlayers_col;
    @FXML
    private TableColumn<GameInfo, String> note_col,button_col;

    private final Stage window;

    public ContinueGameViewController(Stage window){
        this.window = window;
    }

    /*
        get GameInfo from player. Collect them into a ObservableList and return.
    * */
    private ObservableList<GameInfo> getGameData(){
        // Get continues game list form the model
        return new SwitchGameModel().getGameLists(true);
    }

    private void showGameTable(ObservableList<GameInfo> gameList){
        gameID_col.setCellValueFactory(new PropertyValueFactory<>("GameID"));
        nPlayers_col.setCellValueFactory(new PropertyValueFactory<>("NPlayer"));
        note_col.setCellValueFactory(new PropertyValueFactory<>("Note"));

        button_col.setCellFactory((col) -> {
            TableCell<GameInfo, String> cell = new TableCell<GameInfo, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    this.setText(null);
                    this.setGraphic(null);
                    if (!empty) {
                        Button enterBtn = new Button("Enter");
                        this.setGraphic(enterBtn);
                        enterBtn.setOnMouseClicked((me) -> {
                            trySwitchGame();


                        });
                    }
                }

                /**
                 * Try switch game
                 */
                private void trySwitchGame() {
                    GameInfo clickedInfo = this.getTableView().getItems().get(this.getIndex());
                    System.out.println("Enter Game. INFO: "+clickedInfo.getGameID());

                    // Request model to join the game
                    boolean switchResult =  GameModel.getInstance().switchGame(clickedInfo.getGameID(),true);
                    if(switchResult){
                        // Create a new Deployment view
                        try {
                            new MainGameView().show(window, null);
                        } catch (IOException e) {
                            e.printStackTrace();
                            System.out.println("Cannot switch game");
                        }
                    }
                    else {
                        System.out.println("Cannot switch game");
                    }
                }

            };
            return cell;
        });

        continueGameTable.setItems(gameList);
    }

    @FXML
    public void clickOnBack(){
        window.setScene(SceneCollector.menuView);
        window.show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        showGameTable(this.getGameData());
    }
}
