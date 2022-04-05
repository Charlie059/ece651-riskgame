package edu.duke.ece651.client.Controller;

import edu.duke.ece651.client.GameInfo;
import edu.duke.ece651.client.SceneCollector;
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
        /*
            TODO: get the continue Game info from the player use a function, and then save them into the gameLists.
        * */
        // TEST
        GameInfo g1 = new GameInfo(1,3,"");
        return FXCollections.observableArrayList(g1);
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
                            GameInfo clickedInfo = this.getTableView().getItems().get(this.getIndex());
                            System.out.println("Enter Game. INFO: "+clickedInfo.getGameID());
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
