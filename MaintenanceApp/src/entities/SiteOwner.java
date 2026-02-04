package entities;

import db.SiteData;
import db.UserData;

import java.util.List;

public class SiteOwner extends User {

    public SiteOwner(UserData data) {
        super(data);
    }

    public void checkSites(List<SiteData> sites) {
        for (SiteData data : sites) {
            System.out.println(data);
        }
    }
}
