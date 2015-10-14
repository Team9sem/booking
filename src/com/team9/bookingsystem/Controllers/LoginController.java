package com.team9.bookingsystem.Controllers;

import com.team9.bookingsystem.MysqlUtil;
import com.team9.bookingsystem.User;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 * Created by pontuspohl on 14/10/15.
 */


// Controller for login.fxml
public class LoginController
{

    // Parent Controller
    private MainController mainController;
    // Mysqlutil for Database Operations
    private MysqlUtil util;
    // Variables mapped to fxml elements
    @FXML Label loginLabel;
    @FXML TextField username;
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

    }
    // Validate user input with the database.
    @FXML public void login(){
        try{

            User loggedInUser = util.loginAndGetUser(username.getText(), password.getText());
            System.out.println(loggedInUser.toString());
            // TODO Display the booking interface



        }catch(Exception e){
            System.out.println(e.getMessage());
            // Todo: show error message in GUI
        }

    }



}
