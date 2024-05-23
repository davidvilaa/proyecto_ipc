package EditGasto;

import javafx.event.*;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.*;

public class EditGasto_FXMLController implements Initializable{

    @FXML
    private MenuButton categoryMenu;
    @FXML
    private TextField costLabel;
    @FXML
    private TextField titleLabel;
    @FXML
    private TextField descriptionLabel;
    @FXML
    private ImageView imageImage;
    @FXML
    private DatePicker dateLabel;
    @FXML
    private Label labelCategory;
    @FXML
    private Button editar;
    
    private final StringProperty selectedCategory = new SimpleStringProperty();
    Category category = null;
    ObservableList<MenuItem> categories = FXCollections.observableArrayList();

    public void initializateData(String category, String cost, LocalDate date, String name, String description, Image imageScan){
        costLabel.setText(cost);
        titleLabel.setText(name);
        descriptionLabel.setText(description);
        imageImage.setImage(imageScan);
        dateLabel.setValue(date);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb){
        try{
            addCategories();
            categoryMenu.getItems().addAll(categories);
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
    
    private void addCategories() throws IOException {
        try{
            List<Category> categoryList = Acount.getInstance().getUserCategories();
            if(categoryList == null || categoryList.isEmpty()) return;
            for(Category category : categoryList){
                MenuItem item = new MenuItem();
                String a = category.getName();
                item.setText(a);
                item.setOnAction(event -> {
                updateCategory(category);
                });
                categories.add(item);
            }
        } 
        catch(AcountDAOException e){
            e.printStackTrace();
        } 
    }
    
    private void updateCategory(Category category){
        this.category = category;
        selectedCategory.set(category.getName()); 
        labelCategory.setText(category.getName());
    }
    
    @FXML
    private void editarHandle(ActionEvent event){
        
    }
}