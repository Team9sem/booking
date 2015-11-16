package com.team9.bookingsystem.Threading;


import javafx.concurrent.Task;

/**
 * Created by pontuspohl on 16/11/15.
 */
public class AdminSearchService extends ThreadService {
    private Task task;

    public AdminSearchService(){

    }

    public AdminSearchService(Task task){
        this.task = task;
    }


}
