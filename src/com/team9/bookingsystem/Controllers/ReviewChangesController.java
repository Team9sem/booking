package com.team9.bookingsystem.Controllers;

import com.team9.bookingsystem.Components.UserTableView;
import com.team9.bookingsystem.DialogCallback;
import com.team9.bookingsystem.PopupController;
import com.team9.bookingsystem.Room;
import com.team9.bookingsystem.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;

/**
 * Created by pontuspohl on 27/11/15.
 */
public class ReviewChangesController implements PopupController {

    private DialogCallback callback;
    private Stage popupStage;
    private boolean okClicked = false;
    private ArrayList updatedItems;
    private ArrayList addedItems;
    private ArrayList deletedItems;


    private removeItemButton removeUpdated, removeDeleted, removeAdded;


    @FXML Tab updatedTab;
    @FXML Tab deletedTab;
    @FXML Tab addedTab;
    @FXML VBox addedTabVbox;
    @FXML VBox deletedTabVbox;
    @FXML VBox updatedTabVbox;



    public void initialize(){
        createButtons();
    }

    public void populate(){
        if( updatedItems != null && !updatedItems.isEmpty()){
            if(updatedItems.get(0) instanceof User ){
                System.out.println("users in updated table");

                UserTableView updatedItemsTable = new UserTableView();
                ArrayList<TableColumn<User,?>>  colsToRemove = new ArrayList<>();

                // remove all button Columns, which has empty column titles, using streams to filter out those
                updatedItemsTable.getColumns()
                        .stream()
                            .filter(userTableColumn -> userTableColumn.getText().isEmpty())
                                .forEach(element -> colsToRemove.add(element));
                updatedItemsTable.getColumns().removeAll(colsToRemove);
                // also make all columns non-editable since this presentation table is for review only
                updatedItemsTable.getColumns().forEach(element->element.setEditable(false));


                updatedItemsTable.getItems().addAll(updatedItems);
                updatedTabVbox.getChildren().add(updatedItemsTable);
                HBox buttonBox = new HBox();
                buttonBox.getChildren().add(removeUpdated);
                buttonBox.setAlignment(Pos.CENTER);
                updatedTabVbox.getChildren().add(buttonBox);
                updatedTabVbox.setMargin(buttonBox,new Insets(20,0,20,0));
                updatedTab.setContent(updatedTabVbox);
            }
            if( addedItems != null && !addedItems.isEmpty()){

                UserTableView addedItemsTable = new UserTableView();

                ArrayList<TableColumn<User,?>>  colsToRemove = new ArrayList<>();

                // remove all button Columns, which has empty column titles, using streams to filter out those
                addedItemsTable.getColumns()
                        .stream()
                        .filter(userTableColumn -> userTableColumn.getText().isEmpty())
                        .forEach(element -> colsToRemove.add(element));
                addedItemsTable.getColumns().removeAll(colsToRemove);
                // also make all columns non-editable since this presentation table is for review only
                addedItemsTable.getColumns().forEach(element->element.setEditable(false));

                addedItemsTable.getItems().addAll(addedItems);
                addedTabVbox.getChildren().add(addedItemsTable);
                HBox buttonBox = new HBox();
                buttonBox.getChildren().add(removeAdded);
                buttonBox.setAlignment(Pos.CENTER);
                addedTabVbox.getChildren().add(buttonBox);
                addedTabVbox.setMargin(buttonBox,new Insets(20,0,20,0));
                addedTab.setContent(addedTabVbox);
            }
            if( deletedItems != null && !deletedItems.isEmpty()){

                UserTableView deletedItemsTable = new UserTableView();
                ArrayList<TableColumn<User,?>>  colsToRemove = new ArrayList<>();

                // remove all button Columns, which has empty column titles, using streams to filter out those
                deletedItemsTable.getColumns()
                        .stream()
                        .filter(userTableColumn -> userTableColumn.getText().isEmpty())
                        .forEach(element -> colsToRemove.add(element));
                deletedItemsTable.getColumns().removeAll(colsToRemove);
                // also make all columns non-editable since this presentation table is for review only
                deletedItemsTable.getColumns().forEach(element->element.setEditable(false));

                deletedItemsTable.getItems().addAll(addedItems);
                deletedTabVbox.getChildren().add(deletedItemsTable);
                HBox buttonBox = new HBox();
                buttonBox.getChildren().add(removeDeleted);
                buttonBox.setAlignment(Pos.CENTER);
                deletedTabVbox.getChildren().add(buttonBox);
                deletedTabVbox.setMargin(buttonBox,new Insets(20,0,20,0));
                addedTab.setContent(addedTabVbox);
            }
        }


    }


    private void createButtons(){
        removeUpdated = new removeItemButton();
        removeAdded   = new removeItemButton();
        removeDeleted = new removeItemButton();
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
        callback.onSuccess(true);
    }


    private class removeItemButton extends Button{

        public removeItemButton(){
            super("remove Selected Item");
            setPrefWidth(150);
        }

    }

}
