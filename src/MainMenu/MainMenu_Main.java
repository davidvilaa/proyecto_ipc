package MainMenu;

import javafx.application.*;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.image.Image;
import javafx.stage.*;

public class MainMenu_Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader= new FXMLLoader(getClass().getResource("MainMenu_FXML.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Menú Principal");
        stage.getIcons().add(new Image("./assets/ww_black.png"));
        stage.show();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
