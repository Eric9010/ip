package monet.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import monet.Monet;

/**
 * Controller for MainWindow. Provides the layout for the main window and handles user interaction.
 */
public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private Monet monet;

    // TODO: Replace with your own images in the resources folder
    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/user.png"));
    private Image monetImage = new Image(this.getClass().getResourceAsStream("/images/monet.png"));

    /**
     * Initializes the controller. Binds the scroll pane to the dialog container's height.
     * Displays the welcome message.
     */
    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
        // Show the welcome message on startup
        dialogContainer.getChildren().add(
                DialogBox.getMonetDialog(Monet.getWelcomeMessage(), monetImage)
        );
    }

    /**
     * Sets the Monet instance for this controller.
     * @param m The Monet instance.
     */
    public void setMonet(Monet m) {
        monet = m;
    }

    /**
     * Creates two dialog boxes, one for user input and one for Monet's reply.
     * Appends them to the dialog container and clears the user input field.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        String response = monet.getResponse(input); // The core interaction
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getMonetDialog(response, monetImage)
        );
        userInput.clear();
    }
}
