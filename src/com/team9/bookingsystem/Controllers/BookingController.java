package com.team9.bookingsystem.Controllers;

import com.team9.bookingsystem.MysqlUtil;

import com.team9.bookingsystem.Room;
import com.team9.bookingsystem.Threading.LoginService;
import com.team9.bookingsystem.Threading.SearchService;
import com.team9.bookingsystem.User;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.util.Callback;

import javax.jws.soap.SOAPBinding;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Controller for booking.fxml
 *
 */

public class BookingController {


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
    
    //SearchAnchorPaneElements
    @FXML Label searchPreferences;
    @FXML Label features;
    @FXML CheckBox coffeMachine;
    @FXML CheckBox whiteboard;
    @FXML CheckBox projector;
    @FXML Label roomSize;
    @FXML CheckBox small;
    @FXML CheckBox medium;
    @FXML CheckBox large;
    @FXML Label date;
    @FXML DatePicker datePicker;
    @FXML Label fromTime;
    @FXML TextField fromTimeInput;
    @FXML Label toTime;
    @FXML TextField toTimeInput;
//    @FXML Label location;
    @FXML ChoiceBox locationPick;
    @FXML Button searchButton;
    
    



    // this method runs when controller is started
    public void initialize() {
        datePicker.setShowWeekNumbers(true);

        LocalDate localDate = LocalDate.now();
        datePicker.setValue(localDate);
        Callback<DatePicker, DateCell> dayCellFactory = new Callback<DatePicker, DateCell>() {
            @Override
            public DateCell call(final DatePicker datePicker) {
                return new DateCell() {
                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);

                        if (item.isBefore(localDate)) {
                            setDisable(true);
                            setStyle("-fx-background-color: #ee333c;");
                        }
                    }
                };
            }
        };
        datePicker.setDayCellFactory(dayCellFactory);

    	util = new MysqlUtil();
        paginationBox.setAlignment(Pos.CENTER);
//        ObservableList<String> choices= FXCollections.observableArrayList();
//        choices.addAll("OneChoice");
//        locationPick.setItems(choices);


    }



    // takes a reference to the controller of the parent
    public void init(MainController mainController,User user){
        this.mainController = mainController;
        this.loggedInUser = user;

    }

    /* Initialize Pagination method

     */
    public Pagination initPagination(){

        Pagination pagination = new Pagination();

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

                }
            }
        });

        int page = pageIndex * getElementsPerPage();
        for(int i=page;i < page + getElementsPerPage();i++){
            if(i < searchResult.size()){
                HBox element = new HBox();
                VBox content = new VBox();

                ToggleButton button = new ToggleButton("Room in "+searchResult.get(i).getLocation()+
                        "\n Size: " + searchResult.get(i).getRoomSize());
                button.setUserData(searchResult.get(i));
                button.setToggleGroup(group);

                button.setPrefWidth(250);

                content.getChildren().add(button);
                content.setAlignment(Pos.CENTER_LEFT);
                content.setPrefWidth(250);
                element.getChildren().add(content);
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







    // Todo: add method to handle search button
    @FXML public void Search(ActionEvent event) {
        System.out.println("searching");


        System.out.println(datePicker.getValue().toString());
        SearchService searchService = new SearchService(datePicker.getValue().toString(),
                fromTimeInput.getText(),
                toTimeInput.getText(),
                small.isSelected(),
                medium.isSelected(),
                large.isSelected(),
                coffeMachine.isSelected(),
                whiteboard.isSelected(),
                projector.isSelected()
        );
        searchService.start();
        searchService.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {

                searchResult = (ArrayList<Room>) searchService.getValue();

                if (searchResult != null) {
                    System.out.println(searchResult.toString());
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
    }

    @FXML public void bookRoom(ActionEvent event){
        // Todo: book a room
        if(selectedRoom != null){

        }
    }
}
