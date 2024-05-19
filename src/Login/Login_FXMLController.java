package Login;

import java.net.URL;
import java.util.*;
import javafx.fxml.*;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.event.*;
import java.io.IOException;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;

public class Login_FXMLController implements Initializable{

    @FXML
    private TextField nickname;
    @FXML
    private PasswordField password;
    @FXML
    private Button login_button;
    
    @FXML
    private void goToRegister(ActionEvent event) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Register/Register_FXML.fxml"));
        Parent root = loader.load();
        
        Login_Main.setRoot(root);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

}
