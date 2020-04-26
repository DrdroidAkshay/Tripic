package com.tripic.tripic;


public class Notificationitem {
    private String requestername;
    private String requesterphone;
    private String requeststatus;

    public Notificationitem(String requestername, String requesterphone, String requeststatus) {
        this.requestername = requestername;
        this.requesterphone = requesterphone;
        this.requeststatus = requeststatus;
    }

    public String getRequestername() {
        return requestername;
    }

    public String getRequesterphone() {
        return requesterphone;
    }

    public String getRequeststatus() {
        return requeststatus;
    }
}
