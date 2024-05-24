package EditGasto;

import java.io.File;
import javafx.event.*;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
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
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.*;

public class EditGasto_FXMLController implements Initializable{

    @FXML
    private MenuButton categoryMenu;
    @FXML
    private TextField costLabel;
    @FXML
    private TextField cantidadText;
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
    Charge selectedCharge = null;
    ObservableList<MenuItem> categories = FXCollections.observableArrayList();

    public void initializateData(String category, String cost, String cantidad, LocalDate date, String name, String description, Image imageScan, Charge selected){
        costLabel.setText(cost);
        cantidadText.setText(cantidad);
        titleLabel.setText(name);
        descriptionLabel.setText(description);
        imageImage.setImage(imageScan);
        dateLabel.setValue(date);
        selectedCharge = selected;
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb){
        
        BooleanBinding areFieldsEmpty = Bindings.createBooleanBinding(() -> 
                costLabel.getText().isEmpty() ||
                cantidadText.getText().isEmpty() ||
                titleLabel.getText().isEmpty() || 
                dateLabel.getValue() == null || 
                descriptionLabel.getText().isEmpty() || 
                selectedCategory.get() == null || selectedCategory.get().isEmpty(),
                costLabel.textProperty(),
                   cantidadText.textProperty(),
                titleLabel.textProperty(),
                dateLabel.valueProperty(),
                descriptionLabel.textProperty(),
                selectedCategory
        );
        editar.disableProperty().bind(areFieldsEmpty);
        
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
        selectedCharge.setCategory(category);
        selectedCharge.setUnits(Integer.parseInt(cantidadText.getText()));
        selectedCharge.setCost(Double.parseDouble(costLabel.getText()));
        selectedCharge.setName(titleLabel.getText());
        selectedCharge.setDescription(descriptionLabel.getText());
        selectedCharge.setDate(dateLabel.getValue());
        selectedCharge.setImageScan(imageImage.getImage());
        
        Stage stage = (Stage) editar.getScene().getWindow();
        stage.close();
    }
    
    @FXML
    public void selectArchiveHandle(MouseEvent event){
        FileChooser fc = new FileChooser();
        fc.setTitle("Selecciona una Imágen");
        
        fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("*.png","*.jpg","*.jpeg"));
        
        File file = fc.showOpenDialog(new Stage());
        
        if(file != null){
            Image file_image = new Image(file.toURI().toString());
            imageImage.setImage(file_image);
        }
    }
}