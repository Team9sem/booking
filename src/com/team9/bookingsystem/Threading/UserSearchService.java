package com.team9.bookingsystem.Threading;

import java.util.ArrayList;

import com.team9.bookingsystem.MysqlUtil;
import com.team9.bookingsystem.Room;

import javafx.concurrent.Task;

public class UserSearchService extends ThreadService {
	


    /**
     * variables needed to Perform Database Operation
     */
    private Task task;
//    private String userSearchText;
//    private String roomSearchText;
//    private String toTime;
//    private String size;
    
//	variables for user searching
    String userID;
    String userName;
    String name;
    String userType;
    String pNumber;
    String zipCode;

//  variables for room searching  
    String roomID;
    String roomSize;
    boolean hasWhiteboard;
    boolean hasProjector;
    boolean hasCoffeMachine;
    
   


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
     *
     * @param date Desired date to book a room on
     * @param fromTime Desired startTime
     * @param toTime Desired EndTime
     * @param size Desired Size property
     * @see String
     */
        
    public UserSearchService(String userID,
    		String userName,
    		String name,
    		String userType,
    		String pNumber,
    		String zipCode
                        )
    
    
    {
        super();
        this.userID = userID;
        this.userName = userName;
        this.name = name;
        this.userType = userType;
        this.pNumber = pNumber;
        this.zipCode = zipCode;
     
    }

    /**
     * Overriden protected Method from com.team9.bookingsystem.Threading.ThreadService
     * @return Returns a Task Object which returns the Rooms meeting search criteria as ArrayList<Room>
     * @see Object
     */
//    protected Task<Object> createTask(){
//        return new Task<Object>() {
//            @Override
//            protected Object call() throws Exception {
//
//
//                System.out.println("in call method");
//                MysqlUtil util = new MysqlUtil();
//
//                System.out.println("before util.composeQuery");
//                String query = util.composeRoomQuery(
//                		isID,
//                		isName,
//                        isUserName,
//                        isType,
//                        isPnumber,
//                        isRoomID,
//                        isSomething
//                		);
//                System.out.println("after util.composeQuery");
//                ArrayList<Room> searchResult =  util.getRooms(query);
//                searchResult.forEach(element -> System.out.println(element.getLocation()));
//                updateProgress(10, 10);
//
//
//                return searchResult;
//
//            }
//        };
    }
//}



