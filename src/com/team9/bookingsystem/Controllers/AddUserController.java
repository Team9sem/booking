package com.team9.bookingsystem.Controllers;

import com.team9.bookingsystem.Components.DialogCallback;
import com.team9.bookingsystem.MysqlUtil;
import com.team9.bookingsystem.Components.PopupController;
import com.team9.bookingsystem.User;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by pontuspohl on 25/11/15.
 */
public class AddUserController implements PopupController {


    private ArrayList<ProgressIndicator> progressIndicators;

    @FXML private TextField username;
    @FXML private TextField firstname;
    @FXML private TextField lastname;
    @FXML private TextField personnumber;
    @FXML private TextField adress;
    @FXML private TextField zip;
    @FXML private PasswordField password;

    @FXML private Label userNameProgressLabel;
    @FXML private Label firstnameProgressLabel;
    @FXML private Label lastnameProgressLabel;
    @FXML private Label passwordProgressLabel;
    @FXML private Label streetProgressLabel;
    @FXML private Label pNumberProgressLabel;
    @FXML private Label zipCodeProgressLabel;
    @FXML private ProgressIndicator usernameProgress;
    @FXML private ProgressIndicator firstnameProgress;
    @FXML private ProgressIndicator lastnameProgress;
    @FXML private ProgressIndicator passwordProgress;
    @FXML private ProgressIndicator streetProgress;
    @FXML private ProgressIndicator pNumberProgress;
    @FXML private ProgressIndicator zipCodeProgress;
    @FXML private ProgressBar passwordSecurityProgress;
    @FXML private Label             passwordErrorLabel;


    private Stage stage;
    private DialogCallback callback;
    private User toAdd = null;
    private boolean okClicked = false;


    // Todo: Fix so error is displayed when input is missing from fields. Fix so added object gets returned correctly

    public void initialize() {
        setupErrorChecking();
        progressIndicators = new ArrayList<>();
        Stream<ProgressIndicator> indicators = Stream.of(usernameProgress,
                passwordProgress,firstnameProgress,lastnameProgress
                ,pNumberProgress,streetProgress,zipCodeProgress);
        indicators.forEach(indicator -> progressIndicators.add(indicator));
        progressIndicators.forEach(element -> element.setVisible(false));
        userNameProgressLabel.setVisible(false);
        firstnameProgressLabel.setVisible(false);
        lastnameProgressLabel.setVisible(false);
        passwordProgressLabel.setVisible(false);
        pNumberProgressLabel.setVisible(false);
        streetProgressLabel.setVisible(false);
        zipCodeProgressLabel.setVisible(false);
        passwordSecurityProgress.setVisible(false);
        passwordErrorLabel.setVisible(false);
    }

    @Override
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    private void setupErrorChecking(){

        username.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {

                usernameProgress.setProgress(-1.0);
                if(userNameProgressLabel.isVisible()){
                    userNameProgressLabel.setVisible(false);
                }
                if(!newValue.isEmpty()){

                    usernameProgress.setProgress(-1.0);
                    usernameProgress.setVisible(true);
                    Service<Boolean> getIsAvailable = new Service<Boolean>() {
                        @Override
                        protected Task<Boolean> createTask() {
                            Task<Boolean> task = new Task<Boolean>() {
                                @Override
                                protected Boolean call() throws Exception {
                                    MysqlUtil util = new MysqlUtil();
                                    System.out.println(newValue);
//                                event.getTableView().getItems().get(event.getTablePosition()
//                                        .getRow()).getUserName();
                                    return util.isUsernameAvailable(newValue);
                                }
                            };
                            return task;
                        }
                    };
                    getIsAvailable.start();

                    getIsAvailable.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
                        @Override
                        public void handle(WorkerStateEvent event) {

                            if(getIsAvailable.getValue()) {
                                System.out.println("is available");
                                usernameProgress.setProgress(1.0);

                            }
                            else{
                                usernameProgress.setVisible(false);
                                userNameProgressLabel.setText("Username Not Available!");
                                userNameProgressLabel.setVisible(true);
                                System.out.println("not available");
                            }
                        }
                    });
                    getIsAvailable.setOnFailed(new EventHandler<WorkerStateEvent>() {
                        @Override
                        public void handle(WorkerStateEvent event) {
                            System.out.println("error occured");
                        }
                    });

                }
                else{
                    usernameProgress.setVisible(false);
                    userNameProgressLabel.setVisible(true);
                    userNameProgressLabel.setText("This field cannot be empty!");
                }
            }
        });

        password.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                passwordProgress.setProgress(-1.0);
                if (passwordProgressLabel.isVisible()) {
                    passwordProgressLabel.setVisible(false);
                }
                if(passwordSecurityProgress.isVisible()){
                    passwordSecurityProgress.setVisible(false);
                }
                if(passwordErrorLabel.isVisible()){
                    passwordErrorLabel.setVisible(false);
                }
                if (!newValue.isEmpty())
                {
                    if(newValue.length() >= 6)
                    {

                        passwordProgress.setVisible(true);
                        passwordProgress.setProgress(-1.0);
                        boolean hasDigit   = false;
                        boolean hasCapital = false;
                        boolean hasLower   = false;
                        for (Character character : newValue.toCharArray())
                        {
                            if (Character.isDigit(character))
                            {
                                hasDigit = true;
                            }
                            if(Character.isLowerCase(character)){
                                hasLower = true;
                            }
                            if(Character.isUpperCase(character)){
                                hasCapital = true;
                            }
                        }

                        List<Boolean> safetys = Stream.of(hasCapital, hasDigit, hasLower)
                                .filter(aBoolean -> aBoolean)
                                .collect(Collectors.toList());
                        System.out.println(safetys.size());

                        if(safetys.size() == 3){
                            passwordProgress.setProgress(1.0);
                            passwordProgress.setVisible(false);
                            passwordProgressLabel.setVisible(true);
                            passwordProgressLabel.setText("Security: High");
                            passwordSecurityProgress.setProgress(1.0f);
                            passwordSecurityProgress.setStyle("-fx-accent: green;");
                            passwordSecurityProgress.setVisible(true);
                        }
                        else if(safetys.size() == 2){
                            passwordProgress.setProgress(1.0);
                            passwordProgress.setVisible(false);
                            passwordProgressLabel.setVisible(true);
                            passwordProgressLabel.setText("Security: Medium");
                            passwordSecurityProgress.setProgress(0.6f);
                            passwordSecurityProgress.setStyle("-fx-accent: orange;");
                            passwordSecurityProgress.setVisible(true);
                        }
                        else{
                            passwordProgress.setProgress(1.0);
                            passwordProgress.setVisible(false);
                            passwordProgressLabel.setVisible(true);
                            passwordProgressLabel.setText("Security: Low");
                            passwordSecurityProgress.setStyle("-fx-accent: red;");
                            passwordSecurityProgress.setProgress(0.3f);
                            passwordSecurityProgress.setVisible(true);
                        }
                    }
                    else{
                        passwordProgress.setVisible(false);
                        passwordErrorLabel.setVisible(true);
                        passwordErrorLabel.setText("Please pick a password with\n at least 6 characters");
                    }
                }
                else
                {
                    passwordProgress.setVisible(false);
                    passwordErrorLabel.setVisible(true);
                    passwordErrorLabel.setText("This field cannot be empty!");
                }
            }
        });

        firstname.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                firstnameProgress.setProgress(-1.0);
                if(firstnameProgressLabel.isVisible()){
                    firstnameProgressLabel.setVisible(false);
                }
                if(!newValue.isEmpty()) {

                    firstnameProgress.setVisible(true);
                    firstnameProgress.setProgress(-1.0);

                    for (Character character : newValue.toCharArray()) {
                        if (Character.isDigit(character)) {
                            // Todo: output error
                            firstnameProgress.setVisible(false);
                            firstnameProgressLabel.setText("Name Contains Numbers!");
                            firstnameProgressLabel.setVisible(true);
                            System.out.println("First Name is Numeric");
                            break;
                        } else {
                            firstnameProgress.setProgress(1.0);
                            System.out.println("First name is not numeric");
                        }
                    }
                }
                else{
                    firstnameProgress.setVisible(false);
                    firstnameProgressLabel.setVisible(true);
                    firstnameProgressLabel.setText("This field cannot be empty!");
                }
            }
        });
        lastname.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                lastnameProgress.setProgress(-1.0);

                if(lastnameProgressLabel.isVisible()){
                    lastnameProgressLabel.setVisible(false);
                }
                if(!newValue.isEmpty()) {

                    lastnameProgress.setVisible(true);
                    lastnameProgress.setProgress(-1.0);

                    for (Character character : newValue.toCharArray()) {
                        if (Character.isDigit(character)) {
                            // Todo: output error
                            lastnameProgress.setVisible(false);
                            lastnameProgressLabel.setText("Name Contains Numbers!");
                            lastnameProgressLabel.setVisible(true);
                            System.out.println("First Name is Numeric");
                            break;
                        } else {
                            lastnameProgress.setProgress(1.0);
                            System.out.println("First name is not numeric");
                        }
                    }
                }
                else{
                    lastnameProgress.setVisible(false);
                    lastnameProgressLabel.setVisible(true);
                    lastnameProgressLabel.setText("This field cannot be empty!");
                }
            }
        });
        personnumber.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                pNumberProgress.setProgress(-1.0);
                if(pNumberProgressLabel.isVisible()){
                    pNumberProgressLabel.setVisible(false);
                }
                if(!newValue.isEmpty()) {

                    pNumberProgress.setVisible(true);
                    pNumberProgress.setProgress(-1.0);

                    for (Character character : newValue.toCharArray()) {
                        if (!Character.isDigit(character)) {
                            // Todo: output error
                            pNumberProgress.setVisible(false);
                            pNumberProgressLabel.setText("Social Security Number must be all digits!");
                            pNumberProgressLabel.setVisible(true);
                            break;
                        } else {
                            pNumberProgress.setProgress(1.0);

                        }
                    }
                }
                else{
                    pNumberProgress.setVisible(false);
                    pNumberProgressLabel.setVisible(true);
                    pNumberProgressLabel.setText("This field cannot be empty!");
                }
            }
        });

        adress.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {

                streetProgress.setProgress(-1.0);
                if(streetProgressLabel.isVisible()){
                    streetProgressLabel.setVisible(false);
                }
                if(!newValue.isEmpty()) {

                    streetProgress.setVisible(true);
                    streetProgress.setProgress(1.0);


                }
                else{
                    streetProgress.setVisible(false);
                    streetProgressLabel.setVisible(true);
                    streetProgressLabel.setText("This field cannot be empty!");
                }
            }
        });

        zip.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {

                zipCodeProgress.setProgress(-1.0);
                if(zipCodeProgressLabel.isVisible()){
                    zipCodeProgressLabel.setVisible(false);
                }
                if(!newValue.isEmpty()) {

                    zipCodeProgress.setVisible(true);
                    zipCodeProgress.setProgress(-1.0);

                    for (Character character : newValue.toCharArray()) {
                        if (!Character.isDigit(character)) {


                            // Todo: output error
                            zipCodeProgress.setVisible(false);
                            zipCodeProgressLabel.setText("Your zip code can only contain numbers!");
                            zipCodeProgressLabel.setVisible(true);
//                            if(zip.getText().)
                            break;

                        }
                        else
                        {
                            zipCodeProgress.setProgress(1.0);

                        }
                    }
                }
                else{
                    zipCodeProgress.setVisible(false);
                    zipCodeProgressLabel.setVisible(true);
                    zipCodeProgressLabel.setText("This field cannot be empty!");
                }
            }
        });



    }

    public void setCallBack(DialogCallback callback){
        this.callback = callback;
    }

    @FXML public void cancel(ActionEvent event){
        callback.onFailure();
        stage.close();
    }
    @FXML public void addUser(ActionEvent event){
        okClicked = true;
        System.out.println( progressIndicators.stream().filter(indicator -> indicator.getProgress() == 1.0).count());
        if( progressIndicators.stream().filter(indicator -> indicator.getProgress() == 1.0).count() ==
                progressIndicators.size())
        {
            toAdd = new User();
            toAdd.setUserName(username.getText());
            toAdd.setFirstName(firstname.getText());
            toAdd.setLastName(lastname.getText());
            toAdd.setPassword(password.getText());
            toAdd.setpNumber(Long.parseLong(personnumber.getText()));
            toAdd.setStreet(adress.getText());
            toAdd.setZip(Integer.parseInt(zip.getText()));
            callback.onSuccess(toAdd);
        }
            else{
            callback.onFailure();
        }
        stage.close();
    }
    public boolean isOkClicked(){
        return okClicked;
    }



}
