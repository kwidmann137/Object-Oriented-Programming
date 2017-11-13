package Models;

import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.text.Text;

public class ConfirmationAlert {

    private Alert alert;
    private ButtonType confirm, cancel;

    /**
     * A wrapper to create a confirmation alert with custom buttons and message
     * @param message - the content to display in the alert
     */
    public ConfirmationAlert(String message){
        this.confirm = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
        this.cancel = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
        Text messageText = new Text(message);
        messageText.setWrappingWidth(250);
        this.alert = new Alert(Alert.AlertType.WARNING, null, confirm, cancel);
        this.alert.getDialogPane().setContent(messageText);
        this.alert.getDialogPane().setPadding(new Insets(0, 5, 5, 5));
    }

    /**
     * Show the alert and wait for user response
     */
    public void showAndWait(){
        alert.showAndWait();
    }

    /**
     * determine if user clicked the confirm button
     * @return - boolean - true if confirm clicked, false otherwise
     */
    public boolean confirmed(){
        return alert.getResult() == confirm;
    }
}
