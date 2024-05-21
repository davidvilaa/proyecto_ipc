package Register;

import javafx.application.*;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.image.Image;
import javafx.stage.*;

public class Register_Main extends Application {
    
    private static Scene scene;
    
    static void setRoot(Parent root){
        scene.setRoot(root);
    }
    
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader= new FXMLLoader(getClass().getResource("Register_FXML.fxml"));
        Parent root = loader.load();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Registrar Usuario");
        stage.getIcons().add(new Image("./assets/ww_black.png"));
        stage.setResizable(false);
        stage.show();
    }
    
    public static void main(String[] args) {
        launch(args);
        System.out.println("borja marica");
    }
}
