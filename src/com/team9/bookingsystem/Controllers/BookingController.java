package com.team9.bookingsystem.Controllers;

import com.team9.bookingsystem.MysqlUtil;

import com.team9.bookingsystem.Threading.LoginService;
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
//    private ArrayList<Room> searchResult;
//    private Room selectedRoom;

    // ContainerElements
    @FXML AnchorPane topAnchorPane;
    @FXML AnchorPane searchAnchorPane;
    @FXML BorderPane borderPane;
    @FXML HBox paginationBox;
    @FXML AnchorPane resultAnchorPane;
    
    //SearchAnchorPaneElements
    @FXML Label SearchPreferences;
    @FXML Label Features;
    @FXML CheckBox CoffeMachine;
    @FXML CheckBox Whiteboard;
    @FXML CheckBox Projector;
    @FXML Label RoomSize;
    @FXML CheckBox Small;
    @FXML CheckBox Medium;
    @FXML CheckBox Large;
    @FXML Label Date;
    @FXML DatePicker Datepicker;
    @FXML Label FromTime;
    @FXML TextField FromTimeInput;
    @FXML Label ToTime;
    @FXML TextField ToTimeInput;
    @FXML Label Location;
    @FXML ChoiceBox LocationPick;
    @FXML Button SearchButton;
    
    



    // this method runs when controller is started
    public void initialize() {
    	util = new MysqlUtil();
        paginationBox.setAlignment(Pos.CENTER);

//
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
//                VBox vBox = createPage(PageIndex);


                    return null;
//                return vBox;
            }

        });
        return pagination;
    }

//    public VBox createPage(int pageIndex){
//
//        VBox vBox = new VBox(5);
//
//        vBox.setAlignment(Pos.CENTER);
//        final ToggleGroup group = new ToggleGroup();
//
//        group.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
//            @Override
//            public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
//                if(newValue!=null){
//                    selectedRoom = (Room)group.getSelectedToggle().getUserData();
//
//                }
//            }
//        });
//
//        int page = pageIndex * getElementsPerPage();
//        for(int i=page;i < page + getElementsPerPage();i++){
//            if(i < searchResult.size()){
//                HBox element = new HBox();
//                VBox content = new VBox();
//
//                ToggleButton button = new ToggleButton("Room in "+searchResult.get(i).getLocation()+
//                        "\n Size: " + searchResult.get(i).getRoomSize());
//                button.setUserData(searchResult.get(i));
//                button.setToggleGroup(group);
//
//                button.setPrefWidth(250);
//
//                content.getChildren().add(button);
//                content.setAlignment(Pos.CENTER_LEFT);
//                content.setPrefWidth(250);
//                element.getChildren().add(content);
//                element.setAlignment(Pos.CENTER);
//                vBox.getChildren().add(element);
//                vBox.setVgrow(element, Priority.ALWAYS);
//
//            }
//            else{
//                HBox element = new HBox();
//                Region region = new Region();
//                element.getChildren().add(region);
//                element.setAlignment(Pos.CENTER);
//                element.setHgrow(region,Priority.ALWAYS);
//                vBox.getChildren().add(element);
//                vBox.setVgrow(element, Priority.ALWAYS);
//            }
//
//
//        }
//        return vBox;
//    }
    private int getElementsPerPage(){
        return 5;
    }

    //  Method that displays show search result




    // Todo: add method to handle search button








}
