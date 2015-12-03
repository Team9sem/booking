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
    @FXML private Label commitCompletionLabel;


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
        commitCompletionLabel.setVisible(false);

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

        commitCompletionLabel.setVisible(false);

        if(searchedForObject == searchedFor.user && userTableView != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("../resources/view/reviewChanges.fxml"));
                AnchorPane anchorPane = loader.load();
                ReviewChangesController reviewChangesController = loader.getController();
                reviewChangesController.setAddedItems(userTableView.getAddedUsers());
                reviewChangesController.setUpdatedItems(userTableView.getUpdatedUsers());
                reviewChangesController.setDeletedItems(userTableView.getDeletedUsers());
                reviewChangesController.setReviewingObjects(ReviewChangesController.reviewing.user);
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
                                            userTableView.getUpdatedUsers().clear();
                                            userTableView.getAddedUsers().clear();
                                            userTableView.getDeletedUsers().clear();


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
                                        commitCompletionLabel.setText("Your Changes was pushed\n successfully!");
                                        commitCompletionLabel.setVisible(true);
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

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("../resources/view/reviewChanges.fxml"));
                AnchorPane anchorPane = loader.load();
                ReviewChangesController reviewChangesController = loader.getController();
                reviewChangesController.setAddedItems(roomTableView.getAddedRooms());
                reviewChangesController.setUpdatedItems(roomTableView.getUpdatedRooms());
                reviewChangesController.setDeletedItems(roomTableView.getDeletedRooms());
                reviewChangesController.setReviewingObjects(ReviewChangesController.reviewing.room);
                reviewChangesController.setRoomTableView(roomTableView);
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
                                            if (roomTableView.getUpdatedRooms() != null && !roomTableView.getUpdatedRooms().isEmpty()) {
                                               roomTableView.getUpdatedRooms().forEach(element -> util.updateRoom(element));
                                            }
                                            if (roomTableView.getAddedRooms() != null && !roomTableView.getAddedRooms().isEmpty()) {
                                                roomTableView.getAddedRooms()
                                                        .forEach(
                                                                element -> util.
                                                                        RegisterRoom(element.getLocation(),
                                                                                element.getRoomSize(),
                                                                                element.getHasProjector(),
                                                                                element.getHasWhiteboard(),
                                                                                element.getHasCoffeeMachine()));
                                            }
                                            if (roomTableView.getDeletedRooms() != null && !roomTableView.getDeletedRooms().isEmpty())
                                            {
                                                roomTableView.getDeletedRooms().forEach(element -> util.deleteRoom(element));
                                            }

                                        } catch (Exception e) {
                                            e.printStackTrace();
                                            return false;
                                        }
                                        roomTableView.getUpdatedRooms().clear();
                                        roomTableView.getAddedRooms().clear();
                                        roomTableView.getDeletedRooms().clear();

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
                                    commitCompletionLabel.setText("Your Changes was pushed\n successfully!");
                                    commitCompletionLabel.setVisible(true);
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
                    System.out.println("Length of Result is:");
                    System.out.println(userSearchResult.size());


                    if(!userTableView.getItems().isEmpty()){
                        userTableView.getItems().clear();
                    }
                    userTableView.getItems().addAll(userSearchResult);




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
                System.out.println("Length of Result is:");
                System.out.println(roomSearchResult.size());
                if (!roomTableView.getItems().isEmpty()) {
                    roomTableView.getItems().clear();
                }
                roomTableView.getItems().addAll(roomSearchResult);




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
//
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
        System.out.println("pressed add Item");
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

                        if (userTableView != null) {
                            userTableView.getAddedUsers().add(added);


                            userTableView.getItems().add(added);





                            paginationBox.getChildren().clear();
                            paginationBox.getChildren().add(userTableView);
                            paginationBox.setHgrow(userTableView,Priority.SOMETIMES);
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
                popupStage.setTitle("Add new Room");
                popupStage.initModality(Modality.WINDOW_MODAL);
                Scene scene = new Scene(anchorPane);
                popupStage.setScene(scene);
                mainController.showPopup(popupStage, addRoomController, new DialogCallback<Room>() {
                    @Override
                    public void onSuccess(Room added) {
                        System.out.println(added.toString());

                        if (roomTableView != null) {
                            roomTableView.getAddedRooms().add(added);


                            roomTableView.getItems().add(added);





                            paginationBox.getChildren().clear();
                            paginationBox.getChildren().add(roomTableView);
                            paginationBox.setHgrow(roomTableView,Priority.SOMETIMES);
                        }

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
        else if(searchedForObject == searchedFor.room){
            roomTableView.getDeletedRooms().add(roomTableView.getSelectionModel().getSelectedItem());
            roomTableView.getItems().remove(roomTableView.getSelectionModel().getSelectedItem());
            roomTableView.getTableviewData().removeAll(roomTableView.getSelectionModel().getSelectedItem());
            System.out.println(userTableView.getDeletedUsers().toString());
        }
    }



    


    
    

    
    
   
}