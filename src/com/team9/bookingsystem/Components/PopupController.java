package com.team9.bookingsystem.Components;

import com.team9.bookingsystem.Components.DialogCallback;
import javafx.stage.Stage;

/**
 * Created by pontuspohl on 25/11/15.
 * All Content by Pontus Pohl
 *
 */
public interface PopupController  {

    public void setStage(Stage stage);
    public boolean isOkClicked();
    public void setCallBack(DialogCallback param);

}
