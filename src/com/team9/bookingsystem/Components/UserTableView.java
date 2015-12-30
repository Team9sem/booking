package com.team9.bookingsystem.Components;

import com.team9.bookingsystem.MysqlUtil;
import com.team9.bookingsystem.Room;
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
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.Callback;
import javafx.util.converter.IntegerStringConverter;
import javafx.util.converter.LongStringConverter;

import java.util.ArrayList;

/**
 * Created by pontuspohl on 26/11/15.
 * All Content by Pontus Pohl
 * Custom TableView used in Administrator Interface
 */
public class UserTableView extends TableView<User> {




    private ObservableList<User> tableviewData = FXCollections.observableArrayList();



    private ArrayList<User> updatedUsers = new ArrayList<>();
    private ArrayList<User> deletedUsers = new ArrayList<>();
    private ArrayList<User> addedUsers   = new ArrayList<>();
    private ArrayList<Button> tableButtons = new ArrayList<>();


    public UserTableView(){

        setupTable();
    }

    public ArrayList<User> getDeletedUsers() {
        return deletedUsers;

    }

    public void setDeletedUsers(ArrayList<User> deletedUsers) {
        this.deletedUsers = deletedUsers;
    }

    public ObservableList<User> getTableviewData() {
        return tableviewData;
    }

    public void setTableviewData(ObservableList<User> tableviewData) {
        this.tableviewData = tableviewData;
    }

    public ArrayList<User> getAddedUsers() {
        return addedUsers;
    }

    public void setAddedUsers(ArrayList<User> addedUsers) {
        this.addedUsers = addedUsers;
    }
    public void setUpdatedUsers(ArrayList<User> updatedUsers) {
        this.updatedUsers = updatedUsers;
    }

    public ArrayList<Button> getTableButtons() {

        return tableButtons;
    }
    public ArrayList<User> getUpdatedUsers() {
        return updatedUsers;
    }

    private void setupTable(){

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
                    System.out.println(updatedUsers.toString());
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


            }


        });

        lastName.setCellValueFactory(new PropertyValueFactory<User,String>("lastName"));



        TableColumn userType = new TableColumn("Type");
        userType.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));

        userType.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<User,Integer>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<User,Integer> event) {

                User updatedUser = event.getTableView().getItems().get(event.getTablePosition()
                        .getRow());
                updatedUser.setUserType(event.getNewValue());
                if(!updatedUsers.contains(updatedUser)){
                    System.out.println("in first if,\n this is updatedRooms");
                    updatedUsers.add(updatedUser);

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

                }
            }
        });

        pNumberCol.setCellValueFactory(new PropertyValueFactory<User,Long>("pNumber"));






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

                UserTableButtonCell buttonCell = new UserTableButtonCell();

                tableButtons.add(buttonCell.getButton());

                return buttonCell;
            }
        });

        getColumns().addAll(userId,username,firstName,lastName,passWord,userType,street,zip,pNumberCol,buttons);
        setColumnResizePolicy(new CustomColumnResizePolicy());
//        userTableView.setColumnResizePolicy(new Callback<TableView.ResizeFeatures, Boolean>() {
//            @Override
//            public Boolean call(TableView.ResizeFeatures param) {
//                param.getColumn().prefWidthProperty().bind(roomTableView.widthProperty().divide(9.0));
//                return true;
//            }
//        });
        setEditable(true);

    }

    private class UserTableButtonCell extends TableCell<User,Boolean> {
        Button cellButton = new Button("Show Schedule");
        private Room rowRoom;
        private User rowUser;

        public Button getButton(){
            return cellButton;
        }

        UserTableButtonCell(){
//

//            cellButton.setOnAction(new EventHandler<ActionEvent>() {
//                @Override
//                public void handle(ActionEvent event) {
//                    System.out.println("TableButton Clicked");
//                    getSelectionModel().select(getTableRow().getIndex());
//
//                    System.out.println(getItems().get(getTableRow().getIndex()).toString());
////                    showSchedule(roomTableView.getItems().get(getTableRow().getIndex()),loggedInUser);
//
//
//                }
//            });


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



}
