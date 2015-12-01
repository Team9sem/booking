package com.team9.bookingsystem.Controllers;

/**
 * by Nima
 */
import java.text.ParseException;
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

        int id = 0;
        long pNumberValue = 0;
        int zip = 0;
        int usertypeValue = 15;
        try{
            if(!ID.getText().isEmpty()){

                id = Integer.parseInt(ID.getText());
            }
            if(!pNumber.getText().isEmpty()){
                pNumberValue = Long.parseLong(pNumber.getText());
            }
            if(!zipCode.getText().isEmpty()){
                zip = Integer.parseInt(zipCode.getText());
            }
            if(!userType.getText().isEmpty()){

                usertypeValue = Integer.parseInt(userType.getText());
                if(usertypeValue > 4 || usertypeValue < 0){
                    usertypeValue = 15;
                }
            }


        }catch(NumberFormatException e){
            e.printStackTrace();
        }


            User user = new User(id,userName.getText(),
                    "",
                    firstName.getText(),
                    lastName.getText(),
                    usertypeValue,
                    "",
                    pNumberValue,
                    zip,
                    null);

             System.out.println(user.toString());
            System.out.println("Clicked searchbutton");
            adminController.searchForUsers(user);






    }





}
