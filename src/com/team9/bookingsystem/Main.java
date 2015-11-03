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

        Scanner scanner = new Scanner(System.in);
        System.out.println("Location (enter nothing if it doesn't matter): ");
        String location = scanner.nextLine();
        System.out.println("Enter size:(Small/Medium/Large)");
        String size = scanner.nextLine();
        System.out.println("Do you need a projector? 1/0 = Y/N");
        int hasProjector = scanner.nextInt();
        System.out.println("Do you need a whiteboard? 1/0 = Y/N");
        int hasWhiteboard = scanner.nextInt();
        System.out.println("Do you need a coffee machine? 1/0 = Y/N");
        int hasCoffeeMachine = scanner.nextInt();
        int roomID=0;

        //testing time
        String bookingDate = "", timeStart = "", timeEnd="";

        Room room = new Room(roomID, location, size, hasProjector, hasWhiteboard, hasCoffeeMachine);

        try{
            // try to fetch rooms
//            Room[] rooms;
//            String query = util.composeRoomQuery(room, bookingDate, timeStart, timeEnd);
//            rooms = util.getRooms(query);
//
//            for(int k=0; k < rooms.length; k++){
//                System.out.println(rooms[k]);
//            }

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