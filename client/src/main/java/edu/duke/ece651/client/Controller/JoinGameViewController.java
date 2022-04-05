package edu.duke.ece651.client.Controller;

import edu.duke.ece651.client.GameInfo;
import edu.duke.ece651.client.Model.JoinGameModel;
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

public class JoinGameViewController implements Initializable
{
    @FXML
    private TableView<GameInfo> joinGameTable;
    @FXML
    private TableColumn<GameInfo, Integer> gameID_col, nPlayers_col;
    @FXML
    private TableColumn<GameInfo, String> note_col,button_col;

    private final Stage window;

    public JoinGameViewController(Stage window){
        this.window = window;
    }

    /**
     * Get available game list from the model
     * @return ObservableList<GameInfo>
     */
    private ObservableList<GameInfo> getGameData(){
        // Get data from the model
        return new JoinGameModel().getGameLists(true);
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
                            // TODO: enter the game
                            System.out.println("Enter Game ID: "+clickedInfo.getGameID());

                        });
                    }
                }

            };
            return cell;
        });

        joinGameTable.setItems(gameList);
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
