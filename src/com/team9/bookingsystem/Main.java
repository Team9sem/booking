package com.team9.bookingsystem;

import java.sql.SQLException;
import java.util.Scanner;

/**
 * Created by pontuspohl on 12/10/15.
 */
public class Main {

    public static void main( String[] args){
    	
    	//Variable declarations and initialization
    	String temp				="";
        //room booking
    	String bDate 			="";
        String bStart 			="";
        String bEnd				="";
        int roomID				= 0;
        //User
        String alias			="";
        String password			="";
        String firstname		="";
        String lastname			="";
        String usertype			="";
        String street			="";
        long pNumber			= 0;
        int zip					= 0;
        int selection			= 0;
        //room
        String location 		="";
        String roomSize 		="";
        int hasWhiteBoard		= 0;
        int hasProjector		= 0;
        int hasCoffeeMachine	= 0;
        //End Variable declarations and initialization
    	
        // Mysql tool
        MysqlUtil util = new MysqlUtil();
        
        //MAYRA moved object definition to gain access to it
        User user = new User();
        
        
        // get some input
        Scanner scanner = new Scanner(System.in);
        System.out.println("enter username:");
        String uname = scanner.nextLine();
        System.out.println("enter password");
        String passwd = scanner.nextLine();
        try{
    		// try to login
    		user = util.loginAndGetUser(String.format("'%s'",uname),String.format("'%s'",passwd));
    		System.out.println(user.toString());
    	}catch(Exception e){
    		e.printStackTrace();
    	}	
        while(selection !=4){
        	//MAYRA clear any string from the previous loop (garbage in the output can cause integer exception in parseInt)
        	System.out.println("Welcome to the booking system");
        	System.out.println("Please select an option:");
        	System.out.println("1: Add a new user");
        	System.out.println("2: Add a new room");
        	System.out.println("3: Book a room");
        	System.out.println("4: exit");
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
        		System.out.println("Enter location :");
        		location = scanner.nextLine();
        		System.out.println("Enter room size S/M/L:");
        		roomSize = scanner.nextLine();
        		System.out.println("Does the room have a Projector? (Y/N):");
        		temp = scanner.nextLine();
        		if(temp.toLowerCase() == "y" || temp.toLowerCase() == "yes"){
        			hasProjector = 1;
        		}
        		else{
        			hasProjector = 0;
        		}
        			
        		System.out.println("Does the room have a Whiteboard (Y/N)? :");
        		temp = scanner.nextLine();
                if(temp.toLowerCase() == "y" || temp.toLowerCase() == "yes"){
                	hasWhiteBoard = 1;
                }
                else{
                	hasWhiteBoard = 0;
                }
        		System.out.println("Does the room have a Coffe machine? (Y/N)? :");
        		temp = scanner.nextLine();
                if(temp.toLowerCase() == "y" || temp.toLowerCase() == "yes"){
                	hasCoffeeMachine = 1;
                }
                else{
                	hasCoffeeMachine = 0;
                }
        	}
        	if(selection == 3) {
        		System.out.println("enter Date YYYY-MM-DD :");
        		bDate = scanner.nextLine();
        		System.out.println("enter start time HH:MM de:");
        		bStart = scanner.nextLine();
        		System.out.println("enter stop time HH:MM :");
        		bEnd = scanner.nextLine();   
        		System.out.println("These are the rooms that can be selected :");
        		//print available rooms so that the user can select one
        		try{
        			util.GetLocations();
        	    }catch(Exception e){
        	    	e.printStackTrace();
        	    }
        		System.out.println("Please select a room :");
        		location = scanner.nextLine();
        		try{
        			roomID = util.GetRoomID(location);
        		}catch(Exception e){
        	    	e.printStackTrace();
        	    }
        		//roomID = scanner.nextInt();
        	}
        	if(selection == 4) {
        		System.out.println("Bye!");
        	}
        	try{
        		
        		//register user
        		if(selection == 1){ 
        			util.RegisterUser(alias, password, firstname, lastname, pNumber, usertype, street, zip);
        		}
        		if(selection ==2){
        			util.RegisterRoom(location, roomSize, hasProjector, hasWhiteBoard, hasCoffeeMachine);
        		}
        		//book room
        		if(selection == 3){ 
        			util.BookRoom(user.getUserID(), roomID, bDate, bStart, bEnd);
        		}
        	

        	}catch(Exception e){
        		e.printStackTrace();
        	}
        	
        } //end while(selection !=4)
        
        
        scanner.close();
    }
}