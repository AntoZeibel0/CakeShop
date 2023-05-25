package com.example.cakeshop.service.mail;

import java.io.IOException;

public abstract class Subject {
    private ObserverSend observerSend = new ObserverSend();
    protected Subject() {
    }
    public void attach(ObserverSend send){
        observerSend = send;
    }
    public void notifyObservers(int id, String username) throws IOException {
        observerSend.sendEmail(id, username);
    }
}
