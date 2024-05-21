package Register;

import java.io.File;
import java.net.URL;
import java.util.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.*;
import javafx.event.*;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;    
import model.*;
import java.io.IOException;
import java.time.LocalDate;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;

public class Register_FXMLController implements Initializable {
    
    @FXML
    public TextField name;
    @FXML
    public TextField nickname;
    @FXML
    public PasswordField password;
    @FXML
    public TextField mail;
    @FXML
    private Button selectArchive;
    @FXML
    public ImageView image;
    @FXML
    private Button register;
    @FXML
    private Label register_error;
    @FXML
    private Button atras;

    @FXML
    public void selectArchiveHandle(ActionEvent event){
        FileChooser fc = new FileChooser();
        fc.setTitle("Selecciona una Imágen");
        
        fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("*.png","*.jpg","*.jpeg"));
        
        File file = fc.showOpenDialog(new Stage());
        
        if(file != null){
            Image file_image = new Image(file.toURI().toString());
            image.setImage(file_image);
            selectArchive.setVisible(false);
            image.setVisible(true);
            
        }
    }
    
    @FXML
    public void registerHandle(ActionEvent event) throws AcountDAOException, IOException{
        register_error.setText(" ");
        try{
            if(!(name.getText().equals("")) && !(nickname.getText().equals("")) && !(password.getText().equals("")) && !(mail.getText()).equals("")){
                Acount cuentaGastos = Acount.getInstance();
                LocalDate now = LocalDate.now();
                cuentaGastos.registerUser(name.getText(), name.getText(), mail.getText(), nickname.getText(), password.getText(), image.getImage(), now);
                
                goToLogin();
            }
        }
        catch(AcountDAOException e){
            register_error.setText("ERROR: Este usuario ya existe");
        }
    }
    
    @FXML
    public void atrasHandle(ActionEvent event) throws IOException{
        goToLogin();
    }
    
    public void goToLogin() throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Login/Login_FXML.fxml"));
        Parent root = loader.load();
        
        Register_Main.setRoot(root);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        image.setVisible(false);
        image.setImage(new Image("./assets/default_user.png"));
    }
}
