package edu.duke.ece651.client.Controller;

import edu.duke.ece651.client.Model.GameModel;
import edu.duke.ece651.client.Model.Model;
import edu.duke.ece651.client.Model.MyTool;
import edu.duke.ece651.client.Model.SpecialTool;
import edu.duke.ece651.client.View.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class ToolsDialogController implements Initializable {

    @FXML
    TableView<SpecialTool> storeTable;
    @FXML
    TableView<MyTool> repsitoryTable;
    @FXML
    TableColumn<SpecialTool, String> toolName_store, details_store, buy_store;
    @FXML
    TableColumn<SpecialTool, Integer> cost_store;
    @FXML
    TableColumn<MyTool, String> use_rep,toolName_rep;
    @FXML
    Text curPoints;

    private ObservableList<MyTool> myToolList;  // this list should save in server model.

    private final Stage window;
    private boolean debug;
    private Model model;

    public ToolsDialogController(Stage window, Model model, boolean debug){
        this.window = window;
        this.debug = debug;
        this.model = model;
        this.myToolList = FXCollections.observableArrayList();  // use to save player's tool, should be placed in server model
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        curPoints.setText(String.valueOf(GameModel.getInstance().getCurrPoint()));

        // Set repo
        this.myToolList.addAll(GameModel.getInstance().getCardRepository());

        // set the store
        setStoreTable(setStoreData());

        //set my repository
        setRepositoryTable();
    }

    private void setStoreTable(ObservableList<SpecialTool> toolList){
        toolName_store.setCellValueFactory(new PropertyValueFactory<>("toolName"));
        cost_store.setCellValueFactory(new PropertyValueFactory<>("cost"));

        // details button
        details_store.setCellFactory((col) -> {
            TableCell<SpecialTool, String> cell = new TableCell<SpecialTool, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    this.setText(null);
                    this.setGraphic(null);
                    if (!empty) {
                        Button enterBtn = new Button("Details");
                        this.setGraphic(enterBtn);
                        enterBtn.setOnMouseClicked((me) -> {
                            showDetails();
                        });
                    }
                }

                private void showDetails() {
                    // get details fropm server and show it
                    String selectedToolName = this.getTableView().getItems().get(this.getIndex()).getToolName();
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle(selectedToolName+" details");
                    alert.setHeaderText(null);
                    alert.setContentText("Here are the details for "+ selectedToolName);  // get description from server.

                    alert.showAndWait();
                }

            };
            return cell;
        });

        // buy button
        buy_store.setCellFactory((col) -> {
            TableCell<SpecialTool, String> cell = new TableCell<SpecialTool, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    this.setText(null);
                    this.setGraphic(null);
                    if (!empty) {
                        Button enterBtn = new Button("Buy");
                        this.setGraphic(enterBtn);
                        enterBtn.setOnMouseClicked((me) -> {
                            tryToBuy();
                        });
                    }
                }

                private void tryToBuy() {
                    String selectedToolName = this.getTableView().getItems().get(this.getIndex()).getToolName();
                    //TODO: first check whether have enough points.

                    String res = GameModel.getInstance().try2BuyCard(selectedToolName, debug);
                    if(res != null){
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Failure");
                        alert.setHeaderText(null);
                        alert.setContentText(res);  // get description from server.
                        return;
                    }

                    // add to the repository
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Success");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully buy "+ selectedToolName);  // get description from server.

                    alert.showAndWait();
                    myToolList.add(new MyTool(selectedToolName));


                    //TODO: reduce cost
                    curPoints.setText(String.valueOf(GameModel.getInstance().getCurrPoint()));
                    System.out.println(GameModel.getInstance().getCurrPoint());
                }

            };
            return cell;
        });

        storeTable.setItems(toolList);
    }

    private void setRepositoryTable(){
        toolName_rep.setCellValueFactory(new PropertyValueFactory<>("ToolName"));

        // use button
        use_rep.setCellFactory((col) -> {
            TableCell<MyTool, String> cell = new TableCell<MyTool, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    this.setText(null);
                    this.setGraphic(null);
                    if (!empty) {
                        Button enterBtn = new Button("Use");
                        this.setGraphic(enterBtn);
                        enterBtn.setOnMouseClicked((me) -> {
                            try {
                                useTool();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        });
                    }
                }

                private void useTool() throws IOException {
                    String selectedToolName = this.getTableView().getItems().get(this.getIndex()).getToolName();

                    // TODO:call different tool function view based on selectedToolName
                    if(Objects.equals(selectedToolName, "Bombardment")){
                        new BombardmentDialogView().show(new Stage(), null, debug);
                    }else if(Objects.equals(selectedToolName, "Sanction")){
                        new SanctionDialogView().show(new Stage(),null,debug);
                    }else if(Objects.equals(selectedToolName, "The Great Leap Forward")){
                        new GreatLeapForwardDialogView().show(new Stage(),null,debug);
                    }else if(Objects.equals(selectedToolName, "Day breaks(spy)")){
                        // run function
                    }else if(Objects.equals(selectedToolName, "God be with you")){
                        String res = GameModel.getInstance().useGodBeWithU(debug);
                        if (res != null) System.out.println(res);
                    }else if(Objects.equals(selectedToolName, "SpecialSpyUpgrade")){
                        new SpecialSpyDialogView().show(new Stage(),null,debug);
                    }else if(Objects.equals(selectedToolName, "UnitDeploy")){
                        new UnitDeployDialogView().show(new Stage(),null,debug);
                    }

                    //delete this row from table
                    myToolList.remove(this.getIndex());
                }

            };
            return cell;
        });

        repsitoryTable.setItems(myToolList);
    }



    // use for test, initialize the Observable list, which should be done in server side.
    private ObservableList<SpecialTool> setStoreData(){
        ObservableList<SpecialTool> toolList = FXCollections.observableArrayList();
        toolList.add(new SpecialTool("Bombardment",120,""));
        toolList.add(new SpecialTool("Sanction",300,""));
        toolList.add(new SpecialTool("The Great Leap Forward",300,""));
        toolList.add(new SpecialTool("Day breaks(spy)",600,""));
        toolList.add(new SpecialTool("God be with you",150,""));
        toolList.add(new SpecialTool("SpecialSpyUpgrade",120,""));
        toolList.add(new SpecialTool("UnitDeploy",0,""));

        return toolList;
    }

}
