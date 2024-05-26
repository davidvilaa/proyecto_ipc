/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package ViewGasto;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author Borja Rodríguez Vict
 */
public class FOTOGRANDE_FXMLController implements Initializable {

    @FXML
    private AnchorPane AnchorPane;
    @FXML
    private ImageView imageView;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    
    public void initImage(Image im){
    AnchorPane.setMinWidth(im.getWidth());
        AnchorPane.setMaxWidth(im.getWidth());
        AnchorPane.setMaxHeight( im.getHeight());
        AnchorPane.setMinHeight(im.getHeight());
        imageView.setFitHeight(im.getHeight());
        imageView.setFitWidth(im.getWidth());
        imageView.setImage(im);
        
    }
    }

