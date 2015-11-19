package com.team9.bookingsystem.Controllers;


import java.lang.reflect.Array;
import java.util.ArrayList;


import com.team9.bookingsystem.Components.CustomColumnResizePolicy;
import com.team9.bookingsystem.MysqlUtil;
import com.team9.bookingsystem.Room;
import com.team9.bookingsystem.Threading.Admin.RoomSearchService;
import com.team9.bookingsystem.Threading.Admin.UserSearchService;

import com.team9.bookingsystem.User;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.*;
import javafx.util.Callback;
import javafx.util.converter.IntegerStringConverter;
import javafx.util.converter.LongStringConverter;

public class AdminController{
	
	//Logged in user
    private User loggedInUser;
    // Parent Controller
    private MainController mainController;
    // Mysqlutil for Database Operations
    private MysqlUtil util;
    // ArrayList Storing the most recent searchResult;

    private Room selectedRoom;
    private ToggleGroup toggleGroup;
    private Object selectedButton;
    private ArrayList<Room> roomSearchResult;
    private ArrayList<User> userSearchResult;
    private ObservableList<Room> roomTableViewData = FXCollections.observableArrayList();
    private ObservableList<User> userTableViewData = FXCollections.observableArrayList();
    private ArrayList<Room> updatedRooms = new ArrayList<>();
    private ArrayList<User> updatedUsers = new ArrayList<>();
    private TableView<Room> roomTableView = new TableView<>();
    private TableView<User> userTableView = new TableView<>();




 // ContainerElements
    @FXML private AnchorPane topAnchorPane;
    @FXML private AnchorPane searchAnchorPane;
    @FXML private BorderPane borderPane;
    @FXML private HBox paginationBox;
    @FXML private AnchorPane resultAnchorPane;
    @FXML private GridPane searchOptions;
    @FXML private UserSearchController searchOptionsController;
    @FXML private Label loginLabel;
    @FXML private Label searchPreferences;
    @FXML private Label searchFor;
    @FXML private ToggleButton userToggle;
    @FXML private ToggleButton roomToggle;
    @FXML private GridPane userSearchGridPane;
    @FXML private Label loggedInAs;



    
    public void initialize() {



        setupUserTableView();
        setupRoomTableView();
        setupToggleButtons();


        paginationBox.setAlignment(Pos.CENTER);
        searchOptionsController.init(mainController,this,loggedInUser);

    }




    public void init(MainController mainController,User admin){
        this.mainController = mainController;
        this.loggedInUser = admin;
        loggedInAs.setText("Logged in as: "+loggedInUser.getUserName());
    }

    @FXML public void commitChanges(ActionEvent event){
            // Todo: update database
    }


    public void searchForUsers(User user){

        System.out.println("Searching in AdminController");
        UserSearchService userSearchService = new UserSearchService(user);
        userSearchService.start();
        userSearchService.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {



                userSearchResult = (ArrayList<User>)userSearchService.getValue();

                if(userSearchResult == null) System.out.println("is null");

                if (userSearchResult != null) {

                    if(!userTableViewData.isEmpty()){
                        userTableViewData.clear();
                    }
                    userTableViewData.addAll(userSearchResult);
                    Pagination pagination = getUserPagination();


                    System.out.println(userSearchResult.size());
                    if (userSearchResult.size() <= 20) {

                        System.out.println("creating page");
                        pagination.setPageCount(1);
                        pagination.setCurrentPageIndex(0);
                        paginationBox.getChildren().clear();
                        paginationBox.getChildren().add(pagination);
                        paginationBox.setHgrow(pagination,Priority.SOMETIMES);



                    } else {

                        pagination.setPageCount((int) (Math.ceil(userSearchResult.size() / 20.0)));
                        pagination.setCurrentPageIndex(0);
                        paginationBox.getChildren().clear();
                        paginationBox.getChildren().add(pagination);

                    }
                }
                else if(userSearchResult == null){

                    userTableViewData.clear();
                    Pagination pagination = getUserPagination();
                    pagination.setPageCount(1);
                    pagination.setCurrentPageIndex(0);
                    paginationBox.getChildren().clear();
                    paginationBox.getChildren().add(pagination);



                }


            }
        });


    }
    public void searchForRooms(Room room) {}

    private void showSchedule(){

    }
    private void setupToggleButtons(){
        toggleGroup = new ToggleGroup();
        userToggle.setToggleGroup(toggleGroup);
        roomToggle.setToggleGroup(toggleGroup);
        userToggle.setSelected(true);
    }


    private void setupRoomTableView(){

        TableColumn roomid = new TableColumn("ID");

        roomid.setCellValueFactory(new PropertyValueFactory<Room,Integer>("roomID"));
        roomid.setPrefWidth(10.0);


        TableColumn location = new TableColumn("Location");
        location.setCellFactory(TextFieldTableCell.forTableColumn());

        location.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Room,String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Room,String> event) {

                Room updatedRoom = event.getTableView().getItems().get(event.getTablePosition()
                        .getRow());
                updatedRoom.setLocation(event.getNewValue());
                if(!updatedRooms.contains(updatedRoom)){
                    System.out.println("in first if,\n this is updatedRooms");
                    updatedRooms.add(updatedRoom);
                    System.out.println(updatedRooms.toString());
                }
                else{
                    System.out.println("in else,\n this is updatedRooms");
                    System.out.println(updatedRooms.toString());
                }
                System.out.println("this is tableview ArrayList:");
                System.out.println(roomTableViewData.toString());
            }
        });

        location.setCellValueFactory(new PropertyValueFactory<Room,String>("location"));
        location.setPrefWidth(30.0);

        TableColumn size = new TableColumn("Size");
        size.setCellValueFactory(new PropertyValueFactory<Room,String>("roomSize"));
        size.setPrefWidth(10.0);


        TableColumn projector = new TableColumn("Projector");
        projector.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        projector.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Room,Integer>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Room,Integer> event) {

                if(event.getNewValue() == 1 || event.getNewValue() == 0)
                {
                    Room updatedRoom = event.getTableView().getItems().get(event.getTablePosition()
                            .getRow());
                    updatedRoom.setHasProjector(event.getNewValue());
                    if(!updatedRooms.contains(updatedRoom)){
                        System.out.println("in first if,\n this is updatedRooms");
                        updatedRooms.add(updatedRoom);
                        System.out.println(updatedRooms.toString());
                    }
                    else{
                        System.out.println("in else,\n this is updatedRooms");
                        System.out.println(updatedRooms.toString());
                    }
                    System.out.println("this is tableview ArrayList:");
                    System.out.println(roomTableViewData.toString());
                }
                else{
                    event.getTableColumn().setVisible(false);
                    event.getTableColumn().setVisible(true);


                }
            }
        });

        projector.setCellValueFactory(new PropertyValueFactory<Room,Integer>("hasProjector"));
        projector.setPrefWidth(10.0);

        TableColumn whiteboard = new TableColumn("WhiteBoard");
        whiteboard.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        whiteboard.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Room,Integer>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Room,Integer> event) {

                if(event.getNewValue() == 1 || event.getNewValue() == 0){



                    Room updatedRoom = event.getTableView().getItems().get(event.getTablePosition()
                            .getRow());
                    updatedRoom.setHasWhiteboard(event.getNewValue());
                    if(!updatedRooms.contains(updatedRoom)){
                        System.out.println("in first if,\n this is updatedRooms");
                        updatedRooms.add(updatedRoom);
                        System.out.println(updatedRooms.toString());
                    }
                    else{
                        System.out.println("in else,\n this is updatedRooms");
                        System.out.println(updatedRooms.toString());
                    }
                    System.out.println("this is tableview ArrayList:");
                    System.out.println(roomTableViewData.toString());
                }
                else{
                    event.getTableColumn().setVisible(false);
                    event.getTableColumn().setVisible(true);


                }


            }
        });
        whiteboard.setCellValueFactory(new PropertyValueFactory<Room,Integer>("hasWhiteboard"));
        whiteboard.setPrefWidth(10.0);

        TableColumn coffeMachine = new TableColumn("CoffeeMachine");
        coffeMachine.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        coffeMachine.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Room,Integer>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Room,Integer> event) {

                if(event.getNewValue() == 1 || event.getNewValue() == 0) {

                    System.out.println("is 1 or 0");
                    Room updatedRoom = event.getTableView().getItems().get(event.getTablePosition()
                            .getRow());
                    updatedRoom.setHasCoffeeMachine(event.getNewValue());
                    if (!updatedRooms.contains(updatedRoom)) {
                        System.out.println("in first if,\n this is updatedRooms");
                        updatedRooms.add(updatedRoom);
                        System.out.println(updatedRooms.toString());
                    } else {
                        System.out.println("in else,\n this is updatedRooms");
                        System.out.println(updatedRooms.toString());
                    }
                    System.out.println("this is tableview ArrayList:");
                    System.out.println(roomTableViewData.toString());
                }
                else{
                    event.getTableColumn().setVisible(false);
                    event.getTableColumn().setVisible(true);


                }
            }
        });
        coffeMachine.setCellValueFactory(new PropertyValueFactory<Room,Integer>("hasCoffeeMachine"));
        coffeMachine.setPrefWidth(10.0);

        TableColumn buttons = new TableColumn();
        buttons.setSortable(false);
        buttons.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Room,Boolean>,
                ObservableValue<Boolean>>() {
            @Override
            public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<Room,Boolean> param) {
                return new SimpleBooleanProperty(param.getValue() != null);
            }
        });

        buttons.setCellFactory(new Callback<TableColumn<Room,Boolean>, TableCell<Room,Boolean>>() {
            @Override
            public TableCell<Room,Boolean> call(TableColumn<Room,Boolean> param) {


                return new RoomTableButtonCell();
            }
        });

        roomTableView.getColumns().addAll(roomid,location,size,projector,whiteboard,coffeMachine,buttons);
//        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        roomTableView.setColumnResizePolicy(new CustomColumnResizePolicy());
        roomTableView.setEditable(true);


    }



    private void setupUserTableView(){

        TableColumn userId = new TableColumn("ID");

        userId.setCellValueFactory(new PropertyValueFactory<User,Integer>("userID"));
        userId.setResizable(false);
        userId.setPrefWidth(30);

        TableColumn passWord = new TableColumn("Password");
        passWord.setCellValueFactory(new PropertyValueFactory<User,String>("password"));


        TableColumn username = new TableColumn("Username");

        username.setCellFactory(TextFieldTableCell.forTableColumn());
        username.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<User,String>>() {
            private boolean isAvailableUserName;
            @Override
            public void handle(TableColumn.CellEditEvent<User,String> event) {


                Service<Boolean> getIsAvailable = new Service<Boolean>() {
                    @Override
                    protected Task<Boolean> createTask() {
                        Task<Boolean> task = new Task<Boolean>() {
                            @Override
                            protected Boolean call() throws Exception {
                                MysqlUtil util = new MysqlUtil();
                                System.out.println(event.getNewValue());
//                                event.getTableView().getItems().get(event.getTablePosition()
//                                        .getRow()).getUserName();
                                return util.isUsernameAvailable(event.getNewValue());
                            }
                        };
                    return task;
                    }
                };
                getIsAvailable.start();
                getIsAvailable.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
                    @Override
                    public void handle(WorkerStateEvent serviceEvent) {
                        System.out.println(getIsAvailable.getValue());
                        isAvailableUserName = getIsAvailable.getValue();


                        if(isAvailableUserName){

                                User updatedUser = event.getTableView().getItems().get(event.getTablePosition()
                                        .getRow());
                                updatedUser.setUserName(event.getNewValue());

                            if(!updatedUsers.contains(updatedUser)){
                                System.out.println("in first if,\n this is updatedUsers");
                                updatedUsers.add(updatedUser);
                                System.out.println(updatedUsers.toString());
                            }
                            else{
                                for(User user: updatedUsers){
                                    if(user.getUserID() == updatedUser.getUserID()){
                                        user.setUserName(updatedUser.getUserName());
                                    }
                                }
                                System.out.println("in else,\n this is updatedUsers");
                                System.out.println(updatedUsers.toString());
                            }
                            System.out.println("this is tableview ArrayList:");
                            System.out.println(userTableViewData.toString());
                        }
                        else{
                            event.getTableColumn().setVisible(false);
                            event.getTableColumn().setVisible(true);
                        }




                    }
                });



            }
        });

        username.setCellValueFactory(new PropertyValueFactory<User, String>("userName"));



        TableColumn firstName = new TableColumn("Firstname");
        firstName.setCellFactory(TextFieldTableCell.forTableColumn());

        firstName.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<User,String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<User,String> event) {

                User updatedUser = event.getTableView().getItems().get(event.getTablePosition()
                        .getRow());
                updatedUser.setFirstName(event.getNewValue());
                if(!updatedUsers.contains(updatedUser)){
                    System.out.println("in first if,\n this is updatedRooms");
                    updatedUsers.add(updatedUser);
                    System.out.println(updatedRooms.toString());
                }
                else{
                    for(User user: updatedUsers){
                        if(user.getUserID() == updatedUser.getUserID()){
                            user.setFirstName(updatedUser.getFirstName());
                        }
                    }
                    System.out.println("in else,\n this is updatedUsers");
                    System.out.println(updatedUsers.toString());
                }
                System.out.println("this is Usertableview ArrayList:");
                System.out.println(userTableViewData.toString());

            }


        });

        firstName.setCellValueFactory(new PropertyValueFactory<User,String>("firstName"));



        TableColumn lastName = new TableColumn("Surname");
        lastName.setCellFactory(TextFieldTableCell.forTableColumn());

        lastName.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<User,String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<User,String> event) {

                User updatedUser = event.getTableView().getItems().get(event.getTablePosition()
                        .getRow());
                updatedUser.setLastName(event.getNewValue());
                if(!updatedUsers.contains(updatedUser)){
                    System.out.println("in first if,\n this is updatedRooms");
                    updatedUsers.add(updatedUser);
                    System.out.println(updatedRooms.toString());
                }
                else{
                    for(User user: updatedUsers){
                        if(user.getUserID() == updatedUser.getUserID()){
                            user.setLastName(updatedUser.getLastName());
                        }
                    }
                    System.out.println("in else,\n this is updatedUsers");
                    System.out.println(updatedUsers.toString());
                }
                System.out.println("this is Usertableview ArrayList:");
                System.out.println(userTableViewData.toString());

            }


        });

        lastName.setCellValueFactory(new PropertyValueFactory<User,String>("lastName"));



        TableColumn userType = new TableColumn("Type");
        userType.setCellFactory(TextFieldTableCell.forTableColumn());

        userType.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<User,String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<User,String> event) {

                User updatedUser = event.getTableView().getItems().get(event.getTablePosition()
                        .getRow());
                updatedUser.setUserType(event.getNewValue());
                if(!updatedUsers.contains(updatedUser)){
                    System.out.println("in first if,\n this is updatedRooms");
                    updatedUsers.add(updatedUser);
                    System.out.println(updatedRooms.toString());
                }
                else{
                    for(User user: updatedUsers){
                        if(user.getUserID() == updatedUser.getUserID()){
                            user.setUserType(updatedUser.getUserType());
                        }
                    }
                    System.out.println("in else,\n this is updatedUsers");
                    System.out.println(updatedUsers.toString());
                }
                System.out.println("this is Usertableview ArrayList:");
                System.out.println(userTableViewData.toString());

            }


        });

        userType.setCellValueFactory(new PropertyValueFactory<User,String>("userType"));




        TableColumn street = new TableColumn("Street");
        street.setCellFactory(TextFieldTableCell.forTableColumn());

        street.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<User,String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<User,String> event) {

                User updatedUser = event.getTableView().getItems().get(event.getTablePosition()
                        .getRow());
                updatedUser.setStreet(event.getNewValue());
                if(!updatedUsers.contains(updatedUser)){
                    System.out.println("in first if,\n this is updatedRooms");
                    updatedUsers.add(updatedUser);
                    System.out.println(updatedUsers.toString());
                }
                else{
                    for(User user: updatedUsers){
                        if(user.getUserID() == updatedUser.getUserID()){
                            user.setStreet(updatedUser.getStreet());
                        }
                    }
                    System.out.println("in else,\n this is updatedUsers");
                    System.out.println(updatedUsers.toString());
                }
                System.out.println("this is Usertableview ArrayList:");
                System.out.println(userTableViewData.toString());

            }


        });

        street.setCellValueFactory(new PropertyValueFactory<User,String>("street"));
        street.setResizable(false);
        street.setPrefWidth(140);

        TableColumn zip = new TableColumn("Zip");
        zip.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));

        zip.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<User,Integer>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<User, Integer> event) {




                    User updatedUser = event.getTableView().getItems().get(event.getTablePosition()
                            .getRow());
                    updatedUser.setZip(event.getNewValue());
                    if (!updatedUsers.contains(updatedUser)) {
                        System.out.println("in first if,\n this is updatedRooms");
                        updatedUsers.add(updatedUser);
                        System.out.println(updatedUsers.toString());
                    } else {
                        for (User user : updatedUsers) {
                            if (user.getUserID() == updatedUser.getUserID()) {
                                user.setZip(updatedUser.getZip());
                            }
                        }
                        System.out.println("in else,\n this is updatedUsers");
                        System.out.println(updatedUsers.toString());
                        System.out.println("this is tableview ArrayList:");
                        System.out.println(userTableViewData.toString());


                }
            }
        });

        zip.setCellValueFactory(new PropertyValueFactory<User,Integer>("zip"));


        TableColumn pNumberCol = new TableColumn("PNR");
        pNumberCol.setCellFactory(TextFieldTableCell.forTableColumn(new LongStringConverter()));
        pNumberCol.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<User,Long>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<User, Long> event) {

                System.out.println(event.getNewValue());
                User updatedUser = event.getTableView().getItems().get(event.getTablePosition()
                        .getRow());
                updatedUser.setpNumber(event.getNewValue());
                if (!updatedUsers.contains(updatedUser)) {
                    System.out.println("in first if,\n this is updatedRooms");
                    updatedUsers.add(updatedUser);
                    System.out.println(updatedUsers.toString());
                } else {
                    for (User user : updatedUsers) {
                        if (user.getUserID() == updatedUser.getUserID()) {
                            user.setpNumber(updatedUser.getpNumber());
                        }
                    }
                    System.out.println("in else,\n this is updatedUsers");
                    System.out.println(updatedUsers.toString());
                    System.out.println("this is tableview ArrayList:");
                    System.out.println(userTableViewData.toString());
                }
            }
        });

        pNumberCol.setCellValueFactory(new PropertyValueFactory<User,Long>("pNumber"));



//        TableColumn projector = new TableColumn("Projector");
//        projector.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
//        projector.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Room,Integer>>() {
//            @Override
//            public void handle(TableColumn.CellEditEvent<Room,Integer> event) {
//
//                if(event.getNewValue() == 1 || event.getNewValue() == 0)
//                {
//                    Room updatedRoom = event.getTableView().getItems().get(event.getTablePosition()
//                            .getRow());
//                    updatedRoom.setHasProjector(event.getNewValue());
//                    if(!updatedRooms.contains(updatedRoom)){
//                        System.out.println("in first if,\n this is updatedRooms");
//                        updatedRooms.add(updatedRoom);
//                        System.out.println(updatedRooms.toString());
//                    }
//                    else{
//                        System.out.println("in else,\n this is updatedRooms");
//                        System.out.println(updatedRooms.toString());
//                    }
//                    System.out.println("this is tableview ArrayList:");
//                    System.out.println(userTableViewData.toString());
//                }
//                else{
//                    event.getTableColumn().setVisible(false);
//                    event.getTableColumn().setVisible(true);
//
//
//                }
//            }
//        });
//
//        projector.setCellValueFactory(new PropertyValueFactory<Room,Integer>("hasProjector"));
//        projector.setPrefWidth(10.0);
//
//        TableColumn whiteboard = new TableColumn("WhiteBoard");
//        whiteboard.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
//        whiteboard.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Room,Integer>>() {
//            @Override
//            public void handle(TableColumn.CellEditEvent<Room,Integer> event) {
//
//                if(event.getNewValue() == 1 || event.getNewValue() == 0){
//
//
//
//                    Room updatedRoom = event.getTableView().getItems().get(event.getTablePosition()
//                            .getRow());
//                    updatedRoom.setHasWhiteboard(event.getNewValue());
//                    if(!updatedRooms.contains(updatedRoom)){
//                        System.out.println("in first if,\n this is updatedRooms");
//                        updatedRooms.add(updatedRoom);
//                        System.out.println(updatedRooms.toString());
//                    }
//                    else{
//                        System.out.println("in else,\n this is updatedRooms");
//                        System.out.println(updatedRooms.toString());
//                    }
//                    System.out.println("this is tableview ArrayList:");
//                    System.out.println(userTableViewData.toString());
//                }
//                else{
//                    event.getTableColumn().setVisible(false);
//                    event.getTableColumn().setVisible(true);
//
//
//                }
//
//
//            }
//        });
//        whiteboard.setCellValueFactory(new PropertyValueFactory<Room,Integer>("hasWhiteboard"));
//        whiteboard.setPrefWidth(10.0);
//
//        TableColumn coffeMachine = new TableColumn("CoffeeMachine");
//        coffeMachine.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
//        coffeMachine.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Room,Integer>>() {
//            @Override
//            public void handle(TableColumn.CellEditEvent<Room,Integer> event) {
//
//                if(event.getNewValue() == 1 || event.getNewValue() == 0) {
//
//                    System.out.println("is 1 or 0");
//                    Room updatedRoom = event.getTableView().getItems().get(event.getTablePosition()
//                            .getRow());
//                    updatedRoom.setHasCoffeeMachine(event.getNewValue());
//                    if (!updatedRooms.contains(updatedRoom)) {
//                        System.out.println("in first if,\n this is updatedRooms");
//                        updatedRooms.add(updatedRoom);
//                        System.out.println(updatedRooms.toString());
//                    } else {
//                        System.out.println("in else,\n this is updatedRooms");
//                        System.out.println(updatedRooms.toString());
//                    }
//                    System.out.println("this is tableview ArrayList:");
//                    System.out.println(userTableViewData.toString());
//                }
//                else{
//                    event.getTableColumn().setVisible(false);
//                    event.getTableColumn().setVisible(true);
//
//
//                }
//            }
//        });
//        coffeMachine.setCellValueFactory(new PropertyValueFactory<Room,Integer>("hasCoffeeMachine"));
//        coffeMachine.setPrefWidth(10.0);




        TableColumn buttons = new TableColumn();
        buttons.setSortable(false);
        buttons.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<User,Boolean>,
                ObservableValue<Boolean>>() {
            @Override
            public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<User,Boolean> param) {
                return new SimpleBooleanProperty(param.getValue() != null);
            }
        });

        buttons.setCellFactory(new Callback<TableColumn<User,Boolean>, TableCell<User,Boolean>>() {
            @Override
            public TableCell<User,Boolean> call(TableColumn<User,Boolean> param) {


                return new UserTableButtonCell();
            }
        });

        userTableView.getColumns().addAll(userId,username,firstName,lastName,passWord,userType,street,zip,pNumberCol,buttons);
        userTableView.setColumnResizePolicy(new CustomColumnResizePolicy());
//        userTableView.setColumnResizePolicy(new Callback<TableView.ResizeFeatures, Boolean>() {
//            @Override
//            public Boolean call(TableView.ResizeFeatures param) {
//                param.getColumn().prefWidthProperty().bind(roomTableView.widthProperty().divide(9.0));
//                return true;
//            }
//        });
        userTableView.setEditable(true);

    }

    private class UserTableButtonCell extends TableCell<User,Boolean>{
        Button cellButton = new Button("Show Schedule");
        private Room rowRoom;
        private User rowUser;

        UserTableButtonCell(){
//

            cellButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    System.out.println("TableButton Clicked");
                    userTableView.getSelectionModel().select(getTableRow().getIndex());

                    System.out.println(userTableView.getItems().get(getTableRow().getIndex()).toString());
//                    showSchedule(roomTableView.getItems().get(getTableRow().getIndex()),loggedInUser);


                }
            });


        }



        @Override
        protected void updateItem(Boolean t, boolean empty){
            super.updateItem(t,empty);
            if(empty||t == null){
                setText(null);
                setGraphic(null);
            }
            else{

                cellButton.setStyle("-fx-font-size: 9px");
                setGraphic(cellButton);
            }
        }
    }
    private class RoomTableButtonCell extends TableCell<Room,Boolean>{
        Button cellButton = new Button("Show Schedule");
        private Room rowRoom;
        private User rowUser;

        RoomTableButtonCell(){
//

            cellButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    System.out.println("TableButton Clicked");
                    roomTableView.getSelectionModel().select(getTableRow().getIndex());

                    System.out.println(roomTableView.getItems().get(getTableRow().getIndex()).toString());
//                    showSchedule(roomTableView.getItems().get(getTableRow().getIndex()),loggedInUser);


                }
            });


        }



        @Override
        protected void updateItem(Boolean t, boolean empty){
            super.updateItem(t,empty);
            if(!empty){
                System.out.println(roomTableView.getWidth());
                cellButton.setStyle("-fx-font-size: 9px");
                setGraphic(cellButton);
            }
        }
    }


    private Pagination getRoomPagination(){
        Pagination pagination = new Pagination();
        pagination.getStyleClass().add("pagination");


        pagination.setPageFactory(this::createRoomPage);

        return pagination;


    }
    private Pagination getUserPagination(){

        Pagination pagination = new Pagination();
        pagination.getStyleClass().add("pagination");


        pagination.setPageFactory(this::createUserPage);

        return pagination;
    }
    private VBox createRoomPage(int pageIndex){
        VBox vBox = new VBox(5);

        vBox.setAlignment(Pos.CENTER);


        int fromIndex = pageIndex * getElementsPerPage();
        int toIndex = Math.min(pageIndex + getElementsPerPage(),roomTableViewData.size());

        roomTableView.setItems(FXCollections.observableArrayList(roomTableViewData.subList(fromIndex, toIndex)));

        vBox.getChildren().add(roomTableView);
        return vBox;
    }
    private VBox createUserPage(int pageIndex){

        VBox vBox = new VBox(5);

        vBox.setAlignment(Pos.CENTER);


        int fromIndex = pageIndex * getElementsPerPage();
        int toIndex = Math.min(pageIndex + getElementsPerPage(),userTableViewData.size());

        userTableView.setItems(FXCollections.observableArrayList(userTableViewData.subList(fromIndex, toIndex)));

        vBox.getChildren().add(userTableView);
        return vBox;
    }
    private int getElementsPerPage(){
        return 20;
    }


    


    
    

    
    
   
}