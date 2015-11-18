package com.team9.bookingsystem;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.text.*;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;

/**
 * Created by pontuspohl on 12/10/15.
 */
public class MysqlUtil {

    private final String path;
    private final String user;
    private final String pass;


    public MysqlUtil()
    {
        path = "jdbc:mysql://sql.smallwhitebird.com:3306/BookingSystem";
        user = "team9";
        pass = "team9";
    }
    // initialises the Connection.
    public Connection getConnection() throws SQLException{
        try{
            Class.forName("com.mysql.jdbc.Driver");
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }

        return DriverManager.getConnection(path,user,pass);


    }
    // accepts any query, BEWARE can damage database.
    public void runQuery(String query){
        try(Connection connection = getConnection()){

            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);
            System.out.println("worked");
            rs.close();

        }catch(SQLException e){
        e.printStackTrace();
        }

    }

    // accepts username and pass, returns user
    public User loginAndGetUser(String username,String password) throws Exception
    {
        username = "'"+username+"'";
        password = "'"+password+"'";
        // create User Object to populate with database result.
        User toReturn = new User();

        // we have to catch potential SQLExceptions
        try(Connection connection = getConnection()){



            System.out.println("Connection Established");
            String SQL="SELECT * " + "FROM User WHERE alias="+username+" AND passwd="+password+";";
            System.out.println(SQL);
                // statement
                Statement statement = connection.createStatement();

                // Resultset that holds the result of our query, important that the query only returns one user.
                ResultSet rs = statement.executeQuery(
                      "SELECT * " + "FROM User WHERE alias="+username+" AND passwd="+password+";"
                );

                
                // Nevermind this
                //JSONArray array = resultSetToJson(rs);
                //System.out.println(array.getJSONObject(0).get("passwd"));


                // debug message,
                System.out.println("Checking user");

                // This checks if we hav data.
                if (!rs.isBeforeFirst() ) {
                    System.out.println("No data");
                    statement.close();
                    rs.close();
                    connection.close();
                    throw new Exception("Wrong username or Password");
                }


                // While there are rows left
                while(rs.next()){
                    toReturn.setFirstName(rs.getString("firstname"));
                    toReturn.setLastName(rs.getString("lastname"));
                    toReturn.setPassword(rs.getString("passwd"));
                    toReturn.setUserName(rs.getString("alias"));
                    toReturn.setpNumber(rs.getLong("pnumber"));
                    toReturn.setStreet(rs.getString("street"));
                    toReturn.setZip(rs.getInt("zip"));
                    //MAYRA assign the primary key UserID to the User object so that we can use it for booking rooms
                    int userID = Integer.parseInt(rs.getString("userID"));
                    toReturn.setUserID(userID);
                    //MAYRA End
                    break;
                }

                rs.close();
                statement.close();
                connection.close();
                return toReturn;

        	}catch(SQLException e){
        		e.printStackTrace();

        	}
        	return toReturn;
    	}
        
    public void GetLocations() throws Exception
    {
    		//Created by Mayra Soliz.
    		//Method that prints all rooms 
            // we have to catch potential SQLExceptions
            try(Connection connection = getConnection()){
            	
                System.out.println("Connection Established");
                String SQL="SELECT location FROM Room;";
                System.out.println(SQL);
                    // statement
                    Statement statement = connection.createStatement();

                    // Resultset that holds the result of our query, important that the query only returns one user.
                    ResultSet rs = statement.executeQuery(SQL);
                    while(rs.next()){
                    	String location = rs.getString("location");
                    	System.out.println(location);
                    }

                    rs.close();
                    statement.close();
                    connection.close();

            }catch(SQLException e){
                e.printStackTrace();

            }

        }
        
    public Booking[] GetUserBookings(int userId) throws Exception
    {
    		//Created by Mayra Soliz.
    		Booking[] BookObj = new Booking[100];
        	String bID;
        	//Method that prints all Bookings
            // we have to catch potential SQLExceptions
            try(Connection connection = getConnection()){
            	
                System.out.println("Connection Established");
                String SQL="SELECT * FROM Bookings WHERE userid = '"+userId+"';";
                System.out.println(SQL);
                    // statement
                    Statement statement = connection.createStatement();

                    // Resultset that holds the result of our query, important that the query only returns one user.
                    ResultSet rs = statement.executeQuery(SQL);
                    int i = 0;
                    while(rs.next()){
                    	BookObj[i] = new Booking();      
                    	BookObj[i].setbID(rs.getInt("bID"));
                    	BookObj[i].setuserid(rs.getInt("userid"));
                    	BookObj[i].setroomID(rs.getInt("roomID"));
                    	BookObj[i].setbdate(rs.getString("bdate"));
                    	BookObj[i].setbStart(rs.getString("bStart"));
                    	BookObj[i].setbEnd(rs.getString("bEnd"));
                    	i++;
                 
                    }

                    rs.close();
                    statement.close();
                    connection.close();

            }catch(SQLException e){
                e.printStackTrace();

            }
            return BookObj;

        }
        
    public String GetRoomLocation(int roomID) throws Exception
    {
    		//Created by Mayra Soliz.
    		//Method get the location using the roomID
        	String toReturn = "";
            // we have to catch potential SQLExceptions
            try(Connection connection = getConnection()){
            	
                System.out.println("Connection Established");
                String SQL="SELECT location FROM Room WHERE roomID ='"+roomID+"';";
                System.out.println(SQL);
                    // statement
                    Statement statement = connection.createStatement();

                    // Resultset that holds the result of our query, important that the query only returns one user.
                    ResultSet rs = statement.executeQuery(SQL);
                    while(rs.next()){
                    	toReturn = rs.getString("location");
                    	System.out.println(toReturn);
                    }

                    rs.close();
                    statement.close();
                    connection.close();

            }catch(SQLException e){
                e.printStackTrace();

            }
            return toReturn;
        }
        
    public int GetRoomID(String location) throws Exception
    {
    		//Created by Mayra Soliz.
    		//Method that prints all rooms 
        	int roomID = 0;
            // we have to catch potential SQLExceptions
            try(Connection connection = getConnection()){
            	
                System.out.println("Connection Established");
                String SQL="SELECT roomID FROM Room WHERE location ='"+location+"';";
                System.out.println(SQL);
                    // statement
                    Statement statement = connection.createStatement();

                    // Resultset that holds the result of our query, important that the query only returns one user.
                    ResultSet rs = statement.executeQuery(SQL);
                    while(rs.next()){
                    	String tmp = rs.getString("roomID");
                    	roomID = Integer.parseInt(tmp);
                    	System.out.println(roomID);
                    }

                    rs.close();
                    statement.close();
                    connection.close();

            }catch(SQLException e){
                e.printStackTrace();

            }
            return roomID;
        }

    // TODO : Register Method for Mayra
    // TODO : create a class for room, use this class as a return type for BookRoom analog to "loginAndGetUser" 

    public Booking Booking(User userObj, Room roomObj, String bDate, String bStart, String bEnd) throws Exception
    {
    	//Created by Mayra Soliz. Modified 15 November 2015
		//Note that using strings to transfer date information can cause SQL errors if the strings are not formatted
    	//correctly.
    	
    	//TODO How should a series of bookings be handled? BookedRoom[] BookedRoom(roomObj, intbID, userId, date, start, stop, Xtimes)??
        //TODO There should only be one booking with the same Date. needs to be checked, until then use the final intbID
    	
    	int iBid = 0;
        int intbID = 0;
        int userId = userObj.getUserID();
        int roomId = roomObj.getRoomID();
        Date date;
        Date start;
        Date stop;
        
        //creating Date objects so that a BookedRoom object can be returned
        //Warnings suppressed due to Date methods being needed due to the Object definition  
        //BookedRoom(roomObj, int, int, date, date, date);
   
        //try{
        //	DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        //	date = format.parse(bDate);
        	//System.out.println(date); 
        //    @SuppressWarnings("deprecation")
        //	DateFormat formatTime = new SimpleDateFormat("H:m");
        //	start = date; 
        //	start = formatTime.parse(bStart);
        //	start.setDate(date.getDate());
        //	start.setMonth(date.getMonth());
        //	start.setYear(date.getYear()); 
        
        //	stop = date;
        //	stop = formatTime.parse(bEnd);
        //	stop.setDate(date.getDate());
        //	stop.setMonth(date.getMonth());
        //	stop.setYear(date.getYear());
        //} catch(ParseException e){   
        //        e.printStackTrace();
        //        System.out.println("Wrong date format for SQL");
        //        throw new RuntimeException(e);
        //}
        //END creating Date objects so that a BookedRoom object can be returned
        
        
        try(Connection connection = getConnection()){

            System.out.println("Room Registration Connection Established");
            
            // statement
            Statement statement = connection.createStatement();

            String sql = "INSERT INTO Bookings " +
            			 "(userId, roomId, bDate, bStart, bEnd)" +
            			 " Values ('"+userId+ "','"+roomId+"','"+bDate+"','"+bStart+"','"+bEnd+"')";
              
            System.out.println("SQL string: "+sql); 
            statement.executeUpdate(sql);
            
            String bID = "SELECT bID FROM Bookings WHERE " +
       			 "userId = '"+userId+"' AND roomId = '"+roomId+"' AND bDate = '"+bDate+"' AND bStart = '"+bStart+"' AND bEnd = '"+bEnd+"';";
           
            System.out.println("SQL string: "+bID); 
            
            ResultSet rs = statement.executeQuery(bID);
            while(rs.next()){
            	String tmp = rs.getString("bID");
            	intbID = Integer.parseInt(tmp);
            	System.out.println(intbID);
            }
            
            Booking toReturn = new Booking(intbID, userObj.getUserID(), roomObj.getRoomID(), bDate, bStart, bEnd);
            rs.close();       
            statement.close();
            connection.close();
            
            return toReturn;
            
        }catch(SQLException e){
            e.printStackTrace();
        } 
     return null;   
  } //end public BookedRoom

    public boolean removeRoomBooking(int bID) throws Exception
    {
      
    	//Created by Mayra Soliz.
    	// we have to catch potential SQLExceptions
        try(Connection connection = getConnection()){

            System.out.println("Room De-Registration Connection Established");

            // statement
            Statement statement = connection.createStatement();
            
            String sql = "DELETE FROM Bookings WHERE" +
            			 "(bID ='"+bID+"');";
            System.out.println("SQL string: "+sql); 
       
            statement.executeUpdate(sql);
           
            statement.close();
            connection.close();
            return true;
        }catch(SQLException e){
            e.printStackTrace();
        } 
     return false;   
  } //end public User BookRoom
  //userid 		int(11)
  //alias		varchar(20)
  //passwd		varchar(255)
  //firstname 	varchar(20)
  //lastname	varchar(30)
  //pNumber		bigint(20)
  //usertype	varchar(30)
  //street		varchar(30)
  //zip			int(11)
        
  public boolean RegisterUser(String alias, String passwd, String firstname, String lastname, long pNumber, String usertype, String street, int zip) throws Exception
  {
          
	  // we have to catch potential SQLExceptions
      try(Connection connection = getConnection()){
    	  
    	  
    	  System.out.println("User Registration Connection Established");

    	  // statement
    	  Statement statement = connection.createStatement();

    	  String sql = "INSERT INTO User " +
                   	"(alias, passwd, firstname, lastname, pNumber, usertype, street, zip)" +
                   	" Values ('"+alias+ "','"+passwd+"','"+firstname+"','"+lastname+"','"+pNumber+"','"+usertype+"','"+street+"','"+zip+"')";
      
    	  //System.out.println("SQL string: "+sql); 
           
    	  statement.executeUpdate(sql);
               
          statement.close();
          connection.close();
          return true;
       }catch(SQLException e){
            e.printStackTrace();
       }

    return false;

    }//end public User RegisterUser
    
  public boolean RegisterRoom(Room roomObj) throws Exception
  {
	  	//Created by Mayra Soliz.
	  	//roomID int(11)
	    //location char(30)
	    //roomSize char(30)
	    //hasProjector int
	    //hasWhiteBoard int
	    //hasCoffeMachine int
	  String location = roomObj.getLocation();
	  String roomSize= roomObj.getRoomSize();
	  int hasProjector=roomObj.getHasProjector();
	  int hasWhiteBoard=roomObj.getHasWhiteboard();
	  int hasCoffeeMachine=roomObj.getHasCoffeeMachine();
          
	  // we have to catch potential SQLExceptions
      try(Connection connection = getConnection()){
    	  
    	  
    	  System.out.println("User resister room Connection Established");

    	  // statement
    	  Statement statement = connection.createStatement();

    	  String sql = "INSERT INTO Room " +
                   	"(location, roomSize, hasProjector, hasWhiteBoard, hasCoffeeMachine)" +
                   	" Values ('"+location+ "','"+roomSize+"','"+hasProjector+"','"+hasWhiteBoard+"','"+hasCoffeeMachine+"')";
      
    	  System.out.println("SQL string: "+sql); 
           
    	  statement.executeUpdate(sql);
               
          statement.close();
          connection.close();
          return true;
       }catch(SQLException e){
            e.printStackTrace();
       }

    return false;

    }//end public User RegisterUser
  
  	public boolean editRoom(Room room) {
  	//Created by Mayra Soliz 16 Nov 2015.
  	//This method takes the data in the room object and updates the database with the data in the room object
  		
  	//SQL ROOM structure
  		  //roomID int(11)
  		  //location char(30)
  		  //roomSize char(30)
  		  //hasProjector int
  		  //hasWhiteBoard int
  		  //hasCoffeMachine int
  		
  		  int roomID = room.getRoomID();
  		  int hasCoffeeMachine = room.getHasCoffeeMachine();
    	  int hasProjector = room.getHasProjector();
    	  int hasWhiteBoard = room.getHasWhiteboard();
    	  String roomSize = room.getRoomSize();
    	  String location = room.getLocation();
  	          
  		  // we have to catch potential SQLExceptions
  	      try(Connection connection = getConnection()){
  	    	    	    	  
  	    	  System.out.println("Edit room Connection Established");

  	    	  Statement statement = connection.createStatement();
  	    	  
  	    	
  	    	  String sql = "UPDATE Room " + 
  	    			  	   "SET " +
  	                   	   "location='"+location+"',roomSize='"+roomSize+"',hasProjector='"+hasProjector+"'"
  	                   	   			   +",hasWhiteBoard='"+hasWhiteBoard+"',hasCoffeeMachine='"+hasCoffeeMachine+"' "+
  	                   	   "WHERE roomID='"+roomID+"'";
  	                   	
  	    	  System.out.println("SQL string: "+sql); 
  	           
  	    	  statement.executeUpdate(sql);
  	               
  	          statement.close();
  	          connection.close();
  	          return true;
  	       }catch(SQLException e){
  	            e.printStackTrace();
  	       }

  	    return false;
  	}
  	
  	public boolean deleteRoom(Room room) {
  	  	//Created by Mayra Soliz 16 Nov 2015.
  	  	//This method deletes the Room using the roomID.
  		//It also deletes all booking related to this roomID to retain database integrity
  		//Note that the bookings have to be deleted before the room is delete due to the
  		//foreign key restraints
  	  		
  	  	//SQL ROOM structure
  	  		  //roomID int(11)
  	  		  //location char(30)
  	  		  //roomSize char(30)
  	  		  //hasProjector int
  	  		  //hasWhiteBoard int
  	  		  //hasCoffeMachine int
  	  		
  	  		  int roomID = room.getRoomID();
  	  		  String sql = "";
    
  	  		  // we have to catch potential SQLExceptions
  	  	      try(Connection connection = getConnection()){

  	  	    	  System.out.println("Delete room Connection Established");

  	  	    	  Statement statement = connection.createStatement();
  	  	    	  
  	  	    	  //Delete all booking with this roomID from SQL Database to retain integrity
  	  	    	  sql = "DELETE FROM Bookings "+
  	  	    			"WHERE roomID='"+roomID+"';";
  	  	    	  
  	  	    	  System.out.println("SQL string: "+sql); 
  	  	    	  statement.executeUpdate(sql);
  	  	    	  
  	  	    	  //Delete room from SQL Database
  	  	    	  sql = "DELETE FROM Room "+
  	  	    			"WHERE roomID='"+roomID+"';";
  	  	    	  
  	  	    	  System.out.println("SQL string: "+sql); 
  	  	           
  	  	    	  statement.executeUpdate(sql);

  	  	          statement.close();
  	  	          connection.close();
  	  	          return true;
  	  	       }catch(SQLException e){
  	  	            e.printStackTrace();
  	  	       }

  	  	    return false;
  	  	}
  	
    public int totalNumberOfRooms(){
        int j = 0;
        try(Connection connection = getConnection()){

            System.out.println("\nConnection Established");
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(
                    "SELECT * FROM Room NATURAL JOIN Bookings;"
            );

            while (rs.next()) {
                j++;
            }
            System.out.println("Rooms total:"+j);

        }catch(SQLException e) {
            e.printStackTrace();
        }
        return j;
    }

    public String composeRoomQuery(String location,
                                   boolean isSmall,
                                   boolean isMedium,
                                   boolean isLarge,
                                   boolean hasProjector,
                                   boolean hasWhiteboard,
                                   boolean hasCoffeeMachine,
                                   String bookingDate,
                                   String timeStart,
                                   String timeEnd){

        System.out.println("inside composemethod");

        String query = "SELECT * FROM Room WHERE Room.roomID> 0 ";

        query += " AND( ";
        if(isSmall && isMedium && isLarge) query += "roomSize = 'S' OR roomSize = 'M' OR roomSize = 'L' )";
        else if(isSmall){
            if(isMedium) query += "roomSize = 'S' OR roomSize = 'M' )";
            else if(isLarge) query += "roomSize = 'S' OR roomSize = 'L' )";
            else  query += "roomSize = 'S')";
        }
        else if(isMedium){
            if(isLarge) query += "roomSize = 'M' OR roomSize = 'L')";
            else query += "roomSize = 'M' )";
        }
        else if(isLarge) query += "roomSize = 'L')";
        else{
            query = "SELECT * FROM Room WHERE Room.roomID> 0 ";
        }


        if(location != null && !location.isEmpty()){query += " AND location = " + "'"+location+"'";}
        System.out.println(query);
        if(hasProjector){query += " AND hasProjector = 1 ";}
        if(hasWhiteboard){query += " AND hasWhiteboard = 1 ";}
        if(hasCoffeeMachine){query += " AND hasCoffeeMachine = 1 ";}
        System.out.println(query);

        System.out.println(query);
        query += "AND Room.roomID NOT IN(SELECT Bookings.roomID FROM Bookings WHERE Bookings.bdate = '" + bookingDate +
                "' AND ( " +
                "('"+timeStart+"' BETWEEN Bookings.bStart AND Bookings.bEnd or '" + timeEnd + "' BETWEEN Bookings.bStart " +
                "AND Bookings.bEnd) OR ( '"+timeStart+"' < Bookings.bStart AND '" + timeEnd + "' > Bookings.bEnd)))";



        System.out.println(query);
        return query + ";";
    }

    public ArrayList<Room> getRooms(String query){ 	//or maybe it should accept a booking class, or room+time+date
        // returns string for now, just for testing


        int roomsNumber = totalNumberOfRooms();

//        BookedRoom[] bridgeRooms = new BookedRoom[roomsNumber];
        ArrayList<Room> bridgeRooms = new ArrayList<>();


        try(Connection connection = getConnection()){

            System.out.println("\nConnection Established\n");

            String locationOfRoom = "", sizeOfRoom = "";
            int projector = 0, whiteboard = 0, coffee = 0, rID = 0, bID=0, uID=0;
            Date date = null, startTime= null, endTime = null;

            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);

            ResultSetMetaData rsMetaData = rs.getMetaData();
            int columnsNumber = rsMetaData.getColumnCount();

            BookedRoom[] hlpRooms = new BookedRoom[roomsNumber];

            int k = 0;
            System.out.println(query);

            if (!rs.isBeforeFirst() ) {
                return null;

            }

            while (rs.next()) {
                Room room = new Room();
                room.setRoomSize(rs.getString("roomSize"));
                room.setLocation(rs.getString("location"));
                room.setHasProjector(rs.getInt("hasProjector"));
                room.setHasWhiteboard(rs.getInt("hasWhiteboard"));
                room.setHasCoffeeMachine(rs.getInt("hasCoffeeMachine"));
                room.setRoomID(rs.getInt("roomID"));
                bridgeRooms.add(room);
                
                rs.getInt("roomId");
            }
            return bridgeRooms;
//                for (int i = 1; i <= columnsNumber; i++) {
//
//                    String columnValue = rs.getString(i);
//                    char c;
//                    if(i==1 || i==7 || i==8) {
//                        if(i==1){ rID = Integer.parseInt(columnValue); }
//                        if(i==7){ bID = Integer.parseInt(columnValue); }
//                        if(i==8){ uID = Integer.parseInt(columnValue); }
//                    }
//                    if(i==2) {
//                        locationOfRoom = columnValue;
//                    }
//                    if(i==3) {
//                        sizeOfRoom = columnValue;
//                    }
//                    if(i==4 || i == 5 || i == 6){
//                        c = columnValue.charAt(0);
//                        if(i==4) { projector = Character.getNumericValue(c); }
//                        if(i==5) { whiteboard = Character.getNumericValue(c); }
//                        if(i==6) { coffee = Character.getNumericValue(c); }
//                    }
//                    if(i==9){  }
//                    if(i==10){  }
//                    if(i==11){  }


//                Room tmpRoom = new Room(rID, locationOfRoom, sizeOfRoom, projector, whiteboard, coffee);
//                hlpRooms[k] = new BookedRoom(tmpRoom, bID, uID, date, startTime, endTime);
//                k++;
//                availableRoomsNo++;
//
//            //testing purposes - counts results
//            System.out.println("Rooms total:"+availableRoomsNo);
//
//            bridgeRooms = hlpRooms;
//
//            rs.close();
//            statement.close();
//            connection.close();

        }catch(SQLException e){
            e.printStackTrace();
        }

//        Room[] rooms = new Room[availableRoomsNo];
//
//        for(int i=0; i<availableRoomsNo; i++){
//            rooms[i] = bridgeRooms[i];
//        }
//
//        return rooms;
        return null;
    }



    // prototype using HashMap
    public HashMap getAllUsers(){
        HashMap<Integer,User> users = new HashMap<>();

        try{

            Connection connection = DriverManager.getConnection(path,user,pass);
            connection.setAutoCommit(false);

            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("Select * FROM User;");
            while(rs.next()){

            }

        }catch(Exception e){
            e.printStackTrace();
        }

    return null;
    }

    //prototype using JSONArray
    private JSONArray resultSetToJson(ResultSet rs) throws SQLException,JSONException {

        JSONArray jsonArray = new JSONArray();
        ResultSetMetaData rsmd = rs.getMetaData();

        while (rs.next())
        {
            JSONObject row = new JSONObject();

            for (int i = 1; i < rsmd.getColumnCount() + 1; i++)
            {
                String colName = rsmd.getColumnName(i);

                if(rsmd.getColumnType(i)==java.sql.Types.ARRAY){
                    row.put(colName, rs.getArray(colName));
                }
                else if(rsmd.getColumnType(i)==java.sql.Types.BIGINT){
                    row.put(colName, rs.getInt(colName));
                }
                else if(rsmd.getColumnType(i)==java.sql.Types.BOOLEAN){
                    row.put(colName, rs.getBoolean(colName));
                }
                else if(rsmd.getColumnType(i)==java.sql.Types.BLOB){
                    row.put(colName, rs.getBlob(colName));
                }
                else if(rsmd.getColumnType(i)==java.sql.Types.DOUBLE){
                    row.put(colName, rs.getDouble(colName));
                }
                else if(rsmd.getColumnType(i)==java.sql.Types.FLOAT){
                    row.put(colName, rs.getFloat(colName));
                }
                else if(rsmd.getColumnType(i)==java.sql.Types.INTEGER){
                    row.put(colName, rs.getInt(colName));
                }
                else if(rsmd.getColumnType(i)==java.sql.Types.NVARCHAR){
                    row.put(colName, rs.getNString(colName));
                }
                else if(rsmd.getColumnType(i)==java.sql.Types.VARCHAR){
                    row.put(colName, rs.getString(colName));
                }
                else if(rsmd.getColumnType(i)==java.sql.Types.TINYINT){
                    row.put(colName, rs.getInt(colName));
                }
                else if(rsmd.getColumnType(i)==java.sql.Types.SMALLINT){
                    row.put(colName, rs.getInt(colName));
                }
                else if(rsmd.getColumnType(i)==java.sql.Types.DATE){
                    row.put(colName, rs.getDate(colName));
                }
                else if(rsmd.getColumnType(i)==java.sql.Types.TIMESTAMP){
                    row.put(colName, rs.getTimestamp(colName));
                }
                else{
                    row.put(colName, rs.getObject(colName));
                }


            }

            jsonArray.put(row);
        }
        System.out.println(jsonArray.toString());
        return jsonArray;
    }











    }




