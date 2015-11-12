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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
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

public class TestAdminController {


    //Logged in user
    User loggedInUser;
    // Parent Controller
    private MainController mainController;
    // Mysqlutil for Database Operations
    private MysqlUtil util;
    // ArrayList Storing the most recent searchResult;
    private ArrayList<Room> searchResult;
    private ObservableList<Room> roomData = FXCollections.observableArrayList();
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
//        location.setCellFactory(new Callback<TableColumn<Room,String>, TableCell<Room,String>>() {
//            @Override
//            public TableCell<Room,String> call(TableColumn<Room,String> param) {
//                return null;
//            }
//        });


        location.setCellValueFactory(new PropertyValueFactory<Room,String>("location"));

        TableColumn size = new TableColumn("Size");
        size.setCellValueFactory(new PropertyValueFactory<Room,String>("roomSize"));

        TableColumn projector = new TableColumn("Projector");
        projector.setCellValueFactory(new PropertyValueFactory<Room,Integer>("hasProjector"));
        TableColumn whiteboard = new TableColumn("WhiteBoard");
        whiteboard.setCellValueFactory(new PropertyValueFactory<Room,Integer>("hasWhiteboard"));
        TableColumn coffeMachine = new TableColumn("CoffeeMachine");
        coffeMachine.setCellValueFactory(new PropertyValueFactory<Room,Integer>("hasCoffeeMachine"));

        table.getColumns().addAll(roomid,location,size,projector,whiteboard,coffeMachine);
        table.setEditable(true);


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
        int toIndex = Math.min(pageIndex + getElementsPerPage(),roomData.size());

        table.setItems(FXCollections.observableArrayList(roomData.subList(fromIndex,toIndex)));

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


        System.out.println(fromHour+" : "+toHour);


        System.out.println(datePicker.getValue().toString());
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

                if (searchResult != null) {

                    Pagination pagination = initPagination();
                    roomData.addAll(searchResult);

                    System.out.println(searchResult.size());
                    if (searchResult.size() <= 20) {
                        System.out.println("if happened");

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
