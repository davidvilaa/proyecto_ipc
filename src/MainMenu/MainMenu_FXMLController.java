package MainMenu;



import AddGasto.AddGasto_Main;
import Login.Login_FXMLController;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.*;
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
import javafx.scene.control.*;
import javafx.event.*;
import javafx.scene.Parent;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MainMenu_FXMLController implements Initializable {

    @FXML
    private Label bienvenido;
    @FXML
    private ImageView fotoperfil;
    @FXML
    private Button goToAddGasto;
    @FXML
    private Button goToCategory;
    @FXML
    private Button goToVisualizar;
    @FXML
    private Button goToModificar;
    @FXML
    private Button goToBorrar;

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
    
    @FXML
    private void goToAddGastoHandle(ActionEvent event) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AddGasto/AddGasto_FXML.fxml"));
        Parent root = loader.load();
        
        Stage stage = new Stage();
        stage.setTitle("Añadir Gasto");
        stage.setScene(new Scene(root));
        
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(goToAddGasto.getScene().getWindow());
        
        stage.show();
    }
    
    @FXML
    private void goToCategoryHandle(ActionEvent event) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Category/Category_FXML.fxml"));
        Parent root = loader.load();
        
        Stage stage = new Stage();
        stage.setTitle("Categorías");
        stage.setScene(new Scene(root));
        
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(goToCategory.getScene().getWindow());
        
        stage.show();
    }
       
} 
