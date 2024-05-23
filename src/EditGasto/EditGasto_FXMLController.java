package EditGasto;

import java.time.LocalDate;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class EditGasto_FXMLController {

    @FXML
    private TextField categoryLabel;
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

    public void initializateData(String category, String cost, LocalDate date, String name, String description, Image imageScan) {
        categoryLabel.setText(category);
        costLabel.setText(cost);
        titleLabel.setText(name);
        descriptionLabel.setText(description);
        imageImage.setImage(imageScan);
        dateLabel.setValue(date);
    }
    
}