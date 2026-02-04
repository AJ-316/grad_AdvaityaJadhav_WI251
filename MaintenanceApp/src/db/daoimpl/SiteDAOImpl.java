package db.daoimpl;

import db.DatabaseManager;
import db.OccupancyStatus;
import db.SiteData;
import db.SiteType;
import db.dao.SiteDAO;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class SiteDAOImpl implements SiteDAO {

    @Override
    public SiteData getById(int sid) {
        try (ResultSet rs =
             DatabaseManager.getPreparedStatement("select_site_sid", sid).executeQuery()) {

            if (rs.next()) {
                return new SiteData(
                        rs.getInt("sid"),
                        rs.getInt("uid"),
                        rs.getString("location"),
                        SiteType.valueOf(rs.getString("type")),
                        OccupancyStatus.valueOf(rs.getString("status")),
                        rs.getInt("length"),
                        rs.getInt("breadth")
                );
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public List<SiteData> getAll() {
        List<SiteData> sites = new ArrayList<>();
        try (ResultSet rs =
             DatabaseManager.getPreparedStatement("select_sites").executeQuery()) {

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
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return sites;
    }

    @Override
    public List<SiteData> getByOwner(int uid) {
        List<SiteData> sites = new ArrayList<>();
        try (ResultSet rs =
             DatabaseManager.getPreparedStatement("select_site_uid", uid).executeQuery()) {

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
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return sites;
    }

    @Override
    public void update(SiteData site) {
        try {
            DatabaseManager.getPreparedStatement(
                    "update_sites",
                    site.location(),
                    site.type().toString(),
                    site.status().toString(),
                    site.length(),
                    site.breadth(),
                    site.sid()
            ).executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
