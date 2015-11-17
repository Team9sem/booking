package com.team9.bookingsystem.Controllers;

import java.awt.Button;
import java.awt.TextField;
import java.util.ArrayList;

import com.team9.bookingsystem.MysqlUtil;
import com.team9.bookingsystem.Room;
import com.team9.bookingsystem.User;
import com.team9.bookingsystem.Threading.FindRoomService;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.TextAlignment;
import javafx.util.Callback;

public class AdminController {
	
	//Logged in user
    User loggedInUser;
    // Parent Controller
    private MainController mainController;
    // Mysqlutil for Database Operations
    private MysqlUtil util;
    // ArrayList Storing the most recent searchResult;
    private ArrayList<Room> searchResult;
    private Room selectedRoom;
    private ToggleGroup toggleGroup;
    private Object selectedButton;

 // ContainerElements
    @FXML AnchorPane topAnchorPane;
    @FXML AnchorPane searchAnchorPane;
    @FXML BorderPane borderPane;
    @FXML HBox paginationBox;
    @FXML AnchorPane resultAnchorPane;
    @FXML GridPane searchOptions;
    @FXML Label loginLabel;
    @FXML Label searchPreferences;
    @FXML Label searchFor;
    @FXML ToggleButton userToggle;
    @FXML ToggleButton roomToggle;

//    @FXML Label features;

    
  
//    @FXML Label location;
//    @FXML ChoiceBox locationPick;
//    @FXML Button searchButton;
    
    public void initialize() {


//        setupDatePicker();
    	util = new MysqlUtil();
        paginationBox.setAlignment(Pos.CENTER);
        userToggle.setToggleGroup(toggleGroup);
        roomToggle.setToggleGroup(toggleGroup);
        
//        ObservableList<String> choices= FXCollections.observableArrayList();
//        choices.addAll("OneChoice");
//        locationPick.setItems(choices);


    }
   
    public void init(MainController mainController,User admin){
        this.mainController = mainController;
        this.loggedInUser = admin;

    }
    
    public VBox createPage(int pageIndex){

        VBox vBox = new VBox(5);

        vBox.setAlignment(Pos.CENTER);
        
        GridPane grid =new GridPane();
 
       
        toggleGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
                if(newValue!=null){
                	
//               
                    selectedButton = toggleGroup.getSelectedToggle();
                    System.out.println(selectedButton.toString());
                }
            }
        });
        
        return vBox;

        }

    
    
    private int getElementsPerPage(){
        return 5;
    }
   
    @FXML public void getUserSearch(ActionEvent event){
    	
    	if (selectedButton == userToggle) {
    		
    	}
    	
    }
}