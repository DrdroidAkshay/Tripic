package com.tripic.tripic;

public class Listitem {
    private String username;
    private String userphone;
    private String ridefrom;
    private String rideto;
    private String ridedate;
    private String ridetime;

    public Listitem(String username, String userphone, String ridefrom, String rideto, String ridedate, String ridetime) {
        this.username = username;
        this.userphone = userphone;
        this.ridefrom = ridefrom;
        this.rideto = rideto;
        this.ridedate = ridedate;
        this.ridetime = ridetime;
    }

    public String getUsername() {
        return username;
    }

    public String getUserphone() {
        return userphone;
    }

    public String getRidefrom() {
        return ridefrom;
    }

    public String getRideto() {
        return rideto;
    }

    public String getRidedate() {
        return ridedate;
    }

    public String getRidetime() {
        return ridetime;
    }
}
