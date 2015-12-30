package com.team9.bookingsystem.Threading.Admin;

import com.team9.bookingsystem.MysqlUtil;
import com.team9.bookingsystem.Room;
import com.team9.bookingsystem.User;
import javafx.concurrent.Task;

import java.util.ArrayList;

/**
 * Created by pontuspohl on 16/11/15.
 *
 *
 * All content by Pontus
 *
 *
 *
 */
public class RoomSearchService extends AdminSearchService {

    private Task task;
    private Room room;
    private boolean small;
    private boolean medium;
    private boolean large;
    private int roomID;
    private String roomSize;
    private String location;
    private int hasWhiteboard;
    private int hasProjector;
    private int hasCoffeMachine;




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

    public RoomSearchService(int roomID,
            String roomSize, String location,
            boolean hasWhiteboard,
            boolean hasProjector,
            boolean hasCoffeMachine)
    {
        super();

        this.roomID = roomID;
        this.roomSize = roomSize;
        this.location =location;
        if(hasWhiteboard) this.hasWhiteboard = 1;
        else this.hasWhiteboard = 0;
        if(hasProjector) this.hasProjector = 1;
        else this.hasProjector = 0;
        if(hasCoffeMachine) this.hasCoffeMachine = 1;
        else this.hasCoffeMachine = 0;

    }
    public RoomSearchService(Room room,boolean small,boolean medium, boolean large){

        this.room   = room;
        this.small  = small;
        this.medium = medium;
        this.large  = large;
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

                ArrayList<Room> result = util.getRooms(room,small,medium,large);


                return result;
            }
        };
}



}
