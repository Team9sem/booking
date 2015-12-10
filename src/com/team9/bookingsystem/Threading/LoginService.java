package com.team9.bookingsystem.Threading;
import com.team9.bookingsystem.MysqlUtil;
import com.team9.bookingsystem.User;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

/**
 * Created by pontuspohl on 25/10/15.
 */
public class LoginService extends ThreadService{

    private final String username;
    private final String passwd;

    public LoginService(String username, String passwd){
        super();
        this.username = username;
        this.passwd   = passwd;

    }

    protected Task<Object> createTask(){
        return new Task<Object>() {
            @Override
            protected Object call() throws Exception {
                System.out.println("creating util");
                MysqlUtil util = new MysqlUtil();

                System.out.println(util.toString());
                User loggedInUser =  util.loginAndGetUser(username, passwd);
                updateProgress(10,10);
                System.out.println(loggedInUser.toString());
                return loggedInUser;
            }
        };
    }


}