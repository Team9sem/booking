package com.team9.bookingsystem;

public class UserBookingsProfile {

/**
 * Created by Mayra Soliz on 02/1/15.
 */

    private MysqlUtil _db;
    private User user;
    private Booking[] pastBookings;
    private Booking[] FutureBookings;
    private Booking[] todaysBookings;

    // Default Constructor
    public UserBookingsProfile(){}

    // New Room with Parameters
    public UserBookingsProfile(User user)
    {
        _db = new MysqlUtil();
        this.user = user;
        this.pastBookings = _db.findPastBookings(user);
        this.todaysBookings = _db.findTodaysBookings(user);
        this.FutureBookings = _db.findFutureBookings(user);
        this.user = user;
    }

    public Booking[] getPastBookings () {
        return this.pastBookings;
    }

    public Booking[] getTodaysBookings () {
        return this.todaysBookings;
    }

    public Booking[] getFutureBookings () {
        return this.FutureBookings;
    }
}