package com.team9.bookingsystem.Controllers;

import com.team9.bookingsystem.*;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import jfxtras.scene.control.agenda.*;

import java.awt.print.Book;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Year;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by pontuspohl on 13/11/15.
 */
public class ScheduleController {


    private MainController mainController;
    private User loggedInUser;
    private Room room;
    private User user;
    private Agenda agenda;
    @FXML private VBox scheduleBox;


    public void initialize() {
        agenda = new Agenda();
        agenda.setAllowDragging(false);
        agenda.setAllowResize(false);



        agenda.newAppointmentCallbackProperty().set( (localDateTimeRange) -> {
            return new Agenda.AppointmentImplLocal()
                    .withStartLocalDateTime(localDateTimeRange.getStartLocalDateTime())
                    .withEndLocalDateTime(localDateTimeRange.getEndLocalDateTime())
                    .withAppointmentGroup(new Agenda.AppointmentGroupImpl().withStyleClass("group1")); // it is better to have a map of appointment groups to get from
        });

    }

    public void init(MainController mainController,Room room,User user,User loggedInUser){



        this.mainController = mainController;
        this.loggedInUser = loggedInUser;
        scheduleBox.getChildren().add(agenda);
        setupAgenda();
    }
    private void setupAgenda(){

        if(room != null){

            agenda.setStyle("-fx-font-family: sans-serif");

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

    }
}