package com.team9.bookingsystem.Components;

import com.team9.bookingsystem.Room;
import com.team9.bookingsystem.User;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

import java.util.ArrayList;

/**
 * Created by pontuspohl on 26/11/15.
 */
public class RoomTableView extends TableView<Room> {



    private ObservableList<Room> tableviewData = FXCollections.observableArrayList();




    private ArrayList<Room> updatedRooms = new ArrayList<>();
    private ArrayList<Room> deletedRooms = new ArrayList<>();
    private ArrayList<Room> addedRooms   = new ArrayList<>();


    private ArrayList<Button> tableButtons = new ArrayList<>();


    public RoomTableView(){
        setupTable();
    }
    public ArrayList<Button> getTableButtons() {
        return tableButtons;
    }

    public ObservableList<Room> getTableviewData() {
        return tableviewData;
    }

    public ArrayList<Room> getDeletedRooms() {
        return deletedRooms;
    }
    public ArrayList<Room> getUpdatedRooms() {
        return updatedRooms;
    }

    public ArrayList<Room> getAddedRooms() {
        return addedRooms;
    }

    public void setTableviewData(ObservableList<Room> tableviewData) {
        this.tableviewData = tableviewData;
    }




    private void setupTable(){

        TableColumn roomid = new TableColumn("ID");

        roomid.setCellValueFactory(new PropertyValueFactory<Room,Integer>("roomID"));
        roomid.setPrefWidth(10.0);


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

            }
        });

        location.setCellValueFactory(new PropertyValueFactory<Room,String>("location"));
        location.setPrefWidth(30.0);

        TableColumn size = new TableColumn("Size");
        size.setCellValueFactory(new PropertyValueFactory<Room,String>("roomSize"));
        size.setPrefWidth(10.0);


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

                }
                else{
                    event.getTableColumn().setVisible(false);
                    event.getTableColumn().setVisible(true);


                }
            }
        });

        projector.setCellValueFactory(new PropertyValueFactory<Room,Integer>("hasProjector"));
        projector.setPrefWidth(10.0);

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

                }
                else{
                    event.getTableColumn().setVisible(false);
                    event.getTableColumn().setVisible(true);


                }


            }
        });
        whiteboard.setCellValueFactory(new PropertyValueFactory<Room,Integer>("hasWhiteboard"));
        whiteboard.setPrefWidth(10.0);

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

                }
                else{
                    event.getTableColumn().setVisible(false);
                    event.getTableColumn().setVisible(true);


                }
            }
        });
        coffeMachine.setCellValueFactory(new PropertyValueFactory<Room,Integer>("hasCoffeeMachine"));
        coffeMachine.setPrefWidth(10.0);

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

                RoomTableButtonCell roomTableButtonCell = new RoomTableButtonCell();
                tableButtons.add(roomTableButtonCell.getButton());
                return roomTableButtonCell;
            }
        });

        getColumns().addAll(roomid,location,size,projector,whiteboard,coffeMachine,buttons);
//        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        setColumnResizePolicy(new CustomColumnResizePolicy());
        setEditable(true);


    }

    private class RoomTableButtonCell extends TableCell<Room,Boolean>{
        Button cellButton = new Button("Show Schedule");
        private Room rowRoom;
        private User rowUser;

        public Button getButton(){
            return cellButton;
        }

        RoomTableButtonCell(){



        }



        @Override
        protected void updateItem(Boolean t, boolean empty){
            super.updateItem(t,empty);
            if(!empty){

                cellButton.setStyle("-fx-font-size: 9px");
                setGraphic(cellButton);
            }
        }
    }

}
