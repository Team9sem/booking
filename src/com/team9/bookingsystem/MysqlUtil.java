package com.team9.bookingsystem;

import java.sql.*;
import java.util.HashMap;
import java.util.Iterator;

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

    // TODO : Register Method for Mayra
    //TODO: Find rooms by Filip

    public String getRooms(Room room){ 	//or maybe it should accept a booking class, or room+time+date
										//returns string for now, just for testing

        String location = room.getLocation();
        String roomSize = room.getRoomSize();
        //int roomID = room.getRoomID();
        int hasProjector = room.getHasProjector();
        int hasWhiteboard = room.getHasWhiteboard();
        int hasCoffeeMachine = room.getHasCoffeeMachine();
        String query = "SELECT * FROM Room WHERE roomID > 0 ";

        if(location != null && !location.isEmpty()){query += " AND location = " + location;}
        if(roomSize != null && !roomSize.isEmpty()){query += " AND roomSize = " + roomSize;}
        if(hasProjector > 0){query += " AND hasProjector = 1 ";}
        if(hasWhiteboard > 0){query += " AND hasWhiteboard = 1 ";}
        if(hasCoffeeMachine > 0){query += " AND hasCoffeeMachine = 1 ";}

        String toReturn="Nothing to return";

        try(Connection connection = getConnection()){

            System.out.println("Connection Established");

            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(
                query += ";"
            );
            ResultSetMetaData rsMetaData = rs.getMetaData();
            int columnsNumber = rsMetaData.getColumnCount();

            while (rs.next()) {
                for (int i = 1; i <= columnsNumber; i++) {
                    if (i > 1) System.out.print(",  ");
                    String columnValue = rs.getString(i);
                    System.out.print(columnValue + " " + rsMetaData.getColumnName(i));
                }
            }

            rs.close();
            statement.close();
            connection.close();
            return toReturn;

        }catch(SQLException e) {
            e.printStackTrace();
        }
        return toReturn;
        //Room[] rooms = new Room[5];   //
        //return rooms;                 //when i succeed in exporting it to a class file
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




