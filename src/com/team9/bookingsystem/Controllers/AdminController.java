package com.team9.bookingsystem.Controllers;


import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;


import com.team9.bookingsystem.*;
import com.team9.bookingsystem.Components.CustomColumnResizePolicy;
import com.team9.bookingsystem.Components.RoomTableView;
import com.team9.bookingsystem.Components.UserTableView;
import com.team9.bookingsystem.Threading.Admin.RoomSearchService;
import com.team9.bookingsystem.Threading.Admin.UserSearchService;

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
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.converter.IntegerStringConverter;
import javafx.util.converter.LongStringConverter;

/**
 * Controller class for administratorUI.fxml
 * Created by Pontus and Nima
 */
public class AdminController {
	
	//Logged in user
    private User loggedInUser;
    // Parent Controller
    private MainController mainController;
	private AdminController adminController;
    // Mysqlutil for Database Operations
    private MysqlUtil util;
    // ArrayList Storing the most recent searchResult;
	private searchedFor searchedForObject;


    private Room selectedRoom;
    private ToggleGroup toggleGroup;
    private Object selectedButton;
    private ArrayList<Room> roomSearchResult;
    private ArrayList<User> userSearchResult;
    private ObservableList<Room> roomTableViewData = FXCollections.observableArrayList();
    private ObservableList<User> userTableViewData = FXCollections.observableArrayList();
//    private ArrayList<Room> updatedRooms = new ArrayList<>();
//    private ArrayList<User> updatedUsers = new ArrayList<>();
    private ArrayList<Room> addedRooms = new ArrayList<>();
    private ArrayList<User> addedUsers = new ArrayList<>();
    private ArrayList<Room> deletedRooms = new ArrayList<>();
    private ArrayList<User> deletedUsers = new ArrayList<>();
    private RoomTableView roomTableView;

    private UserTableView userTableView;




 // ContainerElements
    @FXML private AnchorPane topAnchorPane;
    @FXML private AnchorPane searchAnchorPane;
    @FXML private BorderPane borderPane;
    @FXML private HBox paginationBox;
    @FXML private AnchorPane resultAnchorPane;
    @FXML private GridPane  searchOptions;
//	@FXML private GridPane  roomSearchOptions;
//    @FXML private UserSearchController SearchOptionsController;
//	@FXML private RoomSearchController roomSearchOptionsController;
    @FXML private Label loginLabel;
    @FXML private Label searchPreferences;
    @FXML private Label searchFor;
    @FXML private ToggleButton userToggle;
    @FXML private ToggleButton roomToggle;
    @FXML private GridPane userSearchGridPane;
    @FXML private Label loggedInAs;


    /**
     * Works as Constructor for Controller Class
     * Created by Nima and Pontus
     *
     */
    public void initialize() {


        adminController = this;
		setupUserTableView();
        setupRoomTableView();
        setupToggleButtons();


        paginationBox.setAlignment(Pos.CENTER);
//        SearchOptionsController.init(mainController,this,loggedInUser);

    }


    /**
     * set this controller up with references to parent controller and loggedInUser.
     * @param mainController
     * @param admin
     */
    public void init(MainController mainController,User admin){
        this.mainController = mainController;
        this.loggedInUser = admin;
        loggedInAs.setText("Logged in as: "+loggedInUser.getUserName());

    }

	public enum searchedFor{user,room,none}

    /**
     * By Pontus Pohl
     * Called when user commits changes
     * @param event
     */
    @FXML public void commitChanges(ActionEvent event){
        if(searchedForObject == searchedFor.user && userTableView != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("../resources/view/reviewChanges.fxml"));
                AnchorPane anchorPane = loader.load();
                ReviewChangesController reviewChangesController = loader.getController();
                reviewChangesController.setAddedItems(userTableView.getAddedUsers());
                reviewChangesController.setUpdatedItems(userTableView.getUpdatedUsers());
                reviewChangesController.setDeletedItems(userTableView.getDeletedUsers());
                reviewChangesController.setUserTableView(userTableView);
                reviewChangesController.populate();
                Stage popupStage = new Stage();
                popupStage.setTitle("Review Changes");
                popupStage.initModality(Modality.WINDOW_MODAL);
                Scene scene = new Scene(anchorPane);
                popupStage.setScene(scene);
                mainController.showPopup(popupStage, reviewChangesController, new DialogCallback() {
                    @Override
                    public void onSuccess(Object param) {

                        // push changes

                            Service<Boolean> pushChanges = new Service<Boolean>() {
                                @Override
                                protected Task<Boolean> createTask() {
                                    Task<Boolean> task = new Task<Boolean>() {
                                        @Override
                                        protected Boolean call() throws Exception {
                                            MysqlUtil util = new MysqlUtil();
                                            try {
                                                if (userTableView.getUpdatedUsers() != null && !userTableView.getUpdatedUsers().isEmpty()) {
                                                    util.updateUser(userTableView.getUpdatedUsers());
                                                }
                                                if (userTableView.getAddedUsers() != null && !userTableView.getAddedUsers().isEmpty()) {
                                                    userTableView.getAddedUsers()
                                                            .forEach(
                                                                    element -> util.
                                                                            RegisterUser(element.getUserName(),
                                                                                    element.getPassword(),
                                                                                    element.getFirstName(),
                                                                                    element.getLastName(),
                                                                                    element.getpNumber(),
                                                                                    element.getUserType(),
                                                                                    element.getStreet(),
                                                                                    element.getZip()));
                                                }
                                                if (userTableView.getDeletedUsers() != null && !userTableView.getDeletedUsers().isEmpty())
                                                {
                                                    userTableView.getDeletedUsers().forEach(element -> util.deleteUser(element));
                                                }

                                            } catch (SQLException e) {
                                                e.printStackTrace();
                                                return false;
                                            }
                                            return true;
                                        }
                                    };
                                    return task;
                                }
                            };
                            pushChanges.start();
                            pushChanges.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
                                @Override
                                public void handle(WorkerStateEvent event) {
                                    if (pushChanges.getValue()) {
                                        System.out.println("updated Sucessfully");
                                    }
                                }
                            });
                            pushChanges.setOnFailed(new EventHandler<WorkerStateEvent>() {
                                @Override
                                public void handle(WorkerStateEvent event) {
                                    System.out.println("Update Failed");
                                }
                            });


                    }

                    @Override
                    public void onFailure() {

                    }
                });
            } catch (IOException e) {
                // Trown if fxml is not read properly
            }
            // TODO: Show Review popup




        }
        else if(searchedForObject == searchedFor.room && roomTableView != null){

                Service<Boolean> updateRooms = new Service<Boolean>() {
                    @Override
                    protected Task<Boolean> createTask() {
                        Task<Boolean> task = new Task<Boolean>() {
                            @Override
                            protected Boolean call() throws Exception {
                                MysqlUtil util = new MysqlUtil();
                                try{
                                    // Todo: wait for Mayra

                                }catch(Exception e){
                                    e.printStackTrace();
                                }
                                return true;
                            }
                        };
                        return task;
                    }
                };
                updateRooms.start();
                updateRooms.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
                    @Override
                    public void handle(WorkerStateEvent event) {
                        if(updateRooms.getValue()){
                            System.out.println("updated Sucessfully");
                        }
                    }
                });
                updateRooms.setOnFailed(new EventHandler<WorkerStateEvent>() {
                    @Override
                    public void handle(WorkerStateEvent event) {
                        System.out.println("Update Failed");
                    }
                });
            }
    }

    /**
     * By Pontus Pohl
     * Called when user Searches for User
     * @param user
     */
    public void searchForUsers(User user){


		searchedForObject = searchedFor.user;

		System.out.println("Searching in AdminController");
        UserSearchService userSearchService = new UserSearchService(user);
        userSearchService.start();
        userSearchService.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {



                userSearchResult = (ArrayList<User>)userSearchService.getValue();

                if(userSearchResult == null) System.out.println("is null");

                if (userSearchResult != null) {



                    if(!userTableView.getItems().isEmpty()){
                        userTableView.getItems().clear();
                    }
                    userTableView.getItems().addAll(userSearchResult);
                    userTableView.getTableviewData().addAll(userSearchResult);



                    System.out.println(userSearchResult.size());
                    paginationBox.getChildren().clear();
                    paginationBox.getChildren().add(userTableView);
                    paginationBox.setHgrow(userTableView,Priority.SOMETIMES);
                }
                else if(userSearchResult == null){


                    userTableView.getItems().clear();

                    paginationBox.getChildren().clear();
                    paginationBox.getChildren().add(userTableView);



                }


            }
        });


    }


    public void searchForRooms(Room room,boolean small,boolean medium, boolean large){

    searchedForObject = searchedFor.room;

    System.out.println("Searching in AdminController");



    RoomSearchService roomSearchService = new RoomSearchService(room,small,medium,large);
    roomSearchService.start();
    roomSearchService.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
        @Override
        public void handle(WorkerStateEvent event) {


            roomSearchResult = (ArrayList<Room>) roomSearchService.getValue();

            if (roomSearchResult == null) System.out.println("is null");

            if (roomSearchResult != null) {

                if (!roomTableView.getItems().isEmpty()) {
                    roomTableView.getItems().clear();
                }
                roomTableView.getItems().addAll(roomSearchResult);
                roomTableView.getTableviewData().addAll(roomSearchResult);



                System.out.println(roomSearchResult.size());
                paginationBox.getChildren().clear();
                paginationBox.getChildren().add(roomTableView);
                paginationBox.setHgrow(roomTableView, Priority.SOMETIMES);
            } else if (roomSearchResult == null) {

                roomTableView.getItems().clear();
                paginationBox.getChildren().clear();
                paginationBox.getChildren().add(roomTableView);


            }


        }
    });
    roomSearchService.setOnFailed(new EventHandler<WorkerStateEvent>() {
        @Override
        public void handle(WorkerStateEvent event) {
            System.out.println("failed");
        }
    });


	}

    private void showSchedule(){

    }

    /**
     * By Nima
     * ToggleButton setup
     *
     */
    private void setupToggleButtons(){
        toggleGroup = new ToggleGroup();
        userToggle.setToggleGroup(toggleGroup);
        roomToggle.setToggleGroup(toggleGroup);




		roomToggle.setOnAction(new EventHandler <ActionEvent>() {


			@Override
			public void handle (ActionEvent event) {
				try{
					FXMLLoader loader = new FXMLLoader(getClass().getResource("../resources/view/roomSearch.fxml"));
					GridPane gridPane = loader.load();
					RoomSearchController roomSearchController = loader.getController();
					roomSearchController.init(mainController,adminController,loggedInUser);
					System.out.println(this.toString());
					searchOptions.getChildren().clear();
					searchOptions.getChildren().add(gridPane);
				}catch(IOException e){
					e.printStackTrace();
				}





			}

		});

		userToggle.setOnAction(new EventHandler<ActionEvent>() {


            @Override
            public void handle(ActionEvent event) {

                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("../resources/view/userSearch.fxml"));
                    GridPane gridPane = loader.load();
                    UserSearchController userSearchController = loader.getController();
                    userSearchController.init(mainController, adminController, loggedInUser);
                    System.out.println(this.toString());
                    searchOptions.getChildren().clear();
                    searchOptions.getChildren().add(gridPane);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

        });

		userToggle.fire();
	}


    /**
     * By Pontus
     * Creates the tableView of Rooms
     */
    private void setupRoomTableView(){

        roomTableView = new RoomTableView();
        roomTableView.getTableButtons().forEach(element -> element.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                roomTableView.getSelectionModel().getSelectedItem();
            }
        }));
//        TableColumn roomid = new TableColumn("ID");
//
//        roomid.setCellValueFactory(new PropertyValueFactory<Room,Integer>("roomID"));
//        roomid.setPrefWidth(10.0);
//
//
//        TableColumn location = new TableColumn("Location");
//        location.setCellFactory(TextFieldTableCell.forTableColumn());
//
//        location.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Room,String>>() {
//            @Override
//            public void handle(TableColumn.CellEditEvent<Room,String> event) {
//
//                Room updatedRoom = event.getTableView().getItems().get(event.getTablePosition()
//                        .getRow());
//                updatedRoom.setLocation(event.getNewValue());
//                if(!updatedRooms.contains(updatedRoom)){
//                    System.out.println("in first if,\n this is updatedRooms");
//                    updatedRooms.add(updatedRoom);
//                    System.out.println(updatedRooms.toString());
//                }
//                else{
//                    System.out.println("in else,\n this is updatedRooms");
//                    System.out.println(updatedRooms.toString());
//                }
//                System.out.println("this is tableview ArrayList:");
//                System.out.println(roomTableViewData.toString());
//            }
//        });
//
//        location.setCellValueFactory(new PropertyValueFactory<Room,String>("location"));
//        location.setPrefWidth(30.0);
//
//        TableColumn size = new TableColumn("Size");
//        size.setCellValueFactory(new PropertyValueFactory<Room,String>("roomSize"));
//        size.setPrefWidth(10.0);
//
//
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
//                    System.out.println(roomTableViewData.toString());
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
//                    System.out.println(roomTableViewData.toString());
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
//                    System.out.println(roomTableViewData.toString());
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
//
//        TableColumn buttons = new TableColumn();
//        buttons.setSortable(false);
//        buttons.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Room,Boolean>,
//                ObservableValue<Boolean>>() {
//            @Override
//            public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<Room,Boolean> param) {
//                return new SimpleBooleanProperty(param.getValue() != null);
//            }
//        });
//
//        buttons.setCellFactory(new Callback<TableColumn<Room,Boolean>, TableCell<Room,Boolean>>() {
//            @Override
//            public TableCell<Room,Boolean> call(TableColumn<Room,Boolean> param) {
//
//
//                return new RoomTableButtonCell();
//            }
//        });
//
//        roomTableView.getColumns().addAll(roomid,location,size,projector,whiteboard,coffeMachine,buttons);
////        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
//        roomTableView.setColumnResizePolicy(new CustomColumnResizePolicy());
//        roomTableView.setEditable(true);
    }


    /**
     * By Pontus
     */
    private void setupUserTableView(){

        userTableView = new UserTableView();
        userTableView.getTableButtons().forEach(element -> element.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("TableButton Clicked");

                userTableView.getSelectionModel().getSelectedItem();
                System.out.println(userTableView.getSelectionModel().getSelectedItem());

            }
        }));


    }



	/**
	 * by Pontus
	 * @param event
	 */
	@FXML public void SignOut(ActionEvent event){

        mainController.showStartScreen();
	}
	@FXML public void displayAddItemPopup(ActionEvent event){

        // Todo: fix so that added items id field is blank
        System.out.println("pressed add user");
        if(searchedForObject == searchedFor.user){
			try{
				FXMLLoader loader = new FXMLLoader(getClass().getResource("../resources/view/adduser.fxml"));
				AnchorPane anchorPane = loader.load();
				AddUserController addUserController = loader.getController();
                Stage popupStage = new Stage();
                popupStage.setTitle("Add new User");
                popupStage.initModality(Modality.WINDOW_MODAL);
                Scene scene = new Scene(anchorPane);
                popupStage.setScene(scene);
				mainController.showPopup(popupStage, addUserController, new DialogCallback<User>() {
                    @Override
                    public void onSuccess(User added) {
                        System.out.println(added.toString());
                        userTableView.getAddedUsers().add(added);


                        if (userSearchResult != null) {

                            userSearchResult.add(added);


                            if(!userTableView.getItems().isEmpty()){
                                userTableView.getItems().clear();
                            }
                            userTableView.getItems().addAll(userSearchResult);


                            System.out.println(userSearchResult.size());
                            System.out.println(userSearchResult.size());
                            paginationBox.getChildren().clear();
                            paginationBox.getChildren().add(userTableView);
                            paginationBox.setHgrow(userTableView,Priority.SOMETIMES);
                        }
                        else if(userSearchResult == null){


                            userTableView.getItems().clear();

                            paginationBox.getChildren().clear();
                            paginationBox.getChildren().add(userTableView);
                        }
                    }



                    @Override
                    public void onFailure() {
                        System.out.println("user canceled Add operation");
                    }
                });



			}catch(IOException e){
				e.printStackTrace();
			}



		}
        else if(searchedForObject == searchedFor.room){
            try{
                FXMLLoader loader = new FXMLLoader(getClass().getResource("../resources/view/addroom.fxml"));
                AnchorPane anchorPane = loader.load();
                AddRoomController addRoomController = loader.getController();
                Stage popupStage = new Stage();
                popupStage.setTitle("Add new User");
                popupStage.initModality(Modality.WINDOW_MODAL);
                Scene scene = new Scene(anchorPane);
                popupStage.setScene(scene);
                mainController.showPopup(popupStage, addRoomController, new DialogCallback<User>() {
                    @Override
                    public void onSuccess(User param) {
                        System.out.println(param.toString());
                    }

                    @Override
                    public void onFailure() {

                    }
                });



            }catch(IOException e){
                e.printStackTrace();
            }




        }
        else if(searchedForObject == searchedFor.none){
            System.out.println("no search result");
        }

	}

    @FXML public void deleteItem(ActionEvent event){
        if(searchedForObject == searchedFor.user){
            userTableView.getDeletedUsers().add(userTableView.getSelectionModel().getSelectedItem());
            userTableView.getItems().remove(userTableView.getSelectionModel().getSelectedItem());
            userTableView.getTableviewData().removeAll(userTableView.getSelectionModel().getSelectedItem());
            System.out.println(userTableView.getDeletedUsers().toString());


        }
    }



    


    
    

    
    
   
}