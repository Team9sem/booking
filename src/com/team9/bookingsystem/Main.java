package com.team9.bookingsystem;

import java.sql.SQLException;
import java.util.Scanner;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.sql.Time;

/**
 * Created by pontuspohl on 12/10/15
 * Edited by iso.f on 28/10/15
 */
public class Main {

	public static void main(String[] args) {
        // Mysql tool
        MysqlUtil util = new MysqlUtil();

        // get some input
//        Scanner scanner = new Scanner(System.in);
//        System.out.println("enter username:");
//        String uname = scanner.nextLine();
//        System.out.println("enter password");
//        String passwd = scanner.nextLine();

        //Scanner scanner = new Scanner(System.in);
        //System.out.println("Location (enter nothing if it doesn't matter): ");
        //String location = scanner.nextLine();
        //System.out.println("Enter size:(Small/Medium/Large)");
        //String size = scanner.nextLine();
        //System.out.println("Do you need a projector? 1/0 = Y/N");
        //int hasProjector = scanner.nextInt();
        //System.out.println("Do you need a whiteboard? 1/0 = Y/N");
        //int hasWhiteboard = scanner.nextInt();
        //System.out.println("Do you need a coffee machine? 1/0 = Y/N");
        //int hasCoffeeMachine = scanner.nextInt();
        //int roomID=0;

        //Mayra test
      //Booking temp = new Booking();
    	//temp = util.BookRoom(3, 1, "2015-10-20", "09:10", "9:15");
    	//System.out.println(temp.toString());
        try{
    	User userObj 	= new User("MS", "test", "Mayra", "Soliz", "testStreet", 12345);
    	long pnumber = 821010123; 
    	userObj.setpNumber(pnumber);
    	userObj.setUserID(3);
    	
    	Room roomObj 	= new Room(18, "AddTestRoom", "L", 0, 1, 0);
    
    	
    	//util.editRoom(roomObj);
    	//util.deleteRoom(roomObj);
    	//BookedRoom bRoom = new BookedRoom();
    	Booking book = new Booking();
    	//book = util.Booking(userObj, roomObj, "2015-10-11", "9:15", "11:15");
    	System.out.println(book.toString());
    	//util.RegisterRoom(roomObj);
    	util.addRoom(roomObj);
    	//BookedRoom bRoom = util.BookRoom(userObj, roomObj, bDateIn, bStartIn, bEndIn);
    	//BookedRoom bRoom = util.Booking(userObj, roomObj, "2015-10-21", "9:15", "10:30");
    	
    	//System.out.println(bRoom.toString());
    }catch(Exception e){
        e.printStackTrace();
    }


        //Mayra test
        //testing time
       // String bookingDate = "", timeStart = "", timeEnd="";

       // Room room = new Room(roomID, location, size, hasProjector, hasWhiteboard, hasCoffeeMachine);

       // try{
            // try to fetch rooms
//            Room[] rooms;
//            String query = util.composeRoomQuery(room, bookingDate, timeStart, timeEnd);
//            rooms = util.getRooms(query);
//
//            for(int k=0; k < rooms.length; k++){
//                System.out.println(rooms[k]);
//            }

       // }catch(Exception e){
       //     e.printStackTrace();
       // }


//        try{
//            // try to login
//            User user = util.loginAndGetUser(String.format("'%s'",uname),String.format("'%s'",passwd));
//            System.out.println(user.toString());
//        }catch(Exception e){
//            e.printStackTrace();
//        }


        System.out.println("Working");
    }
}