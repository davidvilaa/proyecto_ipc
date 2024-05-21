package Login;

import java.net.URL;
import java.util.*;
import javafx.fxml.*;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.event.*;
import java.io.IOException;
import javafx.scene.Parent;
import model.*;

public class Login_FXMLController implements Initializable{

    @FXML
    public TextField nickname;
    @FXML
    public PasswordField password;
    @FXML
    public Button login_button;
    
    @FXML
    private void goToRegister(ActionEvent event) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Register/Register_FXML.fxml"));
        Parent root = loader.load();
        
        Login_Main.setRoot(root);
    }
    
    @FXML
    private void loginHandle(ActionEvent event) throws IOException{
        try{
            Acount cuentaGastos = Acount.getInstance();
            if(!(nickname.getText().trim().isEmpty()) && !(password.getText().trim().isEmpty())){
                if(cuentaGastos.logInUserByCredentials(nickname.getText(), password.getText())){
                    login_button.setText("Inicio de Sesión Correcto");
                    
                }
                else{
                    login_button.setText("Usuario o Contraseña incorrectos");
                }
            }
        }
        catch(AcountDAOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

}