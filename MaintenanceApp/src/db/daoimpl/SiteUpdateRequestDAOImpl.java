package db.daoimpl;

import db.DatabaseManager;
import db.OccupancyStatus;
import db.SiteData;
import db.SiteType;
import db.dao.SiteUpdateRequestDAO;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class SiteUpdateRequestDAOImpl implements SiteUpdateRequestDAO {

    @Override
    public List<SiteData> getAll(boolean approved) {
        List<SiteData> requests = new ArrayList<>();
        try (ResultSet rs =
             DatabaseManager.getPreparedStatement(
                     "select_site_update_requests",
                     approved
             ).executeQuery()) {

            while (rs.next()) {
                requests.add(new SiteData(
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
        return requests;
    }

    @Override
    public boolean exists(int sid) {
        try (ResultSet rs =
             DatabaseManager.getPreparedStatement(
                     "exists_site_update_request",
                     sid
             ).executeQuery()) {

            return rs.next() && rs.getInt("request_exists") == 1;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void insertOrUpdate(SiteData data) {
        try {
            DatabaseManager.getPreparedStatement(
                    "insert_or_replace_site_update_request",
                    data.sid(),
                    data.uid(),
                    data.location(),
                    data.type().toString(),
                    data.status().toString(),
                    data.length(),
                    data.breadth(),
                    false
            ).executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateApproval(int sid, boolean approved) {
        try {
            DatabaseManager.getPreparedStatement(
                    "update_approval_site_update_request",
                    approved,
                    sid
            ).executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
