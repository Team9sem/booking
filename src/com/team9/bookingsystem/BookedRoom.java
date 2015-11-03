package com.team9.bookingsystem;

import java.util.Date;

/**
 * Created by Iso on 31-Oct-15.
 */
public class BookedRoom extends Room{
    private Room room;
    private int bookID;
    private int userID;
    private Date date;
    private Date startTime;
    private Date endTime;

    public BookedRoom (){
    }

    public BookedRoom(Room room,
                      int bookID,
                      int userID,
                      Date date,
                      Date startTime,
                      Date endTime){

        this.room = room;
        this.bookID = bookID;
        this.userID = userID;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public BookedRoom(BookedRoom bookedRoom){

        this.room = bookedRoom.room;
        this.bookID = bookedRoom.bookID;
        this.userID = bookedRoom.userID;
        this.date = bookedRoom.date;
        this.startTime = bookedRoom.startTime;
        this.endTime = bookedRoom.endTime;
    }

    public Room getRoom(){ return room; }
    public int getBookID(){ return bookID; }
    public int getUserID(){ return userID; }
    public Date getDate(){ return date; }
    public Date getStartTime(){ return startTime; }
    public Date getEndTime(){ return endTime; }


    public static boolean isValidInput(int room,
                                       int bookID,
                                       int userID,
                                       Date date,
                                       Date startTime,
                                       Date endTime)
    {
        return true;
    }

    public String toString(){
        String toReturn = "";

        toReturn += String.format("//- %s \n", getRoom());
        toReturn += String.format("//- %s \n", getBookID());
        toReturn += String.format("//- %s \n", getUserID());
        toReturn += String.format("//- %s \n",getDate());
        toReturn += String.format("//- %s \n",getStartTime());
        toReturn += String.format("//- %s \n",getEndTime());
        return toReturn;
    }

}