package db.dao;

import db.SiteData;

import java.util.List;

public interface SiteUpdateRequestDAO {
    List<SiteData> getAll(boolean approved);
    boolean exists(int sid);
    void insertOrUpdate(SiteData data);
    void updateApproval(int sid, boolean approved);
}
