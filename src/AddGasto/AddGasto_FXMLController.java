/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package AddGasto;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
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
    @FXML
    private MenuButton CategoryText;
    @FXML
    private MenuItem ItemCategory1;
    @FXML
    private MenuItem ItemCategory2;
    @FXML
    private Label LabelCategory;

    private final StringProperty selectedCategory = new SimpleStringProperty();

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        ItemCategory1.setOnAction(event -> updateCategory(ItemCategory1.getText()));
        ItemCategory2.setOnAction(event -> updateCategory(ItemCategory2.getText()));
        
        
        
        BooleanBinding areFieldsEmpty = Bindings.createBooleanBinding(() -> 
                CostText.getText().isEmpty() || 
                TitleText.getText().isEmpty() || 
                DateText.getValue() == null || 
                DescriptionText.getText().isEmpty() || 
                selectedCategory.get() == null || selectedCategory.get().isEmpty(),
                CostText.textProperty(),
                TitleText.textProperty(),
                DateText.valueProperty(),
                DescriptionText.textProperty(),
                selectedCategory
        );
         
        ButtonAddCost.disableProperty().bind(areFieldsEmpty);

         
        
        
    }    
    
    private void updateCategory(String text) {
        selectedCategory.set(text); 
        LabelCategory.setText(text);
        
    }

    @FXML
    private void AddingCost(ActionEvent event) {
    }
    
}
