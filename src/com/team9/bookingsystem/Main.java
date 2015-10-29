package com.team9.bookingsystem;

import java.sql.SQLException;
import java.util.Scanner;

/**
 * Created by pontuspohl on 12/10/15.
 */
public class Main {

    public static void main( String[] args){
    	
    	//Variable declarations and initialization
        String bDate 		="";
        String bStart 		="";
        String bEnd			="";
        int roomID			= 0;
        String alias		="";
        String password		="";
        String firstname	="";
        String lastname		="";
        String usertype		="";
        String street		="";
        long pNumber		= 0;
        int zip				= 0;
        int selection		= 0;
        //End Variable declarations and initialization
    	
        // Mysql tool
        MysqlUtil util = new MysqlUtil();
        
        
        // get some input
        Scanner scanner = new Scanner(System.in);
        System.out.println("enter username:");
        String uname = scanner.nextLine();
        System.out.println("enter password");
        String passwd = scanner.nextLine();
        try{
    		// try to login
    		User user = util.loginAndGetUser(String.format("'%s'",uname),String.format("'%s'",passwd));
    		System.out.println(user.toString());
    	}catch(Exception e){
    		e.printStackTrace();
    	}	
        //TODO Mayra temporary fix userID has to be handled properly
        int userID = 3;
        
        while(selection !=3){
        	System.out.println("Welcome to the booking system");
        	System.out.println("Please select an option:");
        	System.out.println("1: Register User");
        	System.out.println("2: Book a room");
        	System.out.println("3: exit");
        	selection = Integer.parseInt(scanner.nextLine());
        
        	if(selection == 1) {
        		System.out.println("enter alias :");
        		alias = scanner.nextLine();
        		System.out.println("enter password:");
        		password = scanner.nextLine();
        		System.out.println("enter first name :");
        		firstname = scanner.nextLine();
        		System.out.println("enter last name :");
        		lastname = scanner.nextLine();
        		System.out.println("enter personal number YYYYMMDDXXXX :");
        		pNumber = Long.parseLong(scanner.nextLine());
        		System.out.println("enter user typer: user, admin :");
        		usertype = scanner.nextLine();
        		System.out.println("street :");
        		street = scanner.nextLine();
        		System.out.println("enter zip code xxxxxx :");
        		zip = Integer.parseInt(scanner.nextLine());
        	}
        	if(selection == 2) {
        		System.out.println("enter Date YYYY-MM-DD :");
        		bDate = scanner.nextLine();
        		System.out.println("enter start time HH:MM de:");
        		bStart = scanner.nextLine();
        		System.out.println("enter stop time HH:MM :");
        		bEnd = scanner.nextLine();
        		System.out.println("enter room ID :");
        		roomID = scanner.nextInt();
        	}
        	if(selection == 3) {
        		System.out.println("Bye!");
        	}
        	try{
        		
        		//register user
        		if(selection == 1){ 
        			util.RegisterUser(alias, password, firstname, lastname, pNumber, usertype, street, zip);
        		}
        		//register room
        		if(selection == 2){ 
        			util.RegisterRoom(userID, roomID, bDate, bStart, bEnd);
        		}

        	}catch(Exception e){
        		e.printStackTrace();
        	}
        } //end while(selection !=3)
        
        scanner.close();
        //System.out.println("Working");
        //System.out.println("Nima was here");
        //System.out.println("and here");

    }
}