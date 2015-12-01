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

/** 
 * ArrayList get(int index) method is used for fetching an element from the list.
* 
* 
* Created by Alemseged Setie 25/11/2015
*
* get user and return ArrayList of booking 
* 
* 
*/
b
   //package com.team9.bookingSystem;
   //import java.util.ArrayList;

   // public Booking(String 0, String 1, String 2, String 3, String 4, String 5 ){
   // public class  booking { 
   //public class ArrayList of booking {   

   //public Booking(String 0, String 1, String 2, String 3, String 4, String 5 ){

    public static void booking(String [] args){
    	
    	// create an empty array list with an initial capacity

	   
       ArrayList<Booking> booking = new ArrayList<>();
       
       Myal.add("bID");
       Myal.add("userid");
       Myal.add("roomID");
       Myal.add("bdate");
       Myal.add("bStart");
       Myal.add("bEnd");
       
    //  public Booking(String 0, String 1, String 2, String 3, String 4, String 5 )
  	//  {
  	//   bID=   0;
    //	 userid=1;
  	//	 roomid=2;
  	//	 bdate= 3;
  	//	 bStart=4;
  	//	 bEnd=  5;
  	// }
       
     //  return(Myal);

   

       System.out.println("First element of the ArrayList: "+Myal.get(0));
       System.out.println("Second element of the ArrayList: "+Myal.get(1));
       System.out.println("Third element of the ArrayList: "+Myal.get(2));
       System.out.println("Fourth element of the ArrayList: "+Myal.get(3));
       System.out.println("Fifth element of the ArrayList: "+Myal.get(4));
       System.out.println("Sixth element of the ArrayList: "+Myal.get(5));
   }
}
	
	
//,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,
	/*
	*
	*  created by Alemseged Setie
	*/
	
	
   //          New	class
	//public class Booking
//	{
//	  private String bID = new String();
//	  private String userid = new String();
//	  private String roomid = new String();
//	  private String bdate = new String();
//	  private String bStart = new String();
//	  private String bEnd = new String();
	  
	  
	  // constructors 
	//  public Booking()
	//  {
	//    bID="";
	//	 userid="";
	//	 roomid="";
	//	 bdate="";
	//	 bStart="";
	//	 bEnd="";
		 
	//  }
	  
	 // public Booking(String I, String U, String R, String D, String S, String E )
	 // {
	 //    bID=I;
	//	 userid=U;
	//	 roomid=R;
	//	 bdate=D;
	//	 bStart=S;
	//	 bEnd=E;
	 // }
	  
	//  public int getbID()
	 // {
	 //   return bID;
	 // }
	  
	 // public int getuserid()
	 // {
	 //   return userid;
	 // }
	  
	//  public int getroomid()
	//  {
	 //   return roomid;
	 // }
	 // public String getbdate()
	 // {
	//	  return bdate;
	 // }
	//  public String getbStart(){
	  
	    //  return bStart;
	//}
	//	  public String bEnd()
	 // {  
	// return bStart;	  
	 // }
	 // public String toString()
	 // {
	 //   return bID+"  "+userid+"  "+roomid+" "+bdate+" "+bStart+" "+bEnd;
	 // }
	  
	//}//end class 







