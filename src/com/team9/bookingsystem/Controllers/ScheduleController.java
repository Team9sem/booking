package com.team9.bookingsystem.Controllers;

import com.team9.bookingsystem.*;
import com.team9.bookingsystem.Components.DialogCallback;
import com.team9.bookingsystem.Components.PopupController;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import jfxtras.scene.control.agenda.*;

import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * Created by pontuspohl on 13/11/15.
 *
 * All Content by Pontus
 *
 * Controller for schedule.fxml popup view
 */
public class ScheduleController implements PopupController {

    // inst vars
    private MainController mainController;
    private User loggedInUser;
    private Room room;
    private User user;
    private Agenda agenda;
    private Stage stage;
    private DialogCallback callback;
    private boolean okClicked;

    // fxml mapped elements
    @FXML private VBox scheduleBox;
    @FXML private ProgressIndicator loadingProgress;
    @FXML private Label loadingLabel;
    @FXML private BorderPane progressPane;

    /**
     * JFX "Constructor" runs when associated fxml file is loaded.
     */
    public void initialize() {
        // Create the agenda A.K.A Schedule.
        agenda = new Agenda();

        // 
        agenda.newAppointmentCallbackProperty().set((localDateTimeRange) -> {
            return new Agenda.AppointmentImplLocal()
                    .withStartLocalDateTime(localDateTimeRange.getStartLocalDateTime())
                    .withEndLocalDateTime(localDateTimeRange.getEndLocalDateTime())
                    .withAppointmentGroup(new Agenda.AppointmentGroupImpl().withStyleClass("group1"));
        });

    }

    public void init(MainController mainController,Room room,User user,User loggedInUser){


        this.user = user;
        this.room = room;
        this.mainController = mainController;
        this.loggedInUser = loggedInUser;

        setupAgenda();



        agenda.setAllowDragging(false);
        agenda.setAllowResize(false);
        scheduleBox.getChildren().add(agenda);
    }
    public void setCallBack(DialogCallback callback){
        this.callback = callback;
    }
    public void setStage(Stage stage) {
        this.stage = stage;
    }
    public boolean isOkClicked(){
        return okClicked;
    }
    public void setRoom(Room room){
        this.room = room;
    }
    public void setUser(User user){
        this.user = user;
    }
    @FXML public void showNextWeek(){
        agenda.setDisplayedLocalDateTime(agenda.getDisplayedLocalDateTime().plusWeeks(1));
    }
    @FXML public void showPreviousWeek(){
        agenda.setDisplayedLocalDateTime(agenda.getDisplayedLocalDateTime().minusWeeks(1));
    }
    @FXML public void close(){
        callback.onFailure();
        stage.close();
    }

    private void setupAgenda(){

        System.out.println("setting up agenda");
        loadingProgress.setProgress(-1.0);
        loadingProgress.setVisible(true);
        progressPane.setVisible(true);
        loadingLabel.setText("Loading Schedule");

        Service<ArrayList> agendaService = new Service<ArrayList>() {
            @Override
            protected Task<ArrayList> createTask() {
                Task<ArrayList> task = new Task<ArrayList>() {
                    @Override
                    protected ArrayList call() throws Exception {

                        if(room != null){

                            MysqlUtil util = new MysqlUtil();
                            ArrayList<Booking> bookings= util.getBookings(room);

                            return bookings;

                        }
                        if(user != null){

                            MysqlUtil util = new MysqlUtil();
                            ArrayList<Booking> bookings = util.GetUserBookings(user.getUserID());



                            return bookings;

                        }
                        else{
                            this.failed();
                            return null;
                        }



                    }
                };
            return task;
            }
        };
        agendaService.start();
        agendaService.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {

                if(room != null && agendaService.getValue()!= null){
                    System.out.println("setting up a room agenda");
                    agenda.setStyle("-fx-font-family: sans-serif;");
                    System.out.println("getting bookings for"+room.toString());

                    ArrayList<Booking> bookings= agendaService.getValue();


                    ArrayList<Agenda.AppointmentImplLocal> appointments = new ArrayList<>();



                    for(Booking booking : bookings){
                        Agenda.AppointmentImplLocal appointment = new Agenda.AppointmentImplLocal();
                        appointment.withAppointmentGroup(new Agenda.AppointmentGroupImpl().withStyleClass("group0"));
                        appointment.setDescription(room.getLocation());

                        appointment.setSummary(""+booking.getRoom().getLocation()+"\n\nBooked By:\n"+booking.getUser().getUserName());
                        String date = booking.getBdate();

                        int year = Integer.parseInt(date.substring(0, 4));
                        System.out.println(year);

                        date = date.substring(5);
                        int month = Integer.parseInt(date.substring(0, 2));
                        System.out.println(month);
                        date = date.substring(3);
                        int day = Integer.parseInt(date.substring(0, 2));
                        System.out.println(day);

                        String startTime = booking.getBStart();
                        int startHour = Integer.parseInt(startTime.substring(0,2));
                        startTime = startTime.substring(3);
                        int startMinute = Integer.parseInt(startTime.substring(0,2));
                        System.out.println(startHour);
                        System.out.println(startMinute);

                        appointment.setStartLocalDateTime(LocalDateTime.of(year,month,day,startHour,startMinute));

                        String endTime = booking.getBEnd();
                        int endHour = Integer.parseInt(endTime.substring(0,2));
                        endTime = endTime.substring(3);
                        int endMinute = Integer.parseInt(endTime.substring(0,2));
                        System.out.println(endHour);
                        System.out.println(endMinute);

                        appointment.setEndLocalDateTime(LocalDateTime.of(year, month, day, endHour, endMinute));

                        appointment.withDescription("Appointment");

                        appointments.add(appointment);



                    }

                    appointments.forEach(element -> agenda.appointments().add(element));

//        appointment.setStartTime(Calendar.getInstance().);
                }
                if(user != null && agendaService.getValue() != null){
                    System.out.println("setting up a user agenda");
                    agenda.setStyle("-fx-font-family: sans-serif;");


                    ArrayList<Booking> bookings = agendaService.getValue();



                    ArrayList<Agenda.AppointmentImplLocal> appointments = new ArrayList<>();

                    for(Booking booking : bookings){
                        Agenda.AppointmentImplLocal appointment = new Agenda.AppointmentImplLocal();
                        appointment.withAppointmentGroup(new Agenda.AppointmentGroupImpl().withStyleClass("group0"));
                        appointment.setDescription(booking.getUser().getUserName());
                        appointment.setSummary(""+booking.getRoom().getLocation()+"\n\nBooked By:\n"+booking.getUser().getUserName());
                        String date = booking.getBdate();

                        int year = Integer.parseInt(date.substring(0, 4));
                        System.out.println(year);

                        date = date.substring(5);
                        int month = Integer.parseInt(date.substring(0, 2));
                        System.out.println(month);
                        date = date.substring(3);
                        int day = Integer.parseInt(date.substring(0, 2));
                        System.out.println(day);

                        String startTime = booking.getBStart();
                        int startHour = Integer.parseInt(startTime.substring(0,2));
                        startTime = startTime.substring(3);
                        int startMinute = Integer.parseInt(startTime.substring(0,2));
                        System.out.println(startHour);
                        System.out.println(startMinute);

                        appointment.setStartLocalDateTime(LocalDateTime.of(year,month,day,startHour,startMinute));

                        String endTime = booking.getBEnd();
                        int endHour = Integer.parseInt(endTime.substring(0,2));
                        endTime = endTime.substring(3);
                        int endMinute = Integer.parseInt(endTime.substring(0,2));
                        System.out.println(endHour);
                        System.out.println(endMinute);

                        appointment.setEndLocalDateTime(LocalDateTime.of(year, month, day, endHour, endMinute));

                        appointment.withDescription("Appointment");

                        appointments.add(appointment);



                    }


                    appointments.forEach(element -> agenda.appointments().add(element));
                    loadingProgress.setVisible(false);
                    loadingLabel.setVisible(false);
                }

                progressPane.setVisible(false);
            }
        });
        agendaService.setOnFailed(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {
                System.out.println("something went wrong");
            }
        });






    }
}