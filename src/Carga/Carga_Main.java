package Carga;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.PauseTransition;
import javafx.application.*;
import static javafx.application.Application.launch;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.image.Image;
import javafx.stage.*;
import javafx.util.Duration;

public class Carga_Main extends Application {
    private static Scene scene;
    private static Stage mainStage;

    @Override
    public void start(Stage stage) throws Exception {
        
        mainStage = stage;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Carga_FXML.fxml"));
        Parent root = loader.load();
        scene = new Scene(root);
        mainStage.setScene(scene);
        mainStage.getIcons().add(new Image("./assets/ww_black.png"));

        mainStage.setResizable(false);
        mainStage.show();
        
        
        PauseTransition pause = new PauseTransition(Duration.seconds(4));
        pause.setOnFinished(event -> {
            try {
                FXMLLoader loader2 = new FXMLLoader(getClass().getResource("/Login/Login_FXML.fxml"));
                Parent root2 = loader2.load();
                scene = new Scene(root2);
                mainStage.setScene(scene);
                mainStage.setTitle("Login Usuario");
                mainStage.getIcons().add(new Image("./assets/ww_black.png"));
                mainStage.setResizable(false);
                mainStage.show();
            } catch (IOException ex) {
                Logger.getLogger(Carga_FXMLController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        pause.play();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

