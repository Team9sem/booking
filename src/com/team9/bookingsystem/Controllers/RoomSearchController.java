package com.team9.bookingsystem.Controllers;


/**
 * By Nima
 */

import java.util.ArrayList;

import com.team9.bookingsystem.MysqlUtil;
import com.team9.bookingsystem.Room;
import com.team9.bookingsystem.Threading.Admin.RoomSearchService;
import com.team9.bookingsystem.User;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

public class RoomSearchController {

	// Logged in user
	User loggedInUser;
	// Parent Controller
	private MainController mainController;
	private AdminController adminController;
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
	@FXML TextField roomID;
//	@FXML TextField location;
	@FXML TextField locationField;
	@FXML CheckBox hasWhiteboard;
	@FXML CheckBox hasCoffeMachine;
	@FXML CheckBox hasProjector;
	@FXML CheckBox small;
	@FXML CheckBox medium;
	@FXML CheckBox large;

	// @FXML Label features;



	@FXML
	Button adminSearchButton;

	public void initialize() {


	}

	public void init(MainController mainController,AdminController adminController,User admin){
		this.mainController = mainController;
		this.loggedInUser = admin;
		this.adminController = adminController;

	}

	@FXML public void Search(ActionEvent event){

		int id = 0;
		int projector = 0;
		int coffeMachine = 0;
		int whiteBoard  = 0;

		if(hasProjector.isSelected()) projector = 1;
		if(hasCoffeMachine.isSelected()) coffeMachine = 1;
		if(hasWhiteboard.isSelected()) whiteBoard = 1;

		try{
			if(!roomID.getText().isEmpty()){
				id = Integer.parseInt(roomID.getText());
			}




		}catch(NumberFormatException e){
			e.printStackTrace();
		}



		Room room = new Room(id,locationField.getText(),"",projector,whiteBoard,coffeMachine);

		adminController.searchForRooms(room,small.isSelected(),medium.isSelected(),large.isSelected());
		System.out.println("Clicked searchbutton");
//         adminController.searchForRooms(room);
	}

}