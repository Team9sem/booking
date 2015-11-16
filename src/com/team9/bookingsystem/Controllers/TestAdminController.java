package com.team9.bookingsystem.Controllers;

import com.team9.bookingsystem.MysqlUtil;

import com.team9.bookingsystem.Room;
import com.team9.bookingsystem.Threading.LoginService;
import com.team9.bookingsystem.Threading.SearchService;
import com.team9.bookingsystem.User;
import javafx.beans.property.SimpleBooleanProperty;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.*;
import javafx.scene.text.TextAlignment;
import javafx.util.Callback;
import javafx.util.converter.IntegerStringConverter;
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

public class TestAdminController {


    //Logged in user
    User loggedInUser;
    // Parent Controller
    private MainController mainController;
    // Mysqlutil for Database Operations
    private MysqlUtil util;
    // ArrayList Storing the most recent searchResult;
    private ArrayList<Room> searchResult;
    private ObservableList<Room> tableViewData = FXCollections.observableArrayList();
    private ArrayList<Room> updatedRooms = new ArrayList<>();
    private TableView<Room> table = new TableView<>();
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



        setupTableView();
        setupDatePicker();
        util = new MysqlUtil();
        paginationBox.setAlignment(Pos.CENTER);
//        ObservableList<String> choices= FXCollections.observableArrayList();
//        choices.addAll("OneChoice");
//        locationPick.setItems(choices);


    }

//    private TableCell<Room,String>{
//
//        TextFieldTableCell cell = new TextFieldTableCell(){
//
//            @Override
//
//
//
//        };
//
//
//    }

    private void setupTableView(){

        TableColumn roomid = new TableColumn("ID");
        roomid.setCellValueFactory(new PropertyValueFactory<Room,Integer>("roomID"));

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


        TableColumn size = new TableColumn("Size");
        size.setCellValueFactory(new PropertyValueFactory<Room,String>("roomSize"));

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
        table.setEditable(true);


    }

    private class ButtonCell extends TableCell<Room,Boolean>{
        final Button cellButton = new Button("Show Schedule");
        private Room rowRoom;
            ButtonCell(){
//                System.out.println( this.getTableView().getItems().get(this.getTableRow().getIndex()).toString());

                cellButton.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        System.out.println("TableButton Clicked");
                        table.getSelectionModel().select(getTableRow().getIndex());

                        System.out.println(table.getItems().get(getTableRow().getIndex()).toString());
                        mainController.showSchedule(table.getItems().get(getTableRow().getIndex()),loggedInUser);


                    }
                });


            }
            @Override
            protected void updateItem(Boolean t, boolean empty){
                super.updateItem(t,empty);
                if(!empty){
                    setGraphic(cellButton);
                }
            }



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


        pagination.setPageFactory(this::createPage);
        return pagination;
    }

    public VBox createPage(int pageIndex){

        VBox vBox = new VBox(5);

        vBox.setAlignment(Pos.CENTER);


        int fromIndex = pageIndex * getElementsPerPage();
        int toIndex = Math.min(pageIndex + getElementsPerPage(),tableViewData.size());

        table.setItems(FXCollections.observableArrayList(tableViewData.subList(fromIndex,toIndex)));

        vBox.getChildren().add(table);
        return vBox;
    }
    private int getElementsPerPage(){
        return 20;
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


//        System.out.println(fromHour+" : "+toHour);


//        System.out.println(datePicker.getValue().toString());
        SearchService searchService = new SearchService(datePicker.getValue().toString(),
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

                if(searchResult == null) System.out.println("is null");

                if (searchResult != null) {

                    if(!tableViewData.isEmpty()){
                        tableViewData.clear();
                    }
                    tableViewData.addAll(searchResult);
                    Pagination pagination = initPagination();


                    System.out.println(searchResult.size());
                    if (searchResult.size() <= 20) {


                        pagination.setPageCount(1);
                        pagination.setCurrentPageIndex(0);
                        paginationBox.getChildren().clear();
                        paginationBox.getChildren().add(pagination);


                    } else {

                        pagination.setPageCount((int) (Math.ceil(searchResult.size() / 20.0)));
                        pagination.setCurrentPageIndex(0);
                        paginationBox.getChildren().clear();
                        paginationBox.getChildren().add(pagination);
                    }
                }
                else if(searchResult == null){

                    tableViewData.clear();
                    Pagination pagination = initPagination();
                    pagination.setPageCount(1);
                    pagination.setCurrentPageIndex(0);
                    paginationBox.getChildren().clear();
                    paginationBox.getChildren().add(pagination);



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
