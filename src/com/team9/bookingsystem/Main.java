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

       // Scanner scanner = new Scanner(System.in);
       // System.out.println("Location (enter nothing if it doesn't matter): ");
       // String location = scanner.nextLine();
       // System.out.println("Enter size:(Small/Medium/Large)");
       // String size = scanner.nextLine();
       // System.out.println("Do you need a projector? 1/0 = Y/N");
       // int hasProjector = scanner.nextInt();
       // System.out.println("Do you need a whiteboard? 1/0 = Y/N");
       // int hasWhiteboard = scanner.nextInt();
       // System.out.println("Do you need a coffee machine? 1/0 = Y/N");
       // int hasCoffeeMachine = scanner.nextInt();
       // int roomID=3;
        
        String size = "s";
        String location = "";
        int hasProjector = 1;
        int hasWhiteboard = 1;
        int hasCoffeeMachine = 1;
        int roomID =3;
        //testing time
        String bookingDate = "", timeStart = "", timeEnd="";

        Room room = new Room(roomID, location, size, hasProjector, hasWhiteboard, hasCoffeeMachine);
        
        User user = new User();
        user.setUserID(3);

        try{
            // try to fetch rooms
//            Room[] rooms;
//            String query = util.composeRoomQuery(room, bookingDate, timeStart, timeEnd);
//            rooms = util.getRooms(query);
//
//            for(int k=0; k < rooms.length; k++){
//                System.out.println(rooms[k]);
//            }
        	//test 1 "2015-12-11", "09:10", "10:30" actual booking
        	//test 2 "2015-12-11", "08:10", "10:30" OK
        	//test 3 "2015-12-11", "09:20", "10:30" OK
        	//test 4 "2015-12-11", "09:20", "11:30" OK
        	//test 5 "2015-12-11", "09:20", "11:30" OK
        	//test 6 "2015-12-10", "09:20", "11:30" OK
        	//test 7 "2015-12-11", "10:20", "11:30" OK
        	//test 5 "2015-12-11", "09:05", "09:10" 
        	
        	util.BookRoomNew(user, room, "2015-12-30", "09:10", "10:30", 2);
        }catch(Exception e){
            e.printStackTrace();
        }


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