package com.team9.bookingsystem.Components;

/**
 * Created by pontuspohl on 27/12/15.
 * All Content by Pontus Pohl
 * used when handling data passed in ProfileController.
 */
public abstract class getBookingsCallback<T>  {




    private Boolean gotFutureBookings = false;
    private Boolean gotPastBookings = false;

    public getBookingsCallback(){
        this.gotFutureBookings = false;
        this.gotPastBookings = false;
    }

    public abstract void onSuccess();

    public abstract void onFailure();

    public void setGotFutureBookings(Boolean gotFutureBookings) {
        this.gotFutureBookings = gotFutureBookings;
    }

    public void setGotPastBookings(Boolean gotPastBookings) {
        this.gotPastBookings = gotPastBookings;
    }
    public Boolean getGotFutureBookings() {
        return gotFutureBookings;
    }

    public Boolean getGotPastBookings() {
        return gotPastBookings;
    }

}
