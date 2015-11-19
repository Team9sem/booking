package com.team9.bookingsystem.Controllers;


import java.util.ArrayList;

import com.team9.bookingsystem.MysqlUtil;
import com.team9.bookingsystem.Room;
import com.team9.bookingsystem.User;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.text.TextAlignment;
import javafx.util.Callback;

public class UserSearchController {
	
	//Logged in user
    User loggedInUser;
    // Parent Controller
    private MainController mainController;
    // Mysqlutil for Database Operations
    private AdminController adminController;



 // ContainerElements
    @FXML GridPane userSearchGridPane;
    
    
    @FXML Label searchPreferences;
    @FXML Label searchForUser;

    @FXML TextField ID;
    @FXML TextField userName;
    @FXML TextField firstName;
    @FXML TextField lastName;
    @FXML TextField userType;
    @FXML TextField pNumber;
    @FXML TextField zipCode;
//    @FXML Label features;
   
    @FXML Button adminSearchButton;
	
	 public void initialize() {



 }
  
   public void init(MainController mainController,AdminController adminController,User admin){
       this.mainController = mainController;
       this.loggedInUser = admin;
       this.adminController = adminController;

   }

    @FXML public void Search(ActionEvent event){

        User user = new User(Integer.parseInt(ID.getText()),
                userName.getText(),
                "",
                firstName.getText(),
                lastName.getText(),
                "",
                Long.parseLong(pNumber.getText()),
                Integer.parseInt(zipCode.getText()));

        System.out.println("Clicked searchbutton");
        adminController.searchForUsers(user);


    }




}
