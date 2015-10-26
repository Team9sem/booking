package com.team9.bookingsystem;

import java.sql.SQLException;
import java.util.Scanner;

/**
 * Created by pontuspohl on 12/10/15
 * Edited by iso.f on 28/10/15
 */
public class Main {

    public static void main( String[] args){
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

        Room room = new Room(location, size, roomID, hasProjector, hasWhiteboard, hasCoffeeMachine);
        try{
            // try to fetch rooms
            String str = util.getRooms(room);
            System.out.println(str);
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