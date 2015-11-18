package com.team9.bookingsystem.Threading.Admin;


import com.team9.bookingsystem.Threading.ThreadService;
import javafx.concurrent.Task;

/**
 * Created by pontuspohl on 16/11/15.
 */
public abstract class AdminSearchService extends ThreadService {
    private Task task;

    public AdminSearchService(){
    }

    public AdminSearchService(Task task){
        this.task = task;
    }


}
