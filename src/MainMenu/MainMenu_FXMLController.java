package MainMenu;



import Login.Login_FXMLController;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import model.*;
import java.io.*;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;

public class MainMenu_FXMLController implements Initializable {

    @FXML
    private Label bienvenido;
    @FXML
    private ImageView fotoperfil;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
         
            bienvenido.setText(Login_FXMLController.getNombrecuentaGastos());
            fotoperfil.setImage(Login_FXMLController.getImagencuentaGastos());
        }


    @FXML
    private void usuariomenu(MouseEvent event) {
    }

    

    @FXML
    private void usuariopasar(MouseEvent event) {
        bienvenido.setStyle("-fx-underline: true;");
        fotoperfil.setStyle("-fx-underline: true;");
    }

    @FXML
    private void usuarionopasar(MouseEvent event) {
        bienvenido.setStyle("-fx-underline: false;"); 
        fotoperfil.setStyle("-fx-underline: false;");
    }
       
        } 
       

