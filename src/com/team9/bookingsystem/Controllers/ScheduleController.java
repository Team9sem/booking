package com.team9.bookingsystem.Controllers;

import com.team9.bookingsystem.*;
import javafx.fxml.FXML;
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

        if(room != null){

            agenda.setStyle("-fx-font-family: sans-serif;");

            MysqlUtil util = new MysqlUtil();
            ArrayList<Booking> bookings= util.getBookings(room);
            System.out.println(bookings);

            ArrayList<Agenda.AppointmentImplLocal> appointments = new ArrayList<>();

            for(Booking booking : bookings){
                Agenda.AppointmentImplLocal appointment = new Agenda.AppointmentImplLocal();
                appointment.withAppointmentGroup(new Agenda.AppointmentGroupImpl().withStyleClass("group0"));
                appointment.setDescription(room.getLocation());
                User appointmentUser = util.getUserFromId(booking.getUser().getUserID());
                appointment.setSummary("Room: "+room.getLocation()+"\nBooked By:"+appointmentUser.getFirstName());
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

                appointment.withDescription("Bajs");

                appointments.add(appointment);



            }

            appointments.forEach(element -> agenda.appointments().add(element));

//        appointment.setStartTime(Calendar.getInstance().);
        }
        if(user != null){

            agenda.setStyle("-fx-font-family: sans-serif;");

            MysqlUtil util = new MysqlUtil();
            ArrayList<Booking> bookings = new ArrayList<>();
            Arrays.asList(util.GetUserBookings(user.getUserID()))
                    .stream()
                    .forEach(element -> bookings.add(element));

            System.out.println(bookings);

            ArrayList<Agenda.AppointmentImplLocal> appointments = new ArrayList<>();

            for(Booking booking : bookings){
                Agenda.AppointmentImplLocal appointment = new Agenda.AppointmentImplLocal();
                appointment.withAppointmentGroup(new Agenda.AppointmentGroupImpl().withStyleClass("group0"));
                appointment.setDescription(user.getUserName());
                User appointmentUser = util.getUserFromId(booking.getUser().getUserID());
                appointment.setSummary("Room: "+room.getLocation()+"\nBooked By:"+appointmentUser.getFirstName());
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

                appointment.withDescription("Bajs");

                appointments.add(appointment);



            }

            appointments.forEach(element -> agenda.appointments().add(element));

        }

    }
}