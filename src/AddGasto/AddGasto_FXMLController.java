package AddGasto;

import MainMenu.MainMenu_FXMLController;
import java.io.File;
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
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.*;

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
    private Label LabelCategory;

    private final StringProperty selectedCategory = new SimpleStringProperty();
    
    ObservableList<MenuItem> categories = FXCollections.observableArrayList();
    
    Category category = null;
    
    @FXML
    private ImageView imageCost;
    @FXML
    private Button selectImage;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        imageCost.setImage(new Image("./assets/default_user.png"));
        imageCost.setVisible(false);
        
        try{
          addCategories();
          CategoryText.getItems().addAll(categories);
        }
        catch(IOException e){
            e.printStackTrace();
        }
        
        
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
    
    private void updateCategory(Category category) {
        this.category = category;
        selectedCategory.set(category.getName()); 
        LabelCategory.setText(category.getName());
        
    }

    @FXML
    private void AddingCost(ActionEvent event) throws IOException{
        LocalDate date = DateText.getValue();
        double cost = Double.parseDouble(CostText.getText());

        try{
            Acount.getInstance().registerCharge(TitleText.getText(), DescriptionText.getText(), cost, 0, imageCost.getImage(), date, category);
            goToMainMenu();
        }
        catch(AcountDAOException a){
            a.printStackTrace();
        }
    }
    
    public void goToMainMenu() throws IOException{
        Stage stage = (Stage) CostText.getScene().getWindow();
        stage.close();
    }
    
    @FXML
    public void selectArchiveHandle(ActionEvent event){
        FileChooser fc = new FileChooser();
        fc.setTitle("Selecciona una Imágen");
        
        fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("*.png","*.jpg","*.jpeg"));
        
        File file = fc.showOpenDialog(new Stage());
        
        if(file != null){
            Image file_image = new Image(file.toURI().toString());
            imageCost.setImage(file_image);
            selectImage.setVisible(false);
            imageCost.setVisible(true);
        }
    }
    
}
