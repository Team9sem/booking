package com.team9.bookingsystem.Controllers;

import com.team9.bookingsystem.MysqlUtil;
import com.team9.bookingsystem.User;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;

/**
 * Created by Olle Renard and Nima Fard 22 October 2015
 * Controller for login.fxml
 */


// Controller for login.fxml
public class RegisterController
{

    // Parent Controller
    private MainController mainController;
    // Mysqlutil for Database Operations
    private MysqlUtil util;
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

    // this method runs when controller is started
    public void initialize() {
        util = new MysqlUtil();

    }




    // takes a reference to the controller of the parent
    public void init(MainController mainController){
        this.mainController = mainController;
        setupErrorChecking();
    }
    //  Method that displays welcomeArea
    @FXML public void showWelcomeArea(){



        // TODO: take user back to welcome area
        mainController.showWelcomeArea();
    }
    // Validate user input with the database.
    @FXML public void register(){

        boolean isValid;

        for(Character character: firstname.getText().toCharArray() ){



        }





        try{
        	// Perform Register logic
           username.getText();
           System.out.println(username);
           Service<Boolean> registerService = new Service<Boolean>() {
               @Override
               protected Task<Boolean> createTask() {
                   Task<Boolean> task = new Task<Boolean>() {
                       @Override
                       protected Boolean call() throws Exception {

                           MysqlUtil util = new MysqlUtil();
                           return util.RegisterUser(username.getText(),
                                   password.getText(),
                                   firstname.getText(),
                                   lastname.getText(),
                                   Long.parseLong(personnumber.getText())
                                   ,"",adress.getText()
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

    private void setupErrorChecking(){

        username.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {

                if(userNameProgressLabel.isVisible()){
                    userNameProgressLabel.setVisible(false);
                }
                if(!newValue.isEmpty()){


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
                usernameProgress.setVisible(true);
                getIsAvailable.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
                    @Override
                    public void handle(WorkerStateEvent event) {

                        if(getIsAvailable.getValue()) {
                            System.out.println("is available");
                            
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
                }
            }
        });

        firstname.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                for(Character character: newValue.toCharArray() )
                {
                    if(Character.isDigit(character)){
                        // Todo: output error
                        System.out.println("First Name is Numeric");
                    }
                    else{
                        System.out.println("First name is not numeric");
                    }
                }

            }
        });
        lastname.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                for(Character character: newValue.toCharArray() )
                {
                    if(Character.isDigit(character)){
                        // Todo: output error
                        System.out.println("last Name is Numeric");
                    }
                    else{
                        System.out.println("last name is not numeric");
                    }
                }

            }
        });
        personnumber.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                for(Character character: newValue.toCharArray() )
                {
                    if(!Character.isDigit(character)){

                        System.out.println("socialSec is not numeric");
                    }
                    else{
                        // Todo: output error
                        System.out.println("Social Sec is  numeric");
                    }
                }

            }
        });
        zip.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                for(Character character: newValue.toCharArray() )
                {
                    if(!Character.isDigit(character)){

                        System.out.println("Zip is not numeric");
                    }
                    else{
                        // Todo: output error
                        System.out.println("Social Sec is  numeric");
                    }
                }

            }
        });



    }



}