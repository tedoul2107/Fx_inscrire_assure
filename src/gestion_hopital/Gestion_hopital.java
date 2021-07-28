package gestion_hopital;

import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author ULRICH TEDONGMO
 */
public class Gestion_hopital extends Application{

    @Override
    public void start(Stage primaryStage) {
        try{
            Parent parent = FXMLLoader.load(getClass().getResource("/interfaceFx/Authentification.fxml"));
            Scene scene = new Scene(parent);
            primaryStage.setScene(scene);
            primaryStage.setResizable(false);
            primaryStage.show();
        }catch(Exception ex){
            Logger.getLogger(Gestion_hopital.class.getName()).log(Level.SEVERE, null, ex);
        }
    }  
    
    public static void main(String[] args) {
        // TODO code application logic here
        launch(args);
    }
    
}
