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
public class LoginController
{
    private MainController mainController;
    private MysqlUtil util;
    @FXML Label loginLabel;
    @FXML TextField username;
    @FXML PasswordField password;
    @FXML Button back;
    @FXML Button login;

    public void initialize() {
        util = new MysqlUtil();
    }


    public void init(MainController mainController){
        this.mainController = mainController;
    }

    @FXML public void register(){


    }
    @FXML public void login(){
        try{

            User loggedInUser = util.loginAndGetUser(username.getText(), password.getText());
            System.out.println(loggedInUser.toString());



        }catch(Exception e){
            System.out.println(e.getMessage());
        }

    }



}
