package models;

import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.text.Text;

public class ConfirmationAlert {

    private Alert alert;
    private ButtonType confirm, cancel;

    public ConfirmationAlert(String message){
        this.confirm = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
        this.cancel = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
        Text messageText = new Text(message);
        messageText.setWrappingWidth(250);
        this.alert = new Alert(Alert.AlertType.WARNING, null, confirm, cancel);
        this.alert.getDialogPane().setContent(messageText);
        this.alert.getDialogPane().setPadding(new Insets(0, 5, 5, 5));
    }

    public void showAndWait(){
        alert.showAndWait();
    }

    public boolean confirmed(){
        return alert.getResult() == confirm;
    }
}
