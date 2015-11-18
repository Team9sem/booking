package com.team9.bookingsystem.Controllers;

import java.awt.Button;
import java.awt.TextField;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.team9.bookingsystem.MysqlUtil;
import com.team9.bookingsystem.Room;
import com.team9.bookingsystem.Threading.Admin.UserSearchService;

import com.team9.bookingsystem.User;
import com.team9.bookingsystem.Threading.User.FindRoomService;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.TextAlignment;
import javafx.util.Callback;

public class AdminController{
	
	//Logged in user
    private User loggedInUser;
    // Parent Controller
    private MainController mainController;
    // Mysqlutil for Database Operations
    private MysqlUtil util;
    // ArrayList Storing the most recent searchResult;
    private ArrayList<Room> searchResult;
    private Room selectedRoom;
    private ToggleGroup toggleGroup;
    private Object selectedButton;
    private ArrayList<Room> searchResult;
    private ObservableList<Room> tableViewData = FXCollections.observableArrayList();
    private ArrayList<Room> updatedRooms = new ArrayList<>();
    private TableView<Room> table = new TableView<>();



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



    
    public void initialize() {



    	util = new MysqlUtil();
        paginationBox.setAlignment(Pos.CENTER);
        toggleGroup = new ToggleGroup();
        userToggle.setToggleGroup(toggleGroup);
        roomToggle.setToggleGroup(toggleGroup);
        userToggle.setSelected(true);
        searchOptionsController.init(mainController,this,loggedInUser);

    }


    public void init(MainController mainController,User admin){
        this.mainController = mainController;
        this.loggedInUser = admin;
    }

    public void searchForUsers(User user){

        UserSearchService userSearchService = new UserSearchService(user);
        userSearchService.start();
        userSearchService.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {

                ArrayList<User> searchResult = (ArrayList<User>)userSearchService.getValue();
                if(searchResult != null){
                    System.out.println(searchResult);
                }


            }
        });


    }


    private void setupTableView(){

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
                System.out.println(tableViewData.toString());
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
                    System.out.println(tableViewData.toString());
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
                    System.out.println(tableViewData.toString());
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
                    System.out.println(tableViewData.toString());
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


                return new ButtonCell();
            }
        });

        table.getColumns().addAll(roomid,location,size,projector,whiteboard,coffeMachine,buttons);
//        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        table.setColumnResizePolicy(new Callback<TableView.ResizeFeatures, Boolean>() {
            @Override
            public Boolean call(TableView.ResizeFeatures param) {
                param.getColumn().prefWidthProperty().bind(table.widthProperty().multiply(0.1));
                return true;
            }
        });
        table.setEditable(true);



    }


    


    
    

    
    
   
}