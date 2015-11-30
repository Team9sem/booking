package com.team9.bookingsystem;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;

import java.io.File;
import java.io.FileOutputStream;

import javax.imageio.ImageIO;

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
    // room registration
    // Output confirmation or error.
    // Input  User, Building, Room, Date, Start time, End time, Purpose
    // Check  if input is valid 
    // Check  If room is available
    // Create Room object
    // SQL server
    // http://sql.smallwhitebird.com
    // user team9, password team9
    
    //STRUCTURE for Bookings
    //bid		NULL	INT
    //userId	NULL	INT
    //roomId	NULL	INT
    //bDate		NULL	date
    //bStart	NULL	time
    //bEnd		NULL	time
    public boolean BookRoom(int userId, int roomId, String bDate, String bStart, String bEnd) throws Exception
    {
      
        // we have to catch potential SQLExceptions
        try(Connection connection = getConnection()){

            System.out.println("Room Registration Connection Established");

            // statement
            Statement statement = connection.createStatement();

            String sql = "INSERT INTO Bookings " +
            			 "(userId, roomId, bDate, bStart, bEnd)" +
            			 " Values ('"+userId+ "','"+roomId+"','"+bDate+"','"+bStart+"','"+bEnd+"')";
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

    public boolean removeRoomBooking(int bID) throws Exception
    {
      
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
    
  public boolean RegisterRoom(String location, String roomSize, int hasProjector, int hasWhiteBoard, int hasCoffeeMachine) throws Exception
  {
	  	//roomID int(11)
	    //location char(30)
	    //roomSize char(30)
	    //hasProjector int
	    //hasWhiteBoard int
	    //hasCoffeMachine int
          
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

    /**
     * Returns a natural join of the tables Room and Bookings
     */

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

    /**
     * Composes a query for the getRooms method
     * Queries all unbooked rooms from the DB
     */

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

    /**
     * Gets rooms from the DB according to the SQL string from the method ComposeRoomQuery()
     * Stores them in an ArrayList of Rooms
     */
    public ArrayList<Room> getRooms(String query){

        int roomsNumber = totalNumberOfRooms();

        ArrayList<Room> bridgeRooms = new ArrayList<>();


        try(Connection connection = getConnection()){

            System.out.println("\nConnection Established\n");

            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);

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

        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    //END OF GETTING ROOMS PART

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

    /**
     *Editing and removing a user from the database
     *Created by iso on 12/11/15
     */

    public void updateUser(ArrayList<User> users){
        try(Connection connection = getConnection()){

            System.out.println("\nUser Connection Established\n");
            connection.setAutoCommit(false);
            for(User user : users) {

                Statement statement = connection.createStatement();
                statement.executeUpdate(
                        "UPDATE User SET alias = '" + user.getUserName() + "',passwd ='" + user.getPassword() +
                                "',firstname = '" + user.getFirstName() + "',lastname = '" + user.getLastName() +
                                "',pNumber = '" + user.getpNumber() + "',usertype = '" + user.getUserType() +
                                "',street = '" + user.getStreet() + "',zip = '" + user.getZip() +
                                "' WHERE userID= '" + user.getUserID() + "'"
                );
            }
            connection.commit();
        }catch(SQLException e){
            e.printStackTrace();
        }

    }

    public void deleteUser(User user){
        try(Connection connection = getConnection()){

            System.out.println("\nUser Connection Established\n");

            Statement statement = connection.createStatement();
            statement.executeUpdate(
                    "DELETE FROM User WHERE userID= '"+user.getUserID()+"'"
            );
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
    //END OF EDITING AND REMOVING USER OPERATIONS

    /**
     * Searching through users, rooms and bookings in the database
     * Created by iso on 13/11/15
     */

    public ArrayList<User> getUsers(User user){
        ArrayList<User> userArrayList = new ArrayList<>();
        try(Connection connection = getConnection()){

            System.out.println("\nUser Connection Established\n");

            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(
                    "SELECT * FROM User WHERE userID = '"+user.getUserID()+"' AND alias LIKE '%%"+
                            user.getUserName()+"%%' AND passwd LIKE '%%"+user.getPassword()+"%%' AND firstname LIKE '%%"+
                            user.getFirstName()+"%%' AND lastname LIKE '%%"+ user.getLastName()+"%%' AND pNumber LIKE '%%"
                            +user.getpNumber()+"%%' AND usertype LIKE '%%"+user.getUserType()+"%%' AND street LIKE '%%"
                            +user.getStreet()+"%%' AND zip LIKE '%%"+user.getZip()+"%%'"
            );

            while (rs.next()) {
                User tmpUser = new User();

                user.setUserID(rs.getInt("userID"));
                user.setUserName(rs.getString("alias"));
                user.setPassword(rs.getString("passwd"));
                user.setFirstName(rs.getString("firstname"));
                user.setLastName(rs.getString("lastname"));
                user.setpNumber(rs.getInt("pNumber"));
                user.setUserType(rs.getInt("usertype"));
                user.setStreet(rs.getString("street"));
                user.setZip(rs.getInt("zip"));

                userArrayList.add(tmpUser);
            }

        }catch(SQLException e){
            e.printStackTrace();
        }

        //list of columns in the USER table
        //userID, alias, passwd, firstname, lastname, pNumber, usertype, street, zip

        return userArrayList;
    }

    public ArrayList<Room> getRooms(Room room, boolean small, boolean medium, boolean large){
        ArrayList<Room> roomArrayList = new ArrayList<>();

        String query="SELECT * FROM Room WHERE roomID = '"+room.getRoomID()+"' ";

        query += " AND( ";
        if(small && medium && large) query += "roomSize = 'S' OR roomSize = 'M' OR roomSize = 'L' )";
        else if(small){
            if(medium) query += "roomSize = 'S' OR roomSize = 'M' )";
            else if(large) query += "roomSize = 'S' OR roomSize = 'L' )";
            else  query += "roomSize = 'S')";
        }
        else if(medium){
            if(large) query += "roomSize = 'M' OR roomSize = 'L')";
            else query += "roomSize = 'M' )";
        }
        else if(large) query += "roomSize = 'L')";
        else{
            query = "SELECT * FROM Room WHERE Room.roomID> 0 ";
        }

        query += " AND( ";
        if(room.getHasProjector()>0 && room.getHasWhiteboard()>0 && room.getHasCoffeeMachine()>0){
            query += "hasProjector = '1' OR hasWhiteboard = '1' OR hasCoffeeMachine = '1' )";
        }
        else if(room.getHasProjector()>0){
            if(room.getHasWhiteboard()>0) query += "hasProjector = '1' OR hasWhiteboard = '1' )";
            else if(room.getHasCoffeeMachine()>0) query += "hasProjector = '1' OR hasCoffeeMachine = '1' )";
            else  query += "hasProjector = '1')";
        }
        else if(room.getHasWhiteboard()>0){
            if(room.getHasCoffeeMachine()>0) query += "hasWhiteboard = '1' OR hasCoffeeMachine = '1')";
            else query += "hasWhiteboard = '1' )";
        }
        else if(room.getHasCoffeeMachine()>0) query += "hasCoffeeMachine = '1')";
        else{
            query = "SELECT * FROM Room WHERE Room.roomID> 0 ";
        }

        try(Connection connection = getConnection()){

            System.out.println("\nUser Connection Established\n");

            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);

            while (rs.next()) {
                Room tmpRoom = new Room();

                room.setRoomSize(rs.getString("roomSize"));
                room.setLocation(rs.getString("location"));
                room.setHasProjector(rs.getInt("hasProjector"));
                room.setHasWhiteboard(rs.getInt("hasWhiteboard"));
                room.setHasCoffeeMachine(rs.getInt("hasCoffeeMachine"));
                room.setRoomID(rs.getInt("roomID"));

                roomArrayList.add(tmpRoom);
            }

        }catch(SQLException e){
            e.printStackTrace();
        }

        return roomArrayList;
    }

    public ArrayList<Booking> getBookings(Booking booking){
        ArrayList<Booking> bookingArrayList = new ArrayList<>();

        try(Connection connection = getConnection()){

            System.out.println("\nUser Connection Established\n");

            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(
                    "SELECT * FROM Bookings WHERE bID = '"+booking.getbID()+"' AND userid LIKE '%%" +
                            booking.getuserid()+"%%' AND roomID LIKE '%%"+booking.getroomID()+"%%' AND bdate LIKE '%%"
                            +booking.getbdate()+"%%' AND bstart LIKE '%%"+booking.getbStart()+"%%' AND bEnd LIKE '%%"
                            +booking.getbEnd()+"%%'"
            );

            while (rs.next()) {
                Booking tmpBooking = new Booking();

                booking.setbID(rs.getInt("bID"));
                booking.setuserid(rs.getInt("userid"));
                booking.setroomID(rs.getInt("roomID"));
                booking.setbdate(rs.getString("bdate"));
                booking.setbStart(rs.getString("bStart"));
                booking.setbEnd(rs.getString("bEnd"));

                bookingArrayList.add(tmpBooking);
            }

        }catch(SQLException e){
            e.printStackTrace();
        }

        return bookingArrayList;
    }

    public ArrayList<Booking> getBookings(User user){
        ArrayList<Booking> bookingArrayList = new ArrayList<>();

        try(Connection connection = getConnection()){

            System.out.println("\nUser Connection Established\n");

            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(
                    "SELECT * FROM Bookings WHERE userID = "+user.getUserID() +""
            );

            while (rs.next()) {
                Booking booking = new Booking();

                booking.setbID(rs.getInt("bID"));
                booking.setuserid(rs.getInt("userid"));
                booking.setroomID(rs.getInt("roomID"));
                booking.setbdate(rs.getString("bdate"));
                booking.setbStart(rs.getString("bStart"));
                booking.setbEnd(rs.getString("bEnd"));

                bookingArrayList.add(booking);
            }

            for(Booking booking : bookingArrayList){
                booking.setUser(getUser(booking.getuserid()));
                booking.setRoom(getRoom(booking.getroomID()));
            }

        }catch(SQLException e){
            e.printStackTrace();
        }

        return bookingArrayList;
    }

    public ArrayList<Booking> getBookings(Room room){
        ArrayList<Booking> bookingArrayList = new ArrayList<>();

        try(Connection connection = getConnection()){

            System.out.println("\nUser Connection Established\n");

            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(
                    "SELECT * FROM Bookings WHERE roomID = " + room.getRoomID() + ""
            );

            while (rs.next()) {
                Booking booking = new Booking();

                booking.setbID(rs.getInt("bID"));
                booking.setuserid(rs.getInt("userid"));
                booking.setroomID(rs.getInt("roomID"));
                booking.setbdate(rs.getString("bdate"));
                booking.setbStart(rs.getString("bStart"));
                booking.setbEnd(rs.getString("bEnd"));

                bookingArrayList.add(booking);
            }


        }catch(SQLException e){
            e.printStackTrace();
        }

        for(Booking booking : bookingArrayList){
            booking.setUser(getUser(booking.getuserid()));
            booking.setRoom(getRoom(booking.getroomID()));
        }

        return bookingArrayList;
    }

    public User getUser(int userID){
        User user = new User();

        try(Connection connection = getConnection()){

            System.out.println("\nUser Connection Established\n");

            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(
                    "SELECT * FROM User WHERE userID ="+userID+""
            );

            while (rs.next()) {
                user.setUserID(rs.getInt("userID"));
                user.setUserName(rs.getString("alias"));
                user.setPassword(rs.getString("passwd"));
                user.setFirstName(rs.getString("firstname"));
                user.setLastName(rs.getString("lastname"));
                user.setpNumber(rs.getInt("pNumber"));
                user.setUserType(rs.getInt("usertype"));
                user.setStreet(rs.getString("street"));
                user.setZip(rs.getInt("zip"));
            }

        }catch(SQLException e){
            e.printStackTrace();
        }

        return user;
    }

    public Room getRoom(int roomID){

        Room room = new Room();

        try(Connection connection = getConnection()){

            System.out.println("\nUser Connection Established\n");

            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(
                    "SELECT * FROM Room WHERE roomID = "+roomID+""
            );

            while (rs.next()) {
                room.setRoomSize(rs.getString("roomSize"));
                room.setLocation(rs.getString("location"));
                room.setHasProjector(rs.getInt("hasProjector"));
                room.setHasWhiteboard(rs.getInt("hasWhiteboard"));
                room.setHasCoffeeMachine(rs.getInt("hasCoffeeMachine"));
                room.setRoomID(rs.getInt("roomID"));
            }

        }catch(SQLException e){
            e.printStackTrace();
        }

        return room;
    }
    //END OF SEARCHING USER, ROOM AND BOOKING METHODS

    /**
     * Getting a User avatar from the DB
     */

    public File getUserAvatar(User user){
        File avatar = null;
        byte[] preDoneAvatar = null;

        try(Connection connection = getConnection()){

            System.out.println("\nUser Connection Established\n");

            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(
                    "SELECT picture FROM User WHERE userID ='"+user.getUserID()+"'"
            );

            while (rs.next()) {
                preDoneAvatar = rs.getBytes("picture");
            }

        }catch(SQLException e){
            e.printStackTrace();
        }

        try(FileOutputStream outputStream = new FileOutputStream(avatar);) {
            //InputStream in = new ByteArrayInputStream(preDoneAvatar);
            //avatar = ImageIO.read(in);
            outputStream.write(preDoneAvatar);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return avatar;
    }

  }




