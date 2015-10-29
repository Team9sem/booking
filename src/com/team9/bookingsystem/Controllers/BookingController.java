package com.team9.bookingsystem.Controllers;

import com.team9.bookingsystem.MysqlUtil;

import com.team9.bookingsystem.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

import javax.jws.soap.SOAPBinding;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class BookingController {

    // Parent Controller
    private MainController mainController;
    // Mysqlutil for Database Operations
    private MysqlUtil util;

    // ContainerElements
    @FXML AnchorPane topAnchorPane;
    @FXML AnchorPane searchAnchorPane;
    @FXML BorderPane borderPane;
    @FXML Pagination pagination;
    @FXML AnchorPane resultAnchorPane;
    
    //SearchAnchorPaneElements
    @FXML Label SearchPreferences;
    @FXML Label Features;
    @FXML CheckBox CoffeMachine;
    @FXML CheckBox Whiteboard;
    @FXML CheckBox Projector;
    @FXML Label RoomSize;
    @FXML CheckBox Small;
    @FXML CheckBox Medium;
    @FXML CheckBox Large;
    @FXML Label Date;
    @FXML DatePicker Datepicker;
    @FXML Label FromTime;
    @FXML TextField FromTimeInput;
    @FXML Label ToTime;
    @FXML TextField ToTimeInput;
    @FXML Label Location;
    @FXML ChoiceBox LocationPick;
    @FXML Button SearchButton;
    
    



    // this method runs when controller is started
    public void initialize() {
    	util = new MysqlUtil();

//


    }



    // takes a reference to the controller of the parent
    public void init(MainController mainController){
        this.mainController = mainController;
    }
    //  Method that displays show search result


    // Todo: add method to handle search button








}
