package com.team9.bookingsystem;

/**
 * Created by Mayra Soliz on 01/11/15.
 */
public class Room {
	//roomID int(11)
    //location char(30)
    //roomSize char(30)
    //hasProjector bool
    //hasWhiteBoard bool
    //hasCoffeMachine bool

    private MysqlUtil _db;
    private int roomID;
    private String location; 
    private String roomSize;
    private boolean hasProjector;
    private boolean hasWhiteBoard;
    private boolean hasCoffeMachine;

    // Default Constructor
    public Room(){}

    // New Room with Parameters
    public Room(String location, String roomSize, boolean hasProjector, boolean hasWhiteBoard, boolean hasCoffeMachine)
    {
        _db = new MysqlUtil();
        this.location = location;
        this.roomSize  = roomSize;
        this.hasProjector  = hasProjector;
        this.hasWhiteBoard = hasWhiteBoard;
        this.hasCoffeMachine  = hasCoffeMachine;
    }

    // Copy Constructor
    
   
   // public Room(Room user)
   // {
   //     _db = new MysqlUtil();
   //
   //     this.userName  = user.userName;
   //     this.password  = user.password;
   //     this.firstName = user.firstName;
   //     this.lastName  = user.lastName;
   //     this.street    = user.street;
   //     this.zip       = user.zip;
   // }
    public int getRoomID() {
    	return roomID;
    }
    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getRoomSize () {
        return roomSize;
    }

    public void setRoomSize(String roomSize) {
    	this.roomSize =  roomSize;
    }

    public Boolean getHasProjectore() {
        return hasProjector;
    }

    public void setHasProjector(Boolean hasProjector) {
        this.hasProjector = hasProjector;
    }

    public Boolean getHasWhiteBoard() {
        return hasWhiteBoard;
    }

    public void setHasWhiteBoard(boolean hasWhiteBoard) {
        this.hasWhiteBoard = hasWhiteBoard;
    }
    public Boolean getHasCoffeMachine(){
    	return this.hasCoffeMachine;
    }
    public void setHasCoffeMachine(Boolean hasCoffeMachine) {
        this.hasCoffeMachine = hasCoffeMachine;
    }

    public static boolean isValidInput(String location,
                                      String roomSize,
                                      Boolean hasProjector,
                                      Boolean hasWhiteBoard,
                                      Boolean hasCoffeMachine)
    {
        if(location.length() > 30) {
        	return false;
        }
        if(roomSize.length() > 30) {
        	return false;
        }
        if(hasProjector.equals(null)) {
        	return false;
        }
        if(hasWhiteBoard.equals(null)) {
        	return false;
        }
        if(hasCoffeMachine.equals(null)) {
        	return false;
        }
    return true;    
    }

    public String toString(){
        String toReturn = "";
        toReturn += String.format("\n//- %s \n",getLocation());
        toReturn += String.format("//- %s \n",getRoomSize());
        toReturn += String.format("//- %s \n",getHasProjectore());
        toReturn += String.format("//- %s \n",getHasWhiteBoard());
        toReturn += String.format("//- %s \n",getHasCoffeMachine());
        return toReturn;
    }


}
