package com.mixoor.khademni.payload.response;

public class UserIdentityAvailability {

    private boolean availble;


    public UserIdentityAvailability(boolean availble) {
        this.availble = availble;
    }

    public boolean isAvailble() {
        return availble;
    }

    public void setAvailble(boolean availble) {
        this.availble = availble;
    }
}
