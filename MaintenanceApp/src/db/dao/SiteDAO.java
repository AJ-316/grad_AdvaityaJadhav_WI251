package db.dao;

import db.SiteData;

import java.util.List;

public interface SiteDAO {
    SiteData getById(int sid);
    List<SiteData> getAll();
    List<SiteData> getByOwner(int uid);
    void update(SiteData site);
}
