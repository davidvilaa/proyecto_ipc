package MainMenu;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import model.*;
import java.io.*;

public class MainMenu_FXMLController implements Initializable {

    @FXML
    private Label bienvenido;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        /*try{
            Acount user = Acount.getInstance();
            bienvenido.setText(user.getLoggedUser().getName());
        }
        catch(AcountDAOException e){
          e.printStackTrace();
        }catch(IOException e){
            e.printStackTrace();
        } */
    }    
}
