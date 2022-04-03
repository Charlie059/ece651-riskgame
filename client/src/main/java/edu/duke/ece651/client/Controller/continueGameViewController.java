package edu.duke.ece651.client.Controller;

import edu.duke.ece651.client.SceneCollector;
import edu.duke.ece651.client.gameInfo;
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

import java.net.URL;
import java.util.ResourceBundle;

public class continueGameViewController implements Initializable
{
    @FXML
    private TableView<gameInfo> continueGameTable;
    @FXML
    private TableColumn<gameInfo, Integer> gameID_col, nPlayers_col;
    @FXML
    private TableColumn<gameInfo, String> note_col,button_col;

    private final Stage window;

    public continueGameViewController(Stage window){
        this.window = window;
    }

    /*
        get gameInfo from player. Collect them into a ObservableList and return.
    * */
    private ObservableList<gameInfo> getGameData(){
        /*
            TODO: get the continue Game info from the player use a function, and then save them into the gameLists.
        * */
        // TEST
        gameInfo g1 = new gameInfo(1,32,"new game");
        return FXCollections.observableArrayList(g1);
    }

    private void showGameTable(ObservableList<gameInfo> gameList){
        gameID_col.setCellValueFactory(new PropertyValueFactory<>("GameID"));
        nPlayers_col.setCellValueFactory(new PropertyValueFactory<>("NPlayer"));
        note_col.setCellValueFactory(new PropertyValueFactory<>("Note"));

        button_col.setCellFactory((col) -> {
            TableCell<gameInfo, String> cell = new TableCell<gameInfo, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    this.setText(null);
                    this.setGraphic(null);
                    if (!empty) {
                        Button enterBtn = new Button("Enter");
                        this.setGraphic(enterBtn);
                        enterBtn.setOnMouseClicked((me) -> {
                            gameInfo clickedInfo = this.getTableView().getItems().get(this.getIndex());
                            System.out.println("Enter Game. INFO: "+clickedInfo.getGameID()+" "+clickedInfo.getNPlayer()+" "+clickedInfo.getNote());
                        });
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
