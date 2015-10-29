package com.team9.bookingsystem.Controllers;

import com.team9.bookingsystem.MysqlUtil;
import com.team9.bookingsystem.User;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 * Created by Olle Renard and Nima Fard 22 October 2015
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

    // this method runs when controller is started
    public void initialize() {
        util = new MysqlUtil();
    }

    // takes a reference to the controller of the parent
    public void init(MainController mainController){
        this.mainController = mainController;
    }
    //  Method that displays welcomeArea
    @FXML public void showWelcomeArea(){
        // TODO: take user back to welcome area
        mainController.showWelcomeArea();
    }
    // Validate user input with the database.
    @FXML public void register(){
        try{
        	// Perform Register logic
           username.getText();
           System.out.println(username);



        }catch(Exception e){
            System.out.println(e.getMessage());
            // Todo: show error message in GUI
        }

    }



}