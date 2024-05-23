package ViewGasto;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import MainMenu.MainMenu_FXMLController;
import javafx.scene.image.Image;

public class ViewGasto_FXMLController implements Initializable {

    @FXML
    private Label categoryLabel;
    @FXML
    private Label costLabel;
    @FXML
    private Label dateLabel;
    @FXML
    private Label titleLabel;
    @FXML
    private Label descriptionLabel;
    @FXML
    private ImageView image;
    
    @Override
    public void initialize(URL url, ResourceBundle rb){
    }

    public void initializateData(String category, String cost, String date, String title, String description, Image image) {
        categoryLabel.setText(category);
        costLabel.setText(cost);
        dateLabel.setText(date);
        titleLabel.setText(title);
        descriptionLabel.setText(description);
        this.image.setImage(image);
    }
}
