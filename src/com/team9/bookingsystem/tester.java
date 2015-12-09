package com.team9.bookingsystem;

import java.util.ArrayList;

/**
 * Created by pontuspohl on 09/12/15.
 */
public class tester {


    public static void main(String[] args)
    {

        MysqlUtil util = new MysqlUtil();

        Room room = new Room(3,"empty","L",1,1,1);
        ArrayList<Booking> bookings = util.getBookings(room);
        System.out.println("got the result\n\n\n");
        System.out.println(bookings.toString());
    }

}
