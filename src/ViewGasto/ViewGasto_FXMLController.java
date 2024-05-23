package ViewGasto;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

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
}
