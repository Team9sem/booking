package com.team9.bookingsystem.Components;

/**
 * Created by pontuspohl on 26/11/15.
 * All Content by Pontus Pohl
 * used when handling data passed when using popups. Behaves like any callback.
 */
public interface DialogCallback<T> {

    public void onSuccess(T param);

    public void onFailure();

}
