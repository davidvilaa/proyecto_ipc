/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Category;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.beans.binding.BooleanBinding;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TextField;


/**
 * FXML Controller class
 *
 * @author Pau
 */
public class Category_FXMLController implements Initializable {

    @FXML
    private Button ButtonAddCategory;
    @FXML
    private Button ButtonEditCategory;
    @FXML
    private Button ButtonDeleteCategory;
    @FXML
    private ListView<?> CategoryList;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        ObservableList<?> items = FXCollections.observableArrayList();
        CategoryList.setItems(items);
        
        BooleanBinding isListEmpty = CategoryList.getItems().emptyProperty();
        ButtonDeleteCategory.disableProperty().bind(isListEmpty);
    }    

    @FXML
    private void AddingCategory(ActionEvent event) {
    }

    @FXML
    private void EditingCategory(ActionEvent event) {
    }

    @FXML
    private void DeletingCategory(ActionEvent event) {
    }
    
}
