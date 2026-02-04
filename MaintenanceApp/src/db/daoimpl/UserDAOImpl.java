package db.daoimpl;

import db.DatabaseManager;
import db.UserData;
import db.dao.UserDAO;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class UserDAOImpl implements UserDAO {

    @Override
    public UserData getById(int uid) {
        try (ResultSet rs =
             DatabaseManager.getPreparedStatement("select_user_uid", uid).executeQuery()) {

            if (rs.next()) {
                return new UserData(
                        rs.getInt("uid"),
                        rs.getString("name"),
                        rs.getString("role")
                );
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public void insert(UserData user) {
        try {
            DatabaseManager.getPreparedStatement(
                    "insert_user",
                    user.name(),
                    user.role()
            ).executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean exists(int uid) {
        try (ResultSet rs =
             DatabaseManager.getPreparedStatement("exists_user", uid).executeQuery()) {

            return rs.next() && rs.getInt("user_exists") == 1;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<UserData> findByRole(String role) {
        List<UserData> users = new ArrayList<>();
        try (ResultSet rs =
             DatabaseManager.getPreparedStatement("select_user", role).executeQuery()) {

            while (rs.next()) {
                users.add(new UserData(
                        rs.getInt("uid"),
                        rs.getString("name"),
                        rs.getString("role")
                ));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return users;
    }
}
