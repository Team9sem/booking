package com.team9.bookingsystem.Threading;

import com.team9.bookingsystem.MysqlUtil;
import javafx.concurrent.Task;

import java.util.ArrayList;

/**
 * Created by pontuspohl on 01/11/15.
 */
public class SearchService extends ThreadService{


    /**
     * variables needed to Perform Database Operation
     */
    private Task task;
    private String date;
    private String fromTime;
    private String toTime;
    private String size;

    /**
     * Class Constructor
     *
     * Initializes Instance variable task
     *
     * @param task custom Task object
     * @see Task
     */
    public SearchService(Task task){
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
    public SearchService(String date, String fromTime, String toTime, String size){
        super();
        this.date = date;
        this.fromTime = fromTime;
        this.toTime = toTime;
        this.size = size;
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

//                ArrayList<Room> searchResult =  util.searchRoom(date,fromTime,toTime,size);
                updateProgress(10,10);


//                return searchResult;
                return null;
            }
        };
    }
}
