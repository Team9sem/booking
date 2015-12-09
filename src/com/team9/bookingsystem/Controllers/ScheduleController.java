package com.team9.bookingsystem.Controllers;

import com.team9.bookingsystem.*;
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

import java.awt.print.Book;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Year;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by pontuspohl on 13/11/15.
 */
public class ScheduleController implements PopupController {


    private MainController mainController;
    private User loggedInUser;
    private Room room;
    private User user;
    private Agenda agenda;
    private Stage stage;
    private DialogCallback callback;
    private boolean okClicked;
    @FXML private VBox scheduleBox;
    @FXML private ProgressIndicator loadingProgress;
    @FXML private Label loadingLabel;
    @FXML private BorderPane progressPane;


    public void initialize() {
        agenda = new Agenda();
        agenda.setAllowDragging(false);
        agenda.setAllowResize(false);




        agenda.newAppointmentCallbackProperty().set( (localDateTimeRange) -> {
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
        scheduleBox.getChildren().add(agenda);
        setupAgenda();
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
                            System.out.println(bookings.size());
                            System.out.println(bookings);
                            return bookings;

                        }
                        if(user != null){

                            MysqlUtil util = new MysqlUtil();
                            ArrayList<Booking> bookings = util.GetUserBookings(user.getUserID());

                            System.out.println(bookings.size());
                            System.out.println(bookings);

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
                    System.out.println(bookings.size());
                    System.out.println(bookings);

                    ArrayList<Agenda.AppointmentImplLocal> appointments = new ArrayList<>();



                    for(Booking booking : bookings){
                        Agenda.AppointmentImplLocal appointment = new Agenda.AppointmentImplLocal();
                        appointment.withAppointmentGroup(new Agenda.AppointmentGroupImpl().withStyleClass("group0"));
                        appointment.setDescription(room.getLocation());

                        appointment.setSummary("Room: "+booking.getRoom().getLocation()+"\nBooked By:"+booking.getUser().getUserName());
                        String date = booking.getbdate();

                        int year = Integer.parseInt(date.substring(0, 4));
                        System.out.println(year);

                        date = date.substring(5);
                        int month = Integer.parseInt(date.substring(0, 2));
                        System.out.println(month);
                        date = date.substring(3);
                        int day = Integer.parseInt(date.substring(0, 2));
                        System.out.println(day);

                        String startTime = booking.getbStart();
                        int startHour = Integer.parseInt(startTime.substring(0,2));
                        startTime = startTime.substring(3);
                        int startMinute = Integer.parseInt(startTime.substring(0,2));
                        System.out.println(startHour);
                        System.out.println(startMinute);

                        appointment.setStartLocalDateTime(LocalDateTime.of(year,month,day,startHour,startMinute));

                        String endTime = booking.getbEnd();
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

                    System.out.println(bookings.size());
                    System.out.println(bookings);

                    ArrayList<Agenda.AppointmentImplLocal> appointments = new ArrayList<>();

                    for(Booking booking : bookings){
                        Agenda.AppointmentImplLocal appointment = new Agenda.AppointmentImplLocal();
                        appointment.withAppointmentGroup(new Agenda.AppointmentGroupImpl().withStyleClass("group0"));
                        appointment.setDescription(booking.getUser().getUserName());
                        appointment.setSummary("Room: "+booking.getRoom().getLocation()+"\nBooked By:"+booking.getUser().getFirstName());
                        String date = booking.getbdate();

                        int year = Integer.parseInt(date.substring(0, 4));
                        System.out.println(year);

                        date = date.substring(5);
                        int month = Integer.parseInt(date.substring(0, 2));
                        System.out.println(month);
                        date = date.substring(3);
                        int day = Integer.parseInt(date.substring(0, 2));
                        System.out.println(day);

                        String startTime = booking.getbStart();
                        int startHour = Integer.parseInt(startTime.substring(0,2));
                        startTime = startTime.substring(3);
                        int startMinute = Integer.parseInt(startTime.substring(0,2));
                        System.out.println(startHour);
                        System.out.println(startMinute);

                        appointment.setStartLocalDateTime(LocalDateTime.of(year,month,day,startHour,startMinute));

                        String endTime = booking.getbEnd();
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