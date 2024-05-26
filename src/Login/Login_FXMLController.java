package Login;

import MainMenu.MainMenu_Main;
import java.net.URL;
import java.util.*;
import javafx.fxml.*;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.event.*;
import java.io.IOException;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import model.*;

public class Login_FXMLController implements Initializable{

    @FXML
    public TextField nickname;
    @FXML
    public PasswordField password;
    @FXML
    public Button login_button;
    
    public static Acount cuentaGastos;
    
    public static User getCuenta(){
        return cuentaGastos.getLoggedUser();
    }
    
    public static String getNombrecuentaGastos() {
        return cuentaGastos.getLoggedUser().getName();
    }
    public static Image getImagencuentaGastos() {
        return cuentaGastos.getLoggedUser().getImage();
    }
    @FXML
    private Label login_error;
    
    @FXML
    private void goToRegister(ActionEvent event) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("/Register/Register_FXML.fxml"));
        Scene scene = new Scene(root);
        String css = this.getClass().getResource("/estilos/botonesLogin.css").toExternalForm();
        scene.getStylesheets().add(css);
        Stage window = (Stage) nickname.getScene().getWindow();
        window.setScene(scene);
        window.setTitle("Registro");
        window.show();
    }
    
    @FXML
    private void keyEnterLogin(KeyEvent event) throws IOException{
        if(event.getCode() == KeyCode.ENTER){
            login();
        }
    }

    @FXML
    private void loginHandle(ActionEvent event) throws IOException{
        login();
    }
    
    public void login() throws IOException{
        try{
            cuentaGastos = Acount.getInstance();
            if(!(nickname.getText().trim().isEmpty()) && !(password.getText().trim().isEmpty())){
                if(cuentaGastos.logInUserByCredentials(nickname.getText(), password.getText())){
                    Parent root2 = FXMLLoader.load(getClass().getResource("/MainMenu/MainMenu_FXML.fxml"));
                    Scene scene2 = new Scene(root2);
                    String css = this.getClass().getResource("/estilos/botonesLogin.css").toExternalForm();
                    scene2.getStylesheets().add(css);
                    Stage window2 =(Stage) nickname.getScene().getWindow();
                    window2.setScene(scene2);
                    window2.setTitle("Menú Principal");
                    window2.show();
                    window2.setResizable(true);
                    
                }
                else{
                    login_error.setText("Usuario o Contraseña incorrectos");
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