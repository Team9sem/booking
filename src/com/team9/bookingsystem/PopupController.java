package com.team9.bookingsystem;

import javafx.stage.Stage;

/**
 * Created by pontuspohl on 25/11/15.
 */
public interface PopupController  {

    public void setStage(Stage stage);
    public boolean isOkClicked();
    public void setCallBack(DialogCallback param);

}
