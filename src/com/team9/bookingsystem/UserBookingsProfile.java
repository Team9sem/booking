package com.team9.bookingsystem;

public class UserBookingsProfile extends Booking{

/**
 * Created by Mayra Soliz on 04/1/15.
 */

    private MysqlUtil _db;
    private User user;
    /*private Booking[] pastBookings;
    private Booking[] FutureBookings;
    private Booking[] todaysBookings;*/
    Room room;
    String bdate;
    String bStart;
    String bEnd;
    
    

    // Default Constructor
    public UserBookingsProfile(String bdate, String bStart, String bEnd, Room room)
    {
        _db = new MysqlUtil();
        
        this.bdate = bdate;
        this.bStart  = bStart;
        this.bEnd = bEnd;
        this.room = room;
    }

    public Room getRoom() {
		return room;
	}



	public String getBdate() {
		return bdate;
	}



	public String getbStart() {
		return bStart;
	}



	public String getbEnd() {
		return bEnd;
	}



    // New Room with Parameters
    //methods to get past, todays and future bookings 
    // 
   /* public UserBookingsProfile(User user)
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
    }*/
}