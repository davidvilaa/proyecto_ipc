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
import javafx.scene.control.TextInputDialog;
import model.*;
import java.io.*;
import java.util.List;

public class Category_FXMLController implements Initializable {

    @FXML
    private Button ButtonAddCategory;
    @FXML
    private Button ButtonEditCategory;
    @FXML
    private Button ButtonDeleteCategory;
    @FXML
    private ListView<String> CategoryList;
    
   ObservableList<String> items = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb){
        ButtonDeleteCategory.disableProperty().bind(Bindings.equal(-1, CategoryList.getSelectionModel().selectedIndexProperty()));
        ButtonEditCategory.disableProperty().bind(Bindings.equal(-1, CategoryList.getSelectionModel().selectedIndexProperty()));
        
        try{
            loadCategories();
        }
        catch(IOException e){
            e.printStackTrace();
        }
        CategoryList.setItems(items);
    }
    
    public void loadCategories() throws IOException{
        try{
            List<Category> categories = Acount.getInstance().getUserCategories();
            for(Category category : categories){
                items.add(category.getName());
            }
        }
        catch(AcountDAOException e){
            e.printStackTrace();
        }
    }
    
    public boolean checkNombre(String name) throws IOException{
        try{
            List<Category> categories = Acount.getInstance().getUserCategories();
            for(Category category : categories){
                if(category.getName().equals(name)){
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setHeaderText("ERROR: Ya existe una categoría con este nombre");
                    alert.showAndWait();
                    return true;
                }
            }
        }
        catch(AcountDAOException e){
            e.printStackTrace();
        }
        return false;
    }
    
    @FXML
    private void AddingCategory(ActionEvent event) throws IOException{
        TextInputDialog dialog1 = new TextInputDialog("");
        dialog1.setTitle("");
        dialog1.setHeaderText("Introduce el nombre de la categoría nueva:");
        Optional<String> name = dialog1.showAndWait();
        
        TextInputDialog dialog2 = new TextInputDialog("");
        dialog2.setTitle("");
        dialog2.setHeaderText("Introduce una descripción de la categoría nueva");
        Optional<String> description = dialog2.showAndWait();
        
        if(checkNombre(name.get())){
            return;
        }
        
        if (name.isPresent() && description.isPresent()){
            try{
                items.add(name.get());
                CategoryList.setItems(items);
                Acount.getInstance().registerCategory(name.get(), description.get());
            }
            catch(AcountDAOException e){
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void EditingCategory(ActionEvent event) throws IOException{
        String selectedItem = CategoryList.getSelectionModel().getSelectedItem();
        TextInputDialog dialog1 = new TextInputDialog("");
        dialog1.setTitle("");
        dialog1.setHeaderText("Introduce el nuevo nombre");
        Optional<String> name = dialog1.showAndWait();
        
        TextInputDialog dialog2 = new TextInputDialog("");
        dialog2.setTitle("");
        dialog2.setHeaderText("Introduce la nueva descripción");
        Optional<String> description = dialog2.showAndWait();
        
        if(checkNombre(name.get())){
            return;
        }
        
        if(name.isPresent() && description.isPresent()){
            Category remove = null;
            try{
                List<Category> categories = Acount.getInstance().getUserCategories();
                for(Category category : categories){
                    if(category.getName().equals(selectedItem)){
                        remove = category;
                    }
                }
                Acount.getInstance().removeCategory(remove);
                Acount.getInstance().registerCategory(name.get(), description.get());
                items.remove(selectedItem);
                items.add(name.get());
                CategoryList.setItems(items);
            }
            catch(AcountDAOException e){
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void DeletingCategory(ActionEvent event) throws IOException{
        String selectedItem = CategoryList.getSelectionModel().getSelectedItem();
        Category remove = null;
        try{
            List<Category> categories = Acount.getInstance().getUserCategories();
            for(Category category : categories){
                if(category.getName().equals(selectedItem)){
                    remove = category;
                    items.remove(selectedItem);
                    CategoryList.setItems(items);
                }
            }
            Acount.getInstance().removeCategory(remove);
        }
        catch(AcountDAOException e){
            e.printStackTrace();
        }
    }
}
