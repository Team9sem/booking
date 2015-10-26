package com.team9.bookingsystem;

import java.sql.SQLException;
import java.util.Scanner;

/**
 * Created by pontuspohl on 12/10/15.
 */
public class Main {

    public static void main( String[] args){
        // Mysql tool
        MysqlUtil util = new MysqlUtil();

        // get some input
        Scanner scanner = new Scanner(System.in);
        System.out.println("enter username:");
        String uname = scanner.nextLine();
        System.out.println("enter password");
        String passwd = scanner.nextLine();
        //MAYRA
        //TODO temporary fix userID has to be handled properly
        int userID = 3;
        
        System.out.println("enter Date YYYY-MM-DD:");
        String bDate = scanner.nextLine();
        System.out.println("enter start time HH:MM:");
        String bStart = scanner.nextLine();
        System.out.println("enter stop time HH:MM:");
        String bEnd = scanner.nextLine();
        System.out.println("enter room ID:");
        int roomID = scanner.nextInt();
        // END MAYRA
        try{
            // try to login
            User user = util.loginAndGetUser(String.format("'%s'",uname),String.format("'%s'",passwd));
            
            //MAYRA
            //DATE YYYY-MM-DD
            util.RegisterRoom(userID, roomID, bDate, bStart, bEnd);
            //MAYRA
            
            System.out.println(user.toString());
        }catch(Exception e){
            e.printStackTrace();
        }


        System.out.println("Working");
        System.out.println("Nima was here");
        System.out.println("and here");

    }
}