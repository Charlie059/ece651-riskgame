package edu.duke.ece651.client.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class MapViewController{
    private boolean debug;
    private final Communication outsideController;
    public MapViewController(Communication outsideController, boolean debug){
        this.debug = debug;
        this.outsideController = outsideController;
    }

    /**
     * If user click terr.return terr name
     * @param ae ActionEvent
     *
     */
    @FXML
    public void clickOnTerr(ActionEvent ae){
        Object source = ae.getSource();
        if (source instanceof Button) {
            Button btn = (Button) source;
            System.out.println(btn.getText());
            if(outsideController != null)
                outsideController.setTerrInfo(btn.getText());
        }
        else {  // situation should never come up
            throw new IllegalArgumentException("Invalid source " +
                    source +
                    " for ActionEvent");
        }
    }
}
