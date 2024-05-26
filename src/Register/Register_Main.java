package Register;

import javafx.application.*;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.image.Image;
import javafx.stage.*;

public class Register_Main extends Application {
    
    private static Scene scene;
    private static Stage mainStage;
    
    @Override
    public void start(Stage stage) throws Exception {
        mainStage = stage;
        
        FXMLLoader loader= new FXMLLoader(getClass().getResource("Register_FXML.fxml"));
        Parent root = loader.load();
        
        scene = new Scene(root);
        String css = this.getClass().getResource("/estilos/botonesLogin.css").toExternalForm();
        scene.getStylesheets().add(css);
        mainStage.setScene(scene);
        mainStage.setTitle("Registrar Usuario");
        mainStage.getIcons().add(new Image("./assets/ww_black.png"));
        mainStage.setResizable(false);
        mainStage.show();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
