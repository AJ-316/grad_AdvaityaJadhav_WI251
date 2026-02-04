package db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class LMADBManager {

    private static final LMADBManager INSTANCE = new LMADBManager();

    public LMADBManager() {
        DatabaseManager.establishConnection("jdbc:postgresql://localhost:5432/lma", "postgres", "psql1234");

        DatabaseManager.addPreparedStatement("insert_user", "insert into users(name, role) values(?, ?::user_role)");
        DatabaseManager.addPreparedStatement("select_user_uid", "select * from users where uid = ?");
        DatabaseManager.addPreparedStatement("exists_user", "select case when exists (select 1 from users where uid = ?) then 1 else 0 end as user_exists");
        DatabaseManager.addPreparedStatement("select_user", "select * from users where role = ?::user_role");

        DatabaseManager.addPreparedStatement("select_site_update_requests", "select * from site_update_requests where approved = ?");
        DatabaseManager.addPreparedStatement("exists_site_update_request", "select case when exists (select 1 from site_update_requests where sid = ?) then 1 else 0 end as request_exists");
        DatabaseManager.addPreparedStatement("update_approval_site_update_request", "update site_update_requests set approved = ? where sid = ?");
        DatabaseManager.addPreparedStatement("insert_or_replace_site_update_request", "insert into site_update_requests values(?, ?, ?, ?::site_type, ?::site_status, ?, ?, ?) on CONFLICT (sid) DO UPDATE SET new_location = EXCLUDED.new_location, new_type = EXCLUDED.new_type, new_status = EXCLUDED.new_status, new_length = EXCLUDED.new_length, new_breadth = EXCLUDED.new_breadth");

        DatabaseManager.addPreparedStatement("select_site_sid", "select * from sites where sid = ?");
        DatabaseManager.addPreparedStatement("select_site_uid", "select * from sites where uid = ?");
        DatabaseManager.addPreparedStatement("select_sites", "select * from sites");
        DatabaseManager.addPreparedStatement("update_sites", "update sites set location = ?, type = ?::site_type, status = ?::site_status, length = ?, breadth = ? where sid = ?");

        DatabaseManager.addPreparedStatement("select_maintenance", "select * from maintenance where paid = ?");
        DatabaseManager.addPreparedStatement("insert_maintenance", "insert into maintenance(sid, amount, paid, updated_uid) values (?, ?, ?, ?)");
        DatabaseManager.addPreparedStatement("update_maintenance", "update maintenance set paid = ?, updated_uid = ? where sid = ?");

    }

    public static LMADBManager instance() {
        return INSTANCE;
    }

    public UserData getUser(int uid) {
        UserData data = null;
        try {
            ResultSet rs = DatabaseManager.getPreparedStatement("select_user_uid", uid).executeQuery();
            if (rs.next()) {
                data = new UserData(rs.getInt("uid"), rs.getString("name"), rs.getString("role"));
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (data == null)
            System.out.println("User not found!");
        return data;
    }

    public List<SiteData> selectAllSites() {
        List<SiteData> siteDataList = new ArrayList<>();
        try {
            ResultSet rs = DatabaseManager.getPreparedStatement("select_sites").executeQuery();
            while (rs.next()) {
                siteDataList.add(new SiteData(
                        rs.getInt("sid"),
                        rs.getInt("uid"),
                        rs.getString("location"),
                        SiteType.valueOf(rs.getString("type")),
                        OccupancyStatus.valueOf(rs.getString("status")),
                        rs.getInt("length"),
                        rs.getInt("breadth")
                ));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return siteDataList;
    }

    public void insertMaintenance(List<MaintenanceData> newDataList) {
        try {
            PreparedStatement ps = DatabaseManager.getPreparedStatement("insert_maintenance");
            for (MaintenanceData data : newDataList) {
                ps.setInt(1, data.sid());
                ps.setInt(2, data.amount());
                ps.setBoolean(3, data.paid());
                ps.setInt(4, data.update_uid());
                ps.addBatch();
            }
            ps.executeBatch();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void updateMaintenance(List<MaintenanceData> newDataList) {
        try {
            PreparedStatement ps = DatabaseManager.getPreparedStatement("update_maintenance");
            for (MaintenanceData data : newDataList) {
                ps.setBoolean(1, data.paid());
                ps.setInt(2, data.update_uid());
                ps.setInt(3, data.sid());
                ps.addBatch();
            }
            ps.executeBatch();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void updateSiteData(SiteData newData) {
        try {
            DatabaseManager.getPreparedStatement("update_sites",
                    newData.location(), newData.type().toString(), newData.status().toString(), newData.length(), newData.breadth(), newData.sid()).executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public boolean isUserPresent(int uid) {
        try {
            ResultSet rs = DatabaseManager.getPreparedStatement("exists_user", uid).executeQuery();
            if (rs.next())
                return rs.getInt("user_exists") == 1;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    public SiteData getSite(int sid) {
        SiteData data = null;
        try {
            ResultSet rs = DatabaseManager.getPreparedStatement("select_site_sid", sid).executeQuery();
            if (rs.next()) {
                data = new SiteData(
                        rs.getInt("sid"),
                        rs.getInt("uid"),
                        rs.getString("location"),
                        SiteType.valueOf(rs.getString("type")),
                        OccupancyStatus.valueOf(rs.getString("status")),
                        rs.getInt("length"),
                        rs.getInt("breadth")
                );
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (data == null)
            System.out.println("Site not found!");
        return data;
    }

    public boolean isAdmin(UserData data) {
        return data.role().equals("ADMIN");
    }

    public boolean isOwner(UserData user) {
        return user.role().equals("SITE_OWNER");
    }

    public List<MaintenanceData> selectAllMaintenance(boolean paid) {
        List<MaintenanceData> maintenanceDataList = new ArrayList<>();
        try {
            ResultSet rs = DatabaseManager.getPreparedStatement("select_maintenance", paid).executeQuery();
            while (rs.next()) {
                maintenanceDataList.add(new MaintenanceData(
                        rs.getInt("sid"),
                        rs.getInt("amount"),
                        rs.getBoolean("paid"),
                        rs.getInt("updated_uid")
                ));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return maintenanceDataList;
    }

    public List<SiteData> getSiteOfOwner(int uid) {
        List<SiteData> sites = new ArrayList<>();
        try {
            ResultSet rs = DatabaseManager.getPreparedStatement("select_site_uid", uid).executeQuery();
            while (rs.next()) {
                sites.add(new SiteData(
                        rs.getInt("sid"),
                        rs.getInt("uid"),
                        rs.getString("location"),
                        SiteType.valueOf(rs.getString("type")),
                        OccupancyStatus.valueOf(rs.getString("status")),
                        rs.getInt("length"),
                        rs.getInt("breadth")
                ));
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return sites;
    }

    public void insertUser(UserData user) {
        try {
            DatabaseManager.getPreparedStatement("insert_user", user.name(), user.role()).executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<UserData> selectAllUsers(String role) {
        List<UserData> users = new ArrayList<>();
        try {
            ResultSet rs = DatabaseManager.getPreparedStatement("select_user", role).executeQuery();
            while (rs.next()) {
                users.add(new UserData(
                        rs.getInt("uid"),
                        rs.getString("name"),
                        rs.getString("role")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return users;
    }

    public List<SiteData> selectAllRequests(boolean approved) {
        List<SiteData> requestList = new ArrayList<>();
        try {
            ResultSet rs = DatabaseManager.getPreparedStatement("select_site_update_requests", approved).executeQuery();
            while (rs.next()) {
                requestList.add(new SiteData(
                        rs.getInt("sid"),
                        rs.getInt("uid"),
                        rs.getString("new_location"),
                        SiteType.valueOf(rs.getString("new_type")),
                        OccupancyStatus.valueOf(rs.getString("new_status")),
                        rs.getInt("new_length"),
                        rs.getInt("new_breadth")
                ));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return requestList;
    }

    public boolean isRequestPresent(int sid) {
        try {
            ResultSet rs = DatabaseManager.getPreparedStatement("exists_site_update_request", sid).executeQuery();
            if (rs.next())
                return rs.getInt("request_exists") == 1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public void insertRequest(SiteData data) {
        try {
            DatabaseManager.getPreparedStatement("insert_or_replace_site_update_request",
                    data.sid(), data.uid(), data.location(),
                    data.type().toString(), data.status().toString(), data.length(),
                    data.breadth(), false
            ).executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateRequest(int sid, boolean approved) {
        try {
            DatabaseManager.getPreparedStatement("update_approval_site_update_request", approved, sid).executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
