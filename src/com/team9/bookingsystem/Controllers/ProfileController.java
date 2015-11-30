package com.team9.bookingsystem.Controllers;

import com.team9.bookingsystem.User;

import javafx.fxml.FXML;
import javafx.scene.*;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;

public class ProfileController {
	
	private MainController mainController;
	private User loggedInUser;
	
	@FXML AnchorPane UserProfileAnchor;
	@FXML TableView<?> currentBookings;
	@FXML TableView<?> bookingHistory;
	@FXML Label profileTitle;
	@FXML Label userInfo;
	@FXML Label UserName;
	@FXML Label passWord;
	@FXML Label firstName;
	@FXML Label lastName;
	@FXML Label adress;
	@FXML Label ssd;
	@FXML Label zipCode;

//	
//	public void init(MainController mainController){
//		 this.mainController = mainController;
////		 this.loggedInUser = user;
//		
		

	
	public void showCurrentBookings(){
		
	}
	
}

 