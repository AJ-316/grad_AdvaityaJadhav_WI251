package db.dao;

import db.UserData;

import java.util.List;

public interface UserDAO {
    UserData getById(int uid);
    void insert(UserData user);
    boolean exists(int uid);
    List<UserData> findByRole(String role);
}
