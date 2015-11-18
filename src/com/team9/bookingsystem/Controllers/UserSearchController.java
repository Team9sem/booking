package com.team9.bookingsystem.Controllers;

import java.awt.Button;
import java.awt.TextField;
import java.util.ArrayList;

import com.team9.bookingsystem.MysqlUtil;
import com.team9.bookingsystem.Room;
import com.team9.bookingsystem.User;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
    private MysqlUtil util;
    // ArrayList Storing the most recent searchResult;
    private ArrayList<Room> searchResult;
    private Room selectedRoom;
    private Button selectedButton;

 // ContainerElements
    @FXML AnchorPane topAnchorPane;
    @FXML AnchorPane searchAnchorPane;
    @FXML BorderPane borderPane;
    @FXML HBox paginationBox;
    @FXML AnchorPane resultAnchorPane;
    
    
    @FXML Label searchPreferences;
    @FXML Label searchForUser;
    @FXML Label adminRoomLabel;
    @FXML TextField userID; 
    @FXML TextField userName;
    @FXML TextField firstName;
    @FXML TextField lastName;
    @FXML TextField userType;
//    @FXML Label features;
   
    @FXML Button adminSearchButton;
	
	 public void initialize() {


//       setupDatePicker();
   	
      
//       ObservableList<String> choices= FXCollections.observableArrayList();
//       choices.addAll("OneChoice");
//       locationPick.setItems(choices);


 }
  
   public void init(MainController mainController,User admin){
       this.mainController = mainController;
       this.loggedInUser = admin;

   }
 
   

}
