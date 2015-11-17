package com.team9.bookingsystem.Threading;

import com.team9.bookingsystem.MysqlUtil;
import com.team9.bookingsystem.Room;
import javafx.concurrent.Task;

import java.util.ArrayList;

/**
 * Created by pontuspohl on 01/11/15.
 */
public class FindRoomService extends ThreadService{


    /**
     * variables needed to Perform Database Operation
     */
    private Task task;
    private String date;
    private String fromTime;
    private String toTime;
    private String size;
    private boolean isSmall;
    private boolean isMedium;
    private boolean isLarge;
    private boolean hasCoffeemachine;
    private boolean hasWhiteBoard;
    private boolean hasProjector;
    private String location;

    /**
     * Class Constructor
     *
     * Initializes Instance variable task
     *
     * @param task custom Task object
     * @see Task
     */
    public FindRoomService(Task task){
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
    public FindRoomService(String date,
                           String fromTime,
                           String toTime,
                           boolean isSmall,
                           boolean isMedium,
                           boolean isLarge,
                           boolean hasCoffeemachine,
                           boolean hasWhiteBoard,
                           boolean hasProjector)
    {
        super();
        this.date = date;
        this.fromTime = fromTime;
        this.toTime = toTime;
        this.size = size;
        this.isSmall = isSmall;
        this.isMedium = isMedium;
        this.isLarge = isLarge;
        this.hasCoffeemachine = hasCoffeemachine;
        this.hasProjector = hasProjector;
        this.hasWhiteBoard = hasWhiteBoard;
        this.location = location;
    }

    
    

    /**
     * Overriden protected Method from com.team9.bookingsystem.Threading.ThreadService
     * @return Returns a Task Object which returns the Rooms meeting search criteria as ArrayList<Room>
     * @see Object
     */
    @Override
    protected Task<Object> createTask(){
        return new Task<Object>() {
            @Override
            protected Object call() throws Exception {


                System.out.println("in call method");
                MysqlUtil util = new MysqlUtil();

                System.out.println("before util.composeQuery");
                String query = util.composeRoomQuery(
                        location,
                        isSmall,
                        isMedium,
                        isLarge,
                        hasProjector,
                        hasWhiteBoard,
                        hasCoffeemachine,
                        date,
                        fromTime,
                        toTime);
                System.out.println("after util.composeQuery");
                ArrayList<Room> searchResult =  util.getRooms(query);
                searchResult.forEach(element -> System.out.println(element.getLocation()));
                updateProgress(10, 10);


                return searchResult;

            }
        };
    }
}
