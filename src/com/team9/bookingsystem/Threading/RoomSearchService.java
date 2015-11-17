package com.team9.bookingsystem.Threading;

import javafx.concurrent.Task;

/**
 * Created by pontuspohl on 16/11/15.
 */
public class RoomSearchService extends AdminSearchService {

    private Task task;

    private String roomID;
    private String roomSize;
    private String location;
    private boolean hasWhiteboard;
    private boolean hasProjector;
    private boolean hasCoffeMachine;




    /**
     * Class Constructor
     *
     * Initializes Instance variable task
     *
     * @param task custom Task object
     * @see Task
     */
    public RoomSearchService(Task task){
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

    public RoomSearchService(String roomID,
            String roomSize, String location,
            boolean hasWhiteboard,
            boolean hasProjector,
            boolean hasCoffeMachine)
    {
        super();
        this.roomID = roomID;
        this.roomSize = roomSize;
        this.location =location;
        this.hasCoffeMachine = hasCoffeMachine;
        this.hasProjector = hasProjector;
        this.hasWhiteboard = hasWhiteboard;

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



                return null;
            }
        };
}



}
