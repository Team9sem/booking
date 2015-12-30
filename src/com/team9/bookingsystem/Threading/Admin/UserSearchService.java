package com.team9.bookingsystem.Threading.Admin;

import com.team9.bookingsystem.MysqlUtil;
import com.team9.bookingsystem.User;
import javafx.concurrent.Task;

import java.util.ArrayList;

/**
 * By Pontus
 *
 *
 * All content by Pontus
 *
 *
 *
 */

public class UserSearchService extends AdminSearchService {




    /**
     * variables needed to Perform Database Operation
     */
    private Task task;
    private User user;
//    private String userSearchText;
//    private String roomSearchText;
//    private String toTime;
//    private String size;
    
//	variables for user searching
    private int userID;
    private String userName;
    private String firstName;
    private String lastName;
    private String userType;
    private String street;
    private long pNumber;
    private int zipCode;

    
   


    /**
     * Class Constructor
     *
     * Initializes Instance variable task
     *
     * @param task custom Task object
     * @see Task
     */
    public UserSearchService(Task task){
        super(task);
        System.out.println(this.task);
    }

    /**
     * Class Constructor
     * @param user
     */
        
    public UserSearchService(User user)
    {
        super();
        this.user = user;
    }

    /**
     * Overriden protected Method from com.team9.bookingsystem.Threading.ThreadService
     * @return Returns a Task Object which returns the Rooms meeting search criteria as ArrayList<Room>
     * @see Object
     */
    protected Task<Object> createTask(){
        return new Task<Object>() {
            @Override
            protected Object call() throws Exception {
                MysqlUtil util = new MysqlUtil();
                System.out.println("Searching for user:");
                System.out.println(user.toString());
                ArrayList<User> users = util.getUsers(user);
                return users;

            }
        };
    }
}




