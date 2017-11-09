package controllers;

import Builders.PlanetBuilder;
import javafx.application.Platform;
import javafx.collections.MapChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javafx.scene.image.Image;
import models.*;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;

public class PlanetController {

    @FXML
    private ImageView planetImage;

    @FXML
    private Button selectImageButton;

    @FXML
    private TextField planetName;

    @FXML
    private TextField planetDiameterKM;

    @FXML
    private TextField planetDiameterM;

    @FXML
    private TextField planetMeanSurfaceTempC;

    @FXML
    private TextField planetMeanSurfaceTempF;

    @FXML
    private TextField planetNumberOfMoons;

    @FXML
    private Label fancyPlanetName;

    @FXML
    private Text nameError;

    @FXML
    private Text temperatureError;

    @FXML
    private Text diameterError;

    @FXML
    private Text moonsError;

    private final FileChooser fileChooser = new FileChooser();
    private Stage stage;
    private Planet currentPlanet;
    private PlanetValidator planetValidator;

    public PlanetController(Stage stage){
        this.stage = stage;
        this.currentPlanet = new Planet();
        this.planetValidator = new PlanetValidator();
    }

    public void initializeComponents(){

        initializeTextListeners();

        initializeErrorListeners();

        planetImage.setImage(new Image("images/no_image.png"));
    }

    private void initializeTextListeners(){
        planetName.textProperty().addListener((obj, oldText, newText) -> {
            handleNameChange(newText, oldText);
        });

        planetDiameterKM.textProperty().addListener((obj, oldText, newText) -> {
            //Need to invoke the update since it alters the text field value, which
            //can not be done by the listener
            Platform.runLater(() -> {
                handleDiameterChange(newText, oldText);
                planetDiameterKM.end();
            });
        });

        planetMeanSurfaceTempC.textProperty().addListener((obj, oldText, newText) -> {
            handleTemperatureChange(newText, oldText);
        });

        planetNumberOfMoons.textProperty().addListener((obj, oldText, newText) -> {
            handleMoonsChange(newText, oldText);
        });
    }

    private void initializeErrorListeners(){

        HookErrorToText(PlanetAttribute.NAME, nameError);

        HookErrorToText(PlanetAttribute.MOONS, moonsError);

        HookErrorToText(PlanetAttribute.DIAMETER, diameterError);

        HookErrorToText(PlanetAttribute.TEMPERATURE, temperatureError);

        HookErrorToAlert(PlanetAttribute.IMAGE_FILE, Alert.AlertType.ERROR);
    }

    private void HookErrorToText(PlanetAttribute attribute, Text text){

        currentPlanet.addErrorListener(new MapChangeListener<PlanetAttribute, String>() {
            @Override
            public void onChanged(Change<? extends PlanetAttribute, ? extends String> change) {
                if(change.getKey() == attribute){
                    text.setText(change.getMap().get(attribute));
                }else if(!change.getMap().containsKey(attribute)){
                    text.setText("");
                }
            }
        });
    }

    private void HookErrorToAlert(PlanetAttribute attribute, Alert.AlertType alertType){
        currentPlanet.addErrorListener(new MapChangeListener<PlanetAttribute, String>() {
            @Override
            public void onChanged(Change<? extends PlanetAttribute, ? extends String> change) {

                if(change.getKey() == attribute && change.getMap().get(attribute) != null){
                    Alert fileAlert = new Alert(alertType);
                    fileAlert.setContentText(change.getMap().get(attribute));
                    fileAlert.showAndWait();
                }

            }
        });
    }

    private void loadCurrentPlanet(){
        planetName.setText(currentPlanet.getName());
        planetMeanSurfaceTempC.setText(String.valueOf(currentPlanet.getTemperature()));
        planetDiameterKM.setText(String.valueOf(currentPlanet.getDiameter()));
        planetNumberOfMoons.setText(String.valueOf(currentPlanet.getNumberOfMoons()));
        planetImage.setImage(currentPlanet.getImage());
    }

    @FXML
    void selectImage(ActionEvent event) {
        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            currentPlanet.setImageFile(file);
            planetImage.setImage(currentPlanet.getImage());
        }
    }

    @FXML
    void confirmLoadPlanet(ActionEvent event){
        File file = fileChooser.showOpenDialog(stage);

        if (file != null) {

            confirmLoadAlert.showAndWait();

            if(confirmLoadAlert.getResult() == confirm){
                loadPlanet(file);
            }

        }
    }

    private void loadPlanet(File file){
        try {
            currentPlanet = PlanetBuilder.BuildPlanetFromFile(file);
            loadCurrentPlanet();
        } catch (IOException e) {
            Alert failedLoadAlert = new Alert(Alert.AlertType.ERROR);
            failedLoadAlert.setContentText("Failed to load planet.");
            failedLoadAlert.show();
        }
    }

    @FXML
    void savePlanet(ActionEvent event){

        File file = fileChooser.showSaveDialog(stage);
        if (file != null) {
            try{
                currentPlanet.saveToFile(file);
            } catch (Exception e) {
                Alert failedSaveAlert = new Alert(Alert.AlertType.ERROR);
                failedSaveAlert.setContentText(e.getMessage());
                failedSaveAlert.show();
            }
        }

    }

    private void handleNameChange(String newName, String oldName){

        currentPlanet.setName(newName);

        fancyPlanetName.setText(currentPlanet.getName());
    }

    private void handleTemperatureChange(String newCelsius, String oldCelsius){

        currentPlanet.setTemperature(newCelsius);

        Double newTempF = (32 + (currentPlanet.getTemperature()*9 / 5));

        DecimalFormat df = new DecimalFormat("#.##");

        planetMeanSurfaceTempF.setText(df.format(newTempF));

    }

    private void handleDiameterChange(String newKM, String oldKM){

        currentPlanet.setDiameter(newKM);

        int newDiameterM = currentPlanet.getDiameter() * 1000;

        if(currentPlanet.getDiameter() > 1000){
            planetDiameterKM.setText(String.format("%,d", currentPlanet.getDiameter()));
        }

        planetDiameterM.setText(String.format("%,d", newDiameterM));

    }

    private void handleMoonsChange(String newMoons, String oldMoons){

        currentPlanet.setNumberOfMoons(newMoons);

    }

    class ConfirmationAlert{

        ConfirmationAlert(String message){
            ButtonType confirm = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
            ButtonType cancel = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
            Text messageText = new Text(message);
            messageText.setWrappingWidth(200);
            Alert confirmLoadAlert = new Alert(Alert.AlertType.WARNING, null, confirm, cancel);
            confirmLoadAlert.getDialogPane().setContent(messageText);
            confirmLoadAlert.getDialogPane().setPadding(new Insets(10, 10, 10, 10));
        }
    }
}