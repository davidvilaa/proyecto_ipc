/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Configurar;

/**
 *
 * @author Borja Rodríguez Vict
 */
import javafx.application.*;
import static javafx.application.Application.launch;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.image.Image;
import javafx.stage.*;

public class Configurar_Main extends Application {
    
    public static Scene scene;
    public static Stage mainStage;
    
    @Override
    public void start(Stage stage) throws Exception {
        mainStage = stage;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Configurar_FXML.fxml"));
        Parent root = loader.load();
        scene = new Scene(root);
        mainStage.setScene(scene);
        mainStage.setTitle("Configuración");
        mainStage.getIcons().add(new Image("./assets/ww_black.png"));
        mainStage.setResizable(false);
        mainStage.setFullScreen(false);
        mainStage.show();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
