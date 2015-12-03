package com.team9.bookingsystem;

import java.util.ArrayList;

/**
 * Created by Mayra Soliz on 01/11/15.
 */
public class Booking {
	//bID
    //userid
    //roomID
    //bdate
    //bStart
    //bEnd

    private MysqlUtil _db;
    private int bID;
    private int userid;
    private int roomID;
    private String bdate;
    private String bStart;
    private String bEnd;
    private User user;
    private Room room;

    // Default Constructor
    public Booking(){}

    // New Room with Parameters
    public Booking(int bID, int userid, int roomID, String bdate, String bStart, String bEnd)
    {
        _db = new MysqlUtil();
        this.bID = bID;
        this.userid  = userid;
        this.roomID  = roomID;
        this.bdate = bdate;
        this.bStart  = bStart;
        this.bEnd = bEnd;
    }

    public Booking(int bID, int userid, int roomID, String bdate, String bStart, String bEnd, User user, Room room)
    {
        _db = new MysqlUtil();
        this.bID = bID;
        this.userid  = userid;
        this.roomID  = roomID;
        this.bdate = bdate;
        this.bStart  = bStart;
        this.bEnd = bEnd;
        this.user = user;
        this.room = room;
    }

    public int getbID() {
    	return bID;
    }
    public void setbID(int bID) {
        this.bID = bID;
    }
    public int getuserid() {
        return userid;
    }
    
    public void setuserid(int userid) {
        this.userid = userid;
    }

    public int getroomID () {
        return roomID;
    }

    public void setroomID(int roomID) {
        this.roomID = roomID;
    }
    public String getbdate () {
        return this.bdate;
    }
    
    public void setbdate(String bdate) {
    	this.bdate =  bdate;
    }

    public String getbStart() {
        return this.bStart;
    }

    public void setbStart(String bStart) {
        this.bStart = bStart;
    }

    public String getbEnd() {
        return this.bEnd;
    }
    
    public void setbEnd(String bEnd) {
        this.bEnd = bEnd;
    }

    public User getUser(){ return this.user; }

    public void setUser(User user){ this.user = user; }

    public Room getRoom(){ return this.room; }

    public void setRoom(Room room){ this.room = room; }
    

    public static boolean isValidInput(int bID, int userid, String roomID, String bdate, String bStart, String bEnd)
    {
        //TODO check for USER and ROOM ID
    	if(roomID.length() > 30) {
        	return false;
        }
        if(bdate.length() > 30) {
        	return false;
        }
        if(bStart.length() > 30) {
        	return false;
        }
        if(bEnd.length() > 30) {
        	return false;
        }
        
    return true;    
    }

    public String toString(){
    	String location="";
    	MysqlUtil BookingDB = new MysqlUtil();
    	try{
    		location = BookingDB.GetRoomLocation(getroomID());
    	}catch(Exception e){
			e.printStackTrace();
		}
        String toReturn = "";
        toReturn += String.format("Booking ID- %s \n",getbID());
        toReturn += String.format("Location- %s \n", location);
        toReturn += String.format("Date- %s \n",getbdate());
        toReturn += String.format("Start- %s \n",getbStart());
        toReturn += String.format("End- %s \n",getbEnd());
        
        return toReturn;
    }


}










