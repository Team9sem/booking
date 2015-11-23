package com.team9.bookingsystem.Controllers;

import com.sun.javafx.scene.control.skin.SpinnerSkin;
import com.team9.bookingsystem.Booking;
import com.team9.bookingsystem.Components.CustomDatePicker;
import com.team9.bookingsystem.Components.CustomDatePickerSkin;
import com.team9.bookingsystem.MysqlUtil;

import com.team9.bookingsystem.Room;
import com.team9.bookingsystem.Threading.User.FindRoomService;
import com.team9.bookingsystem.User;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.TextAlignment;
import javafx.util.Callback;
import javafx.util.StringConverter;
import jfxtras.scene.control.LocalTimePicker;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

/**
 *
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
    private Booking latestSearch;


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
    @FXML HBox datePickerBox;
    @FXML Slider fromHourSlider;
    @FXML Slider fromMinuteSlider;
    @FXML Slider toHourSlider;
    @FXML Slider toMinuteSlider;
    @FXML Label  fromHourDisplayed;
    @FXML Label  fromMinuteDisplayed;
    @FXML Label  toHourDisplayed;
    @FXML Label  toMinuteDisplayed;
    @FXML Label  fromAmPm;
    @FXML Label  toAmPm;
    @FXML Label  searchErrorLabel;
    @FXML Label  bookingResultLabel;
    @FXML ProgressIndicator bookingProgress;
    @FXML Label  loggedInAs;
    
    



    /**
     * this method runs when controller is started
     */
    public void initialize() {


        setupDatePicker();
//        setupSpinners();
        setupSliders();
    	util = new MysqlUtil();
        paginationBox.setAlignment(Pos.CENTER);
//        ObservableList<String> choices= FXCollections.observableArrayList();
//        choices.addAll("OneChoice");
//        locationPick.setItems(choices);


    }

    /**
     * by Pontus
     * Initializes the Slider Controls
     */
    private void setupSliders(){

        fromHourSlider.setMin(0.0);
        fromHourSlider.setMax(23.0);
        fromHourSlider.setValue(12.00);
        fromMinuteSlider.setMin(0.0);
        fromMinuteSlider.setMax(59.0);
        fromMinuteSlider.setValue(00.00);

        toHourSlider.setMin(0.0);
        toHourSlider.setMax(23.0);
        toHourSlider.setValue(14.00);
        toMinuteSlider.setMin(0.0);
        toMinuteSlider.setMax(59.0);
        toMinuteSlider.setValue(00.00);

        fromHourSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {

                if((int)fromHourSlider.getValue() < 12){
                    fromAmPm.setText(" AM");
                }
                else{
                    fromAmPm.setText(" PM");
                }

                if(newValue.intValue() < 10){
                    fromHourDisplayed.setText(String.format("0%d:",newValue.intValue()));
                }
                else{
                    fromHourDisplayed.setText(String.format("%d:",newValue.intValue()));
                }


            }
        });
        fromMinuteSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {




                if(newValue.intValue() < 10){
                    fromMinuteDisplayed.setText(String.format("0%d",newValue.intValue()));
                }
                else{
                    fromMinuteDisplayed.setText(String.format("%d",newValue.intValue()));
                }
            }
        });

        toHourSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {

                if((int)toHourSlider.getValue() < 12){
                    toAmPm.setText(" AM");
                }
                else{
                    toAmPm.setText(" PM");
                }

                if(newValue.intValue() < 10){
                    toHourDisplayed.setText(String.format("0%d:",newValue.intValue()));
                }
                else{
                    toHourDisplayed.setText(String.format("%d:",newValue.intValue()));
                }


            }
        });
        toMinuteSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {




                if(newValue.intValue() < 10){
                    toMinuteDisplayed.setText(String.format("0%d",newValue.intValue()));
                }
                else{
                    toMinuteDisplayed.setText(String.format("%d",newValue.intValue()));
                }
            }
        });

        fromHourDisplayed.setText(String.format("%d:",(int)fromHourSlider.getValue()));
        fromMinuteDisplayed.setText(String.format("0%d:",(int)fromMinuteSlider.getValue()));
        toHourDisplayed.setText(String.format("%d:",(int)toHourSlider.getValue()));
        toMinuteDisplayed.setText(String.format("0%d:",(int)toMinuteSlider.getValue()));
        fromAmPm.setText("PM");
        toAmPm.setText("PM");
    }


    /**
     * by Pontus
     * Initializes Spinner Controls - Currently unused -
     */
    private void setupSpinners(){
        String[] hours = new String[24];
        String[] minutes = new String[60];
        for(int i = 0; i < hours.length; i++){
            if(i < 10){
                hours[i] = "0" + i;
            }
            else{
                hours[i] = "" + i;
            }

        }
        for(int i = 0; i < minutes.length; i++){
            if(i < 10){
                minutes[i] = "0" + i;
            }
            else{
                minutes[i] = "" + i;
            }
        }

//        fromHourSpinner.setValueFactory(new SpinnerValueFactory.ListSpinnerValueFactory<>(FXCollections.observableArrayList(hours)));
//        fromHourSpinner.getValueFactory().setWrapAround(true);
//        fromMinuteSpinner.setValueFactory(new SpinnerValueFactory.ListSpinnerValueFactory<>(FXCollections.observableArrayList(minutes)));
//        fromMinuteSpinner.getValueFactory().setWrapAround(true);
//        toHourSpinner.setValueFactory(new SpinnerValueFactory.ListSpinnerValueFactory<>(FXCollections.observableArrayList(hours)));
//        toHourSpinner.getValueFactory().setWrapAround(true);
//        toMinuteSpinner.setValueFactory(new SpinnerValueFactory.ListSpinnerValueFactory<>(FXCollections.observableArrayList(minutes)));
//        toMinuteSpinner.getValueFactory().setWrapAround(true);





    }

    /**
     * by Pontus
     */
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


        datePicker.setConverter(new StringConverter<LocalDate>() {

            String pattern = "yyyy-MM-dd";
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(pattern);

            {
                datePicker.setPromptText(pattern);
            }

            @Override
            public String toString(LocalDate date) {
                if(date != null){
                    return dateTimeFormatter.format(date);
                }
                else{
                    return "";
                }
            }

            @Override
            public LocalDate fromString(String string) {
                if(string != null && !string.isEmpty()){
                    return LocalDate.parse(string,dateTimeFormatter);
                }
                else{
                    return null;
                }
            }
        });


        final StringConverter<LocalDate> defaultConverter = datePicker.getConverter();
        datePicker.setConverter(new StringConverter<LocalDate>() {
            @Override
            public String toString(LocalDate value) {
                return defaultConverter.toString(value);
            }

            @Override
            public LocalDate fromString(String value) {
                try{
                    return defaultConverter.fromString(value);
                }catch (DateTimeParseException e){
                    e.printStackTrace();
                    throw e;
                }

            }
        });
//        System.out.println(datePicker.skinProperty().getValue().pop);

    }

    // takes a reference to the controller of the parent
    public void init(MainController mainController,User user){
        this.mainController = mainController;
        this.loggedInUser = user;
        loggedInAs.setText("Logged in as: "+loggedInUser.getUserName());

    }

    /**
     * By Pontus
     * @return
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

    /**
     * By Pontus
     * @param pageIndex
     * @return
     */
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
                region.setPrefHeight(100);
                element.getChildren().add(region);
                element.setAlignment(Pos.CENTER);
                element.setHgrow(region,Priority.ALWAYS);
                vBox.getChildren().add(element);
                vBox.setVgrow(element, Priority.ALWAYS);
            }


        }
        return vBox;
    }

    /**
     * By Pontus
     * @return
     */
    private int getElementsPerPage(){
        return 5;
    }


    /**
     * By Pontus
     * Hour formatter that adds a zero if hour is in AM: format or if Minute is less than 10.
     * i.e 7 -> 07,
     * @param hour hour to analyze
     * @return formatted hour as String
     */
    private String formatHourOrMinute(int hour){

        if(hour < 10) {
            return String.format("0%d",hour);
        }

        return ""+hour;

    }


    // Todo: add method to handle search button

    /**
     * by Pontus
     * Called when searchbutton is clicked, Starts a new thread that querys the database for rooms that
     * correspond to search criterias and that are not already booked. It then populates the result Area
     * of the Gui with the results.
     *
     * @param event
     */
    @FXML public void Search(ActionEvent event) {
        System.out.println("searching");

        try{


        LocalDate currentDate = LocalDate.now();
        LocalDate fromLocalDate = datePicker.getValue();
        if(fromLocalDate.isBefore(currentDate)){
            throw new Exception("The date that you picked is before todays date!");

        }

        LocalTime currentTime = LocalTime.now();
        LocalTime fromLocalTime = LocalTime.of((int)fromHourSlider.getValue(),(int)fromMinuteSlider.getValue());
        LocalTime toLocalTime = LocalTime.of((int)toHourSlider.getValue(),(int)toMinuteSlider.getValue());
        if(fromLocalTime.isBefore(currentTime) && fromLocalDate.isEqual(currentDate)){
            throw new Exception("Your preferred start time has already occurred\n" +
                    "current time is: "+currentTime.getHour()+":"+currentTime.getMinute()+
                    " and you entered "+fromLocalTime.toString());
        }
        if(fromLocalTime.isAfter(toLocalTime)){
            throw new Exception("A booking must start before it ends!");
        }

        // Format Hours
        String fromTime = String.format("%s:%s:00",formatHourOrMinute((int)fromHourSlider.getValue()),
                formatHourOrMinute((int)fromMinuteSlider.getValue()));
        String toTime = String.format("%s:%s:00",formatHourOrMinute((int)toHourSlider.getValue()),
                formatHourOrMinute((int)toMinuteSlider.getValue()));
        System.out.println(fromTime);

        latestSearch = new Booking();
            latestSearch.setbdate(datePicker.getValue().toString());
            latestSearch.setbStart(fromTime);
            latestSearch.setbEnd(toTime);
            latestSearch.setUser(loggedInUser);




//        format(DateTimeFormatter.ofPattern(toHour+":m"))+":00",

        System.out.println(datePicker.getValue().toString());
        FindRoomService findRoomService = new FindRoomService(
        		datePicker.getValue().toString(),
                fromTime,
                toTime,
                small.isSelected(),
                medium.isSelected(),
                large.isSelected(),
                coffeMachine.isSelected(),
                whiteboard.isSelected(),
                projector.isSelected()
        );
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
        }catch(Exception e){
            searchErrorLabel.setText(e.getMessage());

        }
    }


    /**
     * by Pontus
     * @param event
     */
    @FXML public void bookRoom(ActionEvent event){
        // Todo: Commented code can be used once the MysqlUtil method to book a room works....

        System.out.println("booking");

        if(selectedRoom != null && loggedInUser != null && latestSearch!=null){

            latestSearch.setRoom(selectedRoom);

            Service<Booking> bookRoomService = new Service<Booking>() {
                @Override
                protected Task<Booking> createTask() {
                    Task<Booking> task = new Task<Booking>() {
                        @Override
                        protected Booking call() throws Exception
                        {
                            MysqlUtil util = new MysqlUtil();
                            return util.BookRoomNew(latestSearch.getUser(),
                                    latestSearch.getRoom(),
                                    latestSearch.getbdate()
                                    , latestSearch.getbStart(),
                                    latestSearch.getbEnd()
                            );
                        }

                    };
                    return task;
                }
            };
            bookRoomService.start();
            bookingProgress.setVisible(true);
        bookRoomService.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {
                bookingProgress.setVisible(false);
                bookingResultLabel.setText("Room Booked Successfully!");
                bookingResultLabel.setVisible(true);
                System.out.println("Booked Successfully");
                System.out.println(bookRoomService.getValue().toString());
                System.out.println(searchResult.toString());

                // Rebuild Pagination so that i doesn't show the bookedRoom any more
                Room toRemove = null;
                for (Room room : searchResult) {
                    if (room.getRoomID() == bookRoomService.getValue().getroomID()) {
                        toRemove = room;
                    }
                    System.out.println(room.getRoomID());
                }
                if(toRemove != null){
                    searchResult.remove(toRemove);
                }


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
        });
        bookRoomService.setOnFailed(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {
                bookingProgress.setVisible(false);
                bookingResultLabel.setText("Booking Failed");
                bookingResultLabel.setStyle("-fx-text-fill: #83161a");
                bookingResultLabel.setVisible(true);

            }
        });
        }
    }
}
