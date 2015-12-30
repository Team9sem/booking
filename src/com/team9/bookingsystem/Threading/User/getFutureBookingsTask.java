package com.team9.bookingsystem.Threading.User;

import com.team9.bookingsystem.Booking;
import com.team9.bookingsystem.MysqlUtil;
import com.team9.bookingsystem.User;
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
public class getFutureBookingsTask extends Task<ArrayList<Booking>> {
    private User loggedInUser;

    public getFutureBookingsTask(User loggedInUser){
        this.loggedInUser = loggedInUser;
    }
    protected ArrayList<Booking> call(){
        MysqlUtil util = new MysqlUtil();
        ArrayList<Booking> res = util.getFutureBookings(loggedInUser);
        res.stream().forEach(element -> element.setUser(util.getUser(element.getuserid())));
        res.stream().forEach(element -> element.setRoom(util.getRoom(element.getroomID())));
        return res;
    }

}