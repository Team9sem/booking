package com.team9.bookingsystem.Threading;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import java.util.ArrayList;

/**
 * Created By Pontus Pohl 31/10 - 2015
 * Basic ThreadService that extends Service<Object>
 * @see Service
 */
public class ThreadService extends Service<Object> {

    /**
     *
     */
    private Task task;


    public ThreadService(){}

    /**
     * Class constructor
     * Initializes private variable task
     *
     * @param task task with return type <Object>
     */
    public ThreadService(Task<Object> task){
        this.task = task;
    }

    /**
     * Overriding method that returns a task<Object>
     *
     * @return instance variable task
     * @see Task
     */

    /**
     * Overriden abstract Method from javafx.concurrent.Service
     * @return Returns Task
     * @see Task
     */
    @Override
    protected Task<Object> createTask(){
        return task;
    }

    public void setTask(Task<Object> task){
        this.task = task;
    }
}
