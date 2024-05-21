package Login;

import javafx.application.*;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.image.Image;
import javafx.stage.*;

public class Login_Main extends Application {
    
    private static Scene scene;
    private static Stage mainStage;
<<<<<<< HEAD
=======
    
    static void setRoot(Parent root){
        mainStage.getScene().setRoot(root);
    }
>>>>>>> 1be3e6c1ccd2e951fee9331a9fca851b22b2578b
    
    @Override
    public void start(Stage stage) throws Exception {
        mainStage = stage;
<<<<<<< HEAD
        
=======
>>>>>>> 1be3e6c1ccd2e951fee9331a9fca851b22b2578b
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Login_FXML.fxml"));
        Parent root = loader.load();
        
        scene = new Scene(root);
<<<<<<< HEAD
        
=======
>>>>>>> 1be3e6c1ccd2e951fee9331a9fca851b22b2578b
        mainStage.setScene(scene);
        mainStage.setTitle("Login Usuario");
        mainStage.getIcons().add(new Image("./assets/ww_black.png"));
        mainStage.setResizable(false);
        mainStage.show();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}