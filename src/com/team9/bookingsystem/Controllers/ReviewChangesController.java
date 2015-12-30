package com.team9.bookingsystem.Controllers;

import com.team9.bookingsystem.Components.RoomTableView;
import com.team9.bookingsystem.Components.UserTableView;
import com.team9.bookingsystem.Components.DialogCallback;
import com.team9.bookingsystem.Components.PopupController;
import com.team9.bookingsystem.Room;
import com.team9.bookingsystem.User;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;

/**
 * Created by pontuspohl on 27/11/15.
 *
 * All Content by Pontus
 *
 * Controller for reviewChanges.fxml popup view
 */
public class ReviewChangesController implements PopupController {

    private DialogCallback callback;
    private Stage popupStage;
    private boolean okClicked = false;
    private ArrayList updatedItems;
    private ArrayList addedItems;
    private ArrayList deletedItems;



    private UserTableView userTableView;
    private RoomTableView roomTableView;




    private reviewing reviewingObjects;


    private removeItemButton removeUpdated, removeDeleted, removeAdded;


    @FXML Tab updatedTab;
    @FXML Tab deletedTab;
    @FXML Tab addedTab;
    @FXML VBox addedTabVbox;
    @FXML VBox deletedTabVbox;
    @FXML VBox updatedTabVbox;

    public enum reviewing{user,room,none}

    public void initialize(){
        createButtons();

    }

    public void setRoomTableView(RoomTableView roomTableView) {
        this.roomTableView = roomTableView;
    }
    public void setUserTableView(UserTableView userTableView) {
        this.userTableView = userTableView;

    }


    public void populate() {
        if (updatedItems != null && !updatedItems.isEmpty()) {

            if (updatedItems.get(0) instanceof User) {
                System.out.println("users in updated table");

                UserTableView updatedItemsTable = new UserTableView();
                updatedItemsTable.setPrefHeight(350);
                updatedItemsTable.setPrefWidth(1000);
                ArrayList<TableColumn<User, ?>> colsToRemove = new ArrayList<>();

                // remove all button Columns, which has empty column titles, using streams to filter out those
                updatedItemsTable.getColumns()
                        .stream()
                        .filter(userTableColumn -> userTableColumn.getText().isEmpty())
                        .forEach(element -> colsToRemove.add(element));
                updatedItemsTable.getColumns().removeAll(colsToRemove);
                // also make all columns non-editable since this presentation table is for review only
                updatedItemsTable.getColumns().forEach(element -> element.setEditable(false));


                updatedItemsTable.getItems().addAll(updatedItems);
                updatedTabVbox.getChildren().add(updatedItemsTable);
                HBox buttonBox = new HBox();
                buttonBox.getChildren().add(removeUpdated);

                removeUpdated.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        updatedItemsTable.getItems().
                                remove(updatedItemsTable.
                                        getSelectionModel().getSelectedItem());
                        updatedItems.remove(updatedItemsTable.
                                getSelectionModel().getSelectedItem());

                    }
                });

                buttonBox.setAlignment(Pos.CENTER_LEFT);
                buttonBox.setMargin(removeUpdated,new Insets(0,0,0,20));
                updatedTabVbox.getChildren().add(buttonBox);
                updatedTabVbox.setMargin(buttonBox, new Insets(20, 0, 20, 0));
                updatedTab.setContent(updatedTabVbox);
            }
            else if(updatedItems.get(0) instanceof Room){
                RoomTableView updatedItemsTable = new RoomTableView();
                updatedItemsTable.setPrefHeight(350);
                updatedItemsTable.setPrefWidth(1000);
                ArrayList<TableColumn<Room, ?>> colsToRemove = new ArrayList<>();

                // remove all button Columns, which has empty column titles, using streams to filter out those
                updatedItemsTable.getColumns()
                        .stream()
                        .filter(userTableColumn -> userTableColumn.getText().isEmpty())
                        .forEach(element -> colsToRemove.add(element));
                updatedItemsTable.getColumns().removeAll(colsToRemove);
                // also make all columns non-editable since this presentation table is for review only
                updatedItemsTable.getColumns().forEach(element -> element.setEditable(false));


                updatedItemsTable.getItems().addAll(updatedItems);
                updatedTabVbox.getChildren().add(updatedItemsTable);
                HBox buttonBox = new HBox();
                buttonBox.getChildren().add(removeUpdated);
                removeUpdated.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        updatedItemsTable.getItems().
                                remove(updatedItemsTable.
                                        getSelectionModel().getSelectedItem());
                        updatedItems.remove(updatedItemsTable.
                                getSelectionModel().getSelectedItem());

                    }
                });

                buttonBox.setAlignment(Pos.CENTER_LEFT);
                buttonBox.setMargin(removeUpdated,new Insets(0,0,0,20));
                updatedTabVbox.getChildren().add(buttonBox);
                updatedTabVbox.setMargin(buttonBox, new Insets(20, 0, 20, 0));
                updatedTab.setContent(updatedTabVbox);



            }

        } else {


            HBox textBox = new HBox();
            textBox.setStyle("-fx-background-color: white;");
            Label noResultsLabel = new Label("No Items");
            noResultsLabel.getStyleClass().add("login-title");
            noResultsLabel.setStyle("-fx-text-fill: black");
            textBox.getChildren().add(noResultsLabel);
            textBox.setAlignment(Pos.CENTER);
            textBox.setPrefHeight(350);
            textBox.setPrefWidth(1000);
            updatedTabVbox.getChildren().clear();
            updatedTabVbox.getChildren().add(textBox);
        }
        if (addedItems != null && !addedItems.isEmpty()) {
            if (addedItems.get(0) instanceof User) {
                UserTableView addedItemsTable = new UserTableView();
                addedItemsTable.setPrefHeight(350);
                addedItemsTable.setPrefWidth(1000);
                ArrayList<TableColumn<User, ?>> colsToRemove = new ArrayList<>();

                // remove all button Columns, which has empty column titles, using streams to filter out those
                addedItemsTable.getColumns()
                        .stream()
                        .filter(userTableColumn -> userTableColumn.getText().isEmpty())
                        .forEach(element -> colsToRemove.add(element));
                addedItemsTable.getColumns().removeAll(colsToRemove);
                // also make all columns non-editable since this presentation table is for review only
                addedItemsTable.getColumns().forEach(element -> element.setEditable(false));

                addedItemsTable.getItems().addAll(addedItems);
                addedTabVbox.getChildren().add(addedItemsTable);
                HBox buttonBox = new HBox();
                buttonBox.getChildren().add(removeAdded);
                removeAdded.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        addedItemsTable.getItems().
                                remove(addedItemsTable.
                                        getSelectionModel().getSelectedItem());
                        addedItems.remove(addedItemsTable.
                                getSelectionModel().getSelectedItem());

                    }
                });

                buttonBox.setAlignment(Pos.CENTER_LEFT);
                buttonBox.setMargin(removeAdded,new Insets(0,0,0,20));
                addedTabVbox.getChildren().add(buttonBox);
                addedTabVbox.setMargin(buttonBox, new Insets(20, 0, 20, 0));
                addedTab.setContent(addedTabVbox);
            }
            else if(addedItems.get(0) instanceof Room){
                RoomTableView addedItemsTable = new RoomTableView();
                addedItemsTable.setPrefHeight(350);
                addedItemsTable.setPrefWidth(1000);
                ArrayList<TableColumn<Room, ?>> colsToRemove = new ArrayList<>();

                // remove all button Columns, which has empty column titles, using streams to filter out those
                addedItemsTable.getColumns()
                        .stream()
                        .filter(userTableColumn -> userTableColumn.getText().isEmpty())
                        .forEach(element -> colsToRemove.add(element));
                addedItemsTable.getColumns().removeAll(colsToRemove);
                // also make all columns non-editable since this presentation table is for review only
                addedItemsTable.getColumns().forEach(element -> element.setEditable(false));

                addedItemsTable.getItems().addAll(addedItems);
                addedTabVbox.getChildren().add(addedItemsTable);
                HBox buttonBox = new HBox();
                buttonBox.getChildren().add(removeAdded);
                removeAdded.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        addedItemsTable.getItems().
                                remove(addedItemsTable.
                                        getSelectionModel().getSelectedItem());
                        addedItems.remove(addedItemsTable.
                                getSelectionModel().getSelectedItem());

                    }
                });

                buttonBox.setAlignment(Pos.CENTER_LEFT);
                buttonBox.setMargin(removeAdded,new Insets(0,0,0,20));
                addedTabVbox.getChildren().add(buttonBox);
                addedTabVbox.setMargin(buttonBox, new Insets(20, 0, 20, 0));
                addedTab.setContent(addedTabVbox);



            }
        } else {
            HBox textBox = new HBox();
            textBox.setStyle("-fx-background-color: white;");
            Label noResultsLabel = new Label("No Items");
            noResultsLabel.getStyleClass().add("login-title");
            noResultsLabel.setStyle("-fx-text-fill: black");
            textBox.getChildren().add(noResultsLabel);
            textBox.setAlignment(Pos.CENTER);
            textBox.setPrefHeight(350);
            textBox.setPrefWidth(1000);
            addedTabVbox.getChildren().clear();
            addedTabVbox.getChildren().add(textBox);
        }
        if (deletedItems != null && !deletedItems.isEmpty()) {
            if (deletedItems.get(0) instanceof User) {
                System.out.println("adding deleted items");
                UserTableView deletedItemsTable = new UserTableView();
                deletedItemsTable.setPrefWidth(1000);
                deletedItemsTable.setPrefHeight(350);
                ArrayList<TableColumn<User, ?>> colsToRemove = new ArrayList<>();

                // remove all button Columns, which has empty column titles, using streams to filter out those
                deletedItemsTable.getColumns()
                        .stream()
                        .filter(userTableColumn -> userTableColumn.getText().isEmpty())
                        .forEach(element -> colsToRemove.add(element));
                deletedItemsTable.getColumns().removeAll(colsToRemove);
                // also make all columns non-editable since this presentation table is for review only
                deletedItemsTable.getColumns().forEach(element -> element.setEditable(false));

                deletedItemsTable.getItems().addAll(deletedItems);
                deletedTabVbox.getChildren().add(deletedItemsTable);
                HBox buttonBox = new HBox();
                buttonBox.getChildren().add(removeDeleted);
                removeDeleted.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        deletedItemsTable.getItems().
                                remove(deletedItemsTable.
                                        getSelectionModel().getSelectedItem());
                        deletedItems.remove(deletedItemsTable.
                                getSelectionModel().getSelectedItem());

                    }
                });

                buttonBox.setAlignment(Pos.CENTER_LEFT);
                buttonBox.setMargin(removeDeleted,new Insets(0,0,0,20));
                deletedTabVbox.getChildren().add(buttonBox);
                deletedTabVbox.setMargin(buttonBox, new Insets(20, 0, 20, 0));
                deletedTab.setContent(deletedTabVbox);
            }
            else if (deletedItems.get(0) instanceof Room) {
                System.out.println("adding deleted items");
                RoomTableView deletedItemsTable = new RoomTableView();
                deletedItemsTable.setPrefWidth(1000);
                deletedItemsTable.setPrefHeight(350);
                ArrayList<TableColumn<Room, ?>> colsToRemove = new ArrayList<>();

                // remove all button Columns, which has empty column titles, using streams to filter out those
                deletedItemsTable.getColumns()
                        .stream()
                        .filter(userTableColumn -> userTableColumn.getText().isEmpty())
                        .forEach(element -> colsToRemove.add(element));
                deletedItemsTable.getColumns().removeAll(colsToRemove);
                // also make all columns non-editable since this presentation table is for review only
                deletedItemsTable.getColumns().forEach(element -> element.setEditable(false));

                deletedItemsTable.getItems().addAll(deletedItems);
                deletedTabVbox.getChildren().add(deletedItemsTable);
                HBox buttonBox = new HBox();
                buttonBox.getChildren().add(removeDeleted);
                removeDeleted.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        deletedItemsTable.getItems().
                                remove(deletedItemsTable.
                                        getSelectionModel().getSelectedItem());
                        deletedItems.remove(deletedItemsTable.
                                getSelectionModel().getSelectedItem());

                    }
                });

                buttonBox.setAlignment(Pos.CENTER_LEFT);
                buttonBox.setMargin(removeDeleted,new Insets(0,0,0,20));
                deletedTabVbox.getChildren().add(buttonBox);
                deletedTabVbox.setMargin(buttonBox, new Insets(20, 0, 20, 0));
                deletedTab.setContent(deletedTabVbox);
            }
        }
        else
        {
            HBox textBox = new HBox();
            textBox.setStyle("-fx-background-color: white;");
            Label noResultsLabel = new Label("No Items");
            noResultsLabel.getStyleClass().add("login-title");
            noResultsLabel.setStyle("-fx-text-fill: black");
            textBox.getChildren().add(noResultsLabel);
            textBox.setAlignment(Pos.CENTER);
            textBox.setPrefHeight(350);
            textBox.setPrefWidth(1000);
            deletedTabVbox.getChildren().clear();
            deletedTabVbox.getChildren().add(textBox);
        }
    }




    private void createButtons(){
        removeUpdated = new removeItemButton();
        removeAdded   = new removeItemButton();
        removeDeleted = new removeItemButton();
    }
    public void setReviewingObjects(reviewing reviewingObjects) {
        this.reviewingObjects = reviewingObjects;
    }
    public reviewing getReviewingObjects() {
        return reviewingObjects;
    }
    public void setUpdatedItems(ArrayList updatedItems){
        this.updatedItems = updatedItems;
    }

    public void setDeletedItems(ArrayList deletedItems){
        this.deletedItems = deletedItems;
    }
    public void setAddedItems(ArrayList addedItems){
        this.addedItems = addedItems;
    }


    public void setStage(Stage stage){
        this.popupStage = stage;
    }
    public void setCallBack(DialogCallback callBack){
        this.callback = callBack;
    }
    public boolean isOkClicked(){
        return okClicked;
    }
    @FXML public void Cancel(ActionEvent event){

            callback.onFailure();
            popupStage.close();

    }
    @FXML public void CommitChanges(ActionEvent actionEvent){
        okClicked = true;
        if(reviewingObjects == reviewing.user){
            userTableView.setDeletedUsers(deletedItems);
        userTableView.setAddedUsers(addedItems);
        userTableView.setUpdatedUsers(updatedItems);
        callback.onSuccess(true);
        popupStage.close();
        }
        else if(reviewingObjects == reviewing.room){
            roomTableView.setDeletedRooms(deletedItems);
            roomTableView.setAddedRooms(addedItems);
            roomTableView.setUpdatedRooms(updatedItems);
            callback.onSuccess(true);
            popupStage.close();
        }
    }


    private class removeItemButton extends Button{

        public removeItemButton(){
            super("Remove Selected Item");
            setPrefWidth(200);
            getStyleClass().add("search-button");
        }

    }

}
