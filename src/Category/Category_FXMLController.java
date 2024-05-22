/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Category;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.beans.binding.BooleanBinding;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;


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
    private ListView<String> CategoryList;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
<<<<<<< HEAD
        CategoryList.setItems(items);
=======
>>>>>>> ce832dc7db3d482d0a75a352cf5cb584b1dd3c61

               
        ButtonDeleteCategory.disableProperty().bind(Bindings.equal(-1, CategoryList.getSelectionModel().selectedIndexProperty()));
    
        ButtonEditCategory.disableProperty().bind(Bindings.equal(-1, CategoryList.getSelectionModel().selectedIndexProperty()));
        
        items = FXCollections.observableArrayList("Item 1", "Item 2", "Item 3");
        CategoryList.setItems(items);
    }    
    
     ObservableList<String> items = FXCollections.observableArrayList();
     
    @FXML
    private void AddingCategory(ActionEvent event) {
       TextInputDialog dialog1 = new TextInputDialog(""); // Por defectodialog.setTitle("Diálogo de entrada de texto");
        dialog1.setHeaderText("Nueva Categoría");
        dialog1.setContentText("Introduce el nombre de la categoría nueva:");
        Optional<String> result = dialog1.showAndWait();
        if (result.isPresent()){
           
        
}
    }

    @FXML
    private void EditingCategory(ActionEvent event) {
        
    }

    @FXML
    private void DeletingCategory(ActionEvent event) {
        String selectedItem = CategoryList.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            items.remove(selectedItem);
    }
    
    }
}
