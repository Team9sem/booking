package com.team9.bookingsystem;

import java.awt.image.BufferedImage;
import java.io.File;

/**
 * Created by pontuspohl on 12/10/15.
 */
public class User {

    private MysqlUtil _db;

    //Mayra added primary key userID. I need this for booking rooms
    private int	   userID;
    //Mayra END added primary key userID. I need this for booking rooms
    private long   pNumber;
    private String userName;
    private String password;
    private String firstName;
    private String lastName;
    private int userType;
    private String street;
    private int    zip;
    private BufferedImage avatar;

    // Default Constructor
    public User(){}

    // New User with Parameters
    public User(String userName,
                String password,
                String firstName,
                String lastName,
                int userType,
                String street,
                int zip,
                BufferedImage avatar) //Or type File - read up on that
    {
        _db = new MysqlUtil();
        this.userName  = userName;
        this.password  = password;
        this.firstName = firstName;
        this.lastName  = lastName;
        this.userType  = userType;
        this.street    = street;
        this.zip       = zip;
        this.avatar    = avatar;
    }

    // Copy Constructor
    public User(User user)
    {
        _db = new MysqlUtil();

        this.userName  = user.userName;
        this.password  = user.password;
        this.firstName = user.firstName;
        this.lastName  = user.lastName;
        this.userType  = user.userType;
        this.street    = user.street;
        this.zip       = user.zip;
        this.avatar    = user.avatar;
    }
    public void setUserID(int userID) {   	
    	this.userID = userID;
    }
    
    public int getUserID(){
    	return userID;
    }

    public long getpNumber() {
        return pNumber;
    }

    public void setpNumber(long pNumber) {
        this.pNumber = pNumber;
    }

    public String getUserName () {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getUserType() { return userType; }

    public String getStreet() { return street; }

    public int getZip() { return zip; }

    public BufferedImage getAvatar(){ return this.avatar; }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public void setZip(int zip) {
        this.zip = zip;
    }

    public void setAvatar(BufferedImage avatar){ this.avatar = avatar; }


    public static boolean isValidInput(String userName,
                                      String password,
                                      String firstName,
                                      String lastName,
                                      int userType,
                                      String street,
                                      int zip,
                                      BufferedImage avatar)
    {
        if(userName.length() > 20) return false;
        for(char c:firstName.toCharArray()){
            if (Character.isDigit(c)) return false;
        }
        for(char c:lastName.toCharArray()){
            if (Character.isDigit(c)) return false;
        }
        return true;
    }

    public String toString(){
        String toReturn = "";
        toReturn += String.format("\n//- %s \n",getFirstName());
        toReturn += String.format("//- %s \n",getLastName());
        toReturn += String.format("//- %s \n",getUserName());
        toReturn += String.format("//- %s \n",getPassword());
        toReturn += String.format("//- %s \n",getpNumber());
        toReturn += String.format("//- %s \n",getUserType());
        toReturn += String.format("//- %s \n",getStreet());
        toReturn += String.format("//- %s \n",getZip());
        toReturn += String.format("//- %s \n",getUserID());
        return toReturn;
    }


}
