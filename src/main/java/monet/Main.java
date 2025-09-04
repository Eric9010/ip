package monet;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import monet.gui.MainWindow;

/**
 * A GUI for Monet using FXML.
 */
public class Main extends Application {

    // Instantiate the Monet application logic
    private Monet monet = new Monet("./data/monet.txt");

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            stage.setTitle("Monet");
            stage.setScene(scene);

            // Give the controller access to the Monet instance
            fxmlLoader.<MainWindow>getController().setMonet(monet);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
