package com.team9.bookingsystem.Threading.User;

import com.team9.bookingsystem.Booking;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

import java.util.ArrayList;

/**
 * Created by pontuspohl on 27/12/15.
 *
 *
 * All Content by Pontus
 *
 *
 */
public class getBookingsService extends Service<ArrayList<Booking>> {

    private Task<ArrayList<Booking>> task;

    public getBookingsService(Task<ArrayList<Booking>> task){
        this.task = task;
    }
    protected Task<ArrayList<Booking>> createTask(){
        return task;
    }


}
