/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package AddGasto;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Pau
 */
public class AddGasto_FXMLController implements Initializable {

    @FXML
    private TextField CostText;
    @FXML
    private TextField TitleText;
    @FXML
    private DatePicker DateText;
    @FXML
    private TextArea DescriptionText;
    @FXML
    private Button ButtonAddCost;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void AddingCost(ActionEvent event) {
    }
    
}
