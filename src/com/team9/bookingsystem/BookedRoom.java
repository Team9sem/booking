package com.team9.bookingsystem;

import java.util.Date;

/**
 * Created by Iso on 31-Oct-15.
 */
public class BookedRoom {


    private int bookID;
    private int userID;
    private int roomId;


    private String date;
    private String startTime;
    private String endTime;

    public BookedRoom (){
    }

    public BookedRoom(Room room,
                      int bookID,
                      int userID,
                      String date,
                      String startTime,
                      String endTime){


        this.bookID = bookID;
        this.userID = userID;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public BookedRoom(BookedRoom bookedRoom){


        this.bookID = bookedRoom.bookID;
        this.userID = bookedRoom.userID;
        this.date = bookedRoom.date;
        this.startTime = bookedRoom.startTime;
        this.endTime = bookedRoom.endTime;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }



    public int getBookID(){ return bookID; }
    public int getUserID(){ return userID; }
    public String getDate(){ return date; }
    public String getStartTime(){ return startTime; }
    public String getEndTime(){ return endTime; }



    public void setBookID(int bookID) {
        this.bookID = bookID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }


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


        toReturn += String.format("\n// BookingID - %s \n", getBookID());
        toReturn += String.format("// UserID - %s \n", getUserID());
        toReturn += String.format("// RoomID - %s \n", getRoomId());
        toReturn += String.format("// Date - %s \n",getDate());
        toReturn += String.format("// StartTime - %s \n",getStartTime());
        toReturn += String.format("// EndTime - %s \n",getEndTime());
        return toReturn;
    }

}