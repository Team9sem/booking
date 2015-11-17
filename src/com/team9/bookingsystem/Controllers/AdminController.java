package com.team9.bookingsystem.Controllers;

import java.awt.Button;
import java.awt.TextField;
import java.util.ArrayList;

import com.team9.bookingsystem.MysqlUtil;
import com.team9.bookingsystem.Room;
import com.team9.bookingsystem.User;
import com.team9.bookingsystem.Threading.User.FindRoomService;

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
import javafx.scene.control.Label;
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

 // ContainerElements
    @FXML AnchorPane topAnchorPane;
    @FXML AnchorPane searchAnchorPane;
    @FXML BorderPane borderPane;
    @FXML HBox paginationBox;
    @FXML AnchorPane resultAnchorPane;
    
    
    @FXML Label searchPreferences;
    @FXML Label searchForUser;
    @FXML Label adminRoomLabel;
    @FXML TextField userTextField; 
    @FXML TextField roomTextField;
//    @FXML Label features;
    @FXML RadioButton radioID;
    @FXML RadioButton radioUserName;
    @FXML RadioButton radioName;
    @FXML RadioButton radioType;
    @FXML RadioButton radioPnumber;
    @FXML RadioButton radioRoomID;
    @FXML RadioButton radioSomething;
    @FXML Button adminSearchButton;
    
  
//    @FXML Label location;
//    @FXML ChoiceBox locationPick;
//    @FXML Button searchButton;
    
    public void initialize() {


//        setupDatePicker();
    	util = new MysqlUtil();
        paginationBox.setAlignment(Pos.CENTER);
//        ObservableList<String> choices= FXCollections.observableArrayList();
//        choices.addAll("OneChoice");
//        locationPick.setItems(choices);


    }
   
    public void init(MainController mainController,User admin){
        this.mainController = mainController;
        this.loggedInUser = admin;

    }
    
    public Pagination initPagination(){

        Pagination pagination = new Pagination();
        pagination.getStyleClass().add("pagination");


        pagination.setPageFactory(new Callback<Integer, Node>() {
            @Override
            public Node call(Integer PageIndex) {

                System.out.println("Callback runs");
                VBox vBox = createPage(PageIndex);



                return vBox;
            }

        });
        return pagination;
    }
    
    public VBox createPage(int pageIndex){

        VBox vBox = new VBox(5);

        vBox.setAlignment(Pos.CENTER);
        final ToggleGroup group = new ToggleGroup();

        group.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
                if(newValue!=null){

                    selectedRoom = (Room)group.getSelectedToggle().getUserData();
                    System.out.println(selectedRoom.toString());
                }
            }
        });

        int page = pageIndex * getElementsPerPage();
        for(int i=page;i < page + getElementsPerPage();i++){
            if(i < searchResult.size()){

                HBox element = new HBox();

                element.setStyle("-fx-background-color: rgba(48, 57, 88, 0.25);" +
                        " -fx-background-radius: 15px; -fx-border-color: black; -fx-border-radius: 15px;");



                GridPane gridPane = new GridPane();
                gridPane.setHgap(10);



                HBox locationElement = new HBox();
                HBox sizeElement = new HBox();
                HBox buttonElement = new HBox();


                locationElement.setAlignment(Pos.CENTER_LEFT);
                sizeElement.setAlignment(Pos.CENTER_LEFT);
                buttonElement.setAlignment(Pos.CENTER);

                locationElement.setPrefWidth(200);
                sizeElement.setPrefWidth(150);
                buttonElement.setPrefWidth(150);
                locationElement.setPrefHeight(100);
                sizeElement.setPrefHeight(100);
                buttonElement.setPrefHeight(100);



                gridPane.setMargin(locationElement,new Insets(0.0,0.0,0.0,15.0));


                Label location = new Label("Room in "+searchResult.get(i).getLocation());

                Label size = new Label("Size of room is: "+searchResult.get(i).getRoomSize());

                location.getStyleClass().add("result-element-label");
                size.getStyleClass().add("result-element-label");

                locationElement.getChildren().add(location);
                sizeElement.getChildren().add(size);
                //

                ToggleButton button = new ToggleButton("Select Room");
                button.setUserData(searchResult.get(i));
                button.setTextAlignment(TextAlignment.LEFT);
                button.setAlignment(Pos.CENTER);
                button.setToggleGroup(group);
                button.getStyleClass().add("element-toggle-button");
                buttonElement.getChildren().add(button);


                gridPane.add(locationElement,0,0);
                gridPane.add(sizeElement,1,0);
                gridPane.add(buttonElement,2,0);


                element.getChildren().add(gridPane);
//                content.getChildren().add(button);
//                content.setAlignment(Pos.CENTER);
//                content.setPrefWidth(250);
//                content.getStyleClass().add("result-box");

                element.setAlignment(Pos.CENTER);
                vBox.getChildren().add(element);
                vBox.setVgrow(element, Priority.ALWAYS);

            }
            else{
                HBox element = new HBox();
                Region region = new Region();
                element.getChildren().add(region);
                element.setAlignment(Pos.CENTER);
                element.setHgrow(region,Priority.ALWAYS);
                vBox.getChildren().add(element);
                vBox.setVgrow(element, Priority.ALWAYS);
            }


        }
        return vBox;
    }
    
    private int getElementsPerPage(){
        return 5;
    }
   
    @FXML public void Search(ActionEvent event) {
        System.out.println("searching");


//        Format Hours
//        String fromHour = formatHour(fromTimeInput.getLocalTime().getHour());
//        String toHour = formatHour(toTimeInput.getLocalTime().getHour());
//
//
//        System.out.println(fromHour+" : "+toHour);


//        System.out.println(toString());
          FindRoomService findRoomService = new FindRoomService(new Task() {
              @Override
              protected Object call() throws Exception {
                  return null;
              }
          });


                  findRoomService.start();
        findRoomService.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {

                searchResult = (ArrayList<Room>) findRoomService.getValue();

                if (searchResult != null) {

                    Pagination pagination = initPagination();


                    System.out.println(searchResult.size());
                    if (searchResult.size() <= 5) {
                        System.out.println("if happened");

                        pagination.setPageCount(1);
                        pagination.setCurrentPageIndex(0);
                        paginationBox.getChildren().clear();
                        paginationBox.getChildren().add(pagination);


                    } else {
                        pagination.setPageCount((int) (Math.ceil(searchResult.size() / 5.0)));
                        pagination.setCurrentPageIndex(0);
                        paginationBox.getChildren().clear();
                        paginationBox.getChildren().add(pagination);
                    }
    			}
                else {
                    System.out.println("no result");
                }


            }
        });
        findRoomService.setOnFailed(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {
                searchResult = new ArrayList<Room>();
                Pagination pagination = initPagination();
                pagination.setPageCount(1);
                pagination.setCurrentPageIndex(0);
                paginationBox.getChildren().clear();
                paginationBox.getChildren().add(pagination);
            }
        });
    }

}
