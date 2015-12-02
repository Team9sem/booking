package com.team9.bookingsystem;

/**
 * Created by pontuspohl on 26/11/15.
 */
public interface DialogCallback<T> {

    public void onSuccess(T param);

    public void onFailure();

}
