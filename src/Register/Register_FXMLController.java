package Register;

import java.net.URL;
import java.util.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

public class Register_FXMLController implements Initializable {
    
    @FXML
    private TextField name;
    @FXML
    private TextField nickname;
    @FXML
    private PasswordField password;
    @FXML
    private TextField mail;
    @FXML
    private Button register_button;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }
}
