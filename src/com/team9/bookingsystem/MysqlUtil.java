package com.team9.bookingsystem;

import java.sql.*;
import java.sql.Date;
import java.util.*;

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

                // statement
                Statement statement = connection.createStatement();

                // Resultset that holds the result of our query, important that the query only returns one user.
                ResultSet rs = statement.executeQuery(
                        "SELECT * " + "FROM User WHERE alias="+username+"AND passwd="+password+";"
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

                    break;
                }

                rs.close();
                statement.close();
                connection.close();
                return toReturn;




        }catch(SQLException e){
            e.printStackTrace();

        }



    return null;

    }

    //TODO: Find rooms by Filip

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

    public String composeRoomQuery(Room room,
                                   String bookingDate,
                                   String timeStart,
                                   String timeEnd){
        String query = "SELECT Room.roomID,Room.location ,Room.roomSize,Room.hasProjector,Room.hasWhiteboard," +
                "Room.hasCoffeeMachine FROM Room JOIN Bookings ON Room.roomID = Bookings.roomID" +
                " WHERE Room.roomID> 0 "; //NATURAL JOIN Bookings

        String location = room.getLocation();
        String roomSize = room.getRoomSize();
        int hasProjector = room.getHasProjector();
        int hasWhiteboard = room.getHasWhiteboard();
        int hasCoffeeMachine = room.getHasCoffeeMachine();

        if(location != null && !location.isEmpty()){query += " AND location = " + "'"+location+"'";}
        if(roomSize != null && !roomSize.isEmpty()){query += " AND roomSize = " + "'"+roomSize+"'";}
        if(hasProjector > 0){query += " AND hasProjector = 1 ";}
        if(hasWhiteboard > 0){query += " AND hasWhiteboard = 1 ";}
        if(hasCoffeeMachine > 0){query += " AND hasCoffeeMachine = 1 ";}
        if(timeStart != null){query += " AND Bookings.bStart NOT BETWEEN " + "'" + timeStart + "'"
                + " AND " + "'" + timeEnd + "'"; }
        if(timeEnd != null && !location.isEmpty()){query += " AND Bookings.bdate = " + "'" + bookingDate + "'";}
        if(bookingDate != null && !location.isEmpty()){}

        return query + ";";
    }

    public Room[] getRooms(String query){ 	//or maybe it should accept a booking class, or room+time+date
                                            // returns string for now, just for testing


        int roomsNumber = totalNumberOfRooms();
        int availableRoomsNo = 0;
        BookedRoom[] bridgeRooms = new BookedRoom[roomsNumber];

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
            while (rs.next()) {

                for (int i = 1; i <= columnsNumber; i++) {

                    String columnValue = rs.getString(i);
                    char c;
                    if(i==1 || i==7 || i==8) {
                        if(i==1){ rID = Integer.parseInt(columnValue); }
                        if(i==7){ bID = Integer.parseInt(columnValue); }
                        if(i==8){ uID = Integer.parseInt(columnValue); }
                    }
                    if(i==2) {
                        locationOfRoom = columnValue;
                    }
                    if(i==3) {
                        sizeOfRoom = columnValue;
                    }
                    if(i==4 || i == 5 || i == 6){
                        c = columnValue.charAt(0);
                        if(i==4) { projector = Character.getNumericValue(c); }
                        if(i==5) { whiteboard = Character.getNumericValue(c); }
                        if(i==6) { coffee = Character.getNumericValue(c); }
                    }
                    if(i==9){  }
                    if(i==10){  }
                    if(i==11){  }

                }
                Room tmpRoom = new Room(rID, locationOfRoom, sizeOfRoom, projector, whiteboard, coffee);
                hlpRooms[k] = new BookedRoom(tmpRoom, bID, uID, date, startTime, endTime);
                k++;
                availableRoomsNo++;
            }
            //testing purposes - counts results
            System.out.println("Rooms total:"+availableRoomsNo);

            bridgeRooms = hlpRooms;

            rs.close();
            statement.close();
            connection.close();

        }catch(SQLException e){
         e.printStackTrace();
        }

        Room[] rooms = new Room[availableRoomsNo];

        for(int i=0; i<availableRoomsNo; i++){
            rooms[i] = bridgeRooms[i];
        }

        return rooms;
    }
    //
    //END OF ROOMS HANDLING-----------------------------------------------------------------------------
    //


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




