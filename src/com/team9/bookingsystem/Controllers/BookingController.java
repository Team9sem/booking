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
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.TextAlignment;
import javafx.util.Callback;
import jfxtras.animation.Timer;
import jfxtras.internal.scene.control.skin.LocalTimePickerSkin;
import jfxtras.scene.control.LocalTimePicker;

import javax.jws.soap.SOAPBinding;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
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
    @FXML LocalTimePicker fromTimeInput;
    @FXML Label toTime;
    @FXML LocalTimePicker toTimeInput;
//    @FXML Label location;
    @FXML ChoiceBox locationPick;
    @FXML Button searchButton;

    
    



    // this method runs when controller is started
    public void initialize() {


        setupDatePicker();
    	util = new MysqlUtil();
        paginationBox.setAlignment(Pos.CENTER);
//        ObservableList<String> choices= FXCollections.observableArrayList();
//        choices.addAll("OneChoice");
//        locationPick.setItems(choices);


    }

    private void setupDatePicker(){
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
                            setStyle("-fx-background-color: rgba(238, 51, 60, 0.67);");
                        }
                    }
                };
            }
        };
        datePicker.setDayCellFactory(dayCellFactory);
        datePicker.

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


    /**
     * Hour formatter that adds a zero if hour is in AM: format.
     * i.e 7 -> 07,
     * @param hour hour to analyze
     * @return formatted hour as String
     */
    private String formatHour(int hour){

        if(hour < 10) {
            return String.format("0%d",hour);
        }

        return ""+hour;

    }


    // Todo: add method to handle search button

    /**
     *
     * Called when searchbutton is clicked, Starts a new thread that querys the database for rooms that
     * correspond to search criterias and that are not already booked. It then populates the result Area
     * of the Gui with the results.
     *
     * @param event
     */
    @FXML public void Search(ActionEvent event) {
        System.out.println("searching");


        // Format Hours
        String fromHour = formatHour(fromTimeInput.getLocalTime().getHour());
        String toHour = formatHour(toTimeInput.getLocalTime().getHour());


        System.out.println(fromHour+" : "+toHour);


        System.out.println(datePicker.getValue().toString());
        SearchService searchService = new SearchService(
        		datePicker.getValue().toString(),
                fromTimeInput.getLocalTime().format(DateTimeFormatter.ofPattern(fromHour+":m"))+":00",
                toTimeInput.getLocalTime().format(DateTimeFormatter.ofPattern(toHour+":m"))+":00",
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
        searchService.setOnFailed(new EventHandler<WorkerStateEvent>() {
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




    @FXML public void bookRoom(ActionEvent event){
        // Todo: book a room
        if(selectedRoom != null && loggedInUser != null){

        }
    }
}
