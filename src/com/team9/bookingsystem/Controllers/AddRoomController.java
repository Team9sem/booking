package com.team9.bookingsystem.Controllers;

import com.team9.bookingsystem.Components.DialogCallback;
import com.team9.bookingsystem.Components.PopupController;
import com.team9.bookingsystem.Room;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.ArrayList;

/**
 * Created by pontuspohl on 30/11/15.
 * All Content by Pontus Pohl
 * Controller for addRoom.fxml popup view component.
 */
public class AddRoomController implements PopupController {


    @FXML private CheckBox  small;
    @FXML private CheckBox  medium;
    @FXML private CheckBox  large;
    @FXML private CheckBox  projector;
    @FXML private CheckBox  coffemachine;
    @FXML private CheckBox  whiteboard;
    @FXML private Label     locationErrorLabel;
    @FXML private Label     submitErrorLabel;
    @FXML private TextField locationField;
    @FXML private ProgressIndicator locationProgressIndicator;


    private ArrayList<CheckBox> sizeCheckBoxes;
    private Stage stage;
    private DialogCallback callback;
    private Room toAdd = null;
    private boolean okClicked = false;

    public void initialize(){
       sizeCheckBoxes = new ArrayList<>();
       sizeCheckBoxes.add(small);
        sizeCheckBoxes.add(medium);
        sizeCheckBoxes.add(large);

        setupErrorChecking();
    }



    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public boolean isOkClicked(){

        return okClicked;
    }
    public void setCallBack(DialogCallback callback){
        this.callback = callback;
    }
    @FXML public void addRoom(ActionEvent event){

        okClicked = true;
        String size = "";
        int projectorValue = 0, whiteboardValue = 0, coffemachineValue = 0;

        if (sizeCheckBoxes.stream().filter(checkBox -> checkBox.isSelected()).count() == 1){
            System.out.println("selected exactly one size");
            for(CheckBox checkBox : sizeCheckBoxes){
                if(checkBox.isSelected()){
                    size = checkBox.getText().substring(0,1);
                    System.out.println(size);
                }
            }
        }
        else{
            submitErrorLabel.setText("Error : Please select exactly one size for this room!");
            submitErrorLabel.setVisible(true);
            return;
        }
        if(locationField.getText().isEmpty()){
            submitErrorLabel.setText("Error : please provide the location of the Room");
            return;
        }
        if(!locationField.getText().isEmpty() && locationProgressIndicator.getProgress() != 1.0){
            submitErrorLabel.setText("Error : Location name is to short!");
            submitErrorLabel.setVisible(true);
            return;
        }

        if(projector.isSelected()) projectorValue = 1;
        if(coffemachine.isSelected()) coffemachineValue = 1;
        if(whiteboard.isSelected()) whiteboardValue = 1;

        toAdd = new Room(0,locationField.getText(),size,projectorValue,whiteboardValue,coffemachineValue);

        callback.onSuccess(toAdd);
        stage.close();





    }
    @FXML public void cancel(ActionEvent event){
        callback.onFailure();
        stage.close();
    }

    private void setupErrorChecking(){

        locationErrorLabel.setVisible(false);
        submitErrorLabel.  setVisible(false);
        locationProgressIndicator.setVisible(false);

        locationField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {

                locationProgressIndicator.setProgress(-1);
                if(locationProgressIndicator.isVisible()){
                    locationProgressIndicator.setVisible(false);
                }
                if(locationErrorLabel.isVisible()){
                    locationErrorLabel.setVisible(false);
                }
                if(!newValue.isEmpty() && newValue.length() < 4) {

                    locationErrorLabel.setText("Location name has to be\n" +
                            "at least 4 characters");
                    locationErrorLabel.setVisible(true);

                }
                else if(newValue.isEmpty()){

                    locationErrorLabel.setText("This field cannot be empty!");
                    locationErrorLabel.setVisible(true);
                }
                else{
                    locationProgressIndicator.setProgress(1.0);
                    locationProgressIndicator.setVisible(true);
                }

            }
        });
    }
}
