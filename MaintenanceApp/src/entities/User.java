package entities;

import db.UserData;

public abstract class User {

    protected final UserData data;

    public User(UserData data) {
        this.data = data;
    }

    public UserData getData() {
        return data;
    }
}