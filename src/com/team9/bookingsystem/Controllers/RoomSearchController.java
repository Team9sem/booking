package com.team9.bookingsystem.Controllers;


/**
 * By Nima
 */
import java.awt.Button;
import java.awt.TextField;
import java.util.ArrayList;

import com.team9.bookingsystem.MysqlUtil;
import com.team9.bookingsystem.Room;
import com.team9.bookingsystem.User;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

public class RoomSearchController {

	// Logged in user
	User loggedInUser;
	// Parent Controller
	private MainController mainController;
	// Mysqlutil for Database Operations
	private MysqlUtil util;
	// ArrayList Storing the most recent searchResult;
	private ArrayList<Room> searchResult;
	private Room selectedRoom;
	private Button selectedButton;

	// ContainerElement
	@FXML GridPane roomSearchGridPane;

	@FXML Label searchPreferences;
	@FXML Label searchForUser;
	@FXML Label adminRoomLabel;
	@FXML TextField userID;
	@FXML TextField userName;
	@FXML TextField firstName;
	@FXML TextField lastName;
	@FXML TextField userType;
	// @FXML Label features;

	@FXML
	Button adminSearchButton;

	public void initialize() {

		// setupDatePicker();

		// ObservableList<String> choices= FXCollections.observableArrayList();
		// choices.addAll("OneChoice");
		// locationPick.setItems(choices);

	}

	public void init(MainController mainController, User admin) {
		this.mainController = mainController;
		this.loggedInUser = admin;

	}
	
    @FXML public void Search(ActionEvent event){
    	
    }
    
    
	   @FXML public void ShowAdminConsole(ActionEvent event){
	        mainController.showAdminConsole(loggedInUser);
	    }
}