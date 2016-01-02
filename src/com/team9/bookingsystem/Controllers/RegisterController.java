package com.team9.bookingsystem.Controllers;

import com.team9.bookingsystem.MysqlUtil;
import com.team9.bookingsystem.Threading.LoginService;
import com.team9.bookingsystem.User;
import javafx.beans.property.BooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by Olle Renard and Nima Fard 22 October 2015
 * Controller for register.fxml
 */

//Mayra added additional error checking on 12 December 2015
//Based on the framework created by Pontus

// Controller for login.fxml
public class RegisterController
{

    // Parent Controller
    private MainController mainController;
    // Mysqlutil for Database Operations
    private MysqlUtil util;

    private ArrayList<ProgressIndicator> progressIndicators;
    // Variables mapped to fxml elements
    @FXML Label registerLabel;
    @FXML TextField username;
    @FXML TextField firstname;
    @FXML TextField lastname;
    @FXML TextField personnumber;
    @FXML TextField adress;
    @FXML TextField zip;
    @FXML PasswordField password;
    @FXML Button back;
    @FXML Button login;
    @FXML Label pNumberLabel;
    @FXML Label userNameProgressLabel;
    @FXML Label firstnameProgressLabel;
    @FXML Label lastnameProgressLabel;
    @FXML Label passwordProgressLabel;
    @FXML Label streetProgressLabel;
    @FXML Label pNumberProgressLabel;
    @FXML Label zipCodeProgressLabel;
    @FXML ProgressIndicator usernameProgress;
    @FXML ProgressIndicator firstnameProgress;
    @FXML ProgressIndicator lastnameProgress;
    @FXML ProgressIndicator passwordProgress;
    @FXML ProgressIndicator streetProgress;
    @FXML ProgressIndicator pNumberProgress;
    @FXML ProgressIndicator zipCodeProgress;
    @FXML ProgressBar passwordSecurityProgress;
    @FXML Label             passwordErrorLabel;

    // this method runs when controller is started

    /**
     * by Pontus
     */
    public void initialize() {


    }


    /**
     * by Pontus
     * @param mainController
     */
    // takes a reference to the controller of the parent
    public void init(MainController mainController){
        this.mainController = mainController;
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
        passwordSecurityProgress.setStyle("-fx-box-border: goldenrod;");
        pNumberLabel.setText("Social Security\nNumber");



    }
    //  Method that displays welcomeArea
    @FXML public void showWelcomeArea(){



        // TODO: take user back to welcome area
        mainController.showWelcomeArea();
    }

    /**
     * all by Pontus except for else-clause by Marya as commented
     */
    // Validate user input with the database.
    @FXML public void register(){



        System.out.println( progressIndicators.stream().filter(indicator -> indicator.getProgress() == 1.0).count());
        if( progressIndicators.stream().filter(indicator -> indicator.getProgress() == 1.0).count() ==
                progressIndicators.size())
        {



        try{
        	// Perform Register logic
           User registered = new User();

           Service<Boolean> registerService = new Service<Boolean>() {
               @Override
               
               protected Task<Boolean> createTask() {
                   Task<Boolean> task = new Task<Boolean>() {
                       @Override
                       protected Boolean call() throws Exception {
                           MysqlUtil util = new MysqlUtil();
                           registered.setUserName(username.getText());
                           registered.setFirstName(firstname.getText());
                           registered.setLastName(lastname.getText());
                           registered.setPassword(password.getText());
                           registered.setpNumber(Long.parseLong(personnumber.getText()));
                           registered.setStreet(adress.getText());
                           registered.setZip(Integer.parseInt(zip.getText()));
                           return util.RegisterUser(username.getText(),
                                   password.getText(),
                                   firstname.getText(),
                                   lastname.getText(),
                                   Long.parseLong(personnumber.getText())
                                   ,0,adress.getText()
                                   ,Integer.parseInt(zip.getText()));

                       }


                   };
                   return task;
               }
           };
           registerService.start();
            registerService.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
                @Override
                public void handle(WorkerStateEvent event) {
                    if(registered != null){
                        LoginService loginService = new LoginService(registered.getUserName(), registered.getPassword());
                        loginService.start();
                        loginService.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
                            @Override
                            public void handle(WorkerStateEvent event) {
                                User loggedInUser = (User)loginService.getValue();
                                mainController.showBookingInterface(loggedInUser);
                            }
                        });
                    }
                    System.out.println("Successfully registered");

                }
            });
            registerService.setOnFailed(new EventHandler<WorkerStateEvent>() {
                @Override
                public void handle(WorkerStateEvent event) {
                    System.out.println("it failed");
                }
            });




        }catch(Exception e){
            System.out.println(e.getMessage());
            // Todo: show error message in GUI
        }

        }
        else{
        	//Added by Mayra Soliz 12.12.2015
        	//The code in this else clause takes care of the case that 
        	//the user presses "Register" without filling out the fields 
        	//in this case a modal PopUp window opens to tell the user to complete the fields
        	//and sets  the labels to "this field cannot be empty"



        	//username.setText("-");
        	double tmp;
        	tmp = usernameProgress.getProgress();
        	if(tmp != 1.0) {
        		userNameProgressLabel.setVisible(true);
                userNameProgressLabel.setText("Please complete this field");
        	}
        	else {
        		userNameProgressLabel.setVisible(false);
        	}
        	tmp = passwordProgress.getProgress();
        	if(!passwordSecurityProgress.isVisible()) {
        		
        		passwordProgressLabel.setVisible(true); 
        		passwordProgressLabel.setText("Please complete this field");
        		
        		passwordErrorLabel.setText("Please complete this field");
        		passwordErrorLabel.setVisible(false); 
        	}
        	else {
        		passwordErrorLabel.setVisible(false); 
        	}
        	tmp = firstnameProgress.getProgress();
        	if(tmp != 1.0) {       	
        		firstnameProgressLabel.setVisible(true); 
        		firstnameProgressLabel.setText("Please complete this field");
        	}
        	else {
        		firstnameProgressLabel.setVisible(false); 
        	}
        	tmp = lastnameProgress.getProgress();
        	if(tmp != 1.0) {
        		lastnameProgressLabel.setVisible(true); 
            	lastnameProgressLabel.setText("Please complete this field");
        	}
        	else {
        		lastnameProgressLabel.setVisible(false); 
        	}
        	tmp = pNumberProgress.getProgress();
        	if(tmp != 1.0) {        		
        		pNumberProgressLabel.setVisible(true); 
        		pNumberProgressLabel.setText("Please complete this field");
        	}
        	else {
        		pNumberProgressLabel.setVisible(false); 
        	}
        	tmp = streetProgress.getProgress();
        	if(tmp != 1.0) { 
        		streetProgressLabel.setVisible(true); 
            	streetProgressLabel.setText("Please complete this field");
        	}
        	else {
        		streetProgressLabel.setVisible(false); 
        	}
        	tmp = zipCodeProgress.getProgress();
        	if(tmp != 1.0) { 
        		zipCodeProgressLabel.setVisible(true); 
        		zipCodeProgressLabel.setText("Please complete this field");
        	}
        	else {
        		zipCodeProgressLabel.setVisible(false); 
        	}
        	
        	Label secondLabel = new Label("Please complete the form");
             
            StackPane secondaryLayout = new StackPane();
            secondaryLayout.getChildren().add(secondLabel);
              
            Scene secondScene = new Scene(secondaryLayout, 200, 100);

            Stage secondStage = new Stage();
            secondStage.setTitle("Missing input");
            secondStage.setScene(secondScene);
            //Mayra make sure that the popup has control and that the user closes this window before continuing
            secondStage.initModality(Modality.APPLICATION_MODAL);
            secondStage.show();
        }
    }

    /**
     * bu Pontus
     * sets up error checking logic
     */
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

                        List<Boolean> safetys = Stream.of(hasCapital,hasDigit,hasLower)
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
                        passwordErrorLabel.setText("Please pick a password with\nat least 6 characters!");
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
                            pNumberProgressLabel.setText("Social Security Number\nmust be all digits!");
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
                            zipCodeProgressLabel.setText("Your zip code can only\ncontain numbers!");
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



}